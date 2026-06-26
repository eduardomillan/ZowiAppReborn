package com.bq.zowi;

import android.app.Application;
import com.bq.zowi.injector.AndroidDependencyInjector;

/* JADX INFO: loaded from: classes.dex */
public class ZowiApplication extends Application {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        AndroidDependencyInjector.getInstance().setApplication(this);
        AndroidDependencyInjector.getInstance().init();
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        AndroidDependencyInjector.getInstance().shutdown();
    }
}
