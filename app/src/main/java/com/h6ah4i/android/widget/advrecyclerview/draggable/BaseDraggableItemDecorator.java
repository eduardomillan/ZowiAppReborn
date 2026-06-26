package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
abstract class BaseDraggableItemDecorator extends RecyclerView.ItemDecoration {
    private static final int RETURN_TO_DEFAULT_POS_ANIMATE_THRESHOLD_DP = 2;
    private static final int RETURN_TO_DEFAULT_POS_ANIMATE_THRESHOLD_MSEC = 20;
    protected RecyclerView.ViewHolder mDraggingItemViewHolder;
    protected RecyclerView mRecyclerView;
    private int mReturnToDefaultPositionAnimateThreshold;
    private int mReturnToDefaultPositionDuration = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private Interpolator mReturnToDefaultPositionInterpolator;

    public BaseDraggableItemDecorator(RecyclerView recyclerView, RecyclerView.ViewHolder draggingItemViewHolder) {
        this.mRecyclerView = recyclerView;
        this.mDraggingItemViewHolder = draggingItemViewHolder;
        float displayDensity = recyclerView.getResources().getDisplayMetrics().density;
        this.mReturnToDefaultPositionAnimateThreshold = (int) ((2.0f * displayDensity) + 0.5f);
    }

    public void setReturnToDefaultPositionAnimationDuration(int duration) {
        this.mReturnToDefaultPositionDuration = duration;
    }

    public void setReturnToDefaultPositionAnimationInterpolator(Interpolator interpolator) {
        this.mReturnToDefaultPositionInterpolator = interpolator;
    }

    protected void moveToDefaultPosition(View targetView, boolean animate) {
        int curTranslationX = (int) ViewCompat.getTranslationX(targetView);
        int curTranslationY = (int) ViewCompat.getTranslationY(targetView);
        int halfItemWidth = targetView.getWidth() / 2;
        int halfItemHeight = targetView.getHeight() / 2;
        float translationProportionX = halfItemWidth > 0 ? Math.abs(curTranslationX / halfItemWidth) : 0.0f;
        float translationProportionY = halfItemHeight > 0 ? Math.abs(curTranslationY / halfItemHeight) : 0.0f;
        float tx = 1.0f - Math.min(translationProportionX, 1.0f);
        float ty = 1.0f - Math.min(translationProportionY, 1.0f);
        int animDurationX = (int) ((this.mReturnToDefaultPositionDuration * (1.0f - (tx * tx))) + 0.5f);
        int animDurationY = (int) ((this.mReturnToDefaultPositionDuration * (1.0f - (ty * ty))) + 0.5f);
        int animDuration = Math.max(animDurationX, animDurationY);
        int maxAbsTranslation = Math.max(Math.abs(curTranslationX), Math.abs(curTranslationY));
        if (supportsViewPropertyAnimation() && animate && animDuration > 20 && maxAbsTranslation > this.mReturnToDefaultPositionAnimateThreshold) {
            final ViewPropertyAnimatorCompat animator = ViewCompat.animate(targetView);
            animator.cancel();
            animator.setDuration(animDuration);
            animator.setInterpolator(this.mReturnToDefaultPositionInterpolator);
            animator.translationX(0.0f);
            animator.translationY(0.0f);
            animator.setListener(new ViewPropertyAnimatorListener() { // from class: com.h6ah4i.android.widget.advrecyclerview.draggable.BaseDraggableItemDecorator.1
                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                    animator.setListener(null);
                    ViewCompat.setTranslationX(view, 0.0f);
                    ViewCompat.setTranslationY(view, 0.0f);
                    if (view.getParent() instanceof RecyclerView) {
                        ViewCompat.postInvalidateOnAnimation((RecyclerView) view.getParent());
                    }
                }

                @Override // android.support.v4.view.ViewPropertyAnimatorListener
                public void onAnimationCancel(View view) {
                }
            });
            animator.start();
            return;
        }
        ViewCompat.setTranslationX(targetView, 0.0f);
        ViewCompat.setTranslationY(targetView, 0.0f);
    }

    protected static void setItemTranslation(RecyclerView rv, RecyclerView.ViewHolder holder, float x, float y) {
        RecyclerView.ItemAnimator itemAnimator = rv.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimation(holder);
        }
        ViewCompat.setTranslationX(holder.itemView, x);
        ViewCompat.setTranslationY(holder.itemView, y);
    }

    private static boolean supportsViewPropertyAnimation() {
        return Build.VERSION.SDK_INT >= 11;
    }
}
