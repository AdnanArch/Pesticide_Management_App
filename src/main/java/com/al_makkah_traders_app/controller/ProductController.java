package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseConnection;
import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.ImportProductsData;
import com.al_makkah_traders_app.model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ProductController {

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField brandNameTextField;
    @FXML
    private SearchableComboBox<String> categoryNameComboBox;
    @FXML
    private TextField companyNameTextField;
    @FXML
    private TextField contactTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField productCodeTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField searchProductField;
    @FXML
    private TextField shopQuantityTextField;
    @FXML
    private TextField whQuantityTextField;

    // Reusable method to initialize the controller
    public void initialize() {
        populateProductTableData();
        setComboBoxEnterKeyBehavior(categoryNameComboBox);
        setEnterKeySearchBehavior(searchProductField);
        addTableSelectionListener(productTableView);
    }

    // Reusable method to populate product table data
    private void populateProductTableData() {
        ObservableList<Product> productsData = DatabaseOperations.fetchProductData("");
        productTableView.setItems(productsData);
    }

    // Reusable method to set Enter key behavior for ComboBox
    private void setComboBoxEnterKeyBehavior(SearchableComboBox<String> comboBox) {
        comboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                comboBox.show();
                event.consume();
            }
        });
    }

    // Reusable method to set Enter key behavior for searching
    private void setEnterKeySearchBehavior(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onSearch();
                event.consume();
            }
        });
    }

    // Reusable method to add a selection listener to the TableView
    private void addTableSelectionListener(TableView<Product> tableView) {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        fillFormFields(newValue);
                        getProductCompanyAddressAndContact();
                    }
                });
    }

    private void getProductCompanyAddressAndContact() {
        String productCode = productCodeTextField.getText();

        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("CALL sp_get_product_company_address_and_contact(?, ?, ?)");

            statement.setString(1, productCode);
            statement.registerOutParameter(2, Types.VARCHAR);
            statement.registerOutParameter(3, Types.VARCHAR);

            statement.execute();

            String address = statement.getString(2);
            String contact = statement.getString(3);

            addressTextField.setText(address);
            contactTextField.setText(contact);

            statement.close();
            db.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Reusable method to fill form fields with data
    private void fillFormFields(Product selectedProduct) {
        productCodeTextField.setText(selectedProduct.getProductCode());
        productNameTextField.setText(selectedProduct.getProductName());
        categoryNameComboBox.setValue(selectedProduct.getCategoryName());
        brandNameTextField.setText(selectedProduct.getBrandName());
        companyNameTextField.setText(selectedProduct.getCompanyName());
        priceTextField.setText(String.valueOf(selectedProduct.getPrice()));
        shopQuantityTextField.setText(String.valueOf(selectedProduct.getShopQuantity()));
        whQuantityTextField.setText(String.valueOf(selectedProduct.getWarehouseQuantity()));
    }

    /**
     * Handles adding a new product.
     */
    @FXML
    void addNewProduct() {
        // Get input values from form fields
        String productCode = productCodeTextField.getText().trim();
        String productName = productNameTextField.getText().trim();
        String categoryName = categoryNameComboBox.getValue();
        String brandName = brandNameTextField.getText().trim();
        String price = priceTextField.getText().trim();
        String companyName = companyNameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String contact = contactTextField.getText().trim();
        double warehouseQuantity = Double.parseDouble(whQuantityTextField.getText());
        double shopQuantity = Double.parseDouble(shopQuantityTextField.getText());

        // Check if the input data is valid
        boolean isValid = checkIfDataIsValid(productCode, productName, categoryName, brandName, price, companyName,
                address, contact, warehouseQuantity, shopQuantity);

        if (isValid) {
            boolean result = DatabaseOperations.addNewProduct(productCode, productName, categoryName, brandName, price,
                    companyName, address, contact, warehouseQuantity, shopQuantity);

            if (result) {
                MessageDialogs.showMessageDialog("Product added successfully.");
                clearInputFields();
                populateProductTableData();
            } else {
                MessageDialogs.showWarningMessage("Product was not inserted. ");
            }
        }
    }

    // Handle product deletion
    @FXML
    void onDelete() {
        String productCode = productCodeTextField.getText().trim();

        boolean deleted = DatabaseOperations.deleteProduct(productCode);

        if (deleted) {
            MessageDialogs.showMessageDialog("Product has been deleted successfully.");
            populateProductTableData();
            clearInputFields();
        } else {
            MessageDialogs.showErrorMessage("This product cannot be deleted.");
        }
    }

    // Handle updating product information
    @FXML
    void onUpdate() {
        String productCode = productCodeTextField.getText().trim();
        String productName = productNameTextField.getText().trim();
        String categoryName = categoryNameComboBox.getValue();
        String brandName = brandNameTextField.getText().trim();
        String price = priceTextField.getText().trim();
        String companyName = companyNameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String contact = contactTextField.getText().trim();
        double warehouseQuantity = Double.parseDouble(whQuantityTextField.getText());
        double shopQuantity = Double.parseDouble(shopQuantityTextField.getText());


        // Check if the input data is valid
        boolean isValid = checkIfDataIsValid(productCode, productName, categoryName, brandName, price, companyName,
                address, contact, warehouseQuantity, shopQuantity);

        if (isValid) {
            boolean updated = DatabaseOperations.updateProductInfo(
                    productCode,
                    productName,
                    categoryName,
                    brandName,
                    price,
                    companyName,
                    address,
                    contact,
                    shopQuantity,
                    warehouseQuantity);

            if (updated) {
                MessageDialogs.showMessageDialog("Product's data is successfully updated.");
            } else {
                MessageDialogs.showWarningMessage("Product's data was not updated.");
            }

            populateProductTableData();
            clearInputFields();
        }
    }

    @FXML
    void onImport() {
        ImportProductsData importProductsData = new ImportProductsData();
        Stage stage = new Stage();
        try {
            importProductsData.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onRefresh() {
        populateProductTableData();
    }

    @FXML
    void onSearch() {
        String searchText = searchProductField.getText();
        ObservableList<Product> productsData = DatabaseOperations.fetchProductData(searchText);
        productTableView.setItems(productsData);
    }

    // Reusable method to check if the input data is valid
    private boolean checkIfDataIsValid(String productCode, String productName, String categoryName, String brandName,
                                       String price, String companyName, String address, String contact, double warehouseQuantity, double shopQuantity) {
        if (productCode.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the product code.");
            return false;
        } else if (productName.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the product name.");
            return false;
        } else if (categoryName == null || categoryName.isEmpty()) {
            MessageDialogs.showWarningMessage("Please select a category.");
            return false;
        } else if (brandName.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the brand name of the product.");
            return false;
        } else if (price.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the price per unit of the product.");
            return false;
        } else if (companyName.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the company name of the product.");
            return false;
        } else if (address.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the address of the company.");
            return false;
        } else if (contact.isEmpty()) {
            MessageDialogs.showWarningMessage("Please enter the contact number of the company.");
            return false;
        } else if (warehouseQuantity < 0 || shopQuantity < 0) {
            MessageDialogs.showWarningMessage("Please enter a valid quantity.");
            return false;
        }
        return true;
    }

    // Reusable method to clear input fields
    private void clearInputFields() {
        productCodeTextField.clear();
        productNameTextField.clear();
        companyNameTextField.clear();
        categoryNameComboBox.getEditor().setText(categoryNameComboBox.getPromptText());
        brandNameTextField.clear();
        priceTextField.clear();
        contactTextField.clear();
        addressTextField.clear();
        whQuantityTextField.clear();
        shopQuantityTextField.clear();
    }

}
