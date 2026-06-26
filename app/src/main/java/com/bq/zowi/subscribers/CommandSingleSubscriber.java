package com.bq.zowi.subscribers;

import com.bq.zowi.utils.Grove;
import rx.SingleSubscriber;

/* JADX INFO: loaded from: classes.dex */
public class CommandSingleSubscriber extends SingleSubscriber<Void> {
    @Override // rx.SingleSubscriber
    public void onSuccess(Void value) {
    }

    @Override // rx.SingleSubscriber
    public void onError(Throwable error) {
        Grove.d("Send COMMAND to Zowi ERROR! " + error.toString(), new Object[0]);
        error.printStackTrace();
    }
}
