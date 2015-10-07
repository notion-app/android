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


public class NotebooksFragment extends Fragment {

    NotionData notionData;

    String title;
    List data;

    @Bind(R.id.notebooks_recyclerview)
    public RecyclerView mRecyclerView;


    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;


    public static NotebooksFragment newInstance(String title) {
        NotebooksFragment fragment = new NotebooksFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public NotebooksFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notionData = NotionData.getInstance();
        title = getArguments().getString("title");
        this.data = notionData.getNotebooks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notion, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(data, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

}
