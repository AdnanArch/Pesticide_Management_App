package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.ImportAccountHoldersData;
import com.al_makkah_traders_app.model.MaskedTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Controller for adding, updating, and deleting account holders.
 */
public class AddNewAccountHolderController {
    @FXML
    private TableView<AccountHolder> accountHolderTableView;
    @FXML
    private TextField addressTextField;
    @FXML
    private MaskedTextField cnicNoTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private MaskedTextField phoneTextField;
    @FXML
    private TextField searchField;
    @FXML
    private CheckBox wholesalerCheck;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        populateAccountHoldersTable();
        setEnterKeySearchBehavior(searchField);
        addTableSelectionListener(accountHolderTableView);
    }

    private void setEnterKeySearchBehavior(TextField searchField) {
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onSearch();
                event.consume();
            }
        });
    }

    /**
     * Populates the account holders table with data from the database.
     */
    public void populateAccountHoldersTable() {
        ObservableList<AccountHolder> accountHoldersData = DatabaseOperations.getAllAccountHolders();
        accountHolderTableView.setItems(accountHoldersData);
    }

    private void addTableSelectionListener(TableView<AccountHolder> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        fillFormFields(newValue);
                    }
                });
    }

    private void fillFormFields(AccountHolder selectedAccountHolder) {
        nameTextField.setText(selectedAccountHolder.getName());
        cnicNoTextField.setPlainText(selectedAccountHolder.getCnicNo());
        addressTextField.setText(selectedAccountHolder.getAddress());
        phoneTextField.setText(selectedAccountHolder.getPhone());
        wholesalerCheck.setSelected(selectedAccountHolder.isRetailerProperty().get());
    }

    @FXML
    void onAdd() {
        String name = nameTextField.getText();
        String cnic = cnicNoTextField.getPlainText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        boolean isRetailer = wholesalerCheck.isSelected();

        boolean isValid = checkDataValidity(name, cnic, address, phone);

        if (isValid) {
            boolean added = DatabaseOperations.createAccountHolder(name, cnic, address, phone, isRetailer);

            if (added) {
                MessageDialogs.showMessageDialog("Account holder has been added successfully.");
                clearInputFields();
                populateAccountHoldersTable();
            } else {
                MessageDialogs.showErrorMessage("An error occurred while adding the account holder.");
            }
        }
    }

    private boolean checkDataValidity(String name, String cnic, String address, String phone) {
        if (name.isEmpty()) {
            MessageDialogs.showErrorMessage("Please enter Account Holder name.");
            return false;
        } else if (cnic.isEmpty()) {
            MessageDialogs.showErrorMessage("Please enter CNIC number.");
            return false;
        } else if (address.isEmpty()) {
            MessageDialogs.showErrorMessage("Please enter the address of the account holder.");
            return false;
        } else if (phone.isEmpty()) {
            MessageDialogs.showErrorMessage("Please enter phone number of the account holder.");
            return false;
        }
        return true;
    }

    @FXML
    void onDelete() {
        String cnicNo = cnicNoTextField.getPlainText().trim();
        if (cnicNo.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select an Account Holder to delete.");
            return;
        }

        boolean confirmed = MessageDialogs
                .showConfirmationDialog("Are you sure you want to delete this account holder?");

        if (confirmed) {
            boolean deleted = DatabaseOperations.deleteAccountHolder(cnicNo);

            if (deleted) {
                MessageDialogs.showMessageDialog("Account holder has been deleted successfully.");
                populateAccountHoldersTable();
                clearInputFields();
            } else {
                MessageDialogs.showErrorMessage("This account holder cannot be deleted.");
            }
        }
    }

    @FXML
    void onImport() {
        ImportAccountHoldersData accountHolder = new ImportAccountHoldersData();
        Stage stage = new Stage();
        try {
            accountHolder.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onRefresh() {
        populateAccountHoldersTable();
        clearInputFields();
    }

    @FXML
    void onSearch() {
        String searchText = searchField.getText();
        ObservableList<AccountHolder> accountHoldersData = DatabaseOperations.searchAccountHolders(searchText);
        accountHolderTableView.setItems(accountHoldersData);
    }

    @FXML
    void onUpdate() {
        String name = nameTextField.getText().trim();
        String cnic = cnicNoTextField.getPlainText().trim();
        String address = addressTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        boolean isRetailer = wholesalerCheck.isSelected();

        boolean isValid = checkDataValidity(name, cnic, address, phone);

        if (isValid) {
            boolean confirmed = MessageDialogs
                    .showConfirmationDialog("Are you sure you want to update this account holder's data?");

            if (confirmed) {
                boolean updated = DatabaseOperations.updateAccountHolderInfo(name, cnic, address, phone, isRetailer);

                if (updated) {
                    MessageDialogs.showMessageDialog("Account Holder's data is successfully updated.");
                } else {
                    MessageDialogs.showWarningMessage("Account Holder's data was not updated. Try deleting this " +
                            "Account Holder and creating a new one.");
                }

                populateAccountHoldersTable();
            }
        }
        clearInputFields();
    }

    private void clearInputFields() {
        nameTextField.clear();
        cnicNoTextField.clear();
        addressTextField.clear();
        phoneTextField.clear();
        wholesalerCheck.setSelected(false);
    }
}
