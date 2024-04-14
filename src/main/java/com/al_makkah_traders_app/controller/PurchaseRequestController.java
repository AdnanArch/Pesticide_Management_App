package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.Cart;
import com.al_makkah_traders_app.model.PurchaseRequest;
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
import java.util.Objects;

import static com.al_makkah_traders_app.utility.CartUtility.removeCommasFromCartItems;

public class PurchaseRequestController {
    ObservableList<Cart> cartItems;
    @FXML
    private TextField totalAmountTextField;
    @FXML
    private TextField paidAmountTextField;
    @FXML
    private SearchableComboBox<String> bankAccountSearchableComboBox;
    @FXML
    private TextField brandNameTextField;
    @FXML
    private TableView<Cart> cartTableView;
    @FXML
    private SearchableComboBox<String> companySearchableComboBox;
    @FXML
    private SearchableComboBox<String> dealerNameSearchableComboBox;
    @FXML
    private TextField freightTextField;
    @FXML
    private SearchableComboBox<String> partyTypeSearchableComboBox;
    @FXML
    private ComboBox<String> paymentMethodComboBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private SearchableComboBox<String> productCodeSearchableComboBox;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TableView<PurchaseRequest> purchaseRequestTableView;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<String> requestTypeComboBox;
    @FXML
    private TextField searchTextField;

    public void initialize() {
        // Initialize cartItems as an empty ObservableList
        cartItems = FXCollections.observableArrayList();

        populateProductCodeComboBox();
        populateAccountHolderNames();
        populateBankAccountsComboBox();
        populateCompaniesSearchableComboBox();

        // set fields edit property to false.
        productNameTextField.setEditable(false);
        brandNameTextField.setEditable(false);
        totalAmountTextField.setEditable(false);

        // Disable fields
        bankAccountSearchableComboBox.setDisable(true);
        freightTextField.setDisable(true);
        dealerNameSearchableComboBox.setDisable(true);
        requestTypeComboBox.setDisable(true);
        bankAccountSearchableComboBox.setDisable(true);

        // Add a listener to productCodeComboBox to populate data when a product is
        // selected
        productCodeSearchableComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        getProductDetails(newValue);
                    }
                });

        addCartTableSelectionListener(cartTableView);

        partyTypeSearchableComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if ("Wholesaler".equals(newValue)) {
                        dealerNameSearchableComboBox.setDisable(false);
                        freightTextField.setDisable(false);
                        requestTypeComboBox.setDisable(true);
                    } else {
                        dealerNameSearchableComboBox.setDisable(true);
                        freightTextField.setDisable(true);
                        requestTypeComboBox.setDisable(false);
                    }
                });

        paymentMethodComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if ("Cash".equals(newValue)) {
                        bankAccountSearchableComboBox.setDisable(true);
                        paidAmountTextField.setEditable(true);
                        paidAmountTextField.setDisable(false);
                    } else if ("Post Payment".equals(newValue)) {
                        paidAmountTextField.setDisable(true);
                        bankAccountSearchableComboBox.setDisable(true);
                        paidAmountTextField.setText("0");
                    } else {
                        paidAmountTextField.setEditable(true);
                        paidAmountTextField.setDisable(false);
                        bankAccountSearchableComboBox.setDisable(false);
                    }
                });

        paidAmountTextField.setText("0");

        // set products to productSearchableComboBox according to selected
        companySearchableComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        ObservableList<String> products = DatabaseOperations.getProductsByCompany(newValue);
                        productCodeSearchableComboBox.setItems(products);
                    }
                });

        populatePurchaseRequestTableView();
    }

    private void populatePurchaseRequestTableView() {
        ObservableList<PurchaseRequest> purchaseRequests = DatabaseOperations.getPurchaseRequests();
        purchaseRequestTableView.setItems(purchaseRequests);
    }

    private void populateCompaniesSearchableComboBox() {
        ObservableList<String> companiesNames = DatabaseOperations.getCompaniesNames();
        companySearchableComboBox.setItems(companiesNames);
    }

    private void populateBankAccountsComboBox() {
        ObservableList<String> bankAccounts = DatabaseOperations.getCompanyBankAccounts();
        bankAccounts.removeIf(s -> Objects.equals(s, "0000-000000 -> Cash"));
        bankAccountSearchableComboBox.setItems(bankAccounts);
    }

    private void populateAccountHolderNames() {
        ObservableList<String> accountHoldersNames = DatabaseOperations.getWholesalersNames();
        dealerNameSearchableComboBox.setItems(accountHoldersNames);
    }

    private void addCartTableSelectionListener(TableView<Cart> cartTableView) {
        cartTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        fillFormFields(newValue);
                    }
                });
    }

    private void getProductDetails(String productCode) {
        ArrayList<String> productData = DatabaseOperations.getProductDetails(productCode);
        productNameTextField.setText(productData.get(0));
        brandNameTextField.setText(productData.get(1));
        companySearchableComboBox.setValue(productData.get(2));
        priceTextField.setText(productData.get(3));
    }

    private void populateProductCodeComboBox() {
        ObservableList<String> productsCodes = DatabaseOperations.getProductsCodes();
        productCodeSearchableComboBox.setItems(productsCodes);
    }

    @FXML
    void onAddToCart() {
        // use the cartUtility class to add the item to the cartItems
        String productCode = productCodeSearchableComboBox.getValue();
        String productName = productNameTextField.getText();
        String brandName = brandNameTextField.getText();
        String quantity = quantityTextField.getText();
        String price = priceTextField.getText();

        boolean isAdded = CartUtility.addCartItem(removeCommasFromCartItems(cartItems), productCode, productName, brandName, quantity, price,
                false);

        if (isAdded) {
            // Add the cartItems to the cartTableView
            cartTableView.setItems(cartItems);
            // Update the totalAmountTextField by recalculating the total amount
            updateTotalAmountTextField();
            // Clear the form fields and reset the productCodeComboBox
            clearInputFields();
        }
    }

    // A helper method to calculate the total amount in the cart
    private double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (Cart cartItem : cartItems) {
            String amountString = cartItem.getTotalPrice();
            String amount = String.valueOf(NumberFormatter.removeCommas(amountString));
            totalAmount += Double.parseDouble(amount);
        }
        return totalAmount;
    }

    @FXML
    void onUpdateCartItem() {
        // Check if a cart item is selected
        Cart selectedCartItem = cartTableView.getSelectionModel().getSelectedItem();

        // Get the values from the text fields
        String productCode = productCodeSearchableComboBox.getValue();
        String productName = productNameTextField.getText();
        String brandName = brandNameTextField.getText();
        String quantity = quantityTextField.getText();
        String price = priceTextField.getText();

        // Update the selected cart item
        boolean isUpdated = CartUtility.updateCartItem(selectedCartItem, productCode, productName, brandName, quantity,
                price, false);

        if (isUpdated) {
            // Update the cartTableView
            cartTableView.refresh();
            // Update the totalAmountTextField by recalculating the total amount
            updateTotalAmountTextField();
            // Clear the form fields and reset the productCodeComboBox
            clearInputFields();
            // Deselect the item
            cartTableView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onDeleteCartItem() {
        // Get the selected item from the cart
        Cart selectedCartItem = cartTableView.getSelectionModel().getSelectedItem();
        boolean deleted = CartUtility.deleteCartItem(cartItems, selectedCartItem);
        if (deleted) {
            // Update the totalAmountTextField by recalculating the total amount
            updateTotalAmountTextField();
            // Refresh the cart table view
            cartTableView.refresh();
            // Deselect the item
            cartTableView.getSelectionModel().clearSelection();
        }
    }

    // A helper method to calculate and update the total amount in the
    // totalAmountTextField
    private void updateTotalAmountTextField() {
        double totalAmount = calculateTotalAmount();
        totalAmountTextField.setText(String.valueOf(totalAmount));
    }

    @FXML
    void onSearch() {
        String searchText = searchTextField.getText();
        if (searchText.isEmpty()) {
            populatePurchaseRequestTableView();
            return;
        }
        ObservableList<PurchaseRequest> purchaseRequests = DatabaseOperations.searchPurchaseRequests(searchText);
        purchaseRequestTableView.setItems(purchaseRequests);
        searchTextField.clear();
    }

    @FXML
    void onRefresh() {
        populatePurchaseRequestTableView();
        searchTextField.clear();
    }

    @FXML
    void onSubmitRequest() {
        // check if the cart is empty then simple return and do nothing.
        if (cartTableView.getItems().isEmpty()) {
            MessageDialogs.showMessageDialog("Please fill the cart before placing the order.");
            return;
        }

        String partyType = partyTypeSearchableComboBox.getValue();
        String paymentType = paymentMethodComboBox.getValue();
        String bankAccountNo = bankAccountSearchableComboBox.getValue();
        String totalAmount = totalAmountTextField.getText();
        String paidAmount = paidAmountTextField.getText();
        String accountNo = null;

        boolean isCorrectData;

        String paymentStatus = "Completed";

        if (partyType == null) {
            MessageDialogs.showErrorMessage("Please Select a party type.");
        } else {
            boolean incorrectAmount = Double.parseDouble(paidAmount) > Double.parseDouble(totalAmount);
            if (partyType.equals("Company")) {
                String requestType = requestTypeComboBox.getValue();
                Integer sourceId = DatabaseOperations.getCompanyId(productCodeSearchableComboBox.getValue());
                isCorrectData = checkRequestFieldsAreCorrectForCompanyOrder(requestType, paymentType, totalAmount,
                        paidAmount);
                String bookingType = requestType.equals("Plant Booking") ? "pb" : "wb";
    
                if (isCorrectData) {
                    switch (paymentType) {
                        case "Online", "Cheque", "Bank Draft" -> {
                            if (bankAccountNo == null) {
                                MessageDialogs.showWarningMessage("Please select a bank account.");
                                return;
                            }
                            accountNo = Utility.extractAccountNumber(bankAccountNo);
                        }
                        case "Cash" -> accountNo = "0000-000000";
                    }
                    // bug fix for paid amount greater than total amount
                    if (incorrectAmount) {
                        MessageDialogs.showWarningMessage("Paid amount cannot be greater than total amount.");
                        return;
                    }
                    boolean requestSubmitted = DatabaseOperations.submitRequestToCompany(removeCommasFromCartItems(cartItems), totalAmount, paidAmount,
                            bookingType, paymentType, accountNo, sourceId);
                    if (requestSubmitted) {
                        MessageDialogs.showMessageDialog("Your request has been submitted successfully.");
                        clearForm();
                    } else {
                        MessageDialogs.showErrorMessage("An error occurred while submitting the request.\n" +
                                "Check the input data or may be your account balance is insufficient.");
                    }
                }
            } else if (partyType.equals("Wholesaler")) {
                String partyName = dealerNameSearchableComboBox.getValue();
                String freightAmount = freightTextField.getText();
                BigInteger sourceId = DatabaseOperations.getWholesalerId(partyName);
    
                isCorrectData = checkRequestFieldsAreCorrectForWholesalerOrder(partyName, paymentType, totalAmount,
                        paidAmount, freightAmount);
    
                if (isCorrectData) {
                    switch (paymentType) {
                        case "Online", "Cheque", "Bank Draft" -> {
                            if (bankAccountNo == null) {
                                MessageDialogs.showWarningMessage("Please select a bank account.");
                                return;
                            }
                            accountNo = Utility.extractAccountNumber(bankAccountNo);
                        }
                        case "Cash" -> accountNo = "0000-000000";
                        case "Post Payment" -> paymentStatus = "Pending";
                    }
                    // bug fix for paid amount greater than total amount
                    if (incorrectAmount) {
                        MessageDialogs.showWarningMessage("Paid amount cannot be greater than total amount.");
                        return;
                    }
                    boolean requestSubmitted = DatabaseOperations.submitRequestToWholesaler(cartItems, totalAmount,
                            paymentType, accountNo, partyType, sourceId, paymentStatus, paidAmount, freightAmount);
    
                    if (requestSubmitted) {
                        MessageDialogs.showMessageDialog("Your request has been submitted successfully.");
                        clearForm();
                    } else {
                        MessageDialogs.showErrorMessage("An error occurred while submitting the request." +
                                "Check the input data or may be your account balance is insufficient.");
                    }
                }
            } else {
                MessageDialogs.showWarningMessage("Please check all input fields.");
            }
        }

    }

    private boolean checkRequestFieldsAreCorrectForCompanyOrder(String requestType, String paymentType,
                                                                String totalAmount, String paidAmount) {
        if (requestType == null) {
            MessageDialogs.showWarningMessage("Please select the booking type.");
            return false;
        } else if (paymentType == null) {
            MessageDialogs.showWarningMessage("Please select a payment method.");
            return false;
        } else if (totalAmount.isEmpty() || !Utility.isNumeric(totalAmount)) {
            MessageDialogs.showWarningMessage("Please check your total bill amount.");
            return false;
        } else if (paidAmount.isEmpty() || !Utility.isNumeric(paidAmount)) {
            MessageDialogs.showWarningMessage("Please Check your Paid Amount.");
            return false;
        }
        return true;
    }

    private boolean checkRequestFieldsAreCorrectForWholesalerOrder(String partyName, String paymentType,
                                                                   String totalAmount, String paidAmount, String freightAmount) {
        if (partyName == null) {
            MessageDialogs.showWarningMessage("Please select the wholesaler.");
            return false;
        } else if (paymentType == null) {
            MessageDialogs.showWarningMessage("Please select a payment method.");
            return false;
        } else if (totalAmount.isEmpty() || !Utility.isNumeric(totalAmount)) {
            MessageDialogs.showWarningMessage("Please check the total amount field.");
            return false;
        } else if (paidAmount.isEmpty() || !Utility.isNumeric(paidAmount)) {
            MessageDialogs.showWarningMessage("Please check the paid amount field.");
            return false;
        } else if (freightAmount.isEmpty() || !Utility.isNumeric(freightAmount)) {
            MessageDialogs.showWarningMessage("Please check the freight amount field.");
            return false;
        }
        return true;
    }

    private void clearInputFields() {
        productNameTextField.clear();
        brandNameTextField.clear();
        quantityTextField.clear();
        priceTextField.clear();
    }

    private void fillFormFields(Cart selectedCartItem) {
        productCodeSearchableComboBox.setValue(selectedCartItem.getProductCode());
        productNameTextField.setText(selectedCartItem.getProductName());
        brandNameTextField.setText(selectedCartItem.getBrandName());
        priceTextField.setText(String.valueOf(NumberFormatter.removeCommas(selectedCartItem.getPricePerUnit())));
        quantityTextField.setText(String.valueOf(selectedCartItem.getQuantity()));
    }

    // A method to clear all the fields and cartItems on the form
    private void clearForm() {
        // Clear the cartItems
        cartItems.clear();
        // Clear the cartTableView
        cartTableView.refresh();
        // Clear the form fields
        clearInputFields();
        // Clear the totalAmountTextField
        totalAmountTextField.clear();
        // Clear the paidAmountTextField
        paidAmountTextField.clear();
        // Clear the freightTextField
        freightTextField.clear();
        // Clear the bankAccountSearchableComboBox
        bankAccountSearchableComboBox.getSelectionModel().clearSelection();
        // Clear the dealerNameSearchableComboBox
        dealerNameSearchableComboBox.getSelectionModel().clearSelection();
        // Clear the partyTypeSearchableComboBox
        partyTypeSearchableComboBox.getSelectionModel().clearSelection();
        // Clear the paymentMethodComboBox
        paymentMethodComboBox.getSelectionModel().clearSelection();
        // Clear the requestTypeComboBox
        requestTypeComboBox.getSelectionModel().clearSelection();
    }
}