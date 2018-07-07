package com.blogspot.mowael.baselibrary.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by moham on 6/9/2017.
 */

public abstract class RecyclerViewSwipeHelper extends ItemTouchHelper.SimpleCallback {
    public RecyclerViewSwipeHelper(RecyclerView mRecyclerView) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        new ItemTouchHelper(this).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        if (swipeDir == ItemTouchHelper.LEFT) {
            onSwipedLeft(viewHolder);
        } else if (swipeDir == ItemTouchHelper.RIGHT) {
            onSwipedRight(viewHolder);
        }
    }

    public abstract void onSwipedLeft(RecyclerView.ViewHolder viewHolder);

    public abstract void onSwipedRight(RecyclerView.ViewHolder viewHolder);
}
