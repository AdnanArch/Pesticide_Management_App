package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CompanyTransaction extends Application {
    private SimpleStringProperty date;
    private SimpleStringProperty description;
    private SimpleDoubleProperty credit;
    private SimpleDoubleProperty debit;
    private SimpleDoubleProperty balance;

    public CompanyTransaction(){

    }

    public CompanyTransaction(String date, String description, double credit, double debit, double balance){
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.credit = new SimpleDoubleProperty(credit);
        this.debit = new SimpleDoubleProperty(debit);
        this.balance = new SimpleDoubleProperty(balance);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AccountHolderBill.class
                .getResource("/com/al_makkah_traders_app/views/company-transactions-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setTitle("Al-Makkah Traders!");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public double getDebit() {
        return debit.get();
    }

    public SimpleDoubleProperty debitProperty() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit.set(debit);
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
}
