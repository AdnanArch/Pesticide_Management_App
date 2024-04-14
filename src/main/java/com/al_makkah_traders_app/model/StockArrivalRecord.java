package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StockArrivalRecord {
    private final SimpleStringProperty productName;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty from;

    public StockArrivalRecord(String productName, String quantity, String from) {
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleStringProperty(quantity);
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

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
