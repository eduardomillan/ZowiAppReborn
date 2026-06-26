package com.bq.zowi.rx;

import com.bq.zowi.utils.Grove;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {
    private final int maxRetries;
    private int retryCount = 0;
    private final int retryDelayMillis;

    static /* synthetic */ int access$004(RetryWithDelay x0) {
        int i = x0.retryCount + 1;
        x0.retryCount = i;
        return i;
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override // rx.functions.Func1
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts.flatMap(new Func1<Throwable, Observable<?>>() { // from class: com.bq.zowi.rx.RetryWithDelay.1
            @Override // rx.functions.Func1
            public Observable<?> call(Throwable throwable) {
                if (RetryWithDelay.access$004(RetryWithDelay.this) < RetryWithDelay.this.maxRetries) {
                    Grove.d(throwable, "Rx Retry: " + RetryWithDelay.this.retryCount, new Object[0]);
                    return Observable.timer(RetryWithDelay.this.retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                Grove.d(throwable, "Rx Max retries hit. Passing error along", new Object[0]);
                return Observable.error(throwable);
            }
        });
    }
}
