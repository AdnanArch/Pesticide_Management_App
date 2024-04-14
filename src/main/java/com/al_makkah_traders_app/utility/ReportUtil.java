package com.al_makkah_traders_app.utility;

import com.al_makkah_traders_app.database.DatabaseOperations;
import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.Booking;
import com.al_makkah_traders_app.model.CompanyAccount;
import com.al_makkah_traders_app.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Author: Adnan Rafique
 * Date: 4/13/2024
 * Time: 11:36 PM
 * Version: 1.0
 */

public class ReportUtil {
    public static ObservableList<AccountHolder> generateAccountHoldersReport() {
        ObservableList<AccountHolder> reportList = FXCollections.observableArrayList();

        ObservableList<AccountHolder> accountHoldersList = DatabaseOperations.getAllAccountHolders();

        // Iterate over accountHolders List
        for (AccountHolder accountHolder : accountHoldersList) {
            // get the account holder's name, debitOrCredit, and totalBalance, isRetailer
            String name = accountHolder.getName();
            String debitOrCredit = accountHolder.getDebitOrCredit();
            String totalBalance = accountHolder.getTotalBalance();
            boolean isRetailer = accountHolder.isRetailer();

            reportList.add(new AccountHolder(name, debitOrCredit, totalBalance, isRetailer));

        }
        return reportList;
    }

    public static ObservableList<Product> generateProductsReport() {
        ObservableList<Product> reportList = FXCollections.observableArrayList();

        ObservableList<Product> productsList = DatabaseOperations.fetchProductData("");

        // Iterate over products List
        for (Product product : productsList) {
            // get the product's name, price, quantity, and total
            String productName = product.getProductName();
            String productCode = product.getProductCode();
            double shopQuantity = product.getShopQuantity();
            double warehouseQuantity = product.getWarehouseQuantity();

            reportList.add(new Product(productName, productCode, shopQuantity, warehouseQuantity));

        }
        return reportList;
    }

    public static ObservableList<CompanyAccount> generateCompanyAccountReport() {
        ObservableList<CompanyAccount> accountData = DatabaseOperations.getAllCompanyAccounts();
        ObservableList<CompanyAccount> reportList = FXCollections.observableArrayList();

        for (CompanyAccount account : accountData) {
            String accountHolderName = account.getAccountHolderName();
            String bankName = account.getBankName();
            String totalBalance = account.getTotalBalance();

            reportList.add(new CompanyAccount(accountHolderName, bankName, totalBalance));
        }

        return accountData;
    }

    public static ObservableList<Booking> generateBookingsReport() {
        return DatabaseOperations.getBookingsReport();
    }
}
