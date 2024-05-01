package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MiscellaneousPaymentsController {
    @FXML
    private TextField amountTextField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    void onSubmit(ActionEvent event) {
        String amountText = amountTextField.getText();
        String description = descriptionArea.getText();

        // check if amount is valid
        boolean validAmount = validateAmount(amountText);
        boolean validDescription = validateDescription(description);

        if (validAmount && validDescription) {
            boolean isAdded = DatabaseOperations.addPettyCashRecord(amountText, description);
            if (isAdded) {
                MessageDialogs.showMessageDialog("Petty Cash Record Added Successfully");
                amountTextField.setText("");
                descriptionArea.setText("");
            } else {
                MessageDialogs.showMessageDialog("Failed to add Petty Cash Record. Try Again!");
            }
        }

    }

    private boolean validateAmount(String amountText) {
        if (Utility.isNumeric(amountText)) {
            float amount = Float.parseFloat(amountText);
            if (amount > 0) {
                return true;
            }
        }
        MessageDialogs.showWarningMessage("Invalid Amount");
        return false;
    }

    private boolean validateDescription(String description) {
        if (!description.isEmpty() && description != null) {
            return true;
        }
        MessageDialogs.showWarningMessage("Invalid Description");
        return false;
    }

}
