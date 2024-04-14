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
    private SimpleStringProperty credit;
    private SimpleStringProperty debit;
    private SimpleStringProperty balance;

    public CompanyTransaction(){

    }

    public CompanyTransaction(String date, String description, String credit, String debit, String balance){
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.credit = new SimpleStringProperty(credit);
        this.debit = new SimpleStringProperty(debit);
        this.balance = new SimpleStringProperty(balance);
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

    public String getCredit() {
        return credit.get();
    }

    public SimpleStringProperty creditProperty() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit.set(credit);
    }

    public String getDebit() {
        return debit.get();
    }

    public SimpleStringProperty debitProperty() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit.set(debit);
    }

    public String getBalance() {
        return balance.get();
    }

    public SimpleStringProperty balanceProperty() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance.set(balance);
    }
}
