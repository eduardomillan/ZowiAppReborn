package com.bq.zowi.components.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    public int holderId;

    public abstract void bind(T t);

    public RecyclerViewHolder(View v) {
        super(v);
    }

    public RecyclerViewHolder(int resourceId, ViewGroup parent, Context c) {
        this(LayoutInflater.from(c).inflate(resourceId, parent, false));
    }

    public void setActivated(boolean activated) {
        this.itemView.setActivated(activated);
    }

    public void setEnabled(boolean enabled) {
        this.itemView.setEnabled(enabled);
    }
}
