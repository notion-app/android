package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class Note {
    private String title;
    private String notebookName;
    private StringBuffer content;

    public Note(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setNotebookName(String notebookName){
        this.notebookName = notebookName;
    }

    public String getNotebookName(){
        return notebookName;
    }

    public StringBuffer getContent() {
        return content;
    }

    public void setContent(StringBuffer content) {
        this.content = content;
    }
}
