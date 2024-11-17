#CREATE DATABASE `test_financial_hub_db`;
#CREATE DATABASE IF NOT EXISTS `test_financial_hub_db`;
#USE `test_financial_hub_db`;

### USERS

DROP TABLE `contact_users`;
CREATE TABLE `contact_users` (
	`user_id` int NOT NULL AUTO_INCREMENT,
    `contact_id` int NOT NULL,
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
    FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`) ON DELETE SET NULL,
    PRIMARY KEY (`id`)
);

# FIND FOREIGN KEY CONSTRAINT NAME
SELECT CONSTRAINT_NAME 
FROM information_schema.KEY_COLUMN_USAGE 
WHERE TABLE_NAME = 'contact_users' 
AND COLUMN_NAME = 'contact_id';
#CONSTRAINT_NAME  'fk_users_contact'

SELECT CONSTRAINT_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'contact_users'
  AND TABLE_SCHEMA = 'test_financial_hub_db'  -- Replace with your database name
  AND COLUMN_NAME = 'contact_id';

# DROP FOREIGN KEY
ALTER TABLE contact_users
DROP FOREIGN KEY fk_users_contact;

# CREATE TABLE CODE
SHOW CREATE TABLE contact_users;

# ADD NEW FOREIGN KEY CONSTRAINT
ALTER TABLE contact_users
ADD CONSTRAINT fk_contact_user
FOREIGN KEY (contact_id)
REFERENCES contacts(contact_id)
ON DELETE SET NULL;

# ALTER TABLE COLUMN TYPE
ALTER TABLE `contact_users` 
MODIFY `contact_id` INT;

# INNER JOIN (only users with a valid contact)
SELECT 
    u.user_id AS user_id,
    u.first_name,
    u.last_name,
    u.email,
    u.username,
    c.contact_id,
    c.contact_type,
    c.category_type,
    c.engagement_date,
    c.best_channel_to_contact
FROM 
    contact_users u
INNER JOIN 
    contacts c ON u.contact_id = c.contact_id;
    
#LEFT JOIN (all users, including those without a contact)

SELECT 
    u.user_id AS user_id,
    u.first_name,
    u.last_name,
    u.email,
    u.username,
    c.contact_id,
    c.contact_type,
    c.category_type,
    c.engagement_date,
    c.best_channel_to_contact
FROM 
    contact_users u
LEFT JOIN 
    contacts c ON u.contact_id = c.contact_id;
    
# ALTER TABLE `test_financial_hub_db`.`contact_users`
# RENAME COLUMN `id` TO `user_id`;

# Change Table Name
#ALTER TABLE `test_financial_hub_db`.`users` RENAME TO `test_financial_hub_db`.`contact_users`;

#ALTER TABLE `test_financial_hub_db`.`contact_users` DROP COLUMN `contact_id`;

#ALTER TABLE  `test_financial_hub_db`.`contact_users`
#MODIFY COLUMN `contact_id` INT AFTER `id`;

#ALTER TABLE `test_financial_hub_db`.`contact_users`
#ADD COLUMN `contact_id` INT,
#ADD CONSTRAINT `fk_users_contact`
#FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`) ON DELETE SET NULL;

-- Insert users (passwords should be encoded using BCrypt, below are plaintext for demonstration)
-- password: 123123 {bcrypt}$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq
INSERT INTO `contact_users` (`first_name`, `last_name`, `middle_name`, `email`, `username`, `password`, `enabled`, `created_by`, `updated_by`) 
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

-- Junction Table
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

SELECT u.user_id AS user_id, u.first_name, u.last_name, u.middle_name,
u.email, u.username, r.id AS role_id, r.name AS role_name
FROM contact_users u
JOIN users_roles ur ON u.user_id = ur.user_id
JOIN roles r ON ur.role_id = r.id;

-- Table to store all contact types (Individual/Company)
DROP TABLE `contacts`;
CREATE TABLE `contacts` (
    `contact_id` INT AUTO_INCREMENT,
    `contact_type` enum('INDIVIDUAL', 'COMPANY') NOT NULL,
    `category_type` enum('CLIENT', 'VENDOR', 'INTERNAL') NOT NULL,
    `engagement_date` date,
    `best_channel_to_contact` varchar(255),
    `created_by` int,
    `created_at` timestamp default current_timestamp,
	`updated_by` int,
    `updated_at` timestamp default current_timestamp on update current_timestamp,
    PRIMARY KEY(`contact_id`)
);

-- Insert into Contact table for individual
INSERT INTO `contacts` (`contact_type`, `category_type`, `engagement_date`, `best_channel_to_contact`, `created_by`, `updated_by`) 
VALUES ('INDIVIDUAL', 'CLIENT', '2024-09-21', 'Email', 1, 1);

-- Insert into Contact table for company
INSERT INTO `contacts` (`contact_type`, `category_type`, `engagement_date`, `best_channel_to_contact`, `created_by`, `updated_by`)
VALUES ('COMPANY', 'VENDOR', '2024-09-20', 'Phone', 1, 1);

-- Table for individuals
DROP TABLE `contact_individual`;
CREATE TABLE `contact_individual` (
    `individual_id` int AUTO_INCREMENT,
    `contact_id` int NOT NULL,
    `title` varchar(10),
    `last_name` varchar(255) NOT NULL,
    `first_name` varchar(255) NOT NULL,
    `middle_name` varchar(255),
    `suffix` varchar(10),
    `mobile_number` varchar(20),
    `email_address` varchar(255),	
    `address` text,
    FOREIGN KEY (`contact_id`) REFERENCES `contacts`(`contact_id`),
    PRIMARY KEY(`individual_id`)
);

-- Insert into Individual table
INSERT INTO `contact_individual` (`contact_id`, `title`, `last_name`, `first_name`, `middle_name`, `suffix`, `mobile_number`, `email_address`, `address`) 
VALUES (1, 'Mr.', 'Doe', 'John', 'A.', 'Jr.', '1234567890', 'john.doe@example.com', '123 Elm St, City');

-- Table for companies
DROP TABLE `contact_company`;
CREATE TABLE `contact_company` (
    `company_id` INT NOT NULL AUTO_INCREMENT,
    `contact_id` INT NOT NULL,
    `company_name` VARCHAR(255) NOT NULL,
    `registration_type` ENUM('CORPORATION', 'PARTNERSHIP', 'SINGLE_PROPRIETORSHIP', 'FOUNDATION', 'ASSOCIATION', 'OTHERS') NOT NULL,
    `representative_name` VARCHAR(255),
    `representative_designation` VARCHAR(255),
    `mobile_number` VARCHAR(20),
    `email_address` VARCHAR(255),
    `address` TEXT,
    FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`),
    PRIMARY KEY (`company_id`)
);

-- Insert into Company table
INSERT INTO `contact_company` (`contact_id`, `company_name`, `registration_type`, `representative_name`, `representative_designation`, `mobile_number`, `email_address`, `address`) 
VALUES (2, 'Tech Solutions', 'CORPORATION', 'Jane Smith', 'CEO', '9876543210', 'info@techsolutions.com', '456 Oak St, City');

-- Table for additional details (Vendor/Internal)
DROP TABLE `contact_additional_details`; 
CREATE TABLE `contact_additional_details` (
    `detail_id` int AUTO_INCREMENT PRIMARY KEY,
    `contact_id` int NOT NULL,
    `designation_for` VARCHAR(255),
    `bank_name` varchar(255),
    `account_no` varchar(50),
    FOREIGN KEY (`contact_id`) REFERENCES `contacts`(`contact_id`)
);

-- Insert additional details for a Company/Vendor/Internal (using the contact_id from the previous insert)
INSERT INTO `contact_additional_details` (`contact_id`, `designation_for`, `bank_name`, `account_no`) 
VALUES (2, 'IT Solutions Provider', 'Tech Bank', '1234567890');

INSERT INTO `contact_additional_details` (`contact_id`, `designation_for`, `bank_name`, `account_no`) 
VALUES (9, null, null, null), (11, null, null, null);

SELECT 
    c.contact_id,
    c.contact_type,
    c.category_type,
    c.engagement_date,
    c.best_channel_to_contact,
    i.title,
    i.last_name,
    i.first_name,
    i.middle_name,
    i.suffix,
    i.mobile_number AS individual_mobile,
    i.email_address AS individual_email,
    i.address AS individual_address,
    co.company_name,
    co.registration_type,
    co.representative_name,
    co.representative_designation,
    co.mobile_number AS company_mobile,
    co.email_address AS company_email,
    co.address AS company_address,
    ad.designation_for,
    ad.bank_name,
    ad.account_no,
    c.created_by,
    c.created_at,
    c.updated_by,
    c.updated_at
FROM
    contacts c
        LEFT JOIN
    contact_individual i ON c.contact_id = i.contact_id
        LEFT JOIN
    contact_company co ON c.contact_id = co.contact_id
        LEFT JOIN
    contact_additional_details ad ON c.contact_id = ad.contact_id;
    
# FUND - REFLENISHMENT- PETTY-CASH - REIMBURSEMENT
# DROP TABLE `client_accounts`;
CREATE TABLE `client_accounts` (
    `client_account_id` INT NOT NULL AUTO_INCREMENT,
    `contact_id` INT NOT NULL,
    `account_title` VARCHAR(255),
    `account_type` ENUM('PROJECT', 'CASE', 'RETAINER') NOT NULL,
	`created_by` int,
    `created_at` timestamp default current_timestamp,
	`updated_by` int,
    `updated_at` timestamp default current_timestamp on update current_timestamp,
    FOREIGN KEY (`contact_id`) REFERENCES `contacts`(`contact_id`),
    PRIMARY KEY (`client_account_id`)
);

INSERT INTO `client_accounts`(`contact_id`, `account_title`, `account_type`, `created_by`, `updated_by`) 
VALUE (1, 'Case Title 1', 'CASE', 1, 1);

INSERT INTO `client_accounts`(`contact_id`, `account_title`, `account_type`, `created_by`, `updated_by`) 
VALUES (2, 'Case Title 2', 'CASE', 1, 1), (9, 'Case Title 3', 'CASE', 1, 1), 
(10, 'Case Title 4', 'CASE', 1, 1), (11, 'Case Title 5', 'CASE', 1, 1);

# DROP TABLE `client_case_details`;
CREATE TABLE `client_case_details` (
	`case_id` int NOT NULL AUTO_INCREMENT,
    `client_account_id` int NOT NULL,
    `case_type` enum('CIVIL', 'CRIMINAL', 'LABOR', 'ADMINISTRATIVE', 'ELECTION', 'SPECIAL_PROCEEDINGS', 'OTHERS') NOT NULL,
    `case_title` varchar(255) NOT NULL,
    `docket_no` varchar(255) NOT NULL,
    `nature` varchar(255) NOT NULL,
    `court` varchar(255) NOT NULL,
    `branch` varchar(255) NOT NULL,
    `judge` varchar(255) NOT NULL,
	`court_email` varchar(255) NOT NULL,
    `prosecutor` varchar(255) NOT NULL,
    `prosecutor_office`varchar(255) NOT NULL,
    `prosecutor_email` varchar(255) NOT NULL,
    `opposing_party` varchar(255) NOT NULL,
    `opposing_counsel` varchar(255) NOT NULL,
    `counsel_email` varchar(255) NOT NULL,
    `start_date` date NOT NULL,
    `end_date` date NULL,
	`status` enum('OPEN', 'IN_PROGRESS', 'PENDING', 'COMPLETED', 'CLOSED') NOT NULL,
    `stage`  varchar(255) NOT NULL,
    FOREIGN KEY (`client_account_id`) REFERENCES `client_accounts`(`client_account_id`),
    PRIMARY KEY (`case_id`)
);

# Delete Column
# ALTER TABLE `client_case_details` DROP COLUMN `updated_at`;

# Change Table Name
# ALTER TABLE `test_financial_hub_db`.`cases` RENAME TO `test_financial_hub_db`.`client_case_details`;

SELECT * FROM `test_financial_hub_db`.`client_case_details`;

INSERT INTO `client_case_details` (
  `client_account_id`, `case_type`, `case_title`, `docket_no`, `nature`, 
  `court`, `branch`, `judge`, `court_email`, `prosecutor`, 
  `prosecutor_office`, `prosecutor_email`, `opposing_party`, `opposing_counsel`, 
  `counsel_email`, `start_date`, `end_date`, `status`, `stage`, `created_by`, `updated_by`
) 
VALUES
  (1, 'CIVIL', 'Case Title 1', 'DCK12345', 'Breach of Contract', 
   'Supreme Court', 'Branch 1', 'Judge John Doe', 'court1@example.com', 'Jane Smith', 
   'Office of Prosecutor A', 'prosecutor1@example.com', 'Opposing Party A', 'Opposing Counsel A', 
   'counsel1@example.com', '2024-01-01', '2024-06-01', 'OPEN', 'Stage 1', 1, 1);

INSERT INTO `client_case_details` (
  `client_account_id`, `case_type`, `case_title`, `docket_no`, `nature`, 
  `court`, `branch`, `judge`, `court_email`, `prosecutor`, 
  `prosecutor_office`, `prosecutor_email`, `opposing_party`, `opposing_counsel`, 
  `counsel_email`, `start_date`, `end_date`, `status`, `stage`, `created_by`, `updated_by`
) 
VALUES
  (2, 'CRIMINAL', 'Case Title 2', 'DCK54321', 'Fraud', 
   'Regional Trial Court', 'Branch 2', 'Judge Jane Roe', 'court2@example.com', 'John Doe', 
   'Office of Prosecutor B', 'prosecutor2@example.com', 'Opposing Party B', 'Opposing Counsel B', 
   'counsel2@example.com', '2023-10-15', '2024-05-15', 'IN_PROGRESS', 'Stage 2', 2, 1),

  (3, 'LABOR', 'Case Title 3', 'DCK67890', 'Unfair Labor Practices', 
   'Labor Court', 'Branch 3', 'Judge Emily White', 'court3@example.com', 'Sarah Green', 
   'Office of Prosecutor C', 'prosecutor3@example.com', 'Opposing Party C', 'Opposing Counsel C', 
   'counsel3@example.com', '2023-12-01', '2024-07-01', 'PENDING', 'Stage 3', 3, 1),

  (4, 'ADMINISTRATIVE', 'Case Title 4', 'DCK09876', 'Misconduct', 
   'Administrative Court', 'Branch 4', 'Judge William Black', 'court4@example.com', 'Paul Blue', 
   'Office of Prosecutor D', 'prosecutor4@example.com', 'Opposing Party D', 'Opposing Counsel D', 
   'counsel4@example.com', '2023-09-01', '2024-02-01', 'COMPLETED', 'Stage 4', 4, 1),

  (5, 'ELECTION', 'Case Title 5', 'DCK34567', 'Election Violation', 
   'Election Court', 'Branch 5', 'Judge Michael Brown', 'court5@example.com', 'Alice Red', 
   'Office of Prosecutor E', 'prosecutor5@example.com', 'Opposing Party E', 'Opposing Counsel E', 
   'counsel5@example.com', '2023-11-10', '2024-08-10', 'CLOSED', 'Stage 5', 5, 1);

SELECT 
    `a`.`client_account_id`,
    `a`.`account_title`,
    `a`.`account_type`,
    `c`.`case_id`,
    `c`.`case_type`,
    `c`.`case_title`,
    `c`.`docket_no`,
    `c`.`nature`,
    `ct`.`contact_id` AS contacts_id,
    `ct`.`contact_type`,
     CONCAT(
           IFNULL(`ci`.`title`, ''), ' ',
           IFNULL(`ci`.`first_name`, ''), ' ',
           IFNULL(`ci`.`middle_name`, ''), ' ',
           IFNULL(`ci`.`last_name`, ''), ' ',
           IFNULL(`ci`.`suffix`, '')
       ) AS full_name,
    `ci`.`mobile_number` AS individual_mobile,
    `ci`.`email_address` AS individual_email,
    `cc`.`company_name` AS company_name,
    `cc`.`representative_name` AS company_representative,
    `cc`.`mobile_number` AS company_mobile,
    `cc`.`email_address` AS company_email,
    `ct`.`best_channel_to_contact`,
    `c`.`start_date`,
    `c`.`end_date`,
    `c`.`status`,
    `c`.`stage`,
    `a`.`created_by`,
    `a`.`created_at`,
    `a`.`updated_by`,
    `a`.`updated_at`
FROM
    `client_accounts` a
        LEFT JOIN
    `client_case_details` c ON a.client_account_id = c.client_account_id
        JOIN
    `contacts` ct ON a.contact_id = ct.contact_id
        LEFT JOIN
    `contact_individual` ci ON ct.contact_id = ci.contact_id
        AND ct.contact_type = 'INDIVIDUAL'
        LEFT JOIN
    `contact_company` cc ON ct.contact_id = cc.contact_id
        AND ct.contact_type = 'COMPANY'
        WHERE `a`.`account_type` = 'CASE';

# DROP TABLE `client_retainer_details`;
CREATE TABLE `client_retainer_details` (
	`retainer_id` int NOT NULL AUTO_INCREMENT,
    `client_account_id` int NOT NULL,
	`ratainer_title` varchar(255) NOT NULL,
	`status` enum('OPEN', 'IN_PROGRESS', 'PENDING', 'COMPLETED', 'CLOSED') NOT NULL,
    FOREIGN KEY (`client_account_id`) REFERENCES `client_accounts`(`client_account_id`),
    PRIMARY KEY (`retainer_id`)
);

# NEW CLIENT ACCOUNT FOR RETAINER
INSERT INTO `client_accounts`(`contact_id`, `account_title`, `account_type`, `created_by`, `updated_by`) 
VALUES (1, 'John  Doe Sr.', 'RETAINER', 1, 1), (2, 'Tech Solutions', 'RETAINER', 1, 1) ;

# CREATE NEW RETAINER DETAILS
INSERT INTO `test_financial_hub_db`.`client_retainer_details` (`client_account_id`, `status`)
VALUES (11, 'OPEN'), (12, 'IN_PROGRESS');

# JOIN CLIENT ACCOUNT & RETAINER DETAILS
SELECT 
    `a`.`client_account_id`,
    `a`.`account_title`,
    `c`.`retainer_title`,
    `c`.`start_date`,
    `c`.`end_date`,
    `a`.`account_type`,
    `ct`.`contact_id` AS contacts_id,
    `ct`.`contact_type`,
     CONCAT(
           IFNULL(`ci`.`title`, ''), ' ',
           IFNULL(`ci`.`first_name`, ''), ' ',
           IFNULL(`ci`.`middle_name`, ''), ' ',
           IFNULL(`ci`.`last_name`, ''), ' ',
           IFNULL(`ci`.`suffix`, '')
       ) AS full_name,
    `ci`.`mobile_number` AS individual_mobile,
    `ci`.`email_address` AS individual_email,
    `cc`.`company_name` AS company_name,
    `cc`.`representative_name` AS company_representative,
    `cc`.`mobile_number` AS company_mobile,
    `cc`.`email_address` AS company_email,
    `ct`.`best_channel_to_contact`,
    `c`.`status`,
    `a`.`created_by`,
    `a`.`created_at`,
    `a`.`updated_by`,
    `a`.`updated_at`
FROM
    `client_accounts` a
        LEFT JOIN
    `client_retainer_details` c ON a.client_account_id = c.client_account_id
        JOIN
    `contacts` ct ON a.contact_id = ct.contact_id
        LEFT JOIN
    `contact_individual` ci ON ct.contact_id = ci.contact_id
        AND ct.contact_type = 'INDIVIDUAL'
        LEFT JOIN
    `contact_company` cc ON ct.contact_id = cc.contact_id
        AND ct.contact_type = 'COMPANY'
	WHERE `a`.`account_type` = 'RETAINER';

# ADD COLUMN
ALTER TABLE `client_retainer_details`
ADD COLUMN `end_date` date AFTER `start_date`;

# MODIFY COLUMN TYPE
ALTER TABLE `client_retainer_details` 
MODIFY COLUMN `start_date` date NOT NULL;

# RENAME COLUMN NAME
# ALTER TABLE `client_retainer_details`
# RENAME COLUMN `ratainer_title` TO `retainer_title`;

# UPDATE Retainer Title
UPDATE `client_retainer_details`
SET `retainer_title` = 'Tech Solutions'
WHERE `retainer_id` = 2;


#DROP TABLE `client_project_details`;
CREATE TABLE `client_project_details` (
	`project_id` int NOT NULL AUTO_INCREMENT,
    `client_account_id` int NOT NULL,
    `project_type` enum('PROPERTIES', 'BUSINESS', 'SEC') NOT NULL,
    `property_sub_type` enum('TRANSFER_OF_TITLE', 'SETTLEMENT_OF_ESTATE', 'ANNOTATION', 'OTHERS'),
    `business_sub_type` enum('BUSINESS_REGISTRATION', 'BUSINESS_RENEWAL', 'BUSINESS_CLOSURE', 'OTHERS'),
    `sec_sub_type` enum('SEC_REGISTRATION','AMENDMENT_OF_ARTICLES_OF_INCORPORATION', 'INCREASE_IN_AUTHORIZED_CAPITAL_STOCK'),
    `project_title` varchar(255) NOT NULL,
	`title_no` varchar(255),
    `tax_dec_no` varchar(255),
    `lot_no` varchar(255),
    `lot_area` varchar(255),
    `location` varchar(255),
    `bir` varchar(255),
    `rd` varchar(255),
    `zonal_value` varchar(255),
    `purchase_price` varchar(255),
    `remarks` varchar(255),
    `deceased` varchar(255),
    `heirs` varchar(255),
    `address` varchar(255),
	`status` enum('OPEN', 'IN_PROGRESS', 'PENDING', 'COMPLETED', 'CLOSED') NOT NULL,
    FOREIGN KEY (`client_account_id`) REFERENCES `client_accounts`(`client_account_id`),
    PRIMARY KEY (`project_id`)
);

# ADD NEW COLUMN
# ALTER TABLE `client_project_details`
# ADD COLUMN `business_sub_type` enum('BUSINESS_REGISTRATION', 'BUSINESS_RENEWAL', 'BUSINESS_CLOSURE', 'OTHERS') AFTER `property_sub_type`;

# CHANGE ENUM VALUES
ALTER TABLE `client_project_details` 
MODIFY COLUMN `sec_sub_type` enum('SEC_REGISTRATION', 'AMENDMENT_OF_ARTICLES_OF_INCORPORATION', 'INCREASE_IN_AUTHORIZED_CAPITAL_STOCK');

# UPDATE project type
# UPDATE your_table_name SET project_type = 'BUSINESS' 
# WHERE project_type IN ('BUSINESS_REGISTRATION', 'BUSINESS_CLOSURE');


INSERT INTO `client_accounts`(`contact_id`, `account_title`, `account_type`, `created_by`, `updated_by`) 
VALUES (1, 'Transfer of Title', 'PROJECT', 1, 1), (2, 'Tech Solutions', 'PROJECT', 1, 1);

INSERT INTO `client_project_details`(`client_account_id`, `project_type`, `property_sub_type`, `sec_sub_type`, `project_title`, `title_no`, `tax_dec_no`, `lot_no`, `lot_area`, `location`, `bir`, `rd`, `zonal_value`, `purchase_price`, `remarks`, `deceased`, `heirs`, `address`, `status`)
VALUES 
(21, 'PROPERTIES', 'TRANSFER_OF_TITLE', NULL, 'Transfer of Title', 'T-12345', 'TD-67890', 'L-4567', '500 sqm', '123 Property Lane', 'BIR123', 'RD123', '500000', '2000000', 'Transfer under process', NULL, NULL, '123 Main St', 'IN_PROGRESS'),
(22, 'SEC', NULL, 'AMENDMENT_OF_ARTICLES_OF_INCORPORATION', 'Tech Solutions', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Amendment in progress', NULL, NULL, NULL, 'IN_PROGRESS');

    
# JOIN CLIENT ACCOUNT & PROJECT DETAILS
SELECT 
    `a`.`client_account_id`,
    `a`.`account_title`,
    `c`.`project_title`,
    `a`.`account_type`,
    `ct`.`contact_type`,
     CONCAT(
           IFNULL(`ci`.`title`, ''), ' ',
           IFNULL(`ci`.`first_name`, ''), ' ',
           IFNULL(`ci`.`middle_name`, ''), ' ',
           IFNULL(`ci`.`last_name`, ''), ' ',
           IFNULL(`ci`.`suffix`, '')
       ) AS full_name,
    `cc`.`company_name` AS company_name,
    `c`.`project_type`,
    `c`.`property_sub_type`,
    `c`.`business_sub_type`,
    `c`.`sec_sub_type`,
	`c`.`title_no`,
    `c`.`tax_dec_no`,
    `c`.`lot_no`,
    `c`.`lot_area`,
    `c`.`location`,
    `c`.`bir`,
    `c`.`rd`,
    `c`.`zonal_value`,
    `c`.`purchase_price`,
    `c`.`remarks`,
    `c`.`deceased`,
    `c`.`heirs`,
    `c`.`address`,
    `c`.`status`,
    `a`.`created_by`,
    `a`.`created_at`,
    `a`.`updated_by`,
    `a`.`updated_at`
FROM
    `client_accounts` a
        LEFT JOIN
    `client_project_details` c ON a.client_account_id = c.client_account_id
        JOIN
    `contacts` ct ON a.contact_id = ct.contact_id
        LEFT JOIN
    `contact_individual` ci ON ct.contact_id = ci.contact_id
        AND ct.contact_type = 'INDIVIDUAL'
        LEFT JOIN
    `contact_company` cc ON ct.contact_id = cc.contact_id
        AND ct.contact_type = 'COMPANY'
	WHERE `a`.`account_type` = 'PROJECT';


# DROP TABLE `fund`;
CREATE TABLE `fund` (
	`fund_id` int NOT NULL AUTO_INCREMENT,
    `fund_balance` decimal(10,2) NOT NULL,
    `created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`fund_id`)
);

INSERT INTO `fund`(`fund_balance`, `created_by`, `updated_by` ) VALUE (5000.00, 1, 1);

#FUND TRANSACTION
#DROP TABLE `fund_transactions`;
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

#DROP TABLE `fund_transaction_details`;
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
	`petty_cash_id` int NOT NULL AUTO_INCREMENT,
    `fund_id` int NOT NULL,
    `pc_voucher_no` varchar(50) NOT NULL,
    `date` date NOT NULL,
    `activity_description` text NOT NULL,
    `activity_category` varchar(100) NOT NULL,
    `soa_category` varchar(100) NOT NULL,
    `total_amount` decimal(10, 2) NOT NULL,
    `approved` boolean,
    `approved_by` int,
	`received_by` int,
    `created_by` int,
    `created_at`timestamp DEFAULT CURRENT_TIMESTAMP,
    `updated_by` int,	
    `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`fund_id`) REFERENCES `fund`(`fund_id`) ON DELETE CASCADE,
    PRIMARY KEY (`petty_cash_id`)
);


INSERT INTO `petty_cash_vouchers` 
(`fund_id`, `pc_voucher_no`, `date`, `activity_description`, `activity_category`, `soa_category`, `total_amount`, `approved`, `approved_by`, `received_by`, `created_by`)
VALUES 
(1, 'PCV-001', '2024-10-01', 'Purchase of office supplies', 'Office Expenses', 'Supplies', 150.00, FALSE, 1, 1, 1),

(1, 'PCV-002', '2024-10-05', 'Transportation for project site visit', 'Travel', 'Transportation', 75.00, FALSE, NULL, 1, 1),

(1, 'PCV-003', '2024-10-10', 'Lunch meeting with client', 'Meals & Entertainment', 'Client Engagement', 200.00, FALSE, 1, 1, 1),

(1, 'PCV-004', '2024-10-12', 'Printing of marketing materials', 'Marketing', 'Print Media', 500.00, FALSE, 2, 5, 3),

(1, 'PCV-005', '2024-10-15', 'Travel for business development', 'Travel', 'Business Development', 300.00, FALSE, 1, 1, 1);

# ALTER TABLE `petty_cash_vouchers`
# RENAME COLUMN `pcv_number` TO `petty_cash_no`;

# Junction Table for petty cash & client accounts
DROP TABLE `petty_cash_client_accounts`;
CREATE TABLE `petty_cash_client_accounts` (
    `petty_cash_id` INT,
    `client_account_id` INT,
    PRIMARY KEY (`petty_cash_id`,`client_account_id`),
    FOREIGN KEY (`petty_cash_id`) REFERENCES `petty_cash_vouchers`(`petty_cash_id`) ON DELETE CASCADE,
    FOREIGN KEY (`client_account_id`) REFERENCES `client_accounts`(`client_account_id`) ON DELETE CASCADE
);

INSERT INTO `petty_cash_client_accounts` (`petty_cash_id`, `client_account_id`)
VALUES 
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3),
(5, 1),
(5, 2),
(5, 3);

INSERT INTO `petty_cash_client_accounts` (`petty_cash_id`, `client_account_id`)
VALUES (9, 1), (12, 1),(13, 2),(14, 3);

SELECT 
    pcv.petty_cash_id,
    pcv.pc_voucher_no AS "PCV Number",
    pcv.date AS "Date",
    pcv.activity_description AS "Activity Description",
    pcv.activity_category AS "Activity Category",
    pcv.soa_category AS "SOA Category",
    pcv.total_amount AS "Total Amount",
    pcv.approved AS "Approved",
    pcv.approved_by AS "Approved By",
    pcv.received_by AS "Received By",
    GROUP_CONCAT(ca.account_title ORDER BY ca.account_title ASC SEPARATOR ', ') AS "Account Titles"
FROM 
    petty_cash_vouchers pcv
JOIN 
    petty_cash_client_accounts pcca ON pcv.petty_cash_id = pcca.petty_cash_id
JOIN 
    client_accounts ca ON pcca.client_account_id = ca.client_account_id	
GROUP BY 
    pcv.petty_cash_id
ORDER BY 
    pcv.date ASC;


# DATE - format: YYYY-MM-DD.
INSERT INTO `petty_cash_vouchers`(`fund_id`, `pcv_number`, `received_by`, `date`, `particulars`, `total_amount`, `approved_by`, `created_by`, `updated_by`)
VALUE (1, 'PCV-2024001', 'Juan Dela Cruz', '2024-07-21', 'Utility expense', 1000, 'John Doe', 1, 1);

### PETTY CASH LIQUIDATION

DROP TABLE `petty_cash_liquidation`;
CREATE TABLE `petty_cash_liquidation` (
	`liquidation_id` int NOT NULL AUTO_INCREMENT,
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
    FOREIGN KEY (`contact_id`) REFERENCES `contacts`(`contact_id`) ON DELETE CASCADE,
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

SELECT `pcv`.`total_amount`, `pcv`.`received_by`, `pcv`.`date`, `pcl`.`contact_id`, `c`.`contact_type`, `c`.`category_type`,
concat(`ci`.`first_name`, ' ', `ci`.`middle_name`, ' ', `ci`.`last_name`) AS `Fullname`, `cc`.`company_name`
FROM `fund` `f`
LEFT JOIN `petty_cash_vouchers` `pcv`
ON `f`.`id` = `pcv`.`fund_id`
LEFT JOIN `petty_cash_liquidation` `pcl`
ON `pcv`.`id` = `pcl`.`petty_cash_id`
LEFT JOIN `contacts` `c`
ON `pcl`.`contact_id` = `c`.`contact_id`
LEFT JOIN `contact_individual` `ci`
ON `c`.`contact_id` = `ci`.`contact_id`
LEFT JOIN `contact_company` `cc`
ON `c`.`contact_id` = `cc`.`contact_id`
ORDER BY `pcl`.`created_at` ASC;

DROP TABLE `gl_admin_activity_category`;
CREATE TABLE `gl_admin_activity_category` (
	`admin_activity_id` int NOT NULL AUTO_INCREMENT,
    `admin_activity` varchar(100) NOT NULL,
    PRIMARY KEY(`admin_activity_id`)
);

INSERT INTO `gl_admin_activity_category` (`admin_activity`) VALUES
('Print/Photocopy (Colored)'),
('Print/Photocopy (B&W)'),
('Office Supplies (Folder/Envelope)'),
('Office Supplies (Others)'),
('Courier - Registered Mail'),
('Courier - Accredited Private Courier'),
('Courier - Express - Grab/Lalamove/Joyride'),
('Transpo - Gas'),
('Transpo - Toll'),
('Transpo - Driver'),
('Transpo - Parking'),
('Transpo - Vehicle - Rental'),
('Meal'),
('Notary - Jurat'),
('Notary - Acknowledgment'),
('Notary - Sale/Transfer'),
('Liason & Messengerial'),
('Certification/Document Fee'),
('Application/Registration Fee'),
('Assessment Fee'),
('Representation'),
('Psychiatrist/Psychologist'),
('Surveyor'),
('Auditor/Accountant'),
('Others - Professional'),
('Others');

DROP TABLE `gl_legal_activity_category`;
CREATE TABLE `gl_legal_activity_category` (
	`legal_activity_id` int NOT NULL AUTO_INCREMENT,
    `legal_activity` varchar(100) NOT NULL,
    PRIMARY KEY(`legal_activity_id`)
);

INSERT INTO `gl_legal_activity_category` (`legal_activity`) VALUES
('Acceptance'),
('Service'),
('Professional'),
('Meeting/Consultation'),
('Appearance'),
('Pleadings'),
('Preparation of Legal/Business Document'),
('Others');

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

ALTER TABLE `bank_accounts` 
DROP INDEX `account_number`, 
MODIFY `account_number` varchar(255) NOT NULL;

ALTER TABLE `bank_accounts` 
MODIFY `account_number` varchar(100) UNIQUE NOT NULL;


ALTER TABLE `bank_accounts`
MODIFY `account_balance` decimal(10,2) NULL DEFAULT 0.00;

INSERT INTO `bank_accounts` (`bank_id`, `account_holder_name`, `account_number`)
VALUES (10, 'John Doe', '1234567890');

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
