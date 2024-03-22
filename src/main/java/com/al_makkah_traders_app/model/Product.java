package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Product extends Application {

    private final SimpleStringProperty productCode;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty categoryName;
    private final SimpleStringProperty brandName;
    private final SimpleStringProperty companyName;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty warehouseQuantity;
    private final SimpleDoubleProperty shopQuantity;
    private SimpleStringProperty address;
    private SimpleStringProperty contact;

    public Product() {
        this.productCode = new SimpleStringProperty("");
        this.productName = new SimpleStringProperty("");
        this.categoryName = new SimpleStringProperty("");
        this.brandName = new SimpleStringProperty("");
        this.companyName = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.contact = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0.0);
        this.warehouseQuantity = new SimpleDoubleProperty(0.0);
        this.shopQuantity = new SimpleDoubleProperty(0.0);
    }

    public Product(String productCode, String productName, String categoryName, String brandName, String companyName,
            double price, double warehouseQuantity, double shopQuantity) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.categoryName = new SimpleStringProperty(categoryName);
        this.brandName = new SimpleStringProperty(brandName);
        this.companyName = new SimpleStringProperty(companyName);
        this.price = new SimpleDoubleProperty(price);
        this.warehouseQuantity = new SimpleDoubleProperty(warehouseQuantity);
        this.shopQuantity = new SimpleDoubleProperty(shopQuantity);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public String getProductCode() {
        return productCode.get();
    }

    public void setProductCode(String productCode) {
        this.productCode.set(productCode);
    }

    public SimpleStringProperty productCodeProperty() {
        return productCode;
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

    public String getCategoryName() {
        return categoryName.get();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public SimpleStringProperty categoryNameProperty() {
        return categoryName;
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

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public double getWarehouseQuantity() {
        return warehouseQuantity.get();
    }

    public void setWarehouseQuantity(double warehouseQuantity) {
        this.warehouseQuantity.set(warehouseQuantity);
    }

    public SimpleDoubleProperty warehouseQuantityProperty() {
        return warehouseQuantity;
    }

    public double getShopQuantity() {
        return shopQuantity.get();
    }

    public void setShopQuantity(double shopQuantity) {
        this.shopQuantity.set(shopQuantity);
    }

    public SimpleDoubleProperty shopQuantityProperty() {
        return shopQuantity;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Product.class.getResource("/com/al_makkah_traders_app/views/add-new-product-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
