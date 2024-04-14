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

import static com.al_makkah_traders_app.utility.GenerateReport.getColumnText;

/**
 * Author: Adnan Rafique
 * Date: 4/14/2024
 * Time: 3:21 PM
 * Version: 1.0
 */

public class ReportTest {
    /**
     * Generate a Report That contains the following tables:
     * 1- Account Holders Table
     * 2- Products Table
     * 3- Bookings Table
     * 4- Bank Balance Table
     *
     * Page should be divided in 2 parts:
     * Account Holders Table should be placed on right side of the page
     * Products Table should be placed on left side of the page
     * Bookings Table should be below Products Table
     * Bank Balance Table should be below Bookings Table
     *
     * Account Holders Table have 3 Columns (Name, Debit/Credit, Balance) Name column should be 200px wide, Debit/Credit column should be 100px wide, Balance column should be 70px wide
     * Products Table have 4 Columns (Product Code, Product Name, Shop Quantity, Warehouse Quantity) Product Code column should be 100px wide, Product Name column should be 150px wide, Shop Quantity column should be 70 wide, Warehouse Quantity column should be 70px wide
     * Bookings Table have 5 Columns (Ordered From (200px Wide), Product Code(150px Wide), Booking Type (30px Wide), Ordered Q(20px Wide), Left Quantity(20px Wide))
     * Bank Balance Table have 3 Columns (Bank Name (200px Wide), Account Name (200px Wide), Total Balance (100px Wide))
     *
     * If the contents of the tables are too large to fit on a single page, the tables should be continued on the next page.
     * */

    private static final int PAGE_MARGIN = 50;
    private static final int ROW_HEIGHT = 20;
    private static final int HEADER_FONT_SIZE = 12;
    private static final int TEXT_FONT_SIZE = 10;

    public static void generateReport(List<AccountHolder> creditDebitItems, List<Product> stockTableItems,
                                      List<Booking> bookingTableItems, List<CompanyAccount> bankBalanceItems) throws IOException {
        PDDocument document = new PDDocument();

        try {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            writeHeader(contentStream);
            contentStream.close();  // Close stream after writing header

            int startY = 700;  // Starting position for tables

            // Starting new content stream for Account Holders Table
            PDPageContentStream newContentStream = new PDPageContentStream(document, page);
            startY = drawTable(document, page, newContentStream, PAGE_MARGIN, startY, "Account Holders", creditDebitItems,
                    new String[]{"Name", "Debit/Credit", "Balance"}, new int[]{200, 100, 70});
            newContentStream.close();

            // Starting new content stream for Products Table
            newContentStream = new PDPageContentStream(document, page);
            startY = drawTable(document, page, newContentStream, PAGE_MARGIN, startY, "Products", stockTableItems,
                    new String[]{"Product Code", "Product Name", "Shop Quantity", "Warehouse Quantity"}, new int[]{100, 150, 70, 70});
            newContentStream.close();

            // Starting new content stream for Bookings Table
            newContentStream = new PDPageContentStream(document, page);
            startY = drawTable(document, page, newContentStream, PAGE_MARGIN, startY, "Bookings", bookingTableItems,
                    new String[]{"Ordered From", "Product Code", "Booking Type", "Ordered Q", "Left Quantity"}, new int[]{200, 150, 30, 20, 20});
            newContentStream.close();

            // Starting new content stream for Bank Balances Table
            newContentStream = new PDPageContentStream(document, page);
            startY = drawTable(document, page, newContentStream, PAGE_MARGIN, startY, "Bank Balances", bankBalanceItems,
                    new String[]{"Bank Name", "Account Name", "Total Balance"}, new int[]{200, 200, 100});
            newContentStream.close();

            document.save("Report.pdf");
        } finally {
            if (document != null) {
                document.close();  // Always ensure resources are freed
            }
        }
    }

    private static void writeHeader(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, HEADER_FONT_SIZE);
        contentStream.newLineAtOffset(250, 750);
        contentStream.showText("Daily Report - " + LocalDate.now());
        contentStream.endText();
    }

    private static int drawTable(PDDocument document, PDPage page, PDPageContentStream contentStream, int startX, int startY,
                                 String tableName, List<?> items, String[] headers, int[] columnWidths) throws IOException {
        if (startY < 50) {  // Check if a new page is needed
            contentStream.close();  // Close the current stream
            page = new PDPage(PDRectangle.A4);  // Create a new page
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);  // Reinitialize content stream for the new page
        }

        // Set font before beginning to write text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, HEADER_FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, startY);
        contentStream.showText(tableName);
        contentStream.endText();

        // Ensure font settings are correctly applied before each new block of text
        startY -= ROW_HEIGHT;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, TEXT_FONT_SIZE);
        for (String header : headers) {
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(header);
            contentStream.endText();
            startX += columnWidths[headers.length - 1];  // Adjust for next header
        }

        startY -= ROW_HEIGHT;  // Move down to start data rows
        for (Object item : items) {
            startX = PAGE_MARGIN;
            for (int i = 0; i < headers.length; i++) {
                if (startY < 50) {  // Check if new page is needed within the data rows
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, TEXT_FONT_SIZE);  // Reset font for new page
                    startY = 650;  // Reset startY for the new page
                }
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText(getColumnText(item, i));  // Draw each piece of data
                contentStream.endText();
                startX += columnWidths[i];  // Move to next column position
            }
            startY -= ROW_HEIGHT;  // Move to next row
        }
        contentStream.close();  // Properly close the stream after all operations
        return startY;  // Return the Y-coordinate for potentially additional content
    }

}
