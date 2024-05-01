package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.OverInvoice;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

public class OverInvoiceController {
     @FXML
    private TableView<OverInvoice> overInvoiceTableView;

    @FXML
    private SearchableComboBox<String> productNameComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField searchTextField;

    public void initialize(){
        quantityTextField.setEditable(false);
        populateOverInvoiceTableView();
        populateProductNameTextField();
        populateTextFields();
    }

    // As user clicks on table item product name and quantity will be set in the text fields
    private void populateTextFields() {
        overInvoiceTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            productNameComboBox.setValue(newValue.getProductName());
            quantityTextField.setText(String.valueOf(newValue.getQuantity()));
        });
    }

    private void populateProductNameTextField() {
        ObservableList<String> productNames = DatabaseOperations.getOverInvoiceProductCodes();
        productNameComboBox.setItems(productNames);
    }

    private void populateOverInvoiceTableView() {
        ObservableList<OverInvoice> overInvoices = DatabaseOperations.getOverInvoices();
        overInvoiceTableView.setItems(overInvoices);
    }

    @FXML
    void onRefresh() {
        populateOverInvoiceTableView();
        searchTextField.clear();
    }

    @FXML
    void onSearch() {
        String searchQuery = searchTextField.getText();
        ObservableList<OverInvoice> overInvoices = DatabaseOperations.searchOverInvoices(searchQuery);
        overInvoiceTableView.setItems(overInvoices);
        searchTextField.clear();
    }

    @FXML
    void onSubmit() {
        String productName = productNameComboBox.getValue();
        float quantity = Float.parseFloat(quantityTextField.getText());

        // validate input
        if (productName == null || productName.isEmpty()) {
            MessageDialogs.showWarningMessage("Product Name is required");
            return;
        }
        if (quantity <= 0 || quantityTextField.getText().isEmpty() || !Utility.isNumeric(quantityTextField.getText())) {
            MessageDialogs.showWarningMessage("Quantity must be greater than 0");
            return;
        }

        // insert into database
        boolean isInserted = DatabaseOperations.insertOverInvoiceStock(productName, quantity);
        if (isInserted) {
            MessageDialogs.showMessageDialog("Over Invoice added successfully");
            populateOverInvoiceTableView();
            populateProductNameTextField();
            productNameComboBox.setValue(null);
            quantityTextField.clear();
        } else {
            MessageDialogs.showErrorMessage("Failed to add Over Invoice");
        }

    }

}
