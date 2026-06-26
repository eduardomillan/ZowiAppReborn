package com.h6ah4i.android.widget.advrecyclerview.touchguard;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewTouchActionGuardManager {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "ARVTouchActionGuardMgr";
    private boolean mEnabled;
    private boolean mGuarding;
    private int mInitialTouchY;
    private boolean mInterceptScrollingWhileAnimationRunning;
    private RecyclerView.OnItemTouchListener mInternalUseOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewTouchActionGuardManager.this.onInterceptTouchEvent(rv, e);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            RecyclerViewTouchActionGuardManager.this.onTouchEvent(rv, e);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    };
    private int mLastTouchY;
    private RecyclerView mRecyclerView;
    private int mTouchSlop;

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    public void attachRecyclerView(@NonNull RecyclerView rv) {
        if (rv == null) {
            throw new IllegalArgumentException("RecyclerView cannot be null");
        }
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        }
        if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        }
        this.mRecyclerView = rv;
        this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        this.mTouchSlop = ViewConfiguration.get(rv.getContext()).getScaledTouchSlop();
    }

    public void release() {
        if (this.mRecyclerView != null && this.mInternalUseOnItemTouchListener != null) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        this.mRecyclerView = null;
    }

    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (!this.mEnabled) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(e);
        switch (action) {
            case 0:
                handleActionDown(e);
                break;
            case 1:
            case 3:
                handleActionUpOrCancel();
                break;
            case 2:
                if (handleActionMove(rv, e)) {
                }
                break;
        }
        return false;
    }

    void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mEnabled) {
            int action = MotionEventCompat.getActionMasked(e);
            switch (action) {
                case 1:
                case 3:
                    handleActionUpOrCancel();
                    break;
            }
        }
    }

    private boolean handleActionMove(RecyclerView rv, MotionEvent e) {
        if (!this.mGuarding) {
            this.mLastTouchY = (int) (e.getY() + 0.5f);
            int distance = this.mLastTouchY - this.mInitialTouchY;
            if (this.mInterceptScrollingWhileAnimationRunning && Math.abs(distance) > this.mTouchSlop && isAnimationRunning(rv)) {
                this.mGuarding = true;
            }
        }
        return this.mGuarding;
    }

    private static boolean isAnimationRunning(RecyclerView rv) {
        RecyclerView.ItemAnimator itemAnimator = rv.getItemAnimator();
        return itemAnimator != null && itemAnimator.isRunning();
    }

    private void handleActionUpOrCancel() {
        this.mGuarding = false;
        this.mInitialTouchY = 0;
        this.mLastTouchY = 0;
    }

    private void handleActionDown(MotionEvent e) {
        int y = (int) (e.getY() + 0.5f);
        this.mLastTouchY = y;
        this.mInitialTouchY = y;
        this.mGuarding = false;
    }

    public void setEnabled(boolean enabled) {
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            if (!this.mEnabled) {
                handleActionUpOrCancel();
            }
        }
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setInterceptVerticalScrollingWhileAnimationRunning(boolean enabled) {
        this.mInterceptScrollingWhileAnimationRunning = enabled;
    }

    public boolean isInterceptScrollingWhileAnimationRunning() {
        return this.mInterceptScrollingWhileAnimationRunning;
    }
}
