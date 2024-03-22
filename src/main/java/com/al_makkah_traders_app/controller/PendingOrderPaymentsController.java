package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.PendingOrdersPayments;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

public class PendingOrderPaymentsController {
    @FXML
    private TextField amountTextField;

    @FXML
    private SearchableComboBox<Integer> orderNoComboBox;

    @FXML
    private SearchableComboBox<String> paymentTypeComboBox;

    @FXML
    private TableView<PendingOrdersPayments> pendingPaymentsTableView;

    @FXML
    private TextField searchTextField;

    public void initialize(){
        populateOrderNoComboBox();
        populatePendingPaymentsTableView();
        populatePaymentTypeComboBox();
    }

    private void populateOrderNoComboBox() {
        ObservableList<Integer> orderNoList = DatabaseOperations.getAllOrderNo();
        orderNoComboBox.setItems(orderNoList);
    }

    private void populatePendingPaymentsTableView() {
        ObservableList<PendingOrdersPayments> pendingOrdersPayments = DatabaseOperations.getAllPendingPayments();
        pendingPaymentsTableView.setItems(pendingOrdersPayments);
    }

    private void populatePaymentTypeComboBox() {
        ObservableList<String> paymentTypeList = DatabaseOperations.getCompanyBankAccounts();
        paymentTypeComboBox.setItems(paymentTypeList);
    }

    @FXML
    void onRefresh() {
        populatePendingPaymentsTableView();
        clearForm();
        searchTextField.clear();
    }

    @FXML
    void onSearch() {
        String searchQuery = searchTextField.getText();
        ObservableList<PendingOrdersPayments> pendingOrdersPayments = DatabaseOperations.searchPendingPayments(searchQuery);
        pendingPaymentsTableView.setItems(pendingOrdersPayments);
        searchTextField.clear();
    }

    @FXML
    void onSubmit() {
        String paymentType = paymentTypeComboBox.getValue();
        int orderNo = orderNoComboBox.getValue();
        double amount = Double.parseDouble(amountTextField.getText());

        boolean isValid = validateInput(paymentType, orderNo, amount);
        String accountNo;
        if (paymentType.endsWith("Cash")){
            accountNo = "0000-000000";
        } else {
            accountNo = Utility.extractAccountNumber(paymentType);
        }

        if (isValid){
            boolean result = DatabaseOperations.addPendingOrderPayment(accountNo, orderNo, amount);

            if (result){
                MessageDialogs.showMessageDialog("Payment added successfully.");
                clearForm();
                populateOrderNoComboBox();
            } else {
                MessageDialogs.showErrorMessage("Failed to add payment.");
            }

        }
        populatePendingPaymentsTableView();
    }

    private boolean validateInput(String paymentType, int orderNo, double amount) {
        if (paymentType == null || paymentType.isEmpty()) {
            MessageDialogs.showWarningMessage("Payment Type is required");
            return false;
        }

        if (orderNo == 0 || orderNo < 0 || !Utility.isNumeric(String.valueOf(orderNo))) {
            MessageDialogs.showWarningMessage("Order No is required");
            return false;
        }

        if (amount == 0 || amount < 0 || !Utility.isNumeric(amountTextField.getText())){
            MessageDialogs.showWarningMessage("Please enter valid amount.");
            return false;
        }

        return true;
    }

    private void clearForm() {
        paymentTypeComboBox.setValue(null);
        orderNoComboBox.setValue(null);
        amountTextField.clear();
    }
}
