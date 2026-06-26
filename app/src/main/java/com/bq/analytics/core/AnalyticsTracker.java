package com.bq.analytics.core;

import com.bq.analytics.hit.Crash;
import com.bq.analytics.hit.Ecommerce;
import com.bq.analytics.hit.Event;
import com.bq.analytics.hit.Screen;
import com.bq.analytics.hit.Social;
import com.bq.analytics.hit.UserTiming;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public interface AnalyticsTracker<Delegate> {
    void send(@NotNull Crash crash);

    void send(@NotNull Ecommerce ecommerce);

    void send(@NotNull Event event);

    void send(@NotNull Screen screen);

    void send(@NotNull Social social);

    void send(@NotNull UserTiming userTiming);
}
