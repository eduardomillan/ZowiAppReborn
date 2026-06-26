package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.internal.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

/* JADX INFO: loaded from: classes.dex */
class ExpandableRecyclerViewWrapperAdapter extends BaseWrapperAdapter<RecyclerView.ViewHolder> implements DraggableItemAdapter<RecyclerView.ViewHolder>, SwipeableItemAdapter<RecyclerView.ViewHolder> {
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVExpandableWrapper";
    private static final int VIEW_TYPE_FLAG_IS_GROUP = Integer.MIN_VALUE;
    private int mDraggingItemChildRangeEnd;
    private int mDraggingItemChildRangeStart;
    private int mDraggingItemGroupRangeEnd;
    private int mDraggingItemGroupRangeStart;
    private ExpandableItemAdapter mExpandableItemAdapter;
    private RecyclerViewExpandableItemManager mExpandableListManager;
    private RecyclerViewExpandableItemManager.OnGroupCollapseListener mOnGroupCollapseListener;
    private RecyclerViewExpandableItemManager.OnGroupExpandListener mOnGroupExpandListener;
    private ExpandablePositionTranslator mPositionTranslator;

    private interface Constants extends ExpandableItemConstants {
    }

    public ExpandableRecyclerViewWrapperAdapter(RecyclerViewExpandableItemManager manager, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, int[] expandedItemsSavedState) {
        super(adapter);
        this.mDraggingItemGroupRangeStart = -1;
        this.mDraggingItemGroupRangeEnd = -1;
        this.mDraggingItemChildRangeStart = -1;
        this.mDraggingItemChildRangeEnd = -1;
        this.mExpandableItemAdapter = getExpandableItemAdapter(adapter);
        if (this.mExpandableItemAdapter == null) {
            throw new IllegalArgumentException("adapter does not implement RecyclerViewExpandableListManager");
        }
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }
        this.mExpandableListManager = manager;
        this.mPositionTranslator = new ExpandablePositionTranslator();
        this.mPositionTranslator.build(this.mExpandableItemAdapter, false);
        if (expandedItemsSavedState != null) {
            this.mPositionTranslator.restoreExpandedGroupItems(expandedItemsSavedState, null, null, null);
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onRelease() {
        super.onRelease();
        this.mExpandableItemAdapter = null;
        this.mExpandableListManager = null;
        this.mOnGroupExpandListener = null;
        this.mOnGroupCollapseListener = null;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mPositionTranslator.getItemCount();
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, android.support.v7.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        if (this.mExpandableItemAdapter == null) {
            return -1L;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            long groupId = this.mExpandableItemAdapter.getGroupId(groupPosition);
            return ExpandableAdapterHelper.getCombinedGroupId(groupId);
        }
        long groupId2 = this.mExpandableItemAdapter.getGroupId(groupPosition);
        long childId = this.mExpandableItemAdapter.getChildId(groupPosition, childPosition);
        return ExpandableAdapterHelper.getCombinedChildId(groupId2, childId);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        int type;
        if (this.mExpandableItemAdapter == null) {
            return 0;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            type = this.mExpandableItemAdapter.getGroupItemViewType(groupPosition);
        } else {
            type = this.mExpandableItemAdapter.getChildItemViewType(groupPosition, childPosition);
        }
        if ((type & Integer.MIN_VALUE) != 0) {
            throw new IllegalStateException("Illegal view type (type = " + Integer.toHexString(type) + ")");
        }
        return childPosition == -1 ? type | Integer.MIN_VALUE : type;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (this.mExpandableItemAdapter == null) {
            return null;
        }
        int maskedViewType = viewType & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        if ((Integer.MIN_VALUE & viewType) != 0) {
            holder = this.mExpandableItemAdapter.onCreateGroupViewHolder(parent, maskedViewType);
        } else {
            holder = this.mExpandableItemAdapter.onCreateChildViewHolder(parent, maskedViewType);
        }
        if (holder instanceof ExpandableItemViewHolder) {
            ((ExpandableItemViewHolder) holder).setExpandStateFlags(-1);
            return holder;
        }
        return holder;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int flags;
        if (this.mExpandableItemAdapter != null) {
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
            int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            int viewType = holder.getItemViewType() & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (childPosition == -1) {
                flags = 0 | 1;
            } else {
                flags = 0 | 2;
            }
            if (this.mPositionTranslator.isGroupExpanded(groupPosition)) {
                flags |= 4;
            }
            safeUpdateExpandStateFlags(holder, flags);
            correctItemDragStateFlags(holder, groupPosition, childPosition);
            if (childPosition == -1) {
                this.mExpandableItemAdapter.onBindGroupViewHolder(holder, groupPosition, viewType);
            } else {
                this.mExpandableItemAdapter.onBindChildViewHolder(holder, groupPosition, childPosition, viewType);
            }
        }
    }

    private void rebuildPositionTranslator() {
        if (this.mPositionTranslator != null) {
            int[] savedState = this.mPositionTranslator.getSavedStateArray();
            this.mPositionTranslator.build(this.mExpandableItemAdapter, false);
            this.mPositionTranslator.restoreExpandedGroupItems(savedState, null, null, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter, android.support.v7.widget.RecyclerView.Adapter
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof ExpandableItemViewHolder) {
            ((ExpandableItemViewHolder) viewHolder).setExpandStateFlags(-1);
        }
        super.onViewRecycled(viewHolder);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterChanged() {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterChanged();
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeChanged(int positionStart, int itemCount) {
        super.onHandleWrappedAdapterItemRangeChanged(positionStart, itemCount);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeInserted(int positionStart, int itemCount) {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterItemRangeInserted(positionStart, itemCount);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterItemRangeRemoved(int positionStart, int itemCount) {
        if (itemCount == 1) {
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(positionStart);
            int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            if (childPosition == -1) {
                this.mPositionTranslator.removeGroupItem(groupPosition);
            } else {
                this.mPositionTranslator.removeChildItem(groupPosition, childPosition);
            }
        } else {
            rebuildPositionTranslator();
        }
        super.onHandleWrappedAdapterItemRangeRemoved(positionStart, itemCount);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter
    protected void onHandleWrappedAdapterRangeMoved(int fromPosition, int toPosition, int itemCount) {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterRangeMoved(fromPosition, toPosition, itemCount);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
    public boolean onCheckCanStartDrag(RecyclerView.ViewHolder holder, int position, int x, int y) {
        boolean canStart;
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter)) {
            return false;
        }
        ExpandableDraggableItemAdapter adapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            canStart = adapter.onCheckGroupCanStartDrag(holder, groupPosition, x, y);
        } else {
            canStart = adapter.onCheckChildCanStartDrag(holder, groupPosition, childPosition, x, y);
        }
        this.mDraggingItemGroupRangeStart = -1;
        this.mDraggingItemGroupRangeEnd = -1;
        this.mDraggingItemChildRangeStart = -1;
        this.mDraggingItemChildRangeEnd = -1;
        return canStart;
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
    public ItemDraggableRange onGetItemDraggableRange(RecyclerView.ViewHolder holder, int position) {
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter) || this.mExpandableItemAdapter.getGroupCount() < 1) {
            return null;
        }
        ExpandableDraggableItemAdapter adapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            ItemDraggableRange range = adapter.onGetGroupItemDraggableRange(holder, groupPosition);
            if (range == null) {
                int lastGroup = Math.max(0, this.mExpandableItemAdapter.getGroupCount() - 1);
                int end = Math.max(0, (this.mPositionTranslator.getItemCount() - this.mPositionTranslator.getVisibleChildCount(lastGroup)) - 1);
                return new ItemDraggableRange(0, end);
            }
            if (isGroupPositionRange(range)) {
                long startPackedGroupPosition = ExpandableAdapterHelper.getPackedPositionForGroup(range.getStart());
                long endPackedGroupPosition = ExpandableAdapterHelper.getPackedPositionForGroup(range.getEnd());
                int start = this.mPositionTranslator.getFlatPosition(startPackedGroupPosition);
                int end2 = this.mPositionTranslator.getFlatPosition(endPackedGroupPosition);
                if (range.getEnd() > groupPosition) {
                    end2 += this.mPositionTranslator.getVisibleChildCount(range.getEnd());
                }
                this.mDraggingItemGroupRangeStart = range.getStart();
                this.mDraggingItemGroupRangeEnd = range.getEnd();
                return new ItemDraggableRange(start, end2);
            }
            throw new IllegalStateException("Invalid range specified: " + range);
        }
        ItemDraggableRange range2 = adapter.onGetChildItemDraggableRange(holder, groupPosition, childPosition);
        if (range2 == null) {
            return new ItemDraggableRange(1, Math.max(1, this.mPositionTranslator.getItemCount() - 1));
        }
        if (isGroupPositionRange(range2)) {
            long startPackedGroupPosition2 = ExpandableAdapterHelper.getPackedPositionForGroup(range2.getStart());
            long endPackedGroupPosition2 = ExpandableAdapterHelper.getPackedPositionForGroup(range2.getEnd());
            int end3 = this.mPositionTranslator.getFlatPosition(endPackedGroupPosition2) + this.mPositionTranslator.getVisibleChildCount(range2.getEnd());
            int start2 = this.mPositionTranslator.getFlatPosition(startPackedGroupPosition2) + 1;
            int start3 = Math.min(start2, end3);
            this.mDraggingItemGroupRangeStart = range2.getStart();
            this.mDraggingItemGroupRangeEnd = range2.getEnd();
            return new ItemDraggableRange(start3, end3);
        }
        if (isChildPositionRange(range2)) {
            int maxChildrenPos = Math.max(this.mPositionTranslator.getVisibleChildCount(groupPosition) - 1, 0);
            int childStart = Math.min(range2.getStart(), maxChildrenPos);
            int childEnd = Math.min(range2.getEnd(), maxChildrenPos);
            long startPackedChildPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childStart);
            long endPackedChildPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childEnd);
            int start4 = this.mPositionTranslator.getFlatPosition(startPackedChildPosition);
            int end4 = this.mPositionTranslator.getFlatPosition(endPackedChildPosition);
            this.mDraggingItemChildRangeStart = childStart;
            this.mDraggingItemChildRangeEnd = childEnd;
            return new ItemDraggableRange(start4, end4);
        }
        throw new IllegalStateException("Invalid range specified: " + range2);
    }

    private static boolean isGroupPositionRange(ItemDraggableRange range) {
        return range.getClass().equals(GroupPositionItemDraggableRange.class) || range.getClass().equals(ItemDraggableRange.class);
    }

    private static boolean isChildPositionRange(ItemDraggableRange range) {
        return range.getClass().equals(ChildPositionItemDraggableRange.class);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
    public void onMoveItem(int fromPosition, int toPosition) {
        int modToGroupPosition;
        int modToChildPosition;
        int modToChildPosition2;
        if (this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter) {
            this.mDraggingItemGroupRangeStart = -1;
            this.mDraggingItemGroupRangeEnd = -1;
            this.mDraggingItemChildRangeStart = -1;
            this.mDraggingItemChildRangeEnd = -1;
            if (fromPosition != toPosition) {
                ExpandableDraggableItemAdapter adapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
                long expandableFromPosition = this.mPositionTranslator.getExpandablePosition(fromPosition);
                int fromGroupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandableFromPosition);
                int fromChildPosition = ExpandableAdapterHelper.getPackedPositionChild(expandableFromPosition);
                long expandableToPosition = this.mPositionTranslator.getExpandablePosition(toPosition);
                int toGroupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandableToPosition);
                int toChildPosition = ExpandableAdapterHelper.getPackedPositionChild(expandableToPosition);
                boolean fromIsGroup = fromChildPosition == -1;
                boolean toIsGroup = toChildPosition == -1;
                int actualToFlatPosition = fromPosition;
                if (fromIsGroup && toIsGroup) {
                    adapter.onMoveGroupItem(fromGroupPosition, toGroupPosition);
                    this.mPositionTranslator.moveGroupItem(fromGroupPosition, toGroupPosition);
                    actualToFlatPosition = toPosition;
                } else if (!fromIsGroup && !toIsGroup) {
                    if (fromGroupPosition != toGroupPosition && fromPosition < toPosition) {
                        modToChildPosition2 = toChildPosition + 1;
                    } else {
                        modToChildPosition2 = toChildPosition;
                    }
                    actualToFlatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(fromGroupPosition, modToChildPosition2));
                    adapter.onMoveChildItem(fromGroupPosition, fromChildPosition, toGroupPosition, modToChildPosition2);
                    this.mPositionTranslator.moveChildItem(fromGroupPosition, fromChildPosition, toGroupPosition, modToChildPosition2);
                } else if (!fromIsGroup) {
                    if (toPosition < fromPosition) {
                        if (toGroupPosition == 0) {
                            modToGroupPosition = toGroupPosition;
                            modToChildPosition = 0;
                        } else {
                            modToGroupPosition = toGroupPosition - 1;
                            modToChildPosition = this.mPositionTranslator.getChildCount(modToGroupPosition);
                        }
                    } else if (this.mPositionTranslator.isGroupExpanded(toGroupPosition)) {
                        modToGroupPosition = toGroupPosition;
                        modToChildPosition = 0;
                    } else {
                        modToGroupPosition = toGroupPosition;
                        modToChildPosition = this.mPositionTranslator.getChildCount(modToGroupPosition);
                    }
                    if (fromGroupPosition == modToGroupPosition) {
                        int lastIndex = Math.max(0, this.mPositionTranslator.getChildCount(modToGroupPosition) - 1);
                        modToChildPosition = Math.min(modToChildPosition, lastIndex);
                    }
                    if (fromGroupPosition != modToGroupPosition || fromChildPosition != modToChildPosition) {
                        if (this.mPositionTranslator.isGroupExpanded(toGroupPosition)) {
                            actualToFlatPosition = toPosition;
                        } else {
                            actualToFlatPosition = -1;
                        }
                        adapter.onMoveChildItem(fromGroupPosition, fromChildPosition, modToGroupPosition, modToChildPosition);
                        this.mPositionTranslator.moveChildItem(fromGroupPosition, fromChildPosition, modToGroupPosition, modToChildPosition);
                    }
                } else if (fromGroupPosition != toGroupPosition) {
                    actualToFlatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(toGroupPosition));
                    adapter.onMoveGroupItem(fromGroupPosition, toGroupPosition);
                    this.mPositionTranslator.moveGroupItem(fromGroupPosition, toGroupPosition);
                }
                if (actualToFlatPosition != fromPosition) {
                    if (actualToFlatPosition != -1) {
                        notifyItemMoved(fromPosition, actualToFlatPosition);
                    } else {
                        notifyItemRemoved(fromPosition);
                    }
                }
            }
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.BaseSwipeableItemAdapter
    public int onGetSwipeReactionType(RecyclerView.ViewHolder holder, int position, int x, int y) {
        if (!(this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter)) {
            return 0;
        }
        BaseExpandableSwipeableItemAdapter adapter = (BaseExpandableSwipeableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition == -1) {
            return adapter.onGetGroupItemSwipeReactionType(holder, groupPosition, x, y);
        }
        return adapter.onGetChildItemSwipeReactionType(holder, groupPosition, childPosition, x, y);
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.BaseSwipeableItemAdapter
    public void onSetSwipeBackground(RecyclerView.ViewHolder holder, int position, int type) {
        if (this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter) {
            BaseExpandableSwipeableItemAdapter adapter = (BaseExpandableSwipeableItemAdapter) this.mExpandableItemAdapter;
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
            int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            if (childPosition == -1) {
                adapter.onSetGroupItemSwipeBackground(holder, groupPosition, type);
            } else {
                adapter.onSetChildItemSwipeBackground(holder, groupPosition, childPosition, type);
            }
        }
    }

    @Override // com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter
    public SwipeResultAction onSwipeItem(RecyclerView.ViewHolder holder, int position, int result) {
        if (!(this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter) || position == -1) {
            return null;
        }
        BaseExpandableSwipeableItemAdapter<?, ?> adapter = (BaseExpandableSwipeableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        return ExpandableSwipeableItemInternalUtils.invokeOnSwipeItem(adapter, holder, groupPosition, childPosition, result);
    }

    boolean onTapItem(RecyclerView.ViewHolder holder, int position, int x, int y) {
        if (this.mExpandableItemAdapter == null) {
            return false;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(position);
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        if (childPosition != -1) {
            return false;
        }
        boolean expand = !this.mPositionTranslator.isGroupExpanded(groupPosition);
        boolean result = this.mExpandableItemAdapter.onCheckCanExpandOrCollapseGroup(holder, groupPosition, x, y, expand);
        if (!result) {
            return false;
        }
        if (expand) {
            expandGroup(groupPosition, true);
        } else {
            collapseGroup(groupPosition, true);
        }
        return true;
    }

    void expandAll() {
        if (!this.mPositionTranslator.isAllExpanded()) {
            this.mPositionTranslator.build(this.mExpandableItemAdapter, true);
            notifyDataSetChanged();
        }
    }

    void collapseAll() {
        if (!this.mPositionTranslator.isAllCollapsed()) {
            this.mPositionTranslator.build(this.mExpandableItemAdapter, false);
            notifyDataSetChanged();
        }
    }

    boolean collapseGroup(int groupPosition, boolean fromUser) {
        if (!this.mPositionTranslator.isGroupExpanded(groupPosition) || !this.mExpandableItemAdapter.onHookGroupCollapse(groupPosition, fromUser)) {
            return false;
        }
        if (this.mPositionTranslator.collapseGroup(groupPosition)) {
            long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
            int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
            int childCount = this.mPositionTranslator.getChildCount(groupPosition);
            notifyItemRangeRemoved(flatPosition + 1, childCount);
        }
        long packedPosition2 = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
        int flatPosition2 = this.mPositionTranslator.getFlatPosition(packedPosition2);
        notifyItemChanged(flatPosition2);
        if (this.mOnGroupCollapseListener != null) {
            this.mOnGroupCollapseListener.onGroupCollapse(groupPosition, fromUser);
        }
        return true;
    }

    boolean expandGroup(int groupPosition, boolean fromUser) {
        if (this.mPositionTranslator.isGroupExpanded(groupPosition) || !this.mExpandableItemAdapter.onHookGroupExpand(groupPosition, fromUser)) {
            return false;
        }
        if (this.mPositionTranslator.expandGroup(groupPosition)) {
            long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
            int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
            int childCount = this.mPositionTranslator.getChildCount(groupPosition);
            notifyItemRangeInserted(flatPosition + 1, childCount);
        }
        long packedPosition2 = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
        int flatPosition2 = this.mPositionTranslator.getFlatPosition(packedPosition2);
        notifyItemChanged(flatPosition2);
        if (this.mOnGroupExpandListener != null) {
            this.mOnGroupExpandListener.onGroupExpand(groupPosition, fromUser);
        }
        return true;
    }

    boolean isGroupExpanded(int groupPosition) {
        return this.mPositionTranslator.isGroupExpanded(groupPosition);
    }

    long getExpandablePosition(int flatPosition) {
        return this.mPositionTranslator.getExpandablePosition(flatPosition);
    }

    int getFlatPosition(long packedPosition) {
        return this.mPositionTranslator.getFlatPosition(packedPosition);
    }

    int[] getExpandedItemsSavedStateArray() {
        if (this.mPositionTranslator != null) {
            return this.mPositionTranslator.getSavedStateArray();
        }
        return null;
    }

    void setOnGroupExpandListener(RecyclerViewExpandableItemManager.OnGroupExpandListener listener) {
        this.mOnGroupExpandListener = listener;
    }

    void setOnGroupCollapseListener(RecyclerViewExpandableItemManager.OnGroupCollapseListener listener) {
        this.mOnGroupCollapseListener = listener;
    }

    void restoreState(int[] adapterSavedState, boolean callHook, boolean callListeners) {
        this.mPositionTranslator.restoreExpandedGroupItems(adapterSavedState, callHook ? this.mExpandableItemAdapter : null, callListeners ? this.mOnGroupExpandListener : null, callListeners ? this.mOnGroupCollapseListener : null);
    }

    void notifyGroupItemChanged(int groupPosition) {
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        if (flatPosition != -1) {
            notifyItemChanged(flatPosition);
        }
    }

    void notifyGroupAndChildrenItemsChanged(int groupPosition, Object payload) {
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(groupPosition);
        if (flatPosition != -1) {
            notifyItemRangeChanged(flatPosition, visibleChildCount + 1, payload);
        }
    }

    void notifyChildrenOfGroupItemChanged(int groupPosition, Object payload) {
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(groupPosition);
        if (visibleChildCount > 0) {
            long packedPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, 0);
            int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
            if (flatPosition != -1) {
                notifyItemRangeChanged(flatPosition, visibleChildCount, payload);
            }
        }
    }

    void notifyChildItemChanged(int groupPosition, int childPosition, Object payload) {
        notifyChildItemRangeChanged(groupPosition, childPosition, 1, payload);
    }

    void notifyChildItemRangeChanged(int groupPosition, int childPositionStart, int itemCount, Object payload) {
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(groupPosition);
        if (visibleChildCount > 0 && childPositionStart < visibleChildCount) {
            long packedPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, 0);
            int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
            if (flatPosition != -1) {
                int startPosition = flatPosition + childPositionStart;
                int count = Math.min(itemCount, visibleChildCount - childPositionStart);
                notifyItemRangeChanged(startPosition, count, payload);
            }
        }
    }

    void notifyChildItemInserted(int groupPosition, int childPosition) {
        this.mPositionTranslator.insertChildItem(groupPosition, childPosition);
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPosition);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        if (flatPosition != -1) {
            notifyItemInserted(flatPosition);
        }
    }

    void notifyChildItemRangeInserted(int groupPosition, int childPositionStart, int itemCount) {
        this.mPositionTranslator.insertChildItems(groupPosition, childPositionStart, itemCount);
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPositionStart);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        if (flatPosition != -1) {
            notifyItemRangeInserted(flatPosition, itemCount);
        }
    }

    void notifyChildItemRemoved(int groupPosition, int childPosition) {
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPosition);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        this.mPositionTranslator.removeChildItem(groupPosition, childPosition);
        if (flatPosition != -1) {
            notifyItemRemoved(flatPosition);
        }
    }

    void notifyChildItemRangeRemoved(int groupPosition, int childPositionStart, int itemCount) {
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForChild(groupPosition, childPositionStart);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        this.mPositionTranslator.removeChildItems(groupPosition, childPositionStart, itemCount);
        if (flatPosition != -1) {
            notifyItemRangeRemoved(flatPosition, itemCount);
        }
    }

    void notifyGroupItemInserted(int groupPosition, boolean expanded) {
        int insertedCount = this.mPositionTranslator.insertGroupItem(groupPosition, expanded);
        if (insertedCount > 0) {
            long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
            int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
            notifyItemInserted(flatPosition);
            raiseOnGroupExpandedSequentially(groupPosition, 1, false);
        }
    }

    void notifyGroupItemRangeInserted(int groupPositionStart, int count, boolean expanded) {
        int insertedCount = this.mPositionTranslator.insertGroupItems(groupPositionStart, count, expanded);
        if (insertedCount > 0) {
            long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPositionStart);
            int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
            notifyItemRangeInserted(flatPosition, insertedCount);
            raiseOnGroupExpandedSequentially(groupPositionStart, count, false);
        }
    }

    private void raiseOnGroupExpandedSequentially(int groupPositionStart, int count, boolean fromUser) {
        if (this.mOnGroupExpandListener != null) {
            for (int i = 0; i < count; i++) {
                this.mOnGroupExpandListener.onGroupExpand(groupPositionStart + i, fromUser);
            }
        }
    }

    void notifyGroupItemRemoved(int groupPosition) {
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPosition);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        int removedCount = this.mPositionTranslator.removeGroupItem(groupPosition);
        if (removedCount > 0) {
            notifyItemRangeRemoved(flatPosition, removedCount);
        }
    }

    void notifyGroupItemRangeRemoved(int groupPositionStart, int count) {
        long packedPosition = ExpandableAdapterHelper.getPackedPositionForGroup(groupPositionStart);
        int flatPosition = this.mPositionTranslator.getFlatPosition(packedPosition);
        int removedCount = this.mPositionTranslator.removeGroupItems(groupPositionStart, count);
        if (removedCount > 0) {
            notifyItemRangeRemoved(flatPosition, removedCount);
        }
    }

    int getGroupCount() {
        return this.mExpandableItemAdapter.getGroupCount();
    }

    int getChildCount(int groupPosition) {
        return this.mExpandableItemAdapter.getChildCount(groupPosition);
    }

    private static ExpandableItemAdapter getExpandableItemAdapter(RecyclerView.Adapter adapter) {
        return (ExpandableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, ExpandableItemAdapter.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void safeUpdateExpandStateFlags(RecyclerView.ViewHolder viewHolder, int flags) {
        if (viewHolder instanceof ExpandableItemViewHolder) {
            ExpandableItemViewHolder holder2 = (ExpandableItemViewHolder) viewHolder;
            int curFlags = holder2.getExpandStateFlags();
            if (curFlags != -1 && ((curFlags ^ flags) & 4) != 0) {
                flags |= 8;
            }
            if (curFlags == -1 || ((curFlags ^ flags) & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) != 0) {
                flags |= Integer.MIN_VALUE;
            }
            holder2.setExpandStateFlags(flags);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void correctItemDragStateFlags(RecyclerView.ViewHolder viewHolder, int groupPosition, int childPosition) {
        if (viewHolder instanceof DraggableItemViewHolder) {
            DraggableItemViewHolder holder2 = (DraggableItemViewHolder) viewHolder;
            boolean groupRangeSpecified = (this.mDraggingItemGroupRangeStart == -1 || this.mDraggingItemGroupRangeEnd == -1) ? false : true;
            boolean childRangeSpecified = (this.mDraggingItemChildRangeStart == -1 || this.mDraggingItemChildRangeEnd == -1) ? false : true;
            boolean isInGroupRange = groupPosition >= this.mDraggingItemGroupRangeStart && groupPosition <= this.mDraggingItemGroupRangeEnd;
            boolean isInChildRange = groupPosition != -1 && childPosition >= this.mDraggingItemChildRangeStart && childPosition <= this.mDraggingItemChildRangeEnd;
            int flags = holder2.getDragStateFlags();
            boolean needCorrection = false;
            if ((flags & 1) != 0 && (flags & 4) == 0 && ((!groupRangeSpecified || isInGroupRange) && (!childRangeSpecified || (childRangeSpecified && isInChildRange)))) {
                needCorrection = true;
            }
            if (needCorrection) {
                holder2.setDragStateFlags(flags | 4 | Integer.MIN_VALUE);
            }
        }
    }
}
