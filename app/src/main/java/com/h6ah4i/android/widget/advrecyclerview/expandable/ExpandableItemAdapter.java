package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public interface ExpandableItemAdapter<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder> {
    int getChildCount(int i);

    long getChildId(int i, int i2);

    int getChildItemViewType(int i, int i2);

    int getGroupCount();

    long getGroupId(int i);

    int getGroupItemViewType(int i);

    void onBindChildViewHolder(CVH cvh, int i, int i2, int i3);

    void onBindGroupViewHolder(GVH gvh, int i, int i2);

    boolean onCheckCanExpandOrCollapseGroup(GVH gvh, int i, int i2, int i3, boolean z);

    CVH onCreateChildViewHolder(ViewGroup viewGroup, int i);

    GVH onCreateGroupViewHolder(ViewGroup viewGroup, int i);

    boolean onHookGroupCollapse(int i, boolean z);

    boolean onHookGroupExpand(int i, boolean z);
}
