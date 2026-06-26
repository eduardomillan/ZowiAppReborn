package com.bq.zowi.presenters;

/* JADX INFO: loaded from: classes.dex */
public interface BasePresenter<V, W> {
    void bindViewAndWireframe(V v, W w);

    V getView();

    W getWireframe();

    void onCreateView();

    void onDestroyView();

    void unBindViewAndWireframe();
}
