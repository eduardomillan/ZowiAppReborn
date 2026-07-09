package com.bq.zowi.controllers;

import android.content.SharedPreferences;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class SessionControllerImpl implements SessionController {
    private final String ACTIVE_ZOWI_DEVICE_ADDRESS = "activeZowiDeviceAddress";
    private final String ACTIVE_ZOWI_NAME = "activeZowiName";
    private final String WIZARD_DISMISSED = "wizardDismissed";
    private String defaultZowiName;
    private SharedPreferences sharedPreferences;

    public SessionControllerImpl(String defaultZowiName, SharedPreferences sharedPreferences) {
        this.defaultZowiName = sanitizeZowiName(defaultZowiName, "Zowi");
        this.sharedPreferences = sharedPreferences;
    }

    @Override // com.bq.zowi.controllers.SessionController
    public void saveActiveZowiDeviceAddress(String address) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString("activeZowiDeviceAddress", address);
        editor.commit();
    }

    @Override // com.bq.zowi.controllers.SessionController
    @Nullable
    public String loadActiveZowiDeviceAddress() {
        return this.sharedPreferences.getString("activeZowiDeviceAddress", null);
    }

    @Override // com.bq.zowi.controllers.SessionController
    public void saveActiveZowiName(String name) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString("activeZowiName", sanitizeZowiName(name, this.defaultZowiName));
        editor.commit();
    }

    @Override // com.bq.zowi.controllers.SessionController
    public void saveWizardDismissed(boolean dismissed) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean("wizardDismissed", dismissed);
        editor.commit();
    }

    @Override // com.bq.zowi.controllers.SessionController
    public String loadActiveZowiName() {
        return sanitizeZowiName(this.sharedPreferences.getString("activeZowiName", this.defaultZowiName), this.defaultZowiName);
    }

    @Override // com.bq.zowi.controllers.SessionController
    public String loadDefaultZowiName() {
        return sanitizeZowiName(this.defaultZowiName, "Zowi");
    }

    @Override // com.bq.zowi.controllers.SessionController
    public void resetActiveZowi() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove("activeZowiName");
        editor.remove("activeZowiDeviceAddress");
        editor.commit();
    }

    @Override // com.bq.zowi.controllers.SessionController
    public boolean hasDismissedWizard() {
        return this.sharedPreferences.getBoolean("wizardDismissed", false);
    }

    private String sanitizeZowiName(String candidateName, String fallbackName) {
        String resolvedFallback = normalizeZowiName(fallbackName);
        if (resolvedFallback == null) {
            resolvedFallback = "Zowi";
        }
        String normalizedCandidate = normalizeZowiName(candidateName);
        return normalizedCandidate != null ? normalizedCandidate : resolvedFallback;
    }

    @Nullable
    private String normalizeZowiName(String name) {
        if (name == null) {
            return null;
        }
        String normalizedName = name.trim();
        if (normalizedName.length() == 0 || "false".equalsIgnoreCase(normalizedName) || "true".equalsIgnoreCase(normalizedName)) {
            return null;
        }
        return normalizedName;
    }
}
