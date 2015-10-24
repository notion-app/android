package com.tylorgarrett.notion.models;

import java.util.List;

/**
 * Created by tylorgarrett on 10/24/15.
 */
public class Topic {
    private String id;
    private List<Note> notes;

    public Topic(String id, List<Note> notes) {
        this.id = id;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
