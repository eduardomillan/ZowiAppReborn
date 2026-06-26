package com.bugsnag.android;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import com.bugsnag.android.JsonStream;
import com.comscore.streaming.Constants;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
final class DeviceData implements JsonStream.Streamable {
    private Context appContext;
    private float dpi;
    private String id;
    private String locale;
    private boolean rooted;
    private float screenDensity;
    private String screenResolution;
    private long totalMemory;

    DeviceData(Context appContext) {
        this.appContext = appContext;
        this.screenDensity = this.appContext.getResources().getDisplayMetrics().density;
        this.dpi = this.appContext.getResources().getDisplayMetrics().densityDpi;
        DisplayMetrics displayMetrics = this.appContext.getResources().getDisplayMetrics();
        this.screenResolution = String.format("%dx%d", Integer.valueOf(Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels)), Integer.valueOf(Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels)));
        this.totalMemory = Runtime.getRuntime().maxMemory() != Long.MAX_VALUE ? Runtime.getRuntime().maxMemory() : Runtime.getRuntime().totalMemory();
        this.rooted = isRooted();
        this.locale = Locale.getDefault().toString();
        this.id = Settings.Secure.getString(this.appContext.getContentResolver(), "android_id");
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginObject();
        writer.name("manufacturer").value(Build.MANUFACTURER);
        writer.name("brand").value(Build.BRAND);
        writer.name("model").value(Build.MODEL);
        writer.name("screenDensity").value(this.screenDensity);
        writer.name("dpi").value(this.dpi);
        writer.name("screenResolution").value(this.screenResolution);
        writer.name("totalMemory").value(this.totalMemory);
        writer.name("osName").value(Constants.C10_VALUE);
        writer.name("osBuild").value(Build.DISPLAY);
        writer.name("apiLevel").value(Build.VERSION.SDK_INT);
        writer.name("jailbroken").value(this.rooted);
        writer.name("locale").value(this.locale);
        writer.name("osVersion").value(Build.VERSION.RELEASE);
        writer.name("id").value(this.id);
        writer.endObject();
    }

    public final String getUserId() {
        return this.id;
    }

    private static boolean isRooted() {
        boolean hasTestKeys = Build.TAGS != null && Build.TAGS.contains("test-keys");
        boolean hasSuperUserApk = false;
        try {
            hasSuperUserApk = new File("/system/app/Superuser.apk").exists();
        } catch (Exception e) {
        }
        return hasTestKeys || hasSuperUserApk;
    }
}
