package com.h6ah4i.android.widget.advrecyclerview.expandable;

/* JADX INFO: loaded from: classes.dex */
class ExpandableAdapterHelper {
    private static final long LOWER_31BIT_MASK = 2147483647L;
    private static final long LOWER_32BIT_MASK = 4294967295L;
    public static final long NO_EXPANDABLE_POSITION = -1;
    static final int VIEW_TYPE_FLAG_IS_GROUP = Integer.MIN_VALUE;

    public static long getPackedPositionForChild(int groupPosition, int childPosition) {
        return (((long) childPosition) << 32) | (((long) groupPosition) & LOWER_32BIT_MASK);
    }

    public static long getPackedPositionForGroup(int groupPosition) {
        return (-4294967296L) | (((long) groupPosition) & LOWER_32BIT_MASK);
    }

    public static int getPackedPositionChild(long packedPosition) {
        return (int) (packedPosition >>> 32);
    }

    public static int getPackedPositionGroup(long packedPosition) {
        return (int) (LOWER_32BIT_MASK & packedPosition);
    }

    public static long getCombinedChildId(long groupId, long childId) {
        return ((LOWER_31BIT_MASK & groupId) << 32) | (LOWER_32BIT_MASK & childId);
    }

    public static long getCombinedGroupId(long groupId) {
        return ((LOWER_31BIT_MASK & groupId) << 32) | LOWER_32BIT_MASK;
    }

    public static boolean isGroupViewType(int rawViewType) {
        return (Integer.MIN_VALUE & rawViewType) != 0;
    }

    public static int getGroupViewType(int rawViewType) {
        return Integer.MAX_VALUE & rawViewType;
    }

    public static int getChildViewType(int rawViewType) {
        return Integer.MAX_VALUE & rawViewType;
    }

    private ExpandableAdapterHelper() {
    }
}
