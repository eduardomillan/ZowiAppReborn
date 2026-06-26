package com.bq.zowi.components.games;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.gridlayout.widget.GridLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public class MouthGridLayout extends GridLayout {
    private boolean isTouchable;
    private MouthGridItemView lastItem;
    private MouthGridLayoutTouchListener listener;

    public interface MouthGridLayoutTouchListener {
        void sendCurrentMouth();
    }

    public MouthGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isTouchable = true;
    }

    public MouthGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isTouchable = true;
    }

    public MouthGridLayout(Context context) {
        super(context);
        this.isTouchable = true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (!this.isTouchable) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        if (x < 0.0f || x > getWidth() || y < 0.0f || y > getHeight()) {
            this.lastItem = null;
            return false;
        }
        int column = (int) (x / (getWidth() / getColumnCount()));
        int row = (int) (y / (getHeight() / getRowCount()));
        MouthGridItemView item = (MouthGridItemView) getChildAt((getColumnCount() * row) + column);
        switch (event.getAction()) {
            case 0:
                if (this.lastItem == null && item != null) {
                    item.handleTouch(false, false);
                    this.lastItem = item;
                    this.listener.sendCurrentMouth();
                    break;
                }
                break;
            case 1:
            case 3:
                this.lastItem = null;
                break;
            case 2:
                if (this.lastItem != null && item != null) {
                    if (item.column_position != this.lastItem.column_position || item.row_position != this.lastItem.row_position) {
                        item.handleTouch(true, this.lastItem.isLedOn);
                        this.lastItem = item;
                        this.listener.sendCurrentMouth();
                    }
                    break;
                }
                break;
        }
        return false;
    }

    public void setIsTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    public void resetGrid() {
        for (int i = 0; i < getChildCount(); i++) {
            ((MouthGridItemView) getChildAt(i)).setState(false);
        }
        this.lastItem = null;
    }

    public void setMouthGridLayoutTouchListener(MouthGridLayoutTouchListener listener) {
        this.listener = listener;
    }
}
