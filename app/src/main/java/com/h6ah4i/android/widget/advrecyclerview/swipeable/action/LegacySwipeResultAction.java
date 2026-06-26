package com.h6ah4i.android.widget.advrecyclerview.swipeable.action;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.LegacySwipeableItemAdapter;

/* JADX INFO: loaded from: classes.dex */
public class LegacySwipeResultAction<VH extends RecyclerView.ViewHolder> extends SwipeResultAction {
    LegacySwipeableItemAdapter<VH> mAdapter;
    VH mHolder;
    int mPosition;
    int mReaction;
    int mResult;

    public LegacySwipeResultAction(LegacySwipeableItemAdapter<VH> adapter, VH holder, int position, int result, int reaction) {
        super(reaction);
        this.mAdapter = adapter;
        this.mHolder = holder;
        this.mPosition = position;
        this.mResult = result;
        this.mReaction = reaction;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction
    protected void onPerformAction() {
        this.mAdapter.onPerformAfterSwipeReaction(this.mHolder, this.mPosition, this.mResult, this.mReaction);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction
    protected void onCleanUp() {
        super.onCleanUp();
        this.mAdapter = null;
        this.mHolder = null;
    }
}
