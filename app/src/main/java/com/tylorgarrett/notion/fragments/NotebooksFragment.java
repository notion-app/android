package com.tylorgarrett.notion.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MyAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotebooksFragment extends Fragment {

    @Bind(R.id.notion_recyclerview)
    public RecyclerView mRecyclerView;

    List data;


    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public static NotebooksFragment newInstance() {
        NotebooksFragment fragment = new NotebooksFragment();
        return fragment;
    }

    public NotebooksFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_notebooks, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(data, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }


}
