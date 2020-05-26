-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 192.168.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	5.5.60-0+deb7u1

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
-- Table structure for table `ac_access`
--

DROP TABLE IF EXISTS `ac_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_access` (
  `acs_id` int(11) NOT NULL AUTO_INCREMENT,
  `acs_valid_from` datetime NOT NULL,
  `acs_valid_to` datetime NOT NULL,
  `acs_opening_counter` int(11) DEFAULT NULL,
  `acs_usr_id` int(11) DEFAULT NULL,
  `acs_pro_id` int(11) DEFAULT NULL,
  `acs_obj_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`acs_id`,`acs_valid_to`,`acs_valid_from`),
  KEY `fk_access_user1_idx` (`acs_usr_id`),
  KEY `fk_access_profiles1_idx` (`acs_pro_id`),
  KEY `fk_access_object1_idx` (`acs_obj_id`),
  CONSTRAINT `fk_access_user1` FOREIGN KEY (`acs_usr_id`) REFERENCES `ac_user` (`usr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_access_profiles1` FOREIGN KEY (`acs_pro_id`) REFERENCES `ac_profil` (`pro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_access_object1` FOREIGN KEY (`acs_obj_id`) REFERENCES `ac_object` (`obj_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_access`
--

LOCK TABLES `ac_access` WRITE;
/*!40000 ALTER TABLE `ac_access` DISABLE KEYS */;
INSERT INTO `ac_access` VALUES (1,'2018-10-15 00:00:00','9999-12-31 23:59:59',NULL,1,1,1);
/*!40000 ALTER TABLE `ac_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_date`
--

DROP TABLE IF EXISTS `ac_date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_date` (
  `dat_id` int(11) NOT NULL AUTO_INCREMENT,
  `dat_date_from` datetime NOT NULL,
  `dat_date_to` datetime NOT NULL,
  `dat_enabled` tinyint(4) NOT NULL,
  `dat_pro_id` int(11) NOT NULL,
  PRIMARY KEY (`dat_id`),
  KEY `fk_date_profil1_idx` (`dat_pro_id`),
  CONSTRAINT `fk_date_profil1` FOREIGN KEY (`dat_pro_id`) REFERENCES `ac_profil` (`pro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_date`
--

LOCK TABLES `ac_date` WRITE;
/*!40000 ALTER TABLE `ac_date` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_day`
--

DROP TABLE IF EXISTS `ac_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_day` (
  `day_id` int(11) NOT NULL AUTO_INCREMENT,
  `day_name` varchar(45) NOT NULL,
  PRIMARY KEY (`day_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_day`
--

LOCK TABLES `ac_day` WRITE;
/*!40000 ALTER TABLE `ac_day` DISABLE KEYS */;
INSERT INTO `ac_day` VALUES (7,'Sunday');
/*!40000 ALTER TABLE `ac_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_event_log`
--

DROP TABLE IF EXISTS `ac_event_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_event_log` (
  `evl_id` int(11) NOT NULL AUTO_INCREMENT,
  `evl_date` datetime NOT NULL,
  `evl_trg_value` varchar(100) NOT NULL,
  `evl_evs_id` int(11) NOT NULL,
  `evl_obj_id` int(11) DEFAULT NULL,
  `evl_usr_id` int(11) DEFAULT NULL,
  `evl_trt_id` int(11) NOT NULL,
  PRIMARY KEY (`evl_id`),
  KEY `fk_event_log_event_type1_idx` (`evl_evs_id`),
  KEY `fk_event_log_object1_idx` (`evl_obj_id`),
  KEY `fk_event_log_user1_idx` (`evl_usr_id`),
  KEY `fk_event_log_trigger_type1_idx` (`evl_trt_id`),
  CONSTRAINT `fk_event_log_event_type1` FOREIGN KEY (`evl_evs_id`) REFERENCES `ac_event_status` (`evs_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_log_object1` FOREIGN KEY (`evl_obj_id`) REFERENCES `ac_object` (`obj_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_log_user1` FOREIGN KEY (`evl_usr_id`) REFERENCES `ac_user` (`usr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_log_trigger_type1` FOREIGN KEY (`evl_trt_id`) REFERENCES `ac_trigger_type` (`trt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_event_log`
--

LOCK TABLES `ac_event_log` WRITE;
/*!40000 ALTER TABLE `ac_event_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_event_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_event_status`
--

DROP TABLE IF EXISTS `ac_event_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_event_status` (
  `evs_id` int(11) NOT NULL AUTO_INCREMENT,
  `evs_name` varchar(45) NOT NULL,
  `evs_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`evs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_event_status`
--

LOCK TABLES `ac_event_status` WRITE;
/*!40000 ALTER TABLE `ac_event_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_event_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_object`
--

DROP TABLE IF EXISTS `ac_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_object` (
  `obj_id` int(11) NOT NULL AUTO_INCREMENT,
  `obj_name` varchar(45) NOT NULL,
  `obj_open` tinyint(4) DEFAULT NULL,
  `obj_auto` tinyint(4) DEFAULT NULL,
  `obj_activity` tinyint(4) NOT NULL,
  `obj_GPS` varchar(100) NOT NULL,
  `obj_action` varchar(1000) NOT NULL,
  `obj_obt_type_id` int(11) NOT NULL,
  PRIMARY KEY (`obj_id`),
  KEY `fk_objects_object_type1_idx` (`obj_obt_type_id`),
  CONSTRAINT `fk_objects_object_type1` FOREIGN KEY (`obj_obt_type_id`) REFERENCES `ac_object_type` (`obt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_object`
--

LOCK TABLES `ac_object` WRITE;
/*!40000 ALTER TABLE `ac_object` DISABLE KEYS */;
INSERT INTO `ac_object` VALUES (1,'ramp north',NULL,1,1,'46.287225,16.320886','DIO_A',1),(2,'ramp west',NULL,1,1,'46.287225,16.320886','DIO_B',1);
/*!40000 ALTER TABLE `ac_object` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_object_has_trigger_type`
--

DROP TABLE IF EXISTS `ac_object_has_trigger_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_object_has_trigger_type` (
  `oht_trt_id` int(11) NOT NULL DEFAULT '0',
  `oht_obj_id` int(11) NOT NULL DEFAULT '0',
  `oht_activity` tinyint(4) NOT NULL,
  PRIMARY KEY (`oht_trt_id`,`oht_obj_id`),
  KEY `fk_trigger_type_has_object_object1_idx` (`oht_obj_id`),
  KEY `fk_trigger_type_has_object_trigger_type1_idx` (`oht_trt_id`),
  CONSTRAINT `fk_trigger_type_has_object_trigger_type1` FOREIGN KEY (`oht_trt_id`) REFERENCES `ac_trigger_type` (`trt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_trigger_type_has_object_object1` FOREIGN KEY (`oht_obj_id`) REFERENCES `ac_object` (`obj_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_object_has_trigger_type`
--

LOCK TABLES `ac_object_has_trigger_type` WRITE;
/*!40000 ALTER TABLE `ac_object_has_trigger_type` DISABLE KEYS */;
INSERT INTO `ac_object_has_trigger_type` VALUES (1,1,1);
/*!40000 ALTER TABLE `ac_object_has_trigger_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_object_type`
--

DROP TABLE IF EXISTS `ac_object_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_object_type` (
  `obt_id` int(11) NOT NULL AUTO_INCREMENT,
  `obt_name` varchar(45) NOT NULL,
  `obt_in` tinyint(4) DEFAULT NULL,
  `obt_out` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`obt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_object_type`
--

LOCK TABLES `ac_object_type` WRITE;
/*!40000 ALTER TABLE `ac_object_type` DISABLE KEYS */;
INSERT INTO `ac_object_type` VALUES (1,'ramp',1,NULL);
/*!40000 ALTER TABLE `ac_object_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_profil`
--

DROP TABLE IF EXISTS `ac_profil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_profil` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(45) NOT NULL,
  `pro_activity` tinyint(4) NOT NULL,
  PRIMARY KEY (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_profil`
--

LOCK TABLES `ac_profil` WRITE;
/*!40000 ALTER TABLE `ac_profil` DISABLE KEYS */;
INSERT INTO `ac_profil` VALUES (1,'employee',1);
/*!40000 ALTER TABLE `ac_profil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_role`
--

DROP TABLE IF EXISTS `ac_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_role` (
  `rol_id` int(11) NOT NULL AUTO_INCREMENT,
  `rol_name` varchar(45) NOT NULL,
  `rol_company` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`rol_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_role`
--

LOCK TABLES `ac_role` WRITE;
/*!40000 ALTER TABLE `ac_role` DISABLE KEYS */;
INSERT INTO `ac_role` VALUES (1,'Administrator','Mobilisis');
/*!40000 ALTER TABLE `ac_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_schedule`
--

DROP TABLE IF EXISTS `ac_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_schedule` (
  `sch_pro_id` int(11) NOT NULL DEFAULT '0',
  `sch_day_id` int(11) NOT NULL DEFAULT '0',
  `sch_time_from` time NOT NULL,
  `sch_time_to` time NOT NULL,
  PRIMARY KEY (`sch_pro_id`,`sch_day_id`),
  KEY `fk_schedule_profil1_idx` (`sch_pro_id`),
  KEY `fk_schedule_day1_idx` (`sch_day_id`),
  CONSTRAINT `fk_schedule_profil1` FOREIGN KEY (`sch_pro_id`) REFERENCES `ac_profil` (`pro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_schedule_day1` FOREIGN KEY (`sch_day_id`) REFERENCES `ac_day` (`day_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_schedule`
--

LOCK TABLES `ac_schedule` WRITE;
/*!40000 ALTER TABLE `ac_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_system_log`
--

DROP TABLE IF EXISTS `ac_system_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_system_log` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_usr_id` int(11) NOT NULL,
  `sys_action` varchar(2000) NOT NULL,
  `sys_text` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_system_log`
--

LOCK TABLES `ac_system_log` WRITE;
/*!40000 ALTER TABLE `ac_system_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_system_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_trigger`
--

DROP TABLE IF EXISTS `ac_trigger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_trigger` (
  `trg_usr_id` int(11) NOT NULL DEFAULT '0',
  `trg_trt_id` int(11) NOT NULL DEFAULT '0',
  `trg_value` varchar(1000) NOT NULL,
  `trg_activity` tinyint(4) NOT NULL,
  PRIMARY KEY (`trg_usr_id`,`trg_trt_id`),
  KEY `fk_user_has_category_category1_idx` (`trg_trt_id`),
  KEY `fk_user_has_category_user1_idx` (`trg_usr_id`),
  CONSTRAINT `fk_user_has_category_user1` FOREIGN KEY (`trg_usr_id`) REFERENCES `ac_user` (`usr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_category_category1` FOREIGN KEY (`trg_trt_id`) REFERENCES `ac_trigger_type` (`trt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_trigger`
--

LOCK TABLES `ac_trigger` WRITE;
/*!40000 ALTER TABLE `ac_trigger` DISABLE KEYS */;
INSERT INTO `ac_trigger` VALUES (1,1,'385911527964',1);
/*!40000 ALTER TABLE `ac_trigger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_trigger_type`
--

DROP TABLE IF EXISTS `ac_trigger_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_trigger_type` (
  `trt_id` int(11) NOT NULL AUTO_INCREMENT,
  `trt_name` varchar(45) NOT NULL,
  PRIMARY KEY (`trt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_trigger_type`
--

LOCK TABLES `ac_trigger_type` WRITE;
/*!40000 ALTER TABLE `ac_trigger_type` DISABLE KEYS */;
INSERT INTO `ac_trigger_type` VALUES (1,'Sms');
/*!40000 ALTER TABLE `ac_trigger_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ac_user`
--

DROP TABLE IF EXISTS `ac_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_user` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT,
  `usr_name` varchar(45) DEFAULT NULL,
  `usr_surname` varchar(45) DEFAULT NULL,
  `usr_email` varchar(45) DEFAULT NULL,
  `usr_activity` tinyint(4) NOT NULL,
  `usr_password` varchar(45) NOT NULL,
  `usr_crypted_password` varchar(100) DEFAULT NULL,
  `usr_rol_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`usr_id`),
  KEY `fk_user_user_type_idx` (`usr_rol_id`),
  CONSTRAINT `fk_user_user_type` FOREIGN KEY (`usr_rol_id`) REFERENCES `ac_role` (`rol_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_user`
--

LOCK TABLES `ac_user` WRITE;
/*!40000 ALTER TABLE `ac_user` DISABLE KEYS */;
INSERT INTO `ac_user` VALUES (1,'Nikola','Horvat','nhorvat@mobilisis.com',1,'1234567',NULL,1);
/*!40000 ALTER TABLE `ac_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mydb'
--
/*!50003 DROP PROCEDURE IF EXISTS `check_trigger` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `check_trigger`(
	IN p_trt_id int,
    IN p_value varchar(1000),
    OUT p_usr_id int,
    OUT p_activity tinyint)
BEGIN
	   
	SELECT  p_usr_id = trg_usr_id, p_activity = trg_activity
    FROM ac_trigger
    WHERE trg_trt_id = p_cat_id AND trg_value = p_value;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `check_trigger_type` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `check_trigger_type`(
	IN p_name varchar(45), 
    OUT p_id int)
BEGIN
	SELECT trt_id INTO p_id 
    FROM ac_trigger_type
    WHERE trt_name = p_name;
END ;;
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

-- Dump completed on 2018-11-06 15:43:50
