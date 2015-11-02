package com.tylorgarrett.notion.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.dialogs.NotebookSettingsDialog;
import com.tylorgarrett.notion.fragments.NotebookNotesFragment;
import com.tylorgarrett.notion.fragments.SubscriptionSettingsFragment;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import java.util.List;

/**
 * Created by tylorgarrett on 10/27/15.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    List data;
    MainActivity mainActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        public ViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
        }
    }

    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notion_settings_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ViewHolder holder, int position) {
        Object object = data.get(position);
        if ( object instanceof Notebook ){
            notebookSettings(object, holder.v);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public SettingsAdapter(List data, MainActivity mainActivity) {
        this.data = data;
        this.mainActivity = mainActivity;
    }

    public void notebookSettings(Object object, View v){
        TextView headerText = (TextView) v.findViewById(R.id.notion_settings_header_textview);
        final Notebook notebook = (Notebook) object;
        headerText.setText(notebook.getName());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NotebookSettingsDialog(mainActivity, notebook);
            }
        });
    }
}
