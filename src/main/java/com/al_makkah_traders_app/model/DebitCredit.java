package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DebitCredit extends Application {
    private final SimpleStringProperty date;
    private final SimpleDoubleProperty debit;
    private final SimpleDoubleProperty credit;
    private final SimpleDoubleProperty balance;
    private final SimpleStringProperty description;

    public DebitCredit(String date, double debit, double credit, double balance, String description) {
        this.date = new SimpleStringProperty(date);
        this.debit = new SimpleDoubleProperty(debit);
        this.credit = new SimpleDoubleProperty(credit);
        this.balance = new SimpleDoubleProperty(balance);
        this.description = new SimpleStringProperty(description);
    }

    public DebitCredit(){
        this.date = new SimpleStringProperty();
        this.debit = new SimpleDoubleProperty();
        this.credit = new SimpleDoubleProperty();
        this.balance = new SimpleDoubleProperty();
        this.description = new SimpleStringProperty();
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getDebit() {
        return debit.get();
    }

    public SimpleDoubleProperty debitProperty() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit.set(debit);
    }

    public double getCredit() {
        return credit.get();
    }

    public SimpleDoubleProperty creditProperty() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit.set(credit);
    }

    public double getBalance() {
        return balance.get();
    }

    public SimpleDoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Dashboard.class.getResource("/com/al_makkah_traders_app/views/holder-debit-credit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
