package com.framework.utils;

import com.framework.core.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);
    private static Connection connection;

    private static boolean isEnabled() {
        return Boolean.parseBoolean(ConfigReader.get("db.enabled"));
    }

    private synchronized static Connection getConnection() {
        if (!isEnabled())
            return null;

        try {
            if (connection == null || connection.isClosed()) {
                String url = ConfigReader.get("db.url");
                String user = ConfigReader.get("db.user");
                String pass = ConfigReader.get("db.password");
                connection = DriverManager.getConnection(url, user, pass);
                logger.info("Connected to PostgreSQL database: " + url);
            }
        } catch (SQLException e) {
            logger.error("Failed to connect to PostgreSQL. Disabling DB logging for this session.", e);
            // Optionally set db.enabled to false dynamically if connection fails
        }
        return connection;
    }

    public static void logExecutionDetails(String testName, String method, String url, int statusCode,
            String responseBody) {
        if (!isEnabled())
            return;

        Connection conn = getConnection();
        if (conn == null)
            return;

        String query = "INSERT INTO test_executions (test_name, method, url, status_code, response_body, execution_time) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, testName);
            stmt.setString(2, method);
            stmt.setString(3, url);
            stmt.setInt(4, statusCode);
            stmt.setString(5, responseBody);
            stmt.executeUpdate();
            logger.debug("Test execution details logged to DB for " + testName);
        } catch (SQLException e) {
            logger.error("Error logging to database", e);
        }
    }
}
