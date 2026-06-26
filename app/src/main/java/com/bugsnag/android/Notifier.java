package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class Notifier implements JsonStream.Streamable {
    private static Notifier instance = new Notifier();

    Notifier() {
    }

    public static Notifier getInstance() {
        return instance;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginObject();
        writer.name("name").value("Android Bugsnag Notifier");
        writer.name("version").value("3.2.6");
        writer.name("url").value("https://bugsnag.com");
        writer.endObject();
    }
}
