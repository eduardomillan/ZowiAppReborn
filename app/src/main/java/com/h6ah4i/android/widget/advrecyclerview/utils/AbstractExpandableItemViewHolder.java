package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractExpandableItemViewHolder extends RecyclerView.ViewHolder implements ExpandableItemViewHolder {
    private int mExpandStateFlags;

    public AbstractExpandableItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder
    public void setExpandStateFlags(int flags) {
        this.mExpandStateFlags = flags;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder
    public int getExpandStateFlags() {
        return this.mExpandStateFlags;
    }
}
