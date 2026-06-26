package rx.android.widget;

import android.view.View;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.functions.Action0;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeAdapterViewOnItemClick implements Observable.OnSubscribe<OnItemClickEvent> {
    private final AdapterView<?> adapterView;

    public OnSubscribeAdapterViewOnItemClick(AdapterView<?> adapterView) {
        this.adapterView = adapterView;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super OnItemClickEvent> observer) {
        Assertions.assertUiThread();
        final CompositeOnClickListener composite = CachedListeners.getFromViewOrCreate(this.adapterView);
        final AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() { // from class: rx.android.widget.OnSubscribeAdapterViewOnItemClick.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                observer.onNext(OnItemClickEvent.create(parent, view, position, id));
            }
        };
        Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() { // from class: rx.android.widget.OnSubscribeAdapterViewOnItemClick.2
            @Override // rx.functions.Action0
            public void call() {
                composite.removeOnClickListener(listener);
            }
        });
        composite.addOnClickListener(listener);
        observer.add(subscription);
    }

    private static class CompositeOnClickListener implements AdapterView.OnItemClickListener {
        private final List<AdapterView.OnItemClickListener> listeners;

        private CompositeOnClickListener() {
            this.listeners = new ArrayList();
        }

        public boolean addOnClickListener(AdapterView.OnItemClickListener listener) {
            return this.listeners.add(listener);
        }

        public boolean removeOnClickListener(AdapterView.OnItemClickListener listener) {
            return this.listeners.remove(listener);
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for (AdapterView.OnItemClickListener listener : this.listeners) {
                listener.onItemClick(parent, view, position, id);
            }
        }
    }

    private static class CachedListeners {
        private static final Map<AdapterView<?>, CompositeOnClickListener> sCachedListeners = new WeakHashMap();

        private CachedListeners() {
        }

        public static CompositeOnClickListener getFromViewOrCreate(AdapterView<?> view) {
            CompositeOnClickListener cached = sCachedListeners.get(view);
            if (cached == null) {
                CompositeOnClickListener listener = new CompositeOnClickListener();
                sCachedListeners.put(view, listener);
                view.setOnItemClickListener(listener);
                return listener;
            }
            return cached;
        }
    }
}
