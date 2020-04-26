-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: final_railroad
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `rout`
--

DROP TABLE IF EXISTS `rout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_departure_station` int(11) NOT NULL,
  `id_destination_station` int(11) NOT NULL,
  `departure_date_time` datetime NOT NULL,
  `destination_date_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `keyStations_idx` (`id_departure_station`,`id_destination_station`),
  KEY `keyDeStation_idx` (`id_destination_station`),
  CONSTRAINT `keyDepartureStation` FOREIGN KEY (`id_departure_station`) REFERENCES `station` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `keyDestinationStation` FOREIGN KEY (`id_destination_station`) REFERENCES `station` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rout`
--

LOCK TABLES `rout` WRITE;
/*!40000 ALTER TABLE `rout` DISABLE KEYS */;
INSERT INTO `rout` VALUES (25,32,38,'2020-04-28 22:00:00','2020-04-29 08:00:00'),(26,38,26,'2020-04-30 10:00:00','2020-04-30 14:00:00'),(44,65,26,'2020-04-29 08:30:00','2020-04-29 13:00:00'),(45,21,75,'2020-04-28 22:00:00','2020-04-28 05:00:00');
/*!40000 ALTER TABLE `rout` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-26  4:23:09
