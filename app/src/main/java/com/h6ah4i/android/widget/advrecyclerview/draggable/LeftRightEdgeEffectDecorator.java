package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.support.v7.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
class LeftRightEdgeEffectDecorator extends BaseEdgeEffectDecorator {
    public LeftRightEdgeEffectDecorator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.BaseEdgeEffectDecorator
    protected int getEdgeDirection(int no) {
        switch (no) {
            case 0:
                return 0;
            case 1:
                return 2;
            default:
                throw new IllegalArgumentException();
        }
    }
}
