DROP TABLE IF EXISTS `MULTI_TML_HISTORY_INFO`;
CREATE TABLE `MULTI_TML_HISTORY_INFO` (
  `mac` varchar(12) NOT NULL DEFAULT '',
  `userId` varchar(16) NOT NULL DEFAULT '',
  `ip` varchar(32) DEFAULT '',
  `type` int(4) DEFAULT '0',
  `status` int(4) DEFAULT '0',
  `onlineTime` datetime DEFAULT NULL,
  `offlineTime` datetime DEFAULT NULL,
  `desc` varchar(256) DEFAULT NULL,
  `appVersion` varchar(32) DEFAULT '',
  `stbVersion` varchar(32) DEFAULT '',
  `stbScreenSize` varchar(32) DEFAULT ''
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
