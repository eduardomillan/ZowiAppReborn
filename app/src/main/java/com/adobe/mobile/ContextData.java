package com.adobe.mobile;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
final class ContextData {
    protected Object value = null;
    protected HashMap<String, Object> contextData = new HashMap<>();

    ContextData() {
    }

    public synchronized String toString() {
        String addString;
        addString = this.value != null ? this.value.toString() : "";
        return super.toString() + addString;
    }

    protected boolean containsKey(String key) {
        return this.contextData.containsKey(key);
    }

    protected void put(String key, ContextData value) {
        this.contextData.put(key, value);
    }

    protected ContextData get(String key) {
        return (ContextData) this.contextData.get(key);
    }
}
