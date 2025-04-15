# Setup Guide
You need to have Maven installed on your machine to build the project. If you do not have Maven yet, follow these steps to install Maven:

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

4. **Build the Project**:
    -  Run the following command to **download dependencies** and **build the project**:

        ```bash
        mvn clean install -U
        ```
    - This will download all dependencies specified in `pom.xml` (including the MySQL connector), and then compile and package your project.