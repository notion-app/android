package com.tylorgarrett.notion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MainAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.listeners.OnUserSubscriptionsReadyListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;


public class NotebooksFragment extends Fragment implements OnUserSubscriptionsReadyListener{

    NotionData notionData;
    MainActivity mainActivity;
    private static NotebooksFragment notebooksFragment = null;

    List data;

    @Bind(R.id.notebooks_recyclerview)
    public RecyclerView mRecyclerView;


    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;


    public static NotebooksFragment getInstance() {
        if ( notebooksFragment == null ){
            return new NotebooksFragment();
        }
        return notebooksFragment;
    }

    public NotebooksFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        notionData = NotionData.getInstance();
        this.data = notionData.getNotebooks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notion, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(data, (MainActivity) getActivity());
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        AlphaInAnimationAdapter adapter = new AlphaInAnimationAdapter(slideInBottomAnimationAdapter);
        adapter.setDuration(500);
        adapter.setFirstOnly(true);
        mRecyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        mainActivity.toolbar.setTitle(getResources().getString(R.string.app_name));
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUserSubScriptionsReadyListener() {
        mAdapter.notifyDataSetChanged();
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }
}
