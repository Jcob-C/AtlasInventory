database (url, host, password) are in /src/jcobc/main/Database.java

create database db01;
use db01;

CREATE TABLE staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullName VARCHAR(50) NOT NULL,
    contactNo VARCHAR(15) NOT NULL,
    address VARCHAR(50) NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    pass VARCHAR(20) NOT NULL,
    workRole VARCHAR(20) NOT NULL
);

CREATE TABLE inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(50) UNIQUE NOT NULL, 
    itemName VARCHAR(100) NOT NULL,
    itemLocation VARCHAR(50) NOT NULL,
    itemType VARCHAR(50) NOT NULL,
    descr TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
);

CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sale_datetime DATETIME DEFAULT CURRENT_TIMESTAMP,
    customer_name VARCHAR(50) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE sales_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sale_id INT NOT NULL,
    item_id INT NOT NULL,
    itemName VARCHAR(100) NOT NULL,
    soldPrice DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES inventory(id) ON DELETE CASCADE
);

insert into 
staff (fullName, contactNo, address, username, pass, workRole) 
values ('name','911','hell','admin','pass','admin');