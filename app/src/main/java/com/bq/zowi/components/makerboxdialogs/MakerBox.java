package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.bq.zowi.utils.ResourceResolver;

/* JADX INFO: loaded from: classes.dex */
public class MakerBox extends LinearLayout {
    private final float cornerRadius;
    private final float maskBottomPadding;
    private final Path path;
    private final RectF roundRectMask;

    public MakerBox(Context context) {
        super(context);
        this.maskBottomPadding = ResourceResolver.getIntegerByResourceId("makerbox_content_mask_bottom_padding", context);
        this.cornerRadius = ResourceResolver.getDimensionByResourceId("makerbox_corner_radius", context);
        this.roundRectMask = new RectF();
        this.path = new Path();
        init(context);
    }

    public MakerBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.maskBottomPadding = ResourceResolver.getIntegerByResourceId("makerbox_content_mask_bottom_padding", context);
        this.cornerRadius = ResourceResolver.getDimensionByResourceId("makerbox_corner_radius", context);
        this.roundRectMask = new RectF();
        this.path = new Path();
        init(context);
    }

    public MakerBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.maskBottomPadding = ResourceResolver.getIntegerByResourceId("makerbox_content_mask_bottom_padding", context);
        this.cornerRadius = ResourceResolver.getDimensionByResourceId("makerbox_corner_radius", context);
        this.roundRectMask = new RectF();
        this.path = new Path();
        init(context);
    }

    private void init(Context context) {
        android.graphics.drawable.Drawable makerBoxDrawable = ResourceResolver.getDrawableByResourceId("maker_box", context);
        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(makerBoxDrawable);
        } else {
            setBackground(makerBoxDrawable);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int w = getWidth();
        int h = getHeight();
        this.roundRectMask.set(0.0f, 0.0f, w, h - this.maskBottomPadding);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        this.path.reset();
        this.path.addRoundRect(this.roundRectMask, this.cornerRadius, this.cornerRadius, Path.Direction.CW);
        if (Build.VERSION.SDK_INT <= 11 || Build.VERSION.SDK_INT >= 16 || !isHardwareAccelerated()) {
            canvas.clipPath(this.path);
        }
        super.onDraw(canvas);
    }
}
