package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 11/2/15.
 */
public class SubscriptionDeleteBody {
    private String notebook_id;

    public SubscriptionDeleteBody(String notebook_id) {
        this.notebook_id = notebook_id;
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id;
    }
}
