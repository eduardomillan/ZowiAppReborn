package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class MakerBox extends LinearLayout {
    private final float cornerRadius;
    private final float maskBottomPadding;
    private final Path path;
    private final RectF roundRectMask;

    public MakerBox(Context context) {
        super(context);
        this.maskBottomPadding = getResources().getInteger(R.integer.makerbox_content_mask_bottom_padding);
        this.cornerRadius = getResources().getDimension(R.dimen.makerbox_corner_radius);
        this.roundRectMask = new RectF();
        this.path = new Path();
        init(context);
    }

    public MakerBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.maskBottomPadding = getResources().getInteger(R.integer.makerbox_content_mask_bottom_padding);
        this.cornerRadius = getResources().getDimension(R.dimen.makerbox_corner_radius);
        this.roundRectMask = new RectF();
        this.path = new Path();
        init(context);
    }

    public MakerBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.maskBottomPadding = getResources().getInteger(R.integer.makerbox_content_mask_bottom_padding);
        this.cornerRadius = getResources().getDimension(R.dimen.makerbox_corner_radius);
        this.roundRectMask = new RectF();
        this.path = new Path();
        init(context);
    }

    private void init(Context context) {
        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.maker_box));
        } else {
            setBackground(ContextCompat.getDrawable(context, R.drawable.maker_box));
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
