package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemAddAnimationManager extends BaseItemAnimationManager<AddAnimationInfo> {
    private static final String TAG = "ARVItemAddAnimMgr";

    public abstract boolean addPendingAnimation(RecyclerView.ViewHolder viewHolder);

    public ItemAddAnimationManager(BaseItemAnimator itemAnimator) {
        super(itemAnimator);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public long getDuration() {
        return this.mItemAnimator.getAddDuration();
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public void setDuration(long duration) {
        this.mItemAnimator.setAddDuration(duration);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public void dispatchStarting(AddAnimationInfo info, RecyclerView.ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchAddStarting(" + item + ")");
        }
        this.mItemAnimator.dispatchAddStarting(item);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public void dispatchFinished(AddAnimationInfo info, RecyclerView.ViewHolder item) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchAddFinished(" + item + ")");
        }
        this.mItemAnimator.dispatchAddFinished(item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.h6ah4i.android.widget.advrecyclerview.animator.impl.BaseItemAnimationManager
    public boolean endNotStartedAnimation(AddAnimationInfo info, RecyclerView.ViewHolder item) {
        if (info.holder == null || !(item == null || info.holder == item)) {
            return false;
        }
        onAnimationEndedBeforeStarted(info, info.holder);
        dispatchFinished(info, info.holder);
        info.clear(info.holder);
        return true;
    }
}
