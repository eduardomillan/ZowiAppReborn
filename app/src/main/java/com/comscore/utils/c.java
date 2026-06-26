package com.comscore.utils;

import com.comscore.measurement.Measurement;

/* JADX INFO: loaded from: classes.dex */
class c implements Runnable {
    final /* synthetic */ Measurement a;
    final /* synthetic */ OfflineMeasurementsCache b;

    c(OfflineMeasurementsCache offlineMeasurementsCache, Measurement measurement) {
        this.b = offlineMeasurementsCache;
        this.a = measurement;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.saveEvent(this.a.retrieveLabelsAsString(this.b.a.getMeasurementLabelOrder()));
    }
}
