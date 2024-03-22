package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockTransferCart {
    private final SimpleStringProperty productCode;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty brandName;
    private final SimpleStringProperty companyName;
    private final SimpleFloatProperty quantity;

    public StockTransferCart(String productCode, String productName, String brandName, String companyName,
            float quantity) {
        this.productCode = new SimpleStringProperty(productCode);
        this.productName = new SimpleStringProperty(productName);
        this.brandName = new SimpleStringProperty(brandName);
        this.companyName = new SimpleStringProperty(companyName);
        this.quantity = new SimpleFloatProperty(quantity);
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
