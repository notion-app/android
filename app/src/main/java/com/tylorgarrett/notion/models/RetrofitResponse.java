package com.tylorgarrett.notion.models;

/*
 * Created by tylorgarrett on 10/26/15.
 */
public class RetrofitResponse {
    private String code;
    private String message;

    public RetrofitResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
