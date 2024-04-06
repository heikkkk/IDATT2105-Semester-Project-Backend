# Quizopia Backend

This repository contains the backend code for our Quiz Application (Quizopia).

# Installation
To run the backend server, follow these steps:

1. Clone this repository to your local machine.
2. Make sure you have Java (v17), Maven, MySQL, Flyway, and Docker installed.
3. Navigate to the project directory in your terminal.
4. Run mvn clean install to install dependencies and build the project.
5. Run mvn spring-boot:run to start the backend server.

# Prerequisites

Before you can run the application, ensure you have the following software installed on your system:

- **Java (v17)**: The application is built using Java, so you'll need the JDK to compile and run it. [Download Java JDK v17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven**: Maven is used for dependency management and to build the application. [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL**: The application uses MySQL as its database. [Download MySQL](https://dev.mysql.com/downloads/mysql/)
- **Docker**: Docker is used for creating containers for the application and database. [Download Docker](https://www.docker.com/get-started)

# Configuration
Before running the Quizopia Backend application, you must configure it to connect to your MySQL database and set up other application-specific settings. This is done by editing the application.yml file in the src/main/resources directory. 

## Configuring Database Access:

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://<your-database-url>:<port>/<your-database-name>?allowPublicKeyRetrieval=true
    username: <your-database-username>
    password: <your-database-password>
```
Replace `<your-database-url>`, `<port>`, `<your-database-name>`, `<your-database-username>`, and `<your-database-password>` with your actual MySQL database details.

## Configuring Test Database Access:
```yaml
spring:
    datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: ${DB_URL:jdbc:mysql://<your-test-database-url>:<port>/<your-test-database-name>?allowPublicKeyRetrieval=true}
        username: ${DB_USERNAME:<your-test-database-username>} 
        password: ${DB_PASSWORD:<your-test-database-password>}
```
Replace `<your-database-url>`, `<port>`, `<your-database-name>`, `<your-database-username>`, and `<your-database-password>` with your actual MySQL test database details.

# Database Setup
Our application is configured to automatically run Flyway migrations when you start your application or run your tests. This means that Flyway will automatically apply the necessary SQL scripts to create or update the database schema.
To run your application, execute mvn spring-boot:run from the command line in the root directory of your project. This starts your Spring Boot application and triggers Flyway migrations.
Similarly, running tests with mvn test will also trigger Flyway migrations against the test database configured in your application properties. Seed Data is also included in this.

# Testing
1. Run mvn clean install to install dependencies and build the project (if you havent already done that)
2. Run mvn test. This will run all tests up against your MySQL test database.
3. (Optionally) If you just wanna test out a specific method run the command mvn -Dtest=YourTestClass#YourTestMethod test. Replace `YourTestClass` with the name of the test class and `YourTestMethod` with the name of the method you wanna test.

# Common Issues and Troubleshooting
During the development and operation of Quizopia, you may encounter various issues. Below are some of the common problems and their solutions:

**Issue**: The schema in the database does not match the one locally, leading to inconsistencies and potential application errors.

**Solution**: To resolve schema inconsistencies, you can use Flyway's repair feature. Run the following command in your terminal:

```shell
mvn flyway:repair
```

**Issue**: The application fails to connect to the database, indicating a possible misconfiguration in the database settings.

**Solution**: Ensure that your database is configured correctly. Common misconfigurations include incorrect database URL, port, username, or password in the application.yml file. Double-check the following configurations:
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://<your-database-url>:<port>/<your-database-name>?allowPublicKeyRetrieval=true
    username: <your-database-username>
    password: <your-database-password>
```
