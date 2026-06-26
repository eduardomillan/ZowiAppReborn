package com.bq.zowi.wireframes.splash;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.views.welcome.WelcomeViewActivity;
import com.bq.zowi.wireframes.BaseWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class SplashWireframeImpl extends BaseWireframeImpl implements SplashWireframe {
    public SplashWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    private void presentWelcome() {
        Intent i = new Intent(this.activity, (Class<?>) WelcomeViewActivity.class);
        this.activity.startActivity(i);
        this.activity.finish();
    }

    private void presentHome() {
        Intent i = new Intent(this.activity, (Class<?>) HomeViewActivity.class);
        this.activity.startActivity(i);
        this.activity.finish();
    }

    @Override // com.bq.zowi.wireframes.splash.SplashWireframe
    public void dismissSplash(boolean isActiveSession) {
        if (isActiveSession) {
            presentHome();
        } else {
            presentWelcome();
        }
    }
}
