package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.math3.optimization.linear.SimplexSolver;

public class DebitCredit extends Application {
    private final SimpleStringProperty date;
    private final SimpleStringProperty debit;
    private final SimpleStringProperty credit;
    private final SimpleStringProperty balance;
    private final SimpleStringProperty description;

    public DebitCredit(String date, String debit, String credit, String balance, String description) {
        this.date = new SimpleStringProperty(date);
        this.debit = new SimpleStringProperty(debit);
        this.credit = new SimpleStringProperty(credit);
        this.balance = new SimpleStringProperty(balance);
        this.description = new SimpleStringProperty(description);
    }

    public DebitCredit(){
        this.date = new SimpleStringProperty();
        this.debit = new SimpleStringProperty();
        this.credit = new SimpleStringProperty();
        this.balance = new SimpleStringProperty();
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

    public String getDebit() {
        return debit.get();
    }

    public SimpleStringProperty debitProperty() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit.set(debit);
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

    public String getBalance() {
        return balance.get();
    }

    public SimpleStringProperty balanceProperty() {
        return balance;
    }

    public void setBalance(String balance) {
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
