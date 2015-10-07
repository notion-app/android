package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Notebook;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteContentFragment extends Fragment {

    @Bind(R.id.note_content_textview)
    TextView contentView;

    NotionData notionData;
    Note note;


    public NoteContentFragment() {}

    public static NoteContentFragment newInstance(String noteID, String notebookID){
        NoteContentFragment f = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putString("note", noteID);
        args.putString("notebook", notebookID);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notionData = NotionData.getInstance();

        String noteId = getArguments().getString("note");
        String notebookId = getArguments().getString("notebook");

        note = notionData.getNoteById(noteId, notebookId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_content, container, false);
        ButterKnife.bind(this, v);
        contentView.setText(note.getContent());
        return v;
    }


}
