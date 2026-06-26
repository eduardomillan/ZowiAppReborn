package com.bq.robotic.protocolSTK500v1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public interface IReader {
    public static final int RESULT_END_OF_STREAM = -1;
    public static final int RESULT_NOT_DONE = -2;
    public static final int TIMEOUT_BYTE_RECEIVED = -3;

    void forget();

    int getResult();

    EReaderState getState();

    int read(TimeoutValues timeoutValues) throws TimeoutException, IOException;

    boolean start();

    boolean stop();

    boolean wasCurrentStateActivated();
}
