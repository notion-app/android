package com.tylorgarrett.notion.dialogs;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.fragments.SubscriptionSettingsFragment;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.SubscriptionBody;
import com.tylorgarrett.notion.models.SubscriptionDeleteBody;
import com.tylorgarrett.notion.services.NotionService;

import org.w3c.dom.Text;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/*
 * Created by tylorgarrett on 10/31/15.
 */
public class NotebookSettingsDialog {

    MainActivity mainActivity;
    Notebook notebook;

    public NotebookSettingsDialog(MainActivity mainActivity, Notebook notebook) {
        this.notebook = notebook;
        this.mainActivity = mainActivity;
        createDialog();
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(notebook.getName() + " Settings");
        builder.setCancelable(true);
        View v = mainActivity.getLayoutInflater().inflate(R.layout.notebook_settings_dialog_view, null);

        TextView tv = (TextView) v.findViewById(R.id.notebook_settings_header_textview);
        tv.setText(mainActivity.getResources().getString(R.string.notebook_name));

        final EditText editText = (EditText) v.findViewById(R.id.notebook_settings_dialog_name_edittext);
        editText.setText(notebook.getName());
        editText.setHint(mainActivity.getResources().getString(R.string.notebook_name));

        builder.setPositiveButton("Update Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNotebookName = editText.getText().toString();
                updateSubscription(newNotebookName);
            }
        });

        builder.setNegativeButton("Delete Notebook", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSubscription();
            }
        });


        builder.setView(v);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        Button b = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        b.setTextColor(Color.rgb(255,0,0));
    }

    public void updateSubscription(String newName){
        Call<Notebook> call = NotionService.getApi().changeSubscription(mainActivity.getCurrentUser().getFb_auth_token(), mainActivity.getCurrentUser().getId(), new SubscriptionBody(notebook.getNotebook_id(), newName));
        call.enqueue(new Callback<Notebook>() {
            @Override
            public void onResponse(Response<Notebook> response, Retrofit retrofit) {
                Notebook results = response.body();
                notebook.setName(results.getName());
                SubscriptionSettingsFragment f = (SubscriptionSettingsFragment) mainActivity.findFragmentByTag(SubscriptionSettingsFragment.TAG);
                if ( f.mAdapter != null ){
                    f.updateAdapter();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void deleteSubscription(){
        Call<Notebook> call = NotionService.getApi().deleteSubscription(mainActivity.getCurrentUser().getFb_auth_token(), mainActivity.getCurrentUser().getId(), new SubscriptionDeleteBody(notebook.getId()));
        call.enqueue(new Callback<Notebook>() {
            @Override
            public void onResponse(Response<Notebook> response, Retrofit retrofit) {
                NotionData.getInstance().removeNotebook(response.body());
                SubscriptionSettingsFragment f = (SubscriptionSettingsFragment) mainActivity.findFragmentByTag(SubscriptionSettingsFragment.TAG);
                if ( f.mAdapter != null ){
                    f.updateAdapter();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
