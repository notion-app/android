package com.tylorgarrett.notion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tylorgarrett.notion.R;

/**
 * Created by tylorgarrett on 9/21/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    String title;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        public ViewHolder(View view) {
            super(view);
            v = view;
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notion_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        TextView subheaderText = (TextView) holder.v.findViewById(R.id.notion_header_textview);
        subheaderText.setText(title);

    }

    @Override
    public int getItemCount() {
        return 56;
    }

    public MyAdapter(String title) {
        this.title = title;
    }
}
