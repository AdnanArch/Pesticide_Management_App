package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesRecord {
    private final SimpleStringProperty product;
    private final SimpleFloatProperty quantity;
    private final SimpleStringProperty accountHolder;
    private final SimpleStringProperty paymentType;
    private final SimpleDoubleProperty amount;

    public SalesRecord(String product, float quantity, String accountHolder, String paymentType,
            double amount) {
        this.product = new SimpleStringProperty(product);
        this.quantity = new SimpleFloatProperty(quantity);
        this.accountHolder = new SimpleStringProperty(accountHolder);
        this.paymentType = new SimpleStringProperty(paymentType);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public String getProduct() {
        return product.get();
    }

    public SimpleStringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
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

    public String getAccountHolder() {
        return accountHolder.get();
    }

    public SimpleStringProperty accountHolderProperty() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder.set(accountHolder);
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public SimpleStringProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}
