package com.bugsnag.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.bugsnag.android.JsonStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLConnection;

/* JADX INFO: loaded from: classes.dex */
class AppData implements JsonStream.Streamable {
    private Context appContext;
    private Configuration config;
    private String packageName;
    private String appName = getAppName();
    private Integer versionCode = getVersionCode();
    private String versionName = getVersionName();
    private String guessedReleaseStage = guessReleaseStage();

    static void info(String message) {
        Log.i("Bugsnag", message);
    }

    static void warn(String message) {
        Log.w("Bugsnag", message);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    static void warn(String message, Throwable e) {
        Log.w("Bugsnag", message, e);
    }

    public static void close(URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    AppData(Context appContext, Configuration config) {
        this.config = config;
        this.appContext = appContext;
        this.packageName = this.appContext.getPackageName();
    }

    public static int copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[4096];
        long count = 0;
        while (true) {
            int n = input.read(buffer);
            if (-1 == n) {
                break;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
        if (count > 2147483647L) {
            return -1;
        }
        return (int) count;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(this.packageName);
        writer.name("name").value(this.appName);
        writer.name("packageName").value(this.packageName);
        writer.name("versionName").value(this.versionName);
        writer.name("versionCode").value(this.versionCode);
        if (this.config.buildUUID != null) {
            writer.name("buildUUID").value(this.config.buildUUID);
        }
        writer.name("version").value(this.config.appVersion != null ? this.config.appVersion : this.versionName);
        writer.name("releaseStage").value(getReleaseStage());
        writer.endObject();
    }

    public final String getReleaseStage() {
        return this.config.releaseStage != null ? this.config.releaseStage : this.guessedReleaseStage;
    }

    private String getAppName() {
        try {
            PackageManager packageManager = this.appContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(this.packageName, 0);
            return (String) packageManager.getApplicationLabel(appInfo);
        } catch (PackageManager.NameNotFoundException e) {
            warn("Could not get app name");
            return null;
        }
    }

    private Integer getVersionCode() {
        try {
            return Integer.valueOf(this.appContext.getPackageManager().getPackageInfo(this.packageName, 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            warn("Could not get versionCode");
            return null;
        }
    }

    private String getVersionName() {
        try {
            return this.appContext.getPackageManager().getPackageInfo(this.packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            warn("Could not get versionName");
            return null;
        }
    }

    private String guessReleaseStage() {
        try {
        } catch (PackageManager.NameNotFoundException e) {
            warn("Could not get releaseStage");
        }
        if ((this.appContext.getPackageManager().getApplicationInfo(this.packageName, 0).flags & 2) != 0) {
            return "development";
        }
        return "production";
    }
}
