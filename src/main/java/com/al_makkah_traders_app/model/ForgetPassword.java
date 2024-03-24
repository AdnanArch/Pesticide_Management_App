package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPassword extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Login.class.getResource("/com/al_makkah_traders_app/views/forget-password-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 350);
        stage.setTitle("Al-Makkah Traders");
        stage.setScene(scene);

        stage.show();
    }
}
