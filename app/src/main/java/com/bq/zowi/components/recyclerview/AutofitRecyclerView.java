package com.bq.zowi.components.recyclerview;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class AutofitRecyclerView extends RecyclerView {
    private int columnWidth;
    private GridLayoutManager manager;
    private int spanCount;

    public AutofitRecyclerView(Context context) {
        super(context);
        this.columnWidth = -1;
        init(context, null);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.columnWidth = -1;
        init(context, attrs);
    }

    public AutofitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.columnWidth = -1;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            int[] attrsArray = {R.attr.columnWidth};
            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
            this.columnWidth = array.getDimensionPixelSize(0, -1);
            array.recycle();
        }
        Context context2 = getContext();
        this.spanCount = 1;
        this.manager = new GridLayoutManager(context2, 1);
        setLayoutManager(this.manager);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (this.columnWidth > 0) {
            this.spanCount = Math.max(1, getMeasuredWidth() / this.columnWidth);
            this.manager.setSpanCount(this.spanCount);
        }
    }

    public int getSpanCount() {
        return this.spanCount;
    }
}
