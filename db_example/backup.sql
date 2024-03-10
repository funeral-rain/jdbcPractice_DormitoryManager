-- MySQL dump 10.13  Distrib 5.7.44, for Win64 (x86_64)
--
-- Host: localhost    Database: dormitorydb
-- ------------------------------------------------------
-- Server version	5.7.44-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dormiddic`
--

DROP TABLE IF EXISTS `dormiddic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dormiddic` (
  `switchRes` int(11) NOT NULL,
  `building` varchar(10) NOT NULL,
  `area` varchar(100) DEFAULT NULL,
  UNIQUE KEY `switchRes` (`switchRes`),
  UNIQUE KEY `building` (`building`),
  UNIQUE KEY `area` (`area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dormiddic`
--

LOCK TABLES `dormiddic` WRITE;
/*!40000 ALTER TABLE `dormiddic` DISABLE KEYS */;
INSERT INTO `dormiddic` VALUES (1,'A','N'),(2,'B','S'),(3,'C','E'),(4,'D',NULL),(5,'E','W'),(6,'F',NULL);
/*!40000 ALTER TABLE `dormiddic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dormitory`
--

DROP TABLE IF EXISTS `dormitory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dormitory` (
  `DormitoryId` int(11) NOT NULL,
  `floor` int(11) NOT NULL DEFAULT '1',
  `building` varchar(10) NOT NULL DEFAULT '',
  `area` varchar(100) NOT NULL DEFAULT '',
  `capacity` int(11) NOT NULL DEFAULT '1',
  `currentOccupancy` tinyint(4) NOT NULL DEFAULT '0',
  `RoomId` int(11) NOT NULL DEFAULT '0',
  `LeaderId` bigint(20) NOT NULL DEFAULT '0',
  UNIQUE KEY `DormitoryId` (`DormitoryId`),
  KEY `map_areaToDic` (`area`),
  KEY `map_buildingToDic` (`building`),
  CONSTRAINT `map_areaToDic` FOREIGN KEY (`area`) REFERENCES `dormiddic` (`area`) ON UPDATE CASCADE,
  CONSTRAINT `map_buildingToDic` FOREIGN KEY (`building`) REFERENCES `dormiddic` (`building`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dormitory`
--

LOCK TABLES `dormitory` WRITE;
/*!40000 ALTER TABLE `dormitory` DISABLE KEYS */;
INSERT INTO `dormitory` VALUES (1010103,1,'A','N',6,0,103,0),(1010104,1,'A','N',6,0,104,0),(1010105,1,'A','N',6,0,105,0),(1010106,1,'A','N',6,0,106,0),(1010107,1,'A','N',6,0,107,0),(1010108,1,'A','N',6,0,108,0),(1010109,1,'A','N',6,0,109,0),(1010110,1,'A','N',6,0,110,0),(1020102,1,'B','N',5,2,102,0),(1020104,1,'B','N',3,0,104,0);
/*!40000 ALTER TABLE `dormitory` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER creat_dormid_trigger BEFORE INSERT ON dormitory
FOR EACH ROW
BEGIN
SET NEW.DormitoryId = ((SELECT switchRes FROM DormIdDic WHERE DormIdDic.area = NEW.area) * 1000000
                    + (SELECT switchRes FROM DormIdDic WHERE DormIdDic.building = NEW.building) * 10000
                    + NEW.RoomId);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER get_floor_trigger BEFORE INSERT ON dormitory
    FOR EACH ROW
BEGIN
    SET NEW.floor = NEW.RoomId / 100;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER dormitory_insert_trigger AFTER INSERT ON Dormitory
    FOR EACH ROW
BEGIN
    INSERT Logging (operation, `table`, type, time) VALUES
        ((CONCAT_WS(' ', 'TABLE Dormitory INSERT DATA:', NEW.DormitoryId, NEW.RoomId, NEW.floor, NEW.building, NEW.area,
                    NEW.capacity, NEW.currentOccupancy, NEW.LeaderId)),
         'Dormitory', 'INSERT', NOW());
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER update_dormid_trigger BEFORE UPDATE ON dormitory
    FOR EACH ROW
BEGIN
    SET NEW.DormitoryId = ((SELECT switchRes FROM DormIdDic WHERE DormIdDic.area = NEW.area) * 1000000
        + (SELECT switchRes FROM DormIdDic WHERE DormIdDic.building = NEW.building) * 10000
        + NEW.RoomId);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER update_floor_trigger BEFORE UPDATE ON dormitory
    FOR EACH ROW
BEGIN
    SET NEW.floor = NEW.RoomId / 100;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER dormitory_update_trigger AFTER UPDATE ON Dormitory
    FOR EACH ROW
BEGIN
    INSERT Logging (operation, `table`, type, time) VALUES
        ((CONCAT_WS(' ', 'TABLE Student UPDATE DATA: FROM', OLD.DormitoryId, OLD.RoomId, OLD.floor, OLD.building, OLD.area,
                    OLD.capacity, OLD.currentOccupancy, OLD.LeaderId,
                    'TO', NEW.DormitoryId, NEW.RoomId, NEW.floor, NEW.building, NEW.area,
                    NEW.capacity, NEW.currentOccupancy, NEW.LeaderId)),
         'Dormitory', 'UPDATE', NOW());
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER dormitory_delete_trigger AFTER DELETE ON Dormitory
    FOR EACH ROW
BEGIN
    INSERT Logging (operation, `table`, type, time) VALUES
        ((CONCAT_WS(' ', 'TABLE Student DELETE DATA:', OLD.DormitoryId, OLD.RoomId, OLD.floor, OLD.building, OLD.area,
                    OLD.capacity, OLD.currentOccupancy, OLD.LeaderId)),
         'Dormitory', 'DELETE', NOW());
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `logging`
--

DROP TABLE IF EXISTS `logging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logging` (
  `operation` varchar(300) NOT NULL,
  `table` varchar(50) NOT NULL,
  `type` varchar(10) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logging`
--

LOCK TABLES `logging` WRITE;
/*!40000 ALTER TABLE `logging` DISABLE KEYS */;
INSERT INTO `logging` VALUES ('TABLE Student UPDATE DATA: FROM 20231024102 Mary 1010102 TO 20231024102 Mary 1010101','Student','UPDATE','2023-12-09 03:48:13'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 A N 5 2 0 TO 1010102 102 1 A N 5 1 0','Dormitory','UPDATE','2023-12-09 03:48:13'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 5 1 0 TO 1010101 101 1 A N 5 2 0','Dormitory','UPDATE','2023-12-09 03:48:13'),('TABLE Student UPDATE DATA: FROM 20231024103 John 1010101 TO 20231024103 John 1010102','Student','UPDATE','2023-12-09 03:48:13'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 5 2 0 TO 1010101 101 1 A N 5 1 0','Dormitory','UPDATE','2023-12-09 03:48:13'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 A N 5 1 0 TO 1010102 102 1 A N 5 2 0','Dormitory','UPDATE','2023-12-09 03:48:13'),('TABLE Student INSERT DATA: 20231024104 ?? 1010102','Student','INSERT','2023-12-09 05:46:18'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 A N 5 2 0 TO 1010102 102 1 A N 5 3 0','Dormitory','UPDATE','2023-12-09 05:46:18'),('TABLE Student UPDATE DATA: FROM 20231024104 ?? 1010102 TO 20231024104 Li Hua 1010102','Student','UPDATE','2023-12-09 05:46:53'),('TABLE Student UPDATE DATA: FROM 20231024104 Li Hua 1010102 TO 20231024104 Ame 1010101','Student','UPDATE','2023-12-09 10:39:24'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 A N 5 3 0 TO 1010102 102 1 A N 5 2 0','Dormitory','UPDATE','2023-12-09 10:39:24'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 5 1 0 TO 1010101 101 1 A N 5 2 0','Dormitory','UPDATE','2023-12-09 10:39:24'),('TABLE Student INSERT DATA: 20231024105 Jack 2020504','Student','INSERT','2023-12-09 10:41:01'),('TABLE Student UPDATE DATA: FROM 2020504 504 5 B S 5 0 0 TO 2020504 504 5 B S 5 1 0','Dormitory','UPDATE','2023-12-09 10:41:01'),('TABLE Dormitory INSERT DATA: 1020104 104 1 B N 3 0 0','Dormitory','INSERT','2023-12-09 11:04:23'),('TABLE Student INSERT DATA: 20231024106 test1 2020504','Student','INSERT','2023-12-09 11:11:28'),('TABLE Student UPDATE DATA: FROM 2020504 504 5 B S 5 1 0 TO 2020504 504 5 B S 5 2 0','Dormitory','UPDATE','2023-12-09 11:11:28'),('TABLE Student INSERT DATA: 20231024107 test2 2020504','Student','INSERT','2023-12-09 11:12:22'),('TABLE Student UPDATE DATA: FROM 2020504 504 5 B S 5 2 0 TO 2020504 504 5 B S 5 3 0','Dormitory','UPDATE','2023-12-09 11:12:22'),('TABLE Student INSERT DATA: 20231024108 test3 2030305','Student','INSERT','2023-12-09 11:13:28'),('TABLE Student UPDATE DATA: FROM 2030305 305 3 C S 1 0 0 TO 2030305 305 3 C S 1 1 0','Dormitory','UPDATE','2023-12-09 11:13:28'),('TABLE Student DELETE DATA: 20231024106 test1 2020504','Student','DELETE','2023-12-09 11:14:38'),('TABLE Student UPDATE DATA: FROM 2020504 504 5 B S 5 3 0 TO 2020504 504 5 B S 5 2 0','Dormitory','UPDATE','2023-12-09 11:14:38'),('TABLE Student DELETE DATA: 20231024107 test2 2020504','Student','DELETE','2023-12-09 11:14:38'),('TABLE Student UPDATE DATA: FROM 2020504 504 5 B S 5 2 0 TO 2020504 504 5 B S 5 1 0','Dormitory','UPDATE','2023-12-09 11:14:38'),('TABLE Student DELETE DATA: 20231024108 test3 2030305','Student','DELETE','2023-12-09 11:14:38'),('TABLE Student UPDATE DATA: FROM 2030305 305 3 C S 1 1 0 TO 2030305 305 3 C S 1 0 0','Dormitory','UPDATE','2023-12-09 11:14:38'),('TABLE Dormitory INSERT DATA: 1010103 103 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:37'),('TABLE Dormitory INSERT DATA: 1010104 104 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:45'),('TABLE Dormitory INSERT DATA: 1010105 105 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:45'),('TABLE Dormitory INSERT DATA: 1010106 106 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:46'),('TABLE Dormitory INSERT DATA: 1010107 107 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:46'),('TABLE Dormitory INSERT DATA: 1010108 108 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:47'),('TABLE Dormitory INSERT DATA: 1010109 109 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:47'),('TABLE Dormitory INSERT DATA: 1010110 110 1 A N 6 0 0','Dormitory','INSERT','2023-12-09 12:11:48'),('TABLE Student UPDATE DATA: FROM 20231024101 Bob 1010102 TO 20231024101 Bob 1010101','Student','UPDATE','2023-12-09 14:38:42'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 A N 5 2 0 TO 1010102 102 1 A N 5 1 0','Dormitory','UPDATE','2023-12-09 14:38:42'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 5 2 0 TO 1010101 101 1 A N 5 3 0','Dormitory','UPDATE','2023-12-09 14:38:42'),('TABLE Student UPDATE DATA: FROM 20231024102 Mary 1010101 TO 20231024102 Mary 1010102','Student','UPDATE','2023-12-09 14:38:42'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 5 3 0 TO 1010101 101 1 A N 5 2 0','Dormitory','UPDATE','2023-12-09 14:38:42'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 A N 5 1 0 TO 1010102 102 1 A N 5 2 0','Dormitory','UPDATE','2023-12-09 14:38:42'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 5 2 0 TO 1010101 101 1 A N 1 2 0','Dormitory','UPDATE','2023-12-09 20:49:16'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 1 2 0 TO 1010101 101 1 A N 3 2 0','Dormitory','UPDATE','2023-12-09 20:52:37'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 3 2 0 TO 1010101 101 1 A N 3 2 20231024101','Dormitory','UPDATE','2023-12-09 20:56:08'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 A N 3 2 20231024101 TO 1010101 101 1 A N 3 2 0','Dormitory','UPDATE','2023-12-09 20:57:19'),('TABLE Student DELETE DATA: 20231024101 Bob 1010101','Student','DELETE','2023-12-09 22:40:34'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 G N 3 2 0 TO 1010101 101 1 G N 3 1 0','Dormitory','UPDATE','2023-12-09 22:40:34'),('TABLE Student DELETE DATA: 20231024104 Ame 1010101','Student','DELETE','2023-12-09 23:04:13'),('TABLE Student UPDATE DATA: FROM 1010101 101 1 G N 3 1 0 TO 1010101 101 1 G N 3 0 0','Dormitory','UPDATE','2023-12-09 23:04:13'),('TABLE Student DELETE DATA: 1010101 101 1 G N 3 0 0','Dormitory','DELETE','2023-12-09 23:04:13'),('TABLE Student DELETE DATA: 20231024105 Jack 2020504','Student','DELETE','2023-12-11 21:01:38'),('TABLE Student UPDATE DATA: FROM 2020504 504 5 B S 5 1 0 TO 2020504 504 5 B S 5 0 0','Dormitory','UPDATE','2023-12-11 21:01:38'),('TABLE Student DELETE DATA: 2020504 504 5 B S 5 0 0','Dormitory','DELETE','2023-12-11 21:01:38'),('TABLE Student DELETE DATA: 2030305 305 3 C S 1 0 0','Dormitory','DELETE','2023-12-11 21:01:38'),('TABLE Student DELETE DATA: 2040333 333 3 D S 3 0 0','Dormitory','DELETE','2023-12-11 21:01:38'),('TABLE Student UPDATE DATA: FROM 1010102 102 1 G N 5 2 0 TO 1020102 102 1 B N 5 2 0','Dormitory','UPDATE','2023-12-11 22:13:09');
/*!40000 ALTER TABLE `logging` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `name` varchar(20) NOT NULL,
  `StudentId` bigint(20) NOT NULL,
  `DormitoryId` int(11) NOT NULL,
  UNIQUE KEY `StudentId` (`StudentId`),
  UNIQUE KEY `StudentId_2` (`StudentId`),
  KEY `map_studentToDorm` (`DormitoryId`),
  CONSTRAINT `map_studentToDorm` FOREIGN KEY (`DormitoryId`) REFERENCES `dormitory` (`DormitoryId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('Mary',20231024102,1020102),('John',20231024103,1020102);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER student_insert_trigger AFTER INSERT ON Student
    FOR EACH ROW
    BEGIN
        INSERT Logging (operation, `table`, type, time) VALUES
        ((CONCAT_WS(' ', 'TABLE Student INSERT DATA:', NEW.StudentId, NEW.name, NEW.DormitoryId)),
         'Student', 'INSERT', NOW());
    end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER insert_student_trigger AFTER INSERT ON student
    FOR EACH ROW
BEGIN
    UPDATE dormitory SET currentOccupancy = currentOccupancy + 1 WHERE DormitoryId = NEW.DormitoryId;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER student_update_trigger AFTER UPDATE ON Student
    FOR EACH ROW
BEGIN
    INSERT Logging (operation, `table`, type, time) VALUES
        ((CONCAT_WS(' ', 'TABLE Student UPDATE DATA: FROM', OLD.StudentId, OLD.name, OLD.DormitoryId,
                          'TO', NEW.StudentId, NEW.name, NEW.DormitoryId)),
         'Student', 'UPDATE', NOW());
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER update_student_trigger AFTER UPDATE ON student
    FOR EACH ROW
BEGIN
    IF OLD.DormitoryId != NEW.DormitoryId THEN
    UPDATE dormitory SET currentOccupancy = currentOccupancy - 1 WHERE DormitoryId = OLD.DormitoryId;
    UPDATE dormitory SET currentOccupancy = currentOccupancy + 1 WHERE DormitoryId = NEW.DormitoryId;
    end if;

end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER student_delete_trigger AFTER DELETE ON Student
    FOR EACH ROW
BEGIN
    INSERT Logging (operation, `table`, type, time) VALUES
        ((CONCAT_WS(' ', 'TABLE Student DELETE DATA:', OLD.StudentId, OLD.name, OLD.DormitoryId)),
         'Student', 'DELETE', NOW());
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER delete_student_trigger AFTER DELETE ON Student
    FOR EACH ROW
BEGIN
    UPDATE dormitory SET currentOccupancy = currentOccupancy - 1 WHERE DormitoryId = OLD.DormitoryId;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-10 20:59:24
