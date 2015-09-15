package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class User {
    private String id;

    private String authMethod;
    private String authToken;

    private String userName;

    public User(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
