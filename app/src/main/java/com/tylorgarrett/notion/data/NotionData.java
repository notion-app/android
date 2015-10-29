package com.tylorgarrett.notion.data;

import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.School;
import com.tylorgarrett.notion.models.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by tylorgarrett on 9/29/15.
 */
public class NotionData {
    private List<Notebook> notebooks;
    private List<Note> notes;
    private List<School> schools;
    private List<Course> courses;
    private Map<Course, List<Section>> sections;

    private static NotionData instance = null;

    public NotionData(){
        notebooks = new ArrayList<Notebook>();
        notes = new ArrayList<Note>();
        sections = new HashMap<Course, List<Section>>();
    }

    public static NotionData getInstance(){
        if ( instance == null ){
            instance = new NotionData();
        }
        return instance;
    }

    public void addNotebook(Notebook notebook){
        notebooks.add(notebook);
    }

    public List<Notebook> getNotebooks(){
        return notebooks;
    }

    public List<Note> getNotes(){
        for (Notebook notebook: notebooks){
            notes.addAll(notebook.getNotes());
        }
        return notes;
    }

    public Notebook getNotebookById(String id){
        for ( Notebook notebook : notebooks ){
            if ( notebook.getName().equals(id) ){
                return notebook;
            }
        }
        return null;
    }

    public Note getNoteById(String noteID, String notebookID){
        Notebook notebook = getNotebookById(notebookID);
        for (Note note: notebook.getNotes() ){
            if ( note.getTitle().equals(noteID) ){
                return note;
            }
        }
        return null;
    }

    public void setNotebooks(List notebooks){
        this.notebooks = notebooks;
    }

    public void clearOut(){
        notebooks = null;
        notes = null;
        notebooks = new ArrayList<Notebook>();
        notes = new ArrayList<Note>();
    }

    public List<String> getNotebookNames(){
        List<String> notebookNames = new ArrayList<String>(notebooks.size());
        for (Notebook n: notebooks){
            notebookNames.add(n.getName());
        }
        return notebookNames;
    }

    public List<String> getCourseNames(){
        List<String> courseNames = new ArrayList<String>(courses.size());
        for ( Course course : courses ){
            courseNames.add(course.getName());
        }
        return courseNames;
    }
    public List<String> getSectionNames(Course course){
        List<String> sectionNames = new ArrayList<String>();
        List<Section> listOfSections = sections.get(course);
        for (Section s : listOfSections ){
            sectionNames.add(s.getCrn());
        }
        return sectionNames;
    }

    public Course getCourseByName(String name){
        for (Course c : courses ){
            if (c.getName().equals(name) ){
                return c;
            }
        }
        return null;
    }


    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Map<Course, List<Section>> getSections() {
        return sections;
    }

    public void setSections(Map<Course, List<Section>> sections) {
        this.sections = sections;
    }
}
