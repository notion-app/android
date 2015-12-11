package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 12/10/15.
 */
public class WebSocketRequestBody {
    String type;
    Recommendation recommendation;

    public WebSocketRequestBody(String type, Recommendation recommendation) {
        this.type = type;
        this.recommendation = recommendation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }
}
