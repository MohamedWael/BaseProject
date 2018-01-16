package com.blogspot.mowael.baselibrary.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.blogspot.mowael.baselibrary.utils.Logger;

import java.util.ArrayList;

/**
 * Created by moham on 6/5/2017.
 */

public abstract class MoRecyclerBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected ArrayList<T> items;

    public MoRecyclerBaseAdapter(ArrayList<T> items) {
        this.items = items;
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemInserted(this.items.size());
//        notifyDataSetChanged();
        onItemAdded(item);
    }

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType) ;

    public void addItem(int position, T item) {
        items.add(position, item);
        notifyItemInserted(position);
        onItemAdded(item);
    }

    public void onItemAdded(T item) {
    }

    public void removeItem(T item) {
        if (items != null && items.contains(item)) {
            int position = items.indexOf(item);
            items.remove(item);
            notifyItemRemoved(position);
            onItemRemoved(item);
        }
    }

    public void onItemRemoved(T item) {
    }

    public void removeItem(int position) {
        if (items != null) {
            onItemRemoved(items.get(position));
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addMoreItems(ArrayList<T> items) {
        if (this.items != null) {
            int lastItem = this.items.size();
            this.items.addAll(items);
            notifyItemRangeInserted(lastItem, this.items.size());
            onItemsAdded(items);
        } else Logger.e("items is null");
    }

    public void onItemsAdded(ArrayList<T> newItems) {
        Logger.d("onItemsAdded");
    }

    /**
     * clear all old data and reset new items to the list
     *
     * @param data new items to be added
     */
    public void resetItems(ArrayList<T> data) {
        clearItems();
        addMoreItems(data);
        onItemsReset(data);
    }

    public void onItemsReset(ArrayList<T> newData) {
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        if (this.items != null) {
            this.items.clear();
        }
        this.items = items;
        notifyDataSetChanged();
        onItemsAdded(items);
    }

    public void clearItems() {
        try {
            if (items != null) {
                int itemCount = items.size();
                items.clear();
                notifyItemRangeRemoved(0, itemCount);
                onItemsCleared();
            }
        } catch (UnsupportedOperationException e) {

        }
    }

    public void onItemsCleared() {
    }

    public void addMoreItemsUniquely(ArrayList<T> items) {
        try {
            ArrayList<T> intersection = new ArrayList<>(this.items);
            intersection.retainAll(items);
            this.items.removeAll(intersection);
            addMoreItems(items);
        } catch (UnsupportedOperationException e) {
            addMoreItems(items);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
