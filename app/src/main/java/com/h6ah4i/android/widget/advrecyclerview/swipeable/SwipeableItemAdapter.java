package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;

/* JADX INFO: loaded from: classes.dex */
public interface SwipeableItemAdapter<T extends RecyclerView.ViewHolder> extends BaseSwipeableItemAdapter<T> {
    SwipeResultAction onSwipeItem(T t, int i, int i2);
}
