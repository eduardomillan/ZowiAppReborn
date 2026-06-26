package com.comscore.streaming;

import com.comscore.applications.ApplicationMeasurement;
import com.comscore.applications.EventType;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class e implements Runnable {
    final /* synthetic */ HashMap a;
    final /* synthetic */ String b;
    final /* synthetic */ StreamSense c;

    e(StreamSense streamSense, HashMap map, String str) {
        this.c = streamSense;
        this.a = map;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a.getMeasurementDispatcher().sendMeasurmement(ApplicationMeasurement.newApplicationMeasurement(this.c.a, EventType.HIDDEN, this.a, this.b), false);
    }
}
