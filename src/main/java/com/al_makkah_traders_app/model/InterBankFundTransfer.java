package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Author: Adnan Rafique
 * Date: 6/15/2024
 * Time: 10:31 AM
 * Version: 1.0
 */

public class InterBankFundTransfer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                InterBankFundTransfer.class.getResource("/com/al_makkah_traders_app/views/inter-bank-fund-transfer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
