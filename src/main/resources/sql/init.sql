-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `enjoytrip` ;

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`member` (
                                                    `userId` VARCHAR(30) NOT NULL,
                                                    `userName` VARCHAR(20) NOT NULL,
                                                    `userPass` VARCHAR(200) NOT NULL,
                                                    `userEmail` VARCHAR(50) NOT NULL,
                                                    `joinDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                    `salt` VARCHAR(200) NOT NULL,
                                                    PRIMARY KEY (`userId`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board` (
                                                   `articleNo` INT NOT NULL AUTO_INCREMENT,
                                                   `userId` VARCHAR(16) NULL DEFAULT NULL,
                                                   `subject` VARCHAR(400) NULL DEFAULT NULL,
                                                   `content` VARCHAR(2000) NULL DEFAULT NULL,
                                                   `category` VARCHAR(20) NULL DEFAULT '기타',
                                                   `hashtags` varchar(100) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
                                                   `hit` INT NULL DEFAULT '0',
                                                   `registerTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                   PRIMARY KEY (`articleNo`),
                                                   CONSTRAINT `board_to_members_user_id_fk`
                                                       FOREIGN KEY (`userId`)
                                                           REFERENCES `enjoytrip`.`member` (`userId`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 101
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;

CREATE INDEX `board_to_members_user_id_fk` ON `enjoytrip`.`board` (`userId` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `enjoytrip`.`tripplan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`tripplan` (
                                                      `planId` INT NOT NULL AUTO_INCREMENT,
                                                      `planName` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_as_cs' NOT NULL,
                                                      `hashtags` varchar(100) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
                                                      `planCreatedAt` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                      PRIMARY KEY (`planId`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`tripcourse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`tripcourse` (
                                                        `courseId` INT NOT NULL AUTO_INCREMENT,
                                                        `planId` INT NOT NULL,
                                                        `userId` VARCHAR(30) NOT NULL,
                                                        `addressName` VARCHAR(45) NULL,
                                                        `categoryGroupName` VARCHAR(45) NULL,
                                                        `placeName` VARCHAR(100) NULL,
                                                        `placeUrl` VARCHAR(200) NULL,
                                                        `roadAddressName` VARCHAR(200) NULL,
                                                        `x` VARCHAR(100) NULL,
                                                        `y` VARCHAR(100) NULL,
                                                        PRIMARY KEY (`courseId`),
                                                        CONSTRAINT `fk_tripcourse_member1`
                                                            FOREIGN KEY (`userId`)
                                                                REFERENCES `enjoytrip`.`member` (`userId`)
                                                                ON DELETE NO ACTION
                                                                ON UPDATE NO ACTION,
                                                        CONSTRAINT `fk_tripcourse_tripplan1`
                                                            FOREIGN KEY (`planId`)
                                                                REFERENCES `enjoytrip`.`tripplan` (`planId`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE CASCADE )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;

CREATE INDEX `fk_tripcourse_member1_idx` ON `enjoytrip`.`tripcourse` (`userId` ASC) VISIBLE;
CREATE INDEX `fk_tripcourse_tripplan1_idx` ON `enjoytrip`.`tripcourse` (`planId` ASC) VISIBLE;

CREATE TABLE `notification` (
                                `notificationId` int NOT NULL AUTO_INCREMENT,
                                `fromId` varchar(30) COLLATE utf8mb4_0900_as_cs NOT NULL,
                                `toId` varchar(30) COLLATE utf8mb4_0900_as_cs NOT NULL,
                                `planId` int NOT NULL,
                                `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (`notificationId`),
                                KEY `fk_notification_member1_idx` (`fromId`),
                                KEY `fk_notification_member2_idx` (`toId`),
                                KEY `fk_notification_tripplan1_idx` (`planId`),
                                CONSTRAINT `fk_notification_member1` FOREIGN KEY (`fromId`) REFERENCES `member` (`userId`),
                                CONSTRAINT `fk_notification_member2` FOREIGN KEY (`toId`) REFERENCES `member` (`userId`),
                                CONSTRAINT `fk_notification_tripplan1` FOREIGN KEY (`planId`) REFERENCES `tripplan` (`planId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
