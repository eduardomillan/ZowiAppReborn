package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class AddAnimationInfo extends ItemAnimationInfo {
    public RecyclerView.ViewHolder holder;

    public AddAnimationInfo(RecyclerView.ViewHolder holder) {
        this.holder = holder;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemAnimationInfo
    public RecyclerView.ViewHolder getAvailableViewHolder() {
        return this.holder;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemAnimationInfo
    public void clear(RecyclerView.ViewHolder item) {
        if (this.holder == null) {
            this.holder = null;
        }
    }

    public String toString() {
        return "AddAnimationInfo{holder=" + this.holder + '}';
    }
}
