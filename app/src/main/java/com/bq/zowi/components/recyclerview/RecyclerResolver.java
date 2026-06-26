package com.bq.zowi.components.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public abstract class RecyclerResolver<T> {
    public static final int UNKNOWN_LAYOUT_ID = -1;

    protected abstract int getItemViewType(T t);

    protected abstract RecyclerViewHolder<? extends T> getViewHolderFromViewType(int i, ViewGroup viewGroup, Context context);

    RecyclerViewHolder<T> getViewHolderFromViewTypeInternal(int i, ViewGroup viewGroup) {
        if (viewGroup == null) {
            throw new RuntimeException("You haven't supplied a proper parent element to the LayoutResolver");
        }
        RecyclerViewHolder<? extends T> viewHolderFromViewType = getViewHolderFromViewType(i, viewGroup, viewGroup.getContext());
        if (viewHolderFromViewType == null) {
            throw new RuntimeException("The selected view type doesn't have a View Holder");
        }
        return (RecyclerViewHolder<T>) viewHolderFromViewType;
    }
}
