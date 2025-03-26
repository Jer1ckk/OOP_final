DROP DATABASE IF EXISTS bank_management_system;

CREATE DATABASE bank_management_system;

USE bank_management_system;

CREATE TABLE account_request (
	id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) UNIQUE KEY,
    password VARCHAR(255) NOT NULL,
    request_date DATE NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE customers (
	id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) UNIQUE KEY,
    password VARCHAR(255) NOT NULL,
    account_type VARCHAR(20) NOT NULL
);

CREATE TABLE staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    account_type VARCHAR(20) NOT NULL
);

CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(15,2) DEFAULT 0.00,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

INSERT INTO staff (first_name, last_name, email, phone_number, password, account_type) VALUES
('Ly', 'Dara', 'daraboy', '123456789', '1234', 'Staff');


