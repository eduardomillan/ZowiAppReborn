package com.comscore.utils;

/* JADX INFO: loaded from: classes.dex */
class d implements Runnable {
    final /* synthetic */ Storage a;

    d(Storage storage) {
        this.a = storage;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.b();
        this.a.c();
    }
}
