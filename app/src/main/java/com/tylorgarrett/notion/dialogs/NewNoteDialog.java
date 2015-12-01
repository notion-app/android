package com.tylorgarrett.notion.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.fragments.NotebookNotesFragment;
import com.tylorgarrett.notion.models.CreateNoteBody;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.services.NotionService;

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

    public NewNoteDialog(MainActivity mainActivity, Notebook notebook, List<Note> notes) {
        this.mainActivity = mainActivity;
        this.notebook = notebook;
        this.notes = notes;
        setUpDialog();
    }

    public void setUpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Select an Option");
        builder.setPositiveButton("Create Topic", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewNoteDialog();
            }
        });
        builder.setNegativeButton("Join Topic", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public void createNewNoteDialog(){
        View v = mainActivity.getLayoutInflater().inflate(R.layout.create_note_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Create Topic");
        builder.setView(v);
        final EditText editText = (EditText) v.findViewById(R.id.create_note_textview);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewNote(editText.getText().toString());
            }
        });
        builder.create().show();
    }

    public void createNewNote(String noteName){
        Call<Topic> call = NotionService.getApi().createNote(mainActivity.getCurrentUser().getFb_auth_token(), notebook.getNotebook_id(), new CreateNoteBody(noteName, null));
        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Response<Topic> response, Retrofit retrofit) {
                Topic t = response.body();
                Note  n = t.getNotes().get(0);
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
}

