package com.framework.services;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserService extends BaseAPI {
    private static final String USERS_ENDPOINT = "/users";

    public Response getUsers() {
        return given().spec(getRequestSpec())
                .get(USERS_ENDPOINT);
    }

    public Response getUser(int userId) {
        return given().spec(getRequestSpec())
                .pathParam("id", userId)
                .get(USERS_ENDPOINT + "/{id}");
    }

    public Response createUser(Object user) {
        return given().spec(getRequestSpec())
                .body(user)
                .post(USERS_ENDPOINT);
    }
}
