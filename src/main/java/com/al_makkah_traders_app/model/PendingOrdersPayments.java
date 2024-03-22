package com.al_makkah_traders_app.model;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PendingOrdersPayments extends Application {
    private SimpleIntegerProperty orderNo;
    private SimpleDoubleProperty paidAmount;
    private SimpleDoubleProperty totalAmount;
    private SimpleStringProperty orderedFrom;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PendingOrdersPayments.class
                .getResource("/com/al_makkah_traders_app/views/pending-order-payments-view.fxml"));
        primaryStage.setTitle("Al-Makkah Traders");
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public PendingOrdersPayments(){

    }

    public PendingOrdersPayments(int orderNo, double paidAmount, double totalAmount, String orderedFrom) {
        this.orderNo = new SimpleIntegerProperty(orderNo);
        this.paidAmount = new SimpleDoubleProperty(paidAmount);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.orderedFrom = new SimpleStringProperty(orderedFrom);
    }


    public int getOrderNo() {
        return orderNo.get();
    }

    public SimpleIntegerProperty orderNoProperty() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo.set(orderNo);
    }

    public double getPaidAmount() {
        return paidAmount.get();
    }

    public SimpleDoubleProperty paidAmountProperty() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount.set(paidAmount);
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public SimpleDoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public String getOrderedFrom() {
        return orderedFrom.get();
    }

    public SimpleStringProperty orderedFromProperty() {
        return orderedFrom;
    }

    public void setOrderedFrom(String orderedFrom) {
        this.orderedFrom.set(orderedFrom);
    }
}
