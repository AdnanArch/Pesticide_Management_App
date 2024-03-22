package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockArrival extends Application {
    private SimpleIntegerProperty orderNo;
    private SimpleStringProperty arrivalDate;
    private SimpleStringProperty productName;
    private SimpleStringProperty brandName;
    private SimpleStringProperty companyName;
    private SimpleFloatProperty quantity;
    private SimpleFloatProperty warehouseQuantity;
    private SimpleFloatProperty shopQuantity;
    private SimpleStringProperty vehicleNo;
    private SimpleStringProperty driverNo;
    private SimpleStringProperty orderedFrom;

    public StockArrival() {
    }

    public StockArrival(
            int orderNo,
            String arrivalDate,
            String productName,
            String brandName,
            String companyName,
            float quantity,
            float warehouseQuantity,
            float shopQuantity,
            String vehicleNo,
            String driverNo,
            String orderedFrom) {
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.arrivalDate = new SimpleStringProperty(arrivalDate);
        this.productName = new SimpleStringProperty(productName);
        this.brandName = new SimpleStringProperty(brandName);
        this.companyName = new SimpleStringProperty(companyName);
        this.quantity = new SimpleFloatProperty(quantity);
        this.warehouseQuantity = new SimpleFloatProperty(warehouseQuantity);
        this.shopQuantity = new SimpleFloatProperty(shopQuantity);
        this.vehicleNo = new SimpleStringProperty(vehicleNo);
        this.driverNo = new SimpleStringProperty(driverNo);
        this.orderedFrom = new SimpleStringProperty(orderedFrom);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                StockArrival.class.getResource("/com/al_makkah_traders_app/views/stock-arrival-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public String getArrivalDate() {
        return arrivalDate.get();
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate.set(arrivalDate);
    }

    public SimpleStringProperty arrivalDateProperty() {
        return arrivalDate;
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

    public float getWarehouseQuantity() {
        return warehouseQuantity.get();
    }

    public void setWarehouseQuantity(float warehouseQuantity) {
        this.warehouseQuantity.set(warehouseQuantity);
    }

    public SimpleFloatProperty warehouseQuantityProperty() {
        return warehouseQuantity;
    }

    public float getShopQuantity() {
        return shopQuantity.get();
    }

    public void setShopQuantity(float shopQuantity) {
        this.shopQuantity.set(shopQuantity);
    }

    public SimpleFloatProperty shopQuantityProperty() {
        return shopQuantity;
    }

    public String getVehicleNo() {
        return vehicleNo.get();
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo.set(vehicleNo);
    }

    public SimpleStringProperty vehicleNoProperty() {
        return vehicleNo;
    }

    public String getDriverNo() {
        return driverNo.get();
    }

    public void setDriverNo(String driverNo) {
        this.driverNo.set(driverNo);
    }

    public SimpleStringProperty driverNoProperty() {
        return driverNo;
    }

    public String getOrderedFrom() {
        return orderedFrom.get();
    }

    public void setOrderedFrom(String orderedFrom) {
        this.orderedFrom.set(orderedFrom);
    }

    public SimpleStringProperty orderedFromProperty() {
        return orderedFrom;
    }

}
