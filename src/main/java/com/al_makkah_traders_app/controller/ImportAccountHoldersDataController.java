package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.AccountHolder;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImportAccountHoldersDataController {
    private static final Logger log = LogManager.getLogger(ImportAccountHoldersDataController.class);

    @FXML
    private TableView<AccountHolder> accountHoldersTableView;

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
        try {
            // Check if the table is not empty
            if (accountHoldersTableView.getItems().isEmpty()) {
                MessageDialogs.showErrorMessage("No account holders to insert.");
                return; // Exit the method since there's nothing to insert
            }

            boolean allRecordsInserted = DatabaseOperations.insertAccountHolders(accountHoldersTableView.getItems());

            if (allRecordsInserted) {
                MessageDialogs.showMessageDialog("All account holders created successfully.");
                accountHoldersTableView.getItems().clear();
            } else {
                MessageDialogs.showErrorMessage("Error inserting account holder(s). May be already exits.");
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error("Error inserting account holders(s): " + e.getMessage(), e);
        }
    }

    private void populateTableView(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            // Clear existing data in the TableView if needed
            accountHoldersTableView.getItems().clear();

            for (Row row : sheet) {
                AccountHolder accountHolder = new AccountHolder();

                // Check if the row is null or empty
                if (row == null || row.getFirstCellNum() == -1) {
                    continue; // Skip this row
                }

                // Add these items to table view if the cell is not null
                accountHolder.setName(getStringValue(row.getCell(0)));
                accountHolder.setCnicNo(getStringValue(row.getCell(1)));
                accountHolder.setAddress(getStringValue(row.getCell(2)));
                accountHolder.setPhone(getStringValue(row.getCell(3)));

                // Handle boolean and numeric values separately
                if (row.getCell(4) != null) {
                    accountHolder.setisRetailer(row.getCell(4).getBooleanCellValue());
                }
                if (row.getCell(5) != null) {
                    accountHolder.setTotalBalance(String.valueOf(row.getCell(5).getNumericCellValue()));
                }

                // Set other properties as needed
                accountHoldersTableView.getItems().add(accountHolder);
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

    // Helper method to handle null cells and retrieve string value
    private String getStringValue(Cell cell) {
        return cell != null ? cell.getStringCellValue() : "";
    }

}
