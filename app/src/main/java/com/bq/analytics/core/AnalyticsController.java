package com.bq.analytics.core;

import com.bq.analytics.hit.Crash;
import com.bq.analytics.hit.Ecommerce;
import com.bq.analytics.hit.Event;
import com.bq.analytics.hit.Screen;
import com.bq.analytics.hit.Social;
import com.bq.analytics.hit.UserTiming;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticsController implements AnalyticsTracker {
    protected final List<AnalyticsTracker> trackers = new CopyOnWriteArrayList();

    public final void addTracker(@NotNull AnalyticsTracker tracker) {
        this.trackers.add(tracker);
    }

    public final boolean removeTracker(@NotNull AnalyticsTracker tracker) {
        return this.trackers.remove(tracker);
    }

    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Ecommerce hit) {
        for (AnalyticsTracker tracker : this.trackers) {
            tracker.send(hit);
        }
    }

    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Event hit) {
        for (AnalyticsTracker tracker : this.trackers) {
            tracker.send(hit);
        }
    }

    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Crash hit) {
        for (AnalyticsTracker tracker : this.trackers) {
            tracker.send(hit);
        }
    }

    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Screen hit) {
        for (AnalyticsTracker tracker : this.trackers) {
            tracker.send(hit);
        }
    }

    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Social hit) {
        for (AnalyticsTracker tracker : this.trackers) {
            tracker.send(hit);
        }
    }

    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull UserTiming hit) {
        for (AnalyticsTracker tracker : this.trackers) {
            tracker.send(hit);
        }
    }
}
