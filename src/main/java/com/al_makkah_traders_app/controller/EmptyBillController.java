package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.utility.BillCreationResult;
import com.al_makkah_traders_app.utility.BillPrinter;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

import java.math.BigInteger;
import java.util.Objects;

public class EmptyBillController {
    @FXML
    private SearchableComboBox<String> paymentOptionsComboBox;
    @FXML
    private TextField netBalanceTextField, previousBalanceTextField, amountTextField;

    @FXML
    private SearchableComboBox<String> accountHolderComboBox, paymentTypeComboBox;

    @FXML
    private ComboBox<String> billTypeComboBox;

    @FXML
    private TextArea descriptionArea;

    private BigInteger holderNo;

    public void initialize() {
        previousBalanceTextField.setEditable(false);
        netBalanceTextField.setEditable(false);

        populateComboBoxes();
        setListeners();
    }

    private void populateComboBoxes() {
        accountHolderComboBox.setItems(DatabaseOperations.getAccountHolderNames());
//        paymentTypeComboBox.setItems(DatabaseOperations.getCompanyBankAccounts());
        ObservableList<String> paymentMethods = FXCollections.observableArrayList();
        paymentMethods.addAll("Cash", "Cheque", "Online");
        paymentOptionsComboBox.setItems(paymentMethods);
    }

    private void setListeners() {
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> calculateAndSetNetBalance());
        accountHolderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> populatePreviousBalance(newValue));
        paymentOptionsComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> populatePaymentMethodsComboBox(newValue)));
    }

    private void populatePaymentMethodsComboBox(String paymentOption){
        // Check if the paymentOption is null, and if so, return without further processing
        if (paymentOption == null) {
            return;
        }
        if (paymentOption.equals("Cash")){
            paymentTypeComboBox.setItems(FXCollections.observableArrayList("0000-000000 -> CASH (DAY BOOK)"));
            paymentTypeComboBox.setValue("0000-000000 -> CASH (DAY BOOK)");
//            paymentTypeComboBox.setDisable(true);
        } else {
            paymentTypeComboBox.setDisable(false);
            ObservableList<String> bankAccounts = DatabaseOperations.getCompanyBankAccounts();
            bankAccounts.removeIf(s -> Objects.equals(s, "0000-000000 -> CASH (DAY BOOK)"));
            paymentTypeComboBox.setItems(bankAccounts);
        }
    }

    private void calculateAndSetNetBalance() {
        double previousBalance = Double.parseDouble(previousBalanceTextField.getText());
        double amount = amountTextField.getText().isEmpty() || !Utility.isNumeric(amountTextField.getText()) ? 0 : Double.parseDouble(amountTextField.getText());
        double netBalance = getNetBalance(billTypeComboBox.getValue(), previousBalance, amount);
        netBalanceTextField.setText(String.valueOf(netBalance));
    }

    private static double getNetBalance(String billType, double previousBalance, double amount) {
        return "Deposit Cash".equals(billType) ? previousBalance - amount : previousBalance + amount;
    }

    private void populatePreviousBalance(String accountHolder) {
        if (accountHolder == null || accountHolder.isEmpty()) return;

        holderNo = DatabaseOperations.getWholesalerId(accountHolder);
        previousBalanceTextField.setText(String.valueOf(DatabaseOperations.getPreviousBalance(holderNo)));
        calculateAndSetNetBalance();
    }

    @FXML
    private void onPrintBill() {
        if (checkAllFieldsFilled()) return;
        if (!insertBillAndPrintReceipt()) MessageDialogs.showErrorMessage("Failed to insert bill");
    }

    @FXML
    private void onSaveBill() {
        if (checkAllFieldsFilled()) return;
        if (!insertBill()) MessageDialogs.showErrorMessage("Failed to insert bill");
    }

    private boolean checkAllFieldsFilled() {
        if (accountHolderComboBox.getValue().isEmpty() ||
                paymentTypeComboBox.getValue() == null ||
                paymentTypeComboBox.getValue().isEmpty() ||
                billTypeComboBox.getValue().isEmpty() ||
                amountTextField.getText().isEmpty() ||
                !Utility.isNumeric(amountTextField.getText()) ||
                Double.parseDouble(amountTextField.getText()) <= 0 ||
                descriptionArea == null ||
                descriptionArea.getText().isEmpty()
        ) {
            MessageDialogs.showWarningMessage("Please fill in all fields correctly");
            return true;
        }
        return false;
    }

    private boolean insertBillAndPrintReceipt() {
        String accountNo;
        String paymentOption = paymentOptionsComboBox.getValue();
        String paymentMethod = paymentTypeComboBox.getValue();
        if (paymentOption.equals("Cash")){
            accountNo = "0000-000000";
        }else{
            accountNo = Utility.extractAccountNumber(paymentMethod);
        }

        System.out.println(accountNo);

        String paymentType = "Deposit Cash".equals(billTypeComboBox.getValue()) ? "debit" : "credit";
//        accountNo = paymentMethod.endsWith("Cash") ? "0000-000000" : Utility.extractAccountNumber(paymentMethod);

        double amount = Double.parseDouble(amountTextField.getText());
        String description = descriptionArea.getText();

        String paymentStatus;
        if (paymentOptionsComboBox.getValue().equals("Cheque")) {
            paymentStatus = "Pending";
        }else{
            paymentStatus = "Completed";
        }

        BillCreationResult isInserted = DatabaseOperations.insertEmptyBill(holderNo, accountNo, amount, description, paymentType, paymentStatus);
        if (isInserted.isSuccess()) {
            MessageDialogs.showMessageDialog("Bill inserted successfully");
            printReceipt(isInserted.getBillId());
            clearInputFields();
            return true;
        } else {
            MessageDialogs.showWarningMessage("Bill Was not inserted successfully.");
        }
        return false;
    }

    private boolean insertBill() {
        String paymentMethod = paymentTypeComboBox.getValue();
        String paymentType = "Deposit Cash".equals(billTypeComboBox.getValue()) ? "credit" : "debit";
        String accountNo = paymentMethod.contains("0000-000000") ? "0000-000000" : Utility.extractAccountNumber(paymentMethod);
        System.out.println(accountNo);
        double amount = Double.parseDouble(amountTextField.getText());
        String description = descriptionArea.getText();

        String paymentStatus;
        if (paymentOptionsComboBox.getValue().equals("Cheque")) {
            paymentStatus = "Pending";
        }else{
            paymentStatus = "Completed";
        }

        BillCreationResult isInserted = DatabaseOperations.insertEmptyBill(holderNo, accountNo, amount, description, paymentType, paymentStatus);

        if (isInserted.isSuccess()) {
            MessageDialogs.showMessageDialog("Bill inserted successfully");
            clearInputFields();
            return true;
        } else {
            MessageDialogs.showWarningMessage("Bill Was not inserted successfully.");
        }
        return false;
    }

    private void printReceipt(String billId) {
        BillPrinter receiptPrinter = new BillPrinter(
                null,
                amountTextField.getText(),
                amountTextField.getText(),
                previousBalanceTextField.getText(),
                netBalanceTextField.getText(),
                accountHolderComboBox.getValue(),
                Utility.extractPaymentMode(paymentTypeComboBox.getValue()),
                false,
                false,
                true,
                descriptionArea.getText(),
                billId
        );
        receiptPrinter.printReceipt();
    }

    private void clearInputFields() {
        accountHolderComboBox.setValue(null);
        paymentTypeComboBox.setValue(null);
        billTypeComboBox.setValue(null);
        amountTextField.clear();
        descriptionArea.clear();
        previousBalanceTextField.clear();
        netBalanceTextField.clear();
    }
}
