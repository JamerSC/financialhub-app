#CREATE DATABASE `test_financial_hub_db`;
#CREATE DATABASE IF NOT EXISTS `test_financial_hub_db`;
#USE `test_financial_hub_db`;

DROP TABLE `users`;
CREATE TABLE `users` (
	`id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `middle_name` varchar(255) NULL,
    `email` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL UNIQUE,
    `password` char(80) NOT NULL,
    `enabled` boolean NOT NULL,
    `created_by` int,
    `created_at` timestamp default current_timestamp,
	`updated_by` int,
    `updated_at` timestamp default current_timestamp on update current_timestamp,
    PRIMARY KEY (`id`)
);

DROP TABLE `roles`;
CREATE TABLE roles (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

-- Create users_roles table (join table for many-to-many relationship)
DROP TABLE `users_roles`;
CREATE TABLE users_roles (
	`user_id` int NOT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- sample data

-- Insert roles
INSERT INTO `roles` (`name`) VALUES ('ROLE_EMPLOYEE');
INSERT INTO `roles` (`name`) VALUES ('ROLE_MANAGER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');

-- Insert users (passwords should be encoded using BCrypt, below are plaintext for demonstration)
-- password: 123123 {bcrypt}$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq
INSERT INTO `users` (`first_name`, `last_name`, `middle_name`, `email`, `username`, `password`, `enabled`, `created_by`, `updated_by`) 
VALUES
('John', 'Doe', 'Eod', 'john@mail.com', 'john', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3),
('Mary', 'Public', 'Private', 'mary@mail.com', 'mary', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3),
('Susan', 'Sun Tzu', 'Yinyang', 'susan@mail.com','susan', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3),
('Eroll', 'Villaraiz', 'Divinaflor', 'huwan17@mail.com', 'huwan', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3);

-- Assign roles to users
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1); -- Employee
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1); -- Employee
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2); -- Manager
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1); -- Employee
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2); -- Manager
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3); -- Admin
INSERT INTO users_roles (user_id, role_id) VALUES (4, 1); -- Employee
#INSERT INTO users_roles (user_id, role_id) VALUES (10, 1); -- Employee username test5 pass 123123

#TRUNCATE TABLE `users`;
#TRUNCATE TABLE `roles`;
#TRUNCATE TABLE `users_roles`;

SELECT 
    u.id AS user_id,
    u.first_name,
    u.last_name,
    u.middle_name,
    u.email,
    u.username,
    r.id AS role_id,
    r.name AS role_name
FROM 
    users u
JOIN 
    users_roles ur ON u.id = ur.user_id
JOIN 
    roles r ON ur.role_id = r.id;

DROP TABLE `petty_cash_vouchers`;
CREATE TABLE `petty_cash_vouchers` (
	`id` int NOT NULL AUTO_INCREMENT,
    `pcv_number` varchar(50) NOT NULL,
    `received_by` varchar(255) NOT NULL,
    `date` date NOT NULL,
    `particulars` varchar(255) NOT NULL,
    `total_amount` decimal(10, 2) NOT NULL,
    `approved_by` varchar(255) NOT NULL,
    `created_by` int NULL,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int NULL,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

# DATE - format: YYYY-MM-DD.
INSERT INTO `petty_cash_vouchers`(`pcv_number`, `received_by`, `date`, `particulars`, `total_amount`, `approved_by`, `created_by`, `updated_by`)
VALUE ('PCV-2024001', 'John Doe', '2024-07-21', 'Utility expense', 1000, 'Susan Swan', 3, 3);

DROP TABLE `petty_cash_liquidation`;
CREATE TABLE `petty_cash_liquidation` (
	`id` int NOT NULL AUTO_INCREMENT,
    `pcvoucher_id` int NOT NULL,
	`date` date NOT NULL,
    `item_description` varchar(255) NOT NULL,
    `amount` DECIMAL(10, 2) NOT NULL,
    `created_by` int NULL,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int NULL,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`pcvoucher_id`) REFERENCES `petty_cash_vouchers`(id) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);

SELECT *
FROM `petty_cash_vouchers`
LEFT JOIN `petty_cash_liquidation`
ON `petty_cash_vouchers`.`id` = `petty_cash_liquidation`.`pcvoucher_id`;

INSERT INTO `petty_cash_liquidation`(`pcvoucher_id`, `date`, `item_description`, `amount`, `created_by`, `updated_by`)
VALUES (3, '2024-07-21','Gas Fee', 50, 1, 1), (3, '2024-07-21','Certification', 30, 1, 1), (3, '2024-07-21','Photo copy', 20, 1, 1);

DROP TABLE `check_vouchers`;
CREATE TABLE `check_vouchers` (
	`id` int NOT NULL AUTO_INCREMENT,
    `cv_number` varchar(50) NOT NULL,
    `payee_name` varchar(255) NOT NULL,
	`date` date NOT NULL,
    `total_amount` decimal(10, 2) NOT NULL,
    `amount_in_words` varchar(255) NOT NULL,
    `bank` varchar(255) NOT NULL,
    `check_number` varchar(50) NOT NULL,
    `check_date` date NOT NULL,
	`created_by` int NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

INSERT INTO `check_vouchers`(`cv_number`, `payee_name`, `date`, `total_amount`, `amount_in_words`, `bank`, `check_number`, `check_date`, `created_by`, `updated_by`)
VALUE ('CV-2024001', 'John Doe', '2024-07-21', 1000, 'One thousand pesos only.', 'Security Bank', '20240001',  '2024-07-21', 3, 3);

DROP TABLE `credit_card_vouchers`;
CREATE TABLE `credit_card_vouchers` (
	`id` int NOT NULL AUTO_INCREMENT,
	`ccv_number` varchar(50) NOT NULL,
    `payee_name` varchar(255) NOT NULL,
	`date` date NOT NULL,
    `total_amount` decimal(10, 2) NOT NULL,
    `amount_in_words` varchar(255) NOT NULL,
    `mode_of_payment` varchar(50) NOT NULL,
    `bank`	 varchar(50) NOT NULL,
	`created_by` int NULL,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

INSERT INTO `credit_card_vouchers`(`ccv_number`, `payee_name`, `date`, `total_amount`, `amount_in_words`, `mode_of_payment`, `bank`, `created_by`, `updated_by`)
VALUE ('CCV-2024001', 'John Doe', '2024-07-21', 1000, 'One thousand pesos only.', 'Credit Card', 'PNB', 3, 3);



