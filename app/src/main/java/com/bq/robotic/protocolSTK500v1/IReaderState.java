package com.bq.robotic.protocolSTK500v1;

/* JADX INFO: loaded from: classes.dex */
public interface IReaderState {
    void activate();

    void execute();

    EReaderState getEnum();

    boolean hasStateBeenActivated();

    boolean isReadingAllowed();
}
