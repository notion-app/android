package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class Notebook {
    private String notebookTitle;
    private String id;

    public Notebook(String notebookTitle) {
        this.notebookTitle = notebookTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotebookTitle() {
        return notebookTitle;
    }

    public void setNotebookTitle(String notebookTitle) {
        this.notebookTitle = notebookTitle;
    }
}
