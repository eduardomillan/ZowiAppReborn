package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

/* JADX INFO: loaded from: classes.dex */
public interface LegacySwipeableItemAdapter<T extends RecyclerView.ViewHolder> extends BaseSwipeableItemAdapter<T> {
    void onPerformAfterSwipeReaction(T t, int i, int i2, int i3);

    int onSwipeItem(T t, int i, int i2);
}
