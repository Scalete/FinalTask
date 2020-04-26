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
-- Table structure for table `intermediate_station`
--

DROP TABLE IF EXISTS `intermediate_station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intermediate_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_station` int(11) NOT NULL,
  `stop_time` time NOT NULL,
  `departure_time` datetime NOT NULL,
  `destination_time` datetime NOT NULL,
  `id_rout` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `KeyStation_idx` (`id_station`),
  KEY `KeyFullRout_idx` (`id_rout`),
  CONSTRAINT `KeyFullRout` FOREIGN KEY (`id_rout`) REFERENCES `rout` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `KeyStation` FOREIGN KEY (`id_station`) REFERENCES `station` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intermediate_station`
--

LOCK TABLES `intermediate_station` WRITE;
/*!40000 ALTER TABLE `intermediate_station` DISABLE KEYS */;
INSERT INTO `intermediate_station` VALUES (24,40,'00:10:00','2020-04-28 22:40:00','2020-04-28 22:30:00',25,1),(25,41,'00:05:00','2020-04-28 23:35:00','2020-04-28 23:30:00',25,2),(26,17,'00:10:00','2020-04-29 00:40:00','2020-04-29 00:30:00',25,3),(27,44,'00:20:00','2020-04-29 01:50:00','2020-04-29 01:30:00',25,4),(28,7,'00:05:00','2020-04-29 02:25:00','2020-04-29 02:20:00',25,5),(29,45,'00:05:00','2020-04-29 02:55:00','2020-04-29 02:50:00',25,6),(30,9,'00:10:00','2020-04-29 03:30:00','2020-04-29 03:20:00',25,7),(31,47,'00:10:00','2020-04-29 04:10:00','2020-04-29 04:00:00',25,8),(32,48,'00:10:00','2020-04-29 05:10:00','2020-04-29 05:00:00',25,9),(33,12,'00:05:00','2020-04-29 07:25:00','2020-04-29 07:20:00',25,10),(34,50,'00:05:00','2020-04-30 11:05:00','2020-04-30 11:00:00',26,1),(35,51,'00:05:00','2020-04-30 11:45:00','2020-04-30 11:40:00',26,2),(36,52,'00:10:00','2020-04-30 12:40:00','2020-04-30 12:30:00',26,3),(37,53,'00:05:00','2020-04-30 13:35:00','2020-04-30 13:30:00',26,4),(56,66,'00:10:00','2020-04-29 09:10:00','2020-04-29 09:00:00',44,1),(57,67,'00:05:00','2020-04-29 10:25:00','2020-04-29 10:20:00',44,2),(58,68,'00:10:00','2020-04-29 11:00:00','2020-04-29 10:50:00',44,3),(59,69,'00:10:00','2020-04-29 12:10:00','2020-04-29 12:00:00',44,4),(60,70,'00:02:00','2020-04-29 12:32:00','2020-04-29 12:30:00',44,5),(64,71,'00:10:00','2020-04-28 23:10:00','2020-04-28 23:00:00',45,1),(65,72,'00:05:00','2020-04-29 00:05:00','2020-04-29 00:00:00',45,2),(66,73,'00:05:00','2020-04-29 02:05:00','2020-04-29 02:00:00',45,3),(67,74,'00:02:00','2020-04-29 04:02:00','2020-04-29 04:00:00',45,4);
/*!40000 ALTER TABLE `intermediate_station` ENABLE KEYS */;
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
