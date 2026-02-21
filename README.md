# API Automation Framework

An enterprise-grade, production-ready API automation framework built using Java 17, Maven, RestAssured, and TestNG.

## 🚀 Overview

This framework is designed for high scalability and robustness in testing RESTful APIs. It includes modular components for core logic, service layers, security, and utility functions.

## 🛠️ Tech Stack

- **Language**: Java 17
- **Build Tool**: Maven
- **Rest Client**: Rest-Assured
- **Test Runner**: TestNG
- **Reporting**: Allure
- **Logging**: Log4j2
- **Database**: PostgreSQL (Optional result persistence)

## 🏗️ Key Features

- **Multi-Environment Support**: Easily switch between dev, qa, stage, and prod via config properties.
- **Auto-Token Refresh**: Dynamic handling of OAuth2 tokens to ensure uninterrupted test execution.
- **Centralized Request Builder**: Standardized API request configuration.
- **Database Logging**: Integrated PostgreSQL support to store test execution results.
- **Retry Logic**: Built-in RetryAnalyzer to handle flaky tests.
- **Postman Support**: Ability to execute Postman collection JSON files via CLI.
- **Parallel Execution**: Configured for high-performance concurrent test runs.

## 📖 Documentation

For detailed class definitions and architecture details, please refer to [doc.md](doc.md).

## 🏃 Running the Project

### Execute via Maven
```bash
mvn clean test -Denv=qa
```

### Generate Allure Report
```bash
allure serve allure-results
```

### CLI Mode (Collection Execution)
```bash
java -cp target/api-framework.jar com.framework.cli.MainRunner --collection path/to/collection.json --env prod
```