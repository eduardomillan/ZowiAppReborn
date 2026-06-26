package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

/* JADX INFO: loaded from: classes.dex */
public class DraggingItemInfo {
    public final int grabbedPositionX;
    public final int grabbedPositionY;
    public final int height;
    public final long id;
    public final Rect margins = new Rect();
    public final int width;

    public DraggingItemInfo(RecyclerView.ViewHolder vh, int touchX, int touchY) {
        this.width = vh.itemView.getWidth();
        this.height = vh.itemView.getHeight();
        this.id = vh.getItemId();
        this.grabbedPositionX = touchX - vh.itemView.getLeft();
        this.grabbedPositionY = touchY - vh.itemView.getTop();
        CustomRecyclerViewUtils.getLayoutMargins(vh.itemView, this.margins);
    }
}
