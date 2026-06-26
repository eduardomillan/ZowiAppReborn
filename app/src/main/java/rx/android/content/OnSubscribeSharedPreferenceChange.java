package rx.android.content;

import android.content.SharedPreferences;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: classes.dex */
class OnSubscribeSharedPreferenceChange implements Observable.OnSubscribe<String> {
    private final SharedPreferences sharedPreferences;

    public OnSubscribeSharedPreferenceChange(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override // rx.functions.Action1
    public void call(final Subscriber<? super String> subscriber) {
        final SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: rx.android.content.OnSubscribeSharedPreferenceChange.1
            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                subscriber.onNext(key);
            }
        };
        subscriber.add(Subscriptions.create(new Action0() { // from class: rx.android.content.OnSubscribeSharedPreferenceChange.2
            @Override // rx.functions.Action0
            public void call() {
                OnSubscribeSharedPreferenceChange.this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
            }
        }));
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }
}
