package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesRecord {
    private final SimpleStringProperty product;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty accountHolder;
    private final SimpleStringProperty paymentType;
    private final SimpleStringProperty amount;

    public SalesRecord(String product, String quantity, String accountHolder, String paymentType, String amount) {
        this.product = new SimpleStringProperty(product);
        this.quantity = new SimpleStringProperty(quantity);
        this.accountHolder = new SimpleStringProperty(accountHolder);
        this.paymentType = new SimpleStringProperty(paymentType);
        this.amount = new SimpleStringProperty(amount);
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

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }
}
