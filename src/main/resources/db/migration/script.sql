-- Create users table
CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   first_name VARCHAR(255) NOT NULL,
   middle_name VARCHAR(255),
   last_name VARCHAR(255) NOT NULL,
   phone_number VARCHAR(20),
   date_of_birth VARCHAR(10),
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   profile_photo_path VARCHAR(255),
   is_admin BOOLEAN DEFAULT FALSE,
   experience VARCHAR(255) NOT NULL,
   skill VARCHAR(255) NOT NULL
);
