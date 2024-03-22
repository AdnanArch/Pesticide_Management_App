package com.al_makkah_traders_app.controller;

import java.util.ArrayList;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.StockTransfer;
import com.al_makkah_traders_app.model.StockTransferCart;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

public class StockTransferController {
    @FXML
    private TableView<StockTransferCart> stockExchangeCartTableView;

    @FXML
    private TableView<StockTransfer> stockTransferTableView;

    @FXML
    private ComboBox<String> fromStockComboBox;

    @FXML
    private SearchableComboBox<String> productCodeComboBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField toStockTextField;

    public void initialize() {
        toStockTextField.setEditable(false);
        setupToStockPlaceListner();
        populateProductCodeComboBox();
        setupStockExchangeCartTableViewListner();
        populateStockTransferTableView();
    }

    private void setupToStockPlaceListner() {
        fromStockComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fromStock = fromStockComboBox.getValue();
                if (fromStock != null) {
                    populateToStockPlaceTextField(fromStock);
                }
            }
        });
    }

    private void populateToStockPlaceTextField(String fromStock) {
        if (fromStock.equals("Shop")) {
            toStockTextField.setText("Warehouse");
        } else if (fromStock.equals("Warehouse")) {
            toStockTextField.setText("Shop");
        }
    }

    private void populateProductCodeComboBox() {
        ObservableList<String> productCodeList = DatabaseOperations.getProductsCodes();
        productCodeComboBox.setItems(productCodeList);
    }

    @FXML
    void onAddToCart(ActionEvent event) {
        String productCode = productCodeComboBox.getValue();
        String quantity = quantityTextField.getText();
        ArrayList<String> productDetails = DatabaseOperations.getProductDetails(productCode);
        String productName = productDetails.get(0);
        String productBrandName = productDetails.get(1);
        String productCompanyName = productDetails.get(2);

        // check if product is already in cart
        ObservableList<StockTransferCart> cartList = stockExchangeCartTableView.getItems();
        boolean isProductAlreadyInCart = false;
        for (StockTransferCart cartItem : cartList) {
            if (cartItem.getProductCode().equals(productCode)) {
                isProductAlreadyInCart = true;
                break;
            }
        }

        if (isProductAlreadyInCart) {
            MessageDialogs.showWarningMessage("Product already in cart");
        } else {
            boolean isValidData = isValidData(productCode, quantity);
            if (isValidData) {
                StockTransferCart cartItem = new StockTransferCart(productCode, productName, productBrandName,
                        productCompanyName, Float.parseFloat(quantity));
                stockExchangeCartTableView.getItems().add(cartItem);
                // clear the fields
                productCodeComboBox.setValue(null);
                quantityTextField.setText("");
                // clear the selection
                stockExchangeCartTableView.getSelectionModel().clearSelection();
                // refresh the table view
                stockExchangeCartTableView.refresh();
            }
        }
    }

    // add listner to table view when user clicks on an item in table the product
    // code and quantity should be populated in the fields
    private void setupStockExchangeCartTableViewListner() {
        stockExchangeCartTableView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                StockTransferCart cartItem = stockExchangeCartTableView.getSelectionModel().getSelectedItem();
                if (cartItem != null) {
                    productCodeComboBox.setValue(cartItem.getProductCode());
                    quantityTextField.setText(String.valueOf(cartItem.getQuantity()));
                }
            }
        });
    }

    @FXML
    void onConfirmCart(ActionEvent event) {
        // get the data from the fields
        String fromStock = fromStockComboBox.getValue();
        String toStock = toStockTextField.getText();
        // check if the data is valid
        boolean isValidData = isValidData(fromStock);
        if (!isValidData) {
            return;
        }
        // get the cart items
        ObservableList<StockTransferCart> cartItems = stockExchangeCartTableView.getItems();
        // add the cart items to the database
        boolean isAdded = DatabaseOperations.addStockTransfer(cartItems, fromStock, toStock);

        if (isAdded) {
            // show a success message
            MessageDialogs.showMessageDialog("Stock transfered successfully.");
            // clear the fields
            fromStockComboBox.setValue(null);
            toStockTextField.setText("");
            // clear the cart
            stockExchangeCartTableView.getItems().clear();
            // refresh the table view
            stockExchangeCartTableView.refresh();
        }
    }

    private boolean isValidData(String fromStock) {
        if (fromStock == null || fromStock.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select a from stock.");
            return false;
        }
        return true;
    }

    @FXML
    void onDeleteCart(ActionEvent event) {
        // delete the selected item from table view
        StockTransferCart cartItem = stockExchangeCartTableView.getSelectionModel().getSelectedItem();
        if (cartItem == null) {
            MessageDialogs.showWarningMessage("Please select an item from the cart to delete.");
            return;
        }
        stockExchangeCartTableView.getItems().remove(cartItem);
        // refresh the table view
        stockExchangeCartTableView.refresh();
        // deselect the item
        stockExchangeCartTableView.getSelectionModel().clearSelection();
    }

    @FXML
    void onSearch(ActionEvent event) {
        String searchText = searchTextField.getText();
        if (searchText.isEmpty()) {
            populateStockTransferTableView();
        } else {
            ObservableList<StockTransfer> stockTransferList = DatabaseOperations.searchStockTransfer(searchText);
            // clear the table view
            stockTransferTableView.getItems().clear();
            stockTransferTableView.setItems(stockTransferList);
        }
    }

    @FXML
    void onUpdateCart(ActionEvent event) {
        // get selected item from table view
        StockTransferCart cartItem = stockExchangeCartTableView.getSelectionModel().getSelectedItem();
        if (cartItem == null) {
            MessageDialogs.showWarningMessage("Please select an item from the cart to update.");
            return;
        } else {
            // get product code and quantity from fields
            String productCode = productCodeComboBox.getValue();
            String quantity = quantityTextField.getText();
            // check if the data is valid
            boolean isValidData = isValidData(productCode, quantity);
            if (!isValidData) {
                return;
            }
            // update the selected item
            cartItem.setProductCode(productCode);
            cartItem.setQuantity(Float.parseFloat(quantity));
            // refresh the table view
            stockExchangeCartTableView.refresh();
            // clear the fields
            productCodeComboBox.setValue(null);
            quantityTextField.setText("");
            // deselect the item
            stockExchangeCartTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onRefresh(ActionEvent event) {
        populateStockTransferTableView();
    }

    private void populateStockTransferTableView() {
        ObservableList<StockTransfer> stockTransferList = DatabaseOperations.getStockTransferHistory();
        stockTransferTableView.setItems(stockTransferList);
    }

    private boolean isValidData(String productCode, String quantity) {
        if (productCode == null || productCode.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select a product code");
            return false;
        }
        if (quantity.isEmpty() || !Utility.isNumeric(quantity) || Float.parseFloat(quantity) <= 0) {
            MessageDialogs.showWarningMessage("Please enter valid quantity");
            return false;
        }
        return true;
    }

}
