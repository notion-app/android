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
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MainAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.dialogs.NewNotebookDialog;
import com.tylorgarrett.notion.listeners.OnUserSubscriptionsReadyListener;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.models.User;
import com.tylorgarrett.notion.services.NotionService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class NotebooksFragment extends Fragment implements OnUserSubscriptionsReadyListener{

    public static String TAG = "NotebooksFragment";

    NotionData notionData;
    MainActivity mainActivity;
    SlideInBottomAnimationAdapter slideInBottomAnimationAdapter;
    AlphaInAnimationAdapter adapter;
    User currentUser;
    private static NotebooksFragment notebooksFragment = null;

    List data;

    @Bind(R.id.notebooks_recyclerview)
    public RecyclerView mRecyclerView;

    @Bind(R.id.notebooks_floatingactionbutton)
    FloatingActionButton fab;

    @Bind(R.id.notebooks_textview)
    TextView textView;

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
        data = notionData.getNotebooks();
        currentUser = mainActivity.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notion, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(data, (MainActivity) getActivity());
        slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        adapter = new AlphaInAnimationAdapter(slideInBottomAnimationAdapter);
        adapter.setDuration(500);
        adapter.setFirstOnly(true);
        mRecyclerView.setAdapter(adapter);
        fab.setColorNormal(getResources().getColor(R.color.NotionDark));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewNotebookDialog(mainActivity.getCurrentUser(), mainActivity);
            }
        });
        checkView();
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

    public void updateAdapter(){
        adapter.notifyDataSetChanged();
        mainActivity.debugToast("updated adapter");
        checkView();
    }

    @Override
    public void onUserSubScriptionsReadyListener() {
        mAdapter.notifyDataSetChanged();
    }


    public void checkView(){
        if ( adapter.getItemCount() == 0 ){
            mRecyclerView.setVisibility(View.GONE);
            textView.setText(getResources().getString(R.string.notebook_empty));
        } else {
            textView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
