package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleStringProperty;

public class OnlinePayment {
    private final SimpleStringProperty paymentMode;
    private final SimpleStringProperty accountHolder;
    private final SimpleStringProperty accountNo;
    private final SimpleStringProperty amount;

    public OnlinePayment(){
        this.paymentMode = new SimpleStringProperty();
        this.accountHolder = new SimpleStringProperty();
        this.accountNo = new SimpleStringProperty();
        this.amount = new SimpleStringProperty();
    }

    public OnlinePayment(String paymentMode, String accountHolder, String accountNo, String amount) {
        this.paymentMode = new SimpleStringProperty(paymentMode);
        this.accountHolder = new SimpleStringProperty(accountHolder);
        this.accountNo = new SimpleStringProperty(accountNo);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getPaymentMode() {
        return paymentMode.get();
    }

    public SimpleStringProperty paymentModeProperty() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode.set(paymentMode);
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

    public String getAccountNo() {
        return accountNo.get();
    }

    public SimpleStringProperty accountNoProperty() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo.set(accountNo);
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
