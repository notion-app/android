package com.tylorgarrett.notion.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.SettingsAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.dialogs.NewNotebookDialog;
import com.tylorgarrett.notion.models.Course;
import com.tylorgarrett.notion.models.Notebook;
import com.tylorgarrett.notion.models.Section;
import com.tylorgarrett.notion.models.SubscriptionBody;
import com.tylorgarrett.notion.models.User;
import com.tylorgarrett.notion.services.NotionService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionSettingsFragment extends Fragment {

    public static String TAG = "SubscriptionSettingsFragment";

    List data;
    MainActivity mainActivity;
    User currentUser;

    @Bind(R.id.subscription_settings_recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.subscription_settings_fab)
    FloatingActionButton floatingActionButton;

    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public SubscriptionSettingsFragment() {}

    public static Fragment newInstance(){
        SubscriptionSettingsFragment f = new SubscriptionSettingsFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        currentUser = mainActivity.getCurrentUser();
        data = NotionData.getInstance().getNotebooks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subscription_settings, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SettingsAdapter(data, mainActivity);
        mRecyclerView.setAdapter(mAdapter);
        floatingActionButton.setColorNormal(mainActivity.getResources().getColor(R.color.NotionDark));
        return v;
    }

    @OnClick(R.id.subscription_settings_fab)
    public void onClick(View v){
        NewNotebookDialog notebookDialog = new NewNotebookDialog(currentUser, mainActivity);
    }

    public void updateAdapter(){
        mAdapter.notifyDataSetChanged();
    }


}
