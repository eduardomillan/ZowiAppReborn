package rx.android;

import android.os.Looper;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: classes.dex */
public final class AndroidSubscriptions {
    private AndroidSubscriptions() {
        throw new AssertionError("No instances");
    }

    public static Subscription unsubscribeInUiThread(final Action0 unsubscribe) {
        return Subscriptions.create(new Action0() { // from class: rx.android.AndroidSubscriptions.1
            @Override // rx.functions.Action0
            public void call() {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    unsubscribe.call();
                } else {
                    final Scheduler.Worker inner = AndroidSchedulers.mainThread().createWorker();
                    inner.schedule(new Action0() { // from class: rx.android.AndroidSubscriptions.1.1
                        @Override // rx.functions.Action0
                        public void call() {
                            unsubscribe.call();
                            inner.unsubscribe();
                        }
                    });
                }
            }
        });
    }
}
