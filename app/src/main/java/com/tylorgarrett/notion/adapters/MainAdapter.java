package com.tylorgarrett.notion.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.tylorgarrett.notion.fragments.NotebooksFragment;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import java.util.List;

/*
 * Created by tylorgarrett on 9/21/15.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

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
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notion_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {

        LinearLayout linearLayout = (LinearLayout) holder.v.findViewById(R.id.notion_header_layout);
        TextView headerText = (TextView) holder.v.findViewById(R.id.notion_header_textview);
        TextView subHeaderText = (TextView) holder.v.findViewById((R.id.notion_subheader_textview));
        TextView editedText = (TextView) holder.v.findViewById(R.id.notion_edited_textview);
        ImageView imageView = (ImageView) holder.v.findViewById(R.id.notion_imageview);
        final Notebook notebook;
        Note note;


        if ( data.get(position) instanceof Note ){
            Log.d("logger", "Instance of Note found");
            note = (Note) data.get(position);
            final Note noteToBePassed = note;
            linearLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.NotionYellow));
            headerText.setTextColor(mainActivity.getResources().getColor(R.color.NotionDark));
            headerText.setText(note.getTitle());
            imageView.setBackground(mainActivity.getResources().getDrawable(R.drawable.note));
            editedText.setText("Last Edited: 4 Hours Ago");
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.performFragmentTransaction(NoteContentFragment.newInstance(noteToBePassed.getId()), true, NoteContentFragment.TAG);
                }
            });
        } else if (data.get(position) instanceof Notebook ){
            notebook = (Notebook) data.get(position);
            linearLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.NotionYellow));
            headerText.setTextColor(mainActivity.getResources().getColor(R.color.NotionDark));
            headerText.setText(notebook.getName());
            subHeaderText.setText(notebook.getNoteCount() + " Notes");
            imageView.setBackground(mainActivity.getResources().getDrawable(R.drawable.notebook));
            editedText.setText("Last Edited: 4 Hours Ago");
            final String notebookID = notebook.getNotebook_id();
            holder.v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.performFragmentTransaction(NotebookNotesFragment.newInstance(notebookID), true, NotebookNotesFragment.TAG);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public MainAdapter(List data, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.data = data;
    }
}
