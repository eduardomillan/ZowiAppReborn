package com.h6ah4i.android.widget.advrecyclerview.animator;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseItemAnimator extends SimpleItemAnimator {
    private ItemAnimatorListener mListener;

    public interface ItemAnimatorListener {
        void onAddFinished(RecyclerView.ViewHolder viewHolder);

        void onChangeFinished(RecyclerView.ViewHolder viewHolder);

        void onMoveFinished(RecyclerView.ViewHolder viewHolder);

        void onRemoveFinished(RecyclerView.ViewHolder viewHolder);
    }

    public void setListener(ItemAnimatorListener listener) {
        this.mListener = listener;
    }

    public final void onAddStarting(RecyclerView.ViewHolder item) {
        onAddStartingImpl(item);
    }

    public final void onAddFinished(RecyclerView.ViewHolder item) {
        onAddFinishedImpl(item);
        if (this.mListener != null) {
            this.mListener.onAddFinished(item);
        }
    }

    public final void onChangeStarting(RecyclerView.ViewHolder item, boolean oldItem) {
        onChangeStartingItem(item, oldItem);
    }

    public final void onChangeFinished(RecyclerView.ViewHolder item, boolean oldItem) {
        onChangeFinishedImpl(item, oldItem);
        if (this.mListener != null) {
            this.mListener.onChangeFinished(item);
        }
    }

    public final void onMoveStarting(RecyclerView.ViewHolder item) {
        onMoveStartingImpl(item);
    }

    public final void onMoveFinished(RecyclerView.ViewHolder item) {
        onMoveFinishedImpl(item);
        if (this.mListener != null) {
            this.mListener.onMoveFinished(item);
        }
    }

    public final void onRemoveStarting(RecyclerView.ViewHolder item) {
        onRemoveStartingImpl(item);
    }

    public final void onRemoveFinished(RecyclerView.ViewHolder item) {
        onRemoveFinishedImpl(item);
        if (this.mListener != null) {
            this.mListener.onRemoveFinished(item);
        }
    }

    protected void onAddStartingImpl(RecyclerView.ViewHolder item) {
    }

    protected void onAddFinishedImpl(RecyclerView.ViewHolder item) {
    }

    protected void onChangeStartingItem(RecyclerView.ViewHolder item, boolean oldItem) {
    }

    protected void onChangeFinishedImpl(RecyclerView.ViewHolder item, boolean oldItem) {
    }

    protected void onMoveStartingImpl(RecyclerView.ViewHolder item) {
    }

    protected void onMoveFinishedImpl(RecyclerView.ViewHolder item) {
    }

    protected void onRemoveStartingImpl(RecyclerView.ViewHolder item) {
    }

    protected void onRemoveFinishedImpl(RecyclerView.ViewHolder item) {
    }

    public boolean dispatchFinishedWhenDone() {
        if (isRunning()) {
            return false;
        }
        dispatchAnimationsFinished();
        return true;
    }

    public boolean debugLogEnabled() {
        return false;
    }
}
