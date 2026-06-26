package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public enum Severity implements JsonStream.Streamable {
    ERROR("error"),
    WARNING("warning"),
    INFO("info");

    private final String name;

    Severity(String name) {
        this.name = name;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.value(this.name);
    }
}
