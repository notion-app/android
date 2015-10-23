package com.tylorgarrett.notion.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tylorgarrett on 9/30/15.
 */
public class School {
    String id;
    String name;
    String location;

    public School(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}