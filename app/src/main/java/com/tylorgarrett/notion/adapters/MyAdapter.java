package com.tylorgarrett.notion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tylorgarrett on 9/21/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        public ViewHolder(View view) {
            super(view);
            v = view;
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notion_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        TextView headerText = (TextView) holder.v.findViewById(R.id.notion_header_textview);
        TextView subHeaderText = (TextView) holder.v.findViewById((R.id.notion_subheader_textview));
        try {
            Note note = (Note) data.get(position);
            headerText.setText(note.getTitle());
            subHeaderText.setText(note.getNotebookName());
        } catch (Exception e){
            Notebook notebook = (Notebook) data.get(position);
            headerText.setText(notebook.getTitle());
            subHeaderText.setText(notebook.getNoteCount() + " Notes");
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public MyAdapter(List data) {
        this.data = data;
    }
}
