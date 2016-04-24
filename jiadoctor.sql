/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.28 : Database - jiadoctor
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jiadoctor` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jiadoctor`;

/*Table structure for table `column_info` */

DROP TABLE IF EXISTS `column_info`;

CREATE TABLE `column_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '栏目主键ID',
  `NAME` varchar(200) DEFAULT NULL COMMENT '栏目名称',
  `SHORT_NAME` varchar(200) DEFAULT NULL COMMENT '栏目简称',
  `IS_SHOW_IN_NAV` varchar(1) DEFAULT NULL COMMENT '是否在导航栏显示',
  `COLUMN_KEY` varchar(20) DEFAULT NULL COMMENT '栏目的key值',
  `SERIAL` varchar(10) DEFAULT NULL COMMENT '栏目序号',
  `TEMPLATE` int(11) DEFAULT NULL COMMENT '模板类型',
  `CONTENT` longtext COMMENT '内容',
  `LINK` varchar(200) DEFAULT NULL COMMENT '栏目链接地址',
  `PARENT` int(11) DEFAULT NULL COMMENT '父级栏目',
  `STATE` varchar(1) DEFAULT NULL COMMENT '栏目状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `column_info` */

insert  into `column_info`(`ID`,`NAME`,`SHORT_NAME`,`IS_SHOW_IN_NAV`,`COLUMN_KEY`,`SERIAL`,`TEMPLATE`,`CONTENT`,`LINK`,`PARENT`,`STATE`) values (1,'首页','Home','1','INDEX_HEME','1',2,NULL,'',0,'1');

/*Table structure for table `column_template` */

DROP TABLE IF EXISTS `column_template`;

CREATE TABLE `column_template` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEATURES_URL` varchar(200) DEFAULT NULL,
  `INTRO` varchar(500) DEFAULT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `STATE` varchar(1) DEFAULT NULL,
  `TEMPLATE_TYPE` varchar(40) DEFAULT NULL,
  `TEMPLATE_URL` varchar(200) DEFAULT NULL,
  `URL_TYPE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `column_template` */

insert  into `column_template`(`ID`,`FEATURES_URL`,`INTRO`,`NAME`,`STATE`,`TEMPLATE_TYPE`,`TEMPLATE_URL`,`URL_TYPE`) values (1,NULL,'','单页','1','SINGLE',NULL,NULL),(2,NULL,'','新闻列表','1','NEWLIST',NULL,NULL),(3,NULL,'','外链','1','OTHER',NULL,NULL);

/*Table structure for table `common_info` */

DROP TABLE IF EXISTS `common_info`;

CREATE TABLE `common_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `COMPANY` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `COMPANY_EN` varchar(20) DEFAULT NULL COMMENT '公司英文简称',
  `COMPANY_ADDR` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `COMPANY_PHONE` varchar(20) DEFAULT NULL COMMENT '公司电话',
  `COMPANY_EMAIL` varchar(100) DEFAULT NULL COMMENT '公司E-MAIL',
  `COMPANY_LOG` varchar(100) DEFAULT NULL COMMENT '是否在导航栏显示',
  `COMPANY_BACKGROUND` varchar(100) DEFAULT NULL COMMENT '栏目的key值',
  `STATUS` varchar(1) DEFAULT NULL COMMENT '栏目状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `common_info` */

/*Table structure for table `jyb_authorities` */

DROP TABLE IF EXISTS `jyb_authorities`;

CREATE TABLE `jyb_authorities` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AUTHORITIES_ID` bigint(20) DEFAULT NULL,
  `AUTHORITIES_TYPE` varchar(2) DEFAULT NULL,
  `DISPLAY_NAME` bigint(20) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyb_authorities` */

/*Table structure for table `jyb_loginrecord` */

DROP TABLE IF EXISTS `jyb_loginrecord`;

CREATE TABLE `jyb_loginrecord` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGINTIME` datetime DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_3wbb1qk84nc9456tym9ysgybt` (`USER_ID`),
  CONSTRAINT `FK_3wbb1qk84nc9456tym9ysgybt` FOREIGN KEY (`USER_ID`) REFERENCES `jyd_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyb_loginrecord` */

/*Table structure for table `jyb_roles` */

DROP TABLE IF EXISTS `jyb_roles`;

CREATE TABLE `jyb_roles` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyb_roles` */

/*Table structure for table `jyb_rolesauthorities` */

DROP TABLE IF EXISTS `jyb_rolesauthorities`;

CREATE TABLE `jyb_rolesauthorities` (
  `ROLE_ID` bigint(20) NOT NULL,
  `AUTHORITIES_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`AUTHORITIES_ID`,`ROLE_ID`),
  KEY `FK_ge0bqxt4nxvqiyon2pldgqmst` (`ROLE_ID`),
  CONSTRAINT `FK_af4exqjwaoaw63wtwlq3tw2ox` FOREIGN KEY (`AUTHORITIES_ID`) REFERENCES `jyb_authorities` (`ID`),
  CONSTRAINT `FK_ge0bqxt4nxvqiyon2pldgqmst` FOREIGN KEY (`ROLE_ID`) REFERENCES `jyb_roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyb_rolesauthorities` */

/*Table structure for table `jyb_user` */

DROP TABLE IF EXISTS `jyb_user`;

CREATE TABLE `jyb_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(20) DEFAULT NULL,
  `ADDRESS` varchar(300) DEFAULT NULL,
  `AREA` varchar(300) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `CARDNUM` varchar(100) DEFAULT NULL,
  `CARDTYPE` varchar(2) DEFAULT NULL,
  `ISBLOCK` varchar(2) DEFAULT NULL,
  `LASTVISITTIME` datetime DEFAULT NULL,
  `LOGINNUMS` int(11) DEFAULT NULL,
  `MOBILE` varchar(20) DEFAULT NULL,
  `NICKNAME` varchar(40) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `REALNAME` varchar(100) DEFAULT NULL,
  `REGISFROM` varchar(2) DEFAULT NULL,
  `REGISTIME` datetime DEFAULT NULL,
  `SEX` varchar(1) DEFAULT NULL,
  `STATUS` varchar(2) DEFAULT NULL,
  `USERHEAD` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `jyb_user` */

insert  into `jyb_user`(`ID`,`ACCOUNT`,`ADDRESS`,`AREA`,`BIRTHDAY`,`CARDNUM`,`CARDTYPE`,`ISBLOCK`,`LASTVISITTIME`,`LOGINNUMS`,`MOBILE`,`NICKNAME`,`PASSWORD`,`REALNAME`,`REGISFROM`,`REGISTIME`,`SEX`,`STATUS`,`USERHEAD`) values (8,'fdsfafdfd','wwww',NULL,NULL,'111111111111111','2','F',NULL,NULL,'1313','ace','33532952794425cd3ef2680875451c73b96c77c34dc3bc31','ace','2','2016-04-13 23:38:11','F','0',NULL),(9,'ace',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','fdsf','15b79757de82c89b9631421428103c049191f0141894d65f','',NULL,'2016-04-13 23:38:29','M','1',NULL),(10,'3333',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','f58d3d77f98cf69a71f44c6a46f67cc6e32785b66e81e92a','',NULL,'2016-04-13 23:42:57','M','1',NULL),(11,'555',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','81253c47653189df2a399e0567d658b4ff8f647f4f09a623','',NULL,'2016-04-13 23:43:08','M','1',NULL),(12,'666',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','67b48ba6789184a54c849460978182e9816aa9347e139800','',NULL,'2016-04-13 23:43:15','M','1',NULL),(13,'777',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','d5025e21957e867790489154657f72243688640a28979d8a','',NULL,'2016-04-13 23:43:22','M','1',NULL),(16,'qqqqq',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','83077d16f640c90847e84a52b70d8357413bc6450c523f7e','',NULL,'2016-04-17 00:04:06','M','1',NULL),(17,'wwww',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','25910ce8001412ab2456bd1256d80ef5b67a778c69937370','',NULL,'2016-04-17 00:04:18','M','1',NULL),(18,'eeeee',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','56f60d22ad93f3254c40cb1271ff89f05d0910656587c60b','',NULL,'2016-04-17 00:04:29','M','1',NULL),(19,'rrrr',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'','','f98456679b5f10d210116c3693539b173268e06e7cc24450','',NULL,'2016-04-17 00:04:40','M','1',NULL);

/*Table structure for table `jyd_authorities` */

DROP TABLE IF EXISTS `jyd_authorities`;

CREATE TABLE `jyd_authorities` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `AUTHORITIES_ID` bigint(20) DEFAULT NULL,
  `AUTHORITIES_TYPE` varchar(2) DEFAULT NULL,
  `DISPLAY_NAME` bigint(20) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyd_authorities` */

/*Table structure for table `jyd_loginrecord` */

DROP TABLE IF EXISTS `jyd_loginrecord`;

CREATE TABLE `jyd_loginrecord` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGINTIME` datetime DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_27dhi7991ahp35vc6w4p1lcyg` (`USER_ID`),
  CONSTRAINT `FK_27dhi7991ahp35vc6w4p1lcyg` FOREIGN KEY (`USER_ID`) REFERENCES `jyd_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyd_loginrecord` */

/*Table structure for table `jyd_roles` */

DROP TABLE IF EXISTS `jyd_roles`;

CREATE TABLE `jyd_roles` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `STATE` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyd_roles` */

/*Table structure for table `jyd_rolesauthorities` */

DROP TABLE IF EXISTS `jyd_rolesauthorities`;

CREATE TABLE `jyd_rolesauthorities` (
  `ROLE_ID` bigint(20) NOT NULL,
  `AUTHORITIES_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`AUTHORITIES_ID`,`ROLE_ID`),
  KEY `FK_kn8oyyt4u3mn5ahb1rxerbufm` (`ROLE_ID`),
  CONSTRAINT `FK_gjiepah5wbwom2ym8gg9aeiwh` FOREIGN KEY (`AUTHORITIES_ID`) REFERENCES `jyd_authorities` (`ID`),
  CONSTRAINT `FK_kn8oyyt4u3mn5ahb1rxerbufm` FOREIGN KEY (`ROLE_ID`) REFERENCES `jyd_roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyd_rolesauthorities` */

/*Table structure for table `jyd_user` */

DROP TABLE IF EXISTS `jyd_user`;

CREATE TABLE `jyd_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(20) DEFAULT NULL,
  `ADDRESS` varchar(300) DEFAULT NULL,
  `AREA` varchar(300) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `CARDNUM` varchar(2) DEFAULT NULL,
  `CARDTYPE` varchar(2) DEFAULT NULL,
  `ISBLOCK` varchar(2) DEFAULT NULL,
  `LASTVISITTIME` datetime DEFAULT NULL,
  `LOGINNUMS` int(11) DEFAULT NULL,
  `MOBILE` varchar(20) DEFAULT NULL,
  `NICKNAME` varchar(40) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `REALNAME` varchar(100) DEFAULT NULL,
  `REGISFROM` varchar(2) DEFAULT NULL,
  `REGISTIME` datetime DEFAULT NULL,
  `SEX` varchar(1) DEFAULT NULL,
  `STATUS` varchar(2) DEFAULT NULL,
  `USERHEAD` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `jyd_user` */

insert  into `jyd_user`(`ID`,`ACCOUNT`,`ADDRESS`,`AREA`,`BIRTHDAY`,`CARDNUM`,`CARDTYPE`,`ISBLOCK`,`LASTVISITTIME`,`LOGINNUMS`,`MOBILE`,`NICKNAME`,`PASSWORD`,`REALNAME`,`REGISFROM`,`REGISTIME`,`SEX`,`STATUS`,`USERHEAD`) values (1,'123','',NULL,NULL,'','1','Y',NULL,NULL,'123','123','32b96d33336098fe6fd72442880714b69b43618e5a73a332','123','1','2016-04-24 13:55:25','F','1',NULL),(2,'d\'f\'d','',NULL,'2016-04-09 00:00:00','11','1','Y',NULL,NULL,'','123','68742545e94371c444c19d56265a0c577c8008e71590af62','123','1','2016-04-24 15:01:47','M','1',NULL);

/*Table structure for table `jyd_userroles` */

DROP TABLE IF EXISTS `jyd_userroles`;

CREATE TABLE `jyd_userroles` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLES_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLES_ID`),
  KEY `FK_mjihresoh3q0d1wlfrb8lempp` (`ROLES_ID`),
  CONSTRAINT `FK_dutax4n16nyu0b9cqo6afg165` FOREIGN KEY (`USER_ID`) REFERENCES `jyd_user` (`ID`),
  CONSTRAINT `FK_mjihresoh3q0d1wlfrb8lempp` FOREIGN KEY (`ROLES_ID`) REFERENCES `jyb_roles` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jyd_userroles` */

/*Table structure for table `login_record` */

DROP TABLE IF EXISTS `login_record`;

CREATE TABLE `login_record` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGINTIME` datetime DEFAULT NULL,
  `Member_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `login_record` */

insert  into `login_record`(`ID`,`LOGINTIME`,`Member_ID`) values (1,'2016-03-03 00:00:00',1),(2,'2016-03-03 00:00:00',1),(3,'2016-03-03 00:00:00',1),(4,'2016-03-03 00:00:00',1),(5,'2016-03-03 00:00:00',1),(6,'2016-03-03 00:00:00',1),(7,'2016-03-03 00:00:00',1),(8,'2016-03-03 00:00:00',1),(9,'2016-03-03 00:00:00',1),(10,'2016-03-03 00:00:00',1),(11,'2016-03-03 00:00:00',1),(12,'2016-03-03 00:00:00',1),(13,'2016-03-03 00:00:00',1),(14,'2016-03-03 00:00:00',1),(15,'2016-03-03 00:00:00',1),(16,'2016-03-03 00:00:00',1),(17,'2016-03-03 00:00:00',1),(18,'2016-03-03 00:00:00',1),(19,'2016-03-03 00:00:00',1),(20,'2016-03-03 00:00:00',1);

/*Table structure for table `loginrecord` */

DROP TABLE IF EXISTS `loginrecord`;

CREATE TABLE `loginrecord` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'UNIQUE',
  `Member_ID` int(10) DEFAULT NULL COMMENT '用户ID  FK: NT_USER',
  `LOGINTIME` datetime DEFAULT NULL COMMENT '登陆时间',
  `TYPE` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '登陆方式 ‘1’= APP ；',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `loginrecord` */

insert  into `loginrecord`(`ID`,`Member_ID`,`LOGINTIME`,`TYPE`) values (1,1,'2016-03-04 00:00:00','1'),(2,1,'2016-03-04 00:00:00','1'),(3,1,'2016-03-04 00:00:00','1'),(4,1,'2016-03-04 00:00:00','1'),(5,1,'2016-03-04 00:00:00','2'),(6,1,'2016-03-04 00:00:00','2'),(7,1,'2016-03-04 00:00:00','2'),(8,1,'2016-03-04 00:00:00','2'),(9,1,'2016-03-04 00:00:00','2'),(10,1,'2016-03-04 00:00:00','2'),(11,1,'2016-03-04 00:00:00','1'),(12,1,'2016-03-04 00:00:00','1'),(13,1,'2016-03-04 00:00:00','3'),(14,1,'2016-03-04 00:00:00','1'),(15,1,'2016-03-04 00:00:00','2'),(16,1,'2016-03-04 00:00:00','1'),(17,1,'2016-03-04 00:00:00','2'),(18,1,'2016-03-04 00:00:00','1'),(19,1,'2016-03-04 00:00:00','2'),(20,1,'2016-03-04 00:00:00','1'),(21,NULL,'2016-03-04 00:00:00',NULL);

/*Table structure for table `mc_user` */

DROP TABLE IF EXISTS `mc_user`;

CREATE TABLE `mc_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `USERNAME` varchar(50) NOT NULL COMMENT '账号（登录名）',
  `PASSWORD` varchar(50) NOT NULL COMMENT '密码',
  `NICK_NAME` varchar(20) NOT NULL COMMENT '昵称',
  `MOBILE_PHONE` varchar(20) NOT NULL COMMENT '手机号码',
  `REAL_NAME` varchar(20) DEFAULT NULL COMMENT '姓名',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `LOGIN_COUNT` int(20) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `LAST_LOGIN` datetime DEFAULT NULL COMMENT '最后登录时间',
  `EDITOR` int(11) DEFAULT NULL COMMENT '编辑人',
  `EDIT_TIME` datetime DEFAULT NULL COMMENT '编辑时间',
  `STATUS` varchar(1) NOT NULL COMMENT '状态',
  `editTime` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `EDITTOR` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_6ulcvy6x033cnliks6ug3hrmo` (`EDITOR`),
  CONSTRAINT `FK_6ulcvy6x033cnliks6ug3hrmo` FOREIGN KEY (`EDITOR`) REFERENCES `mc_user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `mc_user` */

insert  into `mc_user`(`ID`,`USERNAME`,`PASSWORD`,`NICK_NAME`,`MOBILE_PHONE`,`REAL_NAME`,`EMAIL`,`LOGIN_COUNT`,`LAST_LOGIN`,`EDITOR`,`EDIT_TIME`,`STATUS`,`editTime`,`lastLogin`,`EDITTOR`) values (1,'admin','b34f94d04c54c3d056156c9407f46e16bb5fc1581ef6f488','admin','13800138000',NULL,NULL,72,'2016-04-24 16:22:11',1,'2016-03-04 12:05:10','1',NULL,NULL,NULL);

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOGINTIME` datetime DEFAULT NULL,
  `TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `ADDRESS` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `EMAIL` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `HEAD_PORTRAIT` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `NICKNAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `POSTCODE` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `QQ` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `REAL_NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `REGISTER_WAY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `SEX` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `USERNAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `Member_ID` bigint(20) DEFAULT NULL,
  `AREA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `IS_BLOCK` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LAST_VISIT_TIME` datetime DEFAULT NULL,
  `LOGIN_NUMS` int(11) DEFAULT NULL,
  `REGIS_FROM` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REGIS_TIME` datetime DEFAULT NULL,
  `USER_HEAD` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `VIEW1` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `VIEW2` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `VIEW3` int(11) DEFAULT NULL,
  `VIEW4` double DEFAULT NULL,
  `VIEW5` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_shc2jken8edktqkgftnq0ymad` (`USER_ID`),
  KEY `FK_h9o67bei3nmy356n327pmm9ok` (`Member_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `member` */

/*Table structure for table `member_questions` */

DROP TABLE IF EXISTS `member_questions`;

CREATE TABLE `member_questions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `QUESTION1` varchar(50) DEFAULT NULL COMMENT '问题一',
  `ANSWER1` varchar(50) DEFAULT NULL COMMENT '回答一',
  `QUESTION2` varchar(50) DEFAULT NULL COMMENT '问题二',
  `ANSWER2` varchar(50) DEFAULT NULL COMMENT '回答二',
  `MEMBERID` int(11) DEFAULT NULL COMMENT '关联的会员ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `member_questions` */

insert  into `member_questions`(`ID`,`QUESTION1`,`ANSWER1`,`QUESTION2`,`ANSWER2`,`MEMBERID`) values (1,'aaa','aaa','aaa','aaa',3),(2,'1','aaa','000000','aaa',3),(3,'1','aaa','000000','aaa',3),(4,'111','e','jjj','ddd',3);

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COLUMN_ID` int(11) DEFAULT NULL COMMENT '栏目ID',
  `EDITE_USERS` int(11) DEFAULT NULL COMMENT '编辑人',
  `TITLE` varchar(200) DEFAULT NULL COMMENT '新闻题目',
  `PRI_KEY` varchar(500) DEFAULT NULL COMMENT '关键字',
  `SHORT_META` varchar(500) DEFAULT NULL COMMENT '简介',
  `AUTOR` varchar(20) DEFAULT NULL COMMENT '作者',
  `SOURCE` varchar(50) DEFAULT NULL COMMENT '来源',
  `CONTENTS` longtext COMMENT '内容',
  `CLICK_COUNT` int(11) DEFAULT NULL COMMENT '浏览次数',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `EDITE_TIME` datetime DEFAULT NULL COMMENT '编辑时间',
  `IS_TOP` varchar(1) DEFAULT NULL COMMENT '顶置标志',
  `IS_COMMEND` varchar(1) DEFAULT NULL COMMENT '推荐标志',
  `IS_SHOWED_ON_INDEX` varchar(1) DEFAULT NULL COMMENT '首页滚动显示标志',
  `IS_ONLY_CONTENT` int(1) DEFAULT '0' COMMENT '单独内容样式标志',
  `NEWS_TYPE` varchar(1) DEFAULT NULL COMMENT '新闻类型',
  `OTHER_URL` varchar(100) DEFAULT '(NULL)' COMMENT '链接',
  `PHOTO_FILE_NAME` varchar(50) DEFAULT NULL COMMENT '图片文件名称',
  `FILE_NAME` varchar(255) DEFAULT NULL COMMENT '上传文件名称',
  `FILE_SIZE` int(11) DEFAULT NULL COMMENT '上传文件大小',
  `STATE` varchar(1) DEFAULT NULL,
  `NAME` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `news` */

/*Table structure for table `news_comment` */

DROP TABLE IF EXISTS `news_comment`;

CREATE TABLE `news_comment` (
  `COM_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` bigint(20) DEFAULT NULL,
  `COMMENT` varchar(255) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `NEWS_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `COM_PARENT_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`COM_ID`),
  KEY `FK_rlkumt6liwafjiv8ih3cn9ogw` (`COM_PARENT_ID`),
  CONSTRAINT `FK_rlkumt6liwafjiv8ih3cn9ogw` FOREIGN KEY (`COM_PARENT_ID`) REFERENCES `news_comment` (`COM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `news_comment` */

/*Table structure for table `news_like` */

DROP TABLE IF EXISTS `news_like`;

CREATE TABLE `news_like` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACC_ID` bigint(20) DEFAULT NULL,
  `NEWS_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `news_like` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PERMISSION_ID` int(11) NOT NULL,
  `PERMISSION_TYPE` varchar(1) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(100) NOT NULL,
  `URI` varchar(100) DEFAULT NULL,
  `DISPLAY_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`ID`,`PERMISSION_ID`,`PERMISSION_TYPE`,`NAME`,`DESCRIPTION`,`URI`,`DISPLAY_NAME`) values (1,0,'0','BASE_MANAGE','基本配置',NULL,NULL),(2,1,'0','BASE_SITE_SETTING','站点配置','site',NULL),(3,1,'0','BASE_COMMON_SETTING','公告信息管理','commonManagement',NULL),(4,0,'0','BASE_COLUMN_SETTING','栏目配置','',NULL),(5,0,'0','BASE_SYSTEM_SETTING','系统配置','',NULL),(6,0,'0','BASE_MCGT','MCGT','mcgt',NULL),(7,4,'0','COLUMN_MANAGE','栏目管理','columnConfigTotal',NULL),(8,5,'0','SYSTEM_USER_MANAGE','用户管理','user_management',NULL),(9,5,'0','SYSTEM_ROLE_MANAGE','角色管理','role_management',NULL),(10,5,'0','SYSTEM_PERMISSION_MANAGE','权限管理','permission_management',NULL),(11,0,'1','INDEX_HEME','首页','',NULL);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(100) NOT NULL,
  `STATUS` varchar(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`ID`,`NAME`,`DESCRIPTION`,`STATUS`) values (1,'admin','超级管理员','1');

/*Table structure for table `roles_permissions` */

DROP TABLE IF EXISTS `roles_permissions`;

CREATE TABLE `roles_permissions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `permission_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_mxv35w1cdxnicl4xqd1449jms` (`permission_ID`),
  KEY `FK_lre3bl6c1isv4jwhkbb6umqfr` (`ROLE_ID`),
  CONSTRAINT `FK_lre3bl6c1isv4jwhkbb6umqfr` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_mxv35w1cdxnicl4xqd1449jms` FOREIGN KEY (`permission_ID`) REFERENCES `permission` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `roles_permissions` */

insert  into `roles_permissions`(`ID`,`ROLE_ID`,`permission_ID`) values (1,1,5),(2,1,9),(3,1,6),(4,1,4),(5,1,1),(6,1,2),(7,1,8),(8,1,7),(9,1,3),(10,1,10);

/*Table structure for table `users_roles` */

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_777tey2suldskv0nuy69r2q54` (`ROLE_ID`),
  KEY `FK_vt6s6gnj0o1ocigu9mwf0x9d` (`USER_ID`),
  CONSTRAINT `FK_777tey2suldskv0nuy69r2q54` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `FK_vt6s6gnj0o1ocigu9mwf0x9d` FOREIGN KEY (`USER_ID`) REFERENCES `mc_user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `users_roles` */

insert  into `users_roles`(`ID`,`USER_ID`,`ROLE_ID`) values (1,1,1);

/*Table structure for table `web_site` */

DROP TABLE IF EXISTS `web_site`;

CREATE TABLE `web_site` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SITE_NAME` varchar(200) DEFAULT NULL COMMENT '站点名称',
  `SITE_TITLE` varchar(200) DEFAULT NULL COMMENT '站点题目',
  `SITE_KEYWORD` varchar(20) NOT NULL COMMENT '网站关键字',
  `SITE_DOMAIN` varchar(200) DEFAULT NULL COMMENT '站点域名',
  `SITE_PORT` varchar(64) DEFAULT NULL COMMENT '站点端口',
  `SITE_COPYRIGHT` varchar(8000) DEFAULT NULL COMMENT '网站版权',
  `SITE_RECODE` varchar(200) DEFAULT NULL COMMENT '版权后面的那串码',
  `SITE_LOGO` varchar(200) DEFAULT NULL COMMENT '网站logo',
  `SITE_DESCRIBE` varchar(255) DEFAULT NULL COMMENT '网站描述',
  `SITE_STATUS` varchar(1) DEFAULT NULL COMMENT '状态/0无效，1有效',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `web_site` */

insert  into `web_site`(`ID`,`SITE_NAME`,`SITE_TITLE`,`SITE_KEYWORD`,`SITE_DOMAIN`,`SITE_PORT`,`SITE_COPYRIGHT`,`SITE_RECODE`,`SITE_LOGO`,`SITE_DESCRIBE`,`SITE_STATUS`) values (1,'养乐堂','养乐堂','养乐堂,活动','http://www.ys69.cn','80','粤ICP备15041127号','2008 - 2013 YANG LE TANG COMPANY.ALL RIGHTS RESERVED','logo.png','.......','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
