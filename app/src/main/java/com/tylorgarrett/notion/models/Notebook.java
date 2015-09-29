package com.tylorgarrett.notion.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class Notebook {
    private List<Note> notes;
    private String title;

    public Notebook(String title) {
        this.title = title;
        notes = new ArrayList<Note>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public int getNoteCount(){
        return notes.size();
    }
}
