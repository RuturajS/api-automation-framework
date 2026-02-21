package com.framework.base;

import com.framework.utils.DatabaseManager;
import io.restassured.response.Response;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestExecutionListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        logToDatabase(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logToDatabase(result, "FAILED");
    }

    private void logToDatabase(ITestResult result, String status) {
        // This is a simplified example. In a real scenario, you'd capture the Response
        // object
        // from a ThreadLocal or via an attribute if attached to the result.
        Object responseObj = result.getAttribute("lastResponse");
        if (responseObj instanceof Response) {
            Response response = (Response) responseObj;
            DatabaseManager.logExecutionDetails(
                    result.getName(),
                    "N/A", // Method and URL could be extracted from response or request spec
                    response.getHeader("Location") != null ? response.getHeader("Location") : "N/A",
                    response.getStatusCode(),
                    response.getBody().asPrettyString());
        }
    }
}
