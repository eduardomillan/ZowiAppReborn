package rx.android.widget;

import android.widget.AbsListView;

/* JADX INFO: loaded from: classes.dex */
final class AutoValue_OnListViewScrollEvent extends OnListViewScrollEvent {
    private final int firstVisibleItem;
    private final AbsListView listView;
    private final int scrollState;
    private final int totalItemCount;
    private final int visibleItemCount;

    AutoValue_OnListViewScrollEvent(AbsListView listView, int scrollState, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (listView == null) {
            throw new NullPointerException("Null listView");
        }
        this.listView = listView;
        this.scrollState = scrollState;
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override // rx.android.widget.OnListViewScrollEvent
    public AbsListView listView() {
        return this.listView;
    }

    @Override // rx.android.widget.OnListViewScrollEvent
    public int scrollState() {
        return this.scrollState;
    }

    @Override // rx.android.widget.OnListViewScrollEvent
    public int firstVisibleItem() {
        return this.firstVisibleItem;
    }

    @Override // rx.android.widget.OnListViewScrollEvent
    public int visibleItemCount() {
        return this.visibleItemCount;
    }

    @Override // rx.android.widget.OnListViewScrollEvent
    public int totalItemCount() {
        return this.totalItemCount;
    }

    public String toString() {
        return "OnListViewScrollEvent{listView=" + this.listView + ", scrollState=" + this.scrollState + ", firstVisibleItem=" + this.firstVisibleItem + ", visibleItemCount=" + this.visibleItemCount + ", totalItemCount=" + this.totalItemCount + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OnListViewScrollEvent)) {
            return false;
        }
        OnListViewScrollEvent that = (OnListViewScrollEvent) o;
        return this.listView.equals(that.listView()) && this.scrollState == that.scrollState() && this.firstVisibleItem == that.firstVisibleItem() && this.visibleItemCount == that.visibleItemCount() && this.totalItemCount == that.totalItemCount();
    }

    public int hashCode() {
        int h = 1 * 1000003;
        return ((((((((h ^ this.listView.hashCode()) * 1000003) ^ this.scrollState) * 1000003) ^ this.firstVisibleItem) * 1000003) ^ this.visibleItemCount) * 1000003) ^ this.totalItemCount;
    }
}
