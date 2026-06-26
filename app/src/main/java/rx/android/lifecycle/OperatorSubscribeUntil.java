package rx.android.lifecycle;

import rx.Observable;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

/* JADX INFO: loaded from: classes.dex */
final class OperatorSubscribeUntil<T, R> implements Observable.Operator<T, T> {
    private final Observable<? extends R> other;

    public OperatorSubscribeUntil(Observable<? extends R> other) {
        this.other = other;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        final SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        this.other.unsafeSubscribe((Subscriber<? super Object>) new Subscriber<R>(subscriber) { // from class: rx.android.lifecycle.OperatorSubscribeUntil.1
            @Override // rx.Observer
            public void onCompleted() throws Throwable {
                serializedSubscriber.unsubscribe();
            }

            @Override // rx.Observer
            public void onError(Throwable e) {
                serializedSubscriber.onError(e);
            }

            @Override // rx.Observer
            public void onNext(R t) throws Throwable {
                serializedSubscriber.unsubscribe();
            }
        });
        return serializedSubscriber;
    }
}
