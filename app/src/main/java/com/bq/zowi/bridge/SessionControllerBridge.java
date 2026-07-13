package com.bq.zowi.bridge;

import com.bq.zowi.controllers.SessionController;
import org.jetbrains.annotations.Nullable;

public class SessionControllerBridge implements SessionController {
    private final com.bq.zowi.api.SessionController core;

    public SessionControllerBridge(com.bq.zowi.api.SessionController core) {
        this.core = core;
    }

    @Nullable
    @Override
    public String loadActiveZowiDeviceAddress() {
        return core.loadActiveZowiDeviceAddress();
    }

    @Override
    public String loadActiveZowiName() {
        return core.loadActiveZowiName();
    }

    @Override
    public String loadDefaultZowiName() {
        return core.loadDefaultZowiName();
    }

    @Override
    public boolean hasDismissedWizard() {
        return core.hasDismissedWizard();
    }

    @Override
    public void resetActiveZowi() {
        core.resetActiveZowi();
    }

    @Override
    public void saveActiveZowiDeviceAddress(String address) {
        core.saveActiveZowiDeviceAddress(address);
    }

    @Override
    public void saveActiveZowiName(String name) {
        core.saveActiveZowiName(name);
    }

    @Override
    public void saveWizardDismissed(boolean dismissed) {
        core.saveWizardDismissed(dismissed);
    }
}
