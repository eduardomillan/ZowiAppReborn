package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v4.view.ViewCompat;
import android.support.v7.internal.widget.ActivityChooserView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

/* JADX INFO: loaded from: classes.dex */
class SwipeableItemWrapperAdapter<VH extends RecyclerView.ViewHolder> extends BaseWrapperAdapter<VH> {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVSwipeableWrapper";
    private RecyclerViewSwipeManager mSwipeManager;
    private BaseSwipeableItemAdapter mSwipeableItemAdapter;
    private long mSwipingItemId;

    private interface Constants extends SwipeableItemConstants {
    }

    public SwipeableItemWrapperAdapter(RecyclerViewSwipeManager manager, RecyclerView.Adapter<VH> adapter) {
        super(adapter);
        this.mSwipingItemId = -1L;
        this.mSwipeableItemAdapter = getSwipeableItemAdapter(adapter);
        if (this.mSwipeableItemAdapter == null) {
            throw new IllegalArgumentException("adapter does not implement SwipeableItemAdapter");
        }
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        this.mSwipeManager = manager;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onRelease() {
        super.onRelease();
        this.mSwipeableItemAdapter = null;
        this.mSwipeManager = null;
        this.mSwipingItemId = -1L;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);
        if (this.mSwipingItemId == holder.getItemId()) {
            this.mSwipeManager.cancelSwipe();
        }
        if (holder instanceof SwipeableItemViewHolder) {
            this.mSwipeManager.cancelPendingAnimations(holder);
            setSwipeItemSlideAmount((SwipeableItemViewHolder) holder, 0.0f, swipeHorizontal());
            View containerView = ((SwipeableItemViewHolder) holder).getSwipeableContainerView();
            if (containerView != null) {
                ViewCompat.animate(containerView).cancel();
                ViewCompat.setTranslationX(containerView, 0.0f);
                ViewCompat.setTranslationY(containerView, 0.0f);
            }
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        VH vh = (VH) super.onCreateViewHolder(viewGroup, i);
        if (vh instanceof SwipeableItemViewHolder) {
            ((SwipeableItemViewHolder) vh).setSwipeStateFlags(-1);
        }
        return vh;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(VH holder, int position) {
        float prevSwipeItemSlideAmount = 0.0f;
        if (holder instanceof SwipeableItemViewHolder) {
            prevSwipeItemSlideAmount = getSwipeItemSlideAmount((SwipeableItemViewHolder) holder, swipeHorizontal());
        }
        if (isSwiping()) {
            int flags = 1;
            if (holder.getItemId() == this.mSwipingItemId) {
                flags = 1 | 2;
            }
            safeUpdateFlags(holder, flags);
            super.onBindViewHolder(holder, position);
        } else {
            safeUpdateFlags(holder, 0);
            super.onBindViewHolder(holder, position);
        }
        if (holder instanceof SwipeableItemViewHolder) {
            float swipeItemSlideAmount = getSwipeItemSlideAmount((SwipeableItemViewHolder) holder, swipeHorizontal());
            boolean isSwiping = this.mSwipeManager.isSwiping();
            boolean isAnimationRunning = this.mSwipeManager.isAnimationRunning(holder);
            if (prevSwipeItemSlideAmount != swipeItemSlideAmount || (!isSwiping && !isAnimationRunning)) {
                this.mSwipeManager.applySlideItem(holder, position, prevSwipeItemSlideAmount, swipeItemSlideAmount, swipeHorizontal(), true, isSwiping);
            }
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterChanged() {
        if (isSwiping()) {
            cancelSwipe();
        } else {
            super.onHandleWrappedAdapterChanged();
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        if (isSwiping()) {
            cancelSwipe();
        } else {
            super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        if (isSwiping()) {
            cancelSwipe();
        } else {
            super.onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (isSwiping()) {
            cancelSwipe();
        } else {
            super.onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (isSwiping()) {
            cancelSwipe();
        } else {
            super.onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
        }
    }

    private void cancelSwipe() {
        if (this.mSwipeManager != null) {
            this.mSwipeManager.cancelSwipe();
        }
    }

    int getSwipeReactionType(RecyclerView.ViewHolder holder, int position, int x, int y) {
        return this.mSwipeableItemAdapter.onGetSwipeReactionType(holder, position, x, y);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void onUpdateSlideAmount(RecyclerView.ViewHolder viewHolder, int position, boolean horizontal, float amount, boolean isSwiping, int type) {
        this.mSwipeableItemAdapter.onSetSwipeBackground(viewHolder, position, type);
        SwipeableItemViewHolder swipeableItemViewHolder = (SwipeableItemViewHolder) viewHolder;
        float f = horizontal ? amount : 0.0f;
        if (horizontal) {
            amount = 0.0f;
        }
        swipeableItemViewHolder.onSlideAmountUpdated(f, amount, isSwiping);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void onUpdateSlideAmount(RecyclerView.ViewHolder viewHolder, int position, boolean horizontal, float amount, boolean isSwiping) {
        SwipeableItemViewHolder swipeableItemViewHolder = (SwipeableItemViewHolder) viewHolder;
        float f = horizontal ? amount : 0.0f;
        if (horizontal) {
            amount = 0.0f;
        }
        swipeableItemViewHolder.onSlideAmountUpdated(f, amount, isSwiping);
    }

    void onSwipeItemStarted(RecyclerViewSwipeManager manager, RecyclerView.ViewHolder holder, long id) {
        this.mSwipingItemId = id;
        notifyDataSetChanged();
    }

    SwipeResultAction onSwipeItemFinished(RecyclerView.ViewHolder holder, int position, int result) {
        this.mSwipingItemId = -1L;
        return SwipeableItemInternalUtils.invokeOnSwipeItem(this.mSwipeableItemAdapter, holder, position, result);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void onSwipeItemFinished2(RecyclerView.ViewHolder viewHolder, int position, int result, int afterReaction, SwipeResultAction resultAction) {
        ((SwipeableItemViewHolder) viewHolder).setSwipeResult(result);
        ((SwipeableItemViewHolder) viewHolder).setAfterSwipeReaction(afterReaction);
        setSwipeItemSlideAmount((SwipeableItemViewHolder) viewHolder, getSwipeAmountFromAfterReaction(result, afterReaction), swipeHorizontal());
        resultAction.performAction();
        notifyDataSetChanged();
    }

    protected boolean isSwiping() {
        return this.mSwipingItemId != -1;
    }

    private boolean swipeHorizontal() {
        return this.mSwipeManager.swipeHorizontal();
    }

    private static float getSwipeItemSlideAmount(SwipeableItemViewHolder holder, boolean horizontal) {
        return horizontal ? holder.getSwipeItemHorizontalSlideAmount() : holder.getSwipeItemVerticalSlideAmount();
    }

    private static void setSwipeItemSlideAmount(SwipeableItemViewHolder holder, float amount, boolean horizontal) {
        if (horizontal) {
            holder.setSwipeItemHorizontalSlideAmount(amount);
        } else {
            holder.setSwipeItemVerticalSlideAmount(amount);
        }
    }

    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1093)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:390)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:70)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:23)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:370)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:85)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:33)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:70)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:23)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    private static float getSwipeAmountFromAfterReaction(int r1, int r2) {
        /*
            r0 = 0
            switch(r2) {
                case 0: goto L4;
                case 1: goto L5;
                case 2: goto L5;
                default: goto L4;
            }
        L4:
            return r0
        L5:
            switch(r1) {
                case 2: goto L9;
                case 3: goto Lf;
                case 4: goto Lc;
                case 5: goto L13;
                default: goto L8;
            }
        L8:
            goto L4
        L9:
            r0 = -947912704(0xffffffffc7800000, float:-65536.0)
            goto L4
        Lc:
            r0 = 1199570944(0x47800000, float:65536.0)
            goto L4
        Lf:
            r0 = -947912576(0xffffffffc7800080, float:-65537.0)
            goto L4
        L13:
            r0 = 1199571072(0x47800080, float:65537.0)
            goto L4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemWrapperAdapter.getSwipeAmountFromAfterReaction(int, int):float");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void safeUpdateFlags(RecyclerView.ViewHolder viewHolder, int flags) {
        if (viewHolder instanceof SwipeableItemViewHolder) {
            SwipeableItemViewHolder holder2 = (SwipeableItemViewHolder) viewHolder;
            int curFlags = holder2.getSwipeStateFlags();
            if (curFlags == -1 || ((curFlags ^ flags) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) != 0) {
                flags |= Integer.MIN_VALUE;
            }
            ((SwipeableItemViewHolder) viewHolder).setSwipeStateFlags(flags);
        }
    }

    private static BaseSwipeableItemAdapter getSwipeableItemAdapter(RecyclerView.Adapter adapter) {
        return (BaseSwipeableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, BaseSwipeableItemAdapter.class);
    }
}
