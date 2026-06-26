package com.bugsnag.android;

import java.lang.Thread;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
final class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private WeakHashMap<Client, Boolean> clientMap = new WeakHashMap<>();
    private Thread.UncaughtExceptionHandler originalHandler;

    static void enable(Client client) {
        ExceptionHandler bugsnagHandler;
        Thread.UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (currentHandler instanceof ExceptionHandler) {
            bugsnagHandler = (ExceptionHandler) currentHandler;
        } else {
            bugsnagHandler = new ExceptionHandler(currentHandler);
            Thread.setDefaultUncaughtExceptionHandler(bugsnagHandler);
        }
        bugsnagHandler.clientMap.put(client, true);
    }

    static void disable(Client client) {
        Thread.UncaughtExceptionHandler currentHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (currentHandler instanceof ExceptionHandler) {
            ExceptionHandler bugsnagHandler = (ExceptionHandler) currentHandler;
            bugsnagHandler.clientMap.remove(client);
            if (bugsnagHandler.clientMap.size() == 0) {
                Thread.setDefaultUncaughtExceptionHandler(bugsnagHandler.originalHandler);
            }
        }
    }

    private ExceptionHandler(Thread.UncaughtExceptionHandler originalHandler) {
        this.originalHandler = originalHandler;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread t, Throwable e) {
        Iterator<Client> it = this.clientMap.keySet().iterator();
        while (it.hasNext()) {
            it.next().notify(e, Severity.ERROR);
        }
        if (this.originalHandler != null) {
            this.originalHandler.uncaughtException(t, e);
        } else {
            System.err.printf("Exception in thread \"%s\" ", t.getName());
            e.printStackTrace(System.err);
        }
    }
}
