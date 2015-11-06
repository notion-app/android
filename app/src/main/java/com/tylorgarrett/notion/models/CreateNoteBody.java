package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 11/6/15.
 */
public class CreateNoteBody {
    private String title;
    private String topic_id;

    public CreateNoteBody(String title, String topic_id) {
        this.title = title;
        this.topic_id = topic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
