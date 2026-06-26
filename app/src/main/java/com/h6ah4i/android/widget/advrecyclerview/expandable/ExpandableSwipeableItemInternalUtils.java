package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;

/* JADX INFO: loaded from: classes.dex */
class ExpandableSwipeableItemInternalUtils {
    private ExpandableSwipeableItemInternalUtils() {
    }

    public static SwipeResultAction invokeOnSwipeItem(BaseExpandableSwipeableItemAdapter<?, ?> adapter, RecyclerView.ViewHolder holder, int groupPosition, int childPosition, int result) {
        int reaction;
        if (adapter instanceof LegacyExpandableSwipeableItemAdapter) {
            if (childPosition == -1) {
                reaction = ((LegacyExpandableSwipeableItemAdapter) adapter).onSwipeGroupItem(holder, groupPosition, result);
            } else {
                reaction = ((LegacyExpandableSwipeableItemAdapter) adapter).onSwipeChildItem(holder, groupPosition, childPosition, result);
            }
            switch (reaction) {
                case 0:
                case 1:
                case 2:
                    return new LegacyExpandableSwipeResultAction((LegacyExpandableSwipeableItemAdapter) adapter, holder, groupPosition, childPosition, result, reaction);
                default:
                    throw new IllegalStateException("Unexpected reaction type: " + reaction);
            }
        }
        if (childPosition == -1) {
            return ((ExpandableSwipeableItemAdapter) adapter).onSwipeGroupItem(holder, groupPosition, result);
        }
        return ((ExpandableSwipeableItemAdapter) adapter).onSwipeChildItem(holder, groupPosition, childPosition, result);
    }
}
