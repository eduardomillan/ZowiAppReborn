package com.comscore.analytics;

import com.comscore.applications.EventType;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class y implements Runnable {
    final /* synthetic */ EventType a;
    final /* synthetic */ HashMap b;
    final /* synthetic */ Core c;

    y(Core core, EventType eventType, HashMap map) {
        this.c = core;
        this.a = eventType;
        this.b = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a(this.a, this.b);
    }
}
