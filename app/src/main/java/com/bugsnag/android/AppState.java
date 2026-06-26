package com.bugsnag.android;

import android.app.ActivityManager;
import android.content.Context;
import android.os.SystemClock;
import com.bugsnag.android.JsonStream;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class AppState implements JsonStream.Streamable {
    private static Long startTime = Long.valueOf(SystemClock.elapsedRealtime());
    private Context appContext;
    private Long duration = Long.valueOf(SystemClock.elapsedRealtime() - startTime.longValue());
    private Boolean inForeground = isInForeground();
    private String activeScreen = getActiveScreen();
    private Long memoryUsage = Long.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    private Boolean lowMemory = isLowMemory();

    static void init() {
    }

    AppState(Context appContext) {
        this.appContext = appContext;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginObject();
        writer.name("duration").value(this.duration);
        writer.name("inForeground").value(this.inForeground);
        writer.name("activeScreen").value(this.activeScreen);
        writer.name("memoryUsage").value(this.memoryUsage);
        writer.name("lowMemory").value(this.lowMemory);
        writer.endObject();
    }

    public final String getActiveScreenClass() {
        if (this.activeScreen != null) {
            return this.activeScreen.substring(this.activeScreen.lastIndexOf(46) + 1);
        }
        return null;
    }

    private Boolean isLowMemory() {
        try {
            ActivityManager activityManager = (ActivityManager) this.appContext.getSystemService("activity");
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memInfo);
            return Boolean.valueOf(memInfo.lowMemory);
        } catch (Exception e) {
            AppData.warn("Could not check lowMemory status");
            return null;
        }
    }

    private String getActiveScreen() {
        try {
            return ((ActivityManager) this.appContext.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName();
        } catch (Exception e) {
            AppData.warn("Could not get active screen information, we recommend granting the 'android.permission.GET_TASKS' permission");
            return null;
        }
    }

    private Boolean isInForeground() {
        boolean zValueOf;
        try {
            List<ActivityManager.RunningTaskInfo> tasks = ((ActivityManager) this.appContext.getSystemService("activity")).getRunningTasks(1);
            if (tasks.isEmpty()) {
                zValueOf = false;
            } else {
                zValueOf = Boolean.valueOf(tasks.get(0).topActivity.getPackageName().equalsIgnoreCase(this.appContext.getPackageName()));
            }
            return zValueOf;
        } catch (Exception e) {
            AppData.warn("Could not check if app is in the foreground, we recommend granting the 'android.permission.GET_TASKS' permission");
            return null;
        }
    }
}
