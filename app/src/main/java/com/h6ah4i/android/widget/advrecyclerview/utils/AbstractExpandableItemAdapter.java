package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemAdapter;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractExpandableItemAdapter<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ExpandableItemAdapter<GVH, CVH> {
    @Override // android.support.v7.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public final long getItemId(int position) {
        return -1L;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public final int getItemViewType(int position) {
        return 0;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return 0;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemAdapter
    public boolean onHookGroupExpand(int groupPosition, boolean fromUser) {
        return true;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemAdapter
    public boolean onHookGroupCollapse(int groupPosition, boolean fromUser) {
        return true;
    }
}
