# Quizopia Backend

This repository contains the backend code for our Quiz Application, Quizopia.


# Setup
To run the backend server, follow these steps:

1. **Clone this repository to your local machine**:
```shell
git clone https://github.com/heikkkk/IDATT2105-Semester-Project-Backend.git
```
2. **Ensure you have Java (v17), Maven, MySQL, Flyway, and Docker installed.**
3. **Navigate to the project directory in your terminal.**
4. **Install dependencies and build the project**:
```shell
mvn clean install
```
5. **Start the backend server**:
```shell
mvn spring-boot:run
```

# Prerequisites

Before you can run the application, ensure you have the following software installed on your system:

- **Java (v17)**:The application is built using Java, so you'll need the JDK to compile and run it. [Download Java JDK v17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven**:Maven is used for dependency management and to build the application. [Download Maven](https://maven.apache.org/download.cgi)
- **MySQL**:The application uses MySQL as its database. [Download MySQL](https://dev.mysql.com/downloads/mysql/)
- **Docker**:Docker is used for creating containers for the application and database.[ Download Docker](https://www.docker.com/get-started)

# Configuration

## Configuring Database:
Before running the Quizopia Backend application, you need to configure it to connect to your MySQL database. This is done by editing the **`application.yml`** file in the **`src/main/resources`** directory.

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://<your-database-url>:<port>/<your-database-name>?allowPublicKeyRetrieval=true
    username: <your-database-username>
    password: <your-database-password>
```
Replace `<your-database-url>`, `<port>`, `<your-database-name>`, `<your-database-username>`, and `<your-database-password>` with your actual MySQL database details.

## Configuring Flyway for Database:
This step here is necessary to make it easier to configure Flyway to fix migration issues with your database. This is done by editing the **`pom.xml`** file the **`root`** directory.
```xml
<profiles>
    <profile>
        <id>production</id>
        <activation>
            <activeByDefault>false</activeByDefault>
        </activation>
        <properties>
            <flyway.url>jdbc:mysql://<your-database-url>:<port>/<your-database-name>?allowPublicKeyRetrieval=true</flyway.url>
            <flyway.user><your-database-username></flyway.user>
            <flyway.password><your-database-password></flyway.password>
            <flyway.locations>classpath:db/migration</flyway.locations>
        </properties>
        <dependencies>
            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-j</artifactId>
                <scope>runtime</scope>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```
Replace `<your-database-url>`, `<port>`, `<your-database-name>`, `<your-database-username>`, and `<your-database-password>` with your actual MySQL database details. This makes it possible to run mvn flyway:repair -P production to fix any migration issues with your test database.


## Configuring Test Database (Optional):
If you also wanna be able to run tests, you need to configure it to connect to a MySQL database that will be used for the tests. This is done by editing the **`application-test.yml`** file in the **`src/test/resources`** directory.

```yaml
spring:
    datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: ${DB_URL:jdbc:mysql://<your-test-database-url>:<port>/<your-test-database-name>?allowPublicKeyRetrieval=true}
        username: ${DB_USERNAME:<your-test-database-username>} 
        password: ${DB_PASSWORD:<your-test-database-password>}
```
Replace `<your-test-database-url>`, `<port>`, `<your-test-database-name>`, `<your-test-database-username>`, and `<your-test-database-password>` with your actual MySQL test database details.

## Configuring Flyway for Test Database (Optional):
This step here is necessary to make it easier to configure Flyway to fix migration issues with your test database. This is done by editing the **`pom.xml`** file in the **`root`** directory.
```xml
<profiles>
    <profile>
        <id>test</id>
        <activation>
            <activeByDefault>false</activeByDefault>
        </activation>
        <properties>
            <flyway.url>jdbc:mysql://<your-test-database-url>:<port>/<your-test-database-name>?allowPublicKeyRetrieval=true</flyway.url>
            <flyway.user><your-test-database-username></flyway.user>
            <flyway.password><your-test-database-password></flyway.password>
            <flyway.locations>classpath:db/migration</flyway.locations>
        </properties>
        <dependencies>
            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-j</artifactId>
                <scope>runtime</scope>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```
Replace `<your-test-database-url>`, `<port>`, `<your-test-database-name>`, `<your-test-database-username>`, and `<your-test-database-password>` with your actual MySQL test database details. This makes it possible to run mvn flyway:repair -P test to fix any migration issues with your test database.

# Database Setup
Our application automatically runs Flyway migrations when you start the application or run tests. This process applies the necessary SQL scripts to create or update the database schema.

# Testing
1. **Install dependencies and build the project (if you haven't already done so)**:
```shell
mvn clean install
```
2. **Run all tests against your MySQL test database**:
```shell
mvn test
```
3. **To test a specific method (Optional)**:
```shell
mvn -Dtest=YourTestClass#YourTestMethod test
```
Replace `YourTestClass` with the name of the test class and `YourTestMethod` with the name of the method you wanna test.

4. **To test E2E tests locally up against your MySQL test database (Optional)**:
```shell
mvn -Dtest=YourTestClass#YourTestMethod test
```

# Common Issues and Troubleshooting
During the development and operation of Quizopia, you may encounter various issues. Below are some of the common problems and their solutions:

## Migration Fault
**Issue**: The schema in the database does not match the one locally, leading to inconsistencies and potential application errors.

**Solution**: Use Flyway's repair feature:

```shell
mvn flyway:repair -P production
```

Similarily, if you are having Migration Faults with the test database:

```shell
mvn flyway:repair -P test
```
## Communication Fault
**Issue**: The application fails to connect to the database, indicating a possible misconfiguration.

**Solution**: Ensure your database is correctly configured. Common misconfigurations include incorrect database URL, port, username, or password. Double-check your configurations.

# CI/CD - Github Actions
## CI
Tests are run continuously with the Continuous Integration pipeline through Github Actions. The pipeline executes Maven commands to build the application and run tests. There is also another workflow that makes the e2e workflow on our frontend application run: https://github.com/heikkkk/IDATT2105-Semester-Project-Frontend. Run the index.html file found at: **`/target/site/jacoco/index.html`** to view the test coverage. 

## CD
The Continuous Deployment pipeline automates the deployment of the Quizopia backend application to GitHub Packages whenever changes are pushed to the main branch. The deployment to GitHub Packages allows for seamless distribution and versioning of the application, ensuring that the most current and stable version is always available for deployment and integration.

# Contributors
- Henrik Teksle Sandok
- Jens Christian Aanestad
- Andreas Kluge Svendsrud
- Anders Høvik
