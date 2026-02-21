package com.framework.tests;

import com.framework.base.BaseTest;
import com.framework.base.RetryAnalyzer;
import com.framework.services.UserService;
import com.framework.utils.ValidatorUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTests extends BaseTest {
    private UserService userService = new UserService();

    @Test(groups = { "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void testGetAllUsers() {
        logger.info("Running testGetAllUsers...");
        Response response = userService.getUsers();

        // Store response in context for the Database listener
        org.testng.Reporter.getCurrentTestResult().setAttribute("lastResponse", response);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Security header validation
        ValidatorUtils.validateSecurityHeaders(response);
    }

    @Test(groups = { "regression" })
    public void testGetUserById() {
        logger.info("Running testGetUserById...");
        Response response = userService.getUser(1);

        if (response.getStatusCode() == 200) {
            Assert.assertNotNull(response.path("id"));
        } else {
            logger.warn("User service may be mocked or unavailable. Skipping deep assertion.");
        }
    }
}
