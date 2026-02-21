package com.framework.utils;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ValidatorUtils {

    public static void validateSchema(Response response, String schemaPath) {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/" + schemaPath));
    }

    public static void validateSecurityHeaders(Response response) {
        response.then().assertThat()
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .header("Content-Security-Policy", org.hamcrest.Matchers.notNullValue());
    }
}
