package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzn;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzan;
import com.google.android.gms.internal.zzbb;
import com.google.android.gms.internal.zzdk;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zziz;
import com.google.android.gms.internal.zzja;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzh {
    private final Context mContext;
    private zziz zzoM;
    private final VersionInfoParcel zzpb;
    private final Object zzpd = new Object();
    private final zzn zzwF;
    private final JSONObject zzwI;
    private final zzbb zzwJ;
    private final zza zzwK;
    private final zzan zzwL;
    private boolean zzwM;
    private String zzwN;

    public interface zza {
        String getCustomTemplateId();

        void zza(zzh zzhVar);

        String zzdy();

        com.google.android.gms.ads.internal.formats.zza zzdz();
    }

    public zzh(Context context, zzn zznVar, zzbb zzbbVar, zzan zzanVar, JSONObject jSONObject, zza zzaVar, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzwF = zznVar;
        this.zzwJ = zzbbVar;
        this.zzwL = zzanVar;
        this.zzwI = jSONObject;
        this.zzwK = zzaVar;
        this.zzpb = versionInfoParcel;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void recordImpression() {
        zzx.zzci("recordImpression must be called on the main UI thread.");
        zzl(true);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("ad", this.zzwI);
            this.zzwJ.zza("google.afma.nativeAds.handleImpressionPing", jSONObject);
        } catch (JSONException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to create impression JSON.", e);
        }
    }

    public zzb zza(View.OnClickListener onClickListener) {
        com.google.android.gms.ads.internal.formats.zza zzaVarZzdz = this.zzwK.zzdz();
        if (zzaVarZzdz == null) {
            return null;
        }
        zzb zzbVar = new zzb(this.mContext, zzaVarZzdz);
        zzbVar.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        zzbVar.zzdu().setOnClickListener(onClickListener);
        zzbVar.zzdu().setContentDescription("Ad attribution icon");
        return zzbVar;
    }

    public void zza(View view, Map<String, WeakReference<View>> map, JSONObject jSONObject, JSONObject jSONObject2) {
        zzx.zzci("performClick must be called on the main UI thread.");
        for (Map.Entry<String, WeakReference<View>> entry : map.entrySet()) {
            if (view.equals(entry.getValue().get())) {
                zza(entry.getKey(), jSONObject, jSONObject2);
                return;
            }
        }
    }

    public void zza(String str, JSONObject jSONObject, JSONObject jSONObject2) {
        zzx.zzci("performClick must be called on the main UI thread.");
        try {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("asset", str);
            jSONObject3.put("template", this.zzwK.zzdy());
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("ad", this.zzwI);
            jSONObject4.put("click", jSONObject3);
            jSONObject4.put("has_custom_click_handler", this.zzwF.zzr(this.zzwK.getCustomTemplateId()) != null);
            if (jSONObject != null) {
                jSONObject4.put("view_rectangles", jSONObject);
            }
            if (jSONObject2 != null) {
                jSONObject4.put("click_point", jSONObject2);
            }
            this.zzwJ.zza("google.afma.nativeAds.handleClickGmsg", jSONObject4);
        } catch (JSONException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to create click JSON.", e);
        }
    }

    public void zzb(MotionEvent motionEvent) {
        this.zzwL.zza(motionEvent);
    }

    public zziz zzdC() {
        this.zzoM = zzdD();
        this.zzoM.getView().setVisibility(8);
        this.zzwJ.zza("/loadHtml", new zzdk() { // from class: com.google.android.gms.ads.internal.formats.zzh.1
            @Override // com.google.android.gms.internal.zzdk
            public void zza(zziz zzizVar, final Map<String, String> map) {
                zzh.this.zzoM.zzhe().zza(new zzja.zza() { // from class: com.google.android.gms.ads.internal.formats.zzh.1.1
                    @Override // com.google.android.gms.internal.zzja.zza
                    public void zza(zziz zzizVar2, boolean z) {
                        zzh.this.zzwN = (String) map.get("id");
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("messageType", "htmlLoaded");
                            jSONObject.put("id", zzh.this.zzwN);
                            zzh.this.zzwJ.zzb("sendMessageToNativeJs", jSONObject);
                        } catch (JSONException e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to dispatch sendMessageToNativeJsevent", e);
                        }
                    }
                });
                String str = map.get("overlayHtml");
                String str2 = map.get("baseUrl");
                if (TextUtils.isEmpty(str2)) {
                    zzh.this.zzoM.loadData(str, "text/html", "UTF-8");
                } else {
                    zzh.this.zzoM.loadDataWithBaseURL(str2, str, "text/html", "UTF-8", null);
                }
            }
        });
        this.zzwJ.zza("/showOverlay", new zzdk() { // from class: com.google.android.gms.ads.internal.formats.zzh.2
            @Override // com.google.android.gms.internal.zzdk
            public void zza(zziz zzizVar, Map<String, String> map) {
                zzh.this.zzoM.getView().setVisibility(0);
            }
        });
        this.zzwJ.zza("/hideOverlay", new zzdk() { // from class: com.google.android.gms.ads.internal.formats.zzh.3
            @Override // com.google.android.gms.internal.zzdk
            public void zza(zziz zzizVar, Map<String, String> map) {
                zzh.this.zzoM.getView().setVisibility(8);
            }
        });
        this.zzoM.zzhe().zza("/hideOverlay", new zzdk() { // from class: com.google.android.gms.ads.internal.formats.zzh.4
            @Override // com.google.android.gms.internal.zzdk
            public void zza(zziz zzizVar, Map<String, String> map) {
                zzh.this.zzoM.getView().setVisibility(8);
            }
        });
        this.zzoM.zzhe().zza("/sendMessageToSdk", new zzdk() { // from class: com.google.android.gms.ads.internal.formats.zzh.5
            @Override // com.google.android.gms.internal.zzdk
            public void zza(zziz zzizVar, Map<String, String> map) {
                JSONObject jSONObject = new JSONObject();
                try {
                    for (String str : map.keySet()) {
                        jSONObject.put(str, map.get(str));
                    }
                    jSONObject.put("id", zzh.this.zzwN);
                    zzh.this.zzwJ.zzb("sendMessageToNativeJs", jSONObject);
                } catch (JSONException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to dispatch sendMessageToNativeJs event", e);
                }
            }
        });
        return this.zzoM;
    }

    zziz zzdD() {
        return zzp.zzbw().zza(this.mContext, AdSizeParcel.zzs(this.mContext), false, false, this.zzwL, this.zzpb);
    }

    public void zzh(View view) {
    }

    public void zzi(View view) {
        synchronized (this.zzpd) {
            if (this.zzwM) {
                return;
            }
            if (view.isShown()) {
                if (view.getGlobalVisibleRect(new Rect(), null)) {
                    recordImpression();
                }
            }
        }
    }

    protected void zzl(boolean z) {
        this.zzwM = z;
    }
}
