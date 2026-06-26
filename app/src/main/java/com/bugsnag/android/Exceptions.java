package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class Exceptions implements JsonStream.Streamable {
    private Configuration config;
    private Throwable exception;
    private StackTraceElement[] frames;
    private String message;
    private String name;

    Exceptions(Configuration config, Throwable exception) {
        this.config = config;
        this.exception = exception;
    }

    Exceptions(Configuration config, String name, String message, StackTraceElement[] frames) {
        this.config = config;
        this.name = name;
        this.message = message;
        this.frames = frames;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginArray();
        if (this.exception != null) {
            for (Throwable currentEx = this.exception; currentEx != null; currentEx = currentEx.getCause()) {
                exceptionToStream(writer, currentEx.getClass().getName(), currentEx.getLocalizedMessage(), currentEx.getStackTrace());
            }
        } else {
            exceptionToStream(writer, this.name, this.message, this.frames);
        }
        writer.endArray();
    }

    private void exceptionToStream(JsonStream writer, String name, String message, StackTraceElement[] frames) throws IOException {
        Stacktrace stacktrace = new Stacktrace(this.config, frames);
        writer.beginObject();
        writer.name("errorClass").value(name);
        writer.name("message").value(message);
        stacktrace.toStream(writer.name("stacktrace"));
        writer.endObject();
    }
}
