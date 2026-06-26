package rx.android.view;

import android.view.View;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/* JADX INFO: loaded from: classes.dex */
final class OnSubscribeViewDetachedFromWindowFirst implements Observable.OnSubscribe<View> {
    private final View view;

    public OnSubscribeViewDetachedFromWindowFirst(View view) {
        this.view = view;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super View> subscriber) {
        SubscriptionAdapter adapter = new SubscriptionAdapter(subscriber, this.view);
        subscriber.add(adapter);
        this.view.addOnAttachStateChangeListener(adapter);
    }

    private static class SubscriptionAdapter implements View.OnAttachStateChangeListener, Subscription {
        private Subscriber<? super View> subscriber;
        private View view;

        public SubscriptionAdapter(Subscriber<? super View> subscriber, View view) {
            this.subscriber = subscriber;
            this.view = view;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View v) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View v) {
            if (!isUnsubscribed()) {
                Subscriber<? super View> originalSubscriber = this.subscriber;
                unsubscribe();
                originalSubscriber.onNext(v);
                originalSubscriber.onCompleted();
            }
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            if (!isUnsubscribed()) {
                this.view.removeOnAttachStateChangeListener(this);
                this.view = null;
                this.subscriber = null;
            }
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.view == null;
        }
    }
}
