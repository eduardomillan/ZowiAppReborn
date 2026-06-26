package com.bq.zowi.wireframes.interactive;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.views.wizard.WizardViewActivity;
import com.bq.zowi.wireframes.BaseWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class InteractiveWireframeImpl extends BaseWireframeImpl implements InteractiveWireframe {
    public InteractiveWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.interactive.InteractiveWireframe
    public void presentWizard() {
        Intent i = new Intent(this.activity, (Class<?>) WizardViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }
}
