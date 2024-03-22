package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockArrivalRecord {
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty quantity;
    private final SimpleStringProperty from;

    public StockArrivalRecord(String productName, double quantity, String from) {
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.from = new SimpleStringProperty(from);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
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
}
