package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class School {
    private String name;
    private String location;
    private String id;

    public School(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
