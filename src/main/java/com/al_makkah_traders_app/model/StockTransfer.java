package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockTransfer extends Application {
    private SimpleStringProperty transferDate;
    private SimpleStringProperty fromPlace;
    private SimpleStringProperty toPlace;
    private SimpleStringProperty productName;
    private SimpleStringProperty brandName;
    private SimpleStringProperty companyName;
    private SimpleFloatProperty quantity;

    public StockTransfer() {
    }

    public StockTransfer(String transferDate, String fromPlace, String toPlace, String productName, String brandName,
            String companyName, float quantity) {
        this.transferDate = new SimpleStringProperty(transferDate);
        this.fromPlace = new SimpleStringProperty(fromPlace);
        this.toPlace = new SimpleStringProperty(toPlace);
        this.productName = new SimpleStringProperty(productName);
        this.brandName = new SimpleStringProperty(brandName);
        this.companyName = new SimpleStringProperty(companyName);
        this.quantity = new SimpleFloatProperty(quantity);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                StockTransfer.class.getResource("/com/al_makkah_traders_app/views/stock-exchange-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getTransferDate() {
        return transferDate.get();
    }

    public void setTransferDate(String transferDate) {
        this.transferDate.set(transferDate);
    }

    public SimpleStringProperty transferDateProperty() {
        return transferDate;
    }

    public String getFromPlace() {
        return fromPlace.get();
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace.set(fromPlace);
    }

    public SimpleStringProperty fromPlaceProperty() {
        return fromPlace;
    }

    public String getToPlace() {
        return toPlace.get();
    }

    public void setToPlace(String toPlace) {
        this.toPlace.set(toPlace);
    }

    public SimpleStringProperty toPlaceProperty() {
        return toPlace;
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public String getBrandName() {
        return brandName.get();
    }

    public void setBrandName(String brandName) {
        this.brandName.set(brandName);
    }

    public SimpleStringProperty brandNameProperty() {
        return brandName;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public float getQuantity() {
        return quantity.get();
    }

    public void setQuantity(float quantity) {
        this.quantity.set(quantity);
    }

    public SimpleFloatProperty quantityProperty() {
        return quantity;
    }

}
