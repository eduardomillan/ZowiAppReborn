package com.bq.zowi.subscribers;

import com.bq.zowi.utils.Grove;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class CommandSingleSubscriber implements CompletableObserver {
    @Override
    public void onSubscribe(Disposable disposable) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable error) {
        Grove.d("Send COMMAND to Zowi ERROR! " + error.toString(), new Object[0]);
        error.printStackTrace();
    }
}
