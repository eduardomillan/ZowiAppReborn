package com.h6ah4i.android.widget.advrecyclerview.expandable;

import androidx.core.view.InputDeviceCompat;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
class ExpandablePositionTranslator {
    private static final long FLAG_EXPANDED = 2147483648L;
    private static final long LOWER_31BIT_MASK = 2147483647L;
    private static final long LOWER_32BIT_MASK = 4294967295L;
    private static final long UPPER_32BIT_MASK = -4294967296L;
    private ExpandableItemAdapter mAdapter;
    private int[] mCachedGroupId;
    private long[] mCachedGroupPosInfo;
    private int mExpandedChildCount;
    private int mExpandedGroupCount;
    private int mGroupCount;
    private final int ALLOCATE_UNIT = 256;
    private int mEndOfCalculatedOffsetGroupPosition = -1;

    public void build(ExpandableItemAdapter adapter, boolean allExpanded) {
        int groupCount = adapter.getGroupCount();
        enlargeArraysIfNeeded(groupCount, false);
        long[] info = this.mCachedGroupPosInfo;
        int[] ids = this.mCachedGroupId;
        int totalChildCount = 0;
        for (int i = 0; i < groupCount; i++) {
            long groupId = adapter.getGroupId(i);
            int childCount = adapter.getChildCount(i);
            if (allExpanded) {
                info[i] = (((long) (i + totalChildCount)) << 32) | ((long) childCount) | FLAG_EXPANDED;
            } else {
                info[i] = (((long) i) << 32) | ((long) childCount);
            }
            ids[i] = (int) (LOWER_32BIT_MASK & groupId);
            totalChildCount += childCount;
        }
        this.mAdapter = adapter;
        this.mGroupCount = groupCount;
        this.mExpandedGroupCount = allExpanded ? groupCount : 0;
        if (!allExpanded) {
            totalChildCount = 0;
        }
        this.mExpandedChildCount = totalChildCount;
        this.mEndOfCalculatedOffsetGroupPosition = Math.max(0, groupCount - 1);
    }

    public void restoreExpandedGroupItems(int[] restoreGroupIds, ExpandableItemAdapter adapter, RecyclerViewExpandableItemManager.OnGroupExpandListener expandListener, RecyclerViewExpandableItemManager.OnGroupCollapseListener collapseListener) {
        if (restoreGroupIds != null && restoreGroupIds.length != 0 && this.mCachedGroupPosInfo != null) {
            long[] idAndPos = new long[this.mGroupCount];
            for (int i = 0; i < this.mGroupCount; i++) {
                idAndPos[i] = (((long) this.mCachedGroupId[i]) << 32) | ((long) i);
            }
            Arrays.sort(idAndPos);
            int index = 0;
            for (int id1 : restoreGroupIds) {
                for (int j = index; j < idAndPos.length; j++) {
                    int id2 = (int) (idAndPos[j] >> 32);
                    int position = (int) (idAndPos[j] & LOWER_31BIT_MASK);
                    if (id2 < id1) {
                        index = j;
                        if ((adapter == null || adapter.onHookGroupCollapse(position, false)) && collapseGroup(position) && collapseListener != null) {
                            collapseListener.onGroupCollapse(position, false);
                        }
                    } else if (id2 == id1) {
                        index = j + 1;
                        if ((adapter == null || adapter.onHookGroupExpand(position, false)) && expandGroup(position) && expandListener != null) {
                            expandListener.onGroupExpand(position, false);
                        }
                    }
                }
            }
            if (adapter != null || collapseListener != null) {
                for (int i2 = index; i2 < idAndPos.length; i2++) {
                    int position2 = (int) (idAndPos[i2] & LOWER_31BIT_MASK);
                    if ((adapter == null || adapter.onHookGroupCollapse(position2, false)) && collapseGroup(position2) && collapseListener != null) {
                        collapseListener.onGroupCollapse(position2, false);
                    }
                }
            }
        }
    }

    public int[] getSavedStateArray() {
        int[] expandedGroups = new int[this.mExpandedGroupCount];
        int index = 0;
        for (int i = 0; i < this.mGroupCount; i++) {
            long t = this.mCachedGroupPosInfo[i];
            if ((FLAG_EXPANDED & t) != 0) {
                expandedGroups[index] = this.mCachedGroupId[i];
                index++;
            }
        }
        if (index != this.mExpandedGroupCount) {
            throw new IllegalStateException("may be a bug  (index = " + index + ", mExpandedGroupCount = " + this.mExpandedGroupCount + ")");
        }
        Arrays.sort(expandedGroups);
        return expandedGroups;
    }

    public int getItemCount() {
        return this.mGroupCount + this.mExpandedChildCount;
    }

    public boolean isGroupExpanded(int groupPosition) {
        return (this.mCachedGroupPosInfo[groupPosition] & FLAG_EXPANDED) != 0;
    }

    public int getChildCount(int groupPosition) {
        return (int) (this.mCachedGroupPosInfo[groupPosition] & LOWER_31BIT_MASK);
    }

    public int getVisibleChildCount(int groupPosition) {
        if (isGroupExpanded(groupPosition)) {
            return getChildCount(groupPosition);
        }
        return 0;
    }

    public boolean collapseGroup(int groupPosition) {
        if ((this.mCachedGroupPosInfo[groupPosition] & FLAG_EXPANDED) == 0) {
            return false;
        }
        int childCount = (int) (this.mCachedGroupPosInfo[groupPosition] & LOWER_31BIT_MASK);
        long[] jArr = this.mCachedGroupPosInfo;
        jArr[groupPosition] = jArr[groupPosition] & (-2147483649L);
        this.mExpandedGroupCount--;
        this.mExpandedChildCount -= childCount;
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, groupPosition);
        return true;
    }

    public boolean expandGroup(int groupPosition) {
        if ((this.mCachedGroupPosInfo[groupPosition] & FLAG_EXPANDED) != 0) {
            return false;
        }
        int childCount = (int) (this.mCachedGroupPosInfo[groupPosition] & LOWER_31BIT_MASK);
        long[] jArr = this.mCachedGroupPosInfo;
        jArr[groupPosition] = jArr[groupPosition] | FLAG_EXPANDED;
        this.mExpandedGroupCount++;
        this.mExpandedChildCount += childCount;
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, groupPosition);
        return true;
    }

    public void moveGroupItem(int fromGroupPosition, int toGroupPosition) {
        if (fromGroupPosition != toGroupPosition) {
            long tmp1 = this.mCachedGroupPosInfo[fromGroupPosition];
            int tmp2 = this.mCachedGroupId[fromGroupPosition];
            if (toGroupPosition < fromGroupPosition) {
                for (int i = fromGroupPosition; i > toGroupPosition; i--) {
                    this.mCachedGroupPosInfo[i] = this.mCachedGroupPosInfo[i - 1];
                    this.mCachedGroupId[i] = this.mCachedGroupId[i - 1];
                }
            } else {
                for (int i2 = fromGroupPosition; i2 < toGroupPosition; i2++) {
                    this.mCachedGroupPosInfo[i2] = this.mCachedGroupPosInfo[i2 + 1];
                    this.mCachedGroupId[i2] = this.mCachedGroupId[i2 + 1];
                }
            }
            this.mCachedGroupPosInfo[toGroupPosition] = tmp1;
            this.mCachedGroupId[toGroupPosition] = tmp2;
            int minPosition = Math.min(fromGroupPosition, toGroupPosition);
            if (minPosition > 0) {
                this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, minPosition - 1);
            } else {
                this.mEndOfCalculatedOffsetGroupPosition = -1;
            }
        }
    }

    public void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        if (fromGroupPosition != toGroupPosition) {
            int fromChildCount = (int) (this.mCachedGroupPosInfo[fromGroupPosition] & LOWER_31BIT_MASK);
            int toChildCount = (int) (this.mCachedGroupPosInfo[toGroupPosition] & LOWER_31BIT_MASK);
            if (fromChildCount == 0) {
                throw new IllegalStateException("moveChildItem(fromGroupPosition = " + fromGroupPosition + ", fromChildPosition = " + fromChildPosition + ", toGroupPosition = " + toGroupPosition + ", toChildPosition = " + toChildPosition + ")  --- may be a bug.");
            }
            this.mCachedGroupPosInfo[fromGroupPosition] = (this.mCachedGroupPosInfo[fromGroupPosition] & (-2147483648L)) | ((long) (fromChildCount - 1));
            this.mCachedGroupPosInfo[toGroupPosition] = (this.mCachedGroupPosInfo[toGroupPosition] & (-2147483648L)) | ((long) (toChildCount + 1));
            if ((this.mCachedGroupPosInfo[fromGroupPosition] & FLAG_EXPANDED) != 0) {
                this.mExpandedChildCount--;
            }
            if ((this.mCachedGroupPosInfo[toGroupPosition] & FLAG_EXPANDED) != 0) {
                this.mExpandedChildCount++;
            }
            int minPosition = Math.min(fromGroupPosition, toGroupPosition);
            if (minPosition > 0) {
                this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, minPosition - 1);
            } else {
                this.mEndOfCalculatedOffsetGroupPosition = -1;
            }
        }
    }

    public long getExpandablePosition(int flatPosition) {
        if (flatPosition == -1) {
            return -1L;
        }
        int groupCount = this.mGroupCount;
        int startIndex = binarySearchGroupPositionByFlatPosition(this.mCachedGroupPosInfo, this.mEndOfCalculatedOffsetGroupPosition, flatPosition);
        long expandablePosition = -1;
        int endOfCalculatedOffsetGroupPosition = this.mEndOfCalculatedOffsetGroupPosition;
        int offset = startIndex == 0 ? 0 : (int) (this.mCachedGroupPosInfo[startIndex] >>> 32);
        int i = startIndex;
        while (true) {
            if (i >= groupCount) {
                break;
            }
            long t = this.mCachedGroupPosInfo[i];
            this.mCachedGroupPosInfo[i] = (((long) offset) << 32) | (LOWER_32BIT_MASK & t);
            endOfCalculatedOffsetGroupPosition = i;
            if (offset >= flatPosition) {
                expandablePosition = ExpandableAdapterHelper.getPackedPositionForGroup(i);
                break;
            }
            offset++;
            if ((FLAG_EXPANDED & t) != 0) {
                int childCount = (int) (LOWER_31BIT_MASK & t);
                if (childCount > 0 && (offset + childCount) - 1 >= flatPosition) {
                    expandablePosition = ExpandableAdapterHelper.getPackedPositionForChild(i, flatPosition - offset);
                    break;
                }
                offset += childCount;
            }
            i++;
        }
        this.mEndOfCalculatedOffsetGroupPosition = Math.max(this.mEndOfCalculatedOffsetGroupPosition, endOfCalculatedOffsetGroupPosition);
        return expandablePosition;
    }

    public int getFlatPosition(long packedPosition) {
        if (packedPosition == -1) {
            return -1;
        }
        int groupPosition = ExpandableAdapterHelper.getPackedPositionGroup(packedPosition);
        int childPosition = ExpandableAdapterHelper.getPackedPositionChild(packedPosition);
        int groupCount = this.mGroupCount;
        if (groupPosition < 0 || groupPosition >= groupCount) {
            return -1;
        }
        if (childPosition != -1 && !isGroupExpanded(groupPosition)) {
            return -1;
        }
        int startIndex = Math.max(0, Math.min(groupPosition, this.mEndOfCalculatedOffsetGroupPosition));
        int endOfCalculatedOffsetGroupPosition = this.mEndOfCalculatedOffsetGroupPosition;
        int offset = (int) (this.mCachedGroupPosInfo[startIndex] >>> 32);
        int flatPosition = -1;
        int i = startIndex;
        while (true) {
            if (i >= groupCount) {
                break;
            }
            long t = this.mCachedGroupPosInfo[i];
            this.mCachedGroupPosInfo[i] = (((long) offset) << 32) | (LOWER_32BIT_MASK & t);
            endOfCalculatedOffsetGroupPosition = i;
            int childCount = (int) (LOWER_31BIT_MASK & t);
            if (i == groupPosition) {
                if (childPosition == -1) {
                    flatPosition = offset;
                } else if (childPosition < childCount) {
                    flatPosition = offset + 1 + childPosition;
                }
            } else {
                offset++;
                if ((FLAG_EXPANDED & t) != 0) {
                    offset += childCount;
                }
                i++;
            }
        }
        this.mEndOfCalculatedOffsetGroupPosition = Math.max(this.mEndOfCalculatedOffsetGroupPosition, endOfCalculatedOffsetGroupPosition);
        return flatPosition;
    }

    private static int binarySearchGroupPositionByFlatPosition(long[] array, int endArrayPosition, int flatPosition) {
        if (endArrayPosition <= 0) {
            return 0;
        }
        int v1 = (int) (array[0] >>> 32);
        int v2 = (int) (array[endArrayPosition] >>> 32);
        if (flatPosition <= v1) {
            return 0;
        }
        if (flatPosition < v2) {
            int lastS = 0;
            int s = 0;
            int e = endArrayPosition;
            while (s < e) {
                int mid = (s + e) >>> 1;
                int v = (int) (array[mid] >>> 32);
                if (v < flatPosition) {
                    lastS = s;
                    s = mid + 1;
                } else {
                    e = mid;
                }
            }
            return lastS;
        }
        return endArrayPosition;
    }

    public void removeChildItem(int groupPosition, int childPosition) {
        removeChildItems(groupPosition, childPosition, 1);
    }

    public void removeChildItems(int groupPosition, int childPositionStart, int count) {
        long t = this.mCachedGroupPosInfo[groupPosition];
        int curCount = (int) (LOWER_31BIT_MASK & t);
        if (childPositionStart < 0 || childPositionStart + count > curCount) {
            throw new IllegalStateException("Invalid child position removeChildItems(groupPosition = " + groupPosition + ", childPosition = " + childPositionStart + ", count = " + count + ")");
        }
        if ((FLAG_EXPANDED & t) != 0) {
            this.mExpandedChildCount -= count;
        }
        this.mCachedGroupPosInfo[groupPosition] = ((-2147483648L) & t) | ((long) (curCount - count));
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, groupPosition - 1);
    }

    public void insertChildItem(int groupPosition, int childPosition) {
        insertChildItems(groupPosition, childPosition, 1);
    }

    public void insertChildItems(int groupPosition, int childPositionStart, int count) {
        long t = this.mCachedGroupPosInfo[groupPosition];
        int curCount = (int) (LOWER_31BIT_MASK & t);
        if (childPositionStart < 0 || childPositionStart > curCount) {
            throw new IllegalStateException("Invalid child position insertChildItems(groupPosition = " + groupPosition + ", childPositionStart = " + childPositionStart + ", count = " + count + ")");
        }
        if ((FLAG_EXPANDED & t) != 0) {
            this.mExpandedChildCount += count;
        }
        this.mCachedGroupPosInfo[groupPosition] = ((-2147483648L) & t) | ((long) (curCount + count));
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, groupPosition);
    }

    public int insertGroupItems(int groupPosition, int count, boolean expanded) {
        if (count <= 0) {
            return 0;
        }
        enlargeArraysIfNeeded(this.mGroupCount + count, true);
        ExpandableItemAdapter adapter = this.mAdapter;
        long[] info = this.mCachedGroupPosInfo;
        int[] ids = this.mCachedGroupId;
        int start = (this.mGroupCount - 1) + count;
        int end = (groupPosition - 1) + count;
        for (int i = start; i > end; i--) {
            info[i] = info[i - count];
            ids[i] = ids[i - count];
        }
        long expandedFlag = expanded ? FLAG_EXPANDED : 0L;
        int insertedChildCount = 0;
        int end2 = groupPosition + count;
        for (int i2 = groupPosition; i2 < end2; i2++) {
            long groupId = adapter.getGroupId(i2);
            int childCount = adapter.getChildCount(i2);
            info[i2] = (((long) i2) << 32) | ((long) childCount) | expandedFlag;
            ids[i2] = (int) (LOWER_32BIT_MASK & groupId);
            insertedChildCount += childCount;
        }
        this.mGroupCount += count;
        if (expanded) {
            this.mExpandedGroupCount += count;
            this.mExpandedChildCount += insertedChildCount;
        }
        int calculatedOffset = this.mGroupCount == 0 ? -1 : groupPosition - 1;
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, calculatedOffset);
        if (expanded) {
            int n = count + insertedChildCount;
            return n;
        }
        return count;
    }

    public int insertGroupItem(int groupPosition, boolean expanded) {
        return insertGroupItems(groupPosition, 1, expanded);
    }

    public int removeGroupItems(int groupPosition, int count) {
        if (count <= 0) {
            return 0;
        }
        int removedVisibleItemCount = 0;
        for (int i = 0; i < count; i++) {
            long t = this.mCachedGroupPosInfo[groupPosition + i];
            if ((FLAG_EXPANDED & t) != 0) {
                int visibleChildCount = (int) (LOWER_31BIT_MASK & t);
                removedVisibleItemCount += visibleChildCount;
                this.mExpandedChildCount -= visibleChildCount;
                this.mExpandedGroupCount--;
            }
        }
        int removedVisibleItemCount2 = removedVisibleItemCount + count;
        this.mGroupCount -= count;
        for (int i2 = groupPosition; i2 < this.mGroupCount; i2++) {
            this.mCachedGroupPosInfo[i2] = this.mCachedGroupPosInfo[i2 + count];
            this.mCachedGroupId[i2] = this.mCachedGroupId[i2 + count];
        }
        int calculatedOffset = this.mGroupCount == 0 ? -1 : groupPosition - 1;
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, calculatedOffset);
        return removedVisibleItemCount2;
    }

    public int removeGroupItem(int groupPosition) {
        return removeGroupItems(groupPosition, 1);
    }

    private void enlargeArraysIfNeeded(int size, boolean preserveData) {
        int allocSize = (size + 511) & InputDeviceCompat.SOURCE_ANY;
        long[] curInfo = this.mCachedGroupPosInfo;
        int[] curId = this.mCachedGroupId;
        long[] newInfo = curInfo;
        int[] newId = curId;
        if (curInfo == null || curInfo.length < size) {
            newInfo = new long[allocSize];
        }
        if (curId == null || curId.length < size) {
            newId = new int[allocSize];
        }
        if (preserveData) {
            if (curInfo != null && curInfo != newInfo) {
                System.arraycopy(curInfo, 0, newInfo, 0, curInfo.length);
            }
            if (curId != null && curId != newId) {
                System.arraycopy(curId, 0, newId, 0, curId.length);
            }
        }
        this.mCachedGroupPosInfo = newInfo;
        this.mCachedGroupId = newId;
    }

    public boolean isAllExpanded() {
        return this.mExpandedGroupCount == this.mGroupCount;
    }

    public boolean isAllCollapsed() {
        return this.mExpandedGroupCount == 0;
    }
}
