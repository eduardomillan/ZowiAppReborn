package com.bq.zowi.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.AttrRes;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.behavior.SwipeDismissBehavior;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bq.zowi.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class EduBar extends LinearLayout {
    private static final int ANIMATION_DURATION = 250;
    private static final int ANIMATION_FADE_DURATION = 180;
    private static final Interpolator ANIM_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int LONG_DURATION_MS = 2750;
    private static final int MESSAGE_THRESHOLD_LENGTH = 50;
    private static final int SHORT_DURATION_MS = 1500;
    private static final String TAG = "EduBar";
    private TextView action;
    private float height;
    private boolean indefinite;
    private TextView message;

    public EduBar(Context context) {
        this(context, null);
    }

    public EduBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EduBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.component_edubar, (ViewGroup) this, true);
        if (attrs != null) {
            readAttrs(attrs, defStyle);
        }
        initialize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isMessageLong(String message) {
        return message.length() > 50;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.message = (TextView) findViewById(R.id.eduBarMessage);
        this.action = (TextView) findViewById(R.id.eduBarAction);
        setOnTouchListener(new View.OnTouchListener() { // from class: com.bq.zowi.components.EduBar.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                return v != EduBar.this.action;
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.indefinite && (getParent() instanceof CoordinatorLayout) && (getLayoutParams() instanceof CoordinatorLayout.LayoutParams)) {
            ((CoordinatorLayout.LayoutParams) getLayoutParams()).setBehavior(new Behavior());
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public void show(@StringRes int messageResId) {
        if (!isVisible()) {
            show(getContext().getString(messageResId));
        }
    }

    public void show(@NotNull String message) {
        if (!isVisible()) {
            show(message, (String) null, (View.OnClickListener) null);
        }
    }

    public void show(@StringRes int messageResId, @StringRes int actionTextId, @Nullable View.OnClickListener listener) {
        if (!isVisible()) {
            show(getContext().getString(messageResId), getContext().getString(actionTextId), listener);
        }
    }

    public void show(@NotNull final String message, @Nullable String actionMessage, @Nullable View.OnClickListener listener) {
        if (!isVisible()) {
            this.message.setText(message);
            if (actionMessage != null && listener != null) {
                this.action.setText(actionMessage);
                this.action.setOnClickListener(listener);
                this.action.setVisibility(0);
            } else {
                this.action.setVisibility(8);
            }
            setVisibility(0);
            if (!this.indefinite) {
                animate().translationY(0.0f).setDuration(250L).setInterpolator(ANIM_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.bq.zowi.components.EduBar.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        EduBar.this.postDelayed(new Runnable() { // from class: com.bq.zowi.components.EduBar.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EduBar.this.hide();
                            }
                        }, EduBar.isMessageLong(message) ? 2750L : 1500L);
                    }
                });
            }
        }
    }

    public void hide() {
        if (isVisible()) {
            animate().translationY(this.height).setDuration(180L).setListener(new AnimatorListenerAdapter() { // from class: com.bq.zowi.components.EduBar.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    EduBar.this.animate().setStartDelay(0L);
                    EduBar.this.setVisibility(8);
                }
            }).setInterpolator(ANIM_INTERPOLATOR);
        }
    }

    private void readAttrs(AttributeSet attrs, @AttrRes int defStyleAttr) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EduBar, defStyleAttr, 0);
        try {
            this.height = typedArray.getDimension(0, getResources().getDimension(R.dimen.edubar_height));
            this.indefinite = typedArray.getBoolean(1, false);
        } finally {
            typedArray.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize() {
        if (isInEditMode()) {
            setVisibility(0);
            setAlpha(1.0f);
            return;
        }
        setVisibility(8);
        setAlpha(1.0f);
        if (!this.indefinite) {
            setTranslationY(this.height);
        }
    }

    private static final class Behavior extends SwipeDismissBehavior<EduBar> {
        private Behavior() {
            setListener(new SwipeDismissBehavior.OnDismissListener() { // from class: com.bq.zowi.components.EduBar.Behavior.1
                @Override // com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener
                public void onDismiss(View view) {
                    if (view instanceof EduBar) {
                        ((EduBar) view).initialize();
                    }
                }

                @Override // com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener
                public void onDragStateChanged(int i) {
                }
            });
        }
    }
}
