package com.bq.zowi.wireframes.settings;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.wireframes.interactive.InteractiveWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class CalibrationWireframeImpl extends InteractiveWireframeImpl implements CalibrationWireframe {
    public CalibrationWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.settings.CalibrationWireframe
    public void presentHome() {
        Intent i = new Intent(this.activity, (Class<?>) HomeViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }
}
