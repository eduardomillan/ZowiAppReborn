package com.bq.zowi.views.welcome;

import android.os.Bundle;
import android.view.View;
import com.bq.zowi.R;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.analytics.ZowiScreen;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.welcome.WelcomePresenter;
import com.bq.zowi.views.BaseActivity;
import com.bq.zowi.wireframes.welcome.WelcomeWireframe;

/* JADX INFO: loaded from: classes.dex */
public class WelcomeViewActivity extends BaseActivity<WelcomePresenter> implements WelcomeView {
    private View letterToParentsDialog;

    @Override // com.bq.zowi.views.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_view);
        this.letterToParentsDialog = findViewById(R.id.welcome_letter_to_parents_dialog);
        View startWizardButton = findViewById(R.id.welcome_start_wizard_button);
        startWizardButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.welcome.WelcomeViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((WelcomePresenter) WelcomeViewActivity.this.getPresenter()).startWizard();
            }
        });
        View letterToParentsButton = findViewById(R.id.welcome_letter_to_parents_button);
        letterToParentsButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.welcome.WelcomeViewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WelcomeViewActivity.this.letterToParentsDialog.setVisibility(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public WelcomePresenter resolvePresenter() {
        WelcomePresenter presenter = AndroidDependencyInjector.getInstance().provideWelcomePresenter();
        WelcomeWireframe wireframe = AndroidDependencyInjector.getInstance().provideWelcomeWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.BaseActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getAnalyticsController().send(new ZowiScreen(this, AnalyticsUtils.SCREEN_HELLO));
    }
}
