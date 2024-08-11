package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.StockDepletion;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StockDepletionController {

    @FXML
    private TableView<StockDepletion> accountHolderTableView;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField shQuantityTextField;

    @FXML
    private TextField totalQuantityTextField;

    @FXML
    private TextField whQuantityTextField;
    private Integer billNumber;
    private String customerName;

    public void initialize() {
        totalQuantityTextField.setEditable(false);
        populateAccountHolderTableView();
        setupTableSelectionListener();
    }

    private void populateAccountHolderTableView() {
        ObservableList<StockDepletion> stockDepletionObservableList = DatabaseOperations.getStockDepletionObservableList();
        accountHolderTableView.setItems(stockDepletionObservableList);

    }
    private void setupTableSelectionListener() {
        accountHolderTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                productNameTextField.setText(newSelection.getProductName());
                totalQuantityTextField.setText(String.valueOf(newSelection.getQuantity()));
                billNumber = newSelection.getBillNumber();
                customerName = newSelection.getCustomerName();
            }
        });
    }
    @FXML
    void onAdd() {
        String productCode = Utility.extractProductCode(productNameTextField.getText());

        if (productCode.isEmpty()) {
            MessageDialogs.showMessageDialog("Please select a product");
            return;
        }

        if (shQuantityTextField.getText().isEmpty() || !Utility.isNumeric(shQuantityTextField.getText())) {
            MessageDialogs.showMessageDialog("Please enter a valid shop quantity");
            return;
        }
        if (whQuantityTextField.getText().isEmpty() || !Utility.isNumeric(whQuantityTextField.getText())) {
            MessageDialogs.showMessageDialog("Please enter a valid warehouse quantity");
            return;
        }

        float totalQuantity = Float.parseFloat(totalQuantityTextField.getText());
        float shQuantity =  Float.parseFloat(shQuantityTextField.getText());
        float whQuantity =  Float.parseFloat(whQuantityTextField.getText());

        if (totalQuantity != shQuantity + whQuantity) {
            MessageDialogs.showMessageDialog("Total quantity should be = shop quantity + warehouse quantity");
            return;
        }

        if (shQuantity < 0 || whQuantity < 0) {
            MessageDialogs.showMessageDialog("Quantity cannot be negative");
            return;
        }

        boolean isEmptyOverInvoice = DatabaseOperations.checkOverInvoiceIsEmpty();
        boolean isWalkInCustomer;
        boolean isAccountHolder;

        if (isEmptyOverInvoice) {
            if (customerName.startsWith("AH_")){
                isAccountHolder = true;
                isWalkInCustomer = false;
            }else{
                isAccountHolder = false;
                isWalkInCustomer = true;
            }
            // Remove prefix from customer name
            customerName = customerName.substring(3);
            System.out.println("Customer Name: " + customerName);
            boolean isAdded = DatabaseOperations.addStockDepletion(billNumber, productCode, shQuantity, whQuantity, isWalkInCustomer, isAccountHolder, customerName);

            if (isAdded) {
                MessageDialogs.showMessageDialog("Stock depletion added successfully");
                populateAccountHolderTableView();
                clearFields();
            } else {
                MessageDialogs.showMessageDialog("Stock depletion could not be added");
                populateAccountHolderTableView();
            }
        }else{
            MessageDialogs.showWarningMessage("Please clear the over invoice first");
        }

    }

    @FXML
    void onClear() {
        clearFields();
    }

    @FXML
    void onRefresh() {
        populateAccountHolderTableView();
    }

    @FXML
    void onSearch() {

    }

    private void clearFields() {
        productNameTextField.clear();
        shQuantityTextField.clear();
        whQuantityTextField.clear();
        totalQuantityTextField.clear();
    }

}
