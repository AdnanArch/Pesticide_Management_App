package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.Cart;
import com.al_makkah_traders_app.utility.CartUtility;
import com.al_makkah_traders_app.utility.NumberFormatter;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Author: Adnan Rafique
 * Date: 7/30/2024
 * Time: 1:19 PM
 * Version: 1.0
 */

public class StockReturnController {
    @FXML
    private SearchableComboBox<String> customerTypeComboBox;

    @FXML
    private TextField brandNameTextField;

    @FXML
    private TableView<Cart> cartTableView;

    @FXML
    private TextField netBalanceTextField;

    @FXML
    private ComboBox<String> paymentTypeComboBox;

    @FXML
    private TextField previousBalanceTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private SearchableComboBox<String> accountHolderComboBox;

    @FXML
    private SearchableComboBox<String> productNameSearchableComboBox;

    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField productCodeTextField;

    @FXML
    private TextField totalBillTextField;
    private ObservableList<Cart> cartItems;
    private static BigInteger  holderNo;

    public void initialize() {
        // populate productNameSearchableComboBox
        populateProductNameSearchableComboBox();

        // add customer types to customerTypeComboBox 1- Walk-in Customer 2- Account Holder
        customerTypeComboBox.getItems().addAll("Walk-in Customer", "Account Holder");

        paymentTypeComboBox.setItems(DatabaseOperations.getCompanyBankAccounts());

        // disable brandNameTextField, totalBillTextField, netBalanceTextField, previousBalanceTextField
        brandNameTextField.setDisable(true);
        totalBillTextField.setDisable(true);
        netBalanceTextField.setDisable(true);
        previousBalanceTextField.setDisable(true);
        productCodeTextField.setDisable(true);

        addCartTableSelectionListener(cartTableView);

        cartItems = FXCollections.observableArrayList();

        // add listener to customerTypeComboBox if Walk-in Customer selected disable accountHolderComboBox otherwise populate it.
        customerTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Walk-in Customer")) {
                    accountHolderComboBox.setDisable(true);
                    paymentTypeComboBox.setDisable(false);
                } else {
                    accountHolderComboBox.setDisable(false);
                    paymentTypeComboBox.setDisable(true);
                    populateAccountHolderComboBox();
                }
            }
        });

        // add listener to productNameSearchableComboBox to populate brandNameTextField and priceTextField
        productNameSearchableComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        getProductDetails(newValue);
                    }
                });

        accountHolderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                populatePreviousBalanceTextField(newValue);
            }
        });

        totalBillTextField.textProperty().addListener((observable, oldValue, newValue) -> calculateAndSetNetBalance());
    }

    private void populatePreviousBalanceTextField(String accountHolder) {
        if (accountHolder == null || accountHolder.isEmpty()) {
            return;
        }

        holderNo = DatabaseOperations.getWholesalerId(accountHolder);
        double previousBalance = DatabaseOperations.getPreviousBalance(holderNo);
        previousBalanceTextField.setText(String.valueOf(previousBalance));

        // Calculate and set the initial net balance
        calculateAndSetNetBalance();
    }

    private void calculateAndSetNetBalance() {
        // Get the previous balance
        double previousBalance = Double.parseDouble(previousBalanceTextField.getText());

        // Get the entered amount
        double totalBill = 0;

        String totalAmountString = totalBillTextField.getText();

        if (!totalAmountString.isEmpty() && Utility.isNumeric(totalAmountString)) {
            totalBill = Double.parseDouble(totalBillTextField.getText());
        }

        // Set the net balance to the text field
        netBalanceTextField.setText(String.valueOf(previousBalance - totalBill));
    }

    private void addCartTableSelectionListener(TableView<Cart> cartTableView) {
        cartTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        fillFormFields(newValue);
                    }
                });
    }

    private void fillFormFields(Cart cartItem) {
        productNameSearchableComboBox.setValue(cartItem.getProductName());
        productCodeTextField.setText(cartItem.getProductCode());
        brandNameTextField.setText(cartItem.getBrandName());
        priceTextField.setText(String.valueOf(NumberFormatter.removeCommas(cartItem.getPricePerUnit())));
        quantityTextField.setText(String.valueOf(cartItem.getQuantity()));
    }

    private void populateProductNameSearchableComboBox() {
        ObservableList<String> productNames = DatabaseOperations.getProductNames();
        productNameSearchableComboBox.setItems(productNames);
    }

    private void populateAccountHolderComboBox() {
        ObservableList<String> accountHolders = DatabaseOperations.getAccountHolderNames();
        accountHolderComboBox.setItems(accountHolders);
    }

    private void getProductDetails(String productName) {
        ArrayList<String> productDetails = DatabaseOperations.getProductDetails(productName);
        productCodeTextField.setText(productDetails.get(0));
        brandNameTextField.setText(productDetails.get(1));
        priceTextField.setText(productDetails.get(3));
    }

    @FXML
    void onAddCartItem() {
        String productName = productNameSearchableComboBox.getValue();
        String productCode = productCodeTextField.getText();
        String brandName = brandNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();

        if (customerTypeComboBox.getValue() == null || customerTypeComboBox.getValue().isEmpty()) {
            MessageDialogs.showWarningMessage("Please select a customer type.");
            return;
        }

        if (accountHolderComboBox.getValue() == null && customerTypeComboBox.getValue().equals("Account Holder")) {
            MessageDialogs.showWarningMessage("Please select an account holder.");
            return;
        }

        if (productName == null) {
            MessageDialogs.showWarningMessage("Please select a product to add to the cart.");
            return;
        }

        // Add the new item to the cart
        boolean itemAdded = CartUtility.addCartItem(cartItems, productCode, productName, brandName, quantity, price,
                false);

        if (itemAdded) {
            // Add the cartItems to the cartTableView
            cartTableView.setItems(cartItems);
            // Update the total bill
            updateTotalBill();
            // Clear the form fields and reset the productCodeComboBox
            clearInputFields();
            // Refresh the cart table view
            cartTableView.refresh();
        }
        // Deselect the item
        cartTableView.getSelectionModel().clearSelection();
    }

    @FXML
    void onDeleteCartItem() {
        Cart selectedCartItem = cartTableView.getSelectionModel().getSelectedItem();
        boolean deleted = CartUtility.deleteCartItem(cartItems, selectedCartItem);
        if (deleted) {
            // Update the total bill
            updateTotalBill();
            // Refresh the cart table view
            cartTableView.refresh();
            // Deselect the item
            cartTableView.getSelectionModel().clearSelection();
            // Clear the form fields and reset the productCodeComboBox
            clearInputFields();
        }
    }

    @FXML
    void onPurchaseReturn() {
        String customerType = customerTypeComboBox.getValue();
        String paymentType = paymentTypeComboBox.getValue();
        String accountHolder = accountHolderComboBox.getValue();
        String totalBill = totalBillTextField.getText();
        String netBalance = netBalanceTextField.getText();

        if (customerType == null || customerType.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select a customer type.");
            return;
        }

        if (customerType.equals("Account Holder") && accountHolder == null) {
            MessageDialogs.showWarningMessage("Please select an account holder.");
            return;
        }

        if (cartItems.isEmpty()) {
            MessageDialogs.showWarningMessage("Please add items to the cart.");
            return;
        }

        if (totalBill.isEmpty() || netBalance.isEmpty()) {
            MessageDialogs.showWarningMessage("Please calculate the total bill.");
            return;
        }

        // if the customer is walk-in customer then add arrival entry in product ledger and amount will be deducted from bank account
        // if the customer is account holder then add arrival entry in product ledger and amount will be added to the account holder's balance

        if (customerType.equals("Walk-in Customer")) {
            // Add the arrival entry in the product ledger
            boolean arrivalAdded = DatabaseOperations.addArrivalEntry(cartItems);
            if (arrivalAdded) {
                // Deduct the amount from the bank account
                boolean amountDeducted = DatabaseOperations.deductAmountFromBankAccount(paymentType, totalBill);
                if (amountDeducted) {
                    MessageDialogs.showMessageDialog("Purchase return successful.");
                    // Clear the form fields
                    clearInputFields();
                }else{
                    MessageDialogs.showErrorMessage("Error occurred while deducting the amount from the bank account.");
                }
            }else{
                MessageDialogs.showErrorMessage("Error occurred while adding the arrival entry in the product ledger.");
            }
        } else {
            // Add the arrival entry in the product ledger
            boolean arrivalAdded = DatabaseOperations.addArrivalEntry(cartItems, holderNo);
            if (arrivalAdded) {
                // Add the amount to the account holder's balance
                boolean amountAdded = DatabaseOperations.addAmountToAccountHolder(holderNo, cartItems, totalBill);
                if (amountAdded) {
                    MessageDialogs.showMessageDialog("Purchase return successful.");
                    // Clear the form fields
                    clearInputFields();
                }else{
                    MessageDialogs.showMessageDialog("Error occurred while adding the amount to the account holder's balance.");
                }
            }else{
                MessageDialogs.showMessageDialog("Error occurred while adding the arrival entry in the product ledger.");
            }
        }

    }

    @FXML
    void onUpdateCartItem() {
        Cart selectedCartItem = cartTableView.getSelectionModel().getSelectedItem();

        String productName = productNameSearchableComboBox.getValue();
        String productCode = productCodeTextField.getText();
        String brandName = brandNameTextField.getText();
        String quantityText = quantityTextField.getText();
        String priceText = priceTextField.getText();

        boolean isUpdated = CartUtility.updateCartItem(selectedCartItem, productCode, productName, brandName,
                quantityText, priceText, false);

        if (isUpdated) {
            // Update the total bill
            updateTotalBill();
            // Clear the form fields and reset the productCodeComboBox
            clearInputFields();
        }

        // Refresh the cart table view
        cartTableView.refresh();
        // Deselect the item
        cartTableView.getSelectionModel().clearSelection();
    }

    private void updateTotalBill() {
        double totalBill = CartUtility.calculateTotalBill(cartItems);
        // Update the totalBillTextField
        totalBillTextField.setText(String.valueOf(totalBill));
    }

    private void clearInputFields() {
        productNameSearchableComboBox.setValue(null);
        productCodeTextField.clear();
        brandNameTextField.clear();
        priceTextField.clear();
        quantityTextField.clear();
    }

}
