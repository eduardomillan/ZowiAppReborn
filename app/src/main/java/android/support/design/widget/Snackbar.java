package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SnackbarManager;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class Snackbar {
    private static final int ANIMATION_DURATION = 250;
    private static final int ANIMATION_FADE_DURATION = 180;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    private static final int MSG_DISMISS = 1;
    private static final int MSG_SHOW = 0;
    private static final Handler sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: android.support.design.widget.Snackbar.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ((Snackbar) message.obj).showView();
                    break;
                case 1:
                    ((Snackbar) message.obj).hideView();
                    break;
            }
            return true;
        }
    });
    private final Context mContext;
    private int mDuration;
    private final SnackbarManager.Callback mManagerCallback = new SnackbarManager.Callback() { // from class: android.support.design.widget.Snackbar.3
        @Override // android.support.design.widget.SnackbarManager.Callback
        public void show() {
            Snackbar.sHandler.sendMessage(Snackbar.sHandler.obtainMessage(0, Snackbar.this));
        }

        @Override // android.support.design.widget.SnackbarManager.Callback
        public void dismiss() {
            Snackbar.sHandler.sendMessage(Snackbar.sHandler.obtainMessage(1, Snackbar.this));
        }
    };
    private final ViewGroup mParent;
    private final SnackbarLayout mView;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    Snackbar(ViewGroup parent) {
        this.mParent = parent;
        this.mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        this.mView = (SnackbarLayout) inflater.inflate(R.layout.layout_snackbar, this.mParent, false);
    }

    public static Snackbar make(View view, CharSequence text, int duration) {
        Snackbar snackbar = new Snackbar(findSuitableParent(view));
        snackbar.setText(text);
        snackbar.setDuration(duration);
        return snackbar;
    }

    public static Snackbar make(View view, int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        while (!(view instanceof CoordinatorLayout)) {
            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup) view;
                }
                fallback = (ViewGroup) view;
            }
            if (view != null) {
                Object parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            if (view == null) {
                return fallback;
            }
        }
        return (ViewGroup) view;
    }

    public Snackbar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(this.mContext.getText(resId), listener);
    }

    public Snackbar setAction(CharSequence text, final View.OnClickListener listener) {
        TextView tv = this.mView.getActionView();
        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(8);
            tv.setOnClickListener(null);
        } else {
            tv.setVisibility(0);
            tv.setText(text);
            tv.setOnClickListener(new View.OnClickListener() { // from class: android.support.design.widget.Snackbar.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    listener.onClick(view);
                    Snackbar.this.dismiss();
                }
            });
        }
        return this;
    }

    public Snackbar setActionTextColor(ColorStateList colors) {
        TextView tv = this.mView.getActionView();
        tv.setTextColor(colors);
        return this;
    }

    public Snackbar setActionTextColor(int color) {
        TextView tv = this.mView.getActionView();
        tv.setTextColor(color);
        return this;
    }

    public Snackbar setText(CharSequence message) {
        TextView tv = this.mView.getMessageView();
        tv.setText(message);
        return this;
    }

    public Snackbar setText(@StringRes int resId) {
        return setText(this.mContext.getText(resId));
    }

    public Snackbar setDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public View getView() {
        return this.mView;
    }

    public void show() {
        SnackbarManager.getInstance().show(this.mDuration, this.mManagerCallback);
    }

    public void dismiss() {
        SnackbarManager.getInstance().dismiss(this.mManagerCallback);
    }

    final void showView() {
        if (this.mView.getParent() == null) {
            ViewGroup.LayoutParams lp = this.mView.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(0);
                behavior.setListener(new SwipeDismissBehavior.OnDismissListener() { // from class: android.support.design.widget.Snackbar.4
                    @Override // android.support.design.widget.SwipeDismissBehavior.OnDismissListener
                    public void onDismiss(View view) {
                        Snackbar.this.dismiss();
                    }

                    @Override // android.support.design.widget.SwipeDismissBehavior.OnDismissListener
                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case 0:
                                SnackbarManager.getInstance().restoreTimeout(Snackbar.this.mManagerCallback);
                                break;
                            case 1:
                            case 2:
                                SnackbarManager.getInstance().cancelTimeout(Snackbar.this.mManagerCallback);
                                break;
                        }
                    }
                });
                ((CoordinatorLayout.LayoutParams) lp).setBehavior(behavior);
            }
            this.mParent.addView(this.mView);
        }
        if (ViewCompat.isLaidOut(this.mView)) {
            animateViewIn();
        } else {
            this.mView.setOnLayoutChangeListener(new SnackbarLayout.OnLayoutChangeListener() { // from class: android.support.design.widget.Snackbar.5
                @Override // android.support.design.widget.Snackbar.SnackbarLayout.OnLayoutChangeListener
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    Snackbar.this.animateViewIn();
                    Snackbar.this.mView.setOnLayoutChangeListener(null);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateViewIn() {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.setTranslationY(this.mView, this.mView.getHeight());
            ViewCompat.animate(this.mView).translationY(0.0f).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250L).setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: android.support.design.widget.Snackbar.6
                @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                    Snackbar.this.mView.animateChildrenIn(70, Snackbar.ANIMATION_FADE_DURATION);
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                    SnackbarManager.getInstance().onShown(Snackbar.this.mManagerCallback);
                }
            }).start();
            return;
        }
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.snackbar_in);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250L);
        anim.setAnimationListener(new Animation.AnimationListener() { // from class: android.support.design.widget.Snackbar.7
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SnackbarManager.getInstance().onShown(Snackbar.this.mManagerCallback);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mView.startAnimation(anim);
    }

    private void animateViewOut() {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(this.mView).translationY(this.mView.getHeight()).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setDuration(250L).setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: android.support.design.widget.Snackbar.8
                @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                    Snackbar.this.mView.animateChildrenOut(0, Snackbar.ANIMATION_FADE_DURATION);
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListenerAdapter, android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                    Snackbar.this.onViewHidden();
                }
            }).start();
            return;
        }
        Animation anim = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.snackbar_out);
        anim.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(250L);
        anim.setAnimationListener(new Animation.AnimationListener() { // from class: android.support.design.widget.Snackbar.9
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                Snackbar.this.onViewHidden();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mView.startAnimation(anim);
    }

    final void hideView() {
        if (this.mView.getVisibility() != 0 || isBeingDragged()) {
            onViewHidden();
        } else {
            animateViewOut();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onViewHidden() {
        this.mParent.removeView(this.mView);
        SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
    }

    private boolean isBeingDragged() {
        ViewGroup.LayoutParams lp = this.mView.getLayoutParams();
        if (!(lp instanceof CoordinatorLayout.LayoutParams)) {
            return false;
        }
        CoordinatorLayout.LayoutParams cllp = (CoordinatorLayout.LayoutParams) lp;
        CoordinatorLayout.Behavior behavior = cllp.getBehavior();
        return (behavior instanceof SwipeDismissBehavior) && ((SwipeDismissBehavior) behavior).getDragState() != 0;
    }

    public static class SnackbarLayout extends LinearLayout {
        private TextView mActionView;
        private int mMaxInlineActionWidth;
        private int mMaxWidth;
        private TextView mMessageView;
        private OnLayoutChangeListener mOnLayoutChangeListener;

        interface OnLayoutChangeListener {
            void onLayoutChange(View view, int i, int i2, int i3, int i4);
        }

        public SnackbarLayout(Context context) {
            this(context, null);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
            this.mMaxWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
            this.mMaxInlineActionWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
            if (a.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, a.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            a.recycle();
            setClickable(true);
            LayoutInflater.from(context).inflate(R.layout.layout_snackbar_include, this);
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            super.onFinishInflate();
            this.mMessageView = (TextView) findViewById(R.id.snackbar_text);
            this.mActionView = (TextView) findViewById(R.id.snackbar_action);
        }

        TextView getMessageView() {
            return this.mMessageView;
        }

        TextView getActionView() {
            return this.mActionView;
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.mMaxWidth > 0 && getMeasuredWidth() > this.mMaxWidth) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, 1073741824);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
            int multiLineVPadding = getResources().getDimensionPixelSize(R.dimen.snackbar_padding_vertical_2lines);
            int singleLineVPadding = getResources().getDimensionPixelSize(R.dimen.snackbar_padding_vertical);
            boolean isMultiLine = this.mMessageView.getLayout().getLineCount() > 1;
            boolean remeasure = false;
            if (isMultiLine && this.mMaxInlineActionWidth > 0 && this.mActionView.getMeasuredWidth() > this.mMaxInlineActionWidth) {
                if (updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                    remeasure = true;
                }
            } else {
                int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
                if (updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                    remeasure = true;
                }
            }
            if (remeasure) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        void animateChildrenIn(int delay, int duration) {
            ViewCompat.setAlpha(this.mMessageView, 0.0f);
            ViewCompat.animate(this.mMessageView).alpha(1.0f).setDuration(duration).setStartDelay(delay).start();
            if (this.mActionView.getVisibility() == 0) {
                ViewCompat.setAlpha(this.mActionView, 0.0f);
                ViewCompat.animate(this.mActionView).alpha(1.0f).setDuration(duration).setStartDelay(delay).start();
            }
        }

        void animateChildrenOut(int delay, int duration) {
            ViewCompat.setAlpha(this.mMessageView, 1.0f);
            ViewCompat.animate(this.mMessageView).alpha(0.0f).setDuration(duration).setStartDelay(delay).start();
            if (this.mActionView.getVisibility() == 0) {
                ViewCompat.setAlpha(this.mActionView, 1.0f);
                ViewCompat.animate(this.mActionView).alpha(0.0f).setDuration(duration).setStartDelay(delay).start();
            }
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (changed && this.mOnLayoutChangeListener != null) {
                this.mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
            this.mOnLayoutChangeListener = onLayoutChangeListener;
        }

        private boolean updateViewsWithinLayout(int orientation, int messagePadTop, int messagePadBottom) {
            boolean changed = false;
            if (orientation != getOrientation()) {
                setOrientation(orientation);
                changed = true;
            }
            if (this.mMessageView.getPaddingTop() != messagePadTop || this.mMessageView.getPaddingBottom() != messagePadBottom) {
                updateTopBottomPadding(this.mMessageView, messagePadTop, messagePadBottom);
                return true;
            }
            return changed;
        }

        private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
            if (ViewCompat.isPaddingRelative(view)) {
                ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
            } else {
                view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
            }
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarLayout> {
        Behavior() {
        }

        @Override // android.support.design.widget.SwipeDismissBehavior, android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onInterceptTouchEvent(CoordinatorLayout parent, SnackbarLayout child, MotionEvent event) {
            if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                switch (event.getActionMasked()) {
                    case 0:
                        SnackbarManager.getInstance().cancelTimeout(Snackbar.this.mManagerCallback);
                        break;
                    case 1:
                    case 3:
                        SnackbarManager.getInstance().restoreTimeout(Snackbar.this.mManagerCallback);
                        break;
                }
            }
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }
}
