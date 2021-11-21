CREATE DATABASE `soen387`;

CREATE TABLE `soen387`.`poll` (
  `poll_id` varchar(10) NOT NULL,
  `title` varchar(100) NOT NULL,
  `question` varchar(500) NOT NULL,
  `create_timestamp` timestamp NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `status` varchar(15) NOT NULL,
  `release_timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `soen387`.`choice` (
  `choice_id` int NOT NULL AUTO_INCREMENT,
  `poll_id` varchar(10) NOT NULL,
  `text` varchar(500) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `choice_number` int NOT NULL,
  PRIMARY KEY (`choice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `soen387`.`vote` (
  `vote_id` varchar(6) NOT NULL AUTO_INCREMENT COMMENT 'PIN',
  `poll_id` varchar(10) NOT NULL,
  `choice_number` int NOT NULL,
  `create_date` date NOT NULL,
  `create_time` time NOT NULL,
  PRIMARY KEY (`vote_id`,`poll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


