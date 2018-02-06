CREATE TABLE `qa_data_result` (
  `key` varchar(45) NOT NULL,
  `report_date` date DEFAULT NULL,
  `game_code` varchar(45) DEFAULT NULL,
  `game_name` varchar(45) DEFAULT NULL,
  `kpi_id` int(11) DEFAULT NULL,
  `other_source` varchar(45) DEFAULT NULL,
  `tool_source` varchar(45) DEFAULT NULL,
  `diff` float DEFAULT NULL,
  PRIMARY KEY (`key`),
  KEY `report_date` (`report_date`),
  KEY `game_code` (`game_code`),
  KEY `kpi_id` (`kpi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `domain_name` varchar(45) DEFAULT NULL,
  `create_date` varchar(45) DEFAULT NULL,
  `permission` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `domain_name` (`domain_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `game_config` (
  `key` varchar(45) NOT NULL,
  `game_code` varchar(45) DEFAULT NULL,
  `game_name` varchar(45) DEFAULT NULL,
  `kpi_id` int,
  `desc` text,
  PRIMARY KEY (`key`),
  KEY `game_code` (`game_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8