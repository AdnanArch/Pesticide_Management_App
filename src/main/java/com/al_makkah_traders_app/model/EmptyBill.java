package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmptyBill extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                EmptyBill.class.getResource("/com/al_makkah_traders_app/views/empty-bill-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
