package com.framework.base;

import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;

public class RetryAnalyzer extends RetryAnalyzerCount {
    private static final int MAX_RETRY_COUNT = 3;

    public RetryAnalyzer() {
        setCount(MAX_RETRY_COUNT);
    }

    @Override
    public boolean retryMethod(ITestResult result) {
        return !result.isSuccess();
    }
}
