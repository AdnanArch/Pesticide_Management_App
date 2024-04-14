package com.al_makkah_traders_app.utility;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static StringConverter<LocalDate> getDateStringConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    public static String formatDate(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    public static String formatDate(String dateString) {
        try {
            if (dateString != null && !dateString.isEmpty()) {
                LocalDate date = LocalDate.parse(dateString, dateFormatter);
                return dateFormatter.format(date);
            } else {
                return "";
            }
        } catch (DateTimeParseException e) {
            // Log the error or handle it gracefully
            System.err.println("Error parsing date: " + dateString);
            e.printStackTrace();
            return ""; // Return a default value or handle the error as needed
        }
    }

}

