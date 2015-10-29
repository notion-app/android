package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 10/28/15.
 */
public class SubscriptionBody {
    private String notebook_id;
    private String name;

    public SubscriptionBody(String notebook_id, String name) {
        this.notebook_id = notebook_id;
        this.name = name;
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
