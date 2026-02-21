package com.framework.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;

public class PostmanExecutor {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void executeCollection(String filePath) throws IOException {
        JsonNode root = mapper.readTree(new File(filePath));
        JsonNode items = root.get("item");

        for (JsonNode item : items) {
            executeItem(item);
        }
    }

    private static void executeItem(JsonNode item) {
        if (item.has("item")) {
            for (JsonNode subItem : item.get("item")) {
                executeItem(subItem);
            }
        } else {
            String name = item.get("name").asText();
            JsonNode request = item.get("request");
            String method = request.get("method").asText();
            String url = request.get("url").get("raw").asText();

            System.out.println("Executing: " + name + " [" + method + "] " + url);

            RequestSpecification spec = RestAssured.given().baseUri(url);
            // This is a simplified executor. A full version would handle headers, body,
            // variables, etc.
            Response response = spec.request(method);
            System.out.println("Status: " + response.getStatusCode());
        }
    }
}
