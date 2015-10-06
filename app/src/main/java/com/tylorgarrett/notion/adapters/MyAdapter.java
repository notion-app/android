package com.tylorgarrett.notion.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import java.util.List;
import java.util.Random;

/*
 * Created by tylorgarrett on 9/21/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List data;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        public ViewHolder(View view) {
            super(view);
            v = view;
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notion_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        LinearLayout linearLayout = (LinearLayout) holder.v.findViewById(R.id.notion_header_layout);
        TextView headerText = (TextView) holder.v.findViewById(R.id.notion_header_textview);
        TextView subHeaderText = (TextView) holder.v.findViewById((R.id.notion_subheader_textview));
        TextView editedText = (TextView) holder.v.findViewById(R.id.notion_edited_textview);
        ImageView imageView = (ImageView) holder.v.findViewById(R.id.notion_imageview);
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.NotionDark));
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.NotionYellow));
        headerText.setTextColor(context.getResources().getColor(R.color.NotionDark));
        try {
            Note note = (Note) data.get(position);
            headerText.setText(note.getTitle());
            subHeaderText.setText(note.getNotebookName());
            imageView.setBackground(context.getResources().getDrawable(R.drawable.note));

        } catch (Exception e){
            Notebook notebook = (Notebook) data.get(position);
            headerText.setText(notebook.getTitle());
            subHeaderText.setText(notebook.getNoteCount() + " Notes");
            imageView.setBackground(context.getResources().getDrawable(R.drawable.notebook));
        }
        editedText.setText("Last Edited: 4 Hours Ago");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public MyAdapter(List data, Context context) {
        this.context = context;
        this.data = data;
    }
}
