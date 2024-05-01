package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockDepletion extends Application {
    private final SimpleIntegerProperty billNumber;
    private final SimpleStringProperty date;
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty quantity;
    private final SimpleStringProperty customerName;

    public StockDepletion(String date, String productName, double quantity, String customerName, int billNumber) {
        this.date = new SimpleStringProperty(date);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.customerName = new SimpleStringProperty(customerName);
        this.billNumber = new SimpleIntegerProperty(billNumber);
    }

    public StockDepletion(){
        this.date = new SimpleStringProperty("");
        this.productName = new SimpleStringProperty("");
        this.quantity = new SimpleDoubleProperty(0.0);
        this.customerName = new SimpleStringProperty("");
        this.billNumber = new SimpleIntegerProperty(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Dashboard.class.getResource("/com/al_makkah_traders_app/views/stock-depletion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public int getBillNumber() {
        return billNumber.get();
    }

    public SimpleIntegerProperty billNumberProperty() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber.set(billNumber);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
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

    public double getQuantity() {
        return quantity.get();
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
}
