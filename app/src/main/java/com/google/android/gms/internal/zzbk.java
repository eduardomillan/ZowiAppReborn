package com.google.android.gms.internal;

import android.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzbk extends Thread {
    private final zzbj zzsa;
    private final zzbi zzsb;
    private final zzgq zzsc;
    private boolean mStarted = false;
    private boolean zzrZ = false;
    private boolean zzam = false;
    private final Object zzpd = new Object();
    private final int zzrN = zzby.zzuU.get().intValue();
    private final int zzse = zzby.zzuV.get().intValue();
    private final int zzrP = zzby.zzuW.get().intValue();
    private final int zzsf = zzby.zzuX.get().intValue();
    private final int zzsd = zzby.zzuY.get().intValue();

    @zzgr
    class zza {
        final int zzsm;
        final int zzsn;

        zza(int i, int i2) {
            this.zzsm = i;
            this.zzsn = i2;
        }
    }

    public zzbk(zzbj zzbjVar, zzbi zzbiVar, zzgq zzgqVar) {
        this.zzsa = zzbjVar;
        this.zzsb = zzbiVar;
        this.zzsc = zzgqVar;
        setName("ContentFetchTask");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (!this.zzam) {
            try {
            } catch (Throwable th) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error in ContentFetchTask", th);
                this.zzsc.zza(th, true);
            }
            if (zzcu()) {
                Activity activity = this.zzsa.getActivity();
                if (activity == null) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaF("ContentFetchThread: no activity");
                } else {
                    zza(activity);
                }
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("ContentFetchTask: sleeping");
                zzcw();
            }
            Thread.sleep(this.zzsd * 1000);
            synchronized (this.zzpd) {
                while (this.zzrZ) {
                    try {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaF("ContentFetchTask: waiting");
                        this.zzpd.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public void wakeup() {
        synchronized (this.zzpd) {
            this.zzrZ = false;
            this.zzpd.notifyAll();
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("ContentFetchThread: wakeup");
        }
    }

    zza zza(View view, zzbh zzbhVar) {
        if (view == null) {
            return new zza(0, 0);
        }
        if ((view instanceof TextView) && !(view instanceof EditText)) {
            CharSequence text = ((TextView) view).getText();
            if (TextUtils.isEmpty(text)) {
                return new zza(0, 0);
            }
            zzbhVar.zzw(text.toString());
            return new zza(1, 0);
        }
        if ((view instanceof WebView) && !(view instanceof zziz)) {
            zzbhVar.zzcp();
            return zza((WebView) view, zzbhVar) ? new zza(0, 1) : new zza(0, 0);
        }
        if (!(view instanceof ViewGroup)) {
            return new zza(0, 0);
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
            zza zzaVarZza = zza(viewGroup.getChildAt(i3), zzbhVar);
            i2 += zzaVarZza.zzsm;
            i += zzaVarZza.zzsn;
        }
        return new zza(i2, i);
    }

    void zza(Activity activity) {
        if (activity == null) {
            return;
        }
        View viewFindViewById = null;
        if (activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            viewFindViewById = activity.getWindow().getDecorView().findViewById(R.id.content);
        }
        if (viewFindViewById != null) {
            zzf(viewFindViewById);
        }
    }

    void zza(zzbh zzbhVar, WebView webView, String str) {
        zzbhVar.zzco();
        try {
            if (!TextUtils.isEmpty(str)) {
                String strOptString = new JSONObject(str).optString("text");
                if (TextUtils.isEmpty(webView.getTitle())) {
                    zzbhVar.zzv(strOptString);
                } else {
                    zzbhVar.zzv(webView.getTitle() + Droid2InoConstants.NEW_LINE_CHARACTER + strOptString);
                }
            }
            if (zzbhVar.zzcl()) {
                this.zzsb.zzb(zzbhVar);
            }
        } catch (JSONException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Json string may be malformed.");
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zza("Failed to get webview content.", th);
            this.zzsc.zza(th, true);
        }
    }

    boolean zza(ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        return runningAppProcessInfo.importance == 100;
    }

    boolean zza(final WebView webView, final zzbh zzbhVar) {
        if (!zzmx.zzqB()) {
            return false;
        }
        zzbhVar.zzcp();
        webView.post(new Runnable() { // from class: com.google.android.gms.internal.zzbk.2
            ValueCallback<String> zzsi = new ValueCallback<String>() { // from class: com.google.android.gms.internal.zzbk.2.1
                @Override // android.webkit.ValueCallback
                /* JADX INFO: renamed from: zzy, reason: merged with bridge method [inline-methods] */
                public void onReceiveValue(String str) {
                    zzbk.this.zza(zzbhVar, webView, str);
                }
            };

            @Override // java.lang.Runnable
            public void run() {
                if (webView.getSettings().getJavaScriptEnabled()) {
                    try {
                        webView.evaluateJavascript("(function() { return  {text:document.body.innerText}})();", this.zzsi);
                    } catch (Throwable th) {
                        this.zzsi.onReceiveValue("");
                    }
                }
            }
        });
        return true;
    }

    public void zzct() {
        synchronized (this.zzpd) {
            if (this.mStarted) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Content hash thread already started, quiting...");
            } else {
                this.mStarted = true;
                start();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0045, code lost:
    
        if (zza(r0) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
    
        if (r1.inKeyguardRestrictedInputMode() != false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0051, code lost:
    
        if (zzr(r3) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0053, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean zzcu() {
        /*
            r7 = this;
            r2 = 0
            com.google.android.gms.internal.zzbj r0 = r7.zzsa     // Catch: java.lang.Throwable -> L57
            android.content.Context r3 = r0.getContext()     // Catch: java.lang.Throwable -> L57
            if (r3 != 0) goto Lb
            r0 = r2
        La:
            return r0
        Lb:
            java.lang.String r0 = "activity"
            java.lang.Object r0 = r3.getSystemService(r0)     // Catch: java.lang.Throwable -> L57
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0     // Catch: java.lang.Throwable -> L57
            java.lang.String r1 = "keyguard"
            java.lang.Object r1 = r3.getSystemService(r1)     // Catch: java.lang.Throwable -> L57
            android.app.KeyguardManager r1 = (android.app.KeyguardManager) r1     // Catch: java.lang.Throwable -> L57
            if (r0 == 0) goto L1f
            if (r1 != 0) goto L21
        L1f:
            r0 = r2
            goto La
        L21:
            java.util.List r0 = r0.getRunningAppProcesses()     // Catch: java.lang.Throwable -> L57
            if (r0 != 0) goto L29
            r0 = r2
            goto La
        L29:
            java.util.Iterator r4 = r0.iterator()     // Catch: java.lang.Throwable -> L57
        L2d:
            boolean r0 = r4.hasNext()     // Catch: java.lang.Throwable -> L57
            if (r0 == 0) goto L55
            java.lang.Object r0 = r4.next()     // Catch: java.lang.Throwable -> L57
            android.app.ActivityManager$RunningAppProcessInfo r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0     // Catch: java.lang.Throwable -> L57
            int r5 = android.os.Process.myPid()     // Catch: java.lang.Throwable -> L57
            int r6 = r0.pid     // Catch: java.lang.Throwable -> L57
            if (r5 != r6) goto L2d
            boolean r0 = r7.zza(r0)     // Catch: java.lang.Throwable -> L57
            if (r0 == 0) goto L55
            boolean r0 = r1.inKeyguardRestrictedInputMode()     // Catch: java.lang.Throwable -> L57
            if (r0 != 0) goto L55
            boolean r0 = r7.zzr(r3)     // Catch: java.lang.Throwable -> L57
            if (r0 == 0) goto L55
            r0 = 1
            goto La
        L55:
            r0 = r2
            goto La
        L57:
            r0 = move-exception
            r0 = r2
            goto La
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbk.zzcu():boolean");
    }

    public zzbh zzcv() {
        return this.zzsb.zzcs();
    }

    public void zzcw() {
        synchronized (this.zzpd) {
            this.zzrZ = true;
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("ContentFetchThread: paused, mPause = " + this.zzrZ);
        }
    }

    public boolean zzcx() {
        return this.zzrZ;
    }

    boolean zzf(final View view) {
        if (view == null) {
            return false;
        }
        view.post(new Runnable() { // from class: com.google.android.gms.internal.zzbk.1
            @Override // java.lang.Runnable
            public void run() {
                zzbk.this.zzg(view);
            }
        });
        return true;
    }

    void zzg(View view) {
        try {
            zzbh zzbhVar = new zzbh(this.zzrN, this.zzse, this.zzrP, this.zzsf);
            zza zzaVarZza = zza(view, zzbhVar);
            zzbhVar.zzcq();
            if (zzaVarZza.zzsm == 0 && zzaVarZza.zzsn == 0) {
                return;
            }
            if (zzaVarZza.zzsn == 0 && zzbhVar.zzcr() == 0) {
                return;
            }
            if (zzaVarZza.zzsn == 0 && this.zzsb.zza(zzbhVar)) {
                return;
            }
            this.zzsb.zzc(zzbhVar);
        } catch (Exception e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Exception in fetchContentOnUIThread", e);
            this.zzsc.zza(e, true);
        }
    }

    boolean zzr(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return false;
        }
        return powerManager.isScreenOn();
    }
}
