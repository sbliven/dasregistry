-- MySQL dump 9.09
--
-- Host: localhost    Database: das_registry
-- ------------------------------------------------------
-- Server version	4.0.16-log

--
-- Table structure for table `coordinateSystems`
--

CREATE TABLE coordinateSystems (
  coord_auto mediumint(8) unsigned NOT NULL auto_increment,
  coordinateSystem varchar(255) default NULL,
  PRIMARY KEY  (coord_auto),
  UNIQUE KEY coordinateSystem (coordinateSystem)
) TYPE=MyISAM;

--
-- Table structure for table `registry`
--

CREATE TABLE registry (
  auto_id mediumint(8) unsigned NOT NULL auto_increment,
  url varchar(255) default NULL,
  adminemail varchar(255) default NULL,
  description text,
  sequence tinyint(1) unsigned default NULL,
  structure tinyint(1) unsigned default NULL,
  alignment tinyint(1) unsigned default NULL,
  types tinyint(1) unsigned default NULL,
  features tinyint(1) unsigned default NULL,
  entry_points tinyint(1) unsigned default NULL,
  error_segment tinyint(1) unsigned default NULL,
  unknown_segment tinyint(1) unsigned default NULL,
  unknown_feature tinyint(1) unsigned default NULL,
  feature_by_id tinyint(1) unsigned default NULL,
  component tinyint(1) unsigned default NULL,
  supercomponent tinyint(1) unsigned default NULL,
  dna tinyint(1) unsigned default NULL,
  stylesheet tinyint(1) unsigned default NULL,
  PRIMARY KEY  (auto_id),
  UNIQUE KEY url (url)
) TYPE=MyISAM;

--
-- Table structure for table `registry2coordinateSystem`
--

CREATE TABLE registry2coordinateSystem (
  r2c_auto mediumint(8) unsigned NOT NULL auto_increment,
  auto_id mediumint(8) unsigned NOT NULL default '0',
  coord_auto mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY  (r2c_auto)
) TYPE=MyISAM;

