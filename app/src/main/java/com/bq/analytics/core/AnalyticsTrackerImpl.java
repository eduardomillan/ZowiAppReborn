package com.bq.analytics.core;

/* JADX INFO: loaded from: classes.dex */
public abstract class AnalyticsTrackerImpl<Delegate> implements AnalyticsTracker<Delegate> {
    protected final Delegate delegate;

    public AnalyticsTrackerImpl(Delegate delegate) {
        this.delegate = delegate;
    }
}
