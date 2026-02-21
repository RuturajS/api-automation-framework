# 🔐 Authentication & Headers Guide

This guide explains how the framework handles security and headers in a simple, beginner-friendly way.

## 1. How "Bearer Token" Authentication Works
Most modern APIs use **OAuth2 Bearer Tokens**. Think of it like a hotel key card:
1. You show your ID (Client ID & Secret).
2. The hotel gives you a Key Card (The Access Token).
3. Every time you go to a room (an API endpoint), you swipe that card.

### In this Framework:
The `TokenManager.java` class handles the "ID showing" part.
- It calls the `token.url` from your config.
- It receives the `access_token`.
- **Auto-Refresh**: If the token is old, it automatically gets a new one so your tests don't fail.

---

## 2. Passing the Header to Every Request
To avoid writing authentication code in every single test, we use a central method in `BaseAPI.java`.

### The Code Flow:
1. **The Core Builder**: `RequestBuilder.java` sets up common things (like JSON format).
2. **The Security Layer**: `BaseAPI.java` has a method called `getAuthenticatedSpec()`.
   ```java
   protected RequestSpecification getAuthenticatedSpec() {
       String token = TokenManager.getAccessToken(); // Gets the latest token
       return new RequestBuilder()
               .addHeader("Authorization", "Bearer " + token) // Adds the swiped card
               .build();
   }
   ```
3. **The Service Layer**: Classes like `UserService.java` simply use this spec:
   ```java
   public Response getUsers() {
       return given().spec(getAuthenticatedSpec()) // All security is already inside!
               .get("/users");
   }
   ```

---

## 3. Why do we do it this way?
- **Clean Tests**: Your test files only care about checking data, not managing logins.
- **Easy Maintenance**: If the authentication method changes (e.g., from `Bearer` to `ApiKey`), you only change it in **one place** (`BaseAPI.java`).
- **Safety**: The `TokenManager` ensures you always have a valid session without manual intervention.

---

## 4. Beginner Step-by-Step
If you want to add a new API with authentication:
1. Create a new class (e.g., `OrderService.java`) that `extends BaseAPI`.
2. Use `.spec(getAuthenticatedSpec())` in your RestAssured calls.
3. The framework will handle the `Authorization` header, the `Bearer` prefix, and the token refreshing for you!
