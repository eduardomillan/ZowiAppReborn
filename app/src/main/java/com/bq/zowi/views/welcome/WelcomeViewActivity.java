package com.bq.zowi.views.welcome;

import android.os.Bundle;
import android.view.View;
import com.bq.zowi.R;
import com.bq.zowi.injector.AndroidDependencyInjector;
import com.bq.zowi.presenters.welcome.WelcomePresenter;
import com.bq.zowi.views.BaseActivity;
import com.bq.zowi.wireframes.welcome.WelcomeWireframe;

/* JADX INFO: loaded from: classes.dex */
public class WelcomeViewActivity extends BaseActivity<WelcomePresenter> implements WelcomeView {
    private View letterToParentsDialog;

    @Override // com.bq.zowi.views.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResolvedContentView("activity_welcome_view", R.layout.activity_welcome_view);
        this.letterToParentsDialog = findResolvedView("welcome_letter_to_parents_dialog", R.id.welcome_letter_to_parents_dialog);
        View startWizardButton = findResolvedView("welcome_start_wizard_button", R.id.welcome_start_wizard_button);
        if (startWizardButton != null) {
            startWizardButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.welcome.WelcomeViewActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((WelcomePresenter) WelcomeViewActivity.this.getPresenter()).startWizard();
                }
            });
        }
        View letterToParentsButton = findResolvedView("welcome_letter_to_parents_button", R.id.welcome_letter_to_parents_button);
        if (letterToParentsButton != null && this.letterToParentsDialog != null) {
            letterToParentsButton.setOnClickListener(new View.OnClickListener() { // from class: com.bq.zowi.views.welcome.WelcomeViewActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    WelcomeViewActivity.this.letterToParentsDialog.setVisibility(0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bq.zowi.views.BaseActivity
    public WelcomePresenter resolvePresenter() {
        WelcomePresenter presenter = AndroidDependencyInjector.getInstance().provideWelcomePresenter();
        WelcomeWireframe wireframe = AndroidDependencyInjector.getInstance().provideWelcomeWireframe(this);
        presenter.bindViewAndWireframe(this, wireframe);
        return presenter;
    }

    @Override // com.bq.zowi.views.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }
}
