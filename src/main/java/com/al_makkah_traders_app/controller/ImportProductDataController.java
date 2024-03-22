package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImportProductDataController {
    private static final Logger log = LogManager.getLogger(ImportProductDataController.class);

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private void onImportButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            populateTableView(selectedFile);
        }
    }

    @FXML
    void onInsert() {
        ObservableList<Product> products = productTableView.getItems();

        if (products.isEmpty()) {
            MessageDialogs.showErrorMessage("No products to insert.");
            return;
        }

        try {
            boolean allRecordsInserted = DatabaseOperations.insertProducts(products);

            if (allRecordsInserted) {
                MessageDialogs.showMessageDialog("All products inserted successfully.");
            } else {
                MessageDialogs.showErrorMessage("Error inserting one or more products.");
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error("Error inserting product(s): " + e.getMessage(), e);

            // Show a user-friendly error message
            MessageDialogs.showErrorMessage("An error occurred while inserting product(s).");
        }
    }

    private void populateTableView(File file) {
        try (FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            // Clear existing data in the TableView if needed
            productTableView.getItems().clear();

            for (Row row : sheet) {
                Product product = new Product();

                // add these items to table view
                product.setProductCode(row.getCell(0).getStringCellValue());
                product.setProductName(row.getCell(1).getStringCellValue());
                product.setCategoryName(row.getCell(2).getStringCellValue());
                product.setBrandName(row.getCell(3).getStringCellValue());
                product.setCompanyName(row.getCell(4).getStringCellValue());
                product.setPrice(row.getCell(5).getNumericCellValue());
                product.setWarehouseQuantity(row.getCell(6).getNumericCellValue());
                product.setShopQuantity(row.getCell(7).getNumericCellValue());

                // Set other properties as needed
                productTableView.getItems().add(product);

                // these items are not added in table view.
                product.setAddress(row.getCell(8).getStringCellValue());
                product.setContact(row.getCell(9).getStringCellValue());
            }
        } catch (IOException e) {
            log.error("Error reading Excel file: " + e.getMessage(), e);
            // Show a user-friendly error message
            MessageDialogs.showErrorMessage("An error occurred while reading the Excel file.");
        } catch (Exception e) {
            log.error("Error populating table view: " + e.getMessage(), e);
            // Show a user-friendly error message
            MessageDialogs.showErrorMessage("An error occurred while populating the table view.");
        }
    }
}
