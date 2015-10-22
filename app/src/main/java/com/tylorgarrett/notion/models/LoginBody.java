package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 10/21/15.
 */
public class LoginBody {
    String auth_method;
    String access_token;

    public LoginBody(String auth_method, String access_token) {
        this.auth_method = auth_method;
        this.access_token = access_token;
    }

    public String getAuth_method() {
        return auth_method;
    }

    public void setAuth_method(String auth_method) {
        this.auth_method = auth_method;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
