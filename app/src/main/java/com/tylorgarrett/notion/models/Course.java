package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class Course {
    private String id;
    private String name;

    public Course(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
