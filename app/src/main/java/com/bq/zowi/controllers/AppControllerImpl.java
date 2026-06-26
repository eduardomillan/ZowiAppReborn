package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class AppControllerImpl implements AppController {
    private static final String KEY_APP_LOGS = "key:appLogs";
    private final SharedPreferences sharedPreferences;

    public AppControllerImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.bq.zowi.controllers.AppController
    public Single<Boolean> isFirstUsage() {
        return getAppLogs().map(new Func1<Set<String>, Boolean>() { // from class: com.bq.zowi.controllers.AppControllerImpl.1
            @Override // rx.functions.Func1
            public Boolean call(Set<String> strings) {
                return Boolean.valueOf(strings.size() == 0);
            }
        });
    }

    @Override // com.bq.zowi.controllers.AppController
    public Single<Void> logAppStarted() {
        return getAppLogs().flatMap(new Func1<Set<String>, Single<Void>>() { // from class: com.bq.zowi.controllers.AppControllerImpl.2
            @Override // rx.functions.Func1
            public Single<Void> call(Set<String> days) {
                if (days == null) {
                    days = new HashSet<>();
                }
                days.add(String.valueOf(AppControllerImpl.today()));
                return AppControllerImpl.this.saveAppLogs(days);
            }
        });
    }

    @Override // com.bq.zowi.controllers.AppController
    public Single<Integer> getDaysOfUse() {
        return getAppLogs().map(new Func1<Set<String>, Integer>() { // from class: com.bq.zowi.controllers.AppControllerImpl.3
            @Override // rx.functions.Func1
            public Integer call(Set<String> strings) {
                return Integer.valueOf(strings.size());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long today() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(10);
        calendar.clear(11);
        calendar.clear(12);
        calendar.clear(13);
        calendar.clear(14);
        return calendar.getTimeInMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Single<Void> saveAppLogs(final Set<String> days) {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.AppControllerImpl.4
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                AppControllerImpl.this.sharedPreferences.edit().putStringSet(AppControllerImpl.KEY_APP_LOGS, days).commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }

    private Single<Set<String>> getAppLogs() {
        Set<String> appLogsStringSetCopy = new HashSet<>(this.sharedPreferences.getStringSet(KEY_APP_LOGS, new HashSet()));
        return Single.just(appLogsStringSetCopy);
    }

    @Override // com.bq.zowi.controllers.AppController
    public Single<Void> resetAppLogs() {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.controllers.AppControllerImpl.5
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                Map<String, ?> allPrefs = AppControllerImpl.this.sharedPreferences.getAll();
                SharedPreferences.Editor editor = AppControllerImpl.this.sharedPreferences.edit();
                for (String prefKey : allPrefs.keySet()) {
                    if (prefKey.equals(AppControllerImpl.KEY_APP_LOGS)) {
                        editor.remove(prefKey);
                    }
                }
                editor.commit();
                singleSubscriber.onSuccess(null);
            }
        });
    }
}
