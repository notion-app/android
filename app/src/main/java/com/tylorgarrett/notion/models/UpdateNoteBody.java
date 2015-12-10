package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 12/10/15.
 */
public class UpdateNoteBody {
    String content;

    public UpdateNoteBody(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
