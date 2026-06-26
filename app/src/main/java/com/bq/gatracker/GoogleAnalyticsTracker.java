package com.bq.gatracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.XmlRes;
import com.bq.analytics.core.AnalyticsTrackerImpl;
import com.bq.analytics.hit.Crash;
import com.bq.analytics.hit.Ecommerce;
import com.bq.analytics.hit.Event;
import com.bq.analytics.hit.Screen;
import com.bq.analytics.hit.Social;
import com.bq.analytics.hit.UserTiming;
import com.bq.analytics.tracker.Util;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class GoogleAnalyticsTracker extends AnalyticsTrackerImpl<Tracker> {
    public GoogleAnalyticsTracker(@NonNull Tracker tracker) {
        super(tracker);
    }

    public GoogleAnalyticsTracker(@NonNull Context context, @XmlRes int configResId) {
        this(GoogleAnalytics.getInstance(context).newTracker(configResId));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Ecommerce hit) {
        Util.requireNonNull(hit, "hit == null");
        ((Tracker) this.delegate).send(new HitBuilders.TransactionBuilder().setTransactionId((String) Util.requireNonNull(hit.transactionId, "transactionId == null")).setAffiliation((String) Util.requireNonNull(hit.transactionAffiliation, "transactionAffiliation == null")).setRevenue(((Double) Util.requireNonNull(hit.transactionRevenue, "transactionRevenue == null")).doubleValue()).setTax(((Double) Util.requireNonNull(hit.transactionTax, "transactionTax == null")).doubleValue()).setShipping(((Double) Util.requireNonNull(hit.transactionShipping, "transactionShipping == null")).doubleValue()).setCurrencyCode(hit.transactionCurrencyCode).build());
        ((Tracker) this.delegate).send(new HitBuilders.ItemBuilder().setTransactionId(hit.transactionId).setName((String) Util.requireNonNull(hit.itemName, "itemName == null")).setSku((String) Util.requireNonNull(hit.itemSku, "itemSku == null")).setCategory(hit.itemCategory).setPrice(((Double) Util.requireNonNull(hit.itemPrice, "itemPrice == null")).doubleValue()).setQuantity(((Long) Util.requireNonNull(hit.itemQuantity, "itemQuantity == null")).longValue()).setCurrencyCode(hit.transactionCurrencyCode).build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Event hit) {
        Util.requireNonNull(hit, "hit == null");
        HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder().setCategory((String) Util.requireNonNull(hit.category, "category == null")).setAction((String) Util.requireNonNull(hit.action, "action == null")).setLabel(hit.label);
        if (hit.value != null) {
            builder.setValue(hit.value.longValue());
        }
        ((Tracker) this.delegate).send(builder.build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Crash hit) {
        Util.requireNonNull(hit, "hit == null");
        ((Tracker) this.delegate).send(new HitBuilders.ExceptionBuilder().setDescription(hit.description).setFatal(((Boolean) Util.requireNonNull(hit.isFatal, "isFatal == null")).booleanValue()).build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Screen hit) {
        Util.requireNonNull(hit, "hit == null");
        ((Tracker) this.delegate).setScreenName((String) Util.requireNonNull(hit.screen, "screen == null"));
        HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder();
        if (hit.customDimensions != null) {
            for (Map.Entry<Integer, String> entry : hit.customDimensions.entrySet()) {
                builder.setCustomDimension(entry.getKey().intValue(), entry.getValue());
            }
        }
        ((Tracker) this.delegate).send(builder.build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull Social hit) {
        Util.requireNonNull(hit, "hit == null");
        ((Tracker) this.delegate).send(new HitBuilders.SocialBuilder().setNetwork((String) Util.requireNonNull(hit.network, "network == null")).setAction((String) Util.requireNonNull(hit.action, "action == null")).setTarget(hit.target).build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bq.analytics.core.AnalyticsTracker
    public void send(@NotNull UserTiming hit) {
        Util.requireNonNull(hit, "hit == null");
        ((Tracker) this.delegate).send(new HitBuilders.TimingBuilder().setCategory((String) Util.requireNonNull(hit.category, "category == null")).setValue(((Long) Util.requireNonNull(hit.value, "value == null")).longValue()).setVariable(hit.name).setLabel(hit.label).build());
    }
}
