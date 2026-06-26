package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzis;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzaz implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private final Context zzqZ;
    private final WeakReference<zzhs> zzrb;
    private final WeakReference<View> zzrd;
    private final zzax zzre;
    private final zzdz zzrf;
    private final zzdz.zzd zzrg;
    private boolean zzrh;
    private final WindowManager zzri;
    private final PowerManager zzrj;
    private final KeyguardManager zzrk;
    private zzba zzrl;
    private boolean zzrm;
    private boolean zzrp;
    private BroadcastReceiver zzrq;
    private final Object zzpd = new Object();
    private boolean zzpJ = false;
    private boolean zzrn = false;
    private final HashSet<zzaw> zzrr = new HashSet<>();
    private final zzdk zzrs = new zzdk() { // from class: com.google.android.gms.internal.zzaz.6
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            if (zzaz.this.zzb(map)) {
                zzaz.this.zza(zzizVar.getView(), map);
            }
        }
    };
    private final zzdk zzrt = new zzdk() { // from class: com.google.android.gms.internal.zzaz.7
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            if (zzaz.this.zzb(map)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Received request to untrack: " + zzaz.this.zzre.zzbX());
                zzaz.this.destroy();
            }
        }
    };
    private final zzdk zzru = new zzdk() { // from class: com.google.android.gms.internal.zzaz.8
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            if (zzaz.this.zzb(map) && map.containsKey("isVisible")) {
                zzaz.this.zzg(Boolean.valueOf("1".equals(map.get("isVisible")) || "true".equals(map.get("isVisible"))).booleanValue());
            }
        }
    };
    private WeakReference<ViewTreeObserver> zzrc = new WeakReference<>(null);
    private boolean zzro = true;
    private zzik zzqM = new zzik(200);

    public zzaz(AdSizeParcel adSizeParcel, zzhs zzhsVar, VersionInfoParcel versionInfoParcel, View view, zzdz zzdzVar) {
        this.zzrf = zzdzVar;
        this.zzrb = new WeakReference<>(zzhsVar);
        this.zzrd = new WeakReference<>(view);
        this.zzre = new zzax(UUID.randomUUID().toString(), versionInfoParcel, adSizeParcel.zzte, zzhsVar.zzHw, zzhsVar.zzbY());
        this.zzrg = this.zzrf.zzdO();
        this.zzri = (WindowManager) view.getContext().getSystemService("window");
        this.zzrj = (PowerManager) view.getContext().getApplicationContext().getSystemService("power");
        this.zzrk = (KeyguardManager) view.getContext().getSystemService("keyguard");
        this.zzqZ = view.getContext().getApplicationContext();
        try {
            final JSONObject jSONObjectZzd = zzd(view);
            this.zzrg.zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzaz.1
                @Override // com.google.android.gms.internal.zzis.zzc
                /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
                public void zzc(zzbe zzbeVar) {
                    zzaz.this.zza(jSONObjectZzd);
                }
            }, new zzis.zza() { // from class: com.google.android.gms.internal.zzaz.2
                @Override // com.google.android.gms.internal.zzis.zza
                public void run() {
                }
            });
        } catch (RuntimeException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failure while processing active view data.", e);
        } catch (JSONException e2) {
        }
        this.zzrg.zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzaz.3
            @Override // com.google.android.gms.internal.zzis.zzc
            /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
            public void zzc(zzbe zzbeVar) {
                zzaz.this.zzrh = true;
                zzaz.this.zza(zzbeVar);
                zzaz.this.zzbZ();
                zzaz.this.zzh(false);
            }
        }, new zzis.zza() { // from class: com.google.android.gms.internal.zzaz.4
            @Override // com.google.android.gms.internal.zzis.zza
            public void run() {
                zzaz.this.destroy();
            }
        });
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Tracking ad unit: " + this.zzre.zzbX());
    }

    protected void destroy() {
        synchronized (this.zzpd) {
            zzcf();
            zzca();
            this.zzro = false;
            zzcc();
            this.zzrg.release();
        }
    }

    boolean isScreenOn() {
        return this.zzrj.isScreenOn();
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        zzh(false);
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        zzh(true);
    }

    public void pause() {
        synchronized (this.zzpd) {
            this.zzpJ = true;
            zzh(false);
        }
    }

    public void resume() {
        synchronized (this.zzpd) {
            this.zzpJ = false;
            zzh(false);
        }
    }

    public void stop() {
        synchronized (this.zzpd) {
            this.zzrn = true;
            zzh(false);
        }
    }

    protected int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (i / displayMetrics.density);
    }

    protected void zza(View view, Map<String, String> map) {
        zzh(false);
    }

    public void zza(zzaw zzawVar) {
        this.zzrr.add(zzawVar);
    }

    public void zza(zzba zzbaVar) {
        synchronized (this.zzpd) {
            this.zzrl = zzbaVar;
        }
    }

    protected void zza(zzbe zzbeVar) {
        zzbeVar.zza("/updateActiveView", this.zzrs);
        zzbeVar.zza("/untrackActiveViewUnit", this.zzrt);
        zzbeVar.zza("/visibilityChanged", this.zzru);
    }

    protected void zza(JSONObject jSONObject) {
        try {
            JSONArray jSONArray = new JSONArray();
            final JSONObject jSONObject2 = new JSONObject();
            jSONArray.put(jSONObject);
            jSONObject2.put("units", jSONArray);
            this.zzrg.zza(new zzis.zzc<zzbe>() { // from class: com.google.android.gms.internal.zzaz.9
                @Override // com.google.android.gms.internal.zzis.zzc
                /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
                public void zzc(zzbe zzbeVar) {
                    zzbeVar.zza("AFMA_updateActiveView", jSONObject2);
                }
            }, new zzis.zzb());
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Skipping active view message.", th);
        }
    }

    protected boolean zzb(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = map.get("hashCode");
        return !TextUtils.isEmpty(str) && str.equals(this.zzre.zzbX());
    }

    protected void zzbZ() {
        synchronized (this.zzpd) {
            if (this.zzrq != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.zzrq = new BroadcastReceiver() { // from class: com.google.android.gms.internal.zzaz.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    zzaz.this.zzh(false);
                }
            };
            this.zzqZ.registerReceiver(this.zzrq, intentFilter);
        }
    }

    protected void zzca() {
        synchronized (this.zzpd) {
            if (this.zzrq != null) {
                this.zzqZ.unregisterReceiver(this.zzrq);
                this.zzrq = null;
            }
        }
    }

    public void zzcb() {
        synchronized (this.zzpd) {
            if (this.zzro) {
                this.zzrp = true;
                try {
                    try {
                        zza(zzch());
                    } catch (JSONException e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzb("JSON failure while processing active view data.", e);
                    }
                } catch (RuntimeException e2) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Failure while processing active view data.", e2);
                }
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Untracking ad unit: " + this.zzre.zzbX());
            }
        }
    }

    protected void zzcc() {
        if (this.zzrl != null) {
            this.zzrl.zza(this);
        }
    }

    public boolean zzcd() {
        boolean z;
        synchronized (this.zzpd) {
            z = this.zzro;
        }
        return z;
    }

    protected void zzce() {
        ViewTreeObserver viewTreeObserver;
        ViewTreeObserver viewTreeObserver2;
        View view = this.zzrd.get();
        if (view == null || (viewTreeObserver2 = view.getViewTreeObserver()) == (viewTreeObserver = this.zzrc.get())) {
            return;
        }
        zzcf();
        if (!this.zzrm || (viewTreeObserver != null && viewTreeObserver.isAlive())) {
            this.zzrm = true;
            viewTreeObserver2.addOnScrollChangedListener(this);
            viewTreeObserver2.addOnGlobalLayoutListener(this);
        }
        this.zzrc = new WeakReference<>(viewTreeObserver2);
    }

    protected void zzcf() {
        ViewTreeObserver viewTreeObserver = this.zzrc.get();
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return;
        }
        viewTreeObserver.removeOnScrollChangedListener(this);
        viewTreeObserver.removeGlobalOnLayoutListener(this);
    }

    protected JSONObject zzcg() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.zzre.zzbV()).put("activeViewJSON", this.zzre.zzbW()).put("timestamp", com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime()).put("adFormat", this.zzre.zzbU()).put("hashCode", this.zzre.zzbX()).put("isMraid", this.zzre.zzbY());
        return jSONObject;
    }

    protected JSONObject zzch() throws JSONException {
        JSONObject jSONObjectZzcg = zzcg();
        jSONObjectZzcg.put("doneReasonCode", "u");
        return jSONObjectZzcg;
    }

    protected JSONObject zzd(View view) throws JSONException {
        boolean zIsAttachedToWindow = com.google.android.gms.ads.internal.zzp.zzbx().isAttachedToWindow(view);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view.getLocationOnScreen(iArr);
            view.getLocationInWindow(iArr2);
        } catch (Exception e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failure getting view location.", e);
        }
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        rect2.right = this.zzri.getDefaultDisplay().getWidth();
        rect2.bottom = this.zzri.getDefaultDisplay().getHeight();
        Rect rect3 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect3, null);
        Rect rect4 = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect4);
        Rect rect5 = new Rect();
        view.getHitRect(rect5);
        JSONObject jSONObjectZzcg = zzcg();
        jSONObjectZzcg.put("windowVisibility", view.getWindowVisibility()).put("isStopped", this.zzrn).put("isPaused", this.zzpJ).put("isScreenOn", isScreenOn()).put("isAttachedToWindow", zIsAttachedToWindow).put("viewBox", new JSONObject().put("top", zza(rect2.top, displayMetrics)).put("bottom", zza(rect2.bottom, displayMetrics)).put("left", zza(rect2.left, displayMetrics)).put(AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_RIGHT, zza(rect2.right, displayMetrics))).put("adBox", new JSONObject().put("top", zza(rect.top, displayMetrics)).put("bottom", zza(rect.bottom, displayMetrics)).put("left", zza(rect.left, displayMetrics)).put(AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_RIGHT, zza(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", zza(rect3.top, displayMetrics)).put("bottom", zza(rect3.bottom, displayMetrics)).put("left", zza(rect3.left, displayMetrics)).put(AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_RIGHT, zza(rect3.right, displayMetrics))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put("top", zza(rect4.top, displayMetrics)).put("bottom", zza(rect4.bottom, displayMetrics)).put("left", zza(rect4.left, displayMetrics)).put(AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_RIGHT, zza(rect4.right, displayMetrics))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put("top", zza(rect5.top, displayMetrics)).put("bottom", zza(rect5.bottom, displayMetrics)).put("left", zza(rect5.left, displayMetrics)).put(AnalyticsUtils.EVENT_PROJECT_QUIZ_QUESTION_RIGHT, zza(rect5.right, displayMetrics))).put("screenDensity", displayMetrics.density).put("isVisible", zze(view));
        return jSONObjectZzcg;
    }

    protected boolean zze(View view) {
        return view.getVisibility() == 0 && view.isShown() && isScreenOn() && (!this.zzrk.inKeyguardRestrictedInputMode() || com.google.android.gms.ads.internal.zzp.zzbv().zzgB());
    }

    protected void zzg(boolean z) {
        Iterator<zzaw> it = this.zzrr.iterator();
        while (it.hasNext()) {
            it.next().zza(this, z);
        }
    }

    protected void zzh(boolean z) {
        synchronized (this.zzpd) {
            if (this.zzrh && this.zzro) {
                if (!z || this.zzqM.tryAcquire()) {
                    zzhs zzhsVar = this.zzrb.get();
                    View view = this.zzrd.get();
                    if (view == null || zzhsVar == null) {
                        zzcb();
                        return;
                    }
                    try {
                        zza(zzd(view));
                    } catch (RuntimeException | JSONException e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zza("Active view update failed.", e);
                    }
                    zzce();
                    zzcc();
                }
            }
        }
    }
}
