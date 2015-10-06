package com.tylorgarrett.notion.models;

import java.util.List;

/**
 * Created by tylorgarrett on 9/30/15.
 */
public class Course {
    private School school;
    private String courseTitle;
    private String courseNumber;
    private Notebook courseNotebook;
    private String instructor;

    public Course(String title, String instructor) {
        this.courseTitle = title;
        this.instructor = instructor;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Notebook getCourseNotebook() {
        return courseNotebook;
    }

    public void setCourseNotebook(Notebook courseNotebook) {
        this.courseNotebook = courseNotebook;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
