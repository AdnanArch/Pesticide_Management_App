package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseBackup;
import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.*;
import com.al_makkah_traders_app.utility.DateFormatter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashBoardController {
    @FXML
    private DatePicker dateTextField;
    @FXML
    private TableView<SalesRecord> salesTableView;
    @FXML
    private TableView<OnlinePayment> onlinePaymentsTableView;
    @FXML
    private TableView<StockArrivalRecord> stockArrivalsTableView;
    @FXML
    private TableView<MiscellaneousPayments> miscellaneousPaymentsTableView;

    public void initialize() {
        // Set the current date
        dateTextField.setValue(LocalDate.now());

        // Format the date in dd-mm-yyyy format
//        StringConverter<LocalDate> converter = new StringConverter<>() {
//            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//            @Override
//            public String toString(LocalDate date) {
//                if (date != null) {
//                    return dateFormatter.format(date);
//                } else {
//                    return "";
//                }
//            }
//
//            @Override
//            public LocalDate fromString(String string) {
//                if (string != null && !string.isEmpty()) {
//                    return LocalDate.parse(string, dateFormatter);
//                } else {
//                    return null;
//                }
//            }
//        };

        StringConverter<LocalDate> converter = DateFormatter.getDateStringConverter();

        // Set the custom converter to the dateTextField
        dateTextField.setConverter(converter);

        populateTodayDashboard(LocalDate.now());

        // Initialize the scheduler to refresh data every 2 minutes
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // Refresh the data on the dashboard
            Platform.runLater(() -> populateTodayDashboard(dateTextField.getValue()));
        }, 0, 2, TimeUnit.MINUTES);

        Platform.runLater(() -> {
            Stage stage = (Stage) dateTextField.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                scheduler.shutdown();
                Platform.exit();
            });
        });
    }

    private void populateTodayDashboard(LocalDate date){
        populateMiscellaneousPaymentsTable(date);
        populateStockArrivalTableView(date);
        populateSalesRecordTableView(date);
        populateBankPaymentsTableView(date);
    }

    private void populateBankPaymentsTableView(LocalDate date){
        ObservableList<OnlinePayment> onlinePayments = DatabaseOperations.getTodayOnlinePayments(date);
        onlinePaymentsTableView.setItems(onlinePayments);
    }
    private void populateSalesRecordTableView(LocalDate date){
        ObservableList<SalesRecord> salesRecords = DatabaseOperations.getTodaySales(date);
        salesTableView.setItems(salesRecords);
    }
    private void populateStockArrivalTableView(LocalDate date){
        ObservableList<StockArrivalRecord> stockArrivalRecords = DatabaseOperations.getTodayStockArrivals(date);
        stockArrivalsTableView.setItems(stockArrivalRecords);
    }

    private void populateMiscellaneousPaymentsTable(LocalDate date){
        ObservableList<MiscellaneousPayments> miscellaneousPayments = DatabaseOperations.getAllMiscellaneousPayments(date);
        miscellaneousPaymentsTableView.setItems(miscellaneousPayments);
    }

    @FXML
    void accountHolderBillMenuItem() {
        AccountHolderBill accountHolderBill = new AccountHolderBill();
        Stage stage = new Stage();
        try {
            accountHolderBill.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void emptyBillMenuItem() {
        EmptyBill emptyBill = new EmptyBill();
        Stage stage = new Stage();
        try {
            emptyBill.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void walkInBillMenuItem() {
        WalkInBill walkInBill = new WalkInBill();
        Stage stage = new Stage();
        try {
            walkInBill.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAddAccountHolderMenuItem() {
        AccountHolder accountHolder = new AccountHolder();
        Stage stage = new Stage();
        try {
            accountHolder.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAddNewProductMenuItem() {
        Product product = new Product();
        Stage stage = new Stage();
        try {
            product.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onPurchaseRequestMenuItem() {
        PurchaseRequest purchase = new PurchaseRequest();
        Stage stage = new Stage();
        try {
            purchase.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onStockArrivalMenuItem() {
        StockArrival stockArrival = new StockArrival();
        Stage stage = new Stage();
        try {
            stockArrival.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onStockTransferMenuItem() {
        StockTransfer stockTransfer = new StockTransfer();
        Stage stage = new Stage();
        try {
            stockTransfer.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAddNewCompanyAccount() {
        CompanyAccount account = new CompanyAccount();
        Stage stage = new Stage();
        try {
            account.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onOverInvoiceMenuItem() {
        OverInvoice overInvoice = new OverInvoice();
        Stage stage = new Stage();
        try {
            overInvoice.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onPendingOrderPaymentMenuItem() {
        PendingOrdersPayments pendingOrdersPayments = new PendingOrdersPayments();
        Stage stage = new Stage();
        try {
            pendingOrdersPayments.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onPettyCashPaymentMenuItem() {
        MiscellaneousPayments miscellaneousPayments = new MiscellaneousPayments();
        Stage stage = new Stage();
        try {
            miscellaneousPayments.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onReset() {
        populateTodayDashboard(LocalDate.now());
        dateTextField.setValue(LocalDate.now());
    }

    @FXML
    void onSearch() {
        LocalDate date = dateTextField.getValue();
        if (date == null) {
            MessageDialogs.showWarningMessage("Please select a date.");
            return;
        }
        populateTodayDashboard(date);
    }

    @FXML
    void onStockDepletionMenuItem(){
        StockDepletion stockDepletion = new StockDepletion();
        Stage stage = new Stage();
        try {
            stockDepletion.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onCustomerReportMenuItem(){
        DebitCredit customerReport = new DebitCredit();
        Stage stage = new Stage();
        try {
            customerReport.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onCompanyReportMenuItem(){
        CompanyTransaction companyTransaction = new CompanyTransaction();
        Stage stage = new Stage();
        try{
            companyTransaction.start(stage);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onCompanyDailyReportMenuItem(){
        DailyReport dailyReport = new DailyReport();
        Stage stage = new Stage();
        try{
            dailyReport.start(stage);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onBackupDatabaseMenuItem(){
        DatabaseBackup.backupDatabase();
    }

    @FXML
    void onPendingPaymentMenuItem() {
        PendingCheque pendingCheque = new PendingCheque();
        Stage stage = new Stage();
        try {
            pendingCheque.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
