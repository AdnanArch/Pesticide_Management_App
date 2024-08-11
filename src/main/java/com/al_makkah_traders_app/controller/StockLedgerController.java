package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.DebitCredit;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.controlsfx.control.SearchableComboBox;

/**
 * Author: Adnan Rafique
 * Date: 8/11/2024
 * Time: 11:54 AM
 * Version: 1.0
 */

public class StockLedgerController {
    @FXML
    private SearchableComboBox<String> placeComboBox;

    @FXML
    private TableView<DebitCredit> debitCreditTableView;

    @FXML
    private SearchableComboBox<String> productSearchComboBox;

    public void initialize(){
        placeComboBox.getItems().addAll("Warehouse", "Shop");

        populateProductComboBox();
    }

    private void populateProductComboBox(){
        ObservableList<String> productList = DatabaseOperations.getProductNames();
        productSearchComboBox.setItems(productList);
    }

    @FXML
    void onRefresh() {
        // clear place selection and product selection and clear the table
        placeComboBox.getSelectionModel().clearSelection();
        productSearchComboBox.getSelectionModel().clearSelection();
        debitCreditTableView.getItems().clear();
    }

    @FXML
    void getStockReportOfProduct() {
        String place = placeComboBox.getSelectionModel().getSelectedItem();
        String product = productSearchComboBox.getSelectionModel().getSelectedItem();

        if(place == null ){
            MessageDialogs.showWarningMessage("Please select Place for Stock Report.");
            return;
        }
        if (product == null){
            MessageDialogs.showWarningMessage("Please select Product for Stock Report.");
            return;
        }

        place = place.equals("Warehouse") ? "w" : "s";

        ObservableList<DebitCredit> debitCreditList = DatabaseOperations.getStockReport(product, place);
        debitCreditTableView.setItems(debitCreditList);
    }
}
