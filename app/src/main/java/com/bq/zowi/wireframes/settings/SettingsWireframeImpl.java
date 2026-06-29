package com.bq.zowi.wireframes.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.utils.FileReader;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.views.interactive.settings.CalibrationViewActivity;
import com.bq.zowi.wireframes.interactive.InteractiveWireframeImpl;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SettingsWireframeImpl extends InteractiveWireframeImpl implements SettingsWireframe {
    private static final String APP_CONFIG_ASSET = "app_config.properties";

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
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(formatConfiguredUrl("settings_play_store_url_market", appPackageName))));
        } catch (ActivityNotFoundException e) {
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(formatConfiguredUrl("settings_play_store_url_https", appPackageName))));
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
        try {
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getConfiguredUrl("settings_hospital_url_https"))));
        } catch (ActivityNotFoundException e) {
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getConfiguredUrl("settings_hospital_url_http"))));
        }
    }

    private String formatConfiguredUrl(String key, Object... args) {
        String configuredUrl = getConfiguredUrl(key);
        if (configuredUrl == null) {
            return null;
        }
        return String.format(Locale.US, configuredUrl, args);
    }

    private String getConfiguredUrl(String key) {
        Properties properties = loadAppConfig();
        if (properties == null) {
            return null;
        }
        return properties.getProperty(key);
    }

    private Properties loadAppConfig() {
        String config = FileReader.readFielAsString(this.activity.getAssets(), APP_CONFIG_ASSET);
        if (config == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(config));
            return properties;
        } catch (IOException e) {
            return null;
        }
    }
}
