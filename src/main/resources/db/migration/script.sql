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
   skill VARCHAR(255) NOT NULL,
   FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE projects (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   description VARCHAR(255) NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users(id)
);


-- Create user_projects table to establish many-to-many relationship
CREATE TABLE user_projects (
    user_id INT,
    project_id INT,
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);
