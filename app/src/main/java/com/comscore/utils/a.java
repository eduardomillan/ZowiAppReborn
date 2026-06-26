package com.comscore.utils;

/* JADX INFO: loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ ConnectivityChangeReceiver a;

    a(ConnectivityChangeReceiver connectivityChangeReceiver) {
        this.a = connectivityChangeReceiver;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.b(false);
    }
}
