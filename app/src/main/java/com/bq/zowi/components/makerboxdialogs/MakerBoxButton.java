package com.bq.zowi.components.makerboxdialogs;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import com.bq.zowi.R;
import com.bq.zowi.utils.ResourceResolver;

/* JADX INFO: loaded from: classes.dex */
public class MakerBoxButton extends Button {
    private static final int PRESSED_PADDING_TOP_OFFSET = 6;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private Rect rect;

    public MakerBoxButton(Context context) {
        super(context);
        init();
    }

    public MakerBoxButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MakerBoxButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.paddingLeft = getPaddingLeft();
        this.paddingTop = getPaddingTop();
        this.paddingRight = getPaddingRight();
        this.paddingBottom = getPaddingBottom();
        setTextColor(ResourceResolver.getColorStateListByResourceId("maker_box_action_button_color_selector", getContext()));
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            if (event.getAction() == 0) {
                setPadding(this.paddingLeft, this.paddingTop + 6, this.paddingRight, this.paddingBottom);
                this.rect = new Rect(getLeft(), getTop(), getRight(), getBottom());
            } else if (event.getAction() == 1) {
                setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
            } else if (event.getAction() == 2 && !this.rect.contains(getLeft() + ((int) event.getX()), getTop() + ((int) event.getY()))) {
                setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
            }
        }
        return super.onTouchEvent(event);
    }
}
