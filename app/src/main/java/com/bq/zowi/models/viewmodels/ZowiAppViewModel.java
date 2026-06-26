package com.bq.zowi.models.viewmodels;

/* JADX INFO: loaded from: classes.dex */
public class ZowiAppViewModel {
    public final int backgroundResource;
    public final int labelResource;
    public final ClickListener listener;

    public interface ClickListener {
        void onClick();
    }

    public ZowiAppViewModel(int backgroundResource, int labelResource, ClickListener listener) {
        this.backgroundResource = backgroundResource;
        this.labelResource = labelResource;
        this.listener = listener;
    }
}
