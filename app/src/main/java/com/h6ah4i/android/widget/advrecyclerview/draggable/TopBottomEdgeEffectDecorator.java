package com.h6ah4i.android.widget.advrecyclerview.draggable;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
class TopBottomEdgeEffectDecorator extends BaseEdgeEffectDecorator {
    public TopBottomEdgeEffectDecorator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.BaseEdgeEffectDecorator
    protected int getEdgeDirection(int no) {
        switch (no) {
            case 0:
                return 1;
            case 1:
                return 3;
            default:
                throw new IllegalArgumentException();
        }
    }
}
