package com.tylorgarrett.notion.data;

import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tylorgarrett on 9/29/15.
 */
public class NotionData {
    private List<Notebook> notebooks;
    private List<Note> notes;

    private static NotionData instance = null;

    public NotionData(){
        notebooks = new ArrayList<Notebook>();
        notes = new ArrayList<Note>();
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
}
