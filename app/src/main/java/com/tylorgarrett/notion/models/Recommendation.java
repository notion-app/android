package com.tylorgarrett.notion.models;

/*
 * Created by tylorgarrett on 12/10/15.
 */
public class Recommendation {
    String id;
    String text;
    String genesis;

    public Recommendation(String id, String text, String genesis) {
        this.id = id;
        this.text = text;
        this.genesis = genesis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGenesis() {
        return genesis;
    }

    public void setGenesis(String genesis) {
        this.genesis = genesis;
    }
}
