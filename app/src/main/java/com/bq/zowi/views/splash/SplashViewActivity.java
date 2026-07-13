package com.bq.zowi.views.splash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.bq.zowi.R;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.splash.SplashPresenter;
import com.bq.zowi.views.BaseActivity;
import com.bq.zowi.wireframes.splash.SplashWireframe;

/* JADX INFO: loaded from: classes.dex */
public class SplashViewActivity extends BaseActivity<SplashPresenter> implements SplashView {
    @Override // com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_splash_view", R.layout.activity_splash_view);

        Button continueButton = (Button) findViewById(R.id.splash_continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onContinueClicked();
            }
        });

        Button exitButton = (Button) findViewById(R.id.splash_exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
