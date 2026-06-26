package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewSwipeManager implements SwipeableItemConstants {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MIN_DISTANCE_TOUCH_SLOP_MUL = 10;
    private static final int SLIDE_ITEM_IMMEDIATELY_SET_TRANSLATION_THRESHOLD_DP = 8;
    private static final String TAG = "ARVSwipeManager";
    private SwipeableItemWrapperAdapter<RecyclerView.ViewHolder> mAdapter;
    private InternalHandler mHandler;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private ItemSlidingAnimator mItemSlideAnimator;
    private OnItemSwipeEventListener mItemSwipeEventListener;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private RecyclerView mRecyclerView;
    private boolean mSwipeHorizontal;
    private RecyclerView.ViewHolder mSwipingItem;
    private SwipingItemOperator mSwipingItemOperator;
    private int mSwipingItemReactionType;
    private int mTouchSlop;
    private int mTouchedItemOffsetX;
    private int mTouchedItemOffsetY;
    private long mReturnToDefaultPositionAnimationDuration = 300;
    private long mMoveToOutsideWindowAnimationDuration = 200;
    private long mCheckingTouchSlop = -1;
    private int mSwipingItemPosition = -1;
    private long mSwipingItemId = -1;
    private Rect mSwipingItemMargins = new Rect();
    private RecyclerView.OnItemTouchListener mInternalUseOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager.1
        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewSwipeManager.this.onInterceptTouchEvent(rv, e);
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            RecyclerViewSwipeManager.this.onTouchEvent(rv, e);
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            RecyclerViewSwipeManager.this.onRequestDisallowInterceptTouchEvent(disallowIntercept);
        }
    };
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private int mLongPressTimeout = ViewConfiguration.getLongPressTimeout();

    public interface OnItemSwipeEventListener {
        void onItemSwipeFinished(int i, int i2, int i3);

        void onItemSwipeStarted(int i);
    }

    public RecyclerView.Adapter createWrappedAdapter(@NonNull RecyclerView.Adapter adapter) {
        if (this.mAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        }
        this.mAdapter = new SwipeableItemWrapperAdapter<>(this, adapter);
        return this.mAdapter;
    }

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
        if (this.mAdapter == null || getSwipeableItemWrapperAdapter(rv) != this.mAdapter) {
            throw new IllegalStateException("adapter is not set properly");
        }
        int layoutOrientation = CustomRecyclerViewUtils.getOrientation(rv);
        if (layoutOrientation == -1) {
            throw new IllegalStateException("failed to determine layout orientation");
        }
        this.mRecyclerView = rv;
        this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        ViewConfiguration vc = ViewConfiguration.get(rv.getContext());
        this.mTouchSlop = vc.getScaledTouchSlop();
        this.mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        this.mItemSlideAnimator = new ItemSlidingAnimator(this.mAdapter);
        this.mItemSlideAnimator.setImmediatelySetTranslationThreshold((int) ((rv.getResources().getDisplayMetrics().density * 8.0f) + 0.5f));
        this.mSwipeHorizontal = layoutOrientation == 1;
        this.mHandler = new InternalHandler(this);
    }

    public void release() {
        if (this.mHandler != null) {
            this.mHandler.release();
            this.mHandler = null;
        }
        if (this.mRecyclerView != null && this.mInternalUseOnItemTouchListener != null) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mItemSlideAnimator != null) {
            this.mItemSlideAnimator.endAnimations();
            this.mItemSlideAnimator = null;
        }
        this.mAdapter = null;
        this.mRecyclerView = null;
    }

    public boolean isSwiping() {
        return (this.mSwipingItem == null || this.mHandler.isCancelSwipeRequested()) ? false : true;
    }

    public void setLongPressTimeout(int longPressTimeout) {
        this.mLongPressTimeout = longPressTimeout;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0008 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean onInterceptTouchEvent(android.support.v7.widget.RecyclerView r4, android.view.MotionEvent r5) {
        /*
            r3 = this;
            r1 = 1
            int r0 = android.support.v4.view.MotionEventCompat.getActionMasked(r5)
            switch(r0) {
                case 0: goto L11;
                case 1: goto La;
                case 2: goto L1b;
                case 3: goto La;
                default: goto L8;
            }
        L8:
            r1 = 0
        L9:
            return r1
        La:
            boolean r2 = r3.handleActionUpOrCancel(r5, r1)
            if (r2 == 0) goto L8
            goto L9
        L11:
            boolean r1 = r3.isSwiping()
            if (r1 != 0) goto L8
            r3.handleActionDown(r4, r5)
            goto L8
        L1b:
            boolean r2 = r3.isSwiping()
            if (r2 == 0) goto L25
            r3.handleActionMoveWhileSwiping(r5)
            goto L9
        L25:
            boolean r2 = r3.handleActionMoveWhileNotSwiping(r4, r5)
            if (r2 == 0) goto L8
            goto L9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager.onInterceptTouchEvent(android.support.v7.widget.RecyclerView, android.view.MotionEvent):boolean");
    }

    void onTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = MotionEventCompat.getActionMasked(e);
        if (isSwiping()) {
            switch (action) {
                case 1:
                case 3:
                    handleActionUpOrCancel(e, true);
                    break;
                case 2:
                    handleActionMoveWhileSwiping(e);
                    break;
            }
        }
    }

    void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            cancelSwipe(true);
        }
    }

    private boolean handleActionDown(RecyclerView rv, MotionEvent e) {
        int itemPosition;
        RecyclerView.Adapter adapter = rv.getAdapter();
        RecyclerView.ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
        if (!(holder instanceof SwipeableItemViewHolder) || (itemPosition = CustomRecyclerViewUtils.getSynchronizedPosition(holder)) < 0 || itemPosition >= adapter.getItemCount() || holder.getItemId() != adapter.getItemId(itemPosition)) {
            return false;
        }
        int touchX = (int) (e.getX() + 0.5f);
        int touchY = (int) (e.getY() + 0.5f);
        View view = holder.itemView;
        int translateX = (int) (ViewCompat.getTranslationX(view) + 0.5f);
        int translateY = (int) (ViewCompat.getTranslationY(view) + 0.5f);
        int viewX = touchX - (view.getLeft() + translateX);
        int viewY = touchY - (view.getTop() + translateY);
        int reactionType = this.mAdapter.getSwipeReactionType(holder, itemPosition, viewX, viewY);
        if (reactionType == 0) {
            return false;
        }
        this.mInitialTouchX = touchX;
        this.mInitialTouchY = touchY;
        this.mCheckingTouchSlop = holder.getItemId();
        this.mSwipingItemReactionType = reactionType;
        if ((16777216 & reactionType) != 0) {
            this.mHandler.startLongPressDetection(e, this.mLongPressTimeout);
        }
        return true;
    }

    private static SwipeableItemWrapperAdapter getSwipeableItemWrapperAdapter(RecyclerView rv) {
        return (SwipeableItemWrapperAdapter) WrapperAdapterUtils.findWrappedAdapter(rv.getAdapter(), SwipeableItemWrapperAdapter.class);
    }

    private boolean handleActionUpOrCancel(MotionEvent e, boolean invokeFinish) {
        int action = 3;
        if (e != null) {
            action = MotionEventCompat.getActionMasked(e);
            this.mLastTouchX = (int) (e.getX() + 0.5f);
            this.mLastTouchY = (int) (e.getY() + 0.5f);
        }
        if (isSwiping()) {
            if (invokeFinish) {
                handleActionUpOrCancelWhileSwiping(action);
            }
            return true;
        }
        handleActionUpOrCancelWhileNotSwiping();
        return false;
    }

    private void handleActionUpOrCancelWhileNotSwiping() {
        this.mHandler.cancelLongPressDetection();
        this.mCheckingTouchSlop = -1L;
        this.mSwipingItemReactionType = 0;
    }

    private void handleActionUpOrCancelWhileSwiping(int action) {
        int result = 1;
        if (action == 1) {
            boolean horizontal = this.mSwipeHorizontal;
            View itemView = this.mSwipingItem.itemView;
            int viewSize = horizontal ? itemView.getWidth() : itemView.getHeight();
            float distance = horizontal ? this.mLastTouchX - this.mInitialTouchX : this.mLastTouchY - this.mInitialTouchY;
            float absDistance = Math.abs(distance);
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxFlingVelocity);
            float velocity = horizontal ? this.mVelocityTracker.getXVelocity() : this.mVelocityTracker.getYVelocity();
            float absVelocity = Math.abs(velocity);
            if (absDistance > this.mTouchSlop * 10 && distance * velocity > 0.0f && absVelocity <= this.mMaxFlingVelocity && (absDistance > viewSize / 2 || absVelocity >= this.mMinFlingVelocity)) {
                if (horizontal && distance < 0.0f && SwipeReactionUtils.canSwipeLeft(this.mSwipingItemReactionType)) {
                    result = 2;
                } else if (!horizontal && distance < 0.0f && SwipeReactionUtils.canSwipeUp(this.mSwipingItemReactionType)) {
                    result = 3;
                } else if (horizontal && distance > 0.0f && SwipeReactionUtils.canSwipeRight(this.mSwipingItemReactionType)) {
                    result = 4;
                } else if (!horizontal && distance > 0.0f && SwipeReactionUtils.canSwipeDown(this.mSwipingItemReactionType)) {
                    result = 5;
                }
            }
        }
        finishSwiping(result);
    }

    private boolean handleActionMoveWhileNotSwiping(RecyclerView rv, MotionEvent e) {
        int scrollAxisDelta;
        int swipeAxisDelta;
        boolean dirMasked;
        if (this.mCheckingTouchSlop == -1) {
            return false;
        }
        int dx = ((int) (e.getX() + 0.5f)) - this.mInitialTouchX;
        int dy = ((int) (e.getY() + 0.5f)) - this.mInitialTouchY;
        if (this.mSwipeHorizontal) {
            scrollAxisDelta = dy;
            swipeAxisDelta = dx;
        } else {
            scrollAxisDelta = dx;
            swipeAxisDelta = dy;
        }
        if (Math.abs(scrollAxisDelta) > this.mTouchSlop) {
            this.mCheckingTouchSlop = -1L;
            return false;
        }
        if (Math.abs(swipeAxisDelta) <= this.mTouchSlop) {
            return false;
        }
        if (this.mSwipeHorizontal) {
            if (swipeAxisDelta < 0) {
                dirMasked = (this.mSwipingItemReactionType & 8) != 0;
            } else {
                dirMasked = (this.mSwipingItemReactionType & 32768) != 0;
            }
        } else if (swipeAxisDelta < 0) {
            dirMasked = (this.mSwipingItemReactionType & 512) != 0;
        } else {
            dirMasked = (this.mSwipingItemReactionType & 2097152) != 0;
        }
        if (dirMasked) {
            this.mCheckingTouchSlop = -1L;
            return false;
        }
        RecyclerView.ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
        if (holder == null || holder.getItemId() != this.mCheckingTouchSlop) {
            this.mCheckingTouchSlop = -1L;
            return false;
        }
        return checkConditionAndStartSwiping(e, holder);
    }

    private void handleActionMoveWhileSwiping(MotionEvent e) {
        this.mLastTouchX = (int) (e.getX() + 0.5f);
        this.mLastTouchY = (int) (e.getY() + 0.5f);
        this.mVelocityTracker.addMovement(e);
        int swipeDistanceX = this.mLastTouchX - this.mTouchedItemOffsetX;
        int swipeDistanceY = this.mLastTouchY - this.mTouchedItemOffsetY;
        this.mSwipingItemPosition = getItemPosition(this.mAdapter, this.mSwipingItemId, this.mSwipingItemPosition);
        this.mSwipingItemOperator.update(this.mSwipingItemPosition, swipeDistanceX, swipeDistanceY);
    }

    private boolean checkConditionAndStartSwiping(MotionEvent e, RecyclerView.ViewHolder holder) {
        int itemPosition = CustomRecyclerViewUtils.getSynchronizedPosition(holder);
        if (itemPosition == -1) {
            return false;
        }
        startSwiping(e, holder, itemPosition);
        return true;
    }

    private void startSwiping(MotionEvent e, RecyclerView.ViewHolder holder, int itemPosition) {
        this.mHandler.cancelLongPressDetection();
        this.mSwipingItem = holder;
        this.mSwipingItemPosition = itemPosition;
        this.mSwipingItemId = this.mAdapter.getItemId(itemPosition);
        this.mLastTouchX = (int) (e.getX() + 0.5f);
        this.mLastTouchY = (int) (e.getY() + 0.5f);
        this.mTouchedItemOffsetX = this.mLastTouchX;
        this.mTouchedItemOffsetY = this.mLastTouchY;
        this.mCheckingTouchSlop = -1L;
        CustomRecyclerViewUtils.getLayoutMargins(holder.itemView, this.mSwipingItemMargins);
        this.mSwipingItemOperator = new SwipingItemOperator(this, this.mSwipingItem, this.mSwipingItemReactionType, this.mSwipeHorizontal);
        this.mSwipingItemOperator.start();
        this.mVelocityTracker.clear();
        this.mVelocityTracker.addMovement(e);
        this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        if (this.mItemSwipeEventListener != null) {
            this.mItemSwipeEventListener.onItemSwipeStarted(itemPosition);
        }
        this.mAdapter.onSwipeItemStarted(this, holder, this.mSwipingItemId);
    }

    private void finishSwiping(int result) {
        boolean slideAnimated;
        RecyclerView.ViewHolder swipingItem = this.mSwipingItem;
        if (swipingItem != null) {
            this.mHandler.removeDeferredCancelSwipeRequest();
            this.mHandler.cancelLongPressDetection();
            if (this.mRecyclerView != null && this.mRecyclerView.getParent() != null) {
                this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(false);
            }
            int itemPosition = getItemPosition(this.mAdapter, this.mSwipingItemId, this.mSwipingItemPosition);
            this.mVelocityTracker.clear();
            this.mSwipingItem = null;
            this.mSwipingItemPosition = -1;
            this.mSwipingItemId = -1L;
            this.mLastTouchX = 0;
            this.mLastTouchY = 0;
            this.mInitialTouchX = 0;
            this.mTouchedItemOffsetX = 0;
            this.mTouchedItemOffsetY = 0;
            this.mCheckingTouchSlop = -1L;
            this.mSwipingItemReactionType = 0;
            if (this.mSwipingItemOperator != null) {
                this.mSwipingItemOperator.finish();
                this.mSwipingItemOperator = null;
            }
            int slideDir = resultCodeToSlideDirection(result);
            SwipeResultAction resultAction = null;
            if (this.mAdapter != null) {
                resultAction = this.mAdapter.onSwipeItemFinished(swipingItem, itemPosition, result);
            }
            if (resultAction == null) {
                resultAction = new SwipeResultActionDefault();
            }
            int afterReaction = resultAction.getResultActionType();
            verifyAfterReaction(result, afterReaction);
            switch (afterReaction) {
                case 0:
                    slideAnimated = this.mItemSlideAnimator.finishSwipeSlideToDefaultPosition(swipingItem, this.mSwipeHorizontal, true, this.mReturnToDefaultPositionAnimationDuration, itemPosition, resultAction);
                    break;
                case 1:
                    RecyclerView.ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
                    long removeAnimationDuration = itemAnimator != null ? itemAnimator.getRemoveDuration() : 0L;
                    if (supportsViewPropertyAnimator()) {
                        long moveAnimationDuration = itemAnimator != null ? itemAnimator.getMoveDuration() : 0L;
                        RemovingItemDecorator decorator = new RemovingItemDecorator(this.mRecyclerView, swipingItem, result, removeAnimationDuration, moveAnimationDuration);
                        decorator.setMoveAnimationInterpolator(SwipeDismissItemAnimator.MOVE_INTERPOLATOR);
                        decorator.start();
                    }
                    slideAnimated = this.mItemSlideAnimator.finishSwipeSlideToOutsideOfWindow(swipingItem, slideDir, true, removeAnimationDuration, itemPosition, resultAction);
                    break;
                case 2:
                    slideAnimated = this.mItemSlideAnimator.finishSwipeSlideToOutsideOfWindow(swipingItem, slideDir, true, this.mMoveToOutsideWindowAnimationDuration, itemPosition, resultAction);
                    break;
                default:
                    throw new IllegalStateException("Unknown after reaction type: " + afterReaction);
            }
            if (this.mAdapter != null) {
                this.mAdapter.onSwipeItemFinished2(swipingItem, itemPosition, result, afterReaction, resultAction);
            }
            if (this.mItemSwipeEventListener != null) {
                this.mItemSwipeEventListener.onItemSwipeFinished(itemPosition, result, afterReaction);
            }
            if (!slideAnimated) {
                resultAction.slideAnimationEnd();
            }
        }
    }

    private static void verifyAfterReaction(int result, int afterReaction) {
        if (afterReaction == 2 || afterReaction == 1) {
            switch (result) {
                case 2:
                case 3:
                case 4:
                case 5:
                    return;
                default:
                    throw new IllegalStateException("Unexpected after reaction has been requested: result = " + result + ", afterReaction = " + afterReaction);
            }
        }
    }

    private static int resultCodeToSlideDirection(int result) {
        switch (result) {
            case 2:
            default:
                return 0;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
        }
    }

    private static int getItemPosition(@Nullable RecyclerView.Adapter adapter, long itemId, int itemPositionGuess) {
        if (adapter == null) {
            return -1;
        }
        int itemCount = adapter.getItemCount();
        if (itemPositionGuess < 0 || itemPositionGuess >= itemCount || adapter.getItemId(itemPositionGuess) != itemId) {
            for (int i = 0; i < itemCount; i++) {
                if (adapter.getItemId(i) == itemId) {
                    return i;
                }
            }
            return -1;
        }
        return itemPositionGuess;
    }

    public void cancelSwipe() {
        cancelSwipe(false);
    }

    void cancelSwipe(boolean immediately) {
        handleActionUpOrCancel(null, false);
        if (immediately) {
            finishSwiping(1);
        } else if (isSwiping()) {
            this.mHandler.requestDeferredCancelSwipe();
        }
    }

    boolean isAnimationRunning(RecyclerView.ViewHolder item) {
        return this.mItemSlideAnimator != null && this.mItemSlideAnimator.isRunning(item);
    }

    private void slideItem(RecyclerView.ViewHolder holder, float amount, boolean horizontal, boolean shouldAnimate) {
        if (amount == -65536.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 0, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
            return;
        }
        if (amount == -65537.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 1, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
            return;
        }
        if (amount == 65536.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 2, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
            return;
        }
        if (amount == 65537.0f) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(holder, 3, shouldAnimate, this.mMoveToOutsideWindowAnimationDuration);
        } else if (amount == 0.0f) {
            this.mItemSlideAnimator.slideToDefaultPosition(holder, horizontal, shouldAnimate, this.mReturnToDefaultPositionAnimationDuration);
        } else {
            this.mItemSlideAnimator.slideToSpecifiedPosition(holder, amount, horizontal);
        }
    }

    public long getReturnToDefaultPositionAnimationDuration() {
        return this.mReturnToDefaultPositionAnimationDuration;
    }

    public void setReturnToDefaultPositionAnimationDuration(long duration) {
        this.mReturnToDefaultPositionAnimationDuration = duration;
    }

    public long getMoveToOutsideWindowAnimationDuration() {
        return this.mMoveToOutsideWindowAnimationDuration;
    }

    public void setMoveToOutsideWindowAnimationDuration(long duration) {
        this.mMoveToOutsideWindowAnimationDuration = duration;
    }

    @Nullable
    public OnItemSwipeEventListener getOnItemSwipeEventListener() {
        return this.mItemSwipeEventListener;
    }

    public void setOnItemSwipeEventListener(@Nullable OnItemSwipeEventListener listener) {
        this.mItemSwipeEventListener = listener;
    }

    boolean swipeHorizontal() {
        return this.mSwipeHorizontal;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void applySlideItem(RecyclerView.ViewHolder viewHolder, int itemPosition, float prevAmount, float amount, boolean horizontal, boolean shouldAnimate, boolean isSwiping) {
        int reqBackgroundType;
        SwipeableItemViewHolder holder2 = (SwipeableItemViewHolder) viewHolder;
        View containerView = holder2.getSwipeableContainerView();
        if (containerView == null) {
            return;
        }
        if (amount != 0.0f) {
            reqBackgroundType = determineBackgroundType(amount, horizontal);
        } else if (prevAmount == 0.0f) {
            reqBackgroundType = 0;
        } else {
            reqBackgroundType = determineBackgroundType(prevAmount, horizontal);
        }
        if (amount == 0.0f) {
            slideItem(viewHolder, amount, horizontal, shouldAnimate);
            this.mAdapter.onUpdateSlideAmount(viewHolder, itemPosition, horizontal, amount, isSwiping, reqBackgroundType);
            return;
        }
        float minLimit = horizontal ? holder2.getMaxLeftSwipeAmount() : holder2.getMaxUpSwipeAmount();
        float maxLimit = horizontal ? holder2.getMaxRightSwipeAmount() : holder2.getMaxDownSwipeAmount();
        float adjustedAmount = Math.max(amount, minLimit);
        float adjustedAmount2 = Math.min(adjustedAmount, maxLimit);
        this.mAdapter.onUpdateSlideAmount(viewHolder, itemPosition, horizontal, amount, isSwiping, reqBackgroundType);
        slideItem(viewHolder, adjustedAmount2, horizontal, shouldAnimate);
    }

    private static int determineBackgroundType(float amount, boolean horizontal) {
        return horizontal ? amount < 0.0f ? 1 : 3 : amount < 0.0f ? 2 : 4;
    }

    void cancelPendingAnimations(RecyclerView.ViewHolder holder) {
        if (this.mItemSlideAnimator != null) {
            this.mItemSlideAnimator.endAnimation(holder);
        }
    }

    int getSwipeContainerViewTranslationX(RecyclerView.ViewHolder holder) {
        return this.mItemSlideAnimator.getSwipeContainerViewTranslationX(holder);
    }

    int getSwipeContainerViewTranslationY(RecyclerView.ViewHolder holder) {
        return this.mItemSlideAnimator.getSwipeContainerViewTranslationY(holder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnLongPress(MotionEvent e) {
        RecyclerView.ViewHolder holder = this.mRecyclerView.findViewHolderForItemId(this.mCheckingTouchSlop);
        if (holder != null) {
            checkConditionAndStartSwiping(e, holder);
        }
    }

    private static boolean supportsViewPropertyAnimator() {
        return Build.VERSION.SDK_INT >= 11;
    }

    private static class InternalHandler extends Handler {
        private static final int MSG_DEFERRED_CANCEL_SWIPE = 2;
        private static final int MSG_LONGPRESS = 1;
        private MotionEvent mDownMotionEvent;
        private RecyclerViewSwipeManager mHolder;

        public InternalHandler(RecyclerViewSwipeManager holder) {
            this.mHolder = holder;
        }

        public void release() {
            removeCallbacks(null);
            this.mHolder = null;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.mHolder.handleOnLongPress(this.mDownMotionEvent);
                    break;
                case 2:
                    this.mHolder.cancelSwipe(true);
                    break;
            }
        }

        public void startLongPressDetection(MotionEvent e, int timeout) {
            cancelLongPressDetection();
            this.mDownMotionEvent = MotionEvent.obtain(e);
            sendEmptyMessageAtTime(1, e.getDownTime() + ((long) timeout));
        }

        public void cancelLongPressDetection() {
            removeMessages(1);
            if (this.mDownMotionEvent != null) {
                this.mDownMotionEvent.recycle();
                this.mDownMotionEvent = null;
            }
        }

        public void removeDeferredCancelSwipeRequest() {
            removeMessages(2);
        }

        public void requestDeferredCancelSwipe() {
            if (!isCancelSwipeRequested()) {
                sendEmptyMessage(2);
            }
        }

        public boolean isCancelSwipeRequested() {
            return hasMessages(2);
        }
    }
}
