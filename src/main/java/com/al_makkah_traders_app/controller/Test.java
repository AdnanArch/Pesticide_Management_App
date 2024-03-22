package com.al_makkah_traders_app.controller;

import java.util.Scanner;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the number
        System.out.print("Enter a number: ");

        // Read the number entered by the user
        double number = scanner.nextDouble();

        // Create a DecimalFormat object with the desired format
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        // Format the number
        String formattedNumber = decimalFormat.format(number);

        // Print the formatted number
        System.out.println("Formatted number: " + formattedNumber);
    }
}
