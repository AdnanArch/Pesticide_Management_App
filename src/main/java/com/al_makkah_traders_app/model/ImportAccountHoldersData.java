package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ImportAccountHoldersData extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                EmptyBill.class.getResource("/com/al_makkah_traders_app/views/import-account-holders-data-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
