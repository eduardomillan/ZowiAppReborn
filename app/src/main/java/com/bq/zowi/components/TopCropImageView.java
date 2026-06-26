package com.bq.zowi.components;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class TopCropImageView extends ImageView {
    public TopCropImageView(Context context) {
        super(context);
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    public TopCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    public TopCropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    @Override // android.widget.ImageView
    protected boolean setFrame(int l, int t, int r, int b) {
        float scale;
        Matrix matrix = getImageMatrix();
        int viewWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int viewHeight = (getHeight() - getPaddingTop()) - getPaddingBottom();
        int drawableWidth = getDrawable().getIntrinsicWidth();
        int drawableHeight = getDrawable().getIntrinsicHeight();
        if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            scale = viewHeight / drawableHeight;
        } else {
            scale = viewWidth / drawableWidth;
        }
        matrix.setScale(scale, scale);
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }
}
