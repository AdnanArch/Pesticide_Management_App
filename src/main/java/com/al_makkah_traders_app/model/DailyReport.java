package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DailyReport extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AccountHolderBill.class
                .getResource("/com/al_makkah_traders_app/views/daily-report.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
