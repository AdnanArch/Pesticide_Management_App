package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.Login;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswordController {

    @FXML
    private TextField confirmNewPasswordField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField previousPasswordTextField;

    @FXML
    void onResetPassword() {
        String previousPassword = previousPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        boolean isCorrect = validatePasswordFields(previousPassword, newPassword, confirmNewPassword);

        if (isCorrect) {
            // Update the password in the database
            boolean isSuccess = DatabaseOperations.updatePassword(newPassword);
            if (isSuccess) {
                MessageDialogs.showMessageDialog("Password reset successfully.");
                // Clear the input fields
                clearInputFields();

                Login login = new Login();
                Stage stage = new Stage();
                try {
                    // Open the login page
                    login.start(stage);

                    // Close the login page.
                    Stage forgetPasswordStage = (Stage) previousPasswordTextField.getScene().getWindow();
                    forgetPasswordStage.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                MessageDialogs.showErrorMessage("Failed to reset password. Please try again.");
            }
        }
    }

    private boolean validatePasswordFields(String previousPassword, String newPassword, String confirmNewPassword) {
        if (previousPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            MessageDialogs.showErrorMessage("Please fill in all the fields.");
            return false;
        } else if (!newPassword.equals(confirmNewPassword)) {
            MessageDialogs.showErrorMessage("New password and confirm new password do not match.");
            return false;
        } else if (!previousPassword.equals(DatabaseOperations.getSoftwarePassword())) {
            MessageDialogs.showErrorMessage("Previous password is incorrect.");
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        previousPasswordTextField.clear();
        newPasswordTextField.clear();
        confirmNewPasswordField.clear();
    }
}
