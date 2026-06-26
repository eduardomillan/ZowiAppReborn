package com.google.android.gms.tagmanager;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public class TagManager {
    private static TagManager zzaZr;
    private final Context mContext;
    private final DataLayer zzaVR;
    private final zzs zzaYl;
    private final zza zzaZo;
    private final zzct zzaZp;
    private final ConcurrentMap<zzo, Boolean> zzaZq;

    public interface zza {
        zzp zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzs zzsVar);
    }

    TagManager(Context context, zza containerHolderLoaderProvider, DataLayer dataLayer, zzct serviceManager) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.zzaZp = serviceManager;
        this.zzaZo = containerHolderLoaderProvider;
        this.zzaZq = new ConcurrentHashMap();
        this.zzaVR = dataLayer;
        this.zzaVR.zza(new DataLayer.zzb() { // from class: com.google.android.gms.tagmanager.TagManager.1
            @Override // com.google.android.gms.tagmanager.DataLayer.zzb
            public void zzJ(Map<String, Object> map) {
                Object obj = map.get("event");
                if (obj != null) {
                    TagManager.this.zzfa(obj.toString());
                }
            }
        });
        this.zzaVR.zza(new zzd(this.mContext));
        this.zzaYl = new zzs();
        zzDL();
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zzaZr == null) {
                if (context == null) {
                    zzbg.e("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                zzaZr = new TagManager(context, new zza() { // from class: com.google.android.gms.tagmanager.TagManager.2
                    @Override // com.google.android.gms.tagmanager.TagManager.zza
                    public zzp zza(Context context2, TagManager tagManager2, Looper looper, String str, int i, zzs zzsVar) {
                        return new zzp(context2, tagManager2, looper, str, i, zzsVar);
                    }
                }, new DataLayer(new zzw(context)), zzcu.zzDG());
            }
            tagManager = zzaZr;
        }
        return tagManager;
    }

    private void zzDL() {
        if (Build.VERSION.SDK_INT >= 14) {
            this.mContext.registerComponentCallbacks(new ComponentCallbacks2() { // from class: com.google.android.gms.tagmanager.TagManager.3
                @Override // android.content.ComponentCallbacks
                public void onConfigurationChanged(Configuration configuration) {
                }

                @Override // android.content.ComponentCallbacks
                public void onLowMemory() {
                }

                @Override // android.content.ComponentCallbacks2
                public void onTrimMemory(int i) {
                    if (i == 20) {
                        TagManager.this.dispatch();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzfa(String str) {
        Iterator<zzo> it = this.zzaZq.keySet().iterator();
        while (it.hasNext()) {
            it.next().zzeC(str);
        }
    }

    public void dispatch() {
        this.zzaZp.dispatch();
    }

    public DataLayer getDataLayer() {
        return this.zzaVR;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String containerId, int defaultContainerResourceId) {
        zzp zzpVarZza = this.zzaZo.zza(this.mContext, this, null, containerId, defaultContainerResourceId, this.zzaYl);
        zzpVarZza.zzCy();
        return zzpVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String containerId, int defaultContainerResourceId, Handler handler) {
        zzp zzpVarZza = this.zzaZo.zza(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.zzaYl);
        zzpVarZza.zzCy();
        return zzpVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String containerId, int defaultContainerResourceId) {
        zzp zzpVarZza = this.zzaZo.zza(this.mContext, this, null, containerId, defaultContainerResourceId, this.zzaYl);
        zzpVarZza.zzCA();
        return zzpVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String containerId, int defaultContainerResourceId, Handler handler) {
        zzp zzpVarZza = this.zzaZo.zza(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.zzaYl);
        zzpVarZza.zzCA();
        return zzpVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String containerId, int defaultContainerResourceId) {
        zzp zzpVarZza = this.zzaZo.zza(this.mContext, this, null, containerId, defaultContainerResourceId, this.zzaYl);
        zzpVarZza.zzCz();
        return zzpVarZza;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String containerId, int defaultContainerResourceId, Handler handler) {
        zzp zzpVarZza = this.zzaZo.zza(this.mContext, this, handler.getLooper(), containerId, defaultContainerResourceId, this.zzaYl);
        zzpVarZza.zzCz();
        return zzpVarZza;
    }

    public void setVerboseLoggingEnabled(boolean enableVerboseLogging) {
        zzbg.setLogLevel(enableVerboseLogging ? 2 : 5);
    }

    void zza(zzo zzoVar) {
        this.zzaZq.put(zzoVar, true);
    }

    boolean zzb(zzo zzoVar) {
        return this.zzaZq.remove(zzoVar) != null;
    }

    synchronized boolean zzm(Uri uri) {
        boolean z;
        zzcb zzcbVarZzDm = zzcb.zzDm();
        if (zzcbVarZzDm.zzm(uri)) {
            String containerId = zzcbVarZzDm.getContainerId();
            switch (zzcbVarZzDm.zzDn()) {
                case NONE:
                    for (zzo zzoVar : this.zzaZq.keySet()) {
                        if (zzoVar.getContainerId().equals(containerId)) {
                            zzoVar.zzeE(null);
                            zzoVar.refresh();
                        }
                    }
                    break;
                case CONTAINER:
                case CONTAINER_DEBUG:
                    for (zzo zzoVar2 : this.zzaZq.keySet()) {
                        if (zzoVar2.getContainerId().equals(containerId)) {
                            zzoVar2.zzeE(zzcbVarZzDm.zzDo());
                            zzoVar2.refresh();
                        } else if (zzoVar2.zzCv() != null) {
                            zzoVar2.zzeE(null);
                            zzoVar2.refresh();
                        }
                    }
                    break;
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}
