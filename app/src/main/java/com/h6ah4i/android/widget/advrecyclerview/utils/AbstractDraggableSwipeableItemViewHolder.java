package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractDraggableSwipeableItemViewHolder extends AbstractSwipeableItemViewHolder implements DraggableItemViewHolder {
    private int mDragStateFlags;

    public AbstractDraggableSwipeableItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder
    public void setDragStateFlags(int flags) {
        this.mDragStateFlags = flags;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder
    public int getDragStateFlags() {
        return this.mDragStateFlags;
    }
}
