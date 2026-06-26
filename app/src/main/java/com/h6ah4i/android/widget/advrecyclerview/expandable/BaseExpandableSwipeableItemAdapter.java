package com.h6ah4i.android.widget.advrecyclerview.expandable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* JADX INFO: loaded from: classes.dex */
public interface BaseExpandableSwipeableItemAdapter<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder> {
    int onGetChildItemSwipeReactionType(CVH cvh, int i, int i2, int i3, int i4);

    int onGetGroupItemSwipeReactionType(GVH gvh, int i, int i2, int i3);

    void onSetChildItemSwipeBackground(CVH cvh, int i, int i2, int i3);

    void onSetGroupItemSwipeBackground(GVH gvh, int i, int i2);
}
