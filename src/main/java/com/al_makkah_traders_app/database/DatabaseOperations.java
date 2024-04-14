package com.al_makkah_traders_app.database;

import com.al_makkah_traders_app.model.*;
import com.al_makkah_traders_app.utility.BillCreationResult;
import com.al_makkah_traders_app.utility.DateFormatter;
import com.al_makkah_traders_app.utility.NumberFormatter;
import com.al_makkah_traders_app.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class provides all kinds of database operations for managing company
 * accounts and admin login etc.
 */
public class DatabaseOperations {
    private static final Logger logger = LogManager.getLogger(DatabaseOperations.class);

    /**
     * Adds a new company account to the database.
     *
     * @param accountNo         The account number.
     * @param accountHolderName The account holder's name.
     * @param bankName          The bank name.
     * @param balance           The account balance.
     * @return True if the account was added successfully; otherwise, false.
     */
    public static boolean addCompanyAccount(String accountNo, String accountHolderName, String bankName,
                                            String balance) {
        boolean result = false; // Initialize the result to false

        DatabaseConnection db = new DatabaseConnection();

        try {
            CallableStatement statement = db.getConnection()
                    .prepareCall("CALL sp_create_company_account(?, ?, ?, ?, ?)");
            statement.setString(1, accountNo);
            statement.setString(2, accountHolderName);
            statement.setString(3, bankName);
            statement.setDouble(4, Double.parseDouble(balance));
            statement.registerOutParameter(5, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(5);

            statement.close();
            db.close();

            if (result) {
                logger.info("Account added successfully: AccountNo - {}", accountNo);
            } else {
                logger.error("Failed to add account: AccountNo - {}", accountNo);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while adding the account: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while adding the account: {}", e.getMessage(), e);
        }

        return result; // Return the result.
    }

    /**
     * Deletes a company account from the database.
     *
     * @param accountNo The account number to delete.
     * @return True if the account was deleted successfully; otherwise, false.
     */
    public static boolean deleteCompanyAccount(String accountNo) {
        boolean result = false; // Initialize the result to false

        DatabaseConnection db = new DatabaseConnection();

        try {
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_delete_company_account(?, ?)");

            statement.setString(1, accountNo);
            statement.registerOutParameter(2, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(2);

            statement.close();
            db.close();

            if (result) {
                logger.info("Account deleted successfully: AccountNo - {}", accountNo);
            } else {
                logger.error("Failed to delete account: AccountNo - {}", accountNo);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting the account: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while deleting the account: {}", e.getMessage(), e);
        }

        return result; // Return the result at the end
    }

    /**
     * Searches for company accounts in the database based on a search text.
     *
     * @param searchText The text to search for in account details.
     * @return An ObservableList of CompanyAccount objects matching the search
     * criteria.
     */
    public static ObservableList<CompanyAccount> searchCompanyAccounts(String searchText) {
        ObservableList<CompanyAccount> searchResults = FXCollections.observableArrayList();
        DatabaseConnection db = new DatabaseConnection();

        try {
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_search_company_accounts(?)");
            statement.setString(1, searchText);

            boolean hasResults = statement.execute();

            while (hasResults) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    CompanyAccount account = new CompanyAccount(
                            resultSet.getString("account_no"),
                            resultSet.getString("account_name"),
                            resultSet.getString("bank_name"),
                            NumberFormatter.formatWithCommas(resultSet.getDouble("balance")));

                    searchResults.add(account);
                }

                hasResults = statement.getMoreResults();
            }

            statement.close();
            db.close();

            logger.info("Company accounts searched successfully for: {}", searchText);

        } catch (SQLException e) {
            logger.error("An error occurred while searching for accounts: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while searching for accounts: {}", e.getMessage(), e);
        }

        return searchResults;
    }

    /**
     * Updates an existing company account in the database.
     *
     * @param accountNo         The account number.
     * @param accountHolderName The account holder's name.
     * @param bankName          The bank name.
     * @param balance           The new account balance.
     * @return True if the account was updated successfully; otherwise, false.
     */
    public static boolean updateCompanyAccount(String accountNo, String accountHolderName, String bankName,
                                               double balance) {
        DatabaseConnection db = new DatabaseConnection();
        boolean result = false;

        try {
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_update_company_account(?,?,?,?)");
            statement.setString(1, accountNo);
            statement.setString(2, accountHolderName);
            statement.setString(3, bankName);
            statement.setDouble(4, balance);

            int rowsUpdated = statement.executeUpdate();

            statement.close();
            db.close();

            result = rowsUpdated > 0;

            if (result) {
                logger.info("Account updated successfully: AccountNo - {}", accountNo);
            } else {
                logger.error("Failed to update account: AccountNo - {}", accountNo);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while updating the account: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating the account: {}", e.getMessage(), e);
        }

        return result;
    }

    /**
     * Retrieves all company accounts from the database.
     *
     * @return An ObservableList of all CompanyAccount objects.
     */
    public static ObservableList<CompanyAccount> getAllCompanyAccounts() {
        ObservableList<CompanyAccount> companyAccounts = FXCollections.observableArrayList();
        DatabaseConnection db = new DatabaseConnection();

        try {
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_all_company_accounts()");

            boolean hasResults = statement.execute();

            while (hasResults) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    CompanyAccount account = new CompanyAccount(
                            resultSet.getString("account_no"),
                            resultSet.getString("account_name"),
                            resultSet.getString("bank_name"),
                            NumberFormatter.formatWithCommas(resultSet.getDouble("balance")));
                    companyAccounts.add(account);
                }

                hasResults = statement.getMoreResults();
            }

            statement.close();
            db.close();

            logger.info("Retrieved all company accounts successfully.");

        } catch (SQLException e) {
            logger.error("An error occurred while retrieving all company accounts: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving all company accounts: {}", e.getMessage(), e);
        }

        return companyAccounts;
    }

    /**
     * Checks if an admin user can log in with the provided username and password.
     *
     * @param username The admin username.
     * @param password The admin password.
     * @return True if the login is successful; otherwise, false.
     */
    public static boolean checkAdminLogin(String username, String password) {
        DatabaseConnection db = new DatabaseConnection();
        try {
            CallableStatement statement = db.getConnection().prepareCall("{call sp_admin_login(?, ?, ?)}");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.registerOutParameter(3, Types.BOOLEAN);

            statement.execute();
            boolean result = statement.getBoolean(3);

            statement.close();
            db.close();

            if (result) {
                logger.info("Admin login successful for user: {}", username);
            } else {
                logger.warn("Admin login failed for user: {}", username);
            }

            return result;
        } catch (SQLException e) {
            logger.error("SQL error occurred during admin login check: {}", e.getMessage(), e);
            return false; // Return false to indicate login failure
        } catch (Exception e) {
            logger.error("An error occurred during admin login check: {}", e.getMessage(), e);
            return false; // Return false to indicate login failure
        }
    }

    /**
     * Retrieves all account holders from the database.
     *
     * @return An ObservableList of AccountHolder objects.
     */
    public static ObservableList<AccountHolder> getAllAccountHolders() {
        String query = "CALL sp_get_all_account_holders_info()";
        return fetchDataFromDatabase(query);
    }

    /**
     * Searches for account holders in the database based on the given search text.
     *
     * @param searchText The search text.
     * @return An ObservableList of AccountHolder objects matching the search.
     */
    public static ObservableList<AccountHolder> searchAccountHolders(String searchText) {
        String query = "CALL sp_search_account_holder(?)";
        return fetchDataFromDatabase(query, searchText);
    }

    /**
     * Creates a new account holder in the database.
     *
     * @param name       The name of the account holder.
     * @param cnic       The CNIC number of the account holder.
     * @param address    The address of the account holder.
     * @param phone      The phone number of the account holder.
     * @param isRetailer True if the account holder is a retailer, otherwise false.
     * @return A message indicating the result of the operation.
     */
    public static boolean createAccountHolder(String name, String cnic, String address, String phone,
                                              boolean isRetailer, String balance) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("CALL sp_create_account_holder(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setBigDecimal(2, BigDecimal.valueOf(Long.parseLong(cnic)));
            statement.setString(3, address);
            statement.setString(4, phone);
            statement.setBoolean(5, isRetailer);
            statement.setDouble(6, Double.parseDouble(balance));
            statement.registerOutParameter(7, Types.BOOLEAN);

            statement.execute();

            boolean success = statement.getBoolean(7);

            statement.close();
            db.close();

            if (success) {
                logger.info("Created new account holder: {}", name);
            } else {
                logger.error("Failed to create account holder: {}", name);
            }

            return success;
        } catch (SQLException e) {
            logger.error("SQL error occurred while creating account holder: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("An error occurred while creating account holder: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Updates account holder information in the database.
     *
     * @param name         The name of the account holder.
     * @param cnic         The CNIC number of the account holder.
     * @param address      The address of the account holder.
     * @param contact      The contact number of the account holder.
     * @param isWholesaler True if the account holder is a wholesaler, otherwise
     *                     false.
     * @return True if the update was successful, otherwise false.
     */
    public static boolean updateAccountHolderInfo(String name, String cnic, String address, String contact,
                                                  boolean isWholesaler, String balance) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("CALL sp_update_account_holder_info(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setBigDecimal(2, BigDecimal.valueOf(Long.parseLong(cnic)));
            statement.setString(3, address);
            statement.setString(4, contact);
            statement.setBoolean(5, isWholesaler);
            statement.setDouble(6, Double.parseDouble(balance));
            statement.registerOutParameter(7, Types.BOOLEAN);

            statement.execute();

            boolean updated = statement.getBoolean(7);

            statement.close();
            db.close();

            if (updated) {
                logger.info("Updated account holder information: {}", name);
            } else {
                logger.warn("Failed to update account holder information: {}", name);
            }

            return updated;
        } catch (SQLException e) {
            logger.error("SQL error occurred while updating account holder information: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("An error occurred while updating account holder information: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an account holder from the database.
     *
     * @param cnicNo The CNIC number of the account holder to be deleted.
     * @return True if the deletion was successful, otherwise false.
     */
    public static boolean deleteAccountHolder(String cnicNo) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_delete_account_holder(?, ?)");
            statement.setBigDecimal(1, BigDecimal.valueOf(Long.parseLong(cnicNo)));
            statement.registerOutParameter(2, Types.BOOLEAN);
            statement.execute();

            boolean deleted = statement.getBoolean(2);

            statement.close();
            db.close();

            if (deleted) {
                logger.info("Deleted account holder with CNIC: {}", cnicNo);
            } else {
                logger.warn("Failed to delete account holder with CNIC: {}", cnicNo);
            }

            return deleted;
        } catch (SQLException e) {
            logger.error("SQL error occurred while deleting account holder: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("An error occurred while deleting account holder: {}", e.getMessage());
            return false;
        }
    }

    private static ObservableList<AccountHolder> fetchDataFromDatabase(String query, String... params) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall(query);

            if (params.length > 0) {
                statement.setString(1, params[0]);
            }

            ObservableList<AccountHolder> accountHoldersData = FXCollections.observableArrayList();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("holder_name");
                String cnicNo = resultSet.getString("cnic_no");
                String address = resultSet.getString("address");
                String contact = resultSet.getString("contact");
                String type = resultSet.getString("transaction_type");
                double balance = resultSet.getDouble("balance");
                boolean isRetailer = resultSet.getBoolean("is_retailer");
                // Add each row to the ObservableList
                accountHoldersData.add(
                        new AccountHolder(
                                name,
                                cnicNo,
                                address,
                                contact,
                                type,
                                NumberFormatter.formatWithCommas(balance),
                                isRetailer
                        )
                );
            }

            statement.close();
            db.close();

            return accountHoldersData;
        } catch (SQLException e) {
            logger.error("SQL error occurred while fetching data from the database: {}", e.getMessage());
            return FXCollections.observableArrayList(); // Handle the error gracefully
        } catch (Exception e) {
            logger.error("An error occurred while fetching data from the database: {}", e.getMessage());
            return FXCollections.observableArrayList(); // Handle the error gracefully
        }
    }

    /**
     * Inserts a list of account holders into the database.
     *
     * @param accountHolders The list of account holders to insert.
     * @return {@code true} if all account holders were inserted successfully,
     * {@code false} otherwise.
     */
    public static boolean insertAccountHolders(ObservableList<AccountHolder> accountHolders) {
        boolean allRecordsInserted = true;

        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_create_account_holder_by_excel(?, ?, ?, ?, ?, ?, ?)}");

            for (AccountHolder accountHolder : accountHolders) {
                // Set the input parameters for the stored procedure
                statement.setString(1, accountHolder.getName());
                String cnicNo = accountHolder.getCnicNo().replaceAll("-", "");
                statement.setBigDecimal(2, BigDecimal.valueOf(Long.parseLong(cnicNo)));
                statement.setString(3, accountHolder.getAddress());
                statement.setString(4, accountHolder.getPhone());
                statement.setBoolean(5, accountHolder.isRetailer()); // corrected method name
                String balance = accountHolder.getTotalBalance();
                statement.setDouble(6, NumberFormatter.removeCommas(balance));

                statement.registerOutParameter(7, Types.BOOLEAN);

                // Execute the stored procedure
                statement.execute();

                // Retrieve the result message
                boolean resultMessage = statement.getBoolean(7);

                if (!resultMessage) {
                    allRecordsInserted = false;
                }
            }

            // Close the database connection and statement
            db.close(); // Changed from db.getConnection().close()
            statement.close();
        } catch (SQLException e) {
            logger.error("SQL Error inserting account holders: " + e.getMessage(), e);
            allRecordsInserted = false;
        } catch (Exception e) {
            logger.error("An Unexpected Error inserting account holders: " + e.getMessage(), e);
            allRecordsInserted = false;
        }

        return allRecordsInserted;
    }

    /**
     * Inserts a list of products into the database.
     *
     * @param products The list of products to insert.
     * @return {@code true} if all products were inserted successfully,
     * {@code false} otherwise.
     */
    public static boolean insertProducts(ObservableList<Product> products) {
        boolean allRecordsInserted = true;

        try {
            for (Product product : products) {
                DatabaseConnection db = new DatabaseConnection();
                CallableStatement statement = db.getConnection()
                        .prepareCall("{CALL sp_add_new_product(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

                // Set the input parameters for the stored procedure
                statement.setString(1, product.getProductCode());
                statement.setString(2, product.getProductName());
                statement.setString(3, product.getCategoryName());
                statement.setString(4, product.getBrandName());
                statement.setDouble(5, product.getPrice());
                statement.setString(6, product.getCompanyName());
                statement.setString(7, product.getAddress());
                statement.setString(8, product.getContact());
                statement.setString(9, String.valueOf(product.getWarehouseQuantity()));
                statement.setString(10, String.valueOf(product.getShopQuantity()));

                statement.registerOutParameter(11, Types.BOOLEAN);

                // Execute the stored procedure
                statement.execute();

                // Retrieve the result (error or success message)
                boolean result = statement.getBoolean(11);

                // Close the database connection and statement
                db.close();
                statement.close();

                if (!result) {
                    allRecordsInserted = false;
                }
            }

            if (allRecordsInserted) {
                logger.info("All products inserted successfully.");
            } else {
                logger.error("Error inserting one or more products.");
            }
        } catch (SQLException e) {
            logger.error("SQL error occurred while inserting products: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inserting product(s): " + e.getMessage(), e);
        }

        return allRecordsInserted;
    }

    /**
     * Adds a new product to the database.
     *
     * @param productCode       The product code.
     * @param productName       The product name.
     * @param categoryName      The product category.
     * @param brandName         The brand name.
     * @param price             The price per unit.
     * @param companyName       The company name.
     * @param address           The company address.
     * @param contact           The company contact.
     * @param warehouseQuantity The warehouse quantity.
     * @param shopQuantity      The shop quantity.
     * @return True if the product was added successfully, false otherwise.
     */
    public static boolean addNewProduct(String productCode, String productName, String categoryName,
                                        String brandName, String price, String companyName,
                                        String address, String contact, double warehouseQuantity,
                                        double shopQuantity) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_add_new_product(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            // Set the input parameters for the stored procedure
            statement.setString(1, productCode);
            statement.setString(2, productName);
            statement.setString(3, categoryName);
            statement.setString(4, brandName);
            statement.setString(5, price);
            statement.setString(6, companyName);
            statement.setString(7, address);
            statement.setString(8, contact);
            statement.setString(9, String.valueOf(warehouseQuantity));
            statement.setString(10, String.valueOf(shopQuantity));
            statement.registerOutParameter(11, Types.BOOLEAN);

            // Execute the stored procedure
            statement.execute();

            // Retrieve the result (error or success message)
            boolean result = statement.getBoolean(11);

            statement.close();
            db.close();

            return result;
        } catch (SQLException e) {
            logger.error("Error adding a new product: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Fetches product data from the database based on a search parameter.
     *
     * @param searchParam The search parameter for the query.
     * @return An ObservableList containing fetched product data.
     */
    public static ObservableList<Product> fetchProductData(String searchParam) {
        String query = "CALL sp_search_product(?)";

        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall(query);
            statement.setString(1, searchParam);

            ObservableList<Product> productsData = FXCollections.observableArrayList();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                String productName = resultSet.getString("product_name");
                String category = resultSet.getString("category");
                String brand = resultSet.getString("brand");
                String company = resultSet.getString("company");
                double price = resultSet.getDouble("price");
                double warehouseQuantity = resultSet.getDouble("warehouse_quantity");
                double shopQuantity = resultSet.getDouble("shop_quantity");
                // Add each row to the ObservableList
                productsData.add(new Product(productCode, productName, category, brand, company, price,
                        warehouseQuantity, shopQuantity));
            }

            statement.close();
            db.close();

            return productsData;
        } catch (SQLException e) {
            logger.error("Error fetching product data: " + e.getMessage(), e);
            return FXCollections.observableArrayList(); // Handle the error gracefully
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage(), e);
            return FXCollections.observableArrayList(); // Handle the error gracefully
        }
    }

    /**
     * Deletes a product from the database.
     *
     * @param productCode The product code to delete.
     * @return True if the product was deleted successfully, false otherwise.
     */
    public static boolean deleteProduct(String productCode) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_delete_product(?, ?)");
            statement.setString(1, productCode);
            statement.registerOutParameter(2, Types.BOOLEAN);
            statement.execute();

            boolean deleted = statement.getBoolean(2);

            statement.close();
            db.close();

            return deleted;
        } catch (SQLException e) {
            logger.error("Error deleting a product: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Updates product information in the database.
     *
     * @param productCode  The product code to update.
     * @param productName  The new product name.
     * @param categoryName The new category name.
     * @param brandName    The new brand name.
     * @param price        The new price.
     * @param companyName  The new company name.
     * @param address      The new address.
     * @param contact      The new contact.
     * @return True if the product information was updated successfully, false
     * otherwise.
     */
    public static boolean updateProductInfo(
            String productCode,
            String productName,
            String categoryName,
            String brandName,
            String price,
            String companyName,
            String address,
            String contact) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("CALL sp_update_product_info(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, productCode);
            statement.setString(2, productName);
            statement.setString(3, categoryName);
            statement.setString(4, brandName);
            statement.setString(5, price);
            statement.setString(6, companyName);
            statement.setString(7, address);
            statement.setString(8, contact);
            statement.registerOutParameter(9, Types.BOOLEAN);

            statement.execute();

            boolean updated = statement.getBoolean(9);

            statement.close();
            db.close();

            return updated;
        } catch (SQLException e) {
            logger.error("Error updating product information: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Retrieves the products codes from the database.
     *
     * @return An ObservableList containing products codes fetched from the
     * database.
     */
    public static ObservableList<String> getProductsCodes() {
        ObservableList<String> productsCodes = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_products_codes()");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                productsCodes.add(productCode);
            }

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("Error occurred while fetching the products codes from database." + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching the data from the database." + e.getMessage(), e);
        }
        return productsCodes;
    }

    /**
     * Retrieves all wholesalers/retailers names from the database.
     *
     * @return An ObservableList containing wholesalers/retailers names from the
     * database.
     */
    public static ObservableList<String> getWholesalersNames() {
        ObservableList<String> wholesalersNames = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();

            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_wholesalers_names()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("holder_name");
                wholesalersNames.add(name);
            }

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("Error occurred while fetching the wholesalers names from the database." + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected error occurred while fetching wholesalers names." + e.getMessage(), e);
        }
        return wholesalersNames;
    }

    /**
     * Retrieves product's name, brand, company and its price from the database
     * based on product code.
     *
     * @param productCode The product's code for which data is fetched from
     *                    database.
     * @return An ArrayList of type string that holds the product's name, brand,
     * company, price.
     */
    public static ArrayList<String> getProductDetails(String productCode) {
        ArrayList<String> productData = new ArrayList<>();
        try {
            DatabaseConnection db = new DatabaseConnection();

            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_product_data(?)");

            statement.setString(1, productCode);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                productData.add(resultSet.getString("product_name"));
                productData.add(resultSet.getString("brand"));
                productData.add(resultSet.getString("company"));
                productData.add(resultSet.getString("price"));
            }

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("Error occurred while fetching the product data." + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching the data." + e.getMessage(), e);
        }
        return productData;
    }

    /**
     * Retrieve all the Bank Accounts from the database.
     *
     * @return An ArrayList that contains all the Bank Accounts of the company.
     */
    public static ObservableList<String> getCompanyBankAccounts() {
        ObservableList<String> bankAccounts = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_company_bank_accounts_names()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bankAccounts.add(resultSet.getString("bank_name"));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return bankAccounts;
    }

    /**
     * Retrieves the company ID associated with a given product code.
     *
     * @param productCode The product code for which the company ID is to be
     *                    retrieved.
     * @return The company ID or null if not found.
     */
    public static Integer getCompanyId(String productCode) {
        Integer id = null;
        try {
            DatabaseConnection db = new DatabaseConnection();
            PreparedStatement statement = db.getConnection().prepareStatement("SELECT getCompanyId(?) AS companyID");
            statement.setString(1, productCode);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = Integer.valueOf(resultSet.getString("companyID"));
            }

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching company id from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching company id from database.{}", e.getMessage(), e);
        }

        return id;
    }

    /**
     * Submits a purchase request to a company with account details.
     *
     * @param cartItems   The list of cart items to be included in the request.
     * @param totalAmount The total amount of the request.
     * @param paidAmount  The amount paid for the request.
     * @param requestType The type of the purchase request.
     * @param paymentType The payment type for the request.
     * @param accountNo   The account number associated with the request.
     * @param sourceId    The source ID of the company.
     * @return True if the request is submitted successfully; false otherwise.
     */
    public static boolean submitRequestToCompany(
            ObservableList<Cart> cartItems,
            String totalAmount,
            String paidAmount,
            String requestType,
            String paymentType,
            String accountNo,
            Integer sourceId) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall(
                    "{CALL sp_create_purchas_request_with_company(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            double paidBill = Double.parseDouble(paidAmount);
            double totalBill = Double.parseDouble(totalAmount);
            System.out.println(paidAmount);
            System.out.println(totalAmount);

            String paymentStatus = paidBill == totalBill ? "Completed" : "Pending";

            statement.setString(1, Utility.toJsonString(cartItems)); // Convert ObservableList<Cart> to JSON String
            statement.setDouble(2, totalBill);
            statement.setDouble(3, paidBill);
            statement.setString(4, requestType);
            statement.setString(5, paymentType);
            statement.setString(6, accountNo);
            statement.setInt(7, sourceId);
            statement.setString(8, paymentStatus);
            statement.setString(9, Utility.toDescriptionString(cartItems));
            statement.registerOutParameter(10, Types.BOOLEAN);

            // Execute the stored procedure
            statement.execute();

            result = statement.getBoolean(10);
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while submitting the request. {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while submitting the request. {}", e.getMessage(), e);
        }
        return result;
    }

    public static BigInteger getWholesalerId(String partyName) {
        BigInteger id = null;
        try {
            DatabaseConnection db = new DatabaseConnection();
            PreparedStatement statement = db.getConnection()
                    .prepareStatement("SELECT getWholesalerId(?) AS wholesalerId");
            statement.setString(1, partyName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = BigInteger.valueOf(Long.parseLong(resultSet.getString("wholesalerId")));
            }

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching wholesaler id from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching wholesaler id from database.{}", e.getMessage(),
                    e);
        }

        return id;
    }

    /**
     * Placeholder method. No implementation provided.
     *
     * @param cartItems     The list of cart items to be included in the request.
     * @param totalAmount   The total amount of the request.
     * @param paymentType   The payment type for the request.
     * @param partyType     The type of party involved in the request.
     * @param sourceId      The source ID of the wholesaler.
     * @param paymentStatus The payment status of the request.
     * @return Always returns false.
     */
    public static boolean submitRequestToWholesaler(
            ObservableList<Cart> cartItems,
            String totalAmount,
            String paymentType,
            String accountNo,
            String partyType,
            BigInteger sourceId,
            String paymentStatus,
            String paidAmount,
            String freightAmount) {
        boolean result = false;

        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall(
                    "{CALL sp_create_purchase_request_with_wholesaler(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            double paidBill = Double.parseDouble(paidAmount);
            double totalBill = Double.parseDouble(totalAmount);

            paymentStatus = paidBill == totalBill ? "Completed" : "Pending";

            String description = Utility.toDescriptionString(cartItems);

            statement.setString(1, Utility.toJsonString(cartItems)); // Convert ObservableList<Cart> to JSON String
            statement.setDouble(2, totalBill);
            statement.setDouble(3, paidBill);
            statement.setString(4, paymentType);
            statement.setString(5, accountNo);
            statement.setLong(6, sourceId.longValue());
            statement.setString(7, paymentStatus);
            statement.setDouble(8, Double.parseDouble(freightAmount));
            statement.setString(9, description);
            statement.registerOutParameter(10, Types.BOOLEAN);

            // Execute the stored procedure
            statement.execute();

            result = statement.getBoolean(10);
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while submitting the request. {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while submitting the request. {}", e.getMessage(), e);
        }
        return result;
    }

    /**
     * Retrieves a list of company names from the database.
     *
     * @return An ObservableList of company names.
     */
    public static ObservableList<String> getCompaniesNames() {
        ObservableList<String> companiesNames = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_companies_names()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                companiesNames.add(resultSet.getString("company_name"));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return companiesNames;
    }

    /**
     * Retrieves a list of product names by company from the database.
     *
     * @param companyName The name of the company for which product names are to be
     *                    retrieved.
     * @return An ObservableList of product names.
     */
    public static ObservableList<String> getProductsByCompany(String companyName) {
        ObservableList<String> productsNames = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_products_by_company(?)");
            statement.setString(1, companyName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                productsNames.add(resultSet.getString("product_code"));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return productsNames;
    }

    /**
     * Get the product's available quantity.
     *
     * @param productCode The productCode for which to fetch the available quantity.
     * @return True if enough quantity is available at Warehouse or Shop otherwise
     * false.
     */
    public static int getAvailableQuantity(String productCode) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();

            // Calling the database function to get the product available quantity
            String query = "SELECT getAvailableQuantity(?) AS available_quantity";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productCode);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("available_quantity");
            }
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from the database: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching data from the database: {}", e.getMessage(), e);
        }
        return -1;
    }

    /**
     * Inserts a walk-in bill into the database with the specified customer details,
     * account number, total bill amount, and a list of items in the cart.
     *
     * @param customerName The name of the walk-in customer.
     * @param accountNo    The account number associated with the customer.
     * @param totalBill    The total bill amount for the walk-in transaction.
     * @param cartItems    The list of items in the cart to be included in the bill.
     * @return BillCreationObject that has Bill No and true/false value.
     */
    public static BillCreationResult insertWalkInBill(
            String customerName,
            String accountNo,
            String totalBill,
            ObservableList<Cart> cartItems) {
        boolean result;
        String billNo;
        String description = Utility.toDescriptionString(cartItems);
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_create_walk_in_bill(?, ?, ?, ?, ?, ?, ?)}");

            statement.setString(1, customerName);
            statement.setString(2, accountNo);
            statement.setDouble(3, Double.parseDouble(totalBill));
            statement.setString(4, description);
            statement.setString(5, Utility.toJsonString(cartItems));
            statement.registerOutParameter(6, Types.BOOLEAN);
            statement.registerOutParameter(7, Types.INTEGER);

            statement.execute();

            result = statement.getBoolean(6);
            billNo = "WI_" + statement.getInt(7);

            statement.close();
            db.close();

            return new BillCreationResult(result, billNo);
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting bill: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting bill: {}", e.getMessage(), e);
        }
        return new BillCreationResult(false, null); // Return failure with an invalid billId
    }

    /**
     * Retrieves a list of order numbers from the database.
     *
     * @return ObservableList of Strings containing order numbers.
     */
    public static ObservableList<String> getOrderNoList() {
        ObservableList<String> orderNoList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_order_no_list()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orderNoList.add(resultSet.getString("order_no"));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return orderNoList;
    }

    /**
     * Retrieves a list of product codes associated with a specific order number
     * from the database.
     *
     * @param orderNo The order number for which product codes are to be fetched.
     * @return ObservableList of Strings containing product codes.
     */
    public static ObservableList<String> getProductCodeList(int orderNo) {
        ObservableList<String> productCodeList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_product_code_list(?)");
            statement.setInt(1, orderNo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                productCodeList.add(resultSet.getString("product_code"));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return productCodeList;
    }

    /**
     * Retrieves the ordered quantity of a specific product in a given order.
     *
     * @param orderNo     The order number for which the quantity is requested.
     * @param productCode The product code for which the quantity is requested.
     * @return The ordered quantity of the specified product in the given order.
     */
    public static float getOrderedQuantity(int orderNo, String productCode) {
        float orderedQuantity = 0;
        try {
            DatabaseConnection db = new DatabaseConnection();

            String query = "SELECT getLeftQuantity(?, ?) AS left_quantity";
            PreparedStatement statement = db.getConnection().prepareStatement(query);
            statement.setInt(1, orderNo);
            statement.setString(2, productCode);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                orderedQuantity = resultSet.getFloat("left_quantity");
            }

            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return orderedQuantity;
    }

    /**
     * Inserts the stock arrival details into the database.
     *
     * @param orderNo     The order number for which the stock arrived.
     * @param productCode The product code for which the stock arrived.
     * @param shQuantity  The quantity of the product that arrived at the shop.
     * @param whQuantity  The quantity of the product that arrived at the warehouse.
     * @param vehicleNo   The vehicle number associated with the stock arrival.
     * @param driverNo    The driver number associated with the stock arrival.
     * @return True if the stock arrival details were inserted successfully, false
     */
    public static boolean insertStockArrival(
            String orderNo,
            String productCode,
            String shQuantity,
            String whQuantity,
            String vehicleNo,
            String driverNo) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_insert_stock_arrival(?, ?, ?, ?, ?, ?, ?)}");

            statement.setString(1, orderNo);
            statement.setString(2, productCode);
            statement.setString(3, shQuantity);
            statement.setString(4, whQuantity);
            statement.setString(5, vehicleNo);
            statement.setString(6, driverNo);
            statement.registerOutParameter(7, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(7);

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting stock arrival details: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting stock arrival details: {}", e.getMessage(), e);
        }
        return result;
    }

    public static boolean addStockTransfer(ObservableList<StockTransferCart> cartItems, String fromStock,
                                           String toStock) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_add_stock_transfer(?, ?, ?, ?)}");

            statement.setString(1, Utility.toJsonStockTransferString(cartItems));
            statement.setString(2, fromStock);
            statement.setString(3, toStock);
            statement.registerOutParameter(4, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(4);

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting stock transfer details: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting stock transfer details: {}", e.getMessage(), e);
        }
        return result;
    }

    public static ObservableList<StockTransfer> getStockTransferHistory() {
        ObservableList<StockTransfer> stockTransferHistory = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_stock_transfer_history()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String transferDate = resultSet.getString("formatted_transfer_date");
                String fromPlace = resultSet.getString("from_place");
                String toPlace = resultSet.getString("to_place");
                String productName = resultSet.getString("product_name");
                String brand_name = resultSet.getString("brand_name");
                String company_name = resultSet.getString("company_name");
                float quantity = resultSet.getFloat("quantity");

                stockTransferHistory.add(new StockTransfer(transferDate, fromPlace, toPlace, productName, brand_name,
                        company_name, quantity));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return stockTransferHistory;
    }

    public static ObservableList<StockTransfer> searchStockTransfer(String searchText) {
        ObservableList<StockTransfer> stockTransferHistory = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_search_stock_transfer(?)");
            statement.setString(1, searchText);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String transferDate = resultSet.getString("formatted_transfer_date");
                String fromPlace = resultSet.getString("from_place");
                String toPlace = resultSet.getString("to_place");
                String productName = resultSet.getString("product_name");
                String brand_name = resultSet.getString("brand_name");
                String company_name = resultSet.getString("company_name");
                float quantity = resultSet.getLong("quantity");

                stockTransferHistory.add(new StockTransfer(transferDate, fromPlace, toPlace, productName, brand_name,
                        company_name, quantity));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return stockTransferHistory;
    }

    public static ObservableList<StockArrival> getStockArrivalHistory() {
        ObservableList<StockArrival> stockArrivalHistory = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_stock_arrival_history()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");
                String arrivalDate = resultSet.getString("arrive_date");
                String productName = resultSet.getString("product_name");
                String brandName = resultSet.getString("brand_name");
                String companyName = resultSet.getString("company_name");
                float quantity = resultSet.getFloat("quantity");
                float shQuantity = resultSet.getFloat("sh_quantity");
                float whQuantity = resultSet.getFloat("wh_quantity");
                String dealerName = resultSet.getString("ordered_from");
                String vehicleNo = resultSet.getString("vehicle_no");
                String driverNo = resultSet.getString("driver_no");

                stockArrivalHistory.add(new StockArrival(orderNo, arrivalDate, productName, brandName, companyName,
                        quantity, whQuantity, shQuantity, vehicleNo, driverNo, dealerName));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return stockArrivalHistory;
    }

    public static ObservableList<StockArrival> searchStockArrivalHistory(String searchText) {
        ObservableList<StockArrival> stockArrivalHistory = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_search_stock_arrival(?)");
            statement.setString(1, searchText);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");
                String arrivalDate = resultSet.getString("arrive_date");
                String productName = resultSet.getString("product_name");
                String brandName = resultSet.getString("brand_name");
                String companyName = resultSet.getString("company_name");
                float quantity = resultSet.getFloat("quantity");
                float shQuantity = resultSet.getFloat("sh_quantity");
                float whQuantity = resultSet.getFloat("wh_quantity");
                String dealerName = resultSet.getString("ordered_from");
                String vehicleNo = resultSet.getString("vehicle_no");
                String driverNo = resultSet.getString("driver_no");

                stockArrivalHistory.add(new StockArrival(orderNo, arrivalDate, productName, brandName, companyName,
                        quantity, whQuantity, shQuantity, vehicleNo, driverNo, dealerName));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching data from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching the data from database.{}", e.getMessage(), e);
        }
        return stockArrivalHistory;
    }

    public static ObservableList<PurchaseRequest> getPurchaseRequests() {
        ObservableList<PurchaseRequest> purchaseRequests = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_purchase_requests()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");
                String requestDate = resultSet.getString("request_date");
                String productName = resultSet.getString("product_name");
                String brandName = resultSet.getString("brand_name");
                float quantity = resultSet.getFloat("quantity");
                String orderedFrom = resultSet.getString("ordered_from");
                String status = resultSet.getString("order_status");
                float paidAmount = resultSet.getFloat("amount_paid");

                purchaseRequests.add(new PurchaseRequest(orderNo, requestDate, productName, brandName, orderedFrom,
                        quantity, paidAmount, status));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching purchase requests from database {}", e.getMessage(),
                    e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching purchase requests from database.{}",
                    e.getMessage(), e);
        }
        return purchaseRequests;
    }

    public static ObservableList<PurchaseRequest> searchPurchaseRequests(String searchText) {
        ObservableList<PurchaseRequest> purchaseRequests = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_search_purchase_requests(?)");
            statement.setString(1, searchText);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");
                String requestDate = resultSet.getString("request_date");
                String productName = resultSet.getString("product_name");
                String brandName = resultSet.getString("brand_name");
                float quantity = resultSet.getFloat("quantity");
                String orderedFrom = resultSet.getString("ordered_from");
                String status = resultSet.getString("order_status");
                float paidAmount = resultSet.getFloat("amount_paid");

                purchaseRequests.add(new PurchaseRequest(orderNo, requestDate, productName, brandName, orderedFrom,
                        quantity, paidAmount, status));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching purchase requests from database {}", e.getMessage(),
                    e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching purchase requests from database.{}",
                    e.getMessage(), e);
        }
        return purchaseRequests;
    }

    public static boolean addPettyCashRecord(String amountText, String description) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("{CALL sp_add_petty_cash_record(?, ?, ?)}");

            double amount = Double.parseDouble(amountText);

            statement.setDouble(1, amount);
            statement.setString(2, description);
            statement.registerOutParameter(3, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(3);

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting petty cash record: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting petty cash record: {}", e.getMessage(), e);
        }
        return result;
    }

    public static ObservableList<MiscellaneousPayments> getAllMiscellaneousPayments(LocalDate date) {
        ObservableList<MiscellaneousPayments> miscellaneousPayments = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_all_miscellaneous_payments(?)");

            statement.setDate(1, Date.valueOf(date));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String description = resultSet.getString("payment_description");
                float amount = resultSet.getFloat("amount");

                miscellaneousPayments.add(new MiscellaneousPayments(description, NumberFormatter.formatWithCommas(amount)));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching miscellaneous payments from database {}",
                    e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching miscellaneous payments from database.{}",
                    e.getMessage(), e);
        }
        return miscellaneousPayments;
    }

    public static ObservableList<String> getAccountHolderNames() {
        ObservableList<String> accountHolders = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_account_holder_names()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("holder_name");

                accountHolders.add(name);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching account holder names from database {}", e.getMessage(),
                    e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching account holder names from database.{}",
                    e.getMessage(), e);
        }
        return accountHolders;
    }

    public static BillCreationResult insertEmptyBill(
            BigInteger holderNo,
            String accountNo,
            double amount,
            String description,
            String paymentType,
            String paymentStatus) {
        boolean result = false;
        String billId;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_insert_empty_bill(?, ?, ?, ?, ?, ?, ?)}");

            statement.setLong(1, holderNo.longValue());
            statement.setString(2, accountNo);
            statement.setDouble(3, amount);
            statement.setString(4, description);
            statement.setString(5, paymentType);
            statement.registerOutParameter(6, Types.VARCHAR);
            statement.setString(7, paymentStatus);

            ResultSet resultSet = statement.executeQuery();

            billId = statement.getString(6);

            while (resultSet.next()) {
                result = resultSet.getBoolean("result");
            }
            return new BillCreationResult(result, billId);
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting empty bill: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting empty bill: {}", e.getMessage(), e);
        }
        return new BillCreationResult(result, null);
    }

    public static double getPreviousBalance(BigInteger holderNo) {
        double previousBalance = 0;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("{CALL sp_get_balance_by_holder_id(?, ?)}");
            statement.setLong(1, holderNo.longValue());
            statement.registerOutParameter(2, Types.DOUBLE);

            statement.execute();

            previousBalance = statement.getDouble(2);

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching previous balance from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching previous balance from the database.{}",
                    e.getMessage(), e);
        }
        return previousBalance;
    }

    public static ObservableList<PendingOrdersPayments> getAllPendingPayments() {
        ObservableList<PendingOrdersPayments> pendingOrdersPayments = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_all_pending_payments()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");
                String orderedFrom = resultSet.getString("ordered_from");
                double paidAmount = resultSet.getDouble("amount_paid");
                double totalAmount = resultSet.getDouble("total_amount");

                pendingOrdersPayments.add(new PendingOrdersPayments(orderNo, paidAmount, totalAmount, orderedFrom));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching pending orders payments from database {}",
                    e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching pending orders payments from database.{}",
                    e.getMessage(), e);
        }
        return pendingOrdersPayments;
    }

    public static ObservableList<Integer> getAllOrderNo() {
        ObservableList<Integer> orderNoList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_pending_order_numbers()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");

                orderNoList.add(orderNo);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching order numbers from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching order numbers from database.{}", e.getMessage(),
                    e);
        }
        return orderNoList;
    }

    public static boolean addPendingOrderPayment(String accountNo, int orderNo, double amount) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_add_pending_order_payment(?, ?, ?, ?)}");

            statement.setString(1, accountNo);
            statement.setInt(2, orderNo);
            statement.setDouble(3, amount);
            statement.registerOutParameter(4, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(4);

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting pending order payment: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting pending order payment: {}", e.getMessage(), e);
        }
        return result;
    }

    public static ObservableList<PendingOrdersPayments> searchPendingPayments(String searchQuery) {
        ObservableList<PendingOrdersPayments> pendingOrdersPayments = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_search_pending_payments(?)");
            statement.setString(1, searchQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderNo = resultSet.getInt("request_id");
                String orderedFrom = resultSet.getString("ordered_from");
                double paidAmount = resultSet.getDouble("amount_paid");
                double totalAmount = resultSet.getDouble("total_amount");

                pendingOrdersPayments.add(new PendingOrdersPayments(orderNo, paidAmount, totalAmount, orderedFrom));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching pending orders payments from database {}",
                    e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching pending orders payments from database.{}",
                    e.getMessage(), e);
        }
        return pendingOrdersPayments;
    }

    public static BillCreationResult insertAccountHolderBill(
            BigInteger holderNo,
            String accountNumber,
            String totalBill,
            ObservableList<Cart> cartItems,
            double paidAmount) {
        boolean result = false;
        String billId;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_insert_account_holder_bill(?, ?, ?, ?, ?, ?, ?, ?)}");

            double totalAmount = Double.parseDouble(totalBill);

            String description = Utility.toDescriptionString(cartItems);

            statement.setLong(1, holderNo.longValue());
            statement.setString(2, accountNumber);
            statement.setDouble(3, totalAmount);
            statement.setString(4, Utility.toJsonString(cartItems));
            statement.setString(5, description);
            statement.setDouble(6, paidAmount);
            statement.registerOutParameter(7, Types.BOOLEAN);
            statement.registerOutParameter(8, Types.INTEGER);

            statement.execute();

            result = statement.getBoolean(7);
            billId = "AH_" + statement.getInt(8);

            statement.close();
            db.close();
            return new BillCreationResult(result, billId);
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting account holder bill: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting account holder bill: {}", e.getMessage(), e);
        }
        return new BillCreationResult(result, null);
    }

    public static ObservableList<OverInvoice> getOverInvoices() {
        ObservableList<OverInvoice> overInvoices = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_over_invoices()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                float quantity = resultSet.getFloat("over_invoiced_quantity");

                overInvoices.add(new OverInvoice(productCode, quantity));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching over invoices from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching over invoices from database.{}", e.getMessage(),
                    e);
        }
        return overInvoices;
    }

    public static ObservableList<OverInvoice> searchOverInvoices(String searchQuery) {
        ObservableList<OverInvoice> overInvoices = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_search_over_invoices(?)");
            statement.setString(1, searchQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");
                float quantity = resultSet.getFloat("over_invoiced_quantity");

                overInvoices.add(new OverInvoice(productCode, quantity));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching over invoices from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching over invoices from database.{}", e.getMessage(),
                    e);
        }
        return overInvoices;
    }

    public static ObservableList<String> getOverInvoiceProductCodes() {
        ObservableList<String> productCodes = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_over_invoice_product_codes()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_code");

                productCodes.add(productCode);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching over invoice product codes from database {}",
                    e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching over invoice product codes from database.{}",
                    e.getMessage(), e);
        }
        return productCodes;
    }

    public static boolean insertOverInvoiceStock(String productName, float quantity) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection()
                    .prepareCall("{CALL sp_insert_over_invoice_stock(?, ?, ?)}");

            statement.setString(1, productName);
            statement.setFloat(2, quantity);
            statement.registerOutParameter(3, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(3);

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while inserting over invoice stock: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while inserting over invoice stock: {}", e.getMessage(), e);
        }
        return result;
    }

    public static ObservableList<StockArrivalRecord> getTodayStockArrivals(LocalDate date) {
        ObservableList<StockArrivalRecord> stockArrivalRecords = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_today_stock_arrivals(?)");

            statement.setDate(1, Date.valueOf(date));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                double quantity = resultSet.getDouble("quantity");
                String orderedFrom = resultSet.getString("ordered_from");

                stockArrivalRecords.add(new StockArrivalRecord(productName, NumberFormatter.formatWithCommas(quantity), orderedFrom));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching today's stock arrivals from database {}", e.getMessage(),
                    e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching today's stock arrivals from database.{}",
                    e.getMessage(), e);
        }
        return stockArrivalRecords;
    }

    public static ObservableList<SalesRecord> getTodaySales(LocalDate date) {
        ObservableList<SalesRecord> salesRecords = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_today_sales(?)");

            statement.setDate(1, Date.valueOf(date));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString("product_name");
                double quantity = resultSet.getFloat("quantity");
                String customerName = resultSet.getString("customer_name");
                String paymentType = resultSet.getString("payment_type");
                double amount = resultSet.getDouble("amount");

                salesRecords.add(new SalesRecord(productName, NumberFormatter.formatWithCommas(quantity), customerName, paymentType, NumberFormatter.formatWithCommas(amount)));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching today's sales from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching today's sales from database.{}", e.getMessage(),
                    e);
        }
        return salesRecords;
    }

    public static ObservableList<OnlinePayment> getTodayOnlinePayments(LocalDate date) {
        ObservableList<OnlinePayment> onlinePayments = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_today_online_payments(?)");

            statement.setDate(1, Date.valueOf(date));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OnlinePayment onlinePayment = new OnlinePayment(
                        resultSet.getString("bank_name"),
                        resultSet.getString("holder_name"),
                        resultSet.getString("account_no"),
                        NumberFormatter.formatWithCommas(resultSet.getDouble("amount"))
                );
                onlinePayments.add(onlinePayment);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching today's online payments from database {}",
                    e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching today's online payments from database.{}",
                    e.getMessage(), e);
        }
        return onlinePayments;
    }

    public static ObservableList<StockDepletion> getStockDepletionObservableList() {
        ObservableList<StockDepletion> stockDepletionObservableList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_stock_depletion()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int billNumber = resultSet.getInt("bill_id");
                String productName = resultSet.getString("product_name");
                String date = resultSet.getString("bill_date");
                String customerName = resultSet.getString("customer_name");
                float quantity = resultSet.getFloat("quantity");

                stockDepletionObservableList.add(new StockDepletion(date, productName, quantity, customerName, billNumber));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching stock depletion from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching stock depletion from database.{}", e.getMessage(),
                    e);
        }
        return stockDepletionObservableList;
    }

    public static boolean checkOverInvoiceIsEmpty() {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("SELECT isViewEmpty() AS result");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getBoolean("result");
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while checking over invoice is empty {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while checking over invoice is empty.{}", e.getMessage(), e);
        }
        return result;
    }

    public static boolean addStockDepletion(Integer billNumber, String productCode, float shQuantity, float whQuantity, boolean isWalkInCustomer, boolean isAccountHolder) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("{CALL sp_update_stock(?, ?, ?, ?, ?, ?, ?)}");

            statement.setInt(1, billNumber);
            statement.setString(2, productCode);
            statement.setFloat(3, shQuantity);
            statement.setFloat(4, whQuantity);
            statement.setBoolean(5, isWalkInCustomer);
            statement.setBoolean(6, isAccountHolder);
            statement.registerOutParameter(7, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(7);

            statement.close();
            db.close();
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while adding stock depletion: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while adding stock depletion: {}", e.getMessage(), e);
        }
        return result;
    }

    public static ObservableList<DebitCredit> getDebitCreditReport(String holderName) {
        ObservableList<DebitCredit> debitCreditObservableList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_debit_credit_report_by_name(?)");

            statement.setString(1, holderName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("t_date").toLocalDate();
                String description = resultSet.getString("trans_description");
                double debit = resultSet.getDouble("debit");
                double credit = resultSet.getDouble("credit");
                double balance = resultSet.getDouble("balance");

                debitCreditObservableList.add(
                        new DebitCredit(
                                DateFormatter.formatDate(date),
                                NumberFormatter.formatWithCommas(debit),
                                NumberFormatter.formatWithCommas(credit),
                                NumberFormatter.formatWithCommas(balance),
                                description
                        )
                );
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching debit credit report from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching debit credit report from database.{}", e.getMessage(),
                    e);
        }
        return debitCreditObservableList;
    }

    public static ObservableList<DebitCredit> getDebitCreditReport(BigInteger cnicNo) {
        ObservableList<DebitCredit> debitCreditObservableList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_debit_credit_report_by_cnic(?)");

            statement.setLong(1, cnicNo.longValue());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("t_date").toLocalDate();
                String description = resultSet.getString("trans_description");
                double debit = resultSet.getDouble("debit");
                double credit = resultSet.getDouble("credit");
                double balance = resultSet.getDouble("balance");

                debitCreditObservableList.add(
                        new DebitCredit(
                                DateFormatter.formatDate(date),
                                NumberFormatter.formatWithCommas(debit),
                                NumberFormatter.formatWithCommas(credit),
                                NumberFormatter.formatWithCommas(balance),
                                description
                        )
                );
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching debit credit report from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching debit credit report from database.{}", e.getMessage(),
                    e);
        }
        return debitCreditObservableList;
    }

    public static ObservableList<CompanyTransaction> getCompanyAccountReport(String accountNo) {
        ObservableList<CompanyTransaction> companyTransactionsList = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();

            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_debit_credit_report_of_company_accounts(?)");
            statement.setString(1, accountNo);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("t_date").toLocalDate();
                String description = resultSet.getString("trans_description");
                double debit = resultSet.getDouble("debit");
                double credit = resultSet.getDouble("credit");
                double balance = resultSet.getDouble("balance");

                companyTransactionsList.add(
                        new CompanyTransaction(
                                DateFormatter.formatDate(date),
                                description,
                                NumberFormatter.formatWithCommas(credit),
                                NumberFormatter.formatWithCommas(debit),
                                NumberFormatter.formatWithCommas(balance)
                        )
                );
            }

        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching debit credit report from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching debit credit report from database.{}", e.getMessage(),
                    e);
        }

        return companyTransactionsList;
    }

    public static String getSoftwarePassword() {
        String password = null;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_software_password()");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching software password from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching software password from database.{}", e.getMessage(),
                    e);
        }
        return password;
    }

    public static boolean updatePassword(String newPassword) {
        boolean result = false;
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_update_software_password(?, ?)");

            statement.setString(1, newPassword);
            statement.registerOutParameter(2, Types.BOOLEAN);

            statement.execute();

            result = statement.getBoolean(2);
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while updating software password in database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while updating software password in database.{}", e.getMessage(),
                    e);
        }
        return result;
    }

    public static ObservableList<Booking> getBookingsReport() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        try {
            DatabaseConnection db = new DatabaseConnection();
            CallableStatement statement = db.getConnection().prepareCall("CALL sp_get_bookings_report()");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productCode = resultSet.getString("productCode");
                double orderedQuantity = resultSet.getDouble("orderedQuantity");
                double leftQuantity = resultSet.getDouble("leftQuantity");
                String orderedFrom = resultSet.getString("orderedFrom");
                String requestType = resultSet.getString("requestType");

                bookings.add(new Booking(productCode, orderedFrom, orderedQuantity, leftQuantity, requestType));
            }
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while fetching bookings report from database {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("An Unexpected Error occurred while fetching bookings report from database.{}", e.getMessage(),
                    e);
        }
        return bookings;
    }
}
