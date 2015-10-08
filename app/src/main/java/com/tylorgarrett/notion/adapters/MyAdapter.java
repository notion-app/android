package com.tylorgarrett.notion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.fragments.NoteContentFragment;
import com.tylorgarrett.notion.fragments.NotebookNotesFragment;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import java.util.List;

/*
 * Created by tylorgarrett on 9/21/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List data;
    MainActivity mainActivity;

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
        Notebook notebook;
        Note note;


        if ( data.get(position) instanceof Note ){
            note = (Note) data.get(position);
            linearLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.NotionYellow));
            headerText.setTextColor(mainActivity.getResources().getColor(R.color.NotionDark));
            headerText.setText(note.getTitle());
            subHeaderText.setText(note.getNotebookName());
            imageView.setBackground(mainActivity.getResources().getDrawable(R.drawable.note));
            editedText.setText("Last Edited: 4 Hours Ago");
            final String noteName = note.getTitle();
            final String notebookID = note.getNotebookName();
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.performFragmentTransaction(NoteContentFragment.newInstance(noteName, notebookID), true);
                }
            });
        } else if (data.get(position) instanceof Notebook ){
            notebook = (Notebook) data.get(position);
            linearLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.NotionYellow));
            headerText.setTextColor(mainActivity.getResources().getColor(R.color.NotionDark));
            headerText.setText(notebook.getTitle());
            subHeaderText.setText(notebook.getNoteCount() + " Notes");
            imageView.setBackground(mainActivity.getResources().getDrawable(R.drawable.notebook));
            editedText.setText("Last Edited: 4 Hours Ago");
            final String notebookName = notebook.getTitle();
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.performFragmentTransaction(NotebookNotesFragment.newInstance(notebookName), true);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public MyAdapter(List data, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.data = data;
    }
}
