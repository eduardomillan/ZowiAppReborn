package rx.android.view;

import android.view.View;
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
final class OnSubscribeViewClick implements Observable.OnSubscribe<OnClickEvent> {
    private final boolean emitInitialValue;
    private final View view;

    public OnSubscribeViewClick(View view, boolean emitInitialValue) {
        this.emitInitialValue = emitInitialValue;
        this.view = view;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super OnClickEvent> observer) {
        Assertions.assertUiThread();
        final CompositeOnClickListener composite = CachedListeners.getFromViewOrCreate(this.view);
        final View.OnClickListener listener = new View.OnClickListener() { // from class: rx.android.view.OnSubscribeViewClick.1
            @Override // android.view.View.OnClickListener
            public void onClick(View clicked) {
                observer.onNext(OnClickEvent.create(OnSubscribeViewClick.this.view));
            }
        };
        Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() { // from class: rx.android.view.OnSubscribeViewClick.2
            @Override // rx.functions.Action0
            public void call() {
                composite.removeOnClickListener(listener);
            }
        });
        if (this.emitInitialValue) {
            observer.onNext(OnClickEvent.create(this.view));
        }
        composite.addOnClickListener(listener);
        observer.add(subscription);
    }

    private static class CompositeOnClickListener implements View.OnClickListener {
        private final List<View.OnClickListener> listeners;

        private CompositeOnClickListener() {
            this.listeners = new ArrayList();
        }

        public boolean addOnClickListener(View.OnClickListener listener) {
            return this.listeners.add(listener);
        }

        public boolean removeOnClickListener(View.OnClickListener listener) {
            return this.listeners.remove(listener);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            for (View.OnClickListener listener : this.listeners) {
                listener.onClick(view);
            }
        }
    }

    private static class CachedListeners {
        private static final Map<View, CompositeOnClickListener> sCachedListeners = new WeakHashMap();

        private CachedListeners() {
        }

        public static CompositeOnClickListener getFromViewOrCreate(View view) {
            CompositeOnClickListener cached = sCachedListeners.get(view);
            if (cached == null) {
                CompositeOnClickListener listener = new CompositeOnClickListener();
                sCachedListeners.put(view, listener);
                view.setOnClickListener(listener);
                return listener;
            }
            return cached;
        }
    }
}
