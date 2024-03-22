package com.al_makkah_traders_app.utility;

import java.text.DecimalFormat;

public class NumberFormatter {
    public static String formatWithCommas(double number) {
        // Create a DecimalFormat object with the desired format
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        // Format the number
        return decimalFormat.format(number);
    }
}
