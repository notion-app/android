package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MyAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Notebook;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

/*
 * A simple {@link Fragment} subclass.
 */
public class NotebookNotesFragment extends Fragment {

    MainActivity mainActivity;

    List notes;

    Notebook notebook;

    @Bind(R.id.notebooks_recyclerview)
    public RecyclerView mRecyclerView;


    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public NotebookNotesFragment() {}

    public static Fragment newInstance(String id){
        NotebookNotesFragment f = new NotebookNotesFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        String id = getArguments().getString("id");
        notebook = NotionData.getInstance().getNotebookById(id);
        notes = notebook.getNotes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notion, container, false);
        ButterKnife.bind(this, v);
        if ( notes != null ){
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter(notes, (MainActivity) getActivity());
            SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
            AlphaInAnimationAdapter adapter = new AlphaInAnimationAdapter(slideInBottomAnimationAdapter);
            adapter.setDuration(500);
            adapter.setFirstOnly(true);
            mRecyclerView.setAdapter(adapter);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        mainActivity.toolbar.setTitle(notebook.getTitle());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
