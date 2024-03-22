CREATE DATABASE  IF NOT EXISTS `al_makkah_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `al_makkah_db`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: al_makkah_db
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_holder_bill_cart`
--

DROP TABLE IF EXISTS `account_holder_bill_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_holder_bill_cart` (
                                            `cart_id` int NOT NULL AUTO_INCREMENT,
                                            `bill_id` int NOT NULL,
                                            `product_code` varchar(30) NOT NULL,
                                            `quantity` float NOT NULL,
                                            `amount` double NOT NULL,
                                            `p_status` varchar(15) NOT NULL DEFAULT 'Pending',
                                            PRIMARY KEY (`cart_id`),
                                            KEY `fk_bill_id` (`bill_id`),
                                            KEY `fk_product_code` (`product_code`),
                                            CONSTRAINT `fk_bill_id` FOREIGN KEY (`bill_id`) REFERENCES `account_holder_bills` (`bill_id`),
                                            CONSTRAINT `fk_product_code` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_holder_bills`
--

DROP TABLE IF EXISTS `account_holder_bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_holder_bills` (
                                        `bill_id` int NOT NULL AUTO_INCREMENT,
                                        `cnic_no` bigint NOT NULL,
                                        `bill_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `total_amount` double NOT NULL,
                                        `payment_type` varchar(50) NOT NULL,
                                        `bill_status` varchar(20) NOT NULL DEFAULT 'Pending',
                                        PRIMARY KEY (`bill_id`),
                                        KEY `fk_holder_id_bill` (`cnic_no`),
                                        CONSTRAINT `fk_holder_id_bill` FOREIGN KEY (`cnic_no`) REFERENCES `account_holders` (`cnic_no`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_holders`
--

DROP TABLE IF EXISTS `account_holders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_holders` (
                                   `cnic_no` bigint NOT NULL,
                                   `holder_name` varchar(50) NOT NULL,
                                   `address` varchar(100) NOT NULL,
                                   `phone` varchar(15) NOT NULL,
                                   `is_retailer` tinyint(1) NOT NULL,
                                   PRIMARY KEY (`cnic_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin_login`
--

DROP TABLE IF EXISTS `admin_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_login` (
                               `username` varchar(30) NOT NULL,
                               `password` varchar(30) NOT NULL,
                               PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
                             `company_id` bigint NOT NULL AUTO_INCREMENT,
                             `company_name` varchar(100) NOT NULL,
                             `address` varchar(100) DEFAULT NULL,
                             `phone` varchar(15) DEFAULT NULL,
                             PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_accounts`
--

DROP TABLE IF EXISTS `company_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_accounts` (
                                    `account_no` varchar(50) NOT NULL,
                                    `account_name` varchar(50) NOT NULL,
                                    `bank_name` varchar(50) NOT NULL,
                                    `balance` double NOT NULL,
                                    PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_transactions`
--

DROP TABLE IF EXISTS `company_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_transactions` (
                                        `transaction_id` int NOT NULL AUTO_INCREMENT,
                                        `account_no` varchar(50) NOT NULL,
                                        `transaction_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `trans_description` varchar(200) DEFAULT NULL,
                                        `transaction_type` enum('debit','credit') DEFAULT NULL,
                                        `amount` double DEFAULT NULL,
                                        PRIMARY KEY (`transaction_id`),
                                        KEY `fk_account_no` (`account_no`),
                                        CONSTRAINT `fk_account_no` FOREIGN KEY (`account_no`) REFERENCES `company_accounts` (`account_no`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empty_bills`
--

DROP TABLE IF EXISTS `empty_bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empty_bills` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `emp_bill_id` varchar(30) DEFAULT NULL,
                               `cnic_no` bigint NOT NULL,
                               `bill_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `total_amount` double NOT NULL,
                               `payment_type` varchar(15) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `idx_emp_bill_id` (`emp_bill_id`),
                               KEY `fk_holder_emp_bill_id` (`cnic_no`),
                               CONSTRAINT `fk_holder_emp_bill_id` FOREIGN KEY (`cnic_no`) REFERENCES `account_holders` (`cnic_no`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `before_empty_bills_insert` BEFORE INSERT ON `empty_bills` FOR EACH ROW BEGIN
    DECLARE next_id INT;
    SELECT AUTO_INCREMENT INTO next_id FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = 'al_makkah_db' AND TABLE_NAME = 'empty_bills';
    SET NEW.emp_bill_id = CONCAT('emp_', next_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `holder_debit_credit`
--

DROP TABLE IF EXISTS `holder_debit_credit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holder_debit_credit` (
                                       `transaction_id` int NOT NULL AUTO_INCREMENT,
                                       `emp_bill_id` varchar(30) DEFAULT NULL,
                                       `bill_id` int DEFAULT NULL,
                                       `cnic_no` bigint NOT NULL,
                                       `t_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `transaction_kind` enum('debit','credit') NOT NULL,
                                       `trans_description` varchar(200) NOT NULL,
                                       `amount` double NOT NULL,
                                       PRIMARY KEY (`transaction_id`),
                                       KEY `fk_bill_id_dc` (`bill_id`),
                                       KEY `fk_emp_bill_id_dc` (`emp_bill_id`),
                                       KEY `fk_holder_id_dc` (`cnic_no`),
                                       CONSTRAINT `fk_bill_id_dc` FOREIGN KEY (`bill_id`) REFERENCES `account_holder_bills` (`bill_id`),
                                       CONSTRAINT `fk_emp_bill_id_dc` FOREIGN KEY (`emp_bill_id`) REFERENCES `empty_bills` (`emp_bill_id`),
                                       CONSTRAINT `fk_holder_id_dc` FOREIGN KEY (`cnic_no`) REFERENCES `account_holders` (`cnic_no`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `over_invoiced_bills`
--

DROP TABLE IF EXISTS `over_invoiced_bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `over_invoiced_bills` (
                                       `product_code` varchar(30) NOT NULL,
                                       `quantity` float NOT NULL,
                                       `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `pending_order_payments_view`
--

DROP TABLE IF EXISTS `pending_order_payments_view`;
/*!50001 DROP VIEW IF EXISTS `pending_order_payments_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `pending_order_payments_view` AS SELECT
                                                          1 AS `request_id`,
                                                          1 AS `ordered_from`,
                                                          1 AS `amount_paid`,
                                                          1 AS `total_amount`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `petty_cash_payments`
--

DROP TABLE IF EXISTS `petty_cash_payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `petty_cash_payments` (
                                       `payment_id` int NOT NULL AUTO_INCREMENT,
                                       `payment_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `payment_description` varchar(200) NOT NULL,
                                       `amount` double NOT NULL,
                                       PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_brand`
--

DROP TABLE IF EXISTS `product_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_brand` (
                                 `brand_id` int NOT NULL AUTO_INCREMENT,
                                 `company_id` bigint NOT NULL,
                                 `brand_name` varchar(50) NOT NULL,
                                 PRIMARY KEY (`brand_id`),
                                 KEY `fk_company_id` (`company_id`),
                                 CONSTRAINT `fk_company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
                                    `category_id` int NOT NULL AUTO_INCREMENT,
                                    `category_name` varchar(50) NOT NULL,
                                    PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `product_view`
--

DROP TABLE IF EXISTS `product_view`;
/*!50001 DROP VIEW IF EXISTS `product_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `product_view` AS SELECT
                                           1 AS `product_code`,
                                           1 AS `product_name`,
                                           1 AS `category`,
                                           1 AS `brand`,
                                           1 AS `company`,
                                           1 AS `price`,
                                           1 AS `warehouse_quantity`,
                                           1 AS `shop_quantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
                            `product_code` varchar(30) NOT NULL,
                            `category_id` int NOT NULL,
                            `brand_id` int NOT NULL,
                            `product_name` varchar(100) NOT NULL,
                            `price` double NOT NULL,
                            PRIMARY KEY (`product_code`),
                            KEY `fk_brand_id_prod` (`brand_id`),
                            KEY `fk_category_id_prod` (`category_id`),
                            CONSTRAINT `fk_brand_id_prod` FOREIGN KEY (`brand_id`) REFERENCES `product_brand` (`brand_id`),
                            CONSTRAINT `fk_category_id_prod` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_request_cart`
--

DROP TABLE IF EXISTS `purchase_request_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_request_cart` (
                                         `cart_id` int NOT NULL AUTO_INCREMENT,
                                         `request_id` int NOT NULL,
                                         `product_code` varchar(30) NOT NULL,
                                         `quantity` float NOT NULL,
                                         `amount` double NOT NULL,
                                         `p_status` varchar(10) NOT NULL DEFAULT 'Pending',
                                         PRIMARY KEY (`cart_id`),
                                         KEY `fk_request_id_cart` (`request_id`),
                                         KEY `fk_product_code_cart` (`product_code`),
                                         CONSTRAINT `fk_product_code_cart` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`),
                                         CONSTRAINT `fk_request_id_cart` FOREIGN KEY (`request_id`) REFERENCES `purchase_requests` (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_requests`
--

DROP TABLE IF EXISTS `purchase_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_requests` (
                                     `request_id` int NOT NULL AUTO_INCREMENT,
                                     `request_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `amount_paid` double DEFAULT NULL,
                                     `total_amount` double NOT NULL,
                                     `request_type` varchar(2) DEFAULT NULL,
                                     `source_account_holder_id` bigint DEFAULT NULL,
                                     `source_company_id` bigint DEFAULT NULL,
                                     `payment_status` varchar(20) NOT NULL,
                                     `order_status` varchar(20) NOT NULL,
                                     `payment_method` varchar(30) NOT NULL,
                                     `freight` double DEFAULT NULL,
                                     PRIMARY KEY (`request_id`),
                                     KEY `fk_source_account_holder` (`source_account_holder_id`),
                                     KEY `fk_source_company` (`source_company_id`),
                                     CONSTRAINT `fk_source_account_holder` FOREIGN KEY (`source_account_holder_id`) REFERENCES `account_holders` (`cnic_no`),
                                     CONSTRAINT `fk_source_company` FOREIGN KEY (`source_company_id`) REFERENCES `companies` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `purchase_requests_view`
--

DROP TABLE IF EXISTS `purchase_requests_view`;
/*!50001 DROP VIEW IF EXISTS `purchase_requests_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `purchase_requests_view` AS SELECT
                                                     1 AS `request_id`,
                                                     1 AS `request_date`,
                                                     1 AS `product_name`,
                                                     1 AS `brand_name`,
                                                     1 AS `quantity`,
                                                     1 AS `ordered_from`,
                                                     1 AS `amount_paid`,
                                                     1 AS `order_status`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
                         `product_code` varchar(30) NOT NULL,
                         `warehouse_quantity` float NOT NULL,
                         `shop_quantity` float NOT NULL,
                         PRIMARY KEY (`product_code`),
                         CONSTRAINT `fk_product_code_stock` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `stock_arrival_history_view`
--

DROP TABLE IF EXISTS `stock_arrival_history_view`;
/*!50001 DROP VIEW IF EXISTS `stock_arrival_history_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `stock_arrival_history_view` AS SELECT
                                                         1 AS `id`,
                                                         1 AS `request_id`,
                                                         1 AS `arrive_date`,
                                                         1 AS `product_name`,
                                                         1 AS `brand_name`,
                                                         1 AS `company_name`,
                                                         1 AS `sh_quantity`,
                                                         1 AS `wh_quantity`,
                                                         1 AS `quantity`,
                                                         1 AS `ordered_from`,
                                                         1 AS `vehicle_no`,
                                                         1 AS `driver_no`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `stock_arrivals`
--

DROP TABLE IF EXISTS `stock_arrivals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_arrivals` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `request_id` int NOT NULL,
                                  `arrive_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `amount_paid` double DEFAULT NULL,
                                  `wh_quantity` double DEFAULT NULL,
                                  `sh_quantity` double DEFAULT NULL,
                                  `vehicle_no` varchar(15) DEFAULT NULL,
                                  `driver_no` varchar(12) DEFAULT NULL,
                                  `product_code` varchar(30) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `fk_purchase_request_id` (`request_id`),
                                  KEY `fk_product_code_for_stock` (`product_code`),
                                  CONSTRAINT `fk_product_code_for_stock` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`),
                                  CONSTRAINT `fk_purchase_request_id` FOREIGN KEY (`request_id`) REFERENCES `purchase_requests` (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock_transfer`
--

DROP TABLE IF EXISTS `stock_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_transfer` (
                                  `transfer_id` int NOT NULL AUTO_INCREMENT,
                                  `from_place` varchar(30) NOT NULL,
                                  `to_place` varchar(30) NOT NULL,
                                  `transfer_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`transfer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock_transfer_detail`
--

DROP TABLE IF EXISTS `stock_transfer_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_transfer_detail` (
                                         `std_id` int NOT NULL AUTO_INCREMENT,
                                         `transfer_id` int NOT NULL,
                                         `product_code` varchar(30) NOT NULL,
                                         `quantity` double NOT NULL,
                                         PRIMARY KEY (`std_id`),
                                         KEY `fk_transfer_id` (`transfer_id`),
                                         KEY `fk_product_code_std` (`product_code`),
                                         CONSTRAINT `fk_product_code_std` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`),
                                         CONSTRAINT `fk_transfer_id` FOREIGN KEY (`transfer_id`) REFERENCES `stock_transfer` (`transfer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `stock_transfer_history`
--

DROP TABLE IF EXISTS `stock_transfer_history`;
/*!50001 DROP VIEW IF EXISTS `stock_transfer_history`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `stock_transfer_history` AS SELECT
                                                     1 AS `formatted_transfer_date`,
                                                     1 AS `from_place`,
                                                     1 AS `to_place`,
                                                     1 AS `product_name`,
                                                     1 AS `brand_name`,
                                                     1 AS `company_name`,
                                                     1 AS `quantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `today_over_invoice_view`
--

DROP TABLE IF EXISTS `today_over_invoice_view`;
/*!50001 DROP VIEW IF EXISTS `today_over_invoice_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `today_over_invoice_view` AS SELECT
                                                      1 AS `product_code`,
                                                      1 AS `over_invoiced_quantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `today_sale_view`
--

DROP TABLE IF EXISTS `today_sale_view`;
/*!50001 DROP VIEW IF EXISTS `today_sale_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `today_sale_view` AS SELECT
                                              1 AS `bill_id`,
                                              1 AS `customer_name`,
                                              1 AS `bill_date`,
                                              1 AS `product_name`,
                                              1 AS `quantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `walk_in_bill_details`
--

DROP TABLE IF EXISTS `walk_in_bill_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `walk_in_bill_details` (
                                        `detail_id` int NOT NULL AUTO_INCREMENT,
                                        `bill_id` int NOT NULL,
                                        `product_code` varchar(30) NOT NULL,
                                        `quantity` float NOT NULL,
                                        `amount` double NOT NULL,
                                        `p_status` varchar(15) NOT NULL DEFAULT 'Pending',
                                        PRIMARY KEY (`detail_id`),
                                        KEY `fk_bill_id_wibd` (`bill_id`),
                                        KEY `fk_product_code_wibd` (`product_code`),
                                        CONSTRAINT `fk_bill_id_wibd` FOREIGN KEY (`bill_id`) REFERENCES `walk_in_bills` (`bill_id`),
                                        CONSTRAINT `fk_product_code_wibd` FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `walk_in_bills`
--

DROP TABLE IF EXISTS `walk_in_bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `walk_in_bills` (
                                 `bill_id` int NOT NULL AUTO_INCREMENT,
                                 `customer_name` varchar(50) DEFAULT NULL,
                                 `bill_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `total_amount` double NOT NULL,
                                 `payment_type` varchar(50) NOT NULL,
                                 `bill_status` varchar(20) DEFAULT 'Pending',
                                 PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'al_makkah_db'
--

--
-- Dumping routines for database 'al_makkah_db'
--
/*!50003 DROP FUNCTION IF EXISTS `getAvailableQuantity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getAvailableQuantity`(productCode VARCHAR(255)) RETURNS int
    DETERMINISTIC
BEGIN
    DECLARE availableQuantity INTEGER;

    SELECT SUM(shop_quantity + warehouse_quantity) INTO availableQuantity
    FROM product_view
    WHERE product_code = productCode;

    RETURN availableQuantity;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getCompanyId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getCompanyId`(productCode VARCHAR(30)) RETURNS int
    DETERMINISTIC
BEGIN
    DECLARE companyId INT;

    SELECT company_id INTO companyId
    FROM products
             JOIN product_brand USING (brand_id)
             JOIN companies USING (company_id)
    WHERE product_code = productCode;

    RETURN companyId;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getLeftQuantity` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getLeftQuantity`(
    p_orderNo INT,
    p_productCode VARCHAR(30)
) RETURNS double
    DETERMINISTIC
BEGIN
    DECLARE left_quantity DOUBLE;

    SELECT
        MAX(prc.quantity) - IFNULL(SUM(sa.wh_quantity + sa.sh_quantity), 0) INTO left_quantity
    FROM
        purchase_request_cart prc
            LEFT JOIN
        stock_arrivals sa ON prc.request_id = sa.request_id AND prc.product_code = sa.product_code
    WHERE
        prc.request_id = p_orderNo AND prc.product_code = p_productCode;

    RETURN left_quantity;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getProductPrice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getProductPrice`(p_product_code VARCHAR(30)) RETURNS double
    DETERMINISTIC
BEGIN
    DECLARE product_price DOUBLE;

    SELECT price INTO product_price
    FROM products
    WHERE product_code = p_product_code;

    RETURN product_price;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getWholesalerId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getWholesalerId`(wholesalerName VARCHAR(50)) RETURNS bigint
    DETERMINISTIC
BEGIN
    DECLARE wholesalerId BIGINT;

    SELECT cnic_no
    INTO wholesalerId
    FROM account_holders
    WHERE holder_name = wholesalerName;

    RETURN wholesalerId;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isRequestCompleted` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isRequestCompleted`(p_request_id INT) RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
    DECLARE is_completed BOOLEAN;

    SELECT
        CASE
            WHEN COUNT(*) = SUM(CASE WHEN p_status = 'Completed' THEN 1 ELSE 0 END) THEN TRUE
            ELSE FALSE
            END
    INTO is_completed
    FROM
        purchase_request_cart
    WHERE
        request_id = p_request_id;

    RETURN is_completed;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isViewEmpty` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isViewEmpty`() RETURNS tinyint(1)
    DETERMINISTIC
BEGIN
    DECLARE rowCount INT;

    SELECT COUNT(*) INTO rowCount FROM today_over_invoice_view;

    IF rowCount = 0 THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_product_data` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_product_data`(
    IN p_product_code VARCHAR(30)
)
BEGIN
    -- Declare variables to store the result columns
    DECLARE product_name VARCHAR(50);
    DECLARE brand VARCHAR(50);
    DECLARE company VARCHAR(100);
    DECLARE product_price DOUBLE;

    -- Use a SELECT INTO statement to fetch the data into variables
    SELECT
        p.product_name,
        pb.brand_name AS brand,
        c.company_name AS company,
        p.price
    INTO
        product_name,
        brand,
        company,
        product_price
    FROM
        products p
            JOIN
        product_brand pb ON p.brand_id = pb.brand_id
            JOIN
        companies c ON c.company_id = pb.company_id
    WHERE
        p.product_code = p_product_code;

    -- Return the result variables as the procedure output
    SELECT
        product_name AS 'Product Name',
        brand AS 'Brand',
        company AS 'Company',
        product_price AS 'Price';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_add_new_product` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_new_product`(
    IN p_product_code VARCHAR(30),
    IN p_product_name VARCHAR(50),
    IN p_category_name VARCHAR(50),
    IN p_brand_name VARCHAR(50),
    IN p_price DOUBLE,
    IN p_company_name VARCHAR(100),
    IN p_address VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_warehouse_quantity DOUBLE,
    IN p_shop_quantity DOUBLE,
    OUT p_success BOOLEAN
)
BEGIN
    DECLARE v_category_id INT;
    DECLARE v_company_id INT;
    DECLARE v_brand_id INT;

    SET p_success = FALSE; -- Default to failure

    -- Check if the category already exists, if not, insert it
    INSERT INTO product_category (category_name)
    SELECT p_category_name
    WHERE NOT EXISTS (SELECT 1 FROM product_category WHERE category_name = p_category_name);

    -- Get the category_id for the inserted or existing category
    SELECT category_id INTO v_category_id FROM product_category WHERE category_name = p_category_name;

    -- Check if the company already exists, if not, insert it
    INSERT INTO companies (company_name, address, phone)
    SELECT p_company_name, p_address, p_phone
    WHERE NOT EXISTS (SELECT 1 FROM companies WHERE company_name = p_company_name);

    -- Get the company_id for the inserted or existing company
    SELECT company_id INTO v_company_id FROM companies WHERE company_name = p_company_name;

    -- Check if the brand already exists, if not, insert it
    INSERT INTO product_brand (company_id, brand_name)
    SELECT v_company_id, p_brand_name
    WHERE NOT EXISTS (SELECT 1 FROM product_brand WHERE brand_name = p_brand_name AND company_id = v_company_id);

    -- Get the brand_id for the inserted or existing brand
    SELECT brand_id INTO v_brand_id FROM product_brand WHERE brand_name = p_brand_name AND company_id = v_company_id;

    -- Insert the product with default warehouse and shop quantities
    INSERT INTO products (product_code, category_id, brand_id, product_name, price)
    VALUES (p_product_code, v_category_id, v_brand_id, p_product_name, p_price);

    -- Set default warehouse and shop quantities to zero
    INSERT INTO stock (product_code, warehouse_quantity, shop_quantity)
    VALUES (p_product_code, p_warehouse_quantity, p_shop_quantity);

    SET p_success = TRUE; -- Set success flag
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_add_pending_order_payment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_pending_order_payment`(
    IN p_account_no VARCHAR(30),
    IN p_order_no INT,
    IN p_paid_amount DOUBLE,
    OUT result BOOLEAN
)
BEGIN
    DECLARE alreadyPaidAmount DOUBLE;
    DECLARE totalAmount DOUBLE;
    DECLARE productOrderCompleted BOOLEAN;

    -- Start a transaction
    START TRANSACTION;

    -- Select total_amount and amount_paid
    SELECT total_amount, amount_paid
    INTO totalAmount, alreadyPaidAmount
    FROM purchase_requests
    WHERE request_id = p_order_no;

    -- Check if payment can be made
    IF (alreadyPaidAmount + p_paid_amount <= totalAmount) THEN
        -- Update purchase_requests
        UPDATE purchase_requests
        SET amount_paid = amount_paid + p_paid_amount
        WHERE request_id = p_order_no;

        -- Insert into company_transactions
        INSERT INTO company_transactions (account_no, trans_description, transaction_type, amount)
        VALUES (p_account_no, CONCAT('Paid pending amount for ', p_order_no), 'credit', p_paid_amount);

        -- Update company_accounts balance
        UPDATE company_accounts
        SET balance = balance + p_paid_amount
        WHERE account_no = p_account_no;

        -- Commit the transaction
        COMMIT;

        -- Set the result to TRUE
        SET result = TRUE;
    ELSE
        -- Rollback the transaction if payment cannot be made
        ROLLBACK;

        -- Set the result to FALSE
        SET result = FALSE;
    END IF;

    -- Select amount_paid after the update
    SELECT amount_paid INTO alreadyPaidAmount FROM purchase_requests WHERE request_id = p_order_no;

    SET productOrderCompleted = isRequestCompleted(p_order_no);

    -- Check if payment is completed
    IF (alreadyPaidAmount = totalAmount) THEN
        -- Update purchase_requests payment_status
        UPDATE purchase_requests
        SET payment_status = 'Completed'
        WHERE request_id = p_order_no;
    END IF;

    IF (alreadyPaidAmount = totalAmount AND productOrderCompleted) THEN
        -- Update purchase_requests payment_status
        UPDATE purchase_requests
        SET payment_status = 'Completed'
        WHERE request_id = p_order_no;
    END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_add_petty_cash_record` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_petty_cash_record`(
    IN p_amount DOUBLE,
    IN p_description VARCHAR(200),
    OUT result BOOLEAN
)
BEGIN
    DECLARE exit handler for sqlexception
        BEGIN
            -- Log the error or take appropriate action
            SET result = FALSE;
            ROLLBACK;
        END;

    DECLARE exit handler for sqlwarning
        BEGIN
            -- Log the warning or take appropriate action
            SET result = FALSE;
            ROLLBACK;
        END;

    -- Start the transaction
    START TRANSACTION;

    -- Insert into petty_cash_payments table
    INSERT INTO petty_cash_payments (payment_date, payment_description, amount)
    VALUES(NOW(), p_description, p_amount);

    -- Insert into company_transactions table
    INSERT INTO company_transactions (account_no, transaction_date, trans_description, transaction_type, amount)
    VALUES('0000-000000', NOW(), p_description, 'debit', p_amount);

    -- Update company_accounts table
    UPDATE company_accounts
    SET balance = balance - p_amount
    WHERE account_no = '0000-000000';

    -- If no errors occurred, commit the transaction
    COMMIT;

    -- Set result to true indicating success
    SET result = TRUE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_add_stock_transfer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_add_stock_transfer`(
    IN cartItems JSON,
    IN fromStock VARCHAR(30),
    IN toPlace VARCHAR(30),
    OUT result BOOLEAN
)
BEGIN
    DECLARE itemCount INT;
    DECLARE i INT DEFAULT 0;
    DECLARE productCode VARCHAR(30);
    DECLARE quantity DOUBLE;
    DECLARE lastTransferId INT;
    DECLARE success BOOLEAN DEFAULT FALSE;

    -- Variable to track success/failure
    SET result = FALSE;

    -- Get the number of items in the cart
    SET itemCount = JSON_LENGTH(cartItems);

    -- Create a new stock transfer record
    INSERT INTO stock_transfer (from_place, to_place, transfer_date)
    VALUES (fromStock, toPlace, NOW());

    -- Get the last inserted transfer_id
    SET lastTransferId = LAST_INSERT_ID();

    -- Loop through each item in the cart
    WHILE i < itemCount DO
            -- Get the values for each item
            SET productCode = JSON_UNQUOTE(JSON_EXTRACT(cartItems, CONCAT('$[', i, '].productCode.value')));
            SET quantity = JSON_UNQUOTE(JSON_EXTRACT(cartItems, CONCAT('$[', i, '].quantity.value')));

            -- Insert the item into the stock_transfer_detail table
            BEGIN
                -- Use a BEGIN...END block for error handling
                DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
                    BEGIN
                        -- Set success to FALSE on SQL exception
                        SET result := FALSE;
                    END;

                INSERT INTO stock_transfer_detail (transfer_id, product_code, quantity)
                VALUES (lastTransferId, productCode, quantity);
            END;

            -- Update stock levels in the 'stock' table based on 'toPlace'
            -- Update stock levels in the 'stock' table based on 'toPlace'
            IF toPlace = 'Warehouse' THEN
                UPDATE stock
                SET warehouse_quantity = warehouse_quantity + quantity,
                    shop_quantity = shop_quantity - quantity
                WHERE product_code = productCode;
            ELSEIF toPlace = 'Shop' THEN
                UPDATE stock
                SET shop_quantity = shop_quantity + quantity,
                    warehouse_quantity = warehouse_quantity - quantity
                WHERE product_code = productCode;
            END IF;

            -- Increment the counter
            SET i = i + 1;
        END WHILE;

    -- Set the result to true if the procedure execution is successful
    SET result = TRUE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_admin_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_admin_login`(
    IN p_username VARCHAR(30),
    IN p_password VARCHAR(30),
    OUT result BOOLEAN
)
BEGIN
    SELECT EXISTS(
        SELECT username FROM admin_login
        WHERE `password` = p_password AND username = p_username
    )INTO result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_account_holder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_account_holder`(
    IN p_holder_name VARCHAR(50),
    IN p_cnic_no BIGINT,
    IN p_address VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_is_retailer BOOLEAN,
    OUT p_success BOOLEAN
)
BEGIN
    -- Attempt to insert a new account holder into the table
    INSERT INTO account_holders (cnic_no, holder_name, address, phone, is_retailer)
    VALUES (p_cnic_no, p_holder_name, p_address, p_phone, p_is_retailer);

    -- Check if the insertion was successful
    SET p_success = (ROW_COUNT() = 1);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_account_holder_by_excel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_account_holder_by_excel`(
    IN p_holder_name VARCHAR(50),
    IN p_cnic_no BIGINT,
    IN p_address VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_is_retailer BOOLEAN,
    IN p_balance DOUBLE,
    OUT p_success BOOLEAN
)
BEGIN
    DECLARE exit handler for sqlexception
        BEGIN
            ROLLBACK;
            SET p_success = FALSE;
        END;

    DECLARE exit handler for sqlwarning
        BEGIN
            ROLLBACK;
            SET p_success = FALSE;
        END;

    START TRANSACTION;

    -- Attempt to insert a new account holder into the table
    INSERT INTO account_holders (cnic_no, holder_name, address, phone, is_retailer)
    VALUES (p_cnic_no, p_holder_name, p_address, p_phone, p_is_retailer);

    -- Check if the insertion was successful
    IF ROW_COUNT() = 1 THEN
        -- Insert into holder_debit_credit with positive amount
        INSERT INTO holder_debit_credit (cnic_no, transaction_kind, trans_description, amount)
        VALUES (p_cnic_no, IF(p_balance < 0, 'debit', 'credit'), 'Closing balance.', ABS(p_balance));

        SET p_success = TRUE;
        COMMIT;
    ELSE
        ROLLBACK;
        SET p_success = FALSE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_company_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_company_account`(
    IN p_account_no VARCHAR(255),
    IN p_account_holder_name VARCHAR(255),
    IN p_bank_name VARCHAR(255),
    IN p_balance DOUBLE,
    OUT p_success BOOLEAN
)
BEGIN
    DECLARE account_exists INT;
    DECLARE continue_handling BOOLEAN DEFAULT TRUE;

    -- Start transaction
    START TRANSACTION;

    -- Check if the account already exists based on account number
    SELECT COUNT(*) INTO account_exists FROM company_accounts WHERE account_no = p_account_no;

    IF account_exists = 0 THEN
        -- Account does not exist, insert a new record
        INSERT INTO company_accounts (account_no, account_name, bank_name, balance)
        VALUES (p_account_no, p_account_holder_name, p_bank_name, p_balance);

        -- Check for errors in the last operation
        IF ROW_COUNT() = 1 THEN
            INSERT INTO company_transactions (account_no, trans_description, transaction_type, amount)
            VALUES (p_account_no, 'Opening Balance', 'credit', p_balance);

            -- Check for errors in the last operation
            IF ROW_COUNT() = 1 THEN
                SET p_success = TRUE;
            ELSE
                SET continue_handling = FALSE;
            END IF;
        ELSE
            SET continue_handling = FALSE;
        END IF;
    ELSE
        -- Account already exists, set p_success to FALSE
        SET p_success = FALSE;
    END IF;

    -- Check if we need to handle errors and rollback
    IF continue_handling = FALSE THEN
        -- Rollback the transaction
        ROLLBACK;
        SET p_success = FALSE;
    ELSE
        -- Commit the transaction
        COMMIT;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_purchase_request_with_wholesaler` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_purchase_request_with_wholesaler`(
    IN cartItems JSON,
    IN totalAmount DOUBLE,
    IN paidAmount DOUBLE,
    IN paymentType VARCHAR(30),
    IN accountNo VARCHAR(30),
    IN sourceId BIGINT,
    IN paymentStatus VARCHAR(30),
    IN freightAmount DOUBLE,
    IN p_description VARCHAR(200),
    OUT result BOOLEAN
)
BEGIN
    DECLARE accountHolderName VARCHAR(50);
    DECLARE success BOOLEAN DEFAULT TRUE; -- Variable to track success/failure
    DECLARE lastRequestId INT; -- Declare the variable
    DECLARE resultSuccess BOOLEAN DEFAULT TRUE; -- Variable to track sp_insert_purchase_request_cart success
    DECLARE currentBalance DOUBLE;
    DECLARE enoughBalance BOOLEAN;

    -- Deduct amount from the given account no by entering an appropriate description.
    IF paymentType != 'Post Payment' THEN

        SELECT balance INTO currentBalance
        FROM company_accounts
        WHERE account_no = accountNo;

        IF currentBalance >= totalAmount THEN
            SET enoughBalance = TRUE;
        ELSE
            SET enoughBalance = FALSE;
        END IF;

        IF enoughBalance THEN
            -- Get the company Name.
            SELECT holder_name INTO accountHolderName FROM account_holders WHERE cnic_no = sourceId;

            -- Add Transaction record into company_transactions table
            BEGIN
                -- Use a BEGIN...END block for error handling
                DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
                    BEGIN
                        -- Set success to FALSE on SQL exception
                        SET success := FALSE;
                    END;

                INSERT INTO company_transactions (account_no, transaction_date, trans_description, transaction_type, amount)
                VALUES (accountNo, NOW(), CONCAT('Paid to ' , accountHolderName, ' by ', paymentType,  ' for ', p_description), 'debit', paidAmount);
            END;

            -- Update balance
            UPDATE company_accounts
            SET balance = balance - paidAmount
            WHERE account_no = accountNo;

            -- Set the lastRequestId for further use
            SET lastRequestId = LAST_INSERT_ID();
        ELSE
            -- If there's not enough balance, set success to FALSE
            SET success := FALSE;
        END IF;
    END IF;

    -- Amount deducted successfully or no deduction needed, insert data into the purchase request table
    BEGIN
        -- Use a BEGIN...END block for error handling
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
            BEGIN
                -- Set success to FALSE on SQL exception
                SET success := FALSE;
            END;

        INSERT INTO purchase_requests
        (request_date, amount_paid, total_amount, payment_method, source_account_holder_id,
         source_company_id, order_status, payment_status, freight)
        VALUES (NOW(), paidAmount, totalAmount, paymentType, sourceId, NULL, 'Pending', paymentStatus, freightAmount);

        -- Get the last inserted request_id
        SET lastRequestId = LAST_INSERT_ID();
    END;

    -- Fill the purchase request cart with the given JSON
    CALL sp_insert_purchase_request_cart(cartItems, lastRequestId, @resultSuccess);

    -- Set success to the result of sp_insert_purchase_request_cart
    SET success = success AND @resultSuccess;

    -- Set the overall result based on success
    SET result = success;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_purchas_request_with_company` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_purchas_request_with_company`(
    IN cartItems JSON,
    IN totalAmount DOUBLE,
    IN paidAmount DOUBLE,
    IN requestType VARCHAR(30),
    IN paymentType VARCHAR(30),
    IN accountNo VARCHAR(30),
    IN sourceId INT,
    IN paymentStatus VARCHAR(30),
    IN p_description VARCHAR(200),
    OUT result BOOLEAN
)
BEGIN
    DECLARE companyName VARCHAR(50);
    DECLARE success BOOLEAN DEFAULT TRUE; -- Variable to track success/failure
    DECLARE lastRequestId INT; -- Declare the variable
    DECLARE resultSuccess BOOLEAN DEFAULT TRUE; -- Variable to track sp_insert_purchase_request_cart success
    DECLARE currentBalance DOUBLE;
    DECLARE enoughBalance BOOLEAN;

    -- Deduct amount from the given account no by entering an appropriate description.
    IF paymentType != 'Post Payment' THEN

        SELECT balance INTO currentBalance
        FROM company_accounts
        WHERE account_no = accountNo;

        IF currentBalance >= totalAmount THEN
            SET enoughBalance = TRUE;
        ELSE
            SET enoughBalance = FALSE;
        END IF;

        IF enoughBalance THEN
            -- Get the company Name.
            SELECT company_name INTO companyName FROM companies WHERE company_id = sourceId;

            -- Add Transaction record into company_transactions table
            BEGIN
                -- Use a BEGIN...END block for error handling
                DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
                    BEGIN
                        -- Set success to FALSE on SQL exception
                        SET success := FALSE;
                    END;

                INSERT INTO company_transactions (account_no, transaction_date, trans_description, transaction_type, amount)
                VALUES (accountNo, NOW(), CONCAT('Paid for ', requestType, ' of ', p_description, ' to ', companyName, ' by ', paymentType), 'debit', paidAmount);
            END;

            -- Update balance
            UPDATE company_accounts
            SET balance = balance - paidAmount
            WHERE account_no = accountNo;

            -- Set the lastRequestId for further use
            SET lastRequestId = LAST_INSERT_ID();
        ELSE
            -- If there's not enough balance, set success to FALSE
            SET success := FALSE;
        END IF;
    END IF;

    -- Amount deducted successfully or no deduction needed, insert data into the purchase request table
    BEGIN
        -- Use a BEGIN...END block for error handling
        DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
            BEGIN
                -- Set success to FALSE on SQL exception
                SET success := FALSE;
            END;

        INSERT INTO purchase_requests
        (request_date, amount_paid, total_amount,  request_type,  payment_method, source_account_holder_id,
         source_company_id, order_status, payment_status)
        VALUES (NOW(), paidAmount, totalAmount, requestType, paymentType, NULL, sourceId, 'Pending', paymentStatus);

        -- Get the last inserted request_id
        SET lastRequestId = LAST_INSERT_ID();
    END;

    -- Fill the purchase request cart with the given JSON
    CALL sp_insert_purchase_request_cart(cartItems, lastRequestId, @resultSuccess);

    -- Set success to the result of sp_insert_purchase_request_cart
    SET success = success AND @resultSuccess;

    -- Set the overall result based on success
    SET result = success;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_create_walk_in_bill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create_walk_in_bill`(
    IN p_customer_name VARCHAR(50),
    IN p_account_no VARCHAR(50),
    IN p_total_bill DOUBLE,
    IN p_description VARCHAR(200),
    IN p_cart_items JSON,
    OUT p_success BOOLEAN,
    OUT p_bill_id INT
)
BEGIN
    DECLARE v_itemCount INT;
    DECLARE v_i INT DEFAULT 0;
    DECLARE v_productCode VARCHAR(30);
    DECLARE v_quantity FLOAT;
    DECLARE v_amount DOUBLE;

    -- Variable to track success/failure
    SET p_success = TRUE;
    SET p_bill_id = 0; -- Initialize to 0

    -- Get the number of items in the cart
    SET v_itemCount = JSON_LENGTH(p_cart_items);

    -- Create a new walk-in bill record
    INSERT INTO walk_in_bills (customer_name, bill_date, total_amount, payment_type)
    VALUES (p_customer_name, NOW(), p_total_bill, p_account_no);

    -- Get the last inserted bill_id
    SET @lastBillId = LAST_INSERT_ID();
    SET p_bill_id = @lastBillId;

    -- Loop through each item in the cart
    WHILE v_i < v_itemCount DO
            -- Get the values for each item
            SET v_productCode = JSON_UNQUOTE(JSON_EXTRACT(p_cart_items, CONCAT('$[', v_i, '].productCode.value')));
            SET v_quantity = JSON_UNQUOTE(JSON_EXTRACT(p_cart_items, CONCAT('$[', v_i, '].quantity.value')));
            SET v_amount = JSON_UNQUOTE(JSON_EXTRACT(p_cart_items, CONCAT('$[', v_i, '].pricePerUnit.value')));

            -- Insert the item into the walk_in_bill_details table
            BEGIN
                -- Use a BEGIN...END block for error handling
                DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
                    BEGIN
                        -- Set p_success to FALSE on SQL exception
                        SET p_success := FALSE;
                    END;

                INSERT INTO walk_in_bill_details (bill_id, product_code, quantity, amount)
                VALUES (@lastBillId, v_productCode, v_quantity, v_amount);
            END;

            -- Increment the counter
            SET v_i = v_i + 1;
        END WHILE;

    -- Insert data into company_transactions
    INSERT INTO company_transactions (account_no, transaction_date, trans_description, transaction_type, amount)
    VALUES (p_account_no, NOW(), p_description, 'credit', p_total_bill);

    -- Get the last inserted transaction_id
    -- SET @lastTransactionId = LAST_INSERT_ID();

    -- Update company_accounts balance
    UPDATE company_accounts
    SET balance = balance + p_total_bill
    WHERE account_no = p_account_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_delete_account_holder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_account_holder`(
    IN p_cnic_no BIGINT,
    OUT result BOOLEAN
)
BEGIN
    SET result = FALSE;

    DELETE FROM account_holders WHERE cnic_no = p_cnic_no;

    IF ROW_COUNT() > 0 THEN
        SET result = TRUE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_delete_company_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_company_account`(
    IN p_account_no VARCHAR(255),
    OUT p_success BOOLEAN
)
BEGIN
    -- Attempt to delete the account based on the account number
    DELETE FROM company_accounts WHERE account_no = p_account_no;

    -- Check if any rows were affected
    IF ROW_COUNT() > 0 THEN
        SET p_success = TRUE;
    ELSE
        SET p_success = FALSE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_delete_product` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_product`(
    IN p_product_code VARCHAR(30),
    OUT result BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            -- An error occurred, set the result to FALSE
            SET result = FALSE;
            ROLLBACK; -- Rollback the transaction in case of an error
        END;

    -- Start a transaction
    START TRANSACTION;

    -- Initialize result to TRUE
    SET result = TRUE;

    -- Check if the product exists
    IF EXISTS (SELECT 1 FROM products WHERE product_code = p_product_code) THEN
        -- Delete the product from the 'products' table
        DELETE FROM products WHERE product_code = p_product_code;

        -- Check if the product was deleted successfully
        IF ROW_COUNT() > 0 THEN
            -- Delete related stock information
            DELETE FROM stock WHERE product_code = p_product_code;
        ELSE
            -- Product not found, set the result to FALSE
            SET result = FALSE;
        END IF;
    ELSE
        -- Product not found, set the result to FALSE
        SET result = FALSE;
    END IF;

    -- Commit the transaction
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_account_holder_names` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_account_holder_names`()
BEGIN
    SELECT holder_name FROM account_holders;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_all_account_holders_info` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_all_account_holders_info`()
BEGIN
    -- Retrieve data about all account holders including balance and transaction type
    SELECT
        ah.holder_name,
        ah.cnic_no,
        ah.address,
        ah.phone AS contact,
        ah.is_retailer AS is_retailer,
        IFNULL(
                CASE
                    WHEN balances.balance < 0 THEN 'Debit'
                    ELSE 'Credit'
                    END,
                'Credit'
        ) AS transaction_type,
        ABS(IFNULL(balances.balance, 0)) AS balance
    FROM account_holders AS ah
             LEFT JOIN (
        SELECT
            dc.cnic_no,
            SUM(
                    CASE
                        WHEN dc.transaction_kind = 'Debit' THEN -dc.amount
                        WHEN dc.transaction_kind = 'Credit' THEN dc.amount
                        ELSE 0
                        END
            ) AS balance
        FROM holder_debit_credit AS dc
        GROUP BY dc.cnic_no
    ) AS balances ON ah.cnic_no = balances.cnic_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_all_company_accounts` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_all_company_accounts`()
BEGIN
    SELECT * FROM company_accounts;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_all_miscellaneous_payments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_all_miscellaneous_payments`(
    IN p_date DATE
)
BEGIN
    SELECT payment_description, amount
    FROM petty_cash_payments
    WHERE DATE(payment_date) = p_date;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_all_pending_payments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_all_pending_payments`()
BEGIN
    SELECT * FROM pending_order_payments_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_balance_by_holder_id` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_balance_by_holder_id`(
    IN p_holder_id BIGINT,
    OUT p_balance DOUBLE
)
BEGIN
    DECLARE exit handler for sqlexception
        BEGIN
            -- Log the error or take appropriate action
            SET p_balance = NULL;
        END;

    DECLARE exit handler for sqlwarning
        BEGIN
            -- Log the warning or take appropriate action
            SET p_balance = NULL;
        END;

    -- Get the balance for the specified holder_id
    SELECT
        IFNULL(
                SUM(
                        CASE
                            WHEN dc.transaction_kind = 'debit' THEN -dc.amount
                            WHEN dc.transaction_kind = 'credit' THEN dc.amount
                            ELSE 0
                            END
                ), 0
        ) INTO p_balance
    FROM holder_debit_credit AS dc
    WHERE dc.cnic_no = p_holder_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_companies_names` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_companies_names`()
BEGIN
    SELECT company_name
    FROM companies;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_company_bank_accounts_names` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_company_bank_accounts_names`()
BEGIN
    SELECT CONCAT_WS(' -> ', account_no, bank_name) AS bank_name
    FROM company_accounts;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_debit_credit_report` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_debit_credit_report`(
    IN p_account_holder VARCHAR(30)
)
BEGIN
    DECLARE p_cnic BIGINT;

    SET @running_balance := 0;

    SELECT cnic_no INTO p_cnic FROM account_holders WHERE holder_name = p_account_holder;

    SELECT
        DATE(t_date) AS t_date,
        trans_description,
        IF(transaction_kind = 'credit', amount, NULL) AS credit,
        IF(transaction_kind = 'debit', amount, NULL) AS debit,
        @running_balance := @running_balance + IF(transaction_kind = 'credit', amount, -amount) AS balance
    FROM
        holder_debit_credit
    WHERE
        cnic_no = p_cnic
    ORDER BY
        t_date, transaction_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_debit_credit_report_by_cnic` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_debit_credit_report_by_cnic`(
    IN p_cnic BIGINT
)
BEGIN
    SET @running_balance := 0;

    SELECT
        DATE(t_date) AS t_date,
        trans_description,
        IF(transaction_kind = 'credit', amount, NULL) AS credit,
        IF(transaction_kind = 'debit', amount, NULL) AS debit,
        @running_balance := @running_balance + IF(transaction_kind = 'credit', amount, -amount) AS balance
    FROM
        holder_debit_credit
    WHERE
        cnic_no = p_cnic
    ORDER BY
        t_date, transaction_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_debit_credit_report_by_name` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_debit_credit_report_by_name`(
    IN p_account_holder VARCHAR(30)
)
BEGIN
    DECLARE p_cnic BIGINT;

    SET @running_balance := 0;

    SELECT cnic_no INTO p_cnic FROM account_holders WHERE holder_name = p_account_holder;

    SELECT
        DATE(t_date) AS t_date,
        trans_description,
        IF(transaction_kind = 'credit', amount, NULL) AS credit,
        IF(transaction_kind = 'debit', amount, NULL) AS debit,
        @running_balance := @running_balance + IF(transaction_kind = 'credit', amount, -amount) AS balance
    FROM
        holder_debit_credit
    WHERE
        cnic_no = p_cnic
    ORDER BY
        t_date, transaction_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_debit_credit_report_of_company_accounts` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_debit_credit_report_of_company_accounts`(
    IN p_account_no VARCHAR(50)
)
BEGIN
    SET @running_balance := 0;

    SELECT
        DATE(transaction_date) AS t_date,
        trans_description,
        IF(transaction_type = 'credit', amount, NULL) AS credit,
        IF(transaction_type = 'debit', amount, NULL) AS debit,
        @running_balance := @running_balance + IF(transaction_type = 'credit', amount, -amount) AS balance
    FROM
        company_transactions
    WHERE
        account_no = p_account_no
    ORDER BY
        t_date, transaction_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_order_no_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_order_no_list`()
BEGIN
    SELECT request_id AS order_no
    FROM purchase_requests
    WHERE order_status = 'Pending';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_over_invoices` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_over_invoices`()
BEGIN
    SELECT * FROM today_over_invoice_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_over_invoice_product_codes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_over_invoice_product_codes`()
BEGIN
    SELECT product_code FROM today_over_invoice_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_pending_order_numbers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_pending_order_numbers`()
BEGIN
    SELECT request_id FROM pending_order_payments_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_products_by_company` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_products_by_company`(
    IN p_company_name VARCHAR(50)
)
BEGIN
    SELECT product_code
    FROM product_view
    WHERE company = p_company_name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_products_codes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_products_codes`()
BEGIN
    SELECT product_code AS product_code FROM products;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_product_code_list` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_product_code_list`(
    IN p_order_no INT
)
BEGIN
    SELECT product_code
    FROM purchase_request_cart
             JOIN purchase_requests
                  USING (request_id)
    WHERE p_status = 'Pending' AND request_id = p_order_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_product_company_address_and_contact` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_product_company_address_and_contact`(
    IN p_product_code VARCHAR(30),
    OUT p_address VARCHAR(100),
    OUT p_phone VARCHAR(15)
)
BEGIN
    SELECT address, phone
    INTO p_address, p_phone
    FROM products
             JOIN product_brand USING (brand_id)
             JOIN companies USING (company_id)
    WHERE product_code = p_product_code;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_product_data` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_product_data`(
    IN p_product_code VARCHAR(30)
)
BEGIN
    SELECT p.product_name, pb.brand_name AS brand, c.company_name AS company, p.price
    FROM products p
             JOIN product_brand pb ON p.brand_id = pb.brand_id
             JOIN companies c ON c.company_id = pb.company_id
    WHERE p.product_code = p_product_code;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_product_info_with_stock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_product_info_with_stock`()
BEGIN
    SELECT
        p.product_code,
        p.product_name,
        pc.category_name AS category,
        pb.brand_name AS brand,
        c.company_name AS company,
        p.price,
        s.warehouse_quantity,
        s.shop_quantity
    FROM
        products p
            INNER JOIN
        product_category pc ON p.category_id = pc.category_id
            INNER JOIN
        product_brand pb ON p.brand_id = pb.brand_id
            INNER JOIN
        companies c ON pb.company_id = c.company_id
            INNER JOIN
        stock s ON p.product_code = s.product_code;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_purchase_requests` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_purchase_requests`()
BEGIN
    SELECT * FROM purchase_requests_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_stock_arrival_history` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_stock_arrival_history`()
BEGIN
    SELECT * FROM stock_arrival_history_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_stock_depletion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_stock_depletion`()
BEGIN
    SELECT * FROM today_sale_view;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_stock_transfer_history` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_stock_transfer_history`()
BEGIN
    SELECT * FROM stock_transfer_history;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_today_online_payments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_today_online_payments`(
    IN p_date DATE
)
BEGIN
    SELECT ca.bank_name, ct.amount * CASE WHEN ct.transaction_type = 'debit' THEN -1 ELSE 1 END AS amount,
           ca.account_no, ca.account_name AS holder_name
    FROM company_transactions ct
             RIGHT JOIN company_accounts ca ON ct.account_no = ca.account_no
    WHERE DATE(ct.transaction_date) = p_date AND ca.account_no <> '0000-000000'
    ORDER BY ct.transaction_date DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_today_sales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_today_sales`(
    IN p_date DATE
)
BEGIN
    -- Temporary table to store the combined results
    CREATE TEMPORARY TABLE IF NOT EXISTS temp_sales (
                                                        product_name VARCHAR(100),
                                                        quantity FLOAT,
                                                        customer_name VARCHAR(50),
                                                        payment_type VARCHAR(15),
                                                        amount DOUBLE
    );

    -- Fetch sales from walk_in_bills and walk_in_bill_details
    INSERT INTO temp_sales
    SELECT p.product_name, wbd.quantity, wib.customer_name, ca.bank_name, (wbd.quantity * wbd.amount) AS amount
    FROM walk_in_bills wib
             JOIN walk_in_bill_details wbd ON wib.bill_id = wbd.bill_id
             JOIN products p ON wbd.product_code = p.product_code
             JOIN company_accounts ca ON wib.payment_type = ca.account_no
    WHERE DATE(wib.bill_date) = p_date;

    -- Fetch sales from account_holder_bills and account_holder_bill_cart
    INSERT INTO temp_sales
    SELECT p.product_name, ahbc.quantity, ah.holder_name, ca.bank_name, (ahbc.quantity * ahbc.amount) AS amount
    FROM account_holder_bills ahb
             JOIN account_holder_bill_cart ahbc ON ahb.bill_id = ahbc.bill_id
             JOIN account_holders ah ON ahb.cnic_no = ah.cnic_no
             JOIN products p ON ahbc.product_code = p.product_code
             JOIN company_accounts ca ON ahb.payment_type = ca.account_no
    WHERE DATE(ahb.bill_date) = p_date;

    -- Return the combined results
    SELECT * FROM temp_sales;

    -- Drop the temporary table
    DROP TEMPORARY TABLE IF EXISTS temp_sales;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_today_stock_arrivals` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_today_stock_arrivals`(
    IN p_date DATE
)
BEGIN
    SELECT product_name, SUM(sh_quantity + wh_quantity) AS quantity, ordered_from
    FROM stock_arrival_history_view
    WHERE DATE(arrive_date) = p_date
    GROUP BY product_name, ordered_from;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_user_previous_balance` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_user_previous_balance`(
    IN p_cnic_no BIGINT,
    OUT p_total_amount DOUBLE
)
BEGIN
    SELECT
        COALESCE(
                CASE
                    WHEN SUM(amount) >= 0 THEN ABS(SUM(amount))
                    ELSE -ABS(SUM(amount))
                    END,
                0
        ) AS total_amount
    INTO
        p_total_amount
    FROM holder_debit_credit
    WHERE cnic_no = p_cnic_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_get_wholesalers_names` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_get_wholesalers_names`()
BEGIN
    SELECT holder_name FROM account_holders WHERE is_retailer is TRUE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insert_account_holder_bill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_account_holder_bill`(
    IN p_holder_no BIGINT,
    IN p_account_no VARCHAR(50),
    IN p_total_bill DOUBLE,
    IN p_cart_items JSON,
    IN p_description VARCHAR(200),
    IN p_paid_amount DOUBLE,
    OUT result BOOLEAN,
    OUT p_bill_no INT
)
BEGIN
    DECLARE v_bill_id INT;
    DECLARE v_remaining_amount DOUBLE;
    DECLARE i INT DEFAULT 0;
    DECLARE exit handler FOR SQLEXCEPTION
        BEGIN
            -- Rollback the transaction in case of an error
            ROLLBACK;
            -- Set result to FALSE in case of an error
            SET result = FALSE;
        END;

    -- Start a transaction
    START TRANSACTION;

    SET p_bill_no = NULL;

    -- Insert into account_holder_bills table
    INSERT INTO account_holder_bills (cnic_no, total_amount, payment_type)
    VALUES (p_holder_no, p_total_bill, p_account_no);

    SET v_bill_id = LAST_INSERT_ID();

    SET p_bill_no = v_bill_id; -- out bill id

    -- Insert into account_holder_bill_cart table
    WHILE i < JSON_LENGTH(p_cart_items) DO
            INSERT INTO account_holder_bill_cart (bill_id, product_code, quantity, amount)
            VALUES (v_bill_id,
                    JSON_UNQUOTE(JSON_EXTRACT(p_cart_items, CONCAT('$[', i, '].productCode.value'))),
                    JSON_UNQUOTE(JSON_EXTRACT(p_cart_items, CONCAT('$[', i, '].quantity.value'))),
                    JSON_UNQUOTE(JSON_EXTRACT(p_cart_items, CONCAT('$[', i, '].pricePerUnit.value'))));
            SET i = i + 1;
        END WHILE;

    -- Determine if credit or debit should be added to holder_debit_credit
    SET v_remaining_amount = p_total_bill - p_paid_amount;
    IF v_remaining_amount > 0 THEN
        INSERT INTO holder_debit_credit (bill_id, cnic_no, transaction_kind, trans_description, amount)
        VALUES (v_bill_id, p_holder_no, 'debit', p_description, v_remaining_amount);
    ELSEIF v_remaining_amount < 0 THEN
        INSERT INTO holder_debit_credit (bill_id, cnic_no, transaction_kind, trans_description, amount)
        VALUES (v_bill_id, p_holder_no, 'credit', p_description, -v_remaining_amount);
    END IF;

    -- Insert into company_transactions and update company_accounts if paid amount is greater than 0
    IF p_paid_amount > 0 THEN
        INSERT INTO company_transactions (account_no, transaction_date, trans_description, transaction_type, amount)
        VALUES (p_account_no, NOW(), p_description, 'credit', p_paid_amount);

        -- Update company_accounts balance
        UPDATE company_accounts
        SET balance = balance + p_paid_amount
        WHERE account_no = p_account_no;
    END IF;

    -- Commit the transaction
    COMMIT;

    -- Return the result
    SET result = TRUE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insert_empty_bill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_empty_bill`(
    IN p_holder_id BIGINT,
    IN p_account_no VARCHAR(50),
    IN p_amount DOUBLE,
    IN p_description VARCHAR(200),
    IN p_payment_type VARCHAR(15),
    OUT p_emp_bill_id VARCHAR(40)
)
BEGIN
    DECLARE result BOOLEAN DEFAULT TRUE;
    -- Get the last inserted emp_bill_id
    DECLARE id_out VARCHAR(50);

    DECLARE exit handler FOR SQLEXCEPTION
        BEGIN
            -- Rollback the transaction in case of an error
            ROLLBACK;
            -- Set result to FALSE in case of an error
            SET result = FALSE;
        END;

    -- Start a transaction
    START TRANSACTION;

    SET p_emp_bill_id = NULL;

    -- Determine the transaction type based on payment type
    SET @v_transaction_type = IF(p_payment_type = 'debit', 'debit', 'credit');

    -- Insert into empty_bills table
    INSERT INTO empty_bills (cnic_no, total_amount, payment_type)
    VALUES (p_holder_id, p_amount, p_payment_type);


    SELECT emp_bill_id INTO id_out FROM empty_bills WHERE id = LAST_INSERT_ID();

    SET p_emp_bill_id = id_out;

    -- Insert into holder_debit_credit table
    INSERT INTO holder_debit_credit (emp_bill_id, cnic_no, transaction_kind, trans_description, amount)
    VALUES (id_out, p_holder_id, @v_transaction_type, p_description, p_amount);

    -- Insert into company_transactions table
    INSERT INTO company_transactions (account_no, transaction_date, trans_description, transaction_type, amount)
    VALUES (p_account_no, NOW(), p_description, @v_transaction_type, p_amount);

    -- Update company_accounts balance
    UPDATE company_accounts
    SET balance = balance + IF(@v_transaction_type = 'debit', -p_amount, p_amount)
    WHERE account_no = p_account_no;

    -- Commit the transaction
    COMMIT;

    -- Return the result
    SELECT result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insert_over_invoice_stock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_over_invoice_stock`(
    IN p_product_code VARCHAR(30),
    IN p_quantity FLOAT,
    OUT result BOOLEAN
)
BEGIN
    DECLARE rows_updated INT;

    -- Initialize result to FALSE
    SET result = FALSE;

    -- Update stock table
    UPDATE stock
    SET shop_quantity = shop_quantity + p_quantity
    WHERE product_code = p_product_code;

    -- Check if any rows were updated
    SELECT ROW_COUNT() INTO rows_updated;

    -- If rows were updated, set result to TRUE
    IF rows_updated > 0 THEN
        SET result = TRUE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insert_purchase_request_cart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_purchase_request_cart`(
    IN cartItems JSON,
    IN requestID INT,
    OUT success BOOLEAN
)
BEGIN
    DECLARE itemCount INT;
    DECLARE i INT DEFAULT 0;
    DECLARE productCode VARCHAR(30);
    DECLARE quantity DOUBLE;
    DECLARE amount DOUBLE;

    -- Variable to track success/failure
    SET success = TRUE;

    -- Get the number of items in the cart
    SET itemCount = JSON_LENGTH(cartItems);

    -- Loop through each item in the cart
    WHILE i < itemCount DO
            -- Get the values for each item
            SET productCode = JSON_UNQUOTE(JSON_EXTRACT(cartItems, CONCAT('$[', i, '].productCode.value')));
            SET quantity = JSON_UNQUOTE(JSON_EXTRACT(cartItems, CONCAT('$[', i, '].quantity.value')));
            SET amount = JSON_UNQUOTE(JSON_EXTRACT(cartItems, CONCAT('$[', i, '].pricePerUnit.value')));

            -- Insert the item into the purchase_request_cart table
            BEGIN
                -- Use a BEGIN...END block for error handling
                DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
                    BEGIN
                        -- Set success to FALSE on SQL exception
                        SET success := FALSE;
                    END;

                INSERT INTO purchase_request_cart (request_id, product_code, quantity, amount)
                VALUES (requestID, productCode, quantity, amount);
            END;

            -- Increment the counter
            SET i = i + 1;
        END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_insert_stock_arrival` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_stock_arrival`(
    IN p_request_id INT,
    IN p_product_code VARCHAR(30),
    IN p_sh_quantity DOUBLE,
    IN p_wh_quantity DOUBLE,
    IN p_vehicle_no VARCHAR(15),
    IN p_driver_no VARCHAR(12),
    OUT result BOOLEAN
)
BEGIN
    DECLARE leftQuantity DOUBLE;
    DECLARE productOrderCompleted BOOLEAN;
    DECLARE paymentStatus VARCHAR(15);
    DECLARE paymentCompleted BOOLEAN;

    -- Variable for exception handling
    DECLARE exit_handler BOOLEAN DEFAULT FALSE;

    -- Declare EXIT HANDLER for SQLEXCEPTION
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
        BEGIN
            SET result = FALSE;
            SET exit_handler = TRUE;
        END;

    -- Insert stock arrival record
    INSERT INTO
        stock_arrivals (request_id, arrive_date, wh_quantity, sh_quantity, vehicle_no, driver_no, product_code)
    VALUES
        (p_request_id, NOW(), p_wh_quantity, p_sh_quantity, p_vehicle_no, p_driver_no, p_product_code);

    -- Update Stock Level
    UPDATE
        stock s
    SET
        s.shop_quantity = s.shop_quantity + p_sh_quantity,
        s.warehouse_quantity = s.warehouse_quantity + p_wh_quantity
    WHERE
        product_code = p_product_code;

    -- Update purchase_request_cart status if leftQuantity is zero
    SET leftQuantity = getLeftQuantity(p_request_id, p_product_code);
    IF leftQuantity = 0 THEN
        UPDATE purchase_request_cart
        SET p_status = 'Completed'
        WHERE request_id = p_request_id AND product_code = p_product_code;
    END IF;

    -- Check if all products in the order are completed
    SET productOrderCompleted = isRequestCompleted(p_request_id);

    -- Check payment status
    SELECT payment_status INTO paymentStatus
    FROM purchase_requests
    WHERE request_id = p_request_id;

    -- Check if payment is completed
    SET paymentCompleted = (paymentStatus = 'Completed');

    -- Update order status if both product and payment are completed
    IF productOrderCompleted AND paymentCompleted THEN
        UPDATE purchase_requests
        SET order_status = 'Completed'
        WHERE request_id = p_request_id;
    END IF;

    -- Set the OUT parameter to indicate success
    SET result = NOT exit_handler;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_account_holder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_account_holder`(
    IN search_text VARCHAR(50)
)
BEGIN
    -- Retrieve data about all account holders including balance and transaction type
    SELECT
        ah.holder_name,
        ah.cnic_no,
        ah.address,
        ah.phone AS contact,
        ah.is_retailer AS is_retailer,
        IFNULL(
                CASE
                    WHEN balances.balance < 0 THEN 'Debit'
                    ELSE 'Credit'
                    END,
                'Credit'
        ) AS transaction_type,
        ABS(IFNULL(balances.balance, 0)) AS balance
    FROM account_holders AS ah
             LEFT JOIN (
        SELECT
            dc.cnic_no,
            SUM(
                    CASE
                        WHEN dc.transaction_kind = 'Debit' THEN -dc.amount
                        WHEN dc.transaction_kind = 'Credit' THEN dc.amount
                        ELSE 0
                        END
            ) AS balance
        FROM holder_debit_credit AS dc
        GROUP BY dc.cnic_no
    ) AS balances ON ah.cnic_no = balances.cnic_no
    WHERE
        ah.holder_name LIKE CONCAT('%', search_text, '%')
       OR ah.cnic_no LIKE CONCAT('%', search_text, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_company_accounts` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_company_accounts`(
    IN p_search_text VARCHAR(255)
)
BEGIN
    SELECT * FROM company_accounts
    WHERE account_name LIKE CONCAT('%', p_search_text, '%')
       OR account_no LIKE CONCAT('%', p_search_text, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_over_invoices` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_over_invoices`(
    IN p_search_text VARCHAR(30)
)
BEGIN
    SELECT * FROM today_over_invoice_view
    WHERE
        product_code LIKE CONCAT('%', p_search_text, '%') OR
        over_invoiced_quantity LIKE CONCAT('%', p_search_text, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_pending_payments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_pending_payments`(
    IN p_search_text VARCHAR(30)
)
BEGIN
    SELECT *
    FROM pending_order_payments_view
    WHERE
        request_id LIKE CONCAT('%', p_search_text, '%')
       OR ordered_from LIKE CONCAT('%', p_search_text, '%')
       OR amount_paid LIKE CONCAT('%', p_search_text, '%')
       OR total_amount LIKE CONCAT('%', p_search_text, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_product` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_product`(
    IN search_text VARCHAR(50)
)
BEGIN
    SELECT
        p.product_code,
        p.product_name,
        pc.category_name AS category,
        pb.brand_name AS brand,
        c.company_name AS company,
        p.price,
        s.warehouse_quantity,
        s.shop_quantity
    FROM
        products p
            INNER JOIN
        product_category pc ON p.category_id = pc.category_id
            INNER JOIN
        product_brand pb ON p.brand_id = pb.brand_id
            INNER JOIN
        companies c ON pb.company_id = c.company_id
            INNER JOIN
        stock s ON p.product_code = s.product_code
    WHERE
        p.product_code LIKE CONCAT('%', search_text, '%')
       OR p.product_name LIKE CONCAT('%', search_text, '%')
       OR pc.category_name LIKE CONCAT('%', search_text, '%')
       OR pb.brand_name LIKE CONCAT('%', search_text, '%')
       OR c.company_name LIKE CONCAT('%', search_text, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_purchase_requests` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_purchase_requests`(
    IN searchText VARCHAR(50)
)
BEGIN
    SELECT * FROM purchase_requests_view
    WHERE
        request_id LIKE CONCAT('%', searchText, '%') OR
        product_name LIKE CONCAT('%', searchText, '%') OR
        brand_name LIKE CONCAT('%', searchText, '%') OR
        ordered_from LIKE CONCAT('%', searchText, '%') OR
        order_status LIKE CONCAT('%', searchText, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_stock_arrival` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_stock_arrival`(
    IN searchText VARCHAR(50)
)
BEGIN
    SELECT *
    FROM stock_arrival_history_view
    WHERE
        request_id LIKE CONCAT('%', searchText, '%') OR
        product_name LIKE CONCAT('%', searchText, '%') OR
        brand_name LIKE CONCAT('%', searchText, '%') OR
        company_name LIKE CONCAT('%', searchText, '%') OR
        ordered_from LIKE CONCAT('%', searchText, '%') OR
        vehicle_no LIKE CONCAT('%', searchText, '%') OR
        driver_no LIKE CONCAT('%', searchText, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_search_stock_transfer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_search_stock_transfer`(
    IN search_text VARCHAR(50)
)
BEGIN
    SELECT *
    FROM stock_transfer_history
    WHERE
        formatted_transfer_date LIKE CONCAT('%', search_text, '%')
       OR product_name LIKE CONCAT('%', search_text, '%')
       OR brand_name LIKE CONCAT('%', search_text, '%')
       OR company_name LIKE CONCAT('%', search_text, '%')
       OR from_place LIKE CONCAT('%', search_text, '%')
       OR to_place LIKE CONCAT('%', search_text, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_update_account_holder_info` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_account_holder_info`(
    IN p_holder_name VARCHAR(50),
    IN p_cnic_no BIGINT,
    IN p_address VARCHAR(100),
    IN p_phone VARCHAR(15),
    IN p_is_retailer BOOLEAN,
    OUT result BOOLEAN
)
BEGIN
    SET result = FALSE;

    UPDATE account_holders
    SET
        holder_name = p_holder_name,
        address = p_address,
        phone = p_phone,
        is_retailer = p_is_retailer
    WHERE
        cnic_no = p_cnic_no;

    IF ROW_COUNT() > 0 THEN
        SET result = TRUE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_update_company_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_company_account`(
    IN p_account_no VARCHAR(255),
    IN p_account_holder_name VARCHAR(255),
    IN p_bank_name VARCHAR(255),
    IN p_balance DOUBLE
)
BEGIN
    -- Update the account details based on the account number
    UPDATE company_accounts
    SET account_name = p_account_holder_name,
        bank_name = p_bank_name,
        balance = p_balance
    WHERE account_no = p_account_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_update_product_info` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_product_info`(
    IN p_product_code VARCHAR(30),
    IN p_product_name VARCHAR(50),
    IN p_category_name VARCHAR(50),
    IN p_brand_name VARCHAR(50),
    IN p_price DOUBLE,
    IN p_company_name VARCHAR(100),
    IN p_address VARCHAR(100),
    IN p_phone VARCHAR(15),
    OUT p_success BOOLEAN
)
BEGIN
    -- Declare variables to store IDs and the result
    DECLARE v_category_id INT;
    DECLARE v_brand_id INT;
    DECLARE v_company_id INT;
    DECLARE v_result BOOLEAN;

    -- Initialize the result variable to false
    SET v_result = FALSE;

    -- Get product_id, brand_id, category_id
    SELECT brand_id, company_id INTO v_brand_id, v_company_id
    FROM products
             JOIN product_brand USING(brand_id)
             JOIN companies USING(company_id)
    WHERE product_code = p_product_code;

    -- Check if the specified category exists
    SELECT category_id INTO v_category_id
    FROM product_category
    WHERE category_name = p_category_name;

    -- If the category does not exist, insert it
    IF v_category_id IS NULL THEN
        INSERT INTO product_category (category_name)
        VALUES (p_category_name);
        -- Retrieve the newly inserted category's ID
        SET v_category_id = LAST_INSERT_ID();
    END IF;

    -- Update product information
    UPDATE products
    SET
        product_name = p_product_name,
        price = p_price,
        category_id = v_category_id
    WHERE product_code = p_product_code;

    -- Update brand information (or insert if it doesn't exist)
    UPDATE product_brand
    SET
        brand_name = p_brand_name
    WHERE
        brand_id = v_brand_id;


    -- Update companies information (or insert if it doesn't exist)
    UPDATE companies
    SET
        company_name = p_company_name,
        address = p_address,
        phone = p_phone
    WHERE
        company_id = v_company_id;

    -- Set the result to true if the update was successful
    IF ROW_COUNT() > 0 THEN
        SET v_result = TRUE;
    END IF;

    -- Assign the result to the OUT parameter
    SET p_success = v_result;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_update_stock` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_stock`(
    IN p_bill_no INT,
    IN p_product_code VARCHAR(30),
    IN p_shop_quantity FLOAT,
    IN p_wh_quantity FLOAT,
    IN p_walk_in_bill BOOLEAN,
    IN p_account_holder_bill BOOLEAN,
    OUT result BOOLEAN
)
BEGIN
    DECLARE affected_rows INT;

    -- Update stock quantities
    UPDATE stock
    SET warehouse_quantity = warehouse_quantity + p_wh_quantity,
        shop_quantity = shop_quantity + p_shop_quantity
    WHERE product_code = p_product_code;

    -- Check if stock update was successful
    SET affected_rows = ROW_COUNT();
    IF affected_rows > 0 THEN

        -- Update p_status in the appropriate bill details table
        IF p_walk_in_bill THEN
            UPDATE walk_in_bill_details
            SET p_status = 'Completed'
            WHERE bill_id = p_bill_no AND product_code = p_product_code;
        ELSEIF p_account_holder_bill THEN
            UPDATE account_holder_bill_cart
            SET p_status = 'Completed'
            WHERE bill_id = p_bill_no AND product_code = p_product_code;
        END IF;

        -- Check if bill details update was successful
        SET affected_rows = ROW_COUNT();
        IF affected_rows > 0 THEN
            SET result = TRUE;
        ELSE
            SET result = FALSE;
        END IF;
    ELSE
        SET result = FALSE;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `pending_order_payments_view`
--

/*!50001 DROP VIEW IF EXISTS `pending_order_payments_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `pending_order_payments_view` AS select `pr`.`request_id` AS `request_id`,coalesce(`c`.`company_name`,`ah`.`holder_name`) AS `ordered_from`,`pr`.`amount_paid` AS `amount_paid`,`pr`.`total_amount` AS `total_amount` from ((`purchase_requests` `pr` left join `companies` `c` on((`pr`.`source_company_id` = `c`.`company_id`))) left join `account_holders` `ah` on((`pr`.`source_account_holder_id` = `ah`.`cnic_no`))) where (`pr`.`payment_status` = 'Pending') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `product_view`
--

/*!50001 DROP VIEW IF EXISTS `product_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `product_view` AS select `p`.`product_code` AS `product_code`,`p`.`product_name` AS `product_name`,`pc`.`category_name` AS `category`,`pb`.`brand_name` AS `brand`,`c`.`company_name` AS `company`,`p`.`price` AS `price`,`s`.`warehouse_quantity` AS `warehouse_quantity`,`s`.`shop_quantity` AS `shop_quantity` from ((((`products` `p` join `product_category` `pc` on((`p`.`category_id` = `pc`.`category_id`))) join `product_brand` `pb` on((`p`.`brand_id` = `pb`.`brand_id`))) join `companies` `c` on((`pb`.`company_id` = `c`.`company_id`))) join `stock` `s` on((`p`.`product_code` = `s`.`product_code`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `purchase_requests_view`
--

/*!50001 DROP VIEW IF EXISTS `purchase_requests_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `purchase_requests_view` AS select `pr`.`request_id` AS `request_id`,date_format(`pr`.`request_date`,'%d-%m-%Y') AS `request_date`,concat_ws(' ',`p`.`product_name`,`prc`.`product_code`) AS `product_name`,`pb`.`brand_name` AS `brand_name`,`prc`.`quantity` AS `quantity`,coalesce(`c`.`company_name`,`ah`.`holder_name`) AS `ordered_from`,`pr`.`amount_paid` AS `amount_paid`,`pr`.`order_status` AS `order_status` from (((((`purchase_requests` `pr` join `purchase_request_cart` `prc` on((`prc`.`request_id` = `pr`.`request_id`))) join `products` `p` on((`prc`.`product_code` = `p`.`product_code`))) join `product_brand` `pb` on((`pb`.`brand_id` = `p`.`brand_id`))) left join `companies` `c` on((`pr`.`source_company_id` = `c`.`company_id`))) left join `account_holders` `ah` on((`pr`.`source_account_holder_id` = `ah`.`cnic_no`))) order by `pr`.`request_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `stock_arrival_history_view`
--

/*!50001 DROP VIEW IF EXISTS `stock_arrival_history_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `stock_arrival_history_view` AS select `sa`.`id` AS `id`,`sa`.`request_id` AS `request_id`,cast(`sa`.`arrive_date` as date) AS `arrive_date`,concat(`p`.`product_name`,' ( ',`sa`.`product_code`,' ) ') AS `product_name`,`pb`.`brand_name` AS `brand_name`,`c`.`company_name` AS `company_name`,`sa`.`sh_quantity` AS `sh_quantity`,`sa`.`wh_quantity` AS `wh_quantity`,max(`prc`.`quantity`) AS `quantity`,coalesce(`cd`.`company_name`,`ah`.`holder_name`) AS `ordered_from`,`sa`.`vehicle_no` AS `vehicle_no`,`sa`.`driver_no` AS `driver_no` from (((((((`purchase_request_cart` `prc` left join `stock_arrivals` `sa` on((`sa`.`request_id` = `prc`.`request_id`))) join `products` `p` on((`p`.`product_code` = `sa`.`product_code`))) join `product_brand` `pb` on((`pb`.`brand_id` = `p`.`brand_id`))) join `companies` `c` on((`c`.`company_id` = `pb`.`brand_id`))) join `purchase_requests` `pr` on((`pr`.`request_id` = `sa`.`request_id`))) left join `companies` `cd` on((`pr`.`source_company_id` = `cd`.`company_id`))) left join `account_holders` `ah` on((`pr`.`source_account_holder_id` = `ah`.`cnic_no`))) group by `sa`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `stock_transfer_history`
--

/*!50001 DROP VIEW IF EXISTS `stock_transfer_history`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `stock_transfer_history` AS select date_format(`st`.`transfer_date`,'%d-%m-%Y') AS `formatted_transfer_date`,`st`.`from_place` AS `from_place`,`st`.`to_place` AS `to_place`,concat(`p`.`product_name`,' (',`p`.`product_code`,')') AS `product_name`,`pb`.`brand_name` AS `brand_name`,`c`.`company_name` AS `company_name`,`std`.`quantity` AS `quantity` from ((((`stock_transfer` `st` join `stock_transfer_detail` `std` on((`st`.`transfer_id` = `std`.`transfer_id`))) join `products` `p` on((`std`.`product_code` = `p`.`product_code`))) join `product_brand` `pb` on((`p`.`brand_id` = `pb`.`brand_id`))) join `companies` `c` on((`pb`.`company_id` = `c`.`company_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `today_over_invoice_view`
--

/*!50001 DROP VIEW IF EXISTS `today_over_invoice_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `today_over_invoice_view` AS with `soldquantity` as (select `combined_data`.`product_code` AS `product_code`,sum(`combined_data`.`quantity`) AS `sold_quantity` from (select `walk_in_bill_details`.`product_code` AS `product_code`,sum(`walk_in_bill_details`.`quantity`) AS `quantity` from (`walk_in_bill_details` join `walk_in_bills` on((`walk_in_bill_details`.`bill_id` = `walk_in_bills`.`bill_id`))) where (cast(`walk_in_bills`.`bill_date` as date) = cast(now() as date)) group by `walk_in_bill_details`.`product_code` union all select `account_holder_bill_cart`.`product_code` AS `product_code`,sum(`account_holder_bill_cart`.`quantity`) AS `quantity` from (`account_holder_bill_cart` join `account_holder_bills` on((`account_holder_bill_cart`.`bill_id` = `account_holder_bills`.`bill_id`))) where (cast(`account_holder_bills`.`bill_date` as date) = cast(now() as date)) group by `account_holder_bill_cart`.`product_code`) `combined_data` group by `combined_data`.`product_code`), `stockavailability` as (select `stock`.`product_code` AS `product_code`,sum((`stock`.`shop_quantity` + `stock`.`warehouse_quantity`)) AS `available_quantity` from `stock` group by `stock`.`product_code`), `combined` as (select coalesce(`sq`.`product_code`,`sa`.`product_code`) AS `product_code`,coalesce(`sq`.`sold_quantity`,0) AS `sold_quantity`,coalesce(`sa`.`available_quantity`,0) AS `available_quantity` from (`stockavailability` `sa` left join `soldquantity` `sq` on((`sa`.`product_code` = `sq`.`product_code`))) union select coalesce(`sq`.`product_code`,`sa`.`product_code`) AS `product_code`,coalesce(`sq`.`sold_quantity`,0) AS `sold_quantity`,coalesce(`sa`.`available_quantity`,0) AS `available_quantity` from (`soldquantity` `sq` left join `stockavailability` `sa` on((`sa`.`product_code` = `sq`.`product_code`)))) select `combined`.`product_code` AS `product_code`,(`combined`.`sold_quantity` - `combined`.`available_quantity`) AS `over_invoiced_quantity` from `combined` where ((`combined`.`sold_quantity` > `combined`.`available_quantity`) or (`combined`.`available_quantity` is null)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `today_sale_view`
--

/*!50001 DROP VIEW IF EXISTS `today_sale_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `today_sale_view` AS select `walk_in_bills`.`bill_id` AS `bill_id`,concat('WI_',`walk_in_bills`.`customer_name`) AS `customer_name`,cast(`walk_in_bills`.`bill_date` as date) AS `bill_date`,concat(`products`.`product_name`,' ( ',`walk_in_bill_details`.`product_code`,' )') AS `product_name`,`walk_in_bill_details`.`quantity` AS `quantity` from ((`walk_in_bills` join `walk_in_bill_details` on((`walk_in_bills`.`bill_id` = `walk_in_bill_details`.`bill_id`))) join `products` on((`walk_in_bill_details`.`product_code` = `products`.`product_code`))) where ((cast(`walk_in_bills`.`bill_date` as date) = curdate()) and (`walk_in_bill_details`.`p_status` = 'Pending')) union all select `account_holder_bills`.`bill_id` AS `bill_id`,concat('AH_',`account_holders`.`holder_name`) AS `customer_name`,cast(`account_holder_bills`.`bill_date` as date) AS `bill_date`,concat(`products`.`product_name`,' ( ',`account_holder_bill_cart`.`product_code`,' )') AS `product_name`,`account_holder_bill_cart`.`quantity` AS `quantity` from (((`account_holder_bills` join `account_holder_bill_cart` on((`account_holder_bills`.`bill_id` = `account_holder_bill_cart`.`bill_id`))) join `products` on((`account_holder_bill_cart`.`product_code` = `products`.`product_code`))) join `account_holders` on((`account_holder_bills`.`cnic_no` = `account_holders`.`cnic_no`))) where ((cast(`account_holder_bills`.`bill_date` as date) = curdate()) and (`account_holder_bill_cart`.`p_status` = 'Pending')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-28 13:50:44
