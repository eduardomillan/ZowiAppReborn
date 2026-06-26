package com.comscore.measurement;

/* JADX INFO: loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ Measurement a;
    final /* synthetic */ MeasurementDispatcher b;

    a(MeasurementDispatcher measurementDispatcher, Measurement measurement) {
        this.b = measurementDispatcher;
        this.a = measurement;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
