package com.framework.services;

import com.framework.core.RequestBuilder;
import com.framework.security.TokenManager;
import io.restassured.specification.RequestSpecification;

public abstract class BaseAPI {
    protected RequestSpecification getRequestSpec() {
        return new RequestBuilder()
                .addHeader("Authorization", "Bearer " + TokenManager.getAccessToken())
                .build();
    }
}
