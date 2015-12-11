package com.tylorgarrett.notion.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;

/**
 * Created by tylorgarrett on 12/10/15.
 */
public class RecommendationsDialogFragment {

    MainActivity mainActivity;

    public RecommendationsDialogFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        createDialog();
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Recommendations");
        View v = mainActivity.getLayoutInflater().inflate(R.layout.recommendations_dialog, null);
        builder.setView(v);
        builder.create().show();
    }

}
