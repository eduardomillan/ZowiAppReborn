package rx.android.widget;

import android.widget.AbsListView;

/* JADX INFO: loaded from: classes.dex */
public abstract class OnListViewScrollEvent {
    public abstract int firstVisibleItem();

    public abstract AbsListView listView();

    public abstract int scrollState();

    public abstract int totalItemCount();

    public abstract int visibleItemCount();

    public static OnListViewScrollEvent create(AbsListView listView, int scrollState, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        return new AutoValue_OnListViewScrollEvent(listView, scrollState, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
