package com.tylorgarrett.notion.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.fragments.NotebookNotesFragment;
import com.tylorgarrett.notion.interfaces.NotionAPI;
import com.tylorgarrett.notion.models.CreateNoteBody;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.services.NotionService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/*
 * Created by tylorgarrett on 11/5/15.
 */
public class NewNoteDialog {
    MainActivity mainActivity;
    Notebook notebook;
    List<Note> notes;
    List<Topic> joinableNotes;

    public NewNoteDialog(MainActivity mainActivity, Notebook notebook, List<Note> notes) {
        this.mainActivity = mainActivity;
        this.notebook = notebook;
        this.notes = notes;
        setUpDialog();
    }

    public void setUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Select an Option");
        builder.setPositiveButton("Create Note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewNoteDialog();
            }
        });
        builder.setNegativeButton("Join Note", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getNotesToJoin();
            }
        });
        builder.create().show();
    }

    public void createNewNoteDialog() {
        View v = mainActivity.getLayoutInflater().inflate(R.layout.create_note_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Create Note");
        builder.setView(v);
        final EditText editText = (EditText) v.findViewById(R.id.create_note_textview);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewNote(editText.getText().toString(), null);
            }
        });
        builder.create().show();
    }

    public void joinNoteDialog(final List<String> data) {
        View v = mainActivity.getLayoutInflater().inflate(R.layout.join_note_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Join Note");
        builder.setView(v);
        final Spinner spinner = (Spinner) v.findViewById(R.id.join_note_spinner);
        final EditText editText = (EditText) v.findViewById(R.id.join_note_edittext);
        ArrayAdapter<String> noteNames = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, data);
        spinner.setAdapter(noteNames);
        builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = spinner.getSelectedItemPosition();
                String title = editText.getText().toString();
                Topic topic = getTopicFromPosition(position);
                joinNote(title, topic);
            }
        });
        builder.create().show();
    }

    public void createNewNote(String noteName, String topicID) {
        Call<Topic> call = NotionService.getApi().createNote(mainActivity.getCurrentUser().getFb_auth_token(), notebook.getNotebook_id(), new CreateNoteBody(noteName, topicID));
        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Response<Topic> response, Retrofit retrofit) {
                Topic t = response.body();
                Note n = t.getNotes().get(0);
                notes.add(n);
                notebook.setNotes(notes);
                NotebookNotesFragment f = (NotebookNotesFragment) mainActivity.findFragmentByTag(NotebookNotesFragment.TAG);
                f.updateAdapter();
                mainActivity.debugToast("Create New Note Success: " + response.message());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void joinNote(String name, Topic topic){
        createNewNote(name, topic.getId());
    }

    public void getNotesToJoin(){
        Call<List<Topic>> getUnjoinedNotesCall = NotionService.getApi().getUserUnjoinedNotebookNotes(mainActivity.getCurrentUser().getFb_auth_token(), notebook.getNotebook_id(), true);
        getUnjoinedNotesCall.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Response<List<Topic>> response, Retrofit retrofit) {
                mainActivity.debugToast("Get Notes To Join Success: " + response.message());
                //from joinable notes, get the names of Notes that you can join
                setJoinableNotes(response.body());
                List<String> namesOfNotes = new ArrayList<String>();
                // for each topic, get the name of the notes
                for (Topic t: getJoinableNotes() ){
                    for (Note n: t.getNotes()){
                        namesOfNotes.add(n.getTitle());
                    }
                }
                joinNoteDialog(namesOfNotes);
            }

            @Override
            public void onFailure(Throwable t) {
                mainActivity.debugToast("Get Notes To Join Failure: " + t.getMessage());
            }
        });
    }

    public Topic getTopicFromPosition(int position){
        return getJoinableNotes().get(position);
    }

    public List<Topic> getJoinableNotes() {
        return joinableNotes;
    }

    public void setJoinableNotes(List<Topic> joinableNotes) {
        this.joinableNotes = joinableNotes;
    }
}