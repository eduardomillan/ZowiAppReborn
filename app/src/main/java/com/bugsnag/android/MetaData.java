package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class MetaData implements JsonStream.Streamable {
    private String[] filters;
    private final Map<String, Object> store;

    public MetaData() {
        this.store = new ConcurrentHashMap();
    }

    public MetaData(Map<String, Object> m) {
        this.store = new ConcurrentHashMap(m);
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public void toStream(JsonStream writer) throws IOException {
        objectToStream(this.store, writer);
    }

    public void addToTab(String tabName, String key, Object value) {
        Map<String, Object> tab;
        Map<String, Object> map = (Map) this.store.get(tabName);
        if (map == null) {
            tab = new ConcurrentHashMap<>();
            this.store.put(tabName, tab);
        } else {
            tab = map;
        }
        if (value != null) {
            tab.put(key, value);
        } else {
            tab.remove(key);
        }
    }

    public void clearTab(String tabName) {
        this.store.remove(tabName);
    }

    final void setFilters(String... filters) {
        this.filters = filters;
    }

    static MetaData merge(MetaData... metaDataList) {
        ArrayList<Map<String, Object>> stores = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MetaData metaData = metaDataList[i];
            if (metaData != null) {
                stores.add(metaData.store);
            }
        }
        return new MetaData(mergeMaps((Map[]) stores.toArray(new Map[0])));
    }

    private static Map<String, Object> mergeMaps(Map<String, Object>... maps) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        for (Map<String, Object> map : maps) {
            if (map != null) {
                Set<String> allKeys = new HashSet<>(result.keySet());
                allKeys.addAll(map.keySet());
                for (String key : allKeys) {
                    Object baseValue = result.get(key);
                    Object overridesValue = map.get(key);
                    if (overridesValue != null) {
                        if (baseValue != null && (baseValue instanceof Map) && (overridesValue instanceof Map)) {
                            result.put(key, mergeMaps((Map) baseValue, (Map) overridesValue));
                        } else {
                            result.put(key, overridesValue);
                        }
                    } else {
                        result.put(key, baseValue);
                    }
                }
            }
        }
        return result;
    }

    private void objectToStream(Object obj, JsonStream writer) throws IOException {
        boolean z;
        if (obj == null) {
            writer.nullValue();
            return;
        }
        if (obj instanceof String) {
            writer.value((String) obj);
            return;
        }
        if (obj instanceof Number) {
            writer.value((Number) obj);
            return;
        }
        if (obj instanceof Boolean) {
            writer.value((Boolean) obj);
            return;
        }
        if (obj instanceof Map) {
            writer.beginObject();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object keyObj = entry.getKey();
                if (keyObj instanceof String) {
                    String key = (String) keyObj;
                    writer.name(key);
                    if (this.filters == null || key == null) {
                        z = false;
                    } else {
                        String[] strArr = this.filters;
                        int length = strArr.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                z = false;
                                break;
                            } else {
                                if (key.contains(strArr[i])) {
                                    z = true;
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                    if (z) {
                        writer.value("[FILTERED]");
                    } else {
                        objectToStream(entry.getValue(), writer);
                    }
                }
            }
            writer.endObject();
            return;
        }
        if (obj instanceof Collection) {
            writer.beginArray();
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                objectToStream(it.next(), writer);
            }
            writer.endArray();
            return;
        }
        if (obj.getClass().isArray()) {
            writer.beginArray();
            int length2 = Array.getLength(obj);
            for (int i2 = 0; i2 < length2; i2++) {
                objectToStream(Array.get(obj, i2), writer);
            }
            writer.endArray();
            return;
        }
        writer.value("[OBJECT]");
    }
}
