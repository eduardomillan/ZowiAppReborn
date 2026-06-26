package com.h6ah4i.android.widget.advrecyclerview.expandable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;

/* JADX INFO: loaded from: classes.dex */
public class LegacyExpandableSwipeResultAction<GVH extends RecyclerView.ViewHolder, CVH extends RecyclerView.ViewHolder> extends SwipeResultAction {
    LegacyExpandableSwipeableItemAdapter<GVH, CVH> mAdapter;
    int mChildPosition;
    int mGroupPosition;
    RecyclerView.ViewHolder mHolder;
    int mReaction;
    int mResult;

    public LegacyExpandableSwipeResultAction(LegacyExpandableSwipeableItemAdapter<GVH, CVH> adapter, RecyclerView.ViewHolder holder, int groupPosition, int childPosition, int result, int reaction) {
        super(reaction);
        this.mAdapter = adapter;
        this.mHolder = holder;
        this.mGroupPosition = groupPosition;
        this.mChildPosition = childPosition;
        this.mResult = result;
        this.mReaction = reaction;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction
    protected void onPerformAction() {
        if (this.mChildPosition == -1) {
            this.mAdapter.onPerformAfterSwipeGroupReaction(this.mHolder, this.mGroupPosition, this.mResult, this.mReaction);
        } else {
            this.mAdapter.onPerformAfterSwipeChildReaction(this.mHolder, this.mGroupPosition, this.mChildPosition, this.mResult, this.mReaction);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction
    protected void onCleanUp() {
        super.onCleanUp();
        this.mAdapter = null;
        this.mHolder = null;
    }
}
