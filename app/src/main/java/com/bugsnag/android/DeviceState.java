package com.bugsnag.android;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import com.bugsnag.android.JsonStream;
import com.comscore.android.id.IdHelperAndroid;
import java.io.IOException;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
final class DeviceState implements JsonStream.Streamable {
    private Context appContext;
    private Float batteryLevel;
    private Boolean charging;
    private Long freeDisk;
    private Long freeMemory;
    private String locationStatus;
    private String networkAccess;
    private String orientation;
    private String time;

    DeviceState(Context appContext) {
        this.appContext = appContext;
        this.freeMemory = Long.valueOf(Runtime.getRuntime().maxMemory() != Long.MAX_VALUE ? (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) + Runtime.getRuntime().freeMemory() : Runtime.getRuntime().freeMemory());
        String str = null;
        switch (this.appContext.getResources().getConfiguration().orientation) {
            case 1:
            case 2:
                str = "portrait";
                break;
        }
        this.orientation = str;
        this.batteryLevel = getBatteryLevel();
        this.freeDisk = getFreeDisk();
        this.charging = isCharging();
        this.locationStatus = getLocationStatus();
        this.networkAccess = getNetworkAccess();
        this.time = DateUtils.toISO8601(new Date());
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginObject();
        writer.name("freeMemory").value(this.freeMemory);
        writer.name("orientation").value(this.orientation);
        writer.name("batteryLevel").value(this.batteryLevel);
        writer.name("freeDisk").value(this.freeDisk);
        writer.name("charging").value(this.charging);
        writer.name("locationStatus").value(this.locationStatus);
        writer.name("networkAccess").value(this.networkAccess);
        writer.name("time").value(this.time);
        writer.endObject();
    }

    private Float getBatteryLevel() {
        try {
            IntentFilter ifilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
            Intent batteryStatus = this.appContext.registerReceiver(null, ifilter);
            return Float.valueOf(batteryStatus.getIntExtra("level", -1) / batteryStatus.getIntExtra("scale", -1));
        } catch (Exception e) {
            AppData.warn("Could not get batteryLevel");
            return null;
        }
    }

    private static Long getFreeDisk() {
        try {
            StatFs externalStat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long externalBytesAvailable = ((long) externalStat.getBlockSize()) * ((long) externalStat.getBlockCount());
            StatFs internalStat = new StatFs(Environment.getDataDirectory().getPath());
            return Long.valueOf(Math.min(((long) internalStat.getBlockSize()) * ((long) internalStat.getBlockCount()), externalBytesAvailable));
        } catch (Exception e) {
            AppData.warn("Could not get freeDisk");
            return null;
        }
    }

    private Boolean isCharging() {
        try {
            IntentFilter ifilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
            int status = this.appContext.registerReceiver(null, ifilter).getIntExtra("status", -1);
            return Boolean.valueOf(status == 2 || status == 5);
        } catch (Exception e) {
            AppData.warn("Could not get charging status");
            return null;
        }
    }

    private String getLocationStatus() {
        try {
            String providersAllowed = Settings.Secure.getString(this.appContext.getContentResolver(), "location_providers_allowed");
            if (providersAllowed != null) {
                if (providersAllowed.length() > 0) {
                    return "allowed";
                }
            }
            return "disallowed";
        } catch (Exception e) {
            AppData.warn("Could not get locationStatus");
            return null;
        }
    }

    private String getNetworkAccess() {
        try {
            NetworkInfo activeNetwork = ((ConnectivityManager) this.appContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                if (activeNetwork.getType() == 1) {
                    return "wifi";
                }
                if (activeNetwork.getType() == 9) {
                    return "ethernet";
                }
                return "cellular";
            }
            return IdHelperAndroid.NO_ID_AVAILABLE;
        } catch (Exception e) {
            AppData.warn("Could not get network access information, we recommend granting the 'android.permission.ACCESS_NETWORK_STATE' permission");
            return null;
        }
    }
}
