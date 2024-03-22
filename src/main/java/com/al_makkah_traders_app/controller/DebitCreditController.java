package com.al_makkah_traders_app.controller;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.messages.MessageDialogs;
import com.al_makkah_traders_app.model.DebitCredit;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

import java.math.BigInteger;

public class DebitCreditController {

    @FXML
    private TableView<DebitCredit> debitCreditTableView;
    @FXML
    private SearchableComboBox<String> accountHolderComboBox;
    @FXML
    private TextField cnicNoTextField;

    public void initialize() {
        accountHolderComboBox.setItems(DatabaseOperations.getAccountHolderNames());
    }
    @FXML
    void onRefresh() {
        accountHolderComboBox.setValue(null);
        debitCreditTableView.getItems().clear();
        cnicNoTextField.clear();
    }

    @FXML
    void onSearch() {
        String accountHolder = accountHolderComboBox.getSelectionModel().getSelectedItem();
        if (accountHolder == null) {
            MessageDialogs.showWarningMessage("No account holder selected");
            return;
        }
        ObservableList<DebitCredit> debitCreditObservableList = DatabaseOperations.getDebitCreditReport(accountHolder);
        if (debitCreditObservableList.isEmpty()) {
            MessageDialogs.showWarningMessage("No record found!");
            return;
        }
        debitCreditTableView.setItems(debitCreditObservableList);

    }

    @FXML
    void onSearchByCnic() {
        String cnicNo = cnicNoTextField.getText();
        if (cnicNo.isEmpty()) {
            MessageDialogs.showWarningMessage("No CNIC number entered");
            return;
        }
        ObservableList<DebitCredit> debitCreditObservableList = DatabaseOperations.getDebitCreditReport(new BigInteger(cnicNo));
        if (debitCreditObservableList.isEmpty()) {
            MessageDialogs.showWarningMessage("No record found!");
            return;
        }
        debitCreditTableView.setItems(debitCreditObservableList);

    }

}
