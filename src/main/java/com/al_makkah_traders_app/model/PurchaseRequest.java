package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PurchaseRequest extends Application {
    private SimpleIntegerProperty orderNo;
    private SimpleStringProperty orderDate;
    private SimpleStringProperty productName;
    private SimpleStringProperty brandName;
    private SimpleStringProperty companyName;
    private SimpleFloatProperty quantity;
    private SimpleFloatProperty amountPaid;
    private SimpleStringProperty status;

    public PurchaseRequest() {

    }

    public PurchaseRequest(int orderNo, String orderDate, String productName, String brandName, String companyName,
            float quantity, float amountPaid, String status) {
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.productName = new SimpleStringProperty(productName);
        this.brandName = new SimpleStringProperty(brandName);
        this.companyName = new SimpleStringProperty(companyName);
        this.quantity = new SimpleFloatProperty(quantity);
        this.amountPaid = new SimpleFloatProperty(amountPaid);
        this.status = new SimpleStringProperty(status);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int getOrderNo() {
        return orderNo.get();
    }

    public void setOrderNo(int orderNo) {
        this.orderNo.set(orderNo);
    }

    public SimpleIntegerProperty orderNoProperty() {
        return orderNo;
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
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

    public float getAmountPaid() {
        return amountPaid.get();
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public SimpleFloatProperty amountPaidProperty() {
        return amountPaid;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                PurchaseRequest.class.getResource("/com/al_makkah_traders_app/views/purchase-request-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
