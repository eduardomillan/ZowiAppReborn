package com.h6ah4i.android.widget.advrecyclerview.utils;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractDraggableItemViewHolder extends RecyclerView.ViewHolder implements DraggableItemViewHolder {
    private int mDragStateFlags;

    public AbstractDraggableItemViewHolder(View itemView) {
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
