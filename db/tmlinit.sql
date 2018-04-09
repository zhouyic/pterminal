-- MySQL dump 10.11
--
-- Host: localhost    Database: tml
-- ------------------------------------------------------
-- Server version	5.1.37-log

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
-- Table structure for table `SYS_CONFIGURE`
--

DROP TABLE IF EXISTS `SYS_CONFIGURE`;
CREATE TABLE `SYS_CONFIGURE` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `DDESC` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_CONFIGURE`
--

LOCK TABLES `SYS_CONFIGURE` WRITE;
/*!40000 ALTER TABLE `SYS_CONFIGURE` DISABLE KEYS */;
INSERT INTO `SYS_CONFIGURE` VALUES (600001,'DRM_IP','192.168.100.20','加密机服务器的IP'),(600002,'LOCAL_DRM_IP','192.168.100.20','驻地CEP服务器的IP'),(600003,'RTSP_URL','rtsp://172.20.21.11:554/','会话管理服务器地址和端口'),(600004,'DRM_PORT','10190','加密机服务器的PORT');
/*!40000 ALTER TABLE `SYS_CONFIGURE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_DOM`
--

DROP TABLE IF EXISTS `SYS_DOM`;
CREATE TABLE `SYS_DOM` (
  `DOMID` int(11) NOT NULL DEFAULT '0',
  `DONAME` varchar(50) NOT NULL,
  `PARENTID` int(11) DEFAULT '-1',
  `PARENTNAME` varchar(50) DEFAULT NULL,
  `TYPE` int(11) DEFAULT '0',
  `DDESC` varchar(50) DEFAULT NULL,
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATOR` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`DOMID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_DOM`
--

LOCK TABLES `SYS_DOM` WRITE;
/*!40000 ALTER TABLE `SYS_DOM` DISABLE KEYS */;
/*!40000 ALTER TABLE `SYS_DOM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_HISTORY`
--

DROP TABLE IF EXISTS `SYS_HISTORY`;
CREATE TABLE `SYS_HISTORY` (
  `HISTORYID` int(11) NOT NULL DEFAULT '0',
  `HISTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USERID` int(11) DEFAULT '0',
  `USERNAME` varchar(200) DEFAULT NULL,
  `LOGTYPE` int(11) DEFAULT '0',
  `CLIENTIP` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`HISTORYID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_HISTORY`
--

LOCK TABLES `SYS_HISTORY` WRITE;
/*!40000 ALTER TABLE `SYS_HISTORY` DISABLE KEYS */;
/*!40000 ALTER TABLE `SYS_HISTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_LOG`
--

DROP TABLE IF EXISTS `SYS_LOG`;
CREATE TABLE `SYS_LOG` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `WWHO` varchar(255) DEFAULT NULL,
  `TTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DDESC` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_LOG`
--

LOCK TABLES `SYS_LOG` WRITE;
/*!40000 ALTER TABLE `SYS_LOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `SYS_LOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_LOG_SEQ`
--

DROP TABLE IF EXISTS `SYS_LOG_SEQ`;
CREATE TABLE `SYS_LOG_SEQ` (
  `NNEXT` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`NNEXT`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_LOG_SEQ`
--

LOCK TABLES `SYS_LOG_SEQ` WRITE;
/*!40000 ALTER TABLE `SYS_LOG_SEQ` DISABLE KEYS */;
INSERT INTO `SYS_LOG_SEQ` VALUES (1);
/*!40000 ALTER TABLE `SYS_LOG_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_ROLE`
--

DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `ROLEID` int(11) NOT NULL DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `PARENT` varchar(255) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT '0',
  PRIMARY KEY (`ROLEID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_ROLE`
--

LOCK TABLES `SYS_ROLE` WRITE;
/*!40000 ALTER TABLE `SYS_ROLE` DISABLE KEYS */;
INSERT INTO `SYS_ROLE` VALUES (101,'注册终端','RegesteTerminal','终端管理',1),(102,'终端管理','TmlManage','终端管理',1),(103,'终端监控','TmlMonitor','终端管理',1),(104,'服务器管理','ListManage','终端管理',1),(105,'批量注册终端','BatchRegisterTmls','终端管理',1),(106,'终端绑定SPE','DispatchSpe','终端管理',1),(107,'终端&SPE管理','TmlSpeManage','终端管理',1),(108,'测试升级服务器','UpgradeManage','终端管理',1),(801,'操作日志查询','QueryLog','日志管理',1),(802,'操作日志删除','DeleteLog','日志管理',900),(803,'登陆日志查询','QueryLoginLog','日志管理',1),(804,'登陆日志删除','DeleteLoginLog','日志管理',900),(901,'分组管理','AreaManage','系统管理',1),(902,'系统配置','ListProperty','系统管理',900),(903,'修改密码','UpdateUserPwd','系统管理',1),(904,'增加操作员','AddUser','系统管理',900),(905,'管理操作员','ListUser','系统管理',900);
/*!40000 ALTER TABLE `SYS_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_USER`
--

DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `USERID` int(11) NOT NULL DEFAULT '0',
  `TYPE` int(11) DEFAULT '0',
  `NAME` varchar(255) DEFAULT NULL,
  `PWD` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PHONE` varchar(255) DEFAULT NULL,
  `MOBILE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USERID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_USER`
--

LOCK TABLES `SYS_USER` WRITE;
/*!40000 ALTER TABLE `SYS_USER` DISABLE KEYS */;
INSERT INTO `SYS_USER` VALUES (1,900,'admin','21232F297A57A5A743894A0E4A801FC3','','','');
/*!40000 ALTER TABLE `SYS_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_USERID_GEN`
--

DROP TABLE IF EXISTS `SYS_USERID_GEN`;
CREATE TABLE `SYS_USERID_GEN` (
  `NNEXT` int(11) DEFAULT NULL,
  `NEXT_DOM` int(11) DEFAULT NULL,
  `NEXT_HISTID` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_USERID_GEN`
--

LOCK TABLES `SYS_USERID_GEN` WRITE;
/*!40000 ALTER TABLE `SYS_USERID_GEN` DISABLE KEYS */;
INSERT INTO `SYS_USERID_GEN` VALUES (10000000,10000000,10000000);
/*!40000 ALTER TABLE `SYS_USERID_GEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_USER_DOM`
--

DROP TABLE IF EXISTS `SYS_USER_DOM`;
CREATE TABLE `SYS_USER_DOM` (
  `USERID` int(11) DEFAULT NULL,
  `DOMID` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_USER_DOM`
--

LOCK TABLES `SYS_USER_DOM` WRITE;
/*!40000 ALTER TABLE `SYS_USER_DOM` DISABLE KEYS */;
INSERT INTO `SYS_USER_DOM` VALUES (1,1);
/*!40000 ALTER TABLE `SYS_USER_DOM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SYS_USER_ROLE`
--

DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE` (
  `USERID` int(11) DEFAULT NULL,
  `ROLEID` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `SYS_USER_ROLE`
--

LOCK TABLES `SYS_USER_ROLE` WRITE;
/*!40000 ALTER TABLE `SYS_USER_ROLE` DISABLE KEYS */;
INSERT INTO `SYS_USER_ROLE` VALUES (1,101),(1,102),(1,103),(1,104),(1,105),(1,106),(1,107),(1,108),(1,801),(1,802),(1,803),(1,804),(1,901),(1,902),(1,903),(1,904),(1,905);
/*!40000 ALTER TABLE `SYS_USER_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_AD`
--

DROP TABLE IF EXISTS `TML_AD`;
CREATE TABLE `TML_AD` (
  `adId` varchar(64) NOT NULL DEFAULT '',
  `adType` int(11) DEFAULT '0',
  `listName` varchar(64) DEFAULT NULL,
  `CXMLName` varchar(64) DEFAULT NULL,
  `outDate` varchar(64) DEFAULT NULL,
  `inTime` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`adId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_AD`
--

LOCK TABLES `TML_AD` WRITE;
/*!40000 ALTER TABLE `TML_AD` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_AD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_BASE`
--

DROP TABLE IF EXISTS `TML_BASE`;
CREATE TABLE `TML_BASE` (
  `groupId` int(11) DEFAULT '0',
  `unitId` int(11) DEFAULT '0',
  `tmlId` varchar(16) NOT NULL DEFAULT '',
  `tmlType` int(11) DEFAULT '0',
  `customerId` varchar(64) DEFAULT NULL,
  `customerType` varchar(16) DEFAULT NULL,
  `customerPwd` varchar(64) DEFAULT NULL,
  `oldCustomerId` varchar(64) DEFAULT NULL,
  `oldCustomerPwd` varchar(64) DEFAULT NULL,
  `inOperator` varchar(32) DEFAULT NULL,
  `inTime` varchar(64) DEFAULT NULL,
  `tmlStatus` int(11) DEFAULT '0',
  `ipAddr` varchar(64) DEFAULT NULL,
  `onTime` varchar(64) DEFAULT NULL,
  `outTime` varchar(64) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  `isSpe` int(11) DEFAULT '0',
  `adId` varchar(32) DEFAULT NULL,
  `uadId` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`tmlId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_BASE`
--

LOCK TABLES `TML_BASE` WRITE;
/*!40000 ALTER TABLE `TML_BASE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_BASE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_BILLING`
--

DROP TABLE IF EXISTS `TML_BILLING`;
CREATE TABLE `TML_BILLING` (
  `orderId` varchar(64) NOT NULL DEFAULT '',
  `tmlId` varchar(16) DEFAULT NULL,
  `bossId` varchar(32) DEFAULT NULL,
  `contentId` varchar(64) DEFAULT NULL,
  `contentName` varchar(64) DEFAULT NULL,
  `releaseId` varchar(64) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `syncServiceId` varchar(64) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `statusResult` int(11) DEFAULT '0',
  `inTime` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_BILLING`
--

LOCK TABLES `TML_BILLING` WRITE;
/*!40000 ALTER TABLE `TML_BILLING` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_BILLING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_CURRENT_DO`
--

DROP TABLE IF EXISTS `TML_CURRENT_DO`;
CREATE TABLE `TML_CURRENT_DO` (
  `id` int(11) NOT NULL DEFAULT '0',
  `tmlId` varchar(16) DEFAULT NULL,
  `tmlStatus` int(11) DEFAULT '0',
  `cntType` int(11) DEFAULT '0',
  `tmlDoingCnt` varchar(128) DEFAULT NULL,
  `tmlDownrate` varchar(32) DEFAULT '0B/S',
  `tmlDownsource` varchar(64) DEFAULT NULL,
  `inTime` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_CURRENT_DO`
--

LOCK TABLES `TML_CURRENT_DO` WRITE;
/*!40000 ALTER TABLE `TML_CURRENT_DO` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_CURRENT_DO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_LOG`
--

DROP TABLE IF EXISTS `TML_LOG`;
CREATE TABLE `TML_LOG` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `tmlId` varchar(16) DEFAULT NULL,
  `doAction` varchar(16) DEFAULT NULL,
  `doObject` varchar(128) DEFAULT NULL,
  `doTime` varchar(64) DEFAULT NULL,
  `session` varchar(32) DEFAULT NULL,
  `startTime` varchar(64) DEFAULT NULL,
  `endTime` varchar(64) DEFAULT NULL,
  `doResult` varchar(16) DEFAULT NULL,
  `exception` varchar(256) DEFAULT NULL,
  `inTime` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_LOG`
--

LOCK TABLES `TML_LOG` WRITE;
/*!40000 ALTER TABLE `TML_LOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_LOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_ORDER`
--

DROP TABLE IF EXISTS `TML_ORDER`;
CREATE TABLE `TML_ORDER` (
  `orderId` varchar(64) NOT NULL DEFAULT '',
  `tmlId` varchar(64) DEFAULT NULL,
  `serviceType` int(11) DEFAULT '0',
  `programId` varchar(64) DEFAULT NULL,
  `contentId` varchar(64) DEFAULT NULL,
  `contentName` varchar(64) DEFAULT NULL,
  `movieName` varchar(64) DEFAULT NULL,
  `episodeIndex` int(11) DEFAULT '0',
  `episodes` int(11) DEFAULT '0',
  `snap` varchar(64) DEFAULT NULL,
  `movieSize` int(11) DEFAULT '0',
  `chargeMode` int(11) DEFAULT '0',
  `count` int(11) DEFAULT '0',
  `listName` varchar(64) DEFAULT NULL,
  `CXMLName` varchar(64) DEFAULT NULL,
  `licenseName` varchar(64) DEFAULT NULL,
  `parentName` varchar(64) DEFAULT NULL,
  `startTime` varchar(32) DEFAULT '0000-00-00 00:00:00',
  `endTime` varchar(32) DEFAULT '0000-00-00 00:00:00',
  `showTime` varchar(16) DEFAULT '0000-00-00',
  `tarFileName` varchar(64) DEFAULT NULL,
  `idxFileName` varchar(64) DEFAULT NULL,
  `runTime` int(11) DEFAULT '0',
  `directors` varchar(64) DEFAULT NULL,
  `actors` varchar(64) DEFAULT NULL,
  `country` varchar(32) DEFAULT NULL,
  `pLauguage` varchar(32) DEFAULT NULL,
  `screenFormat` varchar(32) DEFAULT NULL,
  `portalType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `description` varchar(256) DEFAULT NULL,
  `inTime` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_ORDER`
--

LOCK TABLES `TML_ORDER` WRITE;
/*!40000 ALTER TABLE `TML_ORDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_ORDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_SERVER_LIST`
--

DROP TABLE IF EXISTS `TML_SERVER_LIST`;
CREATE TABLE `TML_SERVER_LIST` (
  `id` int(11) NOT NULL DEFAULT '0',
  `ip` varchar(64) DEFAULT NULL,
  `port` int(11) DEFAULT '0',
  `tag` int(11) DEFAULT '0',
  `netType` int(11) DEFAULT '0',
  `groupId` int(11) DEFAULT '0',
  `unitId` int(11) DEFAULT '0',
  `area` varchar(128) DEFAULT NULL,
  `inTime` varchar(64) DEFAULT NULL,
  `inOperator` varchar(32) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_SERVER_LIST`
--

LOCK TABLES `TML_SERVER_LIST` WRITE;
/*!40000 ALTER TABLE `TML_SERVER_LIST` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_SERVER_LIST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_SPE`
--

DROP TABLE IF EXISTS `TML_SPE`;
CREATE TABLE `TML_SPE` (
  `tmlId` varchar(16) NOT NULL DEFAULT '',
  `speIp` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`tmlId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_SPE`
--

LOCK TABLES `TML_SPE` WRITE;
/*!40000 ALTER TABLE `TML_SPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_SPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_SYS_INFO`
--

DROP TABLE IF EXISTS `TML_SYS_INFO`;
CREATE TABLE `TML_SYS_INFO` (
  `tmlId` varchar(16) NOT NULL DEFAULT '',
  `tmlVersion` varchar(32) DEFAULT NULL,
  `leftDiskSize` varchar(32) DEFAULT NULL,
  `ableMem` varchar(32) DEFAULT NULL,
  `portalVersion` varchar(32) DEFAULT NULL,
  `portalUrl` varchar(64) DEFAULT NULL,
  `tmlPlayling` varchar(64) DEFAULT NULL,
  `tmlDownling` varchar(256) DEFAULT NULL,
  `tmlApps` varchar(256) DEFAULT NULL,
  `tmlSpelist` varchar(256) DEFAULT NULL,
  `updateDate` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`tmlId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_SYS_INFO`
--

LOCK TABLES `TML_SYS_INFO` WRITE;
/*!40000 ALTER TABLE `TML_SYS_INFO` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_SYS_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_UAD`
--

DROP TABLE IF EXISTS `TML_UAD`;
CREATE TABLE `TML_UAD` (
  `uadId` varchar(64) NOT NULL DEFAULT '',
  `listName` varchar(64) DEFAULT NULL,
  `inTime` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uadId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_UAD`
--

LOCK TABLES `TML_UAD` WRITE;
/*!40000 ALTER TABLE `TML_UAD` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_UAD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TML_UPGRADE_TEST`
--

DROP TABLE IF EXISTS `TML_UPGRADE_TEST`;
CREATE TABLE `TML_UPGRADE_TEST` (
  `tmlId` varchar(16) NOT NULL DEFAULT '',
  `upgradeIp` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`tmlId`,`upgradeIp`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TML_UPGRADE_TEST`
--

LOCK TABLES `TML_UPGRADE_TEST` WRITE;
/*!40000 ALTER TABLE `TML_UPGRADE_TEST` DISABLE KEYS */;
/*!40000 ALTER TABLE `TML_UPGRADE_TEST` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-12-06 10:32:21

CREATE TABLE DOM_CONF(
	dConfId         INTEGER NOT NULL AUTO_INCREMENT,
	DOMID			INTEGER,
	confItemId		INTEGER,
	IP				VARCHAR(50),
	Port			VARCHAR(50),
	PRIMARY KEY (dConfId)
);
CREATE TABLE CONF_ITEM(
	confItemId      INTEGER NOT NULL AUTO_INCREMENT,
	confName        VARCHAR(50),
	confDesc        VARCHAR(50),
	confType        INTEGER,
	confTag         INTEGER,
	IP              VARCHAR(50),
	Port            VARCHAR(50),
	PRIMARY KEY (confItemId)
);
ALTER TABLE TML_BASE ADD telNo VARCHAR(32);

INSERT INTO `CONF_ITEM` VALUES ('14', 'OLS-IP-PORT', 'OLS-IP-PORT', '1', '2004', '', '');
INSERT INTO `CONF_ITEM` VALUES ('2', 'spe-ip-port', 'spe-ip-port', '1', '2005', '192.168.101.29', '2121');
INSERT INTO `CONF_ITEM` VALUES ('3', 'STBPortal URL', 'STBPortal URL', '0', '134', 'http://192.168.101.30:8080/portalui/', '');
INSERT INTO `CONF_ITEM` VALUES ('17', 'App_FTP_URL', 'App_FTP_URL', '0', '136', 'ftp://dsp:portalpic@192.168.101.30:21', '');
INSERT INTO `CONF_ITEM` VALUES ('6', 'Upgrade_FTP_URL', 'Upgrade_FTP_URL', '0', '136', '192.168.101.29', '');
INSERT INTO `CONF_ITEM` VALUES ('19', 'PicServer_URL', 'PicServer_URL', '0', '138', 'http://210.75.225.42/portalpic/', '');
INSERT INTO `CONF_ITEM` VALUES ('8', 'detailURL', 'detailURL', '0', '139', '', '');
INSERT INTO `CONF_ITEM` VALUES ('9', 'LogServerFtp', 'LogServerFtp', '0', '149', '', '');
INSERT INTO `CONF_ITEM` VALUES ('10', 'gubei_index', 'gubei_index', '0', '251', '', '');
INSERT INTO `CONF_ITEM` VALUES ('11', 'CCGW-IP-Port', 'CCGW-IP-Port', '1', '2013', '', '');
INSERT INTO `CONF_ITEM` VALUES ('12', 'SessionServer-IP-Por', 'SessionServer-IP-Por', '1', '2014', '', '');
INSERT INTO `CONF_ITEM` VALUES ('18', 'AppPortal_URL', 'AppPortal_URL', '0', '135', 'http://210.75.225.238:8080/portalui/', '');
INSERT INTO `CONF_ITEM` VALUES ('15', 'SIS-IP-Port', 'SIS-IP-Port', '1', '2015', '192.168.101.30', '8080');
INSERT INTO `CONF_ITEM` VALUES ('22', 'spe-ip-port', '2005', '1', '2005', '', '');