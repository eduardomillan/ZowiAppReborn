package com.comscore.applications;

import com.comscore.analytics.Core;
import com.comscore.measurement.Label;
import com.comscore.utils.Constants;
import com.comscore.utils.InstallReferrerReceiver;
import com.comscore.utils.RootDetector;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class AppStartMeasurement extends ApplicationMeasurement {
    protected AppStartMeasurement(Core core, EventType eventType, String str, boolean z) {
        HashMap<String, String> mapRetrieveReferrerLabels;
        super(core, eventType, str, z, true, true);
        setLabel(new Label("ns_ap_gs", String.valueOf(core.getFirstInstallId()), false));
        setLabel(new Label("ns_ap_install", String.valueOf(core.getInstallId()), false));
        setLabel(new Label("ns_ap_runs", String.valueOf(core.getRunsCount()), false));
        if (z) {
            setLabel(new Label("ns_ap_csf", "1", false));
        }
        setLabel(new Label("ns_ap_jb", RootDetector.isDeviceRooted() ? "1" : "0", false));
        setLabel(new Label("ns_ap_lastrun", String.valueOf(core.getPreviousGenesis()), false));
        String previousVersion = core.getPreviousVersion();
        if (previousVersion != null && previousVersion.length() > 0) {
            setLabel(new Label("ns_ap_updated", previousVersion, false));
        }
        String str2 = core.getStorage().get(Constants.EXCEPTION_OCURRENCES_KEY);
        if (str2 != null && str2.length() > 0 && !str2.equals("0")) {
            setLabel(new Label("ns_ap_er", str2, false));
            core.getStorage().remove(Constants.EXCEPTION_OCURRENCES_KEY);
        }
        if (!z || (mapRetrieveReferrerLabels = InstallReferrerReceiver.retrieveReferrerLabels(core.getAppContext())) == null) {
            return;
        }
        for (String str3 : mapRetrieveReferrerLabels.keySet()) {
            setLabel(str3, mapRetrieveReferrerLabels.get(str3));
        }
    }
}
