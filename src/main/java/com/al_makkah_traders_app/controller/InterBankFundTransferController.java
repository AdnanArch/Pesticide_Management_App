package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

/**
 * Author: Adnan Rafique
 * Date: 6/15/2024
 * Time: 10:30 AM
 * Version: 1.0
 */

public class InterBankFundTransferController {

    @FXML
    private TextField amountTextField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private SearchableComboBox<String> receivingAccountComboBox;

    @FXML
    private SearchableComboBox<String> sendingAccountComboBox;

    public void initialize() {
        populateAccountsComboBoxes();
    }

    private void populateAccountsComboBoxes() {
        ObservableList<String> accountsList = DatabaseOperations.getCompanyBankAccounts();
        receivingAccountComboBox.setItems(accountsList);
        sendingAccountComboBox.setItems(accountsList);
    }

    @FXML
    void onTransferAmount() {
        String amountString = amountTextField.getText();
        String description = descriptionArea.getText();
        String sendingAccountString = sendingAccountComboBox.getValue();
        String receivingAccountString = receivingAccountComboBox.getValue();

        // Validate data
        boolean isValid = validateData(amountString, description, sendingAccountString, receivingAccountString);

        if (isValid) {
            double amount = Double.parseDouble(amountString);
            String sendingAccount = Utility.extractAccountNumber(sendingAccountString);
            String receivingAccount = Utility.extractAccountNumber(receivingAccountString);

            if (amount < 0) {
                MessageDialogs.showWarningMessage("Amount cannot be negative");
                return;
            }

            double sendingAccountBalance = DatabaseOperations.getAccountBalance(sendingAccount);
            if (sendingAccountBalance < amount) {
                // Show error message:
                MessageDialogs.showWarningMessage("Not enough balance in the sending account");
                return;
            }

            boolean isFirstSure = MessageDialogs.showConfirmationDialog("Are you sure?");
            if (isFirstSure) {
                boolean isSecondSure = MessageDialogs.showConfirmationDialog("Are you sure? " + amount + " is going to be sent from " + sendingAccount + " to " + receivingAccount);
                if (isSecondSure) {
                    boolean isTransferSuccessful = DatabaseOperations.transferAmount(sendingAccount, receivingAccount, amount, description);
                    if (isTransferSuccessful) {
                        MessageDialogs.showMessageDialog("Amount successfully sent.");
                        clearInputFields();
                    } else {
                        MessageDialogs.showErrorMessage("Amount is not sent. Try Again!");
                    }
                }
            }
        }
    }

    private void clearInputFields() {
        amountTextField.clear();
        descriptionArea.clear();
        sendingAccountComboBox.getSelectionModel().clearSelection();
        receivingAccountComboBox.getSelectionModel().clearSelection();
    }

    private boolean validateData(String amountString, String description, String sendingAccountString, String receivingAccountString) {
        if (sendingAccountString == null || sendingAccountString.isEmpty()) {
            MessageDialogs.showWarningMessage("Select the Sending account");
            return false;
        } else if (receivingAccountString == null || receivingAccountString.isEmpty()) {
            MessageDialogs.showWarningMessage("Select the Receiving account");
            return false;
        } else if (sendingAccountString.equals(receivingAccountString)) {
            MessageDialogs.showWarningMessage("Sending and receiving accounts cannot be the same.");
            return false;
        } else if (amountString == null || amountString.isEmpty()) {
            MessageDialogs.showWarningMessage("Amount cannot be null");
            return false;
        } else if (description == null || description.isEmpty()) {
            MessageDialogs.showWarningMessage("Description cannot be null");
            return false;
        }
        return true;
    }


}