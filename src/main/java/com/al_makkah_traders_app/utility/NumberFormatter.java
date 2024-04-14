package com.al_makkah_traders_app.utility;

import java.text.DecimalFormat;

public class NumberFormatter {
    public static String formatWithCommas(double number) {
        // Create a DecimalFormat object with the desired format
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        // Format the number
        return decimalFormat.format(number);
    }

    public static double removeCommas(String numberString) {
        // Remove commas from the string
        String cleanedString = numberString.replace(",", "");

        // Parse the cleaned string as a double
        return Double.parseDouble(cleanedString);
    }
}
