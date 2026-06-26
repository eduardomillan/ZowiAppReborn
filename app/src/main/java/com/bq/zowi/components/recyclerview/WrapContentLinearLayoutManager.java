package com.bq.zowi.components.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    private static final int CHILD_HEIGHT = 1;
    private static final int CHILD_WIDTH = 0;
    private static final int DEFAULT_CHILD_SIZE = 100;
    private final int[] childDimensions;
    private int childSize;
    private boolean hasChildSize;

    public WrapContentLinearLayoutManager(Context context) {
        super(context);
        this.childDimensions = new int[2];
        this.childSize = 100;
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.childDimensions = new int[2];
        this.childSize = 100;
    }

    public static int makeUnspecifiedSpec() {
        return View.MeasureSpec.makeMeasureSpec(0, 0);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        int width;
        int height;
        int widthMode = View.MeasureSpec.getMode(widthSpec);
        int heightMode = View.MeasureSpec.getMode(heightSpec);
        int widthSize = View.MeasureSpec.getSize(widthSpec);
        int heightSize = View.MeasureSpec.getSize(heightSpec);
        boolean exactWidth = widthMode == 1073741824;
        boolean exactHeight = heightMode == 1073741824;
        int unspecified = makeUnspecifiedSpec();
        if (exactWidth && exactHeight) {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            return;
        }
        boolean vertical = getOrientation() == 1;
        initChildDimensions(widthSize, heightSize, vertical);
        int width2 = 0;
        int height2 = 0;
        recycler.clear();
        int stateItemCount = state.getItemCount();
        int adapterItemCount = getItemCount();
        for (int i = 0; i < adapterItemCount; i++) {
            if (vertical) {
                if (!this.hasChildSize) {
                    if (i < stateItemCount) {
                        measureChild(recycler, i, widthSpec, unspecified, this.childDimensions);
                    } else {
                        logMeasureWarning(i);
                    }
                }
                height2 += this.childDimensions[1];
                if (i == 0) {
                    width2 = this.childDimensions[0];
                }
                if (height2 >= heightSize) {
                    break;
                }
            } else {
                if (!this.hasChildSize) {
                    if (i < stateItemCount) {
                        measureChild(recycler, i, unspecified, heightSpec, this.childDimensions);
                    } else {
                        logMeasureWarning(i);
                    }
                }
                width2 += this.childDimensions[0];
                if (i == 0) {
                    height2 = this.childDimensions[1];
                }
                if (width2 >= widthSize) {
                    break;
                }
            }
        }
        if ((vertical && height2 < heightSize) || (!vertical && width2 < widthSize)) {
            if (exactWidth) {
                width = widthSize;
            } else {
                width = width2 + getPaddingLeft() + getPaddingRight();
            }
            if (exactHeight) {
                height = heightSize;
            } else {
                height = height2 + getPaddingTop() + getPaddingBottom();
            }
            setMeasuredDimension(width, height);
            return;
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    private void logMeasureWarning(int child) {
    }

    private void initChildDimensions(int width, int height, boolean vertical) {
        if (this.childDimensions[0] == 0 && this.childDimensions[1] == 0) {
            if (vertical) {
                this.childDimensions[0] = width;
                this.childDimensions[1] = this.childSize;
            } else {
                this.childDimensions[0] = this.childSize;
                this.childDimensions[1] = height;
            }
        }
    }

    @Override // android.support.v7.widget.LinearLayoutManager
    public void setOrientation(int orientation) {
        if (this.childDimensions != null && getOrientation() != orientation) {
            this.childDimensions[0] = 0;
            this.childDimensions[1] = 0;
        }
        super.setOrientation(orientation);
    }

    public void clearChildSize() {
        this.hasChildSize = false;
        setChildSize(100);
    }

    public void setChildSize(int childSize) {
        this.hasChildSize = true;
        if (this.childSize != childSize) {
            this.childSize = childSize;
            requestLayout();
        }
    }

    private void measureChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] dimensions) {
        View child = recycler.getViewForPosition(position);
        RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) child.getLayoutParams();
        int hPadding = getPaddingLeft() + getPaddingRight();
        int vPadding = getPaddingTop() + getPaddingBottom();
        int hMargin = p.leftMargin + p.rightMargin;
        int vMargin = p.topMargin + p.bottomMargin;
        int hDecoration = getRightDecorationWidth(child) + getLeftDecorationWidth(child);
        int vDecoration = getTopDecorationHeight(child) + getBottomDecorationHeight(child);
        int childWidthSpec = getChildMeasureSpec(widthSpec, hPadding + hMargin + hDecoration, p.width, canScrollHorizontally());
        int childHeightSpec = getChildMeasureSpec(heightSpec, vPadding + vMargin + vDecoration, p.height, canScrollVertically());
        child.measure(childWidthSpec, childHeightSpec);
        dimensions[0] = getDecoratedMeasuredWidth(child) + p.leftMargin + p.rightMargin;
        dimensions[1] = getDecoratedMeasuredHeight(child) + p.bottomMargin + p.topMargin;
        recycler.recycleView(child);
    }
}
