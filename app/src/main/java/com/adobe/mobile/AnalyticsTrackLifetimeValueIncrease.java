package com.adobe.mobile;

import android.content.SharedPreferences;
import com.adobe.mobile.StaticMethods;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackLifetimeValueIncrease {
    private static final String LOCAL_STORAGE_LIFETIME_VALUE_KEY = "ADB_LIFETIME_VALUE";
    private static final String LTV_ACTION_NAME = "LifetimeValueIncrease";
    protected static final String LTV_AMOUNT_KEY = "a.ltv.amount";
    private static final String LTV_INCREASE_KEY = "a.ltv.increase";
    private static final Object _lifetimeValueMutex = new Object();

    AnalyticsTrackLifetimeValueIncrease() {
    }

    public static void trackLifetimeValueIncrease(BigDecimal amount, Map<String, Object> data) {
        if (amount == null || amount.signum() == -1) {
            StaticMethods.logWarningFormat("Analytics - trackLifetimeValueIncrease failed, invalid amount specified '%f'", amount);
            return;
        }
        incrementLifetimeValue(amount);
        HashMap<String, Object> contextData = new HashMap<>();
        if (data != null) {
            contextData.putAll(data);
        }
        if (getLifetimeValue() != null) {
            contextData.put(LTV_AMOUNT_KEY, getLifetimeValue());
            contextData.put(LTV_INCREASE_KEY, amount);
            AnalyticsTrackInternal.trackInternal(LTV_ACTION_NAME, contextData, StaticMethods.getTimeSince1970());
        }
    }

    protected static BigDecimal getLifetimeValue() {
        BigDecimal lifetimeValue;
        synchronized (_lifetimeValueMutex) {
            try {
                try {
                    lifetimeValue = new BigDecimal(StaticMethods.getSharedPreferences().getString(LOCAL_STORAGE_LIFETIME_VALUE_KEY, "0"));
                } catch (StaticMethods.NullContextException e) {
                    StaticMethods.logErrorFormat("Analytics - Error getting current lifetime value:(%s).", e.getMessage());
                    lifetimeValue = null;
                }
            } catch (NumberFormatException e2) {
                lifetimeValue = new BigDecimal("0");
            }
        }
        return lifetimeValue;
    }

    protected static void setLifetimeValue(BigDecimal ltv) {
        synchronized (_lifetimeValueMutex) {
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                if (ltv == null || ltv.signum() == -1) {
                    editor.putString(LOCAL_STORAGE_LIFETIME_VALUE_KEY, "0.00");
                } else {
                    editor.putString(LOCAL_STORAGE_LIFETIME_VALUE_KEY, ltv.toString());
                }
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Analytics - Error updating lifetime value: (%s).", e.getMessage());
            }
        }
    }

    private static void incrementLifetimeValue(BigDecimal ltv) {
        synchronized (_lifetimeValueMutex) {
            BigDecimal currentLifetimeValue = getLifetimeValue();
            if (ltv != null && ltv.signum() != -1 && currentLifetimeValue != null) {
                setLifetimeValue(currentLifetimeValue.add(ltv));
            }
        }
    }
}
