package com.al_makkah_traders_app.controller;

import org.controlsfx.control.SearchableComboBox;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.StockArrival;
import com.al_makkah_traders_app.utility.Utility;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StockArrivalController {
    @FXML
    private TextField arrivedQuantityTextField;

    @FXML
    private TextField driverNoTextField;

    @FXML
    private TextField leftQuantityTextField;

    @FXML
    private SearchableComboBox<String> orderNoComboBox;

    @FXML
    private SearchableComboBox<String> productCodeComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField shQuantityTextField;

    @FXML
    private TableView<StockArrival> stockArrivalTableView;

    @FXML
    private TextField vehicleNoTextField;

    @FXML
    private TextField whQuantityTextField;

    /**
     * Initializes the StockArrivalController by populating the order number combo
     * box
     * and setting up the necessary event listeners.
     */
    public void initialize() {
        populateOrderNoComboBox();
        setupOrderNoComboBoxListener();
        setupOrderedQuantityListener();

        leftQuantityTextField.setEditable(false);
        populateStockArrivalTableView();
    }

    private void populateStockArrivalTableView() {
        ObservableList<StockArrival> stockArrivalList = DatabaseOperations.getStockArrivalHistory();
        stockArrivalTableView.setItems(stockArrivalList);
    }

    @FXML
    void onRefresh(ActionEvent event) {
        populateStockArrivalTableView();
        searchTextField.clear();
    }

    @FXML
    void onSearch(ActionEvent event) {
        String searchText = searchTextField.getText();
        if (searchText == null || searchText.isEmpty()) {
            MessageDialogs.showWarningMessage("Enter a search query");
            return;
        }
        ObservableList<StockArrival> stockArrivalList = DatabaseOperations.searchStockArrivalHistory(searchText);
        stockArrivalTableView.setItems(stockArrivalList);
        searchTextField.clear();
    }

    /**
     * Populates the order number combo box with the list of available order numbers
     * retrieved from the database.
     */
    private void populateOrderNoComboBox() {
        ObservableList<String> orderNoList = DatabaseOperations.getOrderNoList();
        orderNoComboBox.setItems(orderNoList);
    }

    /**
     * Sets up an event listener for the order number combo box to trigger actions
     * when a specific order number is selected.
     */
    private void setupOrderNoComboBoxListener() {
        orderNoComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedOrderNo = orderNoComboBox.getValue();
                if (selectedOrderNo != null) {
                    populateProductCodeComboBox(selectedOrderNo);
                }
            }
        });
    }

    /**
     * Populates the product code combo box based on the selected order number.
     * Retrieves the list of product codes from the database and displays them
     * in the combo box.
     *
     * @param selectedOrderNo The selected order number for which product codes
     *                        are to be retrieved.
     */
    private void populateProductCodeComboBox(String selectedOrderNo) {
        int orderNo = Integer.parseInt(selectedOrderNo);
        ObservableList<String> productCodeList = DatabaseOperations.getProductCodeList(orderNo);

        if (productCodeList.isEmpty()) {
            MessageDialogs.showWarningMessage("No Product Codes found for the selected Order Number");
        } else {
            productCodeComboBox.setItems(productCodeList);
        }
    }

    private void setupOrderedQuantityListener() {
        productCodeComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                populateLeftQuantityTextField();
            }
        });
    }

    private void populateLeftQuantityTextField() {
        String selectedOrderNo = orderNoComboBox.getValue();
        String productCode = productCodeComboBox.getValue();

        float orderedQuantity = 0;

        if (selectedOrderNo != null && productCode != null) {
            int orderNo = Integer.parseInt(selectedOrderNo);
            orderedQuantity = DatabaseOperations.getOrderedQuantity(orderNo, productCode);
            leftQuantityTextField.setText(String.valueOf(orderedQuantity));
        }
    }

    @FXML
    void onConfirm() {
        // check if all fields are filled and valid
        if (isInputValid() && validQuantities()) {
            // get the values from the text fields
            String orderNo = orderNoComboBox.getValue();
            String productCode = productCodeComboBox.getValue();
            String shQuantity = shQuantityTextField.getText();
            String whQuantity = whQuantityTextField.getText();
            String vehicleNo = vehicleNoTextField.getText();
            String driverNo = driverNoTextField.getText();

            // insert the values into the database
            boolean stockAdded = DatabaseOperations.insertStockArrival(orderNo, productCode, shQuantity, whQuantity,
                    vehicleNo, driverNo);

            if (stockAdded) {
                // clear the text fields
                clearTextFields();

                // show success message
                MessageDialogs.showMessageDialog("Stock Arrival details added successfully");
                // refresh the order number combo box
                populateOrderNoComboBox();
                populateStockArrivalTableView();
            } else {
                MessageDialogs.showErrorMessage("Stock Arrival details could not be added");
            }

        }

    }

    private void clearTextFields() {
        orderNoComboBox.getSelectionModel().clearSelection();
        productCodeComboBox.getSelectionModel().clearSelection();
        shQuantityTextField.clear();
        whQuantityTextField.clear();
        arrivedQuantityTextField.clear();
        vehicleNoTextField.clear();
        driverNoTextField.clear();
        leftQuantityTextField.clear();
    }

    /**
     * Checks if all the input fields are filled and valid.
     *
     * @return true if all the input fields are filled and valid, false otherwise.
     */
    private boolean isInputValid() {
        String orderNo = orderNoComboBox.getValue();
        String productCode = productCodeComboBox.getValue();
        String shQuantity = shQuantityTextField.getText();
        String whQuantity = whQuantityTextField.getText();
        String arrivedQuantity = arrivedQuantityTextField.getText();

        if (orderNo == null || orderNo.isEmpty()) {
            MessageDialogs.showWarningMessage("Order Number cannot be empty");
            return false;
        }

        if (productCode == null || productCode.isEmpty()) {
            MessageDialogs.showWarningMessage("Product Code cannot be empty");
            return false;
        }

        if (shQuantity == null || shQuantity.isEmpty() || !Utility.isNumeric(shQuantity)) {
            MessageDialogs.showWarningMessage("Enter valid Shop quantity");
            return false;
        }

        if (whQuantity == null || whQuantity.isEmpty() || !Utility.isNumeric(whQuantity)) {
            MessageDialogs.showWarningMessage("Enter valid Warehouse quantity");
            return false;
        }

        if (arrivedQuantity == null || arrivedQuantity.isEmpty() || !Utility.isNumeric(arrivedQuantity)) {
            MessageDialogs.showWarningMessage("Enter valid Arrived quantity");
            return false;
        }

        return true;
    }

    private boolean validQuantities() {
        String selectedShopQuantity = shQuantityTextField.getText();
        String selectedWarehouseQuantity = whQuantityTextField.getText();
        String selectedArrivedQuantity = arrivedQuantityTextField.getText();
        boolean result = true;

        // convert the string values to double
        double shQuantity = Double.parseDouble(selectedShopQuantity);
        double whQuantity = Double.parseDouble(selectedWarehouseQuantity);
        double arrivedQuantity = Double.parseDouble(selectedArrivedQuantity);

        if (shQuantity + whQuantity != arrivedQuantity) {
            MessageDialogs
                    .showWarningMessage("The sum of SH Quantity and WH Quantity must be equal to Arrived Quantity");
            result = false;
        }
        if (arrivedQuantity > Double.parseDouble(leftQuantityTextField.getText())) {
            MessageDialogs.showWarningMessage("Arrived Quantity cannot be greater than Ordered Quantity");
            result = false;
        }
        return result;
    }
}
