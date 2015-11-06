package com.tylorgarrett.notion.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;

/*
 * Created by tylorgarrett on 11/5/15.
 */
public class NewNoteDialog {
    MainActivity mainActivity;

    public NewNoteDialog(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}

