package com.tylorgarrett.notion.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by tylorgarrett on 9/15/15.
 */
public class Notebook {
    private String id;
    private String user_id;
    private String notebook_id;
    private String name;
    private Course course;
    private Section section;

    private List<Topic> topics;

    private List<Note> notes;

    public Notebook(String id, String user_id, String notebook_id, Course course, Section section) {
        this.id = id;
        this.user_id = user_id;
        this.notebook_id = notebook_id;
        this.course = course;
        this.section = section;
        this.notes = new ArrayList<Note>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id;
    }

    public int getNoteCount(){
        return getNotes().size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Note> getNotes() {
        notes = new ArrayList<>();
        if ( topics == null ){
            return notes;
        }
        for ( Topic topic : topics ){
            for (Note n: topic.getNotes() ){
                notes.add(n);
            }
        }
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getDescription(){
        String result = "";
        result += "\t\t\t" + getCourse().getName() + "\n";
        result += "\t\t\tSection " + getSection().getCrn() + "\n";
        result += "\t\t\t" + getSection().getProfessor() + " " + getSection().getTime();
        return result;
    }
}
