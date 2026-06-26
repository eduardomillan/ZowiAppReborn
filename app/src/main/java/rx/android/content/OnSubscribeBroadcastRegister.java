package rx.android.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeBroadcastRegister implements Observable.OnSubscribe<Intent> {
    private final String broadcastPermission;
    private final Context context;
    private final IntentFilter intentFilter;
    private final Handler schedulerHandler;

    public OnSubscribeBroadcastRegister(Context context, IntentFilter intentFilter, String broadcastPermission, Handler schedulerHandler) {
        this.context = context;
        this.intentFilter = intentFilter;
        this.broadcastPermission = broadcastPermission;
        this.schedulerHandler = schedulerHandler;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super Intent> subscriber) {
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: rx.android.content.OnSubscribeBroadcastRegister.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                subscriber.onNext(intent);
            }
        };
        Subscription subscription = Subscriptions.create(new Action0() { // from class: rx.android.content.OnSubscribeBroadcastRegister.2
            @Override // rx.functions.Action0
            public void call() {
                OnSubscribeBroadcastRegister.this.context.unregisterReceiver(broadcastReceiver);
            }
        });
        subscriber.add(subscription);
        this.context.registerReceiver(broadcastReceiver, this.intentFilter, this.broadcastPermission, this.schedulerHandler);
    }
}
