package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.Booking;
import com.al_makkah_traders_app.model.CompanyAccount;
import com.al_makkah_traders_app.model.Product;
import com.al_makkah_traders_app.utility.DateFormatter;
import com.al_makkah_traders_app.utility.ReportTest;
import com.al_makkah_traders_app.utility.ReportUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static java.time.LocalDate.now;

public class DailyReportController {
    @FXML
    private TableView<Booking> bookingsTableView;

    @FXML
    private TableView<CompanyAccount> bankBalanceTableView;

    @FXML
    private Label dateLabel;

    @FXML
    private TableView<AccountHolder> debitCreditTableView;

    @FXML
    private TableView<Product> stockTableView;

    public void initialize() {
        dateLabel.setText(DateFormatter.formatDate(now()));
        populateStockTable();
        populateDebitCreditTable();
        populateBankBalanceTable();
        populateBookingsTable();
    }

    private void populateBookingsTable() {
        bookingsTableView.setItems(ReportUtil.generateBookingsReport());
    }

    private void populateBankBalanceTable() {
        bankBalanceTableView.setItems(ReportUtil.generateCompanyAccountReport());
    }

    public void populateDebitCreditTable() {
        debitCreditTableView.setItems(ReportUtil.generateAccountHoldersReport());
    }

    public void populateStockTable() {
        stockTableView.setItems(ReportUtil.generateProductsReport());
    }

    @FXML
    void onPrintReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                ReportTest.generateExcelReport(
                        debitCreditTableView.getItems(),
                        stockTableView.getItems(),
                        bookingsTableView.getItems(),
                        bankBalanceTableView.getItems(), file);
                MessageDialogs.showMessageDialog("Report saved successfully.");
            } catch (IOException e) {
                MessageDialogs.showErrorMessage("Error occurred while saving the report");
                System.err.println("Error occurred while saving the report: " + e.getMessage());
            }
        } else {
            MessageDialogs.showMessageDialog("Save operation was cancelled.");
        }
    }


}
