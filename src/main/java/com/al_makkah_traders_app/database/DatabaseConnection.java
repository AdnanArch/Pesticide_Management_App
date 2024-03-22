package com.al_makkah_traders_app.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    private static final String DB_URL = "jdbc:mysql://localhost:3306/al_makkah_db?characterEncoding=latin1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    private final Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC driver not found.", e);
            throw new RuntimeException("MySQL JDBC driver not found.", e);
        } catch (SQLException e) {
            logger.error("Database connection failed.", e);
            throw new RuntimeException("Database connection failed.", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error while closing the database connection.", e);
        }
    }
}
