package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cart {
    private final SimpleStringProperty productCode;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty brandName;
    private final SimpleDoubleProperty quantity;
    private final SimpleDoubleProperty pricePerUnit;
    private final SimpleDoubleProperty totalPrice;

    public Cart(String productCode, String productName, String brandName, double quantity, double pricePerUnit,
            double totalPrice) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.brandName = new SimpleStringProperty(brandName);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.pricePerUnit = new SimpleDoubleProperty(pricePerUnit);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
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

    public String getBrandName() {
        return brandName.get();
    }

    public void setBrandName(String brandName) {
        this.brandName.set(brandName);
    }

    public SimpleStringProperty brandNameProperty() {
        return brandName;
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit.get();
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit.set(pricePerUnit);
    }

    public SimpleDoubleProperty pricePerUnitProperty() {
        return pricePerUnit;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }
}
