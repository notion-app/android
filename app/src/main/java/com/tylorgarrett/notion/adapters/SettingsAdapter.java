package com.tylorgarrett.notion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tylorgarrett.notion.MainActivity;
import com.tylorgarrett.notion.R;

import java.util.List;

/**
 * Created by tylorgarrett on 10/27/15.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    List data;
    MainActivity mainActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        public ViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
        }
    }

    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notion_settings_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public SettingsAdapter(List data, MainActivity mainActivity) {
        this.data = data;
        this.mainActivity = mainActivity;
    }
}
