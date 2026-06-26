package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

/* JADX INFO: loaded from: classes.dex */
class DraggingItemDecorator extends BaseDraggableItemDecorator {
    private static final String TAG = "DraggingItemDecorator";
    private Bitmap mDraggingItemImage;
    private DraggingItemInfo mDraggingItemInfo;
    private boolean mIsScrolling;
    private int mLayoutOrientation;
    private ItemDraggableRange mRange;
    private NinePatchDrawable mShadowDrawable;
    private Rect mShadowPadding;
    private boolean mStarted;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private int mTranslationBottomLimit;
    private int mTranslationLeftLimit;
    private int mTranslationRightLimit;
    private int mTranslationTopLimit;
    private int mTranslationX;
    private int mTranslationY;

    public DraggingItemDecorator(RecyclerView recyclerView, RecyclerView.ViewHolder draggingItem, ItemDraggableRange range) {
        super(recyclerView, draggingItem);
        this.mShadowPadding = new Rect();
        this.mRange = range;
    }

    private static int clip(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    private static View findRangeFirstItem(RecyclerView rv, ItemDraggableRange range, int firstVisiblePosition, int lastVisiblePosition) {
        int position;
        if (firstVisiblePosition == -1 || lastVisiblePosition == -1) {
            return null;
        }
        int childCount = rv.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v2 = rv.getChildAt(i);
            RecyclerView.ViewHolder vh = rv.getChildViewHolder(v2);
            if (vh != null && (position = vh.getLayoutPosition()) >= firstVisiblePosition && position <= lastVisiblePosition && range.checkInRange(position)) {
                return v2;
            }
        }
        return null;
    }

    private static View findRangeLastItem(RecyclerView rv, ItemDraggableRange range, int firstVisiblePosition, int lastVisiblePosition) {
        int position;
        if (firstVisiblePosition == -1 || lastVisiblePosition == -1) {
            return null;
        }
        int childCount = rv.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View v2 = rv.getChildAt(i);
            RecyclerView.ViewHolder vh = rv.getChildViewHolder(v2);
            if (vh != null && (position = vh.getLayoutPosition()) >= firstVisiblePosition && position <= lastVisiblePosition && range.checkInRange(position)) {
                return v2;
            }
        }
        return null;
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (this.mDraggingItemImage != null) {
            float left = (this.mTranslationX + this.mDraggingItemInfo.margins.left) - this.mShadowPadding.left;
            float top = this.mTranslationY - this.mShadowPadding.top;
            c.drawBitmap(this.mDraggingItemImage, left, top, (Paint) null);
        }
    }

    public void start(MotionEvent e, DraggingItemInfo draggingItemInfo) {
        if (!this.mStarted) {
            View itemView = this.mDraggingItemViewHolder.itemView;
            this.mDraggingItemInfo = draggingItemInfo;
            this.mDraggingItemImage = createDraggingItemImage(itemView, this.mShadowDrawable);
            this.mTranslationLeftLimit = this.mRecyclerView.getPaddingLeft();
            this.mTranslationTopLimit = this.mRecyclerView.getPaddingTop();
            this.mLayoutOrientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
            itemView.setVisibility(4);
            update(e);
            this.mRecyclerView.addItemDecoration(this);
            this.mStarted = true;
        }
    }

    public void finish(boolean animate) {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        RecyclerView.ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        this.mRecyclerView.stopScroll();
        updateDraggingItemPosition(this.mTranslationX, this.mTranslationY);
        if (this.mDraggingItemViewHolder != null) {
            moveToDefaultPosition(this.mDraggingItemViewHolder.itemView, animate);
        }
        if (this.mDraggingItemViewHolder != null) {
            this.mDraggingItemViewHolder.itemView.setVisibility(0);
        }
        this.mDraggingItemViewHolder = null;
        if (this.mDraggingItemImage != null) {
            this.mDraggingItemImage.recycle();
            this.mDraggingItemImage = null;
        }
        this.mRange = null;
        this.mTranslationX = 0;
        this.mTranslationY = 0;
        this.mTranslationLeftLimit = 0;
        this.mTranslationRightLimit = 0;
        this.mTranslationTopLimit = 0;
        this.mTranslationBottomLimit = 0;
        this.mTouchPositionX = 0;
        this.mTouchPositionY = 0;
        this.mStarted = false;
    }

    public void update(MotionEvent e) {
        this.mTouchPositionX = (int) (e.getX() + 0.5f);
        this.mTouchPositionY = (int) (e.getY() + 0.5f);
        refresh();
    }

    public void refresh() {
        updateTranslationOffset();
        updateDraggingItemPosition(this.mTranslationX, this.mTranslationY);
        ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
    }

    public void setShadowDrawable(NinePatchDrawable shadowDrawable) {
        this.mShadowDrawable = shadowDrawable;
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.getPadding(this.mShadowPadding);
        }
    }

    public int getDraggingItemTranslationY() {
        return this.mTranslationY;
    }

    public int getDraggingItemTranslationX() {
        return this.mTranslationX;
    }

    private void updateTranslationOffset() {
        RecyclerView rv = this.mRecyclerView;
        int childCount = rv.getChildCount();
        if (childCount > 0) {
            this.mTranslationLeftLimit = 0;
            this.mTranslationRightLimit = rv.getWidth() - this.mDraggingItemInfo.width;
            this.mTranslationTopLimit = 0;
            this.mTranslationBottomLimit = rv.getHeight() - this.mDraggingItemInfo.height;
            switch (this.mLayoutOrientation) {
                case 0:
                    this.mTranslationTopLimit += rv.getPaddingTop();
                    this.mTranslationBottomLimit -= rv.getPaddingBottom();
                    break;
                case 1:
                    this.mTranslationLeftLimit += rv.getPaddingLeft();
                    this.mTranslationRightLimit -= rv.getPaddingRight();
                    break;
            }
            this.mTranslationRightLimit = Math.max(this.mTranslationLeftLimit, this.mTranslationRightLimit);
            this.mTranslationBottomLimit = Math.max(this.mTranslationTopLimit, this.mTranslationBottomLimit);
            if (!this.mIsScrolling) {
                int firstVisiblePosition = CustomRecyclerViewUtils.findFirstVisibleItemPosition(rv, true);
                int lastVisiblePosition = CustomRecyclerViewUtils.findLastVisibleItemPosition(rv, true);
                View firstChild = findRangeFirstItem(rv, this.mRange, firstVisiblePosition, lastVisiblePosition);
                View lastChild = findRangeLastItem(rv, this.mRange, firstVisiblePosition, lastVisiblePosition);
                switch (this.mLayoutOrientation) {
                    case 0:
                        if (firstChild != null) {
                            this.mTranslationLeftLimit = Math.min(this.mTranslationLeftLimit, firstChild.getLeft());
                        }
                        if (lastChild != null) {
                            this.mTranslationRightLimit = Math.min(this.mTranslationRightLimit, lastChild.getLeft());
                        }
                        break;
                    case 1:
                        if (firstChild != null) {
                            this.mTranslationTopLimit = Math.min(this.mTranslationBottomLimit, firstChild.getTop());
                        }
                        if (lastChild != null) {
                            this.mTranslationBottomLimit = Math.min(this.mTranslationBottomLimit, lastChild.getTop());
                        }
                        break;
                }
            }
        } else {
            int paddingLeft = rv.getPaddingLeft();
            this.mTranslationLeftLimit = paddingLeft;
            this.mTranslationRightLimit = paddingLeft;
            int paddingTop = rv.getPaddingTop();
            this.mTranslationTopLimit = paddingTop;
            this.mTranslationBottomLimit = paddingTop;
        }
        this.mTranslationX = this.mTouchPositionX - this.mDraggingItemInfo.grabbedPositionX;
        this.mTranslationY = this.mTouchPositionY - this.mDraggingItemInfo.grabbedPositionY;
        this.mTranslationX = clip(this.mTranslationX, this.mTranslationLeftLimit, this.mTranslationRightLimit);
        this.mTranslationY = clip(this.mTranslationY, this.mTranslationTopLimit, this.mTranslationBottomLimit);
    }

    private static int toSpanAlignedPosition(int position, int spanCount) {
        if (position == -1) {
            return -1;
        }
        return (position / spanCount) * spanCount;
    }

    public boolean isReachedToTopLimit() {
        return this.mTranslationY == this.mTranslationTopLimit;
    }

    public boolean isReachedToBottomLimit() {
        return this.mTranslationY == this.mTranslationBottomLimit;
    }

    public boolean isReachedToLeftLimit() {
        return this.mTranslationX == this.mTranslationLeftLimit;
    }

    public boolean isReachedToRightLimit() {
        return this.mTranslationX == this.mTranslationRightLimit;
    }

    private Bitmap createDraggingItemImage(View v, NinePatchDrawable shadow) {
        int width = v.getWidth() + this.mShadowPadding.left + this.mShadowPadding.right;
        int height = v.getHeight() + this.mShadowPadding.top + this.mShadowPadding.bottom;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (shadow != null) {
            shadow.setBounds(0, 0, width, height);
            shadow.draw(canvas);
        }
        int savedCount = canvas.save(3);
        canvas.clipRect(this.mShadowPadding.left, this.mShadowPadding.top, width - this.mShadowPadding.right, height - this.mShadowPadding.bottom);
        canvas.translate(this.mShadowPadding.left, this.mShadowPadding.top);
        v.draw(canvas);
        canvas.restoreToCount(savedCount);
        return bitmap;
    }

    private void updateDraggingItemPosition(float translationX, int translationY) {
        if (this.mDraggingItemViewHolder != null) {
            setItemTranslation(this.mRecyclerView, this.mDraggingItemViewHolder, translationX - this.mDraggingItemViewHolder.itemView.getLeft(), translationY - this.mDraggingItemViewHolder.itemView.getTop());
        }
    }

    public void setIsScrolling(boolean isScrolling) {
        if (this.mIsScrolling != isScrolling) {
            this.mIsScrolling = isScrolling;
        }
    }

    public int getTranslatedItemPositionTop() {
        return this.mTranslationY;
    }

    public int getTranslatedItemPositionBottom() {
        return this.mTranslationY + this.mDraggingItemInfo.height;
    }

    public int getTranslatedItemPositionLeft() {
        return this.mTranslationX;
    }

    public int getTranslatedItemPositionRight() {
        return this.mTranslationX + this.mDraggingItemInfo.width;
    }

    public void invalidateDraggingItem() {
        if (this.mDraggingItemViewHolder != null) {
            ViewCompat.setTranslationX(this.mDraggingItemViewHolder.itemView, 0.0f);
            ViewCompat.setTranslationY(this.mDraggingItemViewHolder.itemView, 0.0f);
            this.mDraggingItemViewHolder.itemView.setVisibility(0);
        }
        this.mDraggingItemViewHolder = null;
    }

    public void setDraggingItemViewHolder(RecyclerView.ViewHolder holder) {
        if (this.mDraggingItemViewHolder != null) {
            throw new IllegalStateException("A new view holder is attempt to be assigned before invalidating the older one");
        }
        this.mDraggingItemViewHolder = holder;
        holder.itemView.setVisibility(4);
    }
}
