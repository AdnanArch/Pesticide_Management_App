package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.CompanyAccount;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CompanyAccountController {
    @FXML
    private TextField accountHolderNameTextField;

    @FXML
    private TextField accountNoTextField;

    @FXML
    private TextField balanceTextField;

    @FXML
    private TextField bankNameTextField;

    @FXML
    private TableView<CompanyAccount> compnayAccountsTableView;

    @FXML
    private TextField searchField;

    private CompanyAccount selectedAccount; // Store the selected account for update

    public void initialize() {
        populateCompanyAccountsTable(); // Load initial data into the TableView

        // Add a listener to handle row selection
        compnayAccountsTableView.setRowFactory(tv -> {
            TableRow<CompanyAccount> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    selectedAccount = row.getItem();
                    // Fill the input fields with the selected account data
                    fillInputFields(selectedAccount);
                }
            });
            return row;
        });
    }

    public void populateCompanyAccountsTable() {
        // Call a method in the DatabaseOperations class to retrieve the company account data
        ObservableList<CompanyAccount> accountData = DatabaseOperations.getAllCompanyAccounts();
        // Set the retrieved data in the TableView
        compnayAccountsTableView.setItems(accountData);
    }

    @FXML
    void onAddClick() {
        String accountNo = accountNoTextField.getText();
        String accountHolderName = accountHolderNameTextField.getText();
        String bankName = bankNameTextField.getText();
        String balance = balanceTextField.getText();

        boolean isCorrect = checkInputFields(accountNo, accountHolderName, bankName, balance);

        if (isCorrect) {
            boolean result = DatabaseOperations.addCompanyAccount(accountNo, accountHolderName, bankName, balance);

            if (result) {
                MessageDialogs.showMessageDialog("Account has been added successfully!");
                onRefreshClick();
            } else {
                MessageDialogs.showErrorMessage("An Error occurred while adding the account." +
                        "\nCheck the input data and try again!");
            }
        }
    }

    @FXML
    void onDeleteClick() {
        if (selectedAccount != null) {
            boolean confirmed = MessageDialogs.showConfirmationDialog("Are you sure you want to delete this account?");

            if (confirmed) {
                boolean result = DatabaseOperations.deleteCompanyAccount(selectedAccount.getAccountNo());

                if (result) {
                    MessageDialogs.showMessageDialog("Account has been deleted successfully!");
                    onRefreshClick();
                } else {
                    MessageDialogs.showErrorMessage("An Error occurred while deleting the account.");
                }
            }
        } else {
            MessageDialogs.showErrorMessage("Please select an account to delete.");
        }
    }

    @FXML
    void onSearchClick() {
        String searchText = searchField.getText();

        if (!searchText.isEmpty()) {
            // Call a method in the CompanyAccountDAO class to search for accounts
            ObservableList<CompanyAccount> searchResults = DatabaseOperations.searchCompanyAccounts(searchText);

            // Update the TableView with the search results
            compnayAccountsTableView.setItems(searchResults);
        } else {
            MessageDialogs.showErrorMessage("Please enter a search query.");
        }
    }

    @FXML
    void onUpdateClick() {
        if (selectedAccount != null) {
            String accountNo = accountNoTextField.getText();
            String accountHolderName = accountHolderNameTextField.getText();
            String bankName = bankNameTextField.getText();
            String balance = balanceTextField.getText();

            boolean isCorrect = checkInputFields(accountNo, accountHolderName, bankName, balance);

            if (isCorrect) {
                boolean updated = DatabaseOperations.updateCompanyAccount(accountNo, accountHolderName, bankName,
                        Double.parseDouble(balance));

                if (updated) {
                    MessageDialogs.showMessageDialog("Account has been updated successfully!");
                    onRefreshClick();
                } else {
                    MessageDialogs.showErrorMessage("An Error occurred while updating the account.");
                }
            }
        } else {
            MessageDialogs.showErrorMessage("Please select an account to update.");
        }
    }

    private boolean checkInputFields(String accountNo, String accountHolderName, String bankName, String balance) {
        if (accountNo.isEmpty()) {
            return false;
        } else if (accountHolderName.isEmpty()) {
            return false;
        } else if (bankName.isEmpty()) {
            return false;
        } else
            return !balance.isEmpty();
    }

    private void fillInputFields(CompanyAccount account) {
        if (account != null) {
            accountNoTextField.setText(account.getAccountNo());
            accountHolderNameTextField.setText(account.getAccountHolderName());
            bankNameTextField.setText(account.getBankName());
            balanceTextField.setText((account.getTotalBalance()));
        }
    }

    public void clearInputFields() {
        accountHolderNameTextField.clear();
        accountNoTextField.clear();
        balanceTextField.clear();
        bankNameTextField.clear();
        searchField.clear();
        selectedAccount = null; // Clear the selected account
    }

    @FXML
    void onRefreshClick() {
        clearInputFields();
        populateCompanyAccountsTable(); // Call the method to refresh the TableView
    }
}
