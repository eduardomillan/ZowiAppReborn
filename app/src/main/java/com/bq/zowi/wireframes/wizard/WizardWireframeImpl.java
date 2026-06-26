package com.bq.zowi.wireframes.wizard;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.wireframes.BaseWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class WizardWireframeImpl extends BaseWireframeImpl implements WizardWireframe {
    public WizardWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    private void presentHome() {
        Intent i = new Intent(this.activity, (Class<?>) HomeViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }

    @Override // com.bq.zowi.wireframes.wizard.WizardWireframe
    public void presentWizardCompleteView() {
        presentHome();
    }
}
