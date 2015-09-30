package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MyAdapter;
import com.tylorgarrett.notion.data.NotionData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NotionFragment extends Fragment {

    NotionData notionData;

    String title;
    List data;

    @Bind(R.id.notion_recyclerview)
    public RecyclerView mRecyclerView;


    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;


    public static NotionFragment newInstance(String title) {
        NotionFragment fragment = new NotionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public NotionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notionData = NotionData.getInstance();
        title = getArguments().getString("title");
        if ( title.equals("Notebooks") ) {
            this.data = notionData.getNotebooks();
        } else if ( title.equals("Notes") ){
            this.data = notionData.getNotes();
        } else {
            this.data = new ArrayList(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notion, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

}
