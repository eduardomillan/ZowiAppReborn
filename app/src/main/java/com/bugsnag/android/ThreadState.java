package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class ThreadState implements JsonStream.Streamable {
    private Configuration config;

    ThreadState(Configuration config) {
        this.config = config;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        long currentId = Thread.currentThread().getId();
        Map<Thread, StackTraceElement[]> liveThreads = Thread.getAllStackTraces();
        Object[] keys = liveThreads.keySet().toArray();
        Arrays.sort(keys, new Comparator<Object>(this) { // from class: com.bugsnag.android.ThreadState.1
            @Override // java.util.Comparator
            public final int compare(Object a, Object b) {
                return Long.valueOf(((Thread) a).getId()).compareTo(Long.valueOf(((Thread) b).getId()));
            }
        });
        writer.beginArray();
        for (Object obj : keys) {
            Thread thread = (Thread) obj;
            if (thread.getId() != currentId) {
                StackTraceElement[] stacktrace = liveThreads.get(thread);
                writer.beginObject();
                writer.name("id").value(thread.getId());
                writer.name("name").value(thread.getName());
                new Stacktrace(this.config, stacktrace).toStream(writer.name("stacktrace"));
                writer.endObject();
            }
        }
        writer.endArray();
    }
}
