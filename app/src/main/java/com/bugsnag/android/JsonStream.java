package com.bugsnag.android;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
final class JsonStream extends JsonWriter {
    private Writer out;

    interface Streamable {
        void toStream(JsonStream jsonStream) throws IOException;
    }

    @Override // com.bugsnag.android.JsonWriter
    public final /* bridge */ /* synthetic */ JsonWriter name(String str) throws IOException {
        super.name(str);
        return this;
    }

    JsonStream(Writer out) {
        super(out);
        this.out = out;
    }

    @Override // com.bugsnag.android.JsonWriter
    public final JsonStream name(String name) throws IOException {
        super.name(name);
        return this;
    }

    final void value(Boolean value) throws IOException {
        if (value == null) {
            nullValue();
        } else {
            super.value(value.booleanValue());
        }
    }

    final void value(File file) throws Throwable {
        super.flush();
        FileReader input = null;
        try {
            FileReader input2 = new FileReader(file);
            try {
                AppData.copy(input2, this.out);
                AppData.closeQuietly(input2);
                this.out.flush();
            } catch (Throwable th) {
                th = th;
                input = input2;
                AppData.closeQuietly(input);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
