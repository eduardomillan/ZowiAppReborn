package com.bq.zowi.wireframes.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.views.interactive.settings.CalibrationViewActivity;
import com.bq.zowi.wireframes.interactive.InteractiveWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class SettingsWireframeImpl extends InteractiveWireframeImpl implements SettingsWireframe {
    public SettingsWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.settings.SettingsWireframe
    public void presentHome() {
        Intent i = new Intent(this.activity, (Class<?>) HomeViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }

    @Override // com.bq.zowi.wireframes.settings.SettingsWireframe
    public void openGooglePlayToCheckUpdates() {
        String appPackageName = this.activity.getPackageName();
        try {
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException e) {
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override // com.bq.zowi.wireframes.settings.SettingsWireframe
    public void presentCalibrationView() {
        Intent i = new Intent(this.activity, (Class<?>) CalibrationViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }

    @Override // com.bq.zowi.wireframes.settings.SettingsWireframe
    public void openHospitalWeb() {
        this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://zowi.bq.com/hospital")));
    }
}
