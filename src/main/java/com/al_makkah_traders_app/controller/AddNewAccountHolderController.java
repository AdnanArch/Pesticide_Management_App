package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.ImportAccountHoldersData;
import com.al_makkah_traders_app.model.MaskedTextField;
import com.al_makkah_traders_app.utility.NumberFormatter;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for adding, updating, and deleting account holders.
 */
public class AddNewAccountHolderController {
    @FXML
    private TextField balanceTextField;
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
    @FXML
    private CheckBox disableUserCheckBox;

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
        balanceTextField.setText(String.valueOf(NumberFormatter.removeCommas(selectedAccountHolder.getTotalBalance())));
        disableUserCheckBox.setSelected(false);
    }

    @FXML
    void onAdd() {
        String name = nameTextField.getText();
        String cnic = cnicNoTextField.getPlainText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        boolean isRetailer = wholesalerCheck.isSelected();
        String balance = balanceTextField.getText();

        boolean isValid = checkDataValidity(name, cnic, address, phone, balance);

        if (isValid) {
            boolean added = DatabaseOperations.createAccountHolder(name, cnic, address, phone, isRetailer, balance);

            if (added) {
                MessageDialogs.showMessageDialog("Account holder has been added successfully.");
                clearInputFields();
                populateAccountHoldersTable();
            } else {
                MessageDialogs.showErrorMessage("An error occurred while adding the account holder.");
            }
        }
    }

    private boolean checkDataValidity(String name, String cnic, String address, String phone, String balance) {
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
        } else if (!Utility.isNumeric(balance)) {
            MessageDialogs.showErrorMessage("Please enter a valid balance.");
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
        boolean isActive = disableUserCheckBox.isSelected();
        String balance = balanceTextField.getText();

        if (isActive) {
            if (Double.parseDouble(balance) == 0) {
                boolean sure = MessageDialogs.showConfirmationDialog("Are you sure you want to disable this account holder?");
                if (!sure) return;

                boolean disabled = DatabaseOperations.disableAccountHolder(cnic);

                if (disabled) {
                    MessageDialogs.showMessageDialog("Account Holder is successfully disabled.");
                    populateAccountHoldersTable();
                } else {
                    MessageDialogs.showWarningMessage("Account Holder not updated.");
                }
            } else {
                MessageDialogs.showWarningMessage("Account Holder's balance is not zero. Please clear the balance first.");
            }
            return;
        }

        boolean isValid = checkDataValidity(name, cnic, address, phone, balance);

        if (isValid) {
            boolean confirmed = MessageDialogs.showConfirmationDialog("Are you sure you want to update this account holder's data?");
            if (confirmed) {
                boolean updated = DatabaseOperations.updateAccountHolderInfo(name, cnic, address, phone, isRetailer, balance);
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
        balanceTextField.clear();
        disableUserCheckBox.setSelected(false);
    }

    @FXML
    void onExport() {
        Workbook workbook = new XSSFWorkbook(); // Create a new Workbook
        Sheet sheet = workbook.createSheet("Account Holders"); // Create a Sheet

        // Create a header row and populate it with column names
        Row headerRow = sheet.createRow(0);
        List<String> columnNames = Arrays.asList("Name", "CNIC", "Address", "Phone", "Is Retailer", "Balance");
        for (int i = 0; i < columnNames.size(); i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnNames.get(i));
        }

        // Populate the sheet with data from accountHolderTableView
        ObservableList<AccountHolder> accountHolders = accountHolderTableView.getItems();
        for (int i = 0; i < accountHolders.size(); i++) {
            AccountHolder accountHolder = accountHolders.get(i);
            Row row = sheet.createRow(i + 1); // +1 because header row is at position 0

            row.createCell(0).setCellValue(accountHolder.getName());
            row.createCell(1).setCellValue(accountHolder.getCnicNo());
            row.createCell(2).setCellValue(accountHolder.getAddress());
            row.createCell(3).setCellValue(accountHolder.getPhone());
            row.createCell(4).setCellValue(accountHolder.isRetailer());
            if (accountHolder.getDebitOrCredit().equals("Debit")) {
                row.createCell(5).setCellValue(NumberFormatter.removeCommas(accountHolder.getTotalBalance()));
            } else {
                row.createCell(5).setCellValue(-NumberFormatter.removeCommas(accountHolder.getTotalBalance()));
            }
        }

        // Open a FileChooser to let the user select where to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null); // Replace null with your current stage

        if (file != null) {
            // Write the workbook to the selected file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                MessageDialogs.showMessageDialog("File has been saved successfully.");
            } catch (IOException e) {
                MessageDialogs.showErrorMessage("An error occurred while saving the file.");
                throw new RuntimeException("Error occurred while writing file" + e.getMessage());
            }
        }
    }

}
