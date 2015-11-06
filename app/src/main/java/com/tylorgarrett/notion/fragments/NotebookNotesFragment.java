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
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.MainAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.dialogs.NewNoteDialog;
import com.tylorgarrett.notion.dialogs.NewNotebookDialog;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Topic;
import com.tylorgarrett.notion.services.NotionService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/*
 * A simple {@link Fragment} subclass.
 */
public class NotebookNotesFragment extends Fragment {

    public static String TAG = "NotebookNotesFragment";

    MainActivity mainActivity;

    List notes;

    Notebook notebook;

    @Bind(R.id.notebooks_recyclerview)
    public RecyclerView mRecyclerView;

    @Bind(R.id.notebooks_floatingactionbutton)
    FloatingActionButton fab;

    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    SlideInBottomAnimationAdapter slideInBottomAnimationAdapter;
    AlphaInAnimationAdapter adapter;

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
        mainActivity.debugToast("notebook description: " + notebook.getDescription());
        notes = notebook.getNotes();
        getUserNotes(notebook);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notion, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(notes, (MainActivity) getActivity());
        slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        adapter = new AlphaInAnimationAdapter(slideInBottomAnimationAdapter);
        adapter.setDuration(500);
        adapter.setFirstOnly(true);
        mRecyclerView.setAdapter(adapter);
        fab.setColorNormal(getResources().getColor(R.color.NotionDark));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewNoteDialog(mainActivity);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        mainActivity.toolbar.setTitle(notebook.getName());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void updateAdapter(){
        adapter.notifyDataSetChanged();
    }

    public void getUserNotes(final Notebook notebook){
        Call<List<Topic>> call = NotionService.getApi().getUserNotebookNotes(mainActivity.getCurrentUser().getFb_auth_token(), notebook.getNotebook_id(), true);
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Response<List<Topic>> response, Retrofit retrofit) {
                notebook.setTopics(response.body());
                notebook.setNotes(notebook.getNotes());
                updateAdapter();
                mainActivity.debugToast("Get Notes Success: " + response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                mainActivity.debugToast("Get Notes Failure: " + t.getMessage());
            }
        });
    }
}
