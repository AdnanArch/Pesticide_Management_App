package com.al_makkah_traders_app.utility;

import com.al_makkah_traders_app.model.Cart;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillPrinter {
    private final ObservableList<Cart> cartItems;
    private final String totalBill;
    private final String receivedAmount;
    private final String user;
    private final String paymentMethod;
    private final String previousBalance;
    private final String netBalance;
    private final boolean isWalkInBill;
    private final boolean isAccountHolderBill;
    private final boolean isEmptyBill;
    private final String description;
    private final String billId;

    public BillPrinter(
            ObservableList<Cart> cartItems,
            String totalBill,
            String receivedAmount,
            String previousBalance,
            String netBalance,
            String user,
            String paymentMethod,
            boolean isWalkInBill,
            boolean isAccountHolderBill,
            boolean isEmptyBill,
            String description,
            String billId) {
        this.cartItems = cartItems;
        this.totalBill = totalBill;
        this.receivedAmount = receivedAmount;
        this.previousBalance = previousBalance;
        this.netBalance = netBalance;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.isWalkInBill = isWalkInBill;
        this.isAccountHolderBill = isAccountHolderBill;
        this.isEmptyBill = isEmptyBill;
        this.description = description;
        this.billId = billId;
    }

    public void printReceipt() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            float margin = 20;
            float yStart = page.getMediaBox().getHeight() - margin;
            float leading = 18f;
            float startX = page.getMediaBox().getLowerLeftX() + margin;
            float startY = yStart;
            float pageWidth = page.getMediaBox().getWidth();
            float center = pageWidth / 2;

            contentStream.setFont(PDType1Font.COURIER_BOLD, 14);
            String text = "Al-Makkah Traders";
            float titleWidth = PDType1Font.COURIER_BOLD.getStringWidth(text) / 1000 * 14;
            contentStream.beginText();
            contentStream.newLineAtOffset(center - (titleWidth / 2), startY);
            contentStream.showText(text);
            contentStream.endText();

            contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
            text = "GT Road Harappa Station";
            titleWidth = PDType1Font.COURIER_BOLD.getStringWidth(text) / 1000 * 10;
            contentStream.beginText();
            contentStream.newLineAtOffset(center - (titleWidth / 2), startY - leading);
            contentStream.showText(text);
            contentStream.endText();

            text = "Proprietor: Chaudhary Muhammad Naeem";
            titleWidth = PDType1Font.COURIER_BOLD.getStringWidth(text) / 1000 * 10;
            contentStream.beginText();
            contentStream.newLineAtOffset(center - (titleWidth / 2), startY - leading * 2);
            contentStream.showText(text);
            contentStream.endText();

            text = "0320-7787313, 040-4504423";
            titleWidth = PDType1Font.COURIER_BOLD.getStringWidth(text) / 1000 * 10;
            contentStream.beginText();
            contentStream.newLineAtOffset(center - (titleWidth / 2), startY - leading * 3);
            contentStream.showText(text);
            contentStream.endText();

            if (isEmptyBill) {
                text = "Empty Invoice";
            } else {
                text = "Sale Invoice";
            }

            contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
            titleWidth = PDType1Font.COURIER_BOLD.getStringWidth(text) / 1000 * 12;
            contentStream.beginText();
            contentStream.newLineAtOffset(center - (titleWidth / 2), startY - leading * 5);
            contentStream.showText(text);
            contentStream.endText();

            // Check and sanitize text to avoid unsupported characters
            String sanitizedText = sanitizeText(user);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
            contentStream.newLineAtOffset(startX, startY - leading * 6);
            contentStream.showText(String.format("Customer: %s", user));
            contentStream.endText();

            // Right-justify the Invoice No
            String invoiceText = String.format("Invoice No: %s", billId);
            float invoiceWidth = PDType1Font.COURIER_BOLD.getStringWidth(invoiceText) / 1000 * 10;
            contentStream.beginText();
            contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
            contentStream.newLineAtOffset(pageWidth - margin - invoiceWidth, startY - leading * 6); // Adjust Y-position as needed
            contentStream.showText(invoiceText);
            contentStream.endText();

            // Display Date
            contentStream.beginText();
            contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
            contentStream.newLineAtOffset(startX, startY - leading * 7); // Adjust Y-position as needed
            String dateText = String.format("Date: %s", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            contentStream.showText(dateText);
            contentStream.endText();


            startY -= leading * 7; // Additional spacing from "Sale Invoice"

            contentStream.moveTo(startX, startY - leading);
            contentStream.lineTo(pageWidth - margin, startY - leading);
            contentStream.stroke();

            if (isEmptyBill) {
                // Display Description between the horizontal lines
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                contentStream.newLineAtOffset(startX, startY - leading * 2);
                contentStream.showText(String.format("Description: %s", description));
                contentStream.endText();
            }

            if (!isEmptyBill) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                contentStream.newLineAtOffset(startX, startY - leading * 2);
                String header = String.format("%-2s %-25s %-20s %-15s %-15s %-20s", "Sr", "Product", "Brand", "Qty", "Rate", "Amount");
                contentStream.showText(header);
                contentStream.endText();
            }

            float currentY = startY - leading * 2.5f; // Adjusted for vertical spacing

            contentStream.moveTo(startX, startY - leading * 2.5f);
            contentStream.lineTo(pageWidth - margin, startY - leading * 2.5f);
            contentStream.stroke();

            if (!isEmptyBill) {
                currentY = startY - leading * 3.5f; // Adjusted for vertical spacing
                int srNo = 1;
                double totalQty = 0;
                double totalAmt = 0;
                float qtyWidth = PDType1Font.COURIER.getStringWidth("Qty") / 1000 * 10;
                float rateWidth = PDType1Font.COURIER.getStringWidth("Rate") / 1000 * 10;
                float amountWidth = PDType1Font.COURIER.getStringWidth("Amount") / 1000 * 10;
                float columnStartQty = startX + PDType1Font.COURIER.getStringWidth("Sr Product Brand") / 1000 * 10;
                float columnStartRate = columnStartQty + qtyWidth + 10; // 10 is for padding
                float columnStartAmount = columnStartRate + rateWidth + 10; // 10 is for padding

                for (Cart item : cartItems) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.COURIER, 10);
                    contentStream.newLineAtOffset(startX, currentY);
                    String itemDetails = String.format("%-2d %-25s %-20s %-15.1f %-15.2f %-20.2f", srNo++, item.getProductName(), item.getBrandName(), item.getQuantity(), item.getPricePerUnit(), item.getTotalPrice());
                    contentStream.showText(itemDetails);
                    contentStream.endText();
                    totalQty += item.getQuantity();
                    totalAmt += item.getTotalPrice();
                    currentY -= leading;
                }
                // Display Total Qty and Total Amount below their columns
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
                contentStream.newLineAtOffset(startX + 235, currentY - leading);
                contentStream.showText(String.format("Total Qty: %.2f", totalQty));
                contentStream.endText();
            }

            contentStream.moveTo(startX, currentY);
            contentStream.lineTo(pageWidth - margin, currentY);
            contentStream.stroke();

            String totalAmount = String.format("Amount: %.2f", Double.parseDouble(totalBill));
            float totalAmountWidth = PDType1Font.COURIER_BOLD.getStringWidth(totalAmount) / 1000 * 10;

            contentStream.beginText();
            contentStream.newLineAtOffset(pageWidth - margin - totalAmountWidth, currentY - leading);
            contentStream.showText(totalAmount);
            contentStream.endText();

            // Calculate rightmost point for the amounts
            float rightMost = pageWidth - margin;

            // Display Amount Received, Previous Balance, Net Balance, and Pay Mode right-aligned
            if(isEmptyBill){
                currentY -= leading;
            }

            if (isWalkInBill || isAccountHolderBill){
                currentY -= leading * 2; // Space between items and totals
                displayRightAlignedText(contentStream, rightMost, currentY, String.format("Amount Received: %.2f", Double.parseDouble(receivedAmount)));

            }
            currentY -= leading;

            if (isAccountHolderBill || isEmptyBill) {
                displayRightAlignedText(contentStream, rightMost, currentY, String.format("Previous Balance: %.2f", Double.parseDouble(previousBalance)));
                currentY -= leading;
                displayRightAlignedText(contentStream, rightMost, currentY, String.format("Net Balance: %.2f", Double.parseDouble(netBalance)));
                currentY -= leading;
            }
            displayRightAlignedText(contentStream, rightMost, currentY, String.format("Pay Mode: %s", paymentMethod));
            currentY -= leading;
            float gapAfterPayMode = currentY - leading * 2;

            // Center-align the "Thank You" message
            contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 14);
            String thankYouMessage = "We appreciate your business and hope to see you again soon!";
            float thankYouWidth = PDType1Font.TIMES_BOLD_ITALIC.getStringWidth(thankYouMessage) / 1000 * 14;
            contentStream.beginText();
            contentStream.newLineAtOffset(center - (thankYouWidth / 2), gapAfterPayMode);
            contentStream.showText(thankYouMessage);
            contentStream.endText();

            contentStream.close();

            // Printing
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            job.setPrintService(printService);
            job.setPageable(new PDFPageable(document));
            job.print();

        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayRightAlignedText(PDPageContentStream contentStream, float rightMost, float y, String text) throws IOException {
        float textWidth = PDType1Font.COURIER_BOLD.getStringWidth(text) / 1000 * 10;
        contentStream.beginText();
        contentStream.setFont(PDType1Font.COURIER_BOLD, 10);
        contentStream.newLineAtOffset(rightMost - textWidth, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private String sanitizeText(String text) {
        // Replace any unsupported characters with a space or remove them
        return text.replaceAll("[^\\x20-\\x7e]", ""); // Replace non-printable ASCII characters with space
    }
}