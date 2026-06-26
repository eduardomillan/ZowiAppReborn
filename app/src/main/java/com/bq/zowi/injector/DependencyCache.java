package com.bq.zowi.injector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyCache {
    private static final String DEFAULT_TAG = null;
    private final Map<String, Object> cache = new ConcurrentHashMap();

    public interface Provider<T> {
        @NotNull
        T get();
    }

    public boolean contains(@NotNull Class<?> tClass) {
        return contains(tClass, DEFAULT_TAG);
    }

    public boolean contains(@NotNull Class<?> tClass, @Nullable String tag) {
        return this.cache.containsKey(getKey(tClass, tag));
    }

    @Nullable
    public <T> T get(@NotNull Class<T> cls) {
        return (T) get(cls, DEFAULT_TAG);
    }

    @Nullable
    public <T> T get(@NotNull Class<T> tClass, @Nullable String tag) {
        String key = getKey(tClass, tag);
        Object value = this.cache.get(key);
        if (value != null) {
            return tClass.cast(value);
        }
        return null;
    }

    public <T> void put(@NotNull Class<T> tClass, @NotNull T dependency) {
        put(tClass, DEFAULT_TAG, dependency);
    }

    public <T> void put(@NotNull Class<T> tClass, @Nullable String tag, @NotNull T dependency) {
        this.cache.put(getKey(tClass, tag), dependency);
    }

    @NotNull
    public <T> T get(@NotNull Class<T> cls, @NotNull Provider<T> provider) {
        return (T) get(cls, DEFAULT_TAG, provider);
    }

    @NotNull
    public <T> T get(@NotNull Class<T> tClass, @Nullable String tag, @NotNull Provider<T> provider) {
        String key = getKey(tClass, tag);
        Object value = this.cache.get(key);
        if (value != null) {
            return tClass.cast(value);
        }
        T dependency = provider.get();
        this.cache.put(key, dependency);
        return dependency;
    }

    private String getKey(@NotNull Class<?> tClass, @Nullable String tag) {
        StringBuilder key = new StringBuilder(tClass.getSimpleName());
        if (tag == null) {
            key.append(DEFAULT_TAG);
        } else {
            key.append("_").append(tag);
        }
        return key.toString();
    }
}
