package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Author: Adnan Rafique
 * Date: 4/25/2024
 * Time: 1:01 AM
 * Version: 1.0
 */

public class PendingCheque extends Application {
    private  SimpleStringProperty chequeNo;
    private  SimpleStringProperty from;
    private  SimpleStringProperty amount;
    private  SimpleStringProperty paymentType;

    public PendingCheque() {
    }

    public PendingCheque(String chequeNo, String from, String amount, String paymentType) {
        this.chequeNo = new SimpleStringProperty(chequeNo);
        this.from = new SimpleStringProperty(from);
        this.amount = new SimpleStringProperty(amount);
        this.paymentType = new SimpleStringProperty(paymentType);
    }


    public String getChequeNo() {
        return chequeNo.get();
    }

    public SimpleStringProperty chequeNoProperty() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo.set(chequeNo);
    }

    public String getFrom() {
        return from.get();
    }

    public SimpleStringProperty fromProperty() {
        return from;
    }

    public void setFrom(String from) {
        this.from.set(from);
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

    public String getPaymentType() {
        return paymentType.get();
    }

    public SimpleStringProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PendingOrdersPayments.class
                .getResource("/com/al_makkah_traders_app/views/pending-cheques-view.fxml"));
        primaryStage.setTitle("Al-Makkah Traders");
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
