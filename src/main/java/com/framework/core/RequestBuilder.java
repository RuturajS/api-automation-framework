package com.framework.core;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class RequestBuilder {
    private RequestSpecBuilder builder;

    public RequestBuilder() {
        builder = new RequestSpecBuilder();
        builder.setBaseUri(ConfigReader.get("base.url"));
        builder.setContentType(ContentType.JSON);
        builder.addFilter(new AllureRestAssured()); // For Allure reporting
        // Add default headers if any
    }

    public RequestBuilder addHeader(String key, String value) {
        builder.addHeader(key, value);
        return this;
    }

    public RequestBuilder addHeaders(Map<String, String> headers) {
        builder.addHeaders(headers);
        return this;
    }

    public RequestBuilder addQueryParam(String key, String value) {
        builder.addQueryParam(key, value);
        return this;
    }

    public RequestBuilder setBody(Object body) {
        builder.setBody(body);
        return this;
    }

    public RequestSpecification build() {
        return builder.build();
    }
}
