package com.al_makkah_traders_app.utility;

import com.al_makkah_traders_app.model.Cart;
import com.al_makkah_traders_app.model.StockTransferCart;
import com.google.gson.Gson;
import javafx.collections.ObservableList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static String extractAccountNumber(String inputString) {
        // Define the regular expression pattern for the account number
//        String regex = "^[\\d-]*";

//        String regex = "([\\d-]+)\\s*->";
//        // Create a Pattern object
//        Pattern pattern = Pattern.compile(regex);
//
//        // Create a Matcher object
//        Matcher matcher = pattern.matcher(inputString);
//
//        // Find the first occurrence of the pattern
//        if (matcher.find()) {
//            // Return the matched account number
//            return matcher.group(0);
//        } else {
//            // If no match is found, return an empty string or handle the error as required
//            return "";
//        }
        String[] strings = inputString.split(" -> ");
        return strings[0].trim();
    }

    public static String extractProductCodeForBill(String inputString) {
        String[] strings = inputString.split(" -> ");
        return strings[1].trim();
    }
    /**
     * Converts an ObservableList of Cart items to a JSON String.
     *
     * @param cartItems The list of cart items to be converted.
     * @return The JSON representation of the cart items.
     */
    public static String toJsonString(ObservableList<Cart> cartItems) {
        Gson gson = new Gson();
        return gson.toJson(cartItems);
    }

    /**
     * Converts an ObservableList of Cart items to a JSON String.
     *
     * @param cartItems The list of cart items to be converted.
     * @return The JSON representation of the cart items.
     */
    public static String toJsonStockTransferString(ObservableList<StockTransferCart> cartItems) {
        Gson gson = new Gson();
        return gson.toJson(cartItems);
    }

    /**
     * Converts a ObservableList of Cart items to a Description String Variable.
     *
     * @param cartItems The list of cart items to be converted.
     * @return The Description representation of the cart items.
     */
    public static String toDescriptionString(ObservableList<Cart> cartItems) {
        String description = "";
        for (Cart cartItem : cartItems) {
            description += cartItem.getProductName() + " Q " + cartItem.getQuantity() + " @ "
                    + cartItem.getPricePerUnit() + ", ";
        }
        // remove the last comma
        description = description.substring(0, description.length() - 2);
        return description;
    }

    // Helper method to check if a string is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String extractProductCode(String productString) {
        // Define the regular expression pattern for the product code extraction
        String regex = "\\(\\s*([A-Za-z0-9\\-]+)\\s*\\)";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(productString);

        // Find the first occurrence of the pattern
        if (matcher.find()) {
            // Return the matched product code (group 1) without parentheses and trimmed
            return matcher.group(1).trim();
        } else {
            // If no match is found, return an empty string or handle the error as required
            return "";
        }
    }

    public static String extractPaymentMode(String paymentMethod) {
//        // Define the regular expression pattern for the payment mode extraction "0342-1234567 -> JazzCash"
//        // JazzCash, SadaPay, MBL should be extracted
//        String regex = "\\s*->\\s*([A-Za-z0-9\\-]+)\\s*";
//
//        // Create a Pattern object
//        Pattern pattern = Pattern.compile(regex);
//
//        // Create a Matcher object
//        Matcher matcher = pattern.matcher(paymentMethod);
//
//        // Find the first occurrence of the pattern
//        if (matcher.find()) {
//            // Return the matched product code (group 1) without parentheses and trimmed
//            return matcher.group(1).trim();
//        } else {
//            // If no match is found, return an empty string or handle the error as required
//            return "";
//        }
//    }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            return ""; // or handle the null case appropriately
        }
        String[] strings = paymentMethod.split(" -> ");
        String paymentMode =  strings[1].trim();
        return paymentMode;
    }
}
