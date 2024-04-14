package com.al_makkah_traders_app.utility;

import com.al_makkah_traders_app.model.AccountHolder;
import com.al_makkah_traders_app.model.Booking;
import com.al_makkah_traders_app.model.CompanyAccount;
import com.al_makkah_traders_app.model.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * This class is responsible for generating a daily report in PDF format.
 */
public class GenerateReport {

    private static final int PAGE_MARGIN = 50;
    private static final int ROW_HEIGHT = 20;
    private static final int HEADER_FONT_SIZE = 12;
    private static final int TEXT_FONT_SIZE = 10;

    /**
     * This method generates a daily report in PDF format.
     * It takes lists of different types of items and generates a table for each type.
     * Each table includes a header row and one row for each item in the list.
     * The tables are positioned on the page in the order they are passed to the method.
     * If there is not enough space on the current page for a table, a new page is added.
     *
     * @param creditDebitItems a list of credit/debit items
     * @param stockTableItems a list of stock table items
     * @param bookingTableItems a list of booking table items
     * @param bankBalanceItems a list of bank balance items
     * @throws IOException if an I/O error occurs
     */
    public static void generateReport(List<AccountHolder> creditDebitItems, List<Product> stockTableItems,
                                      List<Booking> bookingTableItems, List<CompanyAccount> bankBalanceItems) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            writeHeader(contentStream);
            contentStream.close();  // Close the stream after the header to prevent drawing on a closed stream.

            int startY = 700;

            startY = drawTable(document, PAGE_MARGIN, startY, "Credit/Debit", creditDebitItems, new String[]{"Name", "Debit/Credit", "Balance"}, new int[]{100, 100, 100});
            startY = drawTable(document, PAGE_MARGIN, startY, "Stock", stockTableItems, new String[]{"Product Code", "Product Name", "Shop Quantity", "Warehouse Quantity"}, new int[]{100, 100, 100, 100});
            startY = drawTable(document, PAGE_MARGIN, startY, "Bookings", bookingTableItems, new String[]{"Booking Code", "Booking Name", "Booking Quantity", "Booking Balance"}, new int[]{100, 100, 100, 100});
            drawTable(document, PAGE_MARGIN, startY, "Bank Balances", bankBalanceItems, new String[]{"Account Holder Name", "Bank Name", "Total Balance"}, new int[]{100, 100, 100});

            document.save("Report.pdf");
        }
    }

    /**
     * This method writes the header of the report.
     *
     * @param contentStream the content stream to write to
     * @throws IOException if an I/O error occurs
     */
    private static void writeHeader(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.beginText();
        contentStream.newLineAtOffset(250, 750);
        contentStream.showText("Daily Report");
        contentStream.endText();

        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(450, 750);
        contentStream.showText(LocalDate.now().toString());
        contentStream.endText();
    }

    /**
     * This method draws a table on a page.
     * It takes a list of items and generates a table with a header row and one row for each item.
     * The table is positioned at the specified coordinates.
     * If there is not enough space on the current page for the table, a new page is added.
     *
     * @param document the document to add the table to
     * @param startX the x-coordinate of the top-left corner of the table
     * @param startY the y-coordinate of the top-left corner of the table
     * @param tableName the name of the table
     * @param items the items to include in the table
     * @param headers the headers of the table
     * @param columnWidths the widths of the columns
     * @return the y-coordinate of the bottom of the table
     * @throws IOException if an I/O error occurs
     */
    private static int drawTable(PDDocument document, int startX, int startY, String tableName, List<?> items, String[] headers, int[] columnWidths) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, HEADER_FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, startY);
        contentStream.showText(tableName);
        contentStream.endText();
        startY -= ROW_HEIGHT + 15;

        int currentX = startX;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, TEXT_FONT_SIZE);
        for (String header : headers) {
            contentStream.beginText();
            contentStream.newLineAtOffset(currentX, startY);
            contentStream.showText(header);
            contentStream.endText();
            currentX += columnWidths[headers.length - 1];
        }
        startY -= ROW_HEIGHT;

        for (Object item : items) {
            currentX = startX;
            for (int i = 0; i < headers.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(currentX, startY);
                contentStream.showText(getColumnText(item, i));
                contentStream.endText();
                currentX += columnWidths[i];
            }
            startY -= ROW_HEIGHT;
        }
        contentStream.close();
        return startY;
    }


    /**
     * This method returns the text to display in a cell of a table.
     * It takes an item and a column index and returns the appropriate text based on the type of the item and the column.
     *
     * @param item the item to display in the cell
     * @param column the index of the column
     * @return the text to display in the cell
     */
    static String getColumnText(Object item, int column) {
        if (item instanceof Product product) {
            return switch (column) {
                case 0 -> product.getProductCode();
                case 1 -> product.getProductName();
                case 2 -> String.valueOf(product.getShopQuantity());
                case 3 -> String.valueOf(product.getWarehouseQuantity());
                default -> "";
            };
        } else if (item instanceof AccountHolder accountHolder) {
            return switch (column) {
                case 0 -> accountHolder.getName();
                case 1 -> accountHolder.getDebitOrCredit();
                case 2 -> accountHolder.getTotalBalance();
                default -> "";
            };
        } else if (item instanceof Booking booking) {
            return switch (column) {
                case 0 -> booking.getProductCode();
                case 1 -> booking.getOrderedFrom();
                case 2 -> String.valueOf(booking.getOrderedQuantity());
                case 3 -> String.valueOf(booking.getLeftQuantity());
                case 4 -> booking.getBookingType();
                default -> "";
            };
        } else if (item instanceof CompanyAccount companyAccount) {
            return switch (column) {
                case 0 -> companyAccount.getAccountHolderName();
                case 1 -> companyAccount.getBankName();
                case 2 -> companyAccount.getTotalBalance();
                default -> "";
            };
        }
        return "";
    }

}