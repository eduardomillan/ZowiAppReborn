package com.bugsnag.android;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class JsonWriter implements Closeable {
    private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
    private static final String[] REPLACEMENT_CHARS = new String[128];
    private String deferredName;
    private final Writer out;
    private String separator;
    private boolean serializeNulls;
    private final List<JsonScope> stack = new ArrayList();

    enum JsonScope {
        EMPTY_ARRAY,
        NONEMPTY_ARRAY,
        EMPTY_OBJECT,
        DANGLING_NAME,
        NONEMPTY_OBJECT,
        EMPTY_DOCUMENT,
        NONEMPTY_DOCUMENT,
        CLOSED
    }

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        REPLACEMENT_CHARS[34] = "\\\"";
        REPLACEMENT_CHARS[92] = "\\\\";
        REPLACEMENT_CHARS[9] = "\\t";
        REPLACEMENT_CHARS[8] = "\\b";
        REPLACEMENT_CHARS[10] = "\\n";
        REPLACEMENT_CHARS[13] = "\\r";
        REPLACEMENT_CHARS[12] = "\\f";
        String[] strArr = (String[]) REPLACEMENT_CHARS.clone();
        HTML_SAFE_REPLACEMENT_CHARS = strArr;
        strArr[60] = "\\u003c";
        HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
        HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
        HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
        HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
    }

    public JsonWriter(Writer out) {
        this.stack.add(JsonScope.EMPTY_DOCUMENT);
        this.separator = ":";
        this.serializeNulls = true;
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
    }

    public final JsonWriter beginArray() throws IOException {
        writeDeferredName();
        return open(JsonScope.EMPTY_ARRAY, "[");
    }

    public final JsonWriter endArray() throws IOException {
        return close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
    }

    public final JsonWriter beginObject() throws IOException {
        writeDeferredName();
        return open(JsonScope.EMPTY_OBJECT, "{");
    }

    public final JsonWriter endObject() throws IOException {
        return close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
    }

    private JsonWriter open(JsonScope empty, String openBracket) throws IOException {
        beforeValue(true);
        this.stack.add(empty);
        this.out.write(openBracket);
        return this;
    }

    private JsonWriter close(JsonScope empty, JsonScope nonempty, String closeBracket) throws IOException {
        JsonScope context = peek();
        if (context != nonempty && context != empty) {
            throw new IllegalStateException("Nesting problem: " + this.stack);
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        this.stack.remove(this.stack.size() - 1);
        if (context == nonempty) {
        }
        this.out.write(closeBracket);
        return this;
    }

    private JsonScope peek() {
        int size = this.stack.size();
        if (size == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        return this.stack.get(size - 1);
    }

    private void replaceTop(JsonScope topOfStack) {
        this.stack.set(this.stack.size() - 1, topOfStack);
    }

    public JsonWriter name(String name) throws IOException {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.deferredName = name;
        return this;
    }

    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            JsonScope jsonScopePeek = peek();
            if (jsonScopePeek == JsonScope.NONEMPTY_OBJECT) {
                this.out.write(44);
            } else if (jsonScopePeek != JsonScope.EMPTY_OBJECT) {
                throw new IllegalStateException("Nesting problem: " + this.stack);
            }
            replaceTop(JsonScope.DANGLING_NAME);
            string(this.deferredName);
            this.deferredName = null;
        }
    }

    public final JsonWriter value(String value) throws IOException {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue(false);
        string(value);
        return this;
    }

    public final JsonWriter nullValue() throws IOException {
        if (this.deferredName != null) {
            if (this.serializeNulls) {
                writeDeferredName();
                beforeValue(false);
                this.out.write("null");
            } else {
                this.deferredName = null;
            }
        } else {
            beforeValue(false);
            this.out.write("null");
        }
        return this;
    }

    public final JsonWriter value(boolean value) throws IOException {
        writeDeferredName();
        beforeValue(false);
        this.out.write(value ? "true" : "false");
        return this;
    }

    public final JsonWriter value(double value) throws IOException {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        writeDeferredName();
        beforeValue(false);
        this.out.append((CharSequence) Double.toString(value));
        return this;
    }

    public final JsonWriter value(long value) throws IOException {
        writeDeferredName();
        beforeValue(false);
        this.out.write(Long.toString(value));
        return this;
    }

    public final JsonWriter value(Number value) throws IOException {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        String string = value.toString();
        if (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN")) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        beforeValue(false);
        this.out.append((CharSequence) string);
        return this;
    }

    public final void flush() throws IOException {
        if (this.stack.isEmpty()) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.out.close();
        int size = this.stack.size();
        if (size > 1 || (size == 1 && this.stack.get(size - 1) != JsonScope.NONEMPTY_DOCUMENT)) {
            throw new IOException("Incomplete document");
        }
        this.stack.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void string(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            java.lang.String[] r5 = com.bugsnag.android.JsonWriter.REPLACEMENT_CHARS
            java.io.Writer r6 = r8.out
            java.lang.String r7 = "\""
            r6.write(r7)
            r2 = 0
            int r3 = r9.length()
            r1 = 0
        Lf:
            if (r1 >= r3) goto L3e
            char r0 = r9.charAt(r1)
            r6 = 128(0x80, float:1.8E-43)
            if (r0 >= r6) goto L20
            r4 = r5[r0]
            if (r4 != 0) goto L26
        L1d:
            int r1 = r1 + 1
            goto Lf
        L20:
            r6 = 8232(0x2028, float:1.1535E-41)
            if (r0 != r6) goto L37
            java.lang.String r4 = "\\u2028"
        L26:
            if (r2 >= r1) goto L2f
            java.io.Writer r6 = r8.out
            int r7 = r1 - r2
            r6.write(r9, r2, r7)
        L2f:
            java.io.Writer r6 = r8.out
            r6.write(r4)
            int r2 = r1 + 1
            goto L1d
        L37:
            r6 = 8233(0x2029, float:1.1537E-41)
            if (r0 != r6) goto L1d
            java.lang.String r4 = "\\u2029"
            goto L26
        L3e:
            if (r2 >= r3) goto L47
            java.io.Writer r6 = r8.out
            int r7 = r3 - r2
            r6.write(r9, r2, r7)
        L47:
            java.io.Writer r6 = r8.out
            java.lang.String r7 = "\""
            r6.write(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bugsnag.android.JsonWriter.string(java.lang.String):void");
    }

    private void beforeValue(boolean root) throws IOException {
        switch (peek()) {
            case NONEMPTY_DOCUMENT:
                throw new IllegalStateException("JSON must have only one top-level value.");
            case EMPTY_DOCUMENT:
                if (!root) {
                    throw new IllegalStateException("JSON must start with an array or an object.");
                }
                replaceTop(JsonScope.NONEMPTY_DOCUMENT);
                return;
            case EMPTY_ARRAY:
                replaceTop(JsonScope.NONEMPTY_ARRAY);
                return;
            case NONEMPTY_ARRAY:
                this.out.append(',');
                return;
            case DANGLING_NAME:
                this.out.append((CharSequence) this.separator);
                replaceTop(JsonScope.NONEMPTY_OBJECT);
                return;
            default:
                throw new IllegalStateException("Nesting problem: " + this.stack);
        }
    }
}
