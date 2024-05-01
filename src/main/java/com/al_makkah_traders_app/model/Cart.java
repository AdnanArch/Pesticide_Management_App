package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleStringProperty;

public class Cart {
    private final SimpleStringProperty productCode;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty brandName;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty pricePerUnit;
    private final SimpleStringProperty totalPrice;

    public Cart(String productCode, String productName, String brandName, String quantity, String pricePerUnit,
            String totalPrice) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.brandName = new SimpleStringProperty(brandName);
        this.quantity = new SimpleStringProperty(quantity);
        this.pricePerUnit = new SimpleStringProperty(pricePerUnit);
        this.totalPrice = new SimpleStringProperty(totalPrice);
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

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public String getPricePerUnit() {
        return pricePerUnit.get();
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit.set(pricePerUnit);
    }

    public SimpleStringProperty pricePerUnitProperty() {
        return pricePerUnit;
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public SimpleStringProperty totalPriceProperty() {
        return totalPrice;
    }
}
