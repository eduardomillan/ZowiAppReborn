package rx.android.widget;

import android.widget.AbsListView;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import rx.Observable;
import rx.Subscriber;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.functions.Action0;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeListViewScroll implements Observable.OnSubscribe<OnListViewScrollEvent> {
    private final AbsListView listView;

    public OnSubscribeListViewScroll(AbsListView listView) {
        this.listView = listView;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super OnListViewScrollEvent> observer) {
        Assertions.assertUiThread();
        final CompositeOnScrollListener composite = CachedListeners.getFromViewOrCreate(this.listView);
        final AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() { // from class: rx.android.widget.OnSubscribeListViewScroll.1
            int currentScrollState = 0;

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                OnListViewScrollEvent event = OnListViewScrollEvent.create(view, this.currentScrollState, firstVisibleItem, visibleItemCount, totalItemCount);
                observer.onNext(event);
            }
        };
        composite.addOnScrollListener(listener);
        observer.add(AndroidSubscriptions.unsubscribeInUiThread(new Action0() { // from class: rx.android.widget.OnSubscribeListViewScroll.2
            @Override // rx.functions.Action0
            public void call() {
                composite.removeOnScrollListener(listener);
            }
        }));
    }

    private static class CompositeOnScrollListener implements AbsListView.OnScrollListener {
        private final List<AbsListView.OnScrollListener> listeners;

        private CompositeOnScrollListener() {
            this.listeners = new ArrayList();
        }

        public boolean addOnScrollListener(AbsListView.OnScrollListener listener) {
            return this.listeners.add(listener);
        }

        public boolean removeOnScrollListener(AbsListView.OnScrollListener listener) {
            return this.listeners.remove(listener);
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            for (AbsListView.OnScrollListener listener : this.listeners) {
                listener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            for (AbsListView.OnScrollListener listener : this.listeners) {
                listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    private static class CachedListeners {
        private static final Map<AdapterView<?>, CompositeOnScrollListener> sCachedListeners = new WeakHashMap();

        private CachedListeners() {
        }

        public static CompositeOnScrollListener getFromViewOrCreate(AbsListView view) {
            CompositeOnScrollListener cached = sCachedListeners.get(view);
            if (cached == null) {
                CompositeOnScrollListener listener = new CompositeOnScrollListener();
                sCachedListeners.put(view, listener);
                view.setOnScrollListener(listener);
                return listener;
            }
            return cached;
        }
    }
}
