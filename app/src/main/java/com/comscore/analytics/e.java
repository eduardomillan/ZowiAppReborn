package com.comscore.analytics;

import com.comscore.utils.Utils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class e implements Runnable {
    final /* synthetic */ HashMap a;
    final /* synthetic */ Core b;

    e(Core core, HashMap map) {
        this.b = core;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.ac.putAll(Utils.mapOfStrings(this.a));
    }
}
