package com.tylorgarrett.notion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;
import com.tylorgarrett.notion.models.Recommendation;

import java.util.List;

/**
 * Created by tylorgarrett on 12/10/15.
 */
public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.ViewHolder> {

    MainActivity mainActivity;
    List<Recommendation> recommendationList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView tv = (TextView) holder.v.findViewById(R.id.recommendation_item_textview);
        tv.setText(recommendationList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        public ViewHolder(View view) {
            super(view);
            v = view;
        }
    }
}
