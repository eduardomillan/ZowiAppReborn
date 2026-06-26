package rx.android.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeLocalBroadcastRegister implements Observable.OnSubscribe<Intent> {
    private final Context context;
    private final IntentFilter intentFilter;

    public OnSubscribeLocalBroadcastRegister(Context context, IntentFilter intentFilter) {
        this.context = context;
        this.intentFilter = intentFilter;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super Intent> subscriber) {
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.context);
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: rx.android.content.OnSubscribeLocalBroadcastRegister.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                subscriber.onNext(intent);
            }
        };
        Subscription subscription = Subscriptions.create(new Action0() { // from class: rx.android.content.OnSubscribeLocalBroadcastRegister.2
            @Override // rx.functions.Action0
            public void call() {
                localBroadcastManager.unregisterReceiver(broadcastReceiver);
            }
        });
        subscriber.add(subscription);
        localBroadcastManager.registerReceiver(broadcastReceiver, this.intentFilter);
    }
}
