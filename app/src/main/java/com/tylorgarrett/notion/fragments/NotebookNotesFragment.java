package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MyAdapter;
import com.tylorgarrett.notion.data.NotionData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * A simple {@link Fragment} subclass.
 */
public class NotebookNotesFragment extends Fragment {

    List notes;

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
        String id = getArguments().getString("id");
        notes = NotionData.getInstance().getNotebookById(id).getNotes();
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
            mRecyclerView.setAdapter(mAdapter);
        }
        return v;
    }


}
