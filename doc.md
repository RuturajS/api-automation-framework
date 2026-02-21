# 📖 API Automation Framework Documentation

This document provides a detailed overview of the framework's architecture, class definitions, and core logic.

## 🏗️ Architecture Overview

The framework follows a **Modular Layered Architecture**, separating concerns into core logic, services (business logic), utilities, and test suites.

---

## 📂 Package: `com.framework.core`

### 1. `ConfigReader.java`
*   **Purpose**: Handles multi-environment configuration management.
*   **How it works**:
    *   Reads properties from `src/main/resources/config/`.
    *   Determines the environment using the `-Denv` system property (default is `dev`).
    *   Provides static helper methods `get(key)` and `getInt(key)` to retrieve configuration values.

### 2. `RequestBuilder.java`
*   **Purpose**: A centralized builder for REST-assured request specifications.
*   **How it works**:
    *   Uses `RequestSpecBuilder` to encapsulate base URLs, headers, and content types.
    *   Automatically attaches the `AllureRestAssured` filter for automated visual reporting.
    *   Returns a `RequestSpecification` that can be passed directly to RestAssured methods.

---

## 📂 Package: `com.framework.security`

### 1. `TokenManager.java`
*   **Purpose**: Manages OAuth2 access tokens with automatic renewal.
*   **How it works**:
    *   Maintains a singleton `accessToken`.
    *   Calculates `expiryTime` based on the `expires_in` field from the auth response.
    *   **Auto-Refresh**: Before providing a token, it checks if it's expired (or about to expire in <60s). If so, it triggers an background refresh request.

---

## 📂 Package: `com.framework.utils`

### 1. `DatabaseManager.java`
*   **Purpose**: Logs API execution details (URL, Status, Body) to a PostgreSQL database.
*   **How it works**:
    *   **Toggle**: Reads `db.enabled` from the environment config. If `false`, database operations are skipped.
    *   **Validation**: Validates the connection before every batch write.
    *   Uses raw JDBC with `PreparedStatement` to store results in the `test_executions` table.

### 2. `ValidatorUtils.java`
*   **Purpose**: Provides reusable assertions for common API standards.
*   **How it works**:
    *   `validateSchema`: Uses RestAssured's JSON Schema Validator to match responses against schemas in `src/main/resources/schemas/`.
    *   `validateSecurityHeaders`: Asserts the presence of mandatory security headers (XSS-Protection, CSP, etc.).

### 3. `PostmanExecutor.java` & `CurlConverter.java`
*   **Purpose**: Bridge the gap between developer tools and automated tests.
*   **How it works**:
    *   `PostmanExecutor` parses JSON collections and iterates through requests to execute them programmatically.
    *   `CurlConverter` uses Regex to extract endpoints and methods from raw curl strings.

---

## 📂 Package: `com.framework.services`

### 1. `BaseAPI.java`
*   **Purpose**: Parent class for all API service modules.
*   **How it works**:
    *   Injects the `Authorization` header automatically using `TokenManager.getAccessToken()`.
    *   Provides a base `RequestSpecification` for all child services (UserService, etc.).

---

## 📂 Package: `com.framework.base` (Test Layer)

### 1. `RetryAnalyzer.java`
*   **Purpose**: Reduces false failures from flaky networks.
*   **How it works**:
    *   Implements TestNG's `IRetryAnalyzer`.
    *   Retries failed test cases up to a configurable limit (default: 3) before marking the test as FAILED.

### 2. `TestExecutionListener.java`
*   **Purpose**: Automated hook for database logging.
*   **How it works**:
    *   Listens to test events (`onTestSuccess`, `onTestFailure`).
    *   Extracts the response object from the test context and passes it to `DatabaseManager` for persistence.

---

## ⚙️ Configuration (`.properties`)

| Key | Description |
|-----|-------------|
| `base.url` | Primary API endpoint |
| `db.enabled` | Set to `true` to enable PostgreSQL logging |
| `db.url` | JDBC connection string |
| `timeout` | Connection timeout in milliseconds |
