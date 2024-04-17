package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.Cart;
import com.al_makkah_traders_app.utility.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

import java.math.BigInteger;
import java.util.ArrayList;

public class AccountHolderBillController {

    @FXML
    private SearchableComboBox<String> accountHolderComboBox;

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
    private SearchableComboBox<String> productCodeSearchableComboBox;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField receivedAmountTextField;

    @FXML
    private TextField totalBillTextField;
    private ObservableList<Cart> cartItems;
    private static BigInteger holderNo;

    public void initialize() {
        // set text fields to editable false
        productNameTextField.setEditable(false);
        brandNameTextField.setEditable(false);
        totalBillTextField.setEditable(false);
        previousBalanceTextField.setEditable(false);
        netBalanceTextField.setEditable(false);

        // initialize the cartItems to an empty list
        cartItems = FXCollections.observableArrayList();

        populateProductCodeComboBox();
        populateAccountHolderComboBox();
        populatePaymentTypeComboBox();
        addCartTableSelectionListener(cartTableView);
        // populate the paymentMethodComboBox get it from the database
        paymentTypeComboBox.setItems(DatabaseOperations.getCompanyBankAccounts());

        // Add a listener to productCodeComboBox to populate data when a product is
        // selected
        productCodeSearchableComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        getProductDetails(newValue);
                    }
                });

        // Add a listener to the amountTextField text property
        receivedAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> calculateAndSetNetBalance());

        // Add a listener to the accountHolderComboBox selection
        accountHolderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                populatePreviousBalanceTextField(newValue);
            }
        });
    }

    private void addCartTableSelectionListener(TableView<Cart> cartTableView) {
        cartTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        fillFormFields(newValue);
                    }
                });
    }

    private void populateAccountHolderComboBox() {
        ObservableList<String> accountHolderList = DatabaseOperations.getAccountHolderNames();
        accountHolderComboBox.setItems(accountHolderList);
    }

    private void populatePaymentTypeComboBox() {
        ObservableList<String> paymentTypeList = DatabaseOperations.getCompanyBankAccounts();
        paymentTypeComboBox.setItems(paymentTypeList);
    }

    private void fillFormFields(Cart cartItem) {
        productCodeSearchableComboBox.setValue(cartItem.getProductCode());
        productNameTextField.setText(cartItem.getProductName());
        brandNameTextField.setText(cartItem.getBrandName());
        priceTextField.setText(String.valueOf(NumberFormatter.removeCommas(cartItem.getPricePerUnit())));
        quantityTextField.setText(String.valueOf(cartItem.getQuantity()));
    }

    private void getProductDetails(String productCode) {
        ArrayList<String> productDetails = DatabaseOperations.getProductDetails(productCode);
        productNameTextField.setText(productDetails.get(0));
        brandNameTextField.setText(productDetails.get(1));
        priceTextField.setText(productDetails.get(3));
    }

    private void populateProductCodeComboBox() {
        ObservableList<String> products = DatabaseOperations.getProductsCodes();
        productCodeSearchableComboBox.setItems(products);
    }

    @FXML
    void onAddCartItem() {
        // Get the values from the text fields
        String productCode = productCodeSearchableComboBox.getValue();
        String productName = productNameTextField.getText();
        String brandName = brandNameTextField.getText();
        String price = priceTextField.getText();
        String quantity = quantityTextField.getText();

        // Add the new item to the cart
        boolean itemAdded = CartUtility.addCartItem(cartItems, productCode, productName, brandName, quantity, price,
                true);

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
    void onUpdateCartItem() {
        // Check if a cart item is selected
        Cart selectedCartItem = cartTableView.getSelectionModel().getSelectedItem();

        String productCode = productCodeSearchableComboBox.getValue();
        String productName = productNameTextField.getText();
        String brandName = brandNameTextField.getText();
        String quantityText = quantityTextField.getText();
        String priceText = priceTextField.getText();

        boolean isUpdated = CartUtility.updateCartItem(selectedCartItem, productCode, productName, brandName,
                quantityText, priceText, true);

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

    @FXML
    void onDeleteCartItem() {
        // Get the selected item from the cart
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

    private void updateTotalBill() {
        double totalBill = CartUtility.calculateTotalBill(cartItems);
        // Update the totalBillTextField
        totalBillTextField.setText(String.valueOf(totalBill));
    }

    @FXML
    void onPrintBill() {
        // Check if the cart is empty
        if (cartItems.isEmpty()) {
            MessageDialogs.showWarningMessage("Please add items to the cart.");
            return;
        }

        String paymentMethod = paymentTypeComboBox.getValue();
        String totalBill = totalBillTextField.getText();

        // Check if the payment method is empty
        if (paymentMethod == null) {
            MessageDialogs.showWarningMessage("Please select the payment method.");
            return;
        }

        String accountNumber;

        // if payment method string ends with "Cash" then accountNo = "0000-000000"
        if (paymentMethod.endsWith("Cash")) {
            accountNumber = "0000-000000";
        } else {
            // else extract the account number from the payment method string
            accountNumber = Utility.extractAccountNumber(paymentMethod);
        }

        // Get the received amount
        double receivedAmount = 0;
        if (!receivedAmountTextField.getText().isEmpty() && Utility.isNumeric(receivedAmountTextField.getText())) {
            receivedAmount = Double.parseDouble(receivedAmountTextField.getText());
        }

        // insert the account-holder-bill into the database
        BillCreationResult isInserted = DatabaseOperations.insertAccountHolderBill(holderNo, accountNumber, totalBill, cartItems, receivedAmount);


        if (isInserted.isSuccess()) {
            // Show a success message
            MessageDialogs.showMessageDialog("Bill added successfully.");

            BillPrinter receiptPrinter = new BillPrinter(
                    cartItems,
                    totalBill,
                    String.valueOf(receivedAmount),
                    previousBalanceTextField.getText(),
                    netBalanceTextField.getText(),
                    accountHolderComboBox.getValue(),
                    Utility.extractPaymentMode(paymentMethod),
                    false,
                    true,
                    false,
                    "",
                    isInserted.getBillId()
            );

            // Print the receipt
            receiptPrinter.printReceipt();

            // Clear the form fields
            clearInputFields();

            // Clear the cartItems
            cartItems.clear();

            receivedAmountTextField.clear();
            totalBillTextField.clear();
            netBalanceTextField.clear();
            previousBalanceTextField.clear();
        } else {
            MessageDialogs.showErrorMessage("Bill not added.");
        }
    }

    private void clearInputFields() {
        productCodeSearchableComboBox.setValue(null);
        productNameTextField.clear();
        brandNameTextField.clear();
        priceTextField.clear();
        quantityTextField.clear();
        paymentTypeComboBox.setValue(null);
    }

    private void calculateAndSetNetBalance() {
        // Get the previous balance
        double previousBalance = Double.parseDouble(previousBalanceTextField.getText());

        // Get the entered amount
        double amount = 0;
        double totalBill = 0;
        if (!receivedAmountTextField.getText().isEmpty() && Utility.isNumeric(receivedAmountTextField.getText())) {
            amount = Double.parseDouble(receivedAmountTextField.getText());
        }

        if (!totalBillTextField.getText().isEmpty() && Utility.isNumeric(totalBillTextField.getText())) {
            totalBill = Double.parseDouble(totalBillTextField.getText());
        }

        // Calculate net balance based on bill type and previous balance
        double netBalance = getNetBalance(previousBalance, amount, totalBill);

        // Set the net balance to the text field
        netBalanceTextField.setText(String.valueOf(netBalance));
    }

    private static double getNetBalance(double previousBalance, double amount, double totalBill) {
        double netBalance;
        // Previous balance is positive, add the amount
        netBalance = previousBalance + amount - totalBill;


        return netBalance;
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



}
