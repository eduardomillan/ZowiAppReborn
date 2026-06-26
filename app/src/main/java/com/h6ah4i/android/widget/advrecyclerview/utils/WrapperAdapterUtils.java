package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class WrapperAdapterUtils {
    private WrapperAdapterUtils() {
    }

    public static <T> T findWrappedAdapter(RecyclerView.Adapter adapter, Class<T> cls) {
        if (cls.isInstance(adapter)) {
            return cls.cast(adapter);
        }
        if (adapter instanceof BaseWrapperAdapter) {
            return (T) findWrappedAdapter(((BaseWrapperAdapter) adapter).getWrappedAdapter(), cls);
        }
        return null;
    }

    public static RecyclerView.Adapter releaseAll(RecyclerView.Adapter adapter) {
        return releaseCyclically(adapter);
    }

    private static RecyclerView.Adapter releaseCyclically(RecyclerView.Adapter adapter) {
        if (adapter instanceof BaseWrapperAdapter) {
            BaseWrapperAdapter wrapperAdapter = (BaseWrapperAdapter) adapter;
            RecyclerView.Adapter wrappedAdapter = wrapperAdapter.getWrappedAdapter();
            wrapperAdapter.release();
            return releaseCyclically(wrappedAdapter);
        }
        return adapter;
    }
}
