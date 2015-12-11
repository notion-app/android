package com.tylorgarrett.notion.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.adapters.RecommendationsAdapter;
import com.tylorgarrett.notion.data.NotionData;
import com.tylorgarrett.notion.models.Note;
import com.tylorgarrett.notion.models.Recommendation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tylorgarrett on 12/10/15.
 */
public class RecommendationsDialogFragment {

    MainActivity mainActivity;

    static AlertDialog alertDialog;

    List<Recommendation> recommendations;

    Note note;

    public RecyclerView mRecyclerView;

    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public RecommendationsDialogFragment(MainActivity mainActivity, List recommendations, Note note) {
        this.note = note;
        this.recommendations = recommendations;
        this.mainActivity = mainActivity;
        createDialog();
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(true);
        builder.setTitle("Recommendations");
        View v = mainActivity.getLayoutInflater().inflate(R.layout.recommendations_dialog, null);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recommendations_recyclerview);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mainActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        recommendations = NotionData.getInstance().getRecommendationList();
        mAdapter = new RecommendationsAdapter(recommendations, note, mainActivity);

        mRecyclerView.setAdapter(mAdapter);

        builder.setView(v);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void closeDialog(){
        if ( alertDialog != null ){
            alertDialog.dismiss();
        }
    }

}
