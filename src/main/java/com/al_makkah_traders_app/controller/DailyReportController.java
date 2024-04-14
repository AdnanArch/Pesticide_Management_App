package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.model.*;
import com.al_makkah_traders_app.utility.DateFormatter;
import com.al_makkah_traders_app.utility.GenerateReport;
import com.al_makkah_traders_app.utility.ReportTest;
import com.al_makkah_traders_app.utility.ReportUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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

    public void populateDebitCreditTable(){
        debitCreditTableView.setItems(ReportUtil.generateAccountHoldersReport());
    }

    public void populateStockTable(){
        stockTableView.setItems(ReportUtil.generateProductsReport());
    }

    @FXML
    void onPrintReport() throws IOException {
        ReportTest.generateReport(debitCreditTableView.getItems(), stockTableView.getItems(),
                bookingsTableView.getItems(), bankBalanceTableView.getItems());
    }
}
