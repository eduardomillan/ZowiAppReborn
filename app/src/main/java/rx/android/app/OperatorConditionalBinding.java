package rx.android.app;

import android.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.android.internal.Assertions;
import rx.functions.Func1;
import rx.internal.util.UtilityFunctions;

/* JADX INFO: loaded from: classes.dex */
final class OperatorConditionalBinding<T, R> implements Observable.Operator<T, T> {
    private static final String LOG_TAG = "ConditionalBinding";
    private R boundRef;
    private final Func1<? super R, Boolean> predicate;

    public OperatorConditionalBinding(R bound, Func1<? super R, Boolean> predicate) {
        this.boundRef = bound;
        this.predicate = predicate;
    }

    public OperatorConditionalBinding(R bound) {
        this.boundRef = bound;
        this.predicate = UtilityFunctions.alwaysTrue();
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) { // from class: rx.android.app.OperatorConditionalBinding.1
            @Override // rx.Observer
            public void onCompleted() {
                Assertions.assertUiThread();
                if (shouldForwardNotification()) {
                    subscriber.onCompleted();
                } else {
                    handleLostBinding("onCompleted");
                }
            }

            @Override // rx.Observer
            public void onError(Throwable e) {
                Assertions.assertUiThread();
                if (shouldForwardNotification()) {
                    subscriber.onError(e);
                } else {
                    handleLostBinding("onError");
                }
            }

            @Override // rx.Observer
            public void onNext(T t) {
                Assertions.assertUiThread();
                if (shouldForwardNotification()) {
                    subscriber.onNext(t);
                } else {
                    handleLostBinding("onNext");
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            private boolean shouldForwardNotification() {
                return OperatorConditionalBinding.this.boundRef != null && ((Boolean) OperatorConditionalBinding.this.predicate.call(OperatorConditionalBinding.this.boundRef)).booleanValue();
            }

            private void handleLostBinding(String context) {
                log("bound object has become invalid; skipping " + context);
                log("unsubscribing...");
                OperatorConditionalBinding.this.boundRef = null;
                unsubscribe();
            }

            private void log(String message) {
                if (Log.isLoggable(OperatorConditionalBinding.LOG_TAG, 3)) {
                    Log.d(OperatorConditionalBinding.LOG_TAG, message);
                }
            }
        };
    }

    R getBoundRef() {
        return this.boundRef;
    }
}
