package com.bq.zowi.presenters.splash;

import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.presenters.BasePresenterImpl;
import com.bq.zowi.views.splash.SplashView;
import com.bq.zowi.wireframes.splash.SplashWireframe;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public class SplashPresenterImpl extends BasePresenterImpl<SplashView, SplashWireframe> implements SplashPresenter {
    private static final long SPLASH_TIME_MILLIS = 1500;
    private SessionController sessionController;
    private Scheduler uiThread;

    public SplashPresenterImpl(SessionController sessionController, Scheduler uiThread) {
        this.sessionController = sessionController;
        this.uiThread = uiThread;
    }

    @Override // com.bq.zowi.presenters.splash.SplashPresenter
    public void initialize() {
        final String activeZowiDeviceAddress = this.sessionController.loadActiveZowiDeviceAddress();
        this.subscriptions.add(Observable.timer(SPLASH_TIME_MILLIS, TimeUnit.MILLISECONDS).observeOn(this.uiThread).subscribe(new Action1<Long>() { // from class: com.bq.zowi.presenters.splash.SplashPresenterImpl.1
            @Override // rx.functions.Action1
            public void call(Long aLong) {
                SplashPresenterImpl.this.getWireframe().dismissSplash(activeZowiDeviceAddress != null);
            }
        }));
    }
}
