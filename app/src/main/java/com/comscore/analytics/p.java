package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class p implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ Core b;

    p(Core core, int i) {
        this.b = core;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.setCacheMaxPosts(this.a);
    }
}
