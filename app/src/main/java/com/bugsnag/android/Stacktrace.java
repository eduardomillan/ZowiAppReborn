package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class Stacktrace implements JsonStream.Streamable {
    private Configuration config;
    private StackTraceElement[] stacktrace;

    Stacktrace(Configuration config, StackTraceElement[] stacktrace) {
        this.config = config;
        this.stacktrace = stacktrace;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginArray();
        for (StackTraceElement el : this.stacktrace) {
            try {
                writer.beginObject();
                writer.name("method").value(el.getClassName() + "." + el.getMethodName());
                writer.name("file").value(el.getFileName() == null ? "Unknown" : el.getFileName());
                writer.name("lineNumber").value(el.getLineNumber());
                if (this.config.inProject(el.getClassName())) {
                    writer.name("inProject").value(true);
                }
                writer.endObject();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        writer.endArray();
    }
}
