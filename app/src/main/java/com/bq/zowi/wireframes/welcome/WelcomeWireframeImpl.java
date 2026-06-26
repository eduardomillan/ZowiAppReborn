package com.bq.zowi.wireframes.welcome;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.bq.zowi.views.wizard.WizardViewActivity;
import com.bq.zowi.wireframes.BaseWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class WelcomeWireframeImpl extends BaseWireframeImpl implements WelcomeWireframe {
    public WelcomeWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.welcome.WelcomeWireframe
    public void showWizard() {
        Intent i = new Intent(this.activity, (Class<?>) WizardViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }
}
