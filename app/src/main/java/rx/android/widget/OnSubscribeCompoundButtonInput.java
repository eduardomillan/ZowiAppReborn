package rx.android.widget;

import android.view.View;
import android.widget.CompoundButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.android.view.OnCheckedChangeEvent;
import rx.functions.Action0;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeCompoundButtonInput implements Observable.OnSubscribe<OnCheckedChangeEvent> {
    private final CompoundButton button;
    private final boolean emitInitialValue;

    public OnSubscribeCompoundButtonInput(CompoundButton button, boolean emitInitialValue) {
        this.emitInitialValue = emitInitialValue;
        this.button = button;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super OnCheckedChangeEvent> observer) {
        Assertions.assertUiThread();
        final CompositeOnCheckedChangeListener composite = CachedListeners.getFromViewOrCreate(this.button);
        final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() { // from class: rx.android.widget.OnSubscribeCompoundButtonInput.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton view, boolean checked) {
                observer.onNext(OnCheckedChangeEvent.create(OnSubscribeCompoundButtonInput.this.button, checked));
            }
        };
        Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() { // from class: rx.android.widget.OnSubscribeCompoundButtonInput.2
            @Override // rx.functions.Action0
            public void call() {
                composite.removeOnCheckedChangeListener(listener);
            }
        });
        if (this.emitInitialValue) {
            observer.onNext(OnCheckedChangeEvent.create(this.button));
        }
        composite.addOnCheckedChangeListener(listener);
        observer.add(subscription);
    }

    private static class CompositeOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        private final List<CompoundButton.OnCheckedChangeListener> listeners;

        private CompositeOnCheckedChangeListener() {
            this.listeners = new ArrayList();
        }

        public boolean addOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            return this.listeners.add(listener);
        }

        public boolean removeOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
            return this.listeners.remove(listener);
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton button, boolean checked) {
            for (CompoundButton.OnCheckedChangeListener listener : this.listeners) {
                listener.onCheckedChanged(button, checked);
            }
        }
    }

    private static class CachedListeners {
        private static final Map<View, CompositeOnCheckedChangeListener> sCachedListeners = new WeakHashMap();

        private CachedListeners() {
        }

        public static CompositeOnCheckedChangeListener getFromViewOrCreate(CompoundButton button) {
            CompositeOnCheckedChangeListener cached = sCachedListeners.get(button);
            if (cached == null) {
                CompositeOnCheckedChangeListener listener = new CompositeOnCheckedChangeListener();
                sCachedListeners.put(button, listener);
                button.setOnCheckedChangeListener(listener);
                return listener;
            }
            return cached;
        }
    }
}
