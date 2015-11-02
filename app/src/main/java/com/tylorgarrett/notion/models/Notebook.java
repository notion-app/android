package com.tylorgarrett.notion.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class Notebook {
    private String id;
    private String user_id;
    private String notebook_id;
    private String name;

    public Notebook(String id, String user_id, String notebook_id) {
        this.id = id;
        this.user_id = user_id;
        this.notebook_id = notebook_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id;
    }

    public List<Note> getNotes(){
        return null;
    }

    public int getNoteCount(){
        return 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
