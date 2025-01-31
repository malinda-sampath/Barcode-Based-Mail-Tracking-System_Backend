-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: uor_mts
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity_log`
--

DROP TABLE IF EXISTS `activity_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_log` (
  `activity_log_id` int NOT NULL AUTO_INCREMENT,
  `activity_date_time` datetime(6) DEFAULT NULL,
  `activity_type` varchar(255) DEFAULT NULL,
  `barcode_id` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `sender_name` varchar(255) DEFAULT NULL,
  `user_id` int NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`activity_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_log`
--

LOCK TABLES `activity_log` WRITE;
/*!40000 ALTER TABLE `activity_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch_users`
--

DROP TABLE IF EXISTS `branch_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_users` (
  `branch_user_id` int NOT NULL,
  `branch_coge` varchar(255) DEFAULT NULL,
  `branch_user_name` varchar(255) DEFAULT NULL,
  `branch_user_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`branch_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_users`
--

LOCK TABLES `branch_users` WRITE;
/*!40000 ALTER TABLE `branch_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `branch_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch_users_seq`
--

DROP TABLE IF EXISTS `branch_users_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_users_seq`
--

LOCK TABLES `branch_users_seq` WRITE;
/*!40000 ALTER TABLE `branch_users_seq` DISABLE KEYS */;
INSERT INTO `branch_users_seq` VALUES (1);
/*!40000 ALTER TABLE `branch_users_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branches`
--

DROP TABLE IF EXISTS `branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branches` (
  `branch_code` int NOT NULL AUTO_INCREMENT,
  `branch_name` varchar(100) NOT NULL,
  `insert_date` datetime(6) NOT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`branch_code`),
  UNIQUE KEY `UKcg8xnm1f8r6ohberdss40rhmm` (`branch_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches`
--

LOCK TABLES `branches` WRITE;
/*!40000 ALTER TABLE `branches` DISABLE KEYS */;
INSERT INTO `branches` VALUES (2,'Nuwan','2025-01-25 11:06:24.961950',NULL),(3,'Mewan','2025-01-25 11:07:39.726359',NULL);
/*!40000 ALTER TABLE `branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_mail`
--

DROP TABLE IF EXISTS `daily_mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_mail` (
  `daily_mail_id` int NOT NULL AUTO_INCREMENT,
  `barcode_id` varchar(255) DEFAULT NULL,
  `barcode_image` longblob,
  `branch_code` int NOT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `insert_date_time` datetime(6) DEFAULT NULL,
  `mail_description` varchar(255) DEFAULT NULL,
  `mail_type` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `sender_name` varchar(255) DEFAULT NULL,
  `tracking_number` varchar(255) DEFAULT NULL,
  `update_date_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`daily_mail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_mail`
--

LOCK TABLES `daily_mail` WRITE;
/*!40000 ALTER TABLE `daily_mail` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_mail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `johny_mailcart`
--

DROP TABLE IF EXISTS `johny_mailcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `johny_mailcart` (
  `barcode` int NOT NULL AUTO_INCREMENT,
  `branch_code` int NOT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `mail_type` varchar(50) DEFAULT NULL,
  `postal_code` int DEFAULT NULL,
  `tracking_code` int DEFAULT NULL,
  `received_date` date DEFAULT NULL,
  `claimed_date` date DEFAULT NULL,
  `claimed_person` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`barcode`),
  KEY `branch_code` (`branch_code`),
  CONSTRAINT `johny_mailcart_ibfk_1` FOREIGN KEY (`branch_code`) REFERENCES `branches` (`branch_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `johny_mailcart`
--

LOCK TABLES `johny_mailcart` WRITE;
/*!40000 ALTER TABLE `johny_mailcart` DISABLE KEYS */;
/*!40000 ALTER TABLE `johny_mailcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mail_admin`
--

DROP TABLE IF EXISTS `mail_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mail_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_no` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK57338w7kntrs8h9cy47e78uv6` (`email`),
  UNIQUE KEY `UKahkibsdc4i0rj8joox3up2atr` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mail_admin`
--

LOCK TABLES `mail_admin` WRITE;
/*!40000 ALTER TABLE `mail_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mail_record`
--

DROP TABLE IF EXISTS `mail_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mail_record` (
  `mail_record_id` int NOT NULL AUTO_INCREMENT,
  `barcode_id` varchar(255) DEFAULT NULL,
  `barcode_image` longblob,
  `branch_code` int NOT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `insert_date_time` datetime(6) DEFAULT NULL,
  `mail_description` varchar(255) DEFAULT NULL,
  `mail_type` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `sender_name` varchar(255) DEFAULT NULL,
  `tracking_number` varchar(255) DEFAULT NULL,
  `update_date_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`mail_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mail_record`
--

LOCK TABLES `mail_record` WRITE;
/*!40000 ALTER TABLE `mail_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mewan_mailcart`
--

DROP TABLE IF EXISTS `mewan_mailcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mewan_mailcart` (
  `barcode` int NOT NULL AUTO_INCREMENT,
  `branch_code` int NOT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `mail_type` varchar(50) DEFAULT NULL,
  `postal_code` int DEFAULT NULL,
  `tracking_code` int DEFAULT NULL,
  `received_date` date DEFAULT NULL,
  `claimed_date` date DEFAULT NULL,
  `claimed_person` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`barcode`),
  KEY `branch_code` (`branch_code`),
  CONSTRAINT `mewan_mailcart_ibfk_1` FOREIGN KEY (`branch_code`) REFERENCES `branches` (`branch_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mewan_mailcart`
--

LOCK TABLES `mewan_mailcart` WRITE;
/*!40000 ALTER TABLE `mewan_mailcart` DISABLE KEYS */;
/*!40000 ALTER TABLE `mewan_mailcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nuwan_mailcart`
--

DROP TABLE IF EXISTS `nuwan_mailcart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nuwan_mailcart` (
  `barcode` int NOT NULL AUTO_INCREMENT,
  `branch_code` int NOT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `mail_type` varchar(50) DEFAULT NULL,
  `postal_code` int DEFAULT NULL,
  `tracking_code` int DEFAULT NULL,
  `received_date` date DEFAULT NULL,
  `claimed_date` date DEFAULT NULL,
  `claimed_person` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`barcode`),
  KEY `branch_code` (`branch_code`),
  CONSTRAINT `nuwan_mailcart_ibfk_1` FOREIGN KEY (`branch_code`) REFERENCES `branches` (`branch_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nuwan_mailcart`
--

LOCK TABLES `nuwan_mailcart` WRITE;
/*!40000 ALTER TABLE `nuwan_mailcart` DISABLE KEYS */;
/*!40000 ALTER TABLE `nuwan_mailcart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tracking_details`
--

DROP TABLE IF EXISTS `tracking_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tracking_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `inserted_at` datetime(6) DEFAULT NULL,
  `mail_tracking_number` int DEFAULT NULL,
  `mail_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tracking_details`
--

LOCK TABLES `tracking_details` WRITE;
/*!40000 ALTER TABLE `tracking_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `tracking_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-31  9:35:25
