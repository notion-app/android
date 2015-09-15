package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 9/15/15.
 */
public class Class {
    private String id;
    private String semester;
    private String professor;
    private String time;

    public Class(String semester, String time, String professor) {
        this.semester = semester;
        this.time = time;
        this.professor = professor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
