package com.google.ads.mediation.customevent;

import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.google.ads.mediation.MediationServerParameters;

/* JADX INFO: loaded from: classes.dex */
public final class CustomEventServerParameters extends MediationServerParameters {

    @MediationServerParameters.Parameter(name = "class_name", required = Droid2InoConstants.D)
    public String className;

    @MediationServerParameters.Parameter(name = "label", required = Droid2InoConstants.D)
    public String label;

    @MediationServerParameters.Parameter(name = "parameter", required = false)
    public String parameter = null;
}
