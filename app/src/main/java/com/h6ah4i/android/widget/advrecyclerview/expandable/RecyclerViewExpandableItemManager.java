package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewExpandableItemManager implements ExpandableItemConstants {
    public static final long NO_EXPANDABLE_POSITION = -1;
    private static final String TAG = "ARVExpandableItemMgr";
    private ExpandableRecyclerViewWrapperAdapter mAdapter;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private RecyclerView mRecyclerView;
    private SavedState mSavedState;
    private int mTouchSlop;
    private long mTouchedItemId = -1;
    private RecyclerView.OnItemTouchListener mInternalUseOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return RecyclerViewExpandableItemManager.this.onInterceptTouchEvent(rv, e);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    };

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i, boolean z);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i, boolean z);
    }

    public RecyclerViewExpandableItemManager(@Nullable Parcelable savedState) {
        if (savedState instanceof SavedState) {
            this.mSavedState = (SavedState) savedState;
        }
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
        this.mRecyclerView = rv;
        this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        this.mTouchSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
    }

    public void release() {
        if (this.mRecyclerView != null && this.mInternalUseOnItemTouchListener != null) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        this.mOnGroupExpandListener = null;
        this.mOnGroupCollapseListener = null;
        this.mRecyclerView = null;
        this.mSavedState = null;
    }

    public RecyclerView.Adapter createWrappedAdapter(@NonNull RecyclerView.Adapter adapter) {
        if (this.mAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        }
        int[] adapterSavedState = this.mSavedState != null ? this.mSavedState.adapterSavedState : null;
        this.mSavedState = null;
        this.mAdapter = new ExpandableRecyclerViewWrapperAdapter(this, adapter, adapterSavedState);
        this.mAdapter.setOnGroupExpandListener(this.mOnGroupExpandListener);
        this.mOnGroupExpandListener = null;
        this.mAdapter.setOnGroupCollapseListener(this.mOnGroupCollapseListener);
        this.mOnGroupCollapseListener = null;
        return this.mAdapter;
    }

    public Parcelable getSavedState() {
        int[] adapterSavedState = null;
        if (this.mAdapter != null) {
            adapterSavedState = this.mAdapter.getExpandedItemsSavedStateArray();
        }
        return new SavedState(adapterSavedState);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.mAdapter != null) {
            int action = MotionEventCompat.getActionMasked(e);
            switch (action) {
                case 0:
                    handleActionDown(rv, e);
                    break;
                case 1:
                case 3:
                    if (handleActionUpOrCancel(rv, e)) {
                    }
                    break;
            }
        }
        return false;
    }

    private void handleActionDown(RecyclerView rv, MotionEvent e) {
        RecyclerView.ViewHolder holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY());
        this.mInitialTouchX = (int) (e.getX() + 0.5f);
        this.mInitialTouchY = (int) (e.getY() + 0.5f);
        if (holder instanceof ExpandableItemViewHolder) {
            this.mTouchedItemId = holder.getItemId();
        } else {
            this.mTouchedItemId = -1L;
        }
    }

    private boolean handleActionUpOrCancel(RecyclerView rv, MotionEvent e) {
        RecyclerView.ViewHolder holder;
        int position;
        long touchedItemId = this.mTouchedItemId;
        int initialTouchX = this.mInitialTouchX;
        int initialTouchY = this.mInitialTouchY;
        this.mTouchedItemId = -1L;
        this.mInitialTouchX = 0;
        this.mInitialTouchY = 0;
        if (touchedItemId == -1 || MotionEventCompat.getActionMasked(e) != 1) {
            return false;
        }
        int touchX = (int) (e.getX() + 0.5f);
        int touchY = (int) (e.getY() + 0.5f);
        int diffX = touchX - initialTouchX;
        int diffY = touchY - initialTouchY;
        if (Math.abs(diffX) >= this.mTouchSlop || Math.abs(diffY) >= this.mTouchSlop || (holder = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(rv, e.getX(), e.getY())) == null || holder.getItemId() != touchedItemId || (position = CustomRecyclerViewUtils.getSynchronizedPosition(holder)) == -1) {
            return false;
        }
        View view = holder.itemView;
        int translateX = (int) (ViewCompat.getTranslationX(view) + 0.5f);
        int translateY = (int) (ViewCompat.getTranslationY(view) + 0.5f);
        int viewX = touchX - (view.getLeft() + translateX);
        int viewY = touchY - (view.getTop() + translateY);
        return this.mAdapter.onTapItem(holder, position, viewX, viewY);
    }

    public void expandAll() {
        if (this.mAdapter != null) {
            this.mAdapter.expandAll();
        }
    }

    public void collapseAll() {
        if (this.mAdapter != null) {
            this.mAdapter.collapseAll();
        }
    }

    public boolean expandGroup(int groupPosition) {
        return this.mAdapter != null && this.mAdapter.expandGroup(groupPosition, false);
    }

    public boolean collapseGroup(int groupPosition) {
        return this.mAdapter != null && this.mAdapter.collapseGroup(groupPosition, false);
    }

    public long getExpandablePosition(int flatPosition) {
        if (this.mAdapter == null) {
            return -1L;
        }
        return this.mAdapter.getExpandablePosition(flatPosition);
    }

    public int getFlatPosition(long packedPosition) {
        if (this.mAdapter == null) {
            return -1;
        }
        return this.mAdapter.getFlatPosition(packedPosition);
    }

    public static int getPackedPositionChild(long packedPosition) {
        return ExpandableAdapterHelper.getPackedPositionChild(packedPosition);
    }

    public static long getPackedPositionForChild(int groupPosition, int childPosition) {
        return ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPosition);
    }

    public static long getPackedPositionForGroup(int groupPosition) {
        return ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
    }

    public static int getPackedPositionGroup(long packedPosition) {
        return ExpandableAdapterHelper.getPackedPositionGroup(packedPosition);
    }

    public boolean isGroupExpanded(int groupPosition) {
        return this.mAdapter != null && this.mAdapter.isGroupExpanded(groupPosition);
    }

    public static long getCombinedChildId(long groupId, long childId) {
        return ExpandableAdapterHelper.getCombinedChildId(groupId, childId);
    }

    public static long getCombinedGroupId(long groupId) {
        return ExpandableAdapterHelper.getCombinedGroupId(groupId);
    }

    public static boolean isGroupViewType(int rawViewType) {
        return ExpandableAdapterHelper.isGroupViewType(rawViewType);
    }

    public static int getGroupViewType(int rawViewType) {
        return ExpandableAdapterHelper.getGroupViewType(rawViewType);
    }

    public static int getChildViewType(int rawViewType) {
        return ExpandableAdapterHelper.getChildViewType(rawViewType);
    }

    public void setOnGroupExpandListener(@Nullable OnGroupExpandListener listener) {
        if (this.mAdapter != null) {
            this.mAdapter.setOnGroupExpandListener(listener);
        } else {
            this.mOnGroupExpandListener = listener;
        }
    }

    public void setOnGroupCollapseListener(@Nullable OnGroupCollapseListener listener) {
        if (this.mAdapter != null) {
            this.mAdapter.setOnGroupCollapseListener(listener);
        } else {
            this.mOnGroupCollapseListener = listener;
        }
    }

    public void restoreState(@Nullable Parcelable savedState) {
        restoreState(savedState, false, false);
    }

    public void restoreState(@Nullable Parcelable savedState, boolean callHooks, boolean callListeners) {
        if (savedState != null) {
            if (!(savedState instanceof SavedState)) {
                throw new IllegalArgumentException("Illegal saved state object passed");
            }
            if (this.mAdapter == null || this.mRecyclerView == null) {
                throw new IllegalStateException("RecyclerView has not been attached");
            }
            this.mAdapter.restoreState(((SavedState) savedState).adapterSavedState, callHooks, callListeners);
        }
    }

    public void notifyGroupItemChanged(int groupPosition) {
        this.mAdapter.notifyGroupItemChanged(groupPosition);
    }

    public void notifyGroupAndChildrenItemsChanged(int groupPosition) {
        this.mAdapter.notifyGroupAndChildrenItemsChanged(groupPosition, null);
    }

    public void notifyGroupAndChildrenItemsChanged(int groupPosition, Object payload) {
        this.mAdapter.notifyGroupAndChildrenItemsChanged(groupPosition, payload);
    }

    public void notifyChildrenOfGroupItemChanged(int groupPosition) {
        this.mAdapter.notifyChildrenOfGroupItemChanged(groupPosition, null);
    }

    public void notifyChildrenOfGroupItemChanged(int groupPosition, Object payload) {
        this.mAdapter.notifyChildrenOfGroupItemChanged(groupPosition, payload);
    }

    public void notifyChildItemChanged(int groupPosition, int childPosition) {
        this.mAdapter.notifyChildItemChanged(groupPosition, childPosition, null);
    }

    public void notifyChildItemChanged(int groupPosition, int childPosition, Object payload) {
        this.mAdapter.notifyChildItemChanged(groupPosition, childPosition, payload);
    }

    public void notifyChildItemRangeChanged(int groupPosition, int childPositionStart, int itemCount) {
        this.mAdapter.notifyChildItemRangeChanged(groupPosition, childPositionStart, itemCount, null);
    }

    public void notifyChildItemRangeChanged(int groupPosition, int childPositionStart, int itemCount, Object payload) {
        this.mAdapter.notifyChildItemRangeChanged(groupPosition, childPositionStart, itemCount, payload);
    }

    public void notifyGroupItemInserted(int groupPosition) {
        notifyGroupItemInserted(groupPosition, false);
    }

    public void notifyGroupItemInserted(int groupPosition, boolean expanded) {
        this.mAdapter.notifyGroupItemInserted(groupPosition, expanded);
    }

    public void notifyGroupItemRangeInserted(int groupPositionStart, int itemCount) {
        notifyGroupItemRangeInserted(groupPositionStart, itemCount, false);
    }

    public void notifyGroupItemRangeInserted(int groupPositionStart, int itemCount, boolean expanded) {
        this.mAdapter.notifyGroupItemRangeInserted(groupPositionStart, itemCount, expanded);
    }

    public void notifyChildItemInserted(int groupPosition, int childPosition) {
        this.mAdapter.notifyChildItemInserted(groupPosition, childPosition);
    }

    public void notifyChildItemRangeInserted(int groupPosition, int childPositionStart, int itemCount) {
        this.mAdapter.notifyChildItemRangeInserted(groupPosition, childPositionStart, itemCount);
    }

    public void notifyGroupItemRemoved(int groupPosition) {
        this.mAdapter.notifyGroupItemRemoved(groupPosition);
    }

    public void notifyGroupItemRangeRemoved(int groupPositionStart, int itemCount) {
        this.mAdapter.notifyGroupItemRangeRemoved(groupPositionStart, itemCount);
    }

    public void notifyChildItemRemoved(int groupPosition, int childPosition) {
        this.mAdapter.notifyChildItemRemoved(groupPosition, childPosition);
    }

    public void notifyChildItemRangeRemoved(int groupPosition, int childPositionStart, int itemCount) {
        this.mAdapter.notifyChildItemRangeRemoved(groupPosition, childPositionStart, itemCount);
    }

    public int getGroupCount() {
        return this.mAdapter.getGroupCount();
    }

    public int getChildCount(int groupPosition) {
        return this.mAdapter.getChildCount(groupPosition);
    }

    public void scrollToGroup(int groupPosition, int childItemHeight) {
        scrollToGroup(groupPosition, childItemHeight, 0, 0);
    }

    public void scrollToGroup(int groupPosition, int childItemHeight, int topMargin, int bottomMargin) {
        int totalChildrenHeight = getChildCount(groupPosition) * childItemHeight;
        scrollToGroupWithTotalChildrenHeight(groupPosition, totalChildrenHeight, topMargin, bottomMargin);
    }

    public void scrollToGroupWithTotalChildrenHeight(int groupPosition, int totalChildrenHeight, int topMargin, int bottomMargin) {
        long packedPosition = getPackedPositionForGroup(groupPosition);
        int flatPosition = getFlatPosition(packedPosition);
        RecyclerView.ViewHolder vh = this.mRecyclerView.findViewHolderForLayoutPosition(flatPosition);
        if (vh != null) {
            if (!isGroupExpanded(groupPosition)) {
                totalChildrenHeight = 0;
            }
            int groupItemTop = vh.itemView.getTop();
            int groupItemBottom = vh.itemView.getBottom();
            int parentHeight = this.mRecyclerView.getHeight();
            int bottomRoom = parentHeight - groupItemBottom;
            if (groupItemTop <= topMargin) {
                int parentTopPadding = this.mRecyclerView.getPaddingTop();
                int itemTopMargin = ((RecyclerView.LayoutParams) vh.itemView.getLayoutParams()).topMargin;
                int offset = (topMargin - parentTopPadding) - itemTopMargin;
                ((LinearLayoutManager) this.mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(flatPosition, offset);
                return;
            }
            if (bottomRoom < totalChildrenHeight + bottomMargin) {
                int scrollAmount = Math.max(0, (totalChildrenHeight + bottomMargin) - bottomRoom);
                this.mRecyclerView.smoothScrollBy(0, Math.min(groupItemTop - topMargin, scrollAmount));
            }
        }
    }

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        final int[] adapterSavedState;

        public SavedState(int[] adapterSavedState) {
            this.adapterSavedState = adapterSavedState;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeIntArray(this.adapterSavedState);
        }

        private SavedState(Parcel in) {
            this.adapterSavedState = in.createIntArray();
        }
    }
}
