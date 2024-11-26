CREATE DATABASE `testdb`;
CREATE DATABASE IF NOT EXISTS `testdb`;
USE `testdb`;

DROP DATABASE testdb;

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