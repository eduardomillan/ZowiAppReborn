package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

/* JADX INFO: loaded from: classes.dex */
public interface BaseSwipeableItemAdapter<T extends RecyclerView.ViewHolder> {
    int onGetSwipeReactionType(T t, int i, int i2, int i3);

    void onSetSwipeBackground(T t, int i, int i2);
}
