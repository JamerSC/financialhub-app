DROP DATABASE IF EXISTS `testdb`;
CREATE DATABASE `testdb`;
USE `testdb`;

DROP DATABASE testdb;

INSERT INTO `contacts` (`contact_type`, `category_type`, `engagement_date`, `best_channel_to_contact`) 
VALUES ('INDIVIDUAL', 'INTERNAL', null, null);

INSERT INTO `contact_individual` (`contact_id`, `title`, `last_name`, `first_name`, `middle_name`, `suffix`, `mobile_number`, `email_address`, `address`) 
VALUES (1, '', 'Administrator', 'GL', '', 'J', '000000000', 'admin@mail.com', 'Lorem ipsum');

-- Insert additional details for a Company/Vendor/Internal (using the contact_id from the previous insert)
INSERT INTO `contact_additional_details` (`contact_id`, `designation_for`, `bank_name`, `account_no`) 
VALUES (1, '', '', '');

INSERT INTO `roles` (`name`) VALUES ('ROLE_SUPER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (`name`) VALUES ('ROLE_MANAGER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_LEGAL');
INSERT INTO `roles` (`name`) VALUES ('ROLE_EMPLOYEE');

-- Sample data for `contact_users`
INSERT INTO `contact_users` (`contact_id`, `full_name`, `username`, `password`, `enabled`) VALUES
(1, 'GL Admininstrator', 'admin', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true);

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1); -- Super
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2); -- Admin
INSERT INTO users_roles (user_id, role_id) VALUES (1, 3); -- Manager
INSERT INTO users_roles (user_id, role_id) VALUES (1, 4); -- Legal
INSERT INTO users_roles (user_id, role_id) VALUES (1, 5); -- Employee

DELETE FROM user_roles WHERE user_id = 1 AND role_id = 5;

SELECT * FROM users_roles WHERE user_id = 1 AND role_id = 5;

DELETE FROM users_roles WHERE user_id = 1 AND role_id = 5;


## FOR BETA VERSION

DROP DATABASE IF EXISTS `financialhub_beta_db`;
CREATE DATABASE `financialhub_beta_db`;

INSERT INTO `contacts` (`contact_type`, `category_type`, `engagement_date`, `best_channel_to_contact`) 
VALUES ('INDIVIDUAL', 'INTERNAL', null, null);

INSERT INTO `roles` (`name`) VALUES ('ROLE_SUPER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (`name`) VALUES ('ROLE_MANAGER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_LEGAL');
INSERT INTO `roles` (`name`) VALUES ('ROLE_EMPLOYEE');

-- Sample data for `contact_users`
INSERT INTO `contact_users` (`contact_id`, `full_name`, `username`, `password`, `enabled`) VALUES
(1, 'GL Admininstrator', 'admin', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true);

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1); -- Super
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2); -- Admin
INSERT INTO users_roles (user_id, role_id) VALUES (1, 3); -- Manager
INSERT INTO users_roles (user_id, role_id) VALUES (1, 4); -- Legal
INSERT INTO users_roles (user_id, role_id) VALUES (1, 5); -- Employee

INSERT INTO `fund`(`fund_balance`, `created_by`, `updated_by` ) VALUE (0.00, 1, 1);