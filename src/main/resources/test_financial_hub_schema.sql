#CREATE DATABASE `test_financial_hub_db`;
#CREATE DATABASE IF NOT EXISTS `test_financial_hub_db`;
#USE `test_financial_hub_db`;

### USERS

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

-- Insert users (passwords should be encoded using BCrypt, below are plaintext for demonstration)
-- password: 123123 {bcrypt}$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq
INSERT INTO `users` (`first_name`, `last_name`, `middle_name`, `email`, `username`, `password`, `enabled`, `created_by`, `updated_by`) 
VALUES
('John', 'Doe', 'Eod', 'john@mail.com', 'john', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3),
('Mary', 'Public', 'Private', 'mary@mail.com', 'mary', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3),
('Susan', 'Sun Tzu', 'Yinyang', 'susan@mail.com','susan', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3),
('Eroll', 'Villaraiz', 'Divinaflor', 'huwan17@mail.com', 'huwan', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true, 3, 3);

### ROLES
DROP TABLE `roles`;
CREATE TABLE roles (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

-- Insert roles
INSERT INTO `roles` (`name`) VALUES ('ROLE_EMPLOYEE');
INSERT INTO `roles` (`name`) VALUES ('ROLE_MANAGER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');

### USERS ROLES
-- Create users_roles table (join table for many-to-many relationship)
DROP TABLE `users_roles`;
CREATE TABLE users_roles (
	`user_id` int NOT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`user_id`,`role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

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

SELECT u.id AS user_id, u.first_name, u.last_name, u.middle_name,
u.email, u.username, r.id AS role_id, r.name AS role_name
FROM users u
JOIN users_roles ur ON u.id = ur.user_id
JOIN roles r ON ur.role_id = r.id;
    
DROP TABLE `contact_category`;
CREATE TABLE `contact_category` (
    `id` int NOT NULL AUTO_INCREMENT,
    `contact_category_name` varchar(255) NOT NULL,
	`created_by` int DEFAULT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int DEFAULT NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

INSERT INTO `contact_category` (`contact_category_name`, `created_by`, `updated_by`)
VALUES ('Client', 1, 1), ('Supplier/Vendor/Partner', 1, 1), ('Employee', 1, 1),
('Internal', 1, 1), ('Others', 1, 1);

DROP TABLE `contact_sub_category`;
CREATE TABLE `contact_sub_category` (
    `id` int NOT NULL AUTO_INCREMENT,
    `contact_sub_category_name` varchar(255) NOT NULL,
	`created_by` int DEFAULT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int DEFAULT NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,	    PRIMARY KEY (`id`)
);

INSERT INTO `contact_sub_category` (`contact_sub_category_name`, `created_by`, `updated_by`)
VALUES ('Individual', 1, 1), ('Company', 1, 1), ('Others', 1, 1);

INSERT INTO `contact_sub_category` (`contact_sub_category_name`, `created_by`, `updated_by`) VALUE ('Employee', 1, 1);

DROP TABLE `contacts`;
CREATE TABLE `contacts` (
    `id` int NOT NULL AUTO_INCREMENT,
    `contact_category_id` int NOT NULL,
    `contact_sub_category_id` int NOT NULL,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `middle_name` varchar(255) NULL,
    `email` varchar(255) NOT NULL,
    `contact_no` varchar(255) NOT NULL,
    `address`varchar(255) NOT NULL,
	`created_by` int DEFAULT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int DEFAULT NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,	
	FOREIGN KEY (`contact_category_id`) REFERENCES `contact_category`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`contact_sub_category_id`) REFERENCES `contact_sub_category`(`id`) ON DELETE CASCADE,    
    PRIMARY KEY (`id`)
);

INSERT INTO `contacts` (`contact_category_id`, `contact_sub_category_id`, `first_name`,`last_name`, `middle_name`, `email`, `contact_no`, `address`, `created_by`, `updated_by`)
VALUES (1, 1, 'Johnny', 'Deff', '', 'john@mail.com', '09XXXXXX', 'Laguna', 1, 1), (2, 1, 'Peter', 'Dinklage', 'Lannister',  'peter@mail.com', '09XXXXXX', 'Manila', 1, 1),
 (3, 1, 'Tony', 'Stark', 'Downey', 'stark@mail.com', '09XXXXXX', 'Boracay', 1, 1);

### Join Contacts, Contact Category, & Contact Sub Category
SELECT `c`.`first_name` AS `Firstname`, `c`.`last_name` AS `Lastname`, `c`.`email` AS `Email`, `c`.`contact_no`,
`cc`.`contact_category_name`, `csc`.`contact_sub_category_name` 
FROM `contact_category` `cc`
RIGHT JOIN `contacts` `c`
ON `cc`.`id` = `c`.`contact_category_id`
LEFT JOIN `contact_sub_category` `csc`
ON `c`.`contact_sub_category_id` = `csc`.`id`;
    
# FUND - REFLENISHMENT- PETTY-CASH - REIMBURSEMENT

DROP TABLE `fund`;
CREATE TABLE `fund` (
	`id` int NOT NULL AUTO_INCREMENT,
    `fund_balance` decimal(10,2) NOT NULL,
    `created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);


INSERT INTO `fund`(`fund_balance`, `created_by`, `updated_by` ) VALUE (5000.00, 1, 1);

# FUND TRANSACTION
DROP TABLE `fund_transactions`;
CREATE TABLE `fund_transactions` (
	`id` int NOT NULL AUTO_INCREMENT,
    `fund_id` int NOT NULL,
	`date` date NOT NULL,
    `total_amount` decimal(10, 2) NOT NULL,
    `fund_type` enum('PETTY_CASH', 'REPLENISHEMENT', 'REIMBURSEMENT', 'CHANGE') NOT NULL,
	`created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`fund_id`) REFERENCES `fund`(id) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);


DROP TABLE `fund_transaction_details`;
CREATE TABLE `fund_transaction_details` (
	`id` int NOT NULL AUTO_INCREMENT,
    `fund_transaction_id` int NOT NULL,
	`created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`fund_transaction_id`) REFERENCES `fund_transactions`(id) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);


DROP TABLE `petty_cash_vouchers`;
CREATE TABLE `petty_cash_vouchers` (
	`id` int NOT NULL AUTO_INCREMENT,
    `fund_id` int NOT NULL,
    `pcv_number` varchar(50) NOT NULL,
    `received_by` varchar(255) NOT NULL,
    `date` date NOT NULL,
    `particulars` varchar(255) NOT NULL,
    `total_amount` decimal(10, 2) NOT NULL,
    `approved_by` varchar(255) NOT NULL,
    `created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`fund_id`) REFERENCES `fund`(id) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);

# DATE - format: YYYY-MM-DD.
INSERT INTO `petty_cash_vouchers`(`fund_id`, `pcv_number`, `received_by`, `date`, `particulars`, `total_amount`, `approved_by`, `created_by`, `updated_by`)
VALUE (1, 'PCV-2024001', 'Juan Dela Cruz', '2024-07-21', 'Utility expense', 1000, 'John Doe', 1, 1);

### PETTY CASH LIQUIDATION

DROP TABLE `petty_cash_liquidation`;
CREATE TABLE `petty_cash_liquidation` (
	`id` int NOT NULL AUTO_INCREMENT,
    `petty_cash_id` int NOT NULL,
    `contact_id` int NOT NULL,
	`date` date NOT NULL,
    `particulars` varchar(255) NOT NULL,
    `cost` decimal(10, 2) NOT NULL,
    `receipt_no` varchar(50) NULL,
    `remarks` varchar(255) NULL,
    `billed` boolean NULL,
    `created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`petty_cash_id`) REFERENCES `petty_cash_vouchers`(id) ON DELETE CASCADE,
    FOREIGN KEY (`contact_id`) REFERENCES `contacts`(id) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);
#`charge_to` varchar(250) NOT NULL,

INSERT INTO `petty_cash_liquidation` 
(`petty_cash_id`, `contact_id`, `date`, `particulars`, `cost`, `receipt_no`, `remarks`, `billed`, `created_by`, `updated_by`)
VALUES 
(1, 1, '2024-09-07', 'Office Supplies', 150.50, '00000001', 'Purchased printer', true, 3, 3),
(1, 1, '2024-09-07', 'Utility Supplies', 200, '0000212321', null, false, 3, 3),
(1, 1, '2024-09-07', 'Meals', 150.50, '0002024001', 'Metro manila transaction', false, 3, 3),
(1, 1, '2024-09-07', 'Certification Fee', 300, '000202400012', 'Metro manila transaction', false, 3, 3);

SELECT `pcv`.`total_amount`, `pcv`.`received_by`, `pcv`.`date`, `pcl`.`contact_id`, `c`.`first_name`, `c`.`last_name`,
`ct`.`con_type`, `cst`.`sub_type`
FROM `fund` `f`
LEFT JOIN `petty_cash_vouchers` `pcv`
ON `f`.`id` = `pcv`.`fund_id`
LEFT JOIN `petty_cash_liquidation` `pcl`
ON `pcv`.`id` = `pcl`.`petty_cash_id`
LEFT JOIN `contacts` `c`
ON `pcl`.`contact_id` = `c`.`id`
LEFT JOIN `contact_type` `ct`
ON `c`.`contact_type_id` = `ct`.`id`
LEFT JOIN `contact_sub_type` `cst`
ON `c`.`contact_sub_type_id` = `cst`.`id`
ORDER BY `pcl`.`created_at` ASC;

### CHECK VOUCHERS

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
	`created_by` int,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

INSERT INTO `check_vouchers`(`cv_number`, `payee_name`, `date`, `total_amount`, `amount_in_words`, `bank`, `check_number`, `check_date`, `created_by`, `updated_by`)
VALUE ('CV-2024001', 'John Doe', '2024-07-21', 1000, 'One thousand pesos only.', 'Security Bank', '20240001',  '2024-07-21', 3, 3);

### CREDIT CARD VOUCHERS
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
	`created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

INSERT INTO `credit_card_vouchers`(`ccv_number`, `payee_name`, `date`, `total_amount`, `amount_in_words`, `mode_of_payment`, `bank`, `created_by`, `updated_by`)
VALUE ('CCV-2024001', 'John Doe', '2024-07-21', 1000, 'One thousand pesos only.', 'Credit Card', 'PNB', 3, 3);

### BANKS

DROP TABLE `banks`;
CREATE TABLE `banks` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `abbreviation` varchar(255) NULL,
	`branch` varchar(255) NOT NULL,
    `created_by` int DEFAULT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int DEFAULT NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
);

INSERT INTO `banks`
(`name`, `abbreviation`, `branch`, `created_by`, `updated_by`) 
VALUES ('PHILIPPINE NATIONAL BANK', 'PNB', 'Bocaue', 1, 1), ('SECURITY BANK', 'SB', 'Bocaue', 1, 1), ('BANCO DE ORO', 'BDO', 'Bocaue', 1, 1);

INSERT INTO `banks` (`name`, `abbreviation`, `branch`, `created_by`, `updated_by`) VALUE ('PHILIPPINE NATIONAL BANK', 'PNB', 'Sta. Maria', 1, 1);

DROP TABLE `bank_accounts`;
CREATE TABLE `bank_accounts` (
    `id` int NOT NULL AUTO_INCREMENT,
    `bank_id` int NOT NULL,
    `account_holder_name` varchar(255) NOT NULL,
    `account_number` varchar(255) UNIQUE NOT NULL,
    `account_balance` decimal(10,2) DEFAULT 0.00,
    `created_by` int DEFAULT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int DEFAULT NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`bank_id`) REFERENCES `banks`(`id`) ON DELETE CASCADE,
    PRIMARY KEY(`id`)
);

INSERT INTO `bank_accounts`
(`bank_id`, `account_holder_name`, `account_number`, `account_balance`, `created_by`, `updated_by`)
VALUES 
(1, 'AMG Legal', '20240001', 0, 1, 1),
(2, 'Ace M. Gomez', '20230001', 0, 1, 1), 
(3, 'AMG Legal', '20220001', 0, 1, 1);

INSERT INTO `bank_accounts`(`bank_id`, `account_holder_name`, `account_number`, `account_balance`, `created_by`, `updated_by`) 
VALUE (2, 'AMG Legal', '20220013', 0, 1, 1);
INSERT INTO `bank_accounts`(`bank_id`, `account_holder_name`, `account_number`, `account_balance`, `created_by`, `updated_by`) 
VALUE (4, 'AMG Legal', '20210023', 0, 1, 1);

### Bank & Bank Account Left Join

SELECT `b`.`id` AS `Bank ID`, `b`.`name` AS `Bank Name`, `b`.`abbreviation` AS `Abrev.`, `b`.`branch` AS `Branch`,
`ba`.`account_holder_name` AS `Account Name`, `ba`.`account_number` AS `Account Number`, `ba`.`bank_id` AS `Bank ID` 
FROM `banks` `b`
LEFT JOIN `bank_accounts` `ba`
ON `b`.`id` = `ba`.`bank_id`;

-- Create a table for Transactions (both Deposit and Withdrawal)
DROP TABLE `bank_transactions`;
CREATE TABLE `bank_transactions` (
    `id` int NOT NULL AUTO_INCREMENT,
    `bank_account_id` int NOT NULL,
    `transaction_date` date NOT NULL,    
    `transaction_type` enum('DEPOSIT', 'WITHDRAWAL') NOT NULL,
    `transaction_amount` decimal(10, 2) NOT NULL,
	`transaction_note` varchar(255) NULL,
	`created_by` int DEFAULT NULL,
    `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int DEFAULT NULL,
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`bank_account_id`) REFERENCES `bank_accounts`(`id`) ON DELETE CASCADE,
    PRIMARY KEY (`id`)
);
### DEPOSIT
INSERT INTO `bank_transactions`
(`bank_account_id`, `transaction_date`, `transaction_type`, `transaction_amount`, `transaction_note`, `created_by`, `updated_by`)
VALUES (1, '2024-09-11', 'DEPOSIT', 1000.00, 'ABC Corp', 1, 1), (1, '2024-09-11', 'DEPOSIT', 500.00, 'EFG Corp', 1, 1), (1, '2024-09-11', 'DEPOSIT', 2500.00, 'ABC Legal', 1, 1);

INSERT INTO `bank_transactions`
(`bank_account_id`, `transaction_date`, `transaction_type`, `transaction_amount`, `transaction_note`, `created_by`, `updated_by`)
VALUES (2, '2024-09-11', 'DEPOSIT', 500.00, 'EFG Corp', 1, 1), (2, '2024-09-11', 'DEPOSIT', 2500.00, 'ABC Legal', 1, 1);

INSERT INTO `bank_transactions`
(`bank_account_id`, `transaction_date`, `transaction_type`, `transaction_amount`, `transaction_note`, `created_by`, `updated_by`)
VALUES (1, '2024-09-13', 'WITHDRAWAL', 3000.00, 'AMG Legal - Electricity Bills', 1, 1), (1, '2024-09-13', 'WITHDRAWAL', 2500.00, 'AMG Legal - Employee Salary', 1, 1), (1, '2024-09-13', 'WITHDRAWAL', 2500.00, 'Ace M. Gomez - Petty Cash', 1, 1);

INSERT INTO `bank_transactions`
(`bank_account_id`, `transaction_date`, `transaction_type`, `transaction_amount`, `transaction_note`, `created_by`, `updated_by`)
VALUE (2, '2024-09-13', 'WITHDRAWAL', 3000.00, 'Ace M. Gomez - Bills Payment', 1, 1);

SELECT `b`.`name` AS `Bank`, `ba`.`id` AS `Acc. ID`, `ba`.`account_number` AS `Acc. Number`, `ba`.`account_holder_name` AS `Acc. Holder Name`,
`ba`.`account_balance` AS `Balance`, `bt`.`transaction_date` AS `Transaction Date`, `bt`.`transaction_type` AS `Transaction Type`,
`bt`.`transaction_amount` AS `Transaction Amount`, `bt`.`transaction_note` AS `Note`, `bt`.`bank_account_id` AS `Bank Acc. ID`
FROM `banks` `b`
LEFT JOIN `bank_accounts` `ba`
ON `b`.`id` = `ba`.`bank_id`
LEFT JOIN `bank_transactions` `bt`
ON `ba`.`id` = `bt`.`bank_account_id`;
# WHERE `bt`.`transaction_type` = 'WITHDRAWAL';

UPDATE `test_financial_hub_db`.`bank_transactions` SET `transaction_note` = 'AMG Legal -  Petty Cash' WHERE (`id` = '12');
