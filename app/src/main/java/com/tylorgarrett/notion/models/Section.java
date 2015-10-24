package com.tylorgarrett.notion.models;

/**
 * Created by tylorgarrett on 10/24/15.
 */
public class Section {
    private String id;
    private String course_id;
    private String notebook_id;
    private String crn;
    private String professor;
    private String year;
    private String semester;
    private String time;
    private boolean verified;

    public Section(String id, String course_id, String notebook_id, String crn, String professor, String year, String semester, String time, boolean verified) {
        this.id = id;
        this.course_id = course_id;
        this.notebook_id = notebook_id;
        this.crn = crn;
        this.professor = professor;
        this.year = year;
        this.semester = semester;
        this.time = time;
        this.verified = verified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getNotebook_id() {
        return notebook_id;
    }

    public void setNotebook_id(String notebook_id) {
        this.notebook_id = notebook_id;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
