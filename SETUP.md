# How to run this project on your computer

I wrote this guide to help anyone who wants to test this project but might be new to setting up a database or using Eclipse. This was the exact process I used to get everything working on my machine.

## 1. Things you need to install
You can't run the code without these. I used the latest versions:
* **Java JDK 21:** The engine that runs the code.
* **MySQL Workbench:** Where the "Users" and "Tickets" are stored.
* **Eclipse IDE:** The program I used to write and run the code.

## 2. Setting up the Database (Important!)
The app will crash if the database isn't ready first. 
1. Open **MySQL Workbench** and log in.
2. Open a new SQL tab and paste the code below. 
3. **Note:** I included a default Admin account in the script so you can log in immediately.

```sql
CREATE DATABASE queue_system;
USE queue_system;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255),
    role VARCHAR(20)
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    concern TEXT,
    qNum VARCHAR(20),
    date VARCHAR(20),
    time VARCHAR(20),
    priority VARCHAR(20),
    status VARCHAR(20)
);

-- Default Admin (User: admin | Pass: admin123)
INSERT INTO users VALUES ('admin', '$2a$10$8K1p/a0dxv5QU1p.mS7p7y6K5M9Z3A1R/f7LzYf9nKqS9iO', 'ADMIN');
