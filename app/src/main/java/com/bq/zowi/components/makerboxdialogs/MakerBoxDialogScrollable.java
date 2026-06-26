package com.bq.zowi.components.makerboxdialogs;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.bq.zowi.R;

/* JADX INFO: loaded from: classes.dex */
public class MakerBoxDialogScrollable extends RelativeLayout {
    private static final float SCREEN_HEIGHT_PIXELS = Resources.getSystem().getDisplayMetrics().heightPixels * 3;
    protected Button closeButton;
    protected MakerBox container;
    private boolean showCloseButton;

    public MakerBoxDialogScrollable(Context context) {
        super(context);
        this.showCloseButton = false;
        init(context, null, 0);
    }

    public MakerBoxDialogScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.showCloseButton = false;
        init(context, attrs, 0);
    }

    public MakerBoxDialogScrollable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.showCloseButton = false;
        init(context, attrs, defStyleAttr);
    }

    @Override // android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (this.container == null) {
            super.addView(child, index, params);
        } else {
            this.container.addView(child, index, params);
        }
    }

    @Override // android.view.View
    public void setVisibility(final int visibility) {
        switch (visibility) {
            case 0:
                super.setVisibility(0);
                setAlpha(0.0f);
                this.container.setTranslationY(-SCREEN_HEIGHT_PIXELS);
                this.closeButton.setTranslationY(-SCREEN_HEIGHT_PIXELS);
                animate().alpha(1.0f).setDuration(400L).setStartDelay(100L);
                this.container.animate().translationY(0.0f).setStartDelay(400L).setDuration(250L).setInterpolator(new DecelerateInterpolator());
                this.closeButton.animate().translationY(0.0f).setStartDelay(400L).setDuration(250L).setInterpolator(new DecelerateInterpolator());
                break;
            default:
                this.container.animate().setStartDelay(0L).translationY(-SCREEN_HEIGHT_PIXELS).setDuration(200L);
                this.closeButton.animate().setStartDelay(0L).translationY(-SCREEN_HEIGHT_PIXELS).setDuration(200L);
                final ViewPropertyAnimator hideAnimator = animate().alpha(0.0f).setStartDelay(150L).setDuration(200L);
                hideAnimator.setListener(new Animator.AnimatorListener() { // from class: com.bq.zowi.components.makerboxdialogs.MakerBoxDialogScrollable.1
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        hideAnimator.setListener(null);
                        MakerBoxDialogScrollable.super.setVisibility(visibility);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        hideAnimator.setListener(null);
                        MakerBoxDialogScrollable.super.setVisibility(visibility);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                        hideAnimator.setListener(null);
                        MakerBoxDialogScrollable.super.setVisibility(visibility);
                    }
                });
                break;
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MakerBoxDialog, 0, 0);
        try {
            this.showCloseButton = a.getBoolean(0, false);
            if (!isInEditMode()) {
                LayoutInflater.from(context).inflate(resolveLayoutId("component_makerbox_scrollable", R.layout.component_makerbox_scrollable), this);
                this.container = (MakerBox) findViewById(resolveViewId("makerbox_dialog_container", R.id.makerbox_dialog_container));
                this.closeButton = (Button) findViewById(resolveViewId("makerbox_dialog_close_button", R.id.makerbox_dialog_close_button));
                addListeners();
            }
            invalidateView();
        } finally {
            a.recycle();
        }
    }

    protected final int resolveLayoutId(String layoutName, int fallbackLayoutId) {
        int layoutId = getResources().getIdentifier(layoutName, "layout", getContext().getPackageName());
        return layoutId != 0 ? layoutId : fallbackLayoutId;
    }

    protected final int resolveViewId(String viewIdName, int fallbackViewId) {
        int viewId = getResources().getIdentifier(viewIdName, "id", getContext().getPackageName());
        return viewId != 0 ? viewId : fallbackViewId;
    }

    protected final View findResolvedView(String viewIdName, int fallbackViewId) {
        return findViewById(resolveViewId(viewIdName, fallbackViewId));
    }

    private void addListeners() {
        setOnTouchListener(new View.OnTouchListener() { // from class: com.bq.zowi.components.makerboxdialogs.MakerBoxDialogScrollable.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.closeButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.components.makerboxdialogs.MakerBoxDialogScrollable.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MakerBoxDialogScrollable.this.setVisibility(8);
            }
        });
    }

    private void invalidateView() {
        if (this.closeButton != null) {
            this.closeButton.setVisibility(this.showCloseButton ? 0 : 8);
        }
    }
}
