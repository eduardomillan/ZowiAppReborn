package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemMoveAnimationManager extends BaseItemAnimationManager<MoveAnimationInfo> {
    public static final String TAG = "ARVItemMoveAnimMgr";

    public abstract boolean addPendingAnimation(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4);

    public ItemMoveAnimationManager(BaseItemAnimator itemAnimator) {
        super(itemAnimator);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public long getDuration() {
        return this.mItemAnimator.getMoveDuration();
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public void setDuration(long duration) {
        this.mItemAnimator.setMoveDuration(duration);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public void dispatchStarting(MoveAnimationInfo info, RecyclerView.ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchMoveStarting(" + item + ")");
        }
        this.mItemAnimator.dispatchMoveStarting(item);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public void dispatchFinished(MoveAnimationInfo info, RecyclerView.ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchMoveFinished(" + item + ")");
        }
        this.mItemAnimator.dispatchMoveFinished(item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public boolean endNotStartedAnimation(MoveAnimationInfo info, RecyclerView.ViewHolder item) {
        if (info.holder == null || !(item == null || info.holder == item)) {
            return false;
        }
        onAnimationEndedBeforeStarted(info, info.holder);
        dispatchFinished(info, info.holder);
        info.clear(info.holder);
        return true;
    }
}
