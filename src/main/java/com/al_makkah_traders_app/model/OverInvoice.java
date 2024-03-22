package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OverInvoice extends Application {
    private SimpleStringProperty productName;
    private SimpleFloatProperty quantity;

    public OverInvoice() {
    }

    public OverInvoice(String productName, float quantity) {
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleFloatProperty(quantity);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                OverInvoice.class.getResource("/com/al_makkah_traders_app/views/over-stock-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 800);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public float getQuantity() {
        return quantity.get();
    }

    public SimpleFloatProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity.set(quantity);
    }
}
