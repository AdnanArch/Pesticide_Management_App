package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MiscellaneousPayments extends Application {
    private final SimpleStringProperty description;
    private final SimpleStringProperty amount;

    public MiscellaneousPayments() {
        this.description = new SimpleStringProperty("");
        this.amount = new SimpleStringProperty("0");
    }

    public MiscellaneousPayments(String description, String amount) {
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getAmount() {
        return amount.get();
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MiscellaneousPayments.class
                .getResource("/com/al_makkah_traders_app/views/miscellaneous-payments-view.fxml"));
        primaryStage.setTitle("Al-Makkah Traders");
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
