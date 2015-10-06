package com.tylorgarrett.notion.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tylorgarrett on 9/30/15.
 */
public class School {
    private List<Course> courses;
    private String title;

    public School(String title){
        courses = new ArrayList<Course>();
        this.title = title;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course c) {
        courses.add(c);
        c.setSchool(this);
    }
    
    public String getTitle() {
        return title;
    }

}