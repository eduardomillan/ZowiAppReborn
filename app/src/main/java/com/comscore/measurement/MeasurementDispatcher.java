package com.comscore.measurement;

import com.comscore.analytics.Core;
import com.comscore.applications.AggregateMeasurement;
import com.comscore.metrics.Request;
import com.comscore.utils.CSLog;
import com.comscore.utils.Constants;
import com.comscore.utils.Date;
import com.comscore.utils.Storage;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class MeasurementDispatcher {
    public static final String DAY_CHECK_COUNTER = "q_dcc";
    public static final String DAY_CHECK_OFFSET = "q_dcf";
    public static final long MILLIS_PER_DAY = 86400000;
    public static final long MILLIS_PER_SECOND = 1000;
    Core a;
    private AggregateMeasurement h = null;
    protected Object g = new Object();
    protected AtomicInteger c = new AtomicInteger(0);
    protected AtomicLong b = new AtomicLong(-1);
    protected AtomicLong d = new AtomicLong(-1);
    protected AtomicInteger e = new AtomicInteger(0);
    protected AtomicInteger f = new AtomicInteger(0);

    public MeasurementDispatcher(Core core) {
        this.a = core;
    }

    private void a(AggregateMeasurement aggregateMeasurement) {
        synchronized (this.g) {
            if (this.h == null) {
                this.h = aggregateMeasurement;
                this.h.formatLists();
            } else {
                this.h.aggregateLabels(aggregateMeasurement.getAggregateLabels());
            }
        }
    }

    private boolean a() {
        Storage storage = this.a.getStorage();
        long jUnixTime = Date.unixTime();
        if (jUnixTime < this.b.get()) {
            this.c.set(0);
            this.b.set(jUnixTime);
            this.e.set(0);
            this.d.set(jUnixTime);
            storage.set(DAY_CHECK_COUNTER, Integer.toString(this.e.get(), 10));
            storage.set(DAY_CHECK_OFFSET, Long.toString(this.d.get(), 10));
        } else {
            if (jUnixTime - this.b.get() > 1000) {
                this.c.set(0);
                this.b.set(jUnixTime);
            }
            if (jUnixTime - this.d.get() > MILLIS_PER_DAY) {
                this.e.set(0);
                this.d.set(jUnixTime);
                storage.set(DAY_CHECK_COUNTER, Integer.toString(this.e.get(), 10));
                storage.set(DAY_CHECK_OFFSET, Long.toString(this.d.get(), 10));
            }
        }
        if (this.c.get() >= 20 || this.e.get() >= 6000) {
            return false;
        }
        this.c.incrementAndGet();
        this.e.getAndIncrement();
        storage.set(DAY_CHECK_COUNTER, Integer.toString(this.e.get(), 10));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Measurement measurement) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "sendMeasurmement: " + measurement.retrieveLabelsAsString(this.a.getMeasurementLabelOrder()));
            addAggregateData(measurement);
            if (measurement instanceof AggregateMeasurement) {
                return;
            }
            addEventCounter(measurement);
            a(measurement);
            new Request(this.a, measurement).send();
        }
    }

    protected void a(Measurement measurement) {
        if (this.a.isEnabled()) {
            measurement.setLabel(new Label("c12", this.a.getVisitorId(), false));
            if (this.a.getCrossPublisherId() != null) {
                measurement.setLabel(new Label("ns_ak", this.a.getCrossPublisherId(), false));
            }
        }
    }

    public void addAggregateData(Measurement measurement) {
        if (this.a.isEnabled()) {
            synchronized (this.g) {
                if (measurement instanceof AggregateMeasurement) {
                    a((AggregateMeasurement) measurement);
                    return;
                }
                if (this.h != null) {
                    Iterator<Label> it = this.h.getAggregateLabels().iterator();
                    while (it.hasNext()) {
                        measurement.setLabel(it.next());
                    }
                    this.h = null;
                }
            }
        }
    }

    public void addEventCounter(Measurement measurement) {
        if (this.a.isEnabled()) {
            this.f.getAndIncrement();
            measurement.setLabel(new Label("ns_ap_ec", String.valueOf(this.f), false));
        }
    }

    public void loadEventData() {
        if (this.a.isEnabled()) {
            Storage storage = this.a.getStorage();
            if (storage.has(DAY_CHECK_COUNTER).booleanValue() && storage.has(DAY_CHECK_OFFSET).booleanValue()) {
                try {
                    int i = Integer.parseInt(storage.get(DAY_CHECK_COUNTER), 10);
                    long j = Long.parseLong(storage.get(DAY_CHECK_OFFSET), 10);
                    if (Date.unixTime() >= j) {
                        this.e.set(i);
                        this.d.set(j);
                    }
                } catch (NumberFormatException e) {
                    if (Constants.DEBUG) {
                        CSLog.e(this, "Unexpected error parsing storage data: ");
                        CSLog.printStackTrace(e);
                        throw e;
                    }
                }
            }
        }
    }

    public boolean sendMeasurmement(Measurement measurement, boolean z) {
        if (!this.a.isEnabled() || measurement == null) {
            return false;
        }
        if (a() || this.a.getStorage() == null) {
            return this.a.getTaskExecutor().execute(new a(this, measurement), z);
        }
        CSLog.d(this, "Data not sent");
        return false;
    }
}
