package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.tagmanager.zzcb;
import com.google.android.gms.tagmanager.zzt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Container {
    private final Context mContext;
    private final String zzaVQ;
    private final DataLayer zzaVR;
    private zzcp zzaVS;
    private volatile long zzaVV;
    private Map<String, FunctionCallMacroCallback> zzaVT = new HashMap();
    private Map<String, FunctionCallTagCallback> zzaVU = new HashMap();
    private volatile String zzaVW = "";

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    private class zza implements zzt.zza {
        private zza() {
        }

        @Override // com.google.android.gms.tagmanager.zzt.zza
        public Object zzc(String str, Map<String, Object> map) {
            FunctionCallMacroCallback functionCallMacroCallbackZzeA = Container.this.zzeA(str);
            if (functionCallMacroCallbackZzeA == null) {
                return null;
            }
            return functionCallMacroCallbackZzeA.getValue(str, map);
        }
    }

    private class zzb implements zzt.zza {
        private zzb() {
        }

        @Override // com.google.android.gms.tagmanager.zzt.zza
        public Object zzc(String str, Map<String, Object> map) {
            FunctionCallTagCallback functionCallTagCallbackZzeB = Container.this.zzeB(str);
            if (functionCallTagCallbackZzeB != null) {
                functionCallTagCallbackZzeB.execute(str, map);
            }
            return zzdf.zzDW();
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, zzaf.zzj resource) {
        this.mContext = context;
        this.zzaVR = dataLayer;
        this.zzaVQ = containerId;
        this.zzaVV = lastRefreshTime;
        zza(resource.zziR);
        if (resource.zziQ != null) {
            zza(resource.zziQ);
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, zzrb.zzc resource) {
        this.mContext = context;
        this.zzaVR = dataLayer;
        this.zzaVQ = containerId;
        this.zzaVV = lastRefreshTime;
        zza(resource);
    }

    private synchronized zzcp zzCu() {
        return this.zzaVS;
    }

    private void zza(zzaf.zzf zzfVar) {
        if (zzfVar == null) {
            throw new NullPointerException();
        }
        try {
            zza(zzrb.zzb(zzfVar));
        } catch (zzrb.zzg e) {
            zzbg.e("Not loading resource: " + zzfVar + " because it is invalid: " + e.toString());
        }
    }

    private void zza(zzrb.zzc zzcVar) {
        this.zzaVW = zzcVar.getVersion();
        zza(new zzcp(this.mContext, zzcVar, this.zzaVR, new zza(), new zzb(), zzeD(this.zzaVW)));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.zzaVR.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.zzaVQ));
        }
    }

    private synchronized void zza(zzcp zzcpVar) {
        this.zzaVS = zzcpVar;
    }

    private void zza(zzaf.zzi[] zziVarArr) {
        ArrayList arrayList = new ArrayList();
        for (zzaf.zzi zziVar : zziVarArr) {
            arrayList.add(zziVar);
        }
        zzCu().zzA(arrayList);
    }

    public boolean getBoolean(String key) {
        zzcp zzcpVarZzCu = zzCu();
        if (zzcpVarZzCu == null) {
            zzbg.e("getBoolean called for closed container.");
            return zzdf.zzDU().booleanValue();
        }
        try {
            return zzdf.zzk(zzcpVarZzCu.zzeY(key).getObject()).booleanValue();
        } catch (Exception e) {
            zzbg.e("Calling getBoolean() threw an exception: " + e.getMessage() + " Returning default value.");
            return zzdf.zzDU().booleanValue();
        }
    }

    public String getContainerId() {
        return this.zzaVQ;
    }

    public double getDouble(String key) {
        zzcp zzcpVarZzCu = zzCu();
        if (zzcpVarZzCu == null) {
            zzbg.e("getDouble called for closed container.");
            return zzdf.zzDT().doubleValue();
        }
        try {
            return zzdf.zzj(zzcpVarZzCu.zzeY(key).getObject()).doubleValue();
        } catch (Exception e) {
            zzbg.e("Calling getDouble() threw an exception: " + e.getMessage() + " Returning default value.");
            return zzdf.zzDT().doubleValue();
        }
    }

    public long getLastRefreshTime() {
        return this.zzaVV;
    }

    public long getLong(String key) {
        zzcp zzcpVarZzCu = zzCu();
        if (zzcpVarZzCu == null) {
            zzbg.e("getLong called for closed container.");
            return zzdf.zzDS().longValue();
        }
        try {
            return zzdf.zzi(zzcpVarZzCu.zzeY(key).getObject()).longValue();
        } catch (Exception e) {
            zzbg.e("Calling getLong() threw an exception: " + e.getMessage() + " Returning default value.");
            return zzdf.zzDS().longValue();
        }
    }

    public String getString(String key) {
        zzcp zzcpVarZzCu = zzCu();
        if (zzcpVarZzCu == null) {
            zzbg.e("getString called for closed container.");
            return zzdf.zzDW();
        }
        try {
            return zzdf.zzg(zzcpVarZzCu.zzeY(key).getObject());
        } catch (Exception e) {
            zzbg.e("Calling getString() threw an exception: " + e.getMessage() + " Returning default value.");
            return zzdf.zzDW();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String customMacroName, FunctionCallMacroCallback customMacroCallback) {
        if (customMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.zzaVT) {
            this.zzaVT.put(customMacroName, customMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String customTagName, FunctionCallTagCallback customTagCallback) {
        if (customTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.zzaVU) {
            this.zzaVU.put(customTagName, customTagCallback);
        }
    }

    void release() {
        this.zzaVS = null;
    }

    public void unregisterFunctionCallMacroCallback(String customMacroName) {
        synchronized (this.zzaVT) {
            this.zzaVT.remove(customMacroName);
        }
    }

    public void unregisterFunctionCallTagCallback(String customTagName) {
        synchronized (this.zzaVU) {
            this.zzaVU.remove(customTagName);
        }
    }

    String zzCt() {
        return this.zzaVW;
    }

    FunctionCallMacroCallback zzeA(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zzaVT) {
            functionCallMacroCallback = this.zzaVT.get(str);
        }
        return functionCallMacroCallback;
    }

    FunctionCallTagCallback zzeB(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzaVU) {
            functionCallTagCallback = this.zzaVU.get(str);
        }
        return functionCallTagCallback;
    }

    void zzeC(String str) {
        zzCu().zzeC(str);
    }

    zzah zzeD(String str) {
        if (zzcb.zzDm().zzDn().equals(zzcb.zza.CONTAINER_DEBUG)) {
        }
        return new zzbo();
    }
}
