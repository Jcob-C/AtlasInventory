# Setup Guide

## MySQL

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
```

## Maven

You need to have Maven installed on your machine to manage project dependencies and build the project. Follow these steps to install Maven:

1. **Download Maven**:
   - Go to the official Maven download page: [Apache Maven Downloads](https://maven.apache.org/download.cgi).
   - Download the **Binary zip archive**.

2. **Extract the Maven Files**:
   - Extract the downloaded ZIP file to a directory of your choice. For example: `C:\Program Files\Apache\Maven`.

3. **Set Up Environment Variables**:
   - **MAVEN_HOME**:
     - Open **Environment Variables** on your system (Search for *Environment Variables* in Windows).
     - Under **System variables**, click **New** and set:
       - **Name**: `MAVEN_HOME`
       - **Value**: The path where Maven was extracted (e.g., `C:\Program Files\Apache\Maven\apache-maven-3.9.9`).
   - **Add Maven to `PATH`**:
     - Find **Path** in **System variables**, and click **Edit**.
     - Add: `C:\Program Files\Apache\Maven\apache-maven-3.9.9\bin`.

4. **Verify Maven Installation**:
   - Open a **Command Prompt** or **PowerShell** window.
   - Run:
     ```bash
     mvn -v
     ```
     - This should output the Maven version, Java version, and other details, confirming the installation.

5. **Build the Project**

    -  Run the following command to **download dependencies** and **build the project**:

        ```bash
        mvn clean install -U
        ```

    - This will download all dependencies specified in `pom.xml` (including the MySQL connector), and then compile and package your project.