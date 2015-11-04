package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Note;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.uncod.android.bypass.Bypass;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteContentFragment extends Fragment {

    public static String TAG = "NoteContentFragment";

    @Bind(R.id.note_content_textview)
    TextView contentView;

    MainActivity mainActivity;

    NotionData notionData;
    Note note;

    Bypass bypass;

    String noteID;
    String notebookID;


    public NoteContentFragment() {}

    public static NoteContentFragment newInstance(String noteID, String notebookID){
        NoteContentFragment f = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putString("noteID", noteID);
        args.putString("notebookID", notebookID);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();

        notionData = NotionData.getInstance();

        noteID = getArguments().getString("noteID");
        notebookID = getArguments().getString("notebookID");

        //note = notionData.getNoteById(noteID, notebookID);
        note = new Note("", "", "", "", "", "", "");

        bypass = new Bypass();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_content, container, false);
        ButterKnife.bind(this, v);
        CharSequence contentMD = bypass.markdownToSpannable(note.getContent().toString());
        contentView.setText(contentMD);
        contentView.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note_content, menu);
        mainActivity.getSupportActionBar().setTitle(note.getTitle());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                mainActivity.performFragmentTransaction(NoteEditFragment.newInstance(noteID, notebookID), true, NoteEditFragment.TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
