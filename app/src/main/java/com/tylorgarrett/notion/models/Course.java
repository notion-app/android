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
}
