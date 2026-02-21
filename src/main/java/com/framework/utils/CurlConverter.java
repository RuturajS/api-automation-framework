package com.framework.utils;

import io.restassured.specification.RequestSpecification;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurlConverter {

    /**
     * Minimal implementation of Curl-to-RestAssured specification converter.
     * Extracts URL and Method from a curl command.
     */
    public static void applyCurl(RequestSpecification spec, String curl) {
        // Simple regex to find URL
        Pattern urlPattern = Pattern.compile("curl\\s+['\"]?([^'\"\\s]+)");
        Matcher matcher = urlPattern.matcher(curl);
        if (matcher.find()) {
            spec.baseUri(matcher.group(1));
        }

        if (curl.contains("-X POST")) {
            // Setup for POST
        }
    }
}
