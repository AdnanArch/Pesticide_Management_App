package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.PendingCheque;
import com.al_makkah_traders_app.utility.NumberFormatter;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

/**
 * Author: Adnan Rafique
 * Date: 4/25/2024
 * Time: 12:50 AM
 * Version: 1.0
 */

public class PendingChequeController {
    ObservableList<PendingCheque> pendingCheques;

    @FXML
    private SearchableComboBox<String> chequeNoComboBox;

    @FXML
    private SearchableComboBox<String> accountNoComboBox;

    @FXML
    private TableView<PendingCheque> pendingPaymentsTableView;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField paymentTypeTextField;

    @FXML
    private TextField amountTextField;

    public void initialize() {
        populatePendingPaymentsTable();
        paymentTypeTextField.setEditable(false);
        populatePaymentTypeComboBox();

        // Add a listener to handle row selection
        pendingPaymentsTableView.setRowFactory(tv -> {
            TableRow<PendingCheque> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    PendingCheque selectedAccount = row.getItem();
                    // Fill the input fields with the selected account data
                    fillFormFields(selectedAccount);
                }
            });
            return row;
        });

        chequeNoComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Find the PendingCheque object corresponding to the selected order number
            PendingCheque selectedPendingCheque = pendingCheques.stream()
                    .filter(cheque -> cheque.getChequeNo().equals(newValue))
                    .findFirst()
                    .orElse(null);

            // If a matching PendingCheque is found, fill the form fields
            if (selectedPendingCheque != null) {
                fillFormFields(selectedPendingCheque);
            }
        });


    }

    private void populatePendingPaymentsTable() {
        pendingCheques = DatabaseOperations.getAllPendingCheques();
        pendingPaymentsTableView.setItems(pendingCheques);

        ObservableList<String> chequeNos = FXCollections.observableArrayList();
        for (PendingCheque pendingCheque : pendingCheques) {
            String chequeNo = pendingCheque.getChequeNo();
            chequeNos.add(chequeNo);
        }

        chequeNoComboBox.setItems(chequeNos);
    }

    public void fillFormFields(PendingCheque pendingCheque) {
        chequeNoComboBox.setValue(pendingCheque.getChequeNo());
        paymentTypeTextField.setText(pendingCheque.getPaymentType());
        amountTextField.setText(String.valueOf(NumberFormatter.removeCommas(pendingCheque.getAmount())));
    }


    public void populatePaymentTypeComboBox(){
        ObservableList<String> bankAccounts = DatabaseOperations.getCompanyBankAccounts();
        accountNoComboBox.setItems(bankAccounts);
    }



    @FXML
    void onRefresh(ActionEvent event) {

    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void onSubmit() {
        String accountNo = accountNoComboBox.getValue();
        String chequeNo = chequeNoComboBox.getValue();


        validateInputFields(accountNo, chequeNo);

    }

    private void validateInputFields(String accountNo, String chequeNo) {
        if (accountNo == null || accountNo.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select account number.");
        } else if (chequeNo == null || chequeNo.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select an order number.");
        } else {
            String account = Utility.extractAccountNumber(accountNo);
            String paymentType = paymentTypeTextField.getText();
            String amount = amountTextField.getText();
            
            // Proceed with the payment
            boolean paymentSuccessful = DatabaseOperations.makePayment(paymentType, chequeNo, amount, account);
            if (paymentSuccessful) {
                MessageDialogs.showMessageDialog("Payment has been made successfully!");
                populatePendingPaymentsTable();
                clearFormFields();
            } else {
                MessageDialogs.showErrorMessage("An error occurred while making the payment.");
            }
        }
    }

    private void clearFormFields() {
        chequeNoComboBox.setValue(null);
        accountNoComboBox.setValue(null);
        paymentTypeTextField.clear();
        amountTextField.clear();
    }

}
