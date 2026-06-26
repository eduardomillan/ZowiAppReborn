package com.bq.zowi.views.splash;

import android.os.Bundle;
import com.bq.zowi.R;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.splash.SplashPresenter;
import com.bq.zowi.views.BaseActivity;
import com.bq.zowi.wireframes.splash.SplashWireframe;

/* JADX INFO: loaded from: classes.dex */
public class SplashViewActivity extends BaseActivity<SplashPresenter> implements SplashView {
    @Override // com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);
    }

    @Override // com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getPresenter().initialize();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public SplashPresenter resolvePresenter() {
        SplashPresenter presenter = AndroidDependencyInjector.getInstance().provideSplashPresenter();
        SplashWireframe wireframe = AndroidDependencyInjector.getInstance().provideSplashWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }
}
