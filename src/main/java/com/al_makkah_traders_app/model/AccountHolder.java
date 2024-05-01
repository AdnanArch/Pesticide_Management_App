package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountHolder extends Application {
    private final SimpleStringProperty name;
    private final SimpleStringProperty cnicNo;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phone;
    private final SimpleBooleanProperty isRetailer;
    private SimpleStringProperty debitOrCredit;
    private final SimpleStringProperty totalBalance;

    public AccountHolder() {
        this.name = new SimpleStringProperty("");
        this.cnicNo = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.isRetailer = new SimpleBooleanProperty(false);
        this.totalBalance = new SimpleStringProperty("");
    }

    public AccountHolder(String name, String cnicNo, String address, String phone, String debitOrCredit,
            String totalBalance, boolean isRetailer) {
        this.name = new SimpleStringProperty(name);
        this.cnicNo = new SimpleStringProperty(cnicNo);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.debitOrCredit = new SimpleStringProperty(debitOrCredit);
        this.totalBalance = new SimpleStringProperty(totalBalance);
        this.isRetailer = new SimpleBooleanProperty(isRetailer);
    }

    public AccountHolder(String name, String debitOrCredit, String totalBalance, boolean isRetailer) {
        this.name = new SimpleStringProperty(name);
        this.debitOrCredit = new SimpleStringProperty(debitOrCredit);
        this.totalBalance = new SimpleStringProperty(totalBalance);
        this.cnicNo = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.isRetailer = new SimpleBooleanProperty(isRetailer);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                AccountHolder.class.getResource("/com/al_makkah_traders_app/views/add-new-account-holder-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 850);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Al-Makkah Traders");
        primaryStage.show();
    }

    public String getCnicNo() {
        return cnicNo.get();
    }

    public void setCnicNo(String cnicNo) {
        this.cnicNo.set(cnicNo);
    }

    public SimpleStringProperty cnicNoProperty() {
        return cnicNo;
    }

    public boolean isRetailer() {
        return isRetailer.get();
    }

    public SimpleBooleanProperty isRetailerProperty() {
        return isRetailer;
    }

    public void setisRetailer(boolean isRetailer) {
        this.isRetailer.set(isRetailer);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public String getDebitOrCredit() {
        return debitOrCredit.get();
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit.set(debitOrCredit);
    }

    public SimpleStringProperty debitOrCreditProperty() {
        return debitOrCredit;
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
