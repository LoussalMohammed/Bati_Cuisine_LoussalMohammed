CREATE TYPE userType AS ENUM ('USER', 'ADMIN');

CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(255),
    lastName  VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(255) UNIQUE,
    userType userType DEFAULT 'USER',
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users(firstName, lastName, email, phone, password) VALUES ('Mohammed', 'Loussal', 'loussalmohammed@gmail.com', '0619827480', '$2a$10$jX92CYgZqJXANRceLpjtuetGrIYzeAouJZBaPhqxlciyUgo5XHhe6');