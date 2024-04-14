package com.al_makkah_traders_app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Author: Adnan Rafique
 * Date: 4/14/2024
 * Time: 2:02 AM
 * Version: 1.0
 */

public class Booking {
    private final SimpleStringProperty productCode;
    private final SimpleStringProperty orderedFrom;
    private final SimpleDoubleProperty orderedQuantity;
    private final SimpleDoubleProperty leftQuantity;
    private final SimpleStringProperty bookingType;

    public Booking(String productCode, String orderedFrom, double orderedQuantity, double leftQuantity, String bookingType) {
        this.productCode = new SimpleStringProperty(productCode);
        this.orderedFrom = new SimpleStringProperty(orderedFrom);
        this.orderedQuantity = new SimpleDoubleProperty(orderedQuantity);
        this.leftQuantity = new SimpleDoubleProperty(leftQuantity);
        this.bookingType = new SimpleStringProperty(bookingType);
    }

    public String getProductCode() {
        return productCode.get();
    }

    public SimpleStringProperty productCodeProperty() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode.set(productCode);
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

    public double getOrderedQuantity() {
        return orderedQuantity.get();
    }

    public SimpleDoubleProperty orderedQuantityProperty() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(double orderedQuantity) {
        this.orderedQuantity.set(orderedQuantity);
    }

    public double getLeftQuantity() {
        return leftQuantity.get();
    }

    public SimpleDoubleProperty leftQuantityProperty() {
        return leftQuantity;
    }

    public void setLeftQuantity(double leftQuantity) {
        this.leftQuantity.set(leftQuantity);
    }

    public String getBookingType() {
        return bookingType.get();
    }

    public SimpleStringProperty bookingTypeProperty() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType.set(bookingType);
    }
}
