/*
Navicat MySQL Data Transfer

Source Server         : Server
Source Server Version : 50540
Source Host           : 39.99.188.1:3306
Source Database       : db_dstorage

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2020-08-18 09:52:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `account` varchar(100) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `securePassword` varchar(50) DEFAULT NULL,
  `role` int(2) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_document
-- ----------------------------
DROP TABLE IF EXISTS `t_document`;
CREATE TABLE `t_document` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `folderName` varchar(50) DEFAULT NULL,
  `pid` int(5) DEFAULT NULL,
  `fatherName` varchar(50) DEFAULT NULL,
  `size` double(50,10) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_file_account` (`account`),
  CONSTRAINT `fk_file_account` FOREIGN KEY (`account`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_filesize
-- ----------------------------
DROP TABLE IF EXISTS `t_filesize`;
CREATE TABLE `t_filesize` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uploadSize` double(50,10) DEFAULT NULL,
  `downloadSize` double(50,10) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_filesize_account_account` (`account`),
  CONSTRAINT `fk_filesize_account_account` FOREIGN KEY (`account`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_friend
-- ----------------------------
DROP TABLE IF EXISTS `t_friend`;
CREATE TABLE `t_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `myAccount` varchar(100) DEFAULT NULL,
  `friendAccount` varchar(100) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_friend_account_account` (`myAccount`),
  CONSTRAINT `fk_friend_account_account` FOREIGN KEY (`myAccount`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `context` varchar(200) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `source` varchar(20) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_log_account_account` (`account`),
  CONSTRAINT `fk_log_account_account` FOREIGN KEY (`account`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_msg`;
CREATE TABLE `t_msg` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `context` varchar(200) DEFAULT NULL,
  `fromAccount` varchar(100) DEFAULT NULL,
  `toAccount` varchar(100) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_msg_account_account` (`toAccount`),
  CONSTRAINT `fk_msg_account_account` FOREIGN KEY (`toAccount`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_securefile
-- ----------------------------
DROP TABLE IF EXISTS `t_securefile`;
CREATE TABLE `t_securefile` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `folderName` varchar(50) DEFAULT NULL,
  `pid` int(5) DEFAULT NULL,
  `fatherName` varchar(50) DEFAULT NULL,
  `size` double(50,10) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_file_account` (`account`),
  CONSTRAINT `t_securefile_ibfk_1` FOREIGN KEY (`account`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_share
-- ----------------------------
DROP TABLE IF EXISTS `t_share`;
CREATE TABLE `t_share` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `fileLock` int(2) DEFAULT NULL,
  `lockPassword` varchar(50) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `size` double(50,10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_share_account_account` (`account`),
  CONSTRAINT `fk_share_account_account` FOREIGN KEY (`account`) REFERENCES `t_account` (`account`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
