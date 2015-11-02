package com.tylorgarrett.notion.models;

import java.util.List;

/**
 * Created by tylorgarrett on 9/30/15.
 */
public class Course {
    private String id;
    private String name;
    private String number;

    public Course(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
