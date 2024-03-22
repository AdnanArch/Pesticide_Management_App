package com.al_makkah_traders_app.database;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RestoreDatabase extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Database Restore");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select MySQL Database File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SQL Files", "*.sql"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            // Load and run the SQL script
            restoreDatabase(selectedFile);
        }
    }

    private void restoreDatabase(File sqlFile) {
        try {
            // JDBC connection parameters
            String url = "jdbc:mysql://localhost:3306/"; // Update with your database URL
            String username = "root"; // Update with your database username
            String password = "admin"; // Update with your database password

            // Name of the database to create
            String databaseName = "al_makkah_db";

            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                try (Connection connection = DriverManager.getConnection(url, username, password)) {

                    // Create a statement
                    try (Statement statement = connection.createStatement()) {

                        // Create the database
                        statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);

                        System.out.println("Database created successfully: " + databaseName);
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.err.println("Error creating database: " + e.getMessage());
            }

            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (Connection connection = DriverManager.getConnection(url, username, password)) {

                // Create a statement
                try (Statement statement = connection.createStatement()) {

                    // Specify the database to use
                    statement.execute("USE " + databaseName);

                    // Read the SQL script from the file
                    StringBuilder sqlScript = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Skip comments
                            if (!line.trim().startsWith("--") && !line.trim().startsWith("/*")) {
                                sqlScript.append(line).append("\n");
                            }
                        }
                    }

                    // Execute the SQL script
                    statement.execute(sqlScript.toString());

                    System.out.println("Database restored successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error restoring database: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
