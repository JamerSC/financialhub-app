#CREATE DATABASE `test_financial_hub_db`;
#CREATE DATABASE IF NOT EXISTS `test_financial_hub_db`;
#USE `test_financial_hub_db`;

DROP TABLE `users`;
CREATE TABLE `users` (
	`id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `middle_name` varchar(255) NOT NULL,
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
    FOREIGN KEY (`ROLE_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- sample data

-- Insert roles
INSERT INTO `roles` (`name`) VALUES ('ROLE_EMPLOYEE');
INSERT INTO `roles` (`name`) VALUES ('ROLE_MANAGER');
INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');

-- Insert users (passwords should be encoded using BCrypt, below are plaintext for demonstration)
-- password: 123123 {bcrypt}$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq
INSERT INTO `users` (`first_name`, `last_name`, `middle_name`, `email`, `username`, `password`, `enabled`) VALUES ('John', 'Doe', 'Eod', 'john@mail.com', 'john', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true);
INSERT INTO `users` (`first_name`, `last_name`, `middle_name`, `email`, `username`, `password`, `enabled`) VALUES ('Mary', 'Public', 'Private', 'mary@mail.com', 'mary', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true);
INSERT INTO `users` (`first_name`, `last_name`, `middle_name`, `email`, `username`, `password`, `enabled`) VALUES ('Susan', 'Sun Tzu', 'Yinyang', 'susan@mail.com','susan', '$2a$10$LGHqAThaYY3yZW0qIiSZoejcr.BUUNex8YKo69DdNhIndLMhTiDWq', true);

-- Assign roles to users
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1); -- Employee
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1); -- Employee
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2); -- Manager
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1); -- Employee
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2); -- Manager
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3); -- Admin

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