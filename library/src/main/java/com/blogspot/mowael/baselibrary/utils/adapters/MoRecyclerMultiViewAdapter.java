package com.blogspot.mowael.baselibrary.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by moham on 8/25/2017.
 */

public abstract class MoRecyclerMultiViewAdapter<T, VH1 extends RecyclerView.ViewHolder, VH2 extends RecyclerView.ViewHolder> extends MoRecyclerBaseAdapter<T, RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_FIRST = 0;
    public static final int VIEW_TYPE_SECOND = 1;

    public MoRecyclerMultiViewAdapter(ArrayList<T> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FIRST)
            return onCreateFirstViewHolder(parent);
        else if (viewType == VIEW_TYPE_SECOND) return onCreateSecondViewHolder(parent);
        else return null;
    }

    public abstract VH1 onCreateFirstViewHolder(ViewGroup parent);

    public abstract VH2 onCreateSecondViewHolder(ViewGroup parent);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (getFirstViewTypeCondition())
            return VIEW_TYPE_FIRST;
        else if (getSecondViewTypeCondition())
            return VIEW_TYPE_SECOND;
        else
            return super.getItemViewType(position);
    }

    public abstract boolean getFirstViewTypeCondition();

    public abstract boolean getSecondViewTypeCondition();

}
