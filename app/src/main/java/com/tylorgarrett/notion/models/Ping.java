package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 12/10/15.
 */
public class Ping {
    String type;

    public Ping(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
