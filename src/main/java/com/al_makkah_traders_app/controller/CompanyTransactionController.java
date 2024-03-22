package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.CompanyTransaction;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.controlsfx.control.SearchableComboBox;

public class CompanyTransactionController {
    @FXML
    private SearchableComboBox<String> accountNoComboBox;

    @FXML
    private TableView<CompanyTransaction> debitCreditTableView;

    public void initialize(){
        populateCompanyAccounts();
    }

    private void populateCompanyAccounts(){
        ObservableList<String> companyAccounts = DatabaseOperations.getCompanyBankAccounts();
        accountNoComboBox.setItems(companyAccounts);
    }

    @FXML
    void onRefresh() {
        debitCreditTableView.getItems().clear();
        accountNoComboBox.setValue(null);
    }

    @FXML
    void onSearch() {
        String accountNoString = accountNoComboBox.getValue();
        if (accountNoString.isEmpty()) {
            MessageDialogs.showWarningMessage("Enter account No.");
            return;
        }
        String accountNo = Utility.extractAccountNumber(accountNoString);
        ObservableList<CompanyTransaction> companyTransactionsList = DatabaseOperations.getCompanyAccountReport(accountNo);

        if (companyTransactionsList.isEmpty()) {
            MessageDialogs.showWarningMessage("No record found!");
        } else {
            debitCreditTableView.setItems(companyTransactionsList);
            accountNoComboBox.setValue(null);
        }

    }
}
