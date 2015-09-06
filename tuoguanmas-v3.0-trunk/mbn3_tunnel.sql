-- MySQL dump 10.13  Distrib 5.5.27, for Linux (x86_64)
--
-- Host: localhost    Database: mbn3_tunnel
-- ------------------------------------------------------
-- Server version	5.5.27-log

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
-- Table structure for table `mbn_clean_db_interval`
--

DROP TABLE IF EXISTS `mbn_clean_db_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_clean_db_interval` (
  `id` varchar(20) NOT NULL,
  `tvalue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Êý¾Ý¿âÇå³ýÆµÂÊ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_clean_db_interval`
--

LOCK TABLES `mbn_clean_db_interval` WRITE;
/*!40000 ALTER TABLE `mbn_clean_db_interval` DISABLE KEYS */;
INSERT INTO `mbn_clean_db_interval` VALUES ('time',24);
/*!40000 ALTER TABLE `mbn_clean_db_interval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_sms_had_send`
--

DROP TABLE IF EXISTS `mbn_sms_had_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_sms_had_send` (
  `id` bigint(20) NOT NULL,
  `merchant_pin` bigint(20) DEFAULT NULL COMMENT 'Ì»PIN,-1ÎªÏµÍ³',
  `operation_id` bigint(20) DEFAULT NULL COMMENT 'ÒµID',
  `batch_id` bigint(20) DEFAULT NULL COMMENT 'Îº',
  `province` varchar(64) DEFAULT NULL COMMENT 'Ê¡Ý´',
  `self_mobile` varchar(20) NOT NULL,
  `tos` varchar(128) NOT NULL COMMENT 'Ô·ë£¨Ö§Èº',
  `tos_name` varchar(64) DEFAULT NULL COMMENT '½ÓÊÕÈËÐÕÃû',
  `content` varchar(512) DEFAULT NULL,
  `cut_apart_number` int(11) DEFAULT '10000' COMMENT 'Ê±Ð·Ö¶',
  `commit_time` datetime DEFAULT NULL COMMENT 'Ã»á½»Ê±',
  `ready_send_time` datetime DEFAULT NULL COMMENT '×¼Ê±',
  `expire_time` datetime DEFAULT NULL COMMENT '¹ýÆÚÊ±¼ä',
  `complete_time` datetime DEFAULT NULL COMMENT 'Ê±',
  `service_id` varchar(64) DEFAULT NULL COMMENT 'ÌØ·þºÅ',
  `tunnel_type` int(11) DEFAULT NULL COMMENT '×°Â¼Ê±Í¨(0Ø£1)',
  `priority_level` int(11) DEFAULT '1' COMMENT '1-50Ô½È¼Ô½',
  `send_result` int(11) DEFAULT NULL COMMENT '-1È¡Ïû·¢ËÍ,0Î´·¢ËÍ,1·¢ËÍÖÐ,2³É¹¦,3Ê§°Ü',
  `fail_reason` varchar(20) DEFAULT NULL COMMENT 'Ê§°ÜÔ­Òò',
  `description` varchar(255) DEFAULT NULL COMMENT '±¸×¢',
  PRIMARY KEY (`id`),
  KEY `indexA` (`merchant_pin`,`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_sms_had_send`
--

LOCK TABLES `mbn_sms_had_send` WRITE;
/*!40000 ALTER TABLE `mbn_sms_had_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_sms_had_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_sms_ready_send`
--

DROP TABLE IF EXISTS `mbn_sms_ready_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_sms_ready_send` (
  `id` bigint(20) NOT NULL,
  `merchant_pin` bigint(20) DEFAULT NULL COMMENT 'Ì»PIN,-1ÎªÏµÍ³',
  `operation_id` bigint(20) DEFAULT NULL COMMENT 'ÒµID',
  `batch_id` bigint(20) DEFAULT NULL COMMENT 'Îº',
  `province` varchar(64) DEFAULT NULL COMMENT 'Ê¡Ý´',
  `self_mobile` varchar(20) NOT NULL,
  `tos` varchar(128) NOT NULL COMMENT 'Ô·ë£¨Ö§Èº',
  `tos_name` varchar(64) DEFAULT NULL COMMENT '½ÓÊÕÈËÐÕÃû',
  `content` varchar(512) NOT NULL,
  `cut_apart_number` int(11) DEFAULT '10000' COMMENT 'Ê±Ð·Ö¶',
  `commit_time` datetime DEFAULT NULL COMMENT 'Ã»á½»Ê±',
  `ready_send_time` datetime DEFAULT NULL COMMENT '×¼Ê±',
  `expire_time` datetime DEFAULT NULL COMMENT '¹ýÆÚÊ±¼ä',
  `complete_time` datetime DEFAULT NULL COMMENT '·µ»Ø½á¹ûÊ±¼ä',
  `service_id` varchar(64) DEFAULT NULL COMMENT 'ÌØ·þºÅ',
  `tunnel_type` int(11) DEFAULT NULL COMMENT '×°Â¼Ê±Í¨(0Ø£1)',
  `priority_level` int(11) DEFAULT NULL COMMENT '1-50Ô½È¼Ô½',
  `send_result` int(11) DEFAULT NULL COMMENT '-1È¡Ïû·¢ËÍ,0Î´·¢ËÍ,1·¢ËÍÖÐ,2³É¹¦,3Ê§°Ü',
  `fail_reason` varchar(20) DEFAULT NULL COMMENT 'Ê§°ÜÔ­Òò',
  `description` varchar(255) DEFAULT NULL COMMENT '±¸×¢',
  PRIMARY KEY (`id`),
  KEY `indexA` (`merchant_pin`,`batch_id`,`ready_send_time`),
  KEY `indexB` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_sms_ready_send`
--

LOCK TABLES `mbn_sms_ready_send` WRITE;
/*!40000 ALTER TABLE `mbn_sms_ready_send` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_sms_ready_send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_sms_up_comm_log`
--

DROP TABLE IF EXISTS `mbn_sms_up_comm_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_sms_up_comm_log` (
  `id` bigint(20) NOT NULL,
  `sender_mobile` varchar(20) NOT NULL COMMENT 'ÉÏÐÐÊÖ»úºÅ',
  `content` varchar(1024) DEFAULT NULL COMMENT 'Ð¶',
  `receiver_service_id` varchar(64) NOT NULL COMMENT '½ÓÊÕÕßÌØ·þºÅ',
  `create_time` datetime NOT NULL COMMENT '´´½¨Ê±¼ä',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ÉÌ»§ÓëÏµÍ³½»»¥Á÷Ë®±í';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_sms_up_comm_log`
--

LOCK TABLES `mbn_sms_up_comm_log` WRITE;
/*!40000 ALTER TABLE `mbn_sms_up_comm_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_sms_up_comm_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_port_status`
--

DROP TABLE IF EXISTS `phone_port_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_port_status` (
  `phone_num` varchar(20) NOT NULL,
  `host` varchar(20) DEFAULT NULL,
  `port_name` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `stop_time` datetime DEFAULT NULL,
  PRIMARY KEY (`phone_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Êý¾Ý¿âÇå³ýÆµÂÊ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_port_status`
--

LOCK TABLES `phone_port_status` WRITE;
/*!40000 ALTER TABLE `phone_port_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `phone_port_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-09 15:53:55
