package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.support.v7.internal.widget.ActivityChooserView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.BaseSwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemInternalUtils;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

/* JADX INFO: loaded from: classes.dex */
class DraggableItemWrapperAdapter<VH extends RecyclerView.ViewHolder> extends BaseWrapperAdapter<VH> implements SwipeableItemAdapter<VH> {
    private static final boolean DEBUG_BYPASS_MOVE_OPERATION_MODE = false;
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGI = true;
    private static final boolean LOCAL_LOGV = false;
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVDraggableWrapper";
    private RecyclerViewDragDropManager mDragDropManager;
    private DraggableItemAdapter mDraggableItemAdapter;
    private ItemDraggableRange mDraggableRange;
    private int mDraggingItemCurrentPosition;
    private DraggingItemInfo mDraggingItemInfo;
    private int mDraggingItemInitialPosition;
    private RecyclerView.ViewHolder mDraggingItemViewHolder;

    private interface Constants extends DraggableItemConstants {
    }

    public DraggableItemWrapperAdapter(RecyclerViewDragDropManager manager, RecyclerView.Adapter<VH> adapter) {
        super(adapter);
        this.mDraggingItemInitialPosition = -1;
        this.mDraggingItemCurrentPosition = -1;
        this.mDraggableItemAdapter = getDraggableItemAdapter(adapter);
        if (getDraggableItemAdapter(adapter) == null) {
            throw new IllegalArgumentException("adapter does not implement DraggableItemAdapter");
        }
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        this.mDragDropManager = manager;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onRelease() {
        super.onRelease();
        this.mDraggingItemViewHolder = null;
        this.mDraggableItemAdapter = null;
        this.mDragDropManager = null;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        VH vh = (VH) super.onCreateViewHolder(viewGroup, i);
        if (vh instanceof DraggableItemViewHolder) {
            ((DraggableItemViewHolder) vh).setDragStateFlags(-1);
        }
        return vh;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(VH holder, int position) {
        if (isDragging()) {
            long draggingItemId = this.mDraggingItemInfo.id;
            long itemId = holder.getItemId();
            int origPosition = convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
            if (itemId == draggingItemId && holder != this.mDraggingItemViewHolder) {
                if (this.mDraggingItemViewHolder == null) {
                    Log.i(TAG, "a new view holder object for the currently dragging item is assigned");
                    this.mDraggingItemViewHolder = holder;
                    this.mDragDropManager.onNewDraggingItemViewBound(holder);
                } else {
                    Log.e(TAG, "an another view holder object for the currently dragging item is assigned");
                }
            }
            int flags = 1;
            if (itemId == draggingItemId) {
                flags = 1 | 2;
            }
            if (this.mDraggableRange.checkInRange(position)) {
                flags |= 4;
            }
            safeUpdateFlags(holder, flags);
            super.onBindViewHolder(holder, origPosition);
            return;
        }
        safeUpdateFlags(holder, 0);
        super.onBindViewHolder(holder, position);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        if (!isDragging()) {
            return super.getItemId(position);
        }
        int origPosition = convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
        return super.getItemId(origPosition);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (!isDragging()) {
            return super.getItemViewType(position);
        }
        int origPosition = convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
        return super.getItemViewType(origPosition);
    }

    protected static int convertToOriginalPosition(int position, int dragInitial, int dragCurrent) {
        if (dragInitial < 0 || dragCurrent < 0) {
            return position;
        }
        if (dragInitial == dragCurrent || ((position < dragInitial && position < dragCurrent) || (position > dragInitial && position > dragCurrent))) {
            return position;
        }
        if (dragCurrent >= dragInitial) {
            return position != dragCurrent ? position + 1 : dragInitial;
        }
        if (position != dragCurrent) {
            return position - 1;
        }
        return dragInitial;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterChanged() {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterChanged();
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
        }
    }

    private boolean shouldCancelDragOnDataUpdated() {
        return isDragging();
    }

    private void cancelDrag() {
        if (this.mDragDropManager != null) {
            this.mDragDropManager.cancelDrag();
        }
    }

    void onDragItemStarted(DraggingItemInfo draggingItemInfo, RecyclerView.ViewHolder holder, ItemDraggableRange range) {
        if (holder.getItemId() == -1) {
            throw new IllegalStateException("dragging target must provides valid ID");
        }
        int adapterPosition = holder.getAdapterPosition();
        this.mDraggingItemCurrentPosition = adapterPosition;
        this.mDraggingItemInitialPosition = adapterPosition;
        this.mDraggingItemInfo = draggingItemInfo;
        this.mDraggingItemViewHolder = holder;
        this.mDraggableRange = range;
        notifyDataSetChanged();
    }

    void onDragItemFinished(boolean result) {
        if (result && this.mDraggingItemCurrentPosition != this.mDraggingItemInitialPosition) {
            DraggableItemAdapter adapter = (DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(getWrappedAdapter(), DraggableItemAdapter.class);
            adapter.onMoveItem(this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
        }
        this.mDraggingItemInitialPosition = -1;
        this.mDraggingItemCurrentPosition = -1;
        this.mDraggableRange = null;
        this.mDraggingItemInfo = null;
        this.mDraggingItemViewHolder = null;
        notifyDataSetChanged();
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(VH holder) {
        if (isDragging() && holder == this.mDraggingItemViewHolder) {
            Log.i(TAG, "a view holder object which is bound to currently dragging item is recycled");
            this.mDraggingItemViewHolder = null;
            this.mDragDropManager.onDraggingItemViewRecycled();
        }
        super.onViewRecycled(holder);
    }

    boolean canStartDrag(RecyclerView.ViewHolder holder, int position, int x, int y) {
        return this.mDraggableItemAdapter.onCheckCanStartDrag(holder, position, x, y);
    }

    ItemDraggableRange getItemDraggableRange(RecyclerView.ViewHolder holder, int position) {
        return this.mDraggableItemAdapter.onGetItemDraggableRange(holder, position);
    }

    void moveItem(int fromPosition, int toPosition) {
        int origFromPosition = convertToOriginalPosition(fromPosition, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
        if (origFromPosition != this.mDraggingItemInitialPosition) {
            throw new IllegalStateException("onMoveItem() - may be a bug or has duplicate IDs  --- mDraggingItemInitialPosition = " + this.mDraggingItemInitialPosition + ", mDraggingItemCurrentPosition = " + this.mDraggingItemCurrentPosition + ", origFromPosition = " + origFromPosition + ", fromPosition = " + fromPosition + ", toPosition = " + toPosition);
        }
        this.mDraggingItemCurrentPosition = toPosition;
        notifyItemMoved(fromPosition, toPosition);
    }

    protected boolean isDragging() {
        return this.mDraggingItemInfo != null;
    }

    int getDraggingItemInitialPosition() {
        return this.mDraggingItemInitialPosition;
    }

    int getDraggingItemCurrentPosition() {
        return this.mDraggingItemCurrentPosition;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void safeUpdateFlags(RecyclerView.ViewHolder viewHolder, int flags) {
        if (viewHolder instanceof DraggableItemViewHolder) {
            DraggableItemViewHolder holder2 = (DraggableItemViewHolder) viewHolder;
            int curFlags = holder2.getDragStateFlags();
            if (curFlags == -1 || ((curFlags ^ flags) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) != 0) {
                flags |= Integer.MIN_VALUE;
            }
            ((DraggableItemViewHolder) viewHolder).setDragStateFlags(flags);
        }
    }

    private static DraggableItemAdapter getDraggableItemAdapter(RecyclerView.Adapter adapter) {
        return (DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, DraggableItemAdapter.class);
    }

    private int getOriginalPosition(int position) {
        if (isDragging()) {
            int correctedPosition = convertToOriginalPosition(position, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
            return correctedPosition;
        }
        return position;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.BaseSwipeableItemAdapter
    public int onGetSwipeReactionType(VH vh, int i, int i2, int i3) {
        RecyclerView.Adapter<VH> wrappedAdapter = getWrappedAdapter();
        if (!(wrappedAdapter instanceof BaseSwipeableItemAdapter)) {
            return 0;
        }
        return ((BaseSwipeableItemAdapter) wrappedAdapter).onGetSwipeReactionType(vh, getOriginalPosition(i), i2, i3);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.BaseSwipeableItemAdapter
    public void onSetSwipeBackground(VH vh, int i, int i2) {
        RecyclerView.Adapter<VH> wrappedAdapter = getWrappedAdapter();
        if (wrappedAdapter instanceof BaseSwipeableItemAdapter) {
            ((BaseSwipeableItemAdapter) wrappedAdapter).onSetSwipeBackground(vh, getOriginalPosition(i), i2);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter
    public SwipeResultAction onSwipeItem(VH vh, int i, int i2) {
        RecyclerView.Adapter<VH> wrappedAdapter = getWrappedAdapter();
        if (!(wrappedAdapter instanceof BaseSwipeableItemAdapter)) {
            return new SwipeResultActionDefault();
        }
        return SwipeableItemInternalUtils.invokeOnSwipeItem((BaseSwipeableItemAdapter) wrappedAdapter, vh, getOriginalPosition(i), i2);
    }
}
