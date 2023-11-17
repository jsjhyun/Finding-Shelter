-- MariaDB dump 10.19  Distrib 10.7.3-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: shelter-recommendation
-- ------------------------------------------------------
-- Server version	10.7.3-MariaDB-1:10.7.3+maria~focal

	@@ -16,31 +16,31 @@
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `shelter`
--

DROP TABLE IF EXISTS `shelter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shelter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `shelter_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shelter_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;