-- MySQL dump 10.13  Distrib 5.5.41, for Linux (x86_64)
--
-- Host: localhost    Database: mbn3_tgmas_admin
-- ------------------------------------------------------
-- Server version	5.5.41-log

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
-- Table structure for table `mbn_config_merchant`
--

DROP TABLE IF EXISTS `mbn_config_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_config_merchant` (
  `id` bigint(20) unsigned zerofill NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `merchant_pin` bigint(20) NOT NULL COMMENT '商户PIN码(与mbn_merchat表 merchant_pin字段对应)',
  `name` varchar(64) NOT NULL COMMENT '设置项（同类设置加相同前缀）',
  `item_value` varchar(128) DEFAULT NULL COMMENT '值',
  `description` varchar(256) DEFAULT NULL,
  `valid_flag` tinyint(2) DEFAULT '0' COMMENT '0时表示永久生效(即页面上不需要出现checkbox) 1生效 2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_config_merchant`
--

LOCK TABLES `mbn_config_merchant` WRITE;
/*!40000 ALTER TABLE `mbn_config_merchant` DISABLE KEYS */;
INSERT INTO `mbn_config_merchant` VALUES (00130200163502041660,1130200163497944497,'sms_sign_content','',NULL,0),(00130200163502149930,1130200163497944497,'isdelegate','1',NULL,0),(00130200163502356769,1130200163497944497,'masserverip','',NULL,0),(00130200163502421270,1130200163497944497,'gatewaylimit','20',NULL,0),(00130200163502516971,1130200163497944497,'sms_send_limit','50',NULL,0),(00130200163502768011,1130200163497944497,'mmtopport','',NULL,0),(00130245694575097623,1130245694571830898,'sms_sign_content','朝阳',NULL,0),(00130245694575289831,1130245694571830898,'isdelegate','1',NULL,0),(00130245694575395537,1130245694571830898,'masserverip','',NULL,0),(00130245694575446860,1130245694571830898,'gatewaylimit','',NULL,0),(00130245694575689832,1130245694571830898,'sms_send_limit','500',NULL,0),(00130245694575711541,1130245694571830898,'mmtopport','',NULL,0),(08130356367033815229,1130200163497944497,'sms_interaction_validity','1500',NULL,0),(08130356367034050336,1130200163497944497,'sms_meeting_notice_validity','1500',NULL,0),(08130356367034115406,1130200163497944497,'sms_schedule_remind_validity','1500',NULL,0),(08130356367034282530,1130200163497944497,'sms_vote_research_validity','1500',NULL,0),(08130356367034444794,1130200163497944497,'sms_answer_validity','1500',NULL,0),(08130356367034543038,1130200163497944497,'sms_lucky_draw_validity','1500',NULL,0),(08130356367034657484,1130200163497944497,'sms_interaction_priority','3',NULL,0),(08130356367034884681,1130200163497944497,'sms_meetint_notice_priority','3',NULL,0),(08130356367034905239,1130200163497944497,'sms_schedule_remind_priority','3',NULL,0),(08130356367035081200,1130200163497944497,'sms_vote_research_priority','3',NULL,0),(08130356367035249942,1130200163497944497,'sms_answer_priority','3',NULL,0),(08130356367035390233,1130200163497944497,'sms_lucky_draw_priority','3',NULL,0),(08130356367035480114,1130200163497944497,'message_length','325',NULL,0),(08130356367035614360,1130200163497944497,'status_report_is_need','y',NULL,0),(08130356367035778698,1130200163497944497,'sms_send_time_interval','08-20',NULL,0),(08130356367035880749,1130200163497944497,'authentication','page',NULL,0);
/*!40000 ALTER TABLE `mbn_config_merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_config_sys_dictionary`
--

DROP TABLE IF EXISTS `mbn_config_sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_config_sys_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `pid` bigint(20) NOT NULL DEFAULT '-1' COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `type` int(11) NOT NULL COMMENT '1省份,2性别,3大区,4市,5区县,6商圈,7通道类型,10短信发送失败原因',
  `coding` varchar(64) NOT NULL COMMENT '编码（非零）',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `last_modify_time` datetime NOT NULL COMMENT '最后一次修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `attr_info` varchar(255) DEFAULT NULL COMMENT '附加属性(备用)',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8113295647449435741 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_config_sys_dictionary`
--

LOCK TABLES `mbn_config_sys_dictionary` WRITE;
/*!40000 ALTER TABLE `mbn_config_sys_dictionary` DISABLE KEYS */;
INSERT INTO `mbn_config_sys_dictionary` VALUES (8113295647449435739,-1,10,'001','超时','2013-02-04 21:37:05','2013-02-04 21:37:05',NULL,NULL),(8113295647449435740,-1,10,'002','未知','2013-02-04 21:37:06','2013-02-04 21:37:06',NULL,NULL);
/*!40000 ALTER TABLE `mbn_config_sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_h3`
--

DROP TABLE IF EXISTS `mbn_h3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_h3` (
  `id` bigint(20) NOT NULL,
  `mobile_prefix` varchar(20) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '手机号码前3位',
  `corp` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT 'yd表示中国移动;lt表示中国联通;dx表示中国电信',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_h3`
--

LOCK TABLES `mbn_h3` WRITE;
/*!40000 ALTER TABLE `mbn_h3` DISABLE KEYS */;
INSERT INTO `mbn_h3` VALUES (1,'134','yd'),(2,'135','yd'),(3,'136','yd'),(4,'137','yd'),(5,'138','yd'),(6,'139','yd'),(7,'150','yd'),(8,'151','yd'),(9,'152','yd'),(10,'157','yd'),(11,'158','yd'),(12,'159','yd'),(13,'182','yd'),(14,'183','yd'),(15,'187','yd'),(16,'188','yd'),(20,'130','lt'),(21,'131','lt'),(22,'132','lt'),(23,'155','lt'),(24,'156','lt'),(25,'185','lt'),(26,'186','lt'),(30,'133','dx'),(31,'153','dx'),(32,'180','dx'),(33,'189','dx'),(34,'145','lt'),(35,'181','dx'),(36,'147','yd'),(37,'178','yd'),(38,'184','yd'),(39,'176','lt'),(40,'177','dx');
/*!40000 ALTER TABLE `mbn_h3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_lisence`
--

DROP TABLE IF EXISTS `mbn_lisence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_lisence` (
  `lisence_id` bigint(20) NOT NULL,
  `lisence_value` varchar(45) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `user_pwd` varchar(45) DEFAULT NULL,
  `user_key` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`lisence_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_lisence`
--

LOCK TABLES `mbn_lisence` WRITE;
/*!40000 ALTER TABLE `mbn_lisence` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_lisence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_merchant_consume`
--

DROP TABLE IF EXISTS `mbn_merchant_consume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_merchant_consume` (
  `id` bigint(20) unsigned NOT NULL,
  `merchant_pin` bigint(20) NOT NULL COMMENT '商户pin码',
  `tunnel_id` bigint(20) NOT NULL COMMENT '通道',
  `remain_number` bigint(20) DEFAULT NULL COMMENT '剩余条数',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_merchant_consume`
--

LOCK TABLES `mbn_merchant_consume` WRITE;
/*!40000 ALTER TABLE `mbn_merchant_consume` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_merchant_consume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_merchant_tunnel_relation`
--

DROP TABLE IF EXISTS `mbn_merchant_tunnel_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_merchant_tunnel_relation` (
  `id` bigint(20) NOT NULL,
  `merchant_pin` bigint(20) NOT NULL,
  `tunnel_id` bigint(20) DEFAULT NULL,
  `tunnel_ext_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '企业在本通道的扩展码(必须是数字字符串,长度与通道扩展码允许的长度一样，位数不够前面补0)',
  `access_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '通道码+扩展码 企业在本通道的扩展码(必须是数字字符串,长度与通道扩展码允许的长度一样，位数不够前面补0)',
  `price` float DEFAULT NULL COMMENT '成本价（分）',
  `priority_level` int(2) DEFAULT NULL COMMENT '此商户使用通道的优先级顺序',
  `state` int(2) NOT NULL COMMENT '1企业正在用此通道，有效；0此企业已经不用此通道了，无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_merchant_tunnel_relation`
--

LOCK TABLES `mbn_merchant_tunnel_relation` WRITE;
/*!40000 ALTER TABLE `mbn_merchant_tunnel_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_merchant_tunnel_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_merchant_vip`
--

DROP TABLE IF EXISTS `mbn_merchant_vip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_merchant_vip` (
  `merchant_pin` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商户PIN码',
  `merchant_mobile` varchar(20) DEFAULT NULL COMMENT '商户TD号码',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `group_code` varchar(64) DEFAULT NULL COMMENT '集团编码(与mbn_group表group_code字段对应,非ADC开户时为空)',
  `ufid` varchar(64) DEFAULT NULL COMMENT 'UFID（ADC平台对“商户TD号码“生成的唯一号,非ADC开户时为空）',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `name` varchar(64) NOT NULL COMMENT '商户名称',
  `sms_state` varchar(4) DEFAULT NULL COMMENT '短信业务状态(正常b，暂停c，注销d)',
  `mms_state` varchar(4) DEFAULT NULL COMMENT '彩信业务状态(正常b，暂停c，注销d)',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '地市',
  `region` varchar(64) DEFAULT NULL COMMENT '区',
  `sms_access_number` varchar(64) DEFAULT NULL COMMENT '短信接入特服号(ADC开户时,与mbn_group保持一致)',
  `mms_access_number` varchar(64) DEFAULT NULL COMMENT '彩信接入特服号(ADC开户时,与mbn_group保持一致)',
  `fee_code` varchar(64) DEFAULT NULL COMMENT '套餐编号(开户报文为空时与mbn_group保持一致)',
  `platform` varchar(64) DEFAULT '2' COMMENT '0省虚拟企业 1地市虚拟企业 2企业',
  `key_primary` varchar(64) DEFAULT 'adc' COMMENT '主要关键字',
  `key_sub` varchar(64) DEFAULT NULL COMMENT '次要关键字',
  `key_other` varchar(128) DEFAULT NULL COMMENT '其它关键字(多个以逗号分隔)',
  `place_id` int(10) DEFAULT NULL COMMENT '位置ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `email` varchar(128) DEFAULT NULL COMMENT '登陆邮件地址',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `corp_id` int(11) DEFAULT '0',
  `corp_ext` varchar(50) DEFAULT '0',
  PRIMARY KEY (`merchant_pin`),
  UNIQUE KEY `merchant_mobile` (`merchant_mobile`) USING BTREE,
  KEY `group_code` (`group_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1131077242080026103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_merchant_vip`
--

LOCK TABLES `mbn_merchant_vip` WRITE;
/*!40000 ALTER TABLE `mbn_merchant_vip` DISABLE KEYS */;
INSERT INTO `mbn_merchant_vip` VALUES (1130357887033966492,NULL,NULL,'1130357887033966492',NULL,NULL,'V_P_8123384771057830001',NULL,NULL,'8123384771057830001',NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,'2013-02-04 21:54:30',NULL,NULL,0,'0'),(1130357898646335843,NULL,NULL,'1130357898646335843',NULL,NULL,'V_C_8123384771057830003',NULL,NULL,'8123384771057830001','8123384771057830003',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,'2013-02-04 21:56:26',NULL,NULL,0,'0'),(1130786721692056521,NULL,NULL,'1130786721692056521',NULL,NULL,'V_C_8123384771057830016',NULL,NULL,'8123384771057830001','8123384771057830016',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,'2013-03-19 18:40:16',NULL,NULL,0,'0'),(1131077217292459192,NULL,NULL,'1131077217292459192',NULL,NULL,'V_P_1000000001',NULL,NULL,'1000000001',NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,'2013-04-17 20:02:52',NULL,NULL,0,'0'),(1131077242080026102,NULL,NULL,'1131077242080026102',NULL,NULL,'V_C_1000000011',NULL,NULL,'1000000001','1000000011',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,'2013-04-17 20:07:00',NULL,NULL,0,'0');
/*!40000 ALTER TABLE `mbn_merchant_vip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_node`
--

DROP TABLE IF EXISTS `mbn_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_node` (
  `id` bigint(20) NOT NULL COMMENT '业务节点ID',
  `name` varchar(128) DEFAULT NULL COMMENT '业务节点名称',
  `password` varchar(32) NOT NULL COMMENT '业务节点密码,md5hex',
  `webserviceurl` varchar(256) DEFAULT NULL COMMENT '业务节点服务地址',
  `ip` varchar(256) DEFAULT NULL COMMENT '业务节点服务IP',
  `location` varchar(256) DEFAULT NULL COMMENT '业务节点位置',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '业务节点状态 0未开通 1已开通',
  `usewebservice` int(2) NOT NULL DEFAULT '1' COMMENT '是否使用WebService 0不使用 1使用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `memo` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_node`
--

LOCK TABLES `mbn_node` WRITE;
/*!40000 ALTER TABLE `mbn_node` DISABLE KEYS */;
INSERT INTO `mbn_node` VALUES (8131176688540902501,'服务器业务平台节点','e10adc3949ba59abbe56e057f20f883e','http://127.0.0.1/plugservice','127.0.0.1','F25',1,1,'2013-09-24 19:50:27','2014-03-09 13:08:34',NULL);
/*!40000 ALTER TABLE `mbn_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_node_merchant_relation`
--

DROP TABLE IF EXISTS `mbn_node_merchant_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_node_merchant_relation` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `node_id` bigint(20) DEFAULT NULL COMMENT '节点ID',
  `merchant_pin` bigint(20) DEFAULT NULL COMMENT '商户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点商户关联表id, node_id, merchant_pin';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_node_merchant_relation`
--

LOCK TABLES `mbn_node_merchant_relation` WRITE;
/*!40000 ALTER TABLE `mbn_node_merchant_relation` DISABLE KEYS */;
INSERT INTO `mbn_node_merchant_relation` VALUES (8133117859199896099,8131176688540902501,1131077242080026102);
/*!40000 ALTER TABLE `mbn_node_merchant_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_periodc_task`
--

DROP TABLE IF EXISTS `mbn_periodc_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_periodc_task` (
  `id` bigint(20) NOT NULL,
  `merchant_pin` bigint(20) NOT NULL,
  `operation_id` int(2) DEFAULT NULL COMMENT '业务类型，如：普通短信、日程提醒、会议通知等。',
  `awoke_mode` int(11) DEFAULT NULL COMMENT '唤醒模式，按时/日/工作日/周/月/年等;1为每天；2为每周；3为每月',
  `number` int(11) DEFAULT NULL COMMENT '简化实现周期短信；如果awoke_mode为月则它表示第几号；如果awoke_mode为周则它表示第几天；如果awoke_mode为天则它不填表示天天',
  `awake_time` datetime DEFAULT NULL COMMENT '唤醒时间/发送时间点，全天事件为空',
  `begin_time` datetime DEFAULT NULL COMMENT '周期开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '周期结束时间',
  `awoke_rate` int(11) DEFAULT NULL COMMENT '唤醒频率，如按日时则每隔几日，按周时则每隔几周等，最大99',
  `expire_time` int(11) unsigned DEFAULT NULL COMMENT '从awake_time起的分钟数,必须是整数,小于十分钟按十分钟算，大于一个月按一天算',
  `div_day` int(11) DEFAULT NULL COMMENT '指定某天',
  `div_week` int(11) DEFAULT NULL COMMENT 'div_week',
  `div_day_of_week` int(11) unsigned zerofill DEFAULT NULL COMMENT '星期几，二进制表示，01111111=127表示每周所有天。',
  `div_month` int(11) DEFAULT NULL COMMENT '指定某月',
  `times` int(11) DEFAULT NULL COMMENT '结束次数，执行多少次后结束,-1表示无结束日期',
  `send_times` int(11) DEFAULT '1' COMMENT '已执行次数',
  `status` int(2) unsigned zerofill NOT NULL COMMENT '任务执行状态，0：待执行，1：执行中，2：执行完成，其它则执行过程中发送错误',
  `calendar_type` int(2) NOT NULL COMMENT '日历类型，0：公历，1：农历',
  `last_time` datetime NOT NULL COMMENT '上次执行时间',
  `task_type` int(2) NOT NULL COMMENT '1短信；2彩信',
  `creater` bigint(20) unsigned NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_periodc_task`
--

LOCK TABLES `mbn_periodc_task` WRITE;
/*!40000 ALTER TABLE `mbn_periodc_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_periodc_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_tunnel`
--

DROP TABLE IF EXISTS `mbn_tunnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_tunnel` (
  `id` bigint(20) NOT NULL COMMENT '通道标识',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '通道名称',
  `access_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '特服号',
  `state` int(11) DEFAULT NULL COMMENT '0不可用；1可用',
  `corp_ext_len` int(2) DEFAULT NULL COMMENT '允许的企业扩展码位数',
  `classify` int(2) DEFAULT NULL COMMENT '1本省移动；2移动；3本省联通；4联通；5本省电信；6电信；7全网;8猫；9TD话机',
  `gateway_addr` varchar(20) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '网关地址',
  `gateway_port` int(10) DEFAULT NULL COMMENT '端口',
  `user` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '用户名',
  `passwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '密码，DES算法加密',
  `province` varchar(64) DEFAULT NULL COMMENT '所属省份',
  `type` int(2) DEFAULT NULL COMMENT '1短信；2彩信；',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '记录通道说明',
  `sms_send_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '短信网关发送URL',
  `sms_receive_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '短信网关接收URL',
  `sms_report_path` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '短信网关状态报告URL',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_status` int(2) DEFAULT '0' COMMENT '表示删除(0：显示，1：删除)',
  `attribute` int(2) DEFAULT NULL COMMENT '通道属性：1直连网关；2第三方通道',
  `service_id` varchar(64) DEFAULT NULL COMMENT '短信业务代码',
  `sms_corp_ident` varchar(64) DEFAULT NULL COMMENT '短信企业代码',
  `protocal_version` varchar(45) DEFAULT NULL,
  `tunnel_range` int(10) DEFAULT NULL COMMENT '四种状态：0001=1（本省移动支持），0010=2（他省移动支持）0100=4（联通支持）1000=8（电信支持）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_tunnel`
--

LOCK TABLES `mbn_tunnel` WRITE;
/*!40000 ALTER TABLE `mbn_tunnel` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_tunnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mbn_tunnel_account`
--

DROP TABLE IF EXISTS `mbn_tunnel_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mbn_tunnel_account` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识',
  `tunnel_id` bigint(20) NOT NULL COMMENT '通道标识',
  `balance_number` int(11) NOT NULL COMMENT '剩余条数',
  `balance_amount` float NOT NULL COMMENT '剩余钱',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mbn_tunnel_account`
--

LOCK TABLES `mbn_tunnel_account` WRITE;
/*!40000 ALTER TABLE `mbn_tunnel_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `mbn_tunnel_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_operation`
--

DROP TABLE IF EXISTS `portal_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_operation` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '可用；不可用；',
  `operation` int(2) NOT NULL COMMENT '0：不可用；1：可用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者标识',
  `update_by` bigint(20) DEFAULT NULL COMMENT '变更者标识',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '操作描述',
  `type` int(4) unsigned DEFAULT NULL COMMENT '0默认，1通讯录操作，',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_operation`
--

LOCK TABLES `portal_operation` WRITE;
/*!40000 ALTER TABLE `portal_operation` DISABLE KEYS */;
INSERT INTO `portal_operation` VALUES (1,'可用',1,'2012-11-16 13:40:27',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `portal_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_region`
--

DROP TABLE IF EXISTS `portal_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_region` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '区域名称',
  `code` varchar(20) NOT NULL COMMENT '区域代码',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级区域标识',
  `ip_address` varchar(20) DEFAULT NULL COMMENT '该区域的管理地址',
  `web_domain` varchar(200) DEFAULT NULL COMMENT '该区域的管理域名',
  `active_flag` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_region`
--

LOCK TABLES `portal_region` WRITE;
/*!40000 ALTER TABLE `portal_region` DISABLE KEYS */;
INSERT INTO `portal_region` VALUES (1000000001,'MAS省份','ln',-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1000000011,'MAS地市','masCity',1000000001,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `portal_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_resource`
--

DROP TABLE IF EXISTS `portal_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_resource` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '资源名称',
  `url` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '资源对应url',
  `type` int(2) NOT NULL COMMENT '资源类别：1模块,2菜单,3页面,4导航,5业务实体',
  `pid` bigint(20) NOT NULL DEFAULT '-1' COMMENT '父id，唯一标识号',
  `position_sort` int(2) NOT NULL COMMENT '菜单显示排序用',
  `is_management_fun` int(2) NOT NULL DEFAULT '0' COMMENT '1是管理功能,0不是管理功能',
  `active_flag` int(2) NOT NULL DEFAULT '1' COMMENT '有效标志：1有效；0无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '建创者标识',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者标识',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '资源描述',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL,
  `is_mobile` int(11) DEFAULT NULL COMMENT '是否为移动管理需要的功能。 1 省管理员移动功能 0 业务用户 未设值则为移动业务都具备的功能 2地市管理员功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_resource`
--

LOCK TABLES `portal_resource` WRITE;
/*!40000 ALTER TABLE `portal_resource` DISABLE KEYS */;
INSERT INTO `portal_resource` VALUES (1,'短信中心','/index1.action',2,-1,1,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'ggfb',0),(2,'发送短信','./smssend/writesms.action',2,1,2,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'',0),(3,'短信列表','./sms/smssend/jsp/sms_info.jsp',2,1,4,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'',0),(4,'通讯录','/tunnelManage/jsp/smstunnel_info.jsp',2,-1,10,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'txlgl',0),(5,'联系人列表','./ap/address/addresslist.jsp',2,4,11,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(6,'联系人导入','./ap/address/import_contacts.jsp',2,4,12,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(7,'联系人分组','./ap/address/show_group.jsp',2,4,13,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(8,'用户管理','./userAction/queryForward.action',2,-1,53,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'customeradmin',0),(9,'创建编辑','./userAction/forward.action?flag=addForward',2,8,54,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(10,'用户列表','./userAction/queryForward.action',2,8,55,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(11,'角色管理','./ap/role/rolelist.jsp',2,-1,56,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'roleadmin',0),(12,'创建编辑','./roleAction/masforward.action?flag=addForward',2,11,57,0,1,'2012-12-13 17:31:38',NULL,NULL,NULL,NULL,NULL,0),(13,'角色列表','./delegatemas/role/rolelist.jsp',2,11,58,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(14,'系统管理','./tunnelManage/jsp/smstunnel_info.jsp',2,-1,59,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'xtsz',0),(15,'参数设置','./systemSettingsAction/showParas.action',2,14,60,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(19,'省企业管理','./ap/corpmanage/showMerchantVip.jsp',2,-1,25,1,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'corpadmin',1),(20,'企业统计','./delegatemas/corpmanage/corpcountbycitylist.jsp',2,19,20,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,1),(21,'会议通知','/index2.action',2,-1,4,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'hytz',0),(22,'发送会议通知','./meeting/writesms.action',2,21,5,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(23,'会议通知列表','./sms/meeting/jsp/sms_info.jsp',2,21,6,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,0),(24,'彩信中心','/index1.action',2,-1,7,0,1,'2012-12-17 14:49:37',NULL,NULL,NULL,NULL,'cxb',0),(25,'发送彩信','./mmsAction/writeMms.action',2,24,8,0,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,0),(26,'彩信列表','./mms/newspaper/jsp/mms_news.jsp',2,24,9,0,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,0),(27,'企业列表','./delegatemas/corpmanage/corplist.jsp',2,19,27,1,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'',1),(29,'地市管理员','./userAction/queryForward.action',2,-1,29,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,'customeradmin',1),(30,'创建编辑','./userAction/forward.action?flag=addForward',2,29,30,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,1),(31,'管理员列表','./userAction/queryForward.action',2,29,31,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,1),(32,'管理员角色','./ap/role/rolelist.jsp',2,-1,32,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,'roleadmin',1),(33,'创建编辑','./roleAction/masforward.action?flag=addForward',2,32,33,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,1),(34,'角色列表','./delegatemas/role/rolelist.jsp',2,32,34,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,1),(35,'企业管理','./ap/corpmanage/showMerchantVip.jsp',2,-1,35,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,'corpadmin',2),(36,'企业列表','./delegatemas/corpmanage/corplist.jsp',2,35,36,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,2),(37,'创建编辑','./corpManageAction/preCorpConfigInfoNodes.action',2,35,37,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,2),(38,'企业管理员','./userAction/queryForward.action',2,-1,38,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,'customeradmin',2),(39,'创建编辑','./userAction/forward.action?flag=addForward',2,38,40,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,2),(40,'帐户列表','./userAction/queryForward.action',2,38,39,1,1,'2012-01-10 11:23:00',NULL,NULL,NULL,NULL,NULL,2),(44,'省管理员','./userAction/queryForward.action',2,-1,44,1,1,'2013-01-17 11:23:00',NULL,NULL,NULL,NULL,'customeradmin',3),(45,'创建编辑','./userAction/forward.action?flag=addForward',2,44,45,1,1,'2013-01-17 11:23:00',NULL,NULL,NULL,NULL,NULL,3),(46,'管理员列表','./userAction/queryForward.action',2,44,46,1,1,'2013-01-17 11:23:00',NULL,NULL,NULL,NULL,NULL,3),(47,'省管理员角色','./ap/role/rolelist.jsp',2,-1,47,1,1,'2013-01-17 11:23:00',NULL,NULL,NULL,NULL,'roleadmin',3),(48,'创建编辑','./roleAction/masforward.action?flag=addForward',2,47,48,1,1,'2013-01-17 11:23:00',NULL,NULL,NULL,NULL,NULL,3),(49,'角色列表','./delegatemas/role/rolelist.jsp',2,47,49,1,1,'2013-01-17 11:23:00',NULL,NULL,NULL,NULL,NULL,3),(50,'动态短信','./smssend/writesms.action?flag=dynamic',2,1,3,0,1,'0000-00-00 00:00:00',NULL,NULL,NULL,NULL,'',0),(51,'查询统计','./statistic/sms_query.jsp',2,-1,51,0,1,'2013-08-18 20:18:18',NULL,NULL,NULL,NULL,'smstatistic',NULL),(52,'短信查询统计','./statistic/sms_query.jsp',2,51,52,0,1,'2013-08-18 20:18:33',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `portal_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_role`
--

DROP TABLE IF EXISTS `portal_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_role` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '角色名称',
  `active_flag` int(2) NOT NULL COMMENT '有效标志：1有效；0无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者标识',
  `update_by` bigint(20) DEFAULT NULL COMMENT '变更者标识',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '角色描述',
  `merchant_pin` bigint(20) DEFAULT NULL COMMENT '角色隶属商户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_role`
--

LOCK TABLES `portal_role` WRITE;
/*!40000 ALTER TABLE `portal_role` DISABLE KEYS */;
INSERT INTO `portal_role` VALUES (1,'系统管理员',1,'2013-02-04 21:36:08',NULL,NULL,NULL,NULL,NULL),(2,'企业管理员',1,'2013-02-04 21:36:08',NULL,NULL,NULL,NULL,NULL),(8131077182104811801,'MAS省管理员',1,'2013-04-17 19:57:01',NULL,1,NULL,'MAS省管理员',10001),(8131077232487131409,'MAS地市管理员',1,'2013-04-17 20:05:24',NULL,8131077217293613274,NULL,'MAS地市管理员',1131077217292459192);
/*!40000 ALTER TABLE `portal_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_role_resource_operation_relation`
--

DROP TABLE IF EXISTS `portal_role_resource_operation_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_role_resource_operation_relation` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `role_id` bigint(20) NOT NULL COMMENT '角色标识',
  `resource_id` bigint(20) NOT NULL COMMENT '资源标识',
  `operation_id` bigint(20) NOT NULL COMMENT '操作标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) unsigned zerofill DEFAULT NULL COMMENT '创建者标识',
  `update_by` bigint(20) DEFAULT NULL COMMENT '变更者标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_role_resource_operation_relation`
--

LOCK TABLES `portal_role_resource_operation_relation` WRITE;
/*!40000 ALTER TABLE `portal_role_resource_operation_relation` DISABLE KEYS */;
INSERT INTO `portal_role_resource_operation_relation` VALUES (1,1,44,1,'2013-02-04 21:36:38',NULL,NULL,NULL),(2,1,47,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(3,2,4,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(4,2,1,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(5,2,24,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(6,2,21,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(7,2,8,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(8,2,11,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(9,2,14,1,'2013-02-04 21:36:39',NULL,NULL,NULL),(10,2,51,1,'2013-08-18 20:52:48',NULL,NULL,NULL),(8131077182105304328,8131077182104811801,29,1,'2013-04-17 19:57:01',NULL,00000000000000000001,NULL),(8131077182105576488,8131077182104811801,32,1,'2013-04-17 19:57:01',NULL,00000000000000000001,NULL),(8131077182105651283,8131077182104811801,19,1,'2013-04-17 19:57:01',NULL,00000000000000000001,NULL),(8131077232487494276,8131077232487131409,38,1,'2013-04-17 20:05:24',NULL,08131077217293613274,NULL),(8131077232487692907,8131077232487131409,35,1,'2013-04-17 20:05:24',NULL,08131077217293613274,NULL);
/*!40000 ALTER TABLE `portal_role_resource_operation_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_user`
--

DROP TABLE IF EXISTS `portal_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_user` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `merchant_pin` bigint(20) DEFAULT NULL COMMENT '商户pin码,兼容业务系统需要',
  `login_account` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '登录账号',
  `login_pwd` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci NOT NULL COMMENT '登录密码,DES加密',
  `user_ext_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '用户短信上下行扩展码',
  `login_type` int(2) DEFAULT '1' COMMENT '登录鉴权方式  1:用户名或密码  2：用户名+密码+短信验证',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '邮箱',
  `department_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '公司及部门名称',
  `duty` varchar(128) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '职务',
  `gender` int(2) DEFAULT NULL COMMENT '性别 0:女 1:男',
  `user_type` int(2) unsigned NOT NULL COMMENT '用户类型0:超级管理员 1:省系统管理员；2：地市管理员3、企业超级管理员4、企业用户',
  `lock_flag` int(2) NOT NULL COMMENT '锁定标志：1锁定0非锁定',
  `limit_try_count` int(11) NOT NULL COMMENT '失败可重试次数,不是还有几次重试机会',
  `active_flag` int(2) NOT NULL COMMENT '有效标志：1有效；0无效',
  `login_time` datetime DEFAULT NULL COMMENT '本次登录时间',
  `first_login_flag` int(2) NOT NULL DEFAULT '0' COMMENT '首次登录标识（0:未登录过,1:已经登录过）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者标识',
  `update_by` bigint(20) DEFAULT NULL COMMENT '变更者标识',
  `ip_limit_flag` int(2) DEFAULT NULL COMMENT '是否限制ip',
  `ip_address` varchar(15) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '限制ip地址',
  `province` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '地市',
  `region` varchar(64) CHARACTER SET utf8 COLLATE utf8_swedish_ci DEFAULT NULL COMMENT '区',
  `corp_access_number` varchar(64) DEFAULT NULL COMMENT '用户对应的企业的短信网关接入号，一个企业一个（河北托管MAS用）',
  `webservice` int(11) DEFAULT '1' COMMENT '用户使用的登录方式。1：页面；2：webservice。',
  `zxt_user_id` varchar(64) DEFAULT NULL COMMENT '资信通7位扩展码',
  `zxt_login_acount` varchar(64) DEFAULT NULL,
  `zxt_pwd` varchar(64) DEFAULT NULL,
  `zxt_id` varchar(64) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_user`
--

LOCK TABLES `portal_user` WRITE;
/*!40000 ALTER TABLE `portal_user` DISABLE KEYS */;
INSERT INTO `portal_user` VALUES (1,10001,'system_admin','BICinmdnNTQ04T6ETrWy2w==',NULL,1,'系统管理员',NULL,NULL,NULL,NULL,NULL,0,0,3,1,NULL,0,'2013-02-04 21:35:40','2013-04-17 20:04:00',NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,'0'),(8131077217293613274,1131077217292459192,'masprovince_admin','Ewj5tpBY/c4m0dxGZM3Qcg==',NULL,NULL,'MAS省管理员','13900000000','',NULL,NULL,1,1,0,0,1,NULL,0,'2013-04-17 20:02:52',NULL,1,NULL,0,NULL,'1000000001',NULL,NULL,NULL,1,NULL,NULL,NULL,'0'),(8131077242081970003,1131077242080026102,'mascity_admin','JgoHITILYgIiBSz7/M4YoA==',NULL,NULL,'MAS地市管理员','13800000000','',NULL,NULL,1,2,0,0,1,NULL,0,'2013-04-17 20:07:00',NULL,8131077217293613274,NULL,0,NULL,'1000000001','1000000011',NULL,NULL,1,NULL,NULL,NULL,'0');
/*!40000 ALTER TABLE `portal_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_user_ext`
--

DROP TABLE IF EXISTS `portal_user_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_user_ext` (
  `id` bigint(20) NOT NULL COMMENT '用户ID，对应portal_user',
  `sms_limit` int(2) DEFAULT '0' COMMENT '是否启用发送限制 0无限制 1限制',
  `sms_limit_period` int(2) DEFAULT '0' COMMENT '限制统计周期 0日 1月',
  `sms_limit_count` int(10) DEFAULT '0' COMMENT '周期内可发送数量',
  `sms_priority` int(2) DEFAULT '0' COMMENT '用户发送短信优先级',
  `sms_send_count` int(10) DEFAULT '0' COMMENT '周期内已发送数量',
  `count_time` datetime DEFAULT NULL COMMENT '统计时间',
  `memo` varchar(32) DEFAULT NULL COMMENT '备注',
  `sms_mobile` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_user_ext`
--

LOCK TABLES `portal_user_ext` WRITE;
/*!40000 ALTER TABLE `portal_user_ext` DISABLE KEYS */;
/*!40000 ALTER TABLE `portal_user_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portal_user_role_relation`
--

DROP TABLE IF EXISTS `portal_user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portal_user_role_relation` (
  `id` bigint(20) NOT NULL COMMENT '唯一标识号 20位数字(格式见数据库设计定义)',
  `user_id` bigint(20) NOT NULL COMMENT '用户标识',
  `role_id` bigint(20) NOT NULL COMMENT '角色标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者标识',
  `update_by` bigint(20) DEFAULT NULL COMMENT '变更者标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portal_user_role_relation`
--

LOCK TABLES `portal_user_role_relation` WRITE;
/*!40000 ALTER TABLE `portal_user_role_relation` DISABLE KEYS */;
INSERT INTO `portal_user_role_relation` VALUES (1,1,1,'2013-02-04 21:36:56',NULL,NULL,NULL),(8131077217293984874,8131077217293613274,8131077182104811801,'2013-04-17 20:02:52',NULL,1,NULL),(8131077242082111399,8131077242081970003,8131077232487131409,'2013-04-17 20:07:00',NULL,8131077217293613274,NULL);
/*!40000 ALTER TABLE `portal_user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_mapping`
--

DROP TABLE IF EXISTS `schedule_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_mapping` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `gatewayID` varchar(32) DEFAULT NULL COMMENT '标识企业短、彩信网关',
  `province_code` varchar(32) DEFAULT NULL COMMENT '省码',
  `area_code` varchar(32) DEFAULT NULL COMMENT '地市码',
  `ip` varchar(32) DEFAULT '127.0.0.1' COMMENT '通讯机ip',
  `port` int(10) DEFAULT '7890' COMMENT '通讯机端口',
  `run_status` varchar(32) DEFAULT NULL COMMENT '运行状态',
  `start_time` varchar(20) DEFAULT NULL COMMENT '开始时间',
  `running_time` varchar(20) DEFAULT NULL COMMENT '运行时长',
  `listen_port` int(10) NOT NULL DEFAULT '7001' COMMENT '短信监听端口，该端口从7001开始，每增一个服务，递增1',
  `mas_ip` varchar(32) DEFAULT NULL COMMENT 'Mas系统运行ip',
  `gw_ip` varchar(32) DEFAULT NULL COMMENT '短信/彩信网关ip',
  `gw_port` int(10) DEFAULT NULL COMMENT '短信/彩信网关端口',
  `gw_user` varchar(20) DEFAULT NULL COMMENT '登陆网关用户名',
  `gw_passwd` varchar(20) DEFAULT NULL COMMENT '登陆网关密码',
  `enterprise_code` varchar(20) DEFAULT NULL COMMENT '企业代码',
  `protocol_version` varchar(20) DEFAULT NULL COMMENT '协议类型：cmpp2.0/cmpp3.0/mm7',
  `tunnel_id` int(10) unsigned zerofill DEFAULT '0000000000' COMMENT '通讯机短/彩信服务映射转发机绑定端口，取值范围【1~1000】，每增一个服务，递增1。\\\\r\\\\n彩信取值范围【10001~11000】。\\\\r\\\\n根据协议：转发机绑定端口=通讯机服务端口+7000,\\\\r\\\\n即1<-->7001,1000<-->8000;\\\\r\\\\n10001<-->17001...',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `senderCode` varchar(21) DEFAULT NULL COMMENT '特服号',
  `sendSpeed` int(5) DEFAULT '5' COMMENT '下行速率',
  `mms_url` varchar(20) DEFAULT '/vas/' COMMENT '彩信url',
  `service_code` varchar(20) DEFAULT NULL COMMENT '短、彩信业务代码',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=197 DEFAULT CHARSET=utf8 CHECKSUM=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_mapping`
--

LOCK TABLES `schedule_mapping` WRITE;
/*!40000 ALTER TABLE `schedule_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-06 21:22:45
