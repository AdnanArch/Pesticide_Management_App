package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.database.RestoreDatabase;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameTextField;

    @FXML
    void onPasswordFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Execute the login function when Enter key is pressed in the password field
            onLoginButtonClick();
        }
    }

    @FXML
    void onLoginButtonClick() {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter your username.");
        } else if (password.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter your password.");
        } else {
            boolean result = DatabaseOperations.checkAdminLogin(username, password);

            if (result) {
                // Open the dashboard
                openDashboard();
            } else {
                MessageDialogs.showErrorMessage("Incorrect username or password.");
                logger.warn("Login attempt failed for user: {}", username);
            }
        }
    }

    private void openDashboard() {
        Dashboard dashboard = new Dashboard();
        Stage stage = new Stage();
        try {
            // Open the dashboard
            dashboard.start(stage);

            // Close the login page.
            Stage loginStage = (Stage) usernameTextField.getScene().getWindow();
            loginStage.close();
        } catch (Exception e) {
            MessageDialogs.showErrorMessage("An error occurred while opening the dashboard.");
            logger.error("Error opening the dashboard: {}", e.getMessage(), e);
        }
    }

    @FXML
    void onRestoreDatabase(){
        RestoreDatabase restoreDatabase = new RestoreDatabase();
        Stage stage = new Stage();
        try {
            restoreDatabase.start(stage);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
