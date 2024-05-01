package com.al_makkah_traders_app.database;

import com.al_makkah_traders_app.messages.MessageDialogs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class DatabaseBackup {
    public static void backupDatabase() {
        String databaseName = "al_makkah_db";
        String mysqlUsername = "root";
        String mysqlPassword = "admin";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Location to Save Backup");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("SQL Files", "sql"));
        fileChooser.setSelectedFile(new File(databaseName + "_backup.sql"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String backupFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            executeBackup(databaseName, mysqlUsername, mysqlPassword, backupFilePath);
        }
    }

    private static void executeBackup(String databaseName, String mysqlUsername, String mysqlPassword, String backupFilePath) {
        String mysqldumpPath = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump"; // Full path to mysqldump

        ProcessBuilder processBuilder = new ProcessBuilder(
                mysqldumpPath,
                "-u" + mysqlUsername,
                "-p" + mysqlPassword,
                "--routines",
                "--triggers",
                "--add-drop-database",
                "--single-transaction",
                databaseName,
                "--result-file=" + backupFilePath // Using --result-file to specify the output file
        );

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                MessageDialogs.showMessageDialog("Backup completed successfully.");
            } else {
                MessageDialogs.showErrorMessage("Backup failed.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
