# How to run this project on your computer

I wrote this guide to help anyone who wants to test this project but might be new to setting up a database or using Eclipse. This was the exact process I used to get everything working on my machine.

## 1. Things you need to install
You can't run the code without these. I used the latest versions:
* **Java JDK 21:** The engine that runs the code.
* **MySQL Community Server & Workbench:** Where the "Users" and "Tickets" are stored.
* **Eclipse IDE:** The program I used to write and run the code.

## 2. Setting up the Database (Important!)
The app will crash if the database isn't ready first. 

1. Open **MySQL Workbench** and log in.
2. Open a new SQL tab.
3. Copy and paste the code below and click the **Lightning Bolt** icon:

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

## 3. Importing to Eclipse
1. Download this project as a ZIP from GitHub and extract it.
2. In Eclipse, go to File > Import > General > Existing Projects into Workspace.
3. Browse to the folder and click Finish.

## 4. Fixing the Library Links
Since this project uses external tools, you have to link them in Eclipse:
1. Right-click the project folder -> Properties.
2. Go to Java Build Path -> Libraries tab.
3. Click Modulepath, then click Add JARs... on the right.
4. Select all the files inside the lib folder of this project.
5. Click Apply and Close.

## 5. Connecting to your MySQL
Everyone has a different MySQL password. You need to tell the app yours:
1. Open the file: src/queue/system/DatabaseConfig.java.
2. Find the PASS variable and change "root" to your own MySQL password.
3. Save the file (Ctrl + S).

## 6. Launching
1. Find Main.java in the src folder.
2. Right-click it and select Run As > Java Application. 

The Dark Mode login screen should appear!
