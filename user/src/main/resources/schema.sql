DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id   LONG AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);
