package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CompanyAccount extends Application {
    private SimpleStringProperty accountNo;
    private SimpleStringProperty accountHolderName;
    private SimpleStringProperty bankName;
    private SimpleStringProperty totalBalance;

    public CompanyAccount() {
    }

    public CompanyAccount(String accountNo, String accountHolderName, String bankName, String totalBalance) {
        this.accountNo = new SimpleStringProperty(accountNo);
        this.accountHolderName = new SimpleStringProperty(accountHolderName);
        this.bankName = new SimpleStringProperty(bankName);
        this.totalBalance = new SimpleStringProperty(totalBalance);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AccountHolderBill.class
                .getResource("/com/al_makkah_traders_app/views/add-new-company-account-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 768);
        primaryStage.setTitle("Al-Makkah Traders!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getAccountNo() {
        return accountNo.get();
    }

    public void setAccountNo(String accountNo) {
        this.accountNo.set(accountNo);
    }

    public SimpleStringProperty accountNoProperty() {
        return accountNo;
    }

    public String getAccountHolderName() {
        return accountHolderName.get();
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName.set(accountHolderName);
    }

    public SimpleStringProperty accountHolderNameProperty() {
        return accountHolderName;
    }

    public String getBankName() {
        return bankName.get();
    }

    public void setBankName(String bankName) {
        this.bankName.set(bankName);
    }

    public SimpleStringProperty bankNameProperty() {
        return bankName;
    }

    public String getTotalBalance() {
        return totalBalance.get();
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance.set(totalBalance);
    }

    public SimpleStringProperty totalBalanceProperty() {
        return totalBalance;
    }
}
