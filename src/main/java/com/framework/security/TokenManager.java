package com.framework.security;

import com.framework.core.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TokenManager {
    private static String accessToken;
    private static Instant expiryTime;

    public synchronized static String getAccessToken() {
        if (accessToken == null || isExpired()) {
            refreshAccessToken();
        }
        return accessToken;
    }

    private static boolean isExpired() {
        return expiryTime == null || Instant.now().isAfter(expiryTime.minusSeconds(60));
    }

    private static void refreshAccessToken() {
        System.out.println("Refreshing Access Token...");
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("client_id", ConfigReader.get("client.id"));
        formParams.put("client_secret", ConfigReader.get("client.secret"));

        Response response = RestAssured.given()
                .baseUri(ConfigReader.get("token.url"))
                .formParams(formParams)
                .post();

        if (response.getStatusCode() == 200) {
            accessToken = response.path("access_token");
            int expiresIn = response.path("expires_in");
            expiryTime = Instant.now().plusSeconds(expiresIn);
        } else {
            // Mocking for demonstration if the actual endpoint fails
            accessToken = "mock-token-" + System.currentTimeMillis();
            expiryTime = Instant.now().plusSeconds(3600);
            System.err.println("Token refresh failed. Using mock token.");
        }
    }
}
