# SQL Database Setup

```sql
CREATE DATABASE db0321;
USE db0321;

CREATE TABLE inventory (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(50) NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    item_type VARCHAR(50),
    descr TEXT,
    location VARCHAR(100),
    stock INT DEFAULT 0
);