package com.al_makkah_traders_app.utility;

import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.Booking;
import com.al_makkah_traders_app.model.CompanyAccount;
import com.al_makkah_traders_app.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Author: Adnan Rafique
 * Date: 4/14/2024
 * Time: 3:21 PM
 * Version: 1.0
 */

public class ReportTest {

    public static void generateExcelReport(List<AccountHolder> debitCreditItems, List<Product> stockItems,
                                           List<Booking> bookingItems, List<CompanyAccount> bankBalanceItems, File file) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        // Create sheets with valid names
        Sheet debitCreditSheet = workbook.createSheet("Debit_Credit");
        Sheet stockSheet = workbook.createSheet("Stock");
        Sheet bookingSheet = workbook.createSheet("Bookings");
        Sheet bankBalanceSheet = workbook.createSheet("Bank_Balances");

        // Populate sheets
        populateDebitCreditSheet(debitCreditSheet, debitCreditItems);
        populateStockSheet(stockSheet, stockItems);
        populateBookingSheet(bookingSheet, bookingItems);
        populateBankBalanceSheet(bankBalanceSheet, bankBalanceItems);

        // Write the output to the selected file
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    private static void populateDebitCreditSheet(Sheet sheet, List<AccountHolder> items) {
        String[] headers = {"Name", "Debit/Credit", "Balance"};
        createHeaderRow(sheet, headers);

        int rowNum = 1;
        for (AccountHolder item : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getName());
            row.createCell(1).setCellValue(item.getDebitOrCredit());
            row.createCell(2).setCellValue(item.getTotalBalance());
        }
    }

    private static void populateStockSheet(Sheet sheet, List<Product> items) {
        String[] headers = {"Product Code", "Product Name", "Shop Quantity", "Warehouse Quantity"};
        createHeaderRow(sheet, headers);

        int rowNum = 1;
        for (Product item : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getProductCode());
            row.createCell(1).setCellValue(item.getProductName());
            row.createCell(2).setCellValue(item.getShopQuantity());
            row.createCell(3).setCellValue(item.getWarehouseQuantity());
        }
    }

    private static void populateBookingSheet(Sheet sheet, List<Booking> items) {
        String[] headers = {"Ordered From", "Product Code", "Booking Type", "Ordered Q", "Left Quantity"};
        createHeaderRow(sheet, headers);

        int rowNum = 1;
        for (Booking item : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getOrderedFrom());
            row.createCell(1).setCellValue(item.getProductCode());
            row.createCell(2).setCellValue(item.getBookingType());
            row.createCell(3).setCellValue(item.getOrderedQuantity());
            row.createCell(4).setCellValue(item.getLeftQuantity());
        }
    }

    private static void populateBankBalanceSheet(Sheet sheet, List<CompanyAccount> items) {
        String[] headers = {"Bank Name", "Account Name", "Total Balance"};
        createHeaderRow(sheet, headers);

        int rowNum = 1;
        for (CompanyAccount item : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getBankName());
            row.createCell(1).setCellValue(item.getAccountHolderName());
            row.createCell(2).setCellValue(item.getTotalBalance());
        }
    }

    private static void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(sheet.getWorkbook()));
        }
    }

    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}