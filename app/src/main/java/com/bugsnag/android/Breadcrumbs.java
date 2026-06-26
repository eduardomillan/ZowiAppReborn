package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class Breadcrumbs implements JsonStream.Streamable {
    private List<Breadcrumb> store = new LinkedList();
    private int maxSize = 20;

    static class Breadcrumb {
        private String message;
        private String timestamp = DateUtils.toISO8601(new Date());

        Breadcrumb(String message) {
            this.message = message.substring(0, Math.min(message.length(), 140));
        }
    }

    Breadcrumbs() {
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginArray();
        for (Breadcrumb breadcrumb : this.store) {
            writer.beginArray();
            writer.value(breadcrumb.timestamp);
            writer.value(breadcrumb.message);
            writer.endArray();
        }
        writer.endArray();
    }

    final void add(String message) {
        if (this.store.size() >= this.maxSize) {
            this.store.remove(0);
        }
        this.store.add(new Breadcrumb(message));
    }

    final void clear() {
        this.store.clear();
    }

    final void setSize(int size) {
        if (size > this.store.size()) {
            this.maxSize = size;
        } else {
            this.store.subList(0, this.store.size() - size).clear();
        }
    }
}
