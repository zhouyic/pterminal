/*
Navicat MySQL Data Transfer

Source Server         : 192.168.101.128
Source Server Version : 50137
Source Host           : 192.168.101.128:3306
Source Database       : tml

Target Server Type    : MYSQL
Target Server Version : 50137
File Encoding         : 65001

Date: 2013-04-12 14:41:35
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `MULTI_TML_HOSTORY_INFO`
-- ----------------------------
DROP TABLE IF EXISTS `MULTI_TML_HOSTORY_INFO`;
CREATE TABLE `MULTI_TML_HOSTORY_INFO` (
  `mac` varchar(12) NOT NULL DEFAULT '',
  `userId` varchar(16) NOT NULL DEFAULT '',
  `ip` varchar(32) DEFAULT '',
  `type` int(4) DEFAULT '0',
  `status` int(4) DEFAULT '0',
  `onlineTime` datetime DEFAULT NULL,
  `offlineTime` datetime DEFAULT NULL,
  PRIMARY KEY (`mac`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of MULTI_TML_HOSTORY_INFO
-- ----------------------------

-- ----------------------------
-- Table structure for `MULTI_TML_INFO`
-- ----------------------------
DROP TABLE IF EXISTS `MULTI_TML_INFO`;
CREATE TABLE `MULTI_TML_INFO` (
  `mac` varchar(12) NOT NULL DEFAULT '',
  `userId` varchar(16) NOT NULL DEFAULT '',
  `ip` varchar(32) DEFAULT '',
  `type` int(4) DEFAULT '0',
  `status` int(4) DEFAULT '0',
  `onlineTime` datetime DEFAULT NULL,
  `offlineTime` datetime DEFAULT NULL,
  PRIMARY KEY (`mac`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of MULTI_TML_INFO
-- ----------------------------

-- ----------------------------
-- Table structure for `USER`
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `userId` varchar(16) NOT NULL DEFAULT '',
  `password` varchar(6) DEFAULT '',
  `type` int(4) DEFAULT NULL,
  `ip` varchar(32) DEFAULT '',
  `desc` varchar(64) DEFAULT '',
  `inTime` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of USER
-- ----------------------------
