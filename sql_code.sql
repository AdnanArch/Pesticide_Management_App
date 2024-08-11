CREATE DATABASE al_makkah_db;
USE al_makkah_db;
-- Table for Admin Login
CREATE TABLE `admin_login` (
  `username` varchar(30) NOT NULL PRIMARY KEY,
  `password` varchar(30) NOT NULL
);
-- Table for Account Holders
CREATE TABLE `account_holders` (
  `cnic_no` bigint NOT NULL PRIMARY KEY,
  `holder_name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `is_retailer` bool NOT NULL
);
-- Table for Companies
CREATE TABLE `companies` (
  `company_id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `company_name` varchar(100) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL
);
-- Table for Product Brand
CREATE TABLE `product_brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `company_id` bigint NOT NULL,
  `brand_name` varchar(50) NOT NULL,
  PRIMARY KEY (`brand_id`),
  KEY `fk_company_id` (`company_id`),
  CONSTRAINT `fk_company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
);
-- Table for Product Category
CREATE TABLE `product_category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`)
);
-- Table for Products
CREATE TABLE `products` (
  `product_code` varchar(30) NOT NULL PRIMARY KEY,
  `category_id` int NOT NULL,
  `brand_id` int NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  CONSTRAINT `fk_brand_id_prod` FOREIGN KEY (`brand_id`) REFERENCES `product_brand` (`brand_id`),
  CONSTRAINT `fk_category_id_prod` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`category_id`)
);
-- Table for Account Holder Bills
CREATE TABLE `account_holder_bills` (
  `bill_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `cnic_no` bigint NOT NULL,
  `bill_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` double NOT NULL,
  `payment_type` VARCHAR(15) NOT NULL,
  `bill_status` varchar(20) NOT NULL DEFAULT 'Pending',
  CONSTRAINT `fk_holder_id_bill` FOREIGN KEY (`cnic_no`) REFERENCES `account_holders` (`cnic_no`)
);
-- Table for Account Holder's Bill Cart
CREATE TABLE `account_holder_bill_cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `bill_id` int NOT NULL,
  `product_code` varchar(30) NOT NULL,
  `quantity` float NOT NULL,
  `amount` double NOT NULL,
  CONSTRAINT `fk_bill_id` FOREIGN KEY (`bill_id`) REFERENCES `account_holder_bills` (`bill_id`),
  CONSTRAINT `fk_product_code` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
);
-- Table for Empty Bills
CREATE TABLE `empty_bills` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `emp_bill_id` varchar(30) DEFAULT NULL,
  `cnic_no` bigint NOT NULL,
  `bill_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` double NOT NULL,
  `payment_type` VARCHAR(15) NOT NULL,
  -- Add an index to emp_bill_id
  INDEX `idx_emp_bill_id` (`emp_bill_id`),
  CONSTRAINT `fk_holder_emp_bill_id` FOREIGN KEY (`cnic_no`) REFERENCES `account_holders` (`cnic_no`)
);
DELIMITER;
;
CREATE TRIGGER `before_empty_bills_insert` BEFORE
INSERT ON `empty_bills` FOR EACH ROW BEGIN
SET NEW.emp_bill_id = CONCAT('emp_', NEW.id);
END;
;
DELIMITER;
-- Table for Holder Debit Credit
CREATE TABLE `holder_debit_credit` (
  `transaction_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `emp_bill_id` varchar(30) DEFAULT NULL,
  `bill_id` int DEFAULT NULL,
  `cnic_no` bigint NOT NULL,
  `t_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transaction_kind` enum('debit', 'credit') NOT NULL,
  `trans_description` varchar(200) NOT NULL,
  `amount` double NOT NULL,
  CONSTRAINT `fk_bill_id_dc` FOREIGN KEY (`bill_id`) REFERENCES `account_holder_bills` (`bill_id`),
  CONSTRAINT `fk_emp_bill_id_dc` FOREIGN KEY (`emp_bill_id`) REFERENCES `empty_bills` (`emp_bill_id`),
  CONSTRAINT `fk_holder_id_dc` FOREIGN KEY (`cnic_no`) REFERENCES `account_holders` (`cnic_no`)
);
-- Table for Petty Cash Payments
CREATE TABLE `petty_cash_payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `payment_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_description` varchar(200) NOT NULL,
  `amount` double NOT NULL
);
-- Table for Purchase Requests
CREATE TABLE `purchase_requests` (
  `request_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `request_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount_paid` DOUBLE,
  `total_amount` DOUBLE NOT NULL,
  `request_type` VARCHAR(2),
  `source_account_holder_id` BIGINT,
  `source_company_id` BIGINT,
  `payment_status` VARCHAR(20) NOT NULL,
  `order_status` VARCHAR(20) NOT NULL,
  `payment_method` VARCHAR(30) NOT NULL,
  `freight` DOUBLE,
  CONSTRAINT `fk_source_account_holder` FOREIGN KEY (`source_account_holder_id`) REFERENCES `account_holders` (`cnic_no`),
  CONSTRAINT `fk_source_company` FOREIGN KEY (`source_company_id`) REFERENCES `companies` (`company_id`)
);
-- Table for Purchase Request Cart
CREATE TABLE `purchase_request_cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `request_id` INT NOT NULL,
  `product_code` varchar(30) NOT NULL,
  `quantity` float NOT NULL,
  `amount` DOUBLE NOT NULL,
  CONSTRAINT `fk_request_id_cart` FOREIGN KEY (`request_id`) REFERENCES `purchase_requests` (`request_id`),
  CONSTRAINT `fk_product_code_cart` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
);
-- Table for Purchase History
CREATE TABLE `stock_arrivals` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `request_id` INT NOT NULL,
  `arrive_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `arrived_quantity` float NOT NULL,
  `amount_paid` DOUBLE,
  CONSTRAINT `fk_purchase_request_id` FOREIGN KEY (`request_id`) REFERENCES `purchase_requests` (`request_id`)
);
-- Table for Stock
CREATE TABLE `stock` (
  `product_code` varchar(30) NOT NULL PRIMARY KEY,
  `warehouse_quantity` float NOT NULL,
  `shop_quantity` float NOT NULL,
  CONSTRAINT `fk_product_code_stock` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
);
-- Table for Stock Transfer
CREATE TABLE `stock_transfer` (
  `transfer_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `from_place` varchar(30) NOT NULL,
  `to_place` varchar(30) NOT NULL,
  `transfer_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- Table for Walk-in Bills
CREATE TABLE `walk_in_bills` (
  `bill_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `customer_name` VARCHAR(50),
  `bill_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` double NOT NULL,
  `payment_type` VARCHAR(15) NOT NULL
);
-- Table for Walk-in Bill Details
CREATE TABLE `walk_in_bill_details` (
  `detail_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `bill_id` INT NOT NULL,
  `product_code` varchar(30) NOT NULL,
  `quantity` float NOT NULL,
  `amount` double NOT NULL,
  CONSTRAINT `fk_bill_id_wibd` FOREIGN KEY (`bill_id`) REFERENCES `walk_in_bills` (`bill_id`),
  CONSTRAINT `fk_product_code_wibd` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
);
-- Table for Company Accounts
CREATE TABLE `company_accounts` (
  `account_no` varchar(50) NOT NULL PRIMARY KEY,
  `account_name` varchar(50) NOT NULL,
  `bank_name` varchar(50) NOT NULL,
  `balance` double NOT NULL
);
-- Table for Company Transactions
CREATE TABLE `company_transactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `account_no` varchar(50) NOT NULL,
  `transaction_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trans_description` varchar(100) NOT NULL,
  `transaction_type` enum('debit', 'credit') DEFAULT NULL,
  CONSTRAINT `fk_account_no` FOREIGN KEY (`account_no`) REFERENCES `company_accounts` (`account_no`)
);
CREATE TABLE `temp_stock`(
  product_code varchar(30) NOT NULL,
  warehouse_quantity float NOT NULL,
  shop_quantity float NOT NULL
);
-- Create a table that will store the data of over invoiced bills (if any)
CREATE TABLE `over_invoiced_bills` (
  `product_code` VARCHAR(30) NOT NULL,
  `quantity` FLOAT NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- write an sql query to get left quantity of product in purchase request cart
-- It should work in a way that purchase_request_cart table has a column named quantity it should take that quantity and subtract it from the sum of quantity column in stock_arrivals table
-- if there is no arrival of that product in stock_arrivals table then it should consider arrived quantity as 0
SELECT prc.quantity - IFNULL(SUM(sa.arrived_quantity), 0) AS left_quantity
FROM purchase_request_cart prc
  LEFT JOIN stock_arrivals sa ON prc.request_id = sa.request_id
WHERE prc.request_id = request_id
  AND product_code = product_code;
-- Alter stock_arrivals table to add product_code as foreign key constraint
ALTER TABLE `stock_arrivals`
ADD `product_code` VARCHAR(30) NOT NULL
AFTER `id`,
  -- Add missing table `products`
  CREATE TABLE `products` (
    `product_code` varchar(30) NOT NULL PRIMARY KEY,
    -- Add other columns here
  );
-- Add foreign key constraint
ALTER TABLE `stock`
ADD CONSTRAINT `fk_product_code` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`);
-- write the query to drop product code foreign from stock transfer table
ALTER TABLE `stock_transfer` DROP FOREIGN KEY `fk_product_code_st`;
CREATE TABLE stock_transfer_detail (
  std_id INT PRIMARY KEY NOT NULL,
  transfer_id INT NOT NULL,
  product_code VARCHAR(30) NOT NULL,
  quantity DOUBLE NOT NULL,
  CONSTRAINT `fk_transfer_id` FOREIGN KEY (`transfer_id`) REFERENCES stock_transfer(`transfer_id`),
  CONSTRAINT `fk_product_code_std` FOREIGN KEY (`product_code`) REFERENCES products(`product_code`)
);



  -- Write a query to get stock transfer details (transferdate, fromplace, toplace, CONCAT(productname, productcode), brand, company, quantity)
SELECT
  DATE_FORMAT(st.transfer_date, '%d-%m-%Y') AS formatted_transfer_date,
  st.from_place,
  st.to_place,
  CONCAT(p.product_name, ' (', p.product_code, ')') AS product_name,
  pb.brand_name,
  c.company_name,
  std.quantity
FROM
  stock_transfer st
  INNER JOIN stock_transfer_detail std ON st.transfer_id = std.transfer_id
  INNER JOIN products p ON std.product_code = p.product_code
  INNER JOIN product_brand pb ON p.brand_id = pb.brand_id
  INNER JOIN companies c ON pb.company_id = c.company_id;


-- write sql query to search in stock transfer details
SELECT
  DATE_FORMAT(st.transfer_date, '%d-%m-%Y') AS formatted_transfer_date,
  st.from_place,
  st.to_place,
  CONCAT(p.product_name, ' (', p.product_code, ')') AS product_name,
  pb.brand_name,
  c.company_name,
  std.quantity
FROM
  stock_transfer st
  INNER JOIN stock_transfer_detail std ON st.transfer_id = std.transfer_id
  INNER JOIN products p ON std.product_code = p.product_code
  INNER JOIN product_brand pb ON p.brand_id = pb.brand_id
  INNER JOIN companies c ON pb.company_id = c.company_id
WHERE
  st.transfer_date BETWEEN '2020-01-01' AND '2020-12-31'
  AND p.product_name LIKE '%product_name%'
  AND pb.brand_name LIKE '%brand_name%'
  AND c.company_name LIKE '%company_name%'
  AND st.from_place LIKE '%from_place%'
  AND st.to_place LIKE '%to_place%';


CREATE TABLE product_ledger (
                                transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                                product_code VARCHAR(30) NOT NULL,
                                transaction_type ENUM('a', 's') NOT NULL,
                                quantity INT NOT NULL,
                                location_type ENUM('s', 'w') NOT NULL,
                                `description` TEXT,
                                transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,

                                FOREIGN KEY (product_code) REFERENCES products(product_code)
);


CREATE FUNCTION generate_unique_13_digit_number(tableName VARCHAR(255), columnName VARCHAR(255))
    RETURNS BIGINT
    READS SQL DATA
BEGIN
    DECLARE newNumber BIGINT;
    DECLARE isUnique BOOLEAN DEFAULT FALSE;

    WHILE isUnique = FALSE DO
            -- Generate a random 13-digit number
            SET newNumber = FLOOR(RAND() * 10000000000000);

            -- Ensure the number is 13 digits by checking the length
            IF LENGTH(newNumber) = 13 THEN
                -- Check if the generated number already exists in the provided table and column
                SET isUnique = NOT EXISTS (
                    SELECT 1
                    FROM (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = tableName AND COLUMN_NAME = columnName) AS c
                    WHERE CAST(newNumber AS CHAR(13)) = CAST(columnName AS CHAR(13))
                );
            END IF;
        END WHILE;

    RETURN newNumber;
END


SELECT generate_unique_13_digit_number('account_holders', 'cnic_no') AS uniqueNumber;
  
