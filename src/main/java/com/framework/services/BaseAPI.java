package com.framework.services;

import com.framework.core.RequestBuilder;
import com.framework.security.TokenManager;
import io.restassured.specification.RequestSpecification;

public abstract class BaseAPI {

    /**
     * This method creates a "Base Request" that already includes
     * the "Authorization: Bearer <token>" header automatically.
     * 
     * Any class that extends BaseAPI can use this to make authenticated calls.
     */
    protected RequestSpecification getAuthenticatedSpec() {
        String token = TokenManager.getAccessToken();

        return new RequestBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
