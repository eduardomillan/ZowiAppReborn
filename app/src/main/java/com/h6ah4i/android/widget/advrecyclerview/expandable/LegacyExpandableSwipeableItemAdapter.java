package com.h6ah4i.android.widget.advrecyclerview.expandable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/* JADX INFO: loaded from: classes.dex */
public interface LegacyExpandableSwipeableItemAdapter<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder> extends BaseExpandableSwipeableItemAdapter<GVH, CVH> {
    void onPerformAfterSwipeChildReaction(CVH cvh, int i, int i2, int i3, int i4);

    void onPerformAfterSwipeGroupReaction(GVH gvh, int i, int i2, int i3);

    int onSwipeChildItem(CVH cvh, int i, int i2, int i3);

    int onSwipeGroupItem(GVH gvh, int i, int i2);
}
