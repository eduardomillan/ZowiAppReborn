package com.google.android.gms.ads.internal.overlay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzfk;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zzif;
import com.google.android.gms.internal.zziz;
import com.google.android.gms.internal.zzja;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzd extends zzfk.zza implements zzo {
    static final int zzBh = Color.argb(0, 0, 0, 0);
    private final Activity mActivity;
    RelativeLayout zzAn;
    AdOverlayInfoParcel zzBi;
    zzc zzBj;
    zzm zzBk;
    FrameLayout zzBm;
    WebChromeClient.CustomViewCallback zzBn;
    private boolean zzBs;
    zziz zzoM;
    boolean zzBl = false;
    boolean zzBo = false;
    boolean zzBp = false;
    boolean zzBq = false;
    int zzBr = 0;
    private boolean zzBt = false;
    private boolean zzBu = true;

    @zzgr
    private static final class zza extends Exception {
        public zza(String str) {
            super(str);
        }
    }

    @zzgr
    static final class zzb extends RelativeLayout {
        zzif zzqQ;

        public zzb(Context context, String str) {
            super(context);
            this.zzqQ = new zzif(context, str);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.zzqQ.zze(event);
            return false;
        }
    }

    @zzgr
    public static class zzc {
        public final Context context;
        public final int index;
        public final ViewGroup.LayoutParams zzBw;
        public final ViewGroup zzBx;

        public zzc(zziz zzizVar) throws zza {
            this.zzBw = zzizVar.getLayoutParams();
            ViewParent parent = zzizVar.getParent();
            this.context = zzizVar.zzha();
            if (parent == null || !(parent instanceof ViewGroup)) {
                throw new zza("Could not get the parent of the WebView for an overlay.");
            }
            this.zzBx = (ViewGroup) parent;
            this.index = this.zzBx.indexOfChild(zzizVar.getView());
            this.zzBx.removeView(zzizVar.getView());
            zzizVar.zzC(true);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.ads.internal.overlay.zzd$zzd, reason: collision with other inner class name */
    @zzgr
    private class C0017zzd extends zzhz {
        private C0017zzd() {
        }

        @Override // com.google.android.gms.internal.zzhz
        public void onStop() {
        }

        @Override // com.google.android.gms.internal.zzhz
        public void zzbn() {
            Bitmap bitmapZzg = com.google.android.gms.ads.internal.zzp.zzbv().zzg(zzd.this.mActivity, zzd.this.zzBi.zzBM.zzpv);
            if (bitmapZzg != null) {
                final Drawable drawableZza = com.google.android.gms.ads.internal.zzp.zzbx().zza(zzd.this.mActivity, bitmapZzg, zzd.this.zzBi.zzBM.zzpw, zzd.this.zzBi.zzBM.zzpx);
                zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzd.zzd.1
                    @Override // java.lang.Runnable
                    public void run() {
                        zzd.this.mActivity.getWindow().setBackgroundDrawable(drawableZza);
                    }
                });
            }
        }
    }

    public zzd(Activity activity) {
        this.mActivity = activity;
    }

    public void close() {
        this.zzBr = 2;
        this.mActivity.finish();
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onBackPressed() {
        this.zzBr = 0;
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onCreate(Bundle savedInstanceState) {
        this.zzBo = savedInstanceState != null ? savedInstanceState.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false) : false;
        try {
            this.zzBi = AdOverlayInfoParcel.zzb(this.mActivity.getIntent());
            if (this.zzBi == null) {
                throw new zza("Could not get info for ad overlay.");
            }
            if (this.zzBi.zzqj.zzJw > 7500000) {
                this.zzBr = 3;
            }
            if (this.mActivity.getIntent() != null) {
                this.zzBu = this.mActivity.getIntent().getBooleanExtra("shouldCallOnOverlayOpened", true);
            }
            if (this.zzBi.zzBM != null) {
                this.zzBp = this.zzBi.zzBM.zzpt;
            } else {
                this.zzBp = false;
            }
            if (zzby.zzvz.get().booleanValue() && this.zzBp && this.zzBi.zzBM.zzpv != null) {
                new C0017zzd().zzfu();
            }
            if (savedInstanceState == null) {
                if (this.zzBi.zzBC != null && this.zzBu) {
                    this.zzBi.zzBC.zzaW();
                }
                if (this.zzBi.zzBJ != 1 && this.zzBi.zzBB != null) {
                    this.zzBi.zzBB.onAdClicked();
                }
            }
            this.zzAn = new zzb(this.mActivity, this.zzBi.zzBL);
            switch (this.zzBi.zzBJ) {
                case 1:
                    zzv(false);
                    return;
                case 2:
                    this.zzBj = new zzc(this.zzBi.zzBD);
                    zzv(false);
                    return;
                case 3:
                    zzv(true);
                    return;
                case 4:
                    if (this.zzBo) {
                        this.zzBr = 3;
                        this.mActivity.finish();
                        return;
                    } else {
                        if (com.google.android.gms.ads.internal.zzp.zzbs().zza(this.mActivity, this.zzBi.zzBA, this.zzBi.zzBI)) {
                            return;
                        }
                        this.zzBr = 3;
                        this.mActivity.finish();
                        return;
                    }
                default:
                    throw new zza("Could not determine ad overlay type.");
            }
        } catch (zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH(e.getMessage());
            this.zzBr = 3;
            this.mActivity.finish();
        }
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onDestroy() {
        if (this.zzoM != null) {
            this.zzAn.removeView(this.zzoM.getView());
        }
        zzeH();
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onPause() {
        zzeD();
        if (this.zzoM != null && (!this.mActivity.isFinishing() || this.zzBj == null)) {
            com.google.android.gms.ads.internal.zzp.zzbx().zza(this.zzoM.getWebView());
        }
        zzeH();
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onRestart() {
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onResume() {
        if (this.zzBi != null && this.zzBi.zzBJ == 4) {
            if (this.zzBo) {
                this.zzBr = 3;
                this.mActivity.finish();
            } else {
                this.zzBo = true;
            }
        }
        if (this.zzoM == null || this.zzoM.isDestroyed()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview does not exit. Ignoring action.");
        } else {
            com.google.android.gms.ads.internal.zzp.zzbx().zzb(this.zzoM.getWebView());
        }
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onSaveInstanceState(Bundle outBundle) {
        outBundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.zzBo);
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onStart() {
    }

    @Override // com.google.android.gms.internal.zzfk
    public void onStop() {
        zzeH();
    }

    public void setRequestedOrientation(int requestedOrientation) {
        this.mActivity.setRequestedOrientation(requestedOrientation);
    }

    public void zza(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.zzBm = new FrameLayout(this.mActivity);
        this.zzBm.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.zzBm.addView(view, -1, -1);
        this.mActivity.setContentView(this.zzBm);
        zzaE();
        this.zzBn = customViewCallback;
        this.zzBl = true;
    }

    public void zza(boolean z, boolean z2) {
        if (this.zzBk != null) {
            this.zzBk.zza(z, z2);
        }
    }

    @Override // com.google.android.gms.internal.zzfk
    public void zzaE() {
        this.zzBs = true;
    }

    public void zzeD() {
        if (this.zzBi != null && this.zzBl) {
            setRequestedOrientation(this.zzBi.orientation);
        }
        if (this.zzBm != null) {
            this.mActivity.setContentView(this.zzAn);
            zzaE();
            this.zzBm.removeAllViews();
            this.zzBm = null;
        }
        if (this.zzBn != null) {
            this.zzBn.onCustomViewHidden();
            this.zzBn = null;
        }
        this.zzBl = false;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzo
    public void zzeE() {
        this.zzBr = 1;
        this.mActivity.finish();
    }

    @Override // com.google.android.gms.internal.zzfk
    public boolean zzeF() {
        this.zzBr = 0;
        if (this.zzoM == null) {
            return true;
        }
        boolean zZzhk = this.zzoM.zzhk();
        if (zZzhk) {
            return zZzhk;
        }
        this.zzoM.zzb("onbackblocked", Collections.emptyMap());
        return zZzhk;
    }

    public void zzeG() {
        this.zzAn.removeView(this.zzBk);
        zzu(true);
    }

    protected void zzeH() {
        if (!this.mActivity.isFinishing() || this.zzBt) {
            return;
        }
        this.zzBt = true;
        if (this.zzoM != null) {
            zzv(this.zzBr);
            this.zzAn.removeView(this.zzoM.getView());
            if (this.zzBj != null) {
                this.zzoM.setContext(this.zzBj.context);
                this.zzoM.zzC(false);
                this.zzBj.zzBx.addView(this.zzoM.getView(), this.zzBj.index, this.zzBj.zzBw);
                this.zzBj = null;
            }
            this.zzoM = null;
        }
        if (this.zzBi == null || this.zzBi.zzBC == null) {
            return;
        }
        this.zzBi.zzBC.zzaV();
    }

    public void zzeI() {
        if (this.zzBq) {
            this.zzBq = false;
            zzeJ();
        }
    }

    protected void zzeJ() {
        this.zzoM.zzeJ();
    }

    public void zzu(boolean z) {
        this.zzBk = new zzm(this.mActivity, z ? 50 : 32, this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        this.zzBk.zza(z, this.zzBi.zzBG);
        this.zzAn.addView(this.zzBk, layoutParams);
    }

    protected void zzv(int i) {
        this.zzoM.zzv(i);
    }

    protected void zzv(boolean z) throws zza {
        if (!this.zzBs) {
            this.mActivity.requestWindowFeature(1);
        }
        Window window = this.mActivity.getWindow();
        if (window == null) {
            throw new zza("Invalid activity, no window available.");
        }
        if (!this.zzBp || (this.zzBi.zzBM != null && this.zzBi.zzBM.zzpu)) {
            window.setFlags(1024, 1024);
        }
        boolean zZzbY = this.zzBi.zzBD.zzhe().zzbY();
        this.zzBq = false;
        if (zZzbY) {
            if (this.zzBi.orientation == com.google.android.gms.ads.internal.zzp.zzbx().zzgG()) {
                this.zzBq = this.mActivity.getResources().getConfiguration().orientation == 1;
            } else if (this.zzBi.orientation == com.google.android.gms.ads.internal.zzp.zzbx().zzgH()) {
                this.zzBq = this.mActivity.getResources().getConfiguration().orientation == 2;
            }
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Delay onShow to next orientation change: " + this.zzBq);
        setRequestedOrientation(this.zzBi.orientation);
        if (com.google.android.gms.ads.internal.zzp.zzbx().zza(window)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Hardware acceleration on the AdActivity window enabled.");
        }
        if (this.zzBp) {
            this.zzAn.setBackgroundColor(zzBh);
        } else {
            this.zzAn.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.mActivity.setContentView(this.zzAn);
        zzaE();
        if (z) {
            this.zzoM = com.google.android.gms.ads.internal.zzp.zzbw().zza(this.mActivity, this.zzBi.zzBD.zzaN(), true, zZzbY, null, this.zzBi.zzqj);
            this.zzoM.zzhe().zzb(null, null, this.zzBi.zzBE, this.zzBi.zzBI, true, this.zzBi.zzBK, null, this.zzBi.zzBD.zzhe().zzhq(), null);
            this.zzoM.zzhe().zza(new zzja.zza() { // from class: com.google.android.gms.ads.internal.overlay.zzd.1
                @Override // com.google.android.gms.internal.zzja.zza
                public void zza(zziz zzizVar, boolean z2) {
                    zzizVar.zzeJ();
                }
            });
            if (this.zzBi.url != null) {
                this.zzoM.loadUrl(this.zzBi.url);
            } else {
                if (this.zzBi.zzBH == null) {
                    throw new zza("No URL or HTML to display in ad overlay.");
                }
                this.zzoM.loadDataWithBaseURL(this.zzBi.zzBF, this.zzBi.zzBH, "text/html", "UTF-8", null);
            }
            if (this.zzBi.zzBD != null) {
                this.zzBi.zzBD.zzc(this);
            }
        } else {
            this.zzoM = this.zzBi.zzBD;
            this.zzoM.setContext(this.mActivity);
        }
        this.zzoM.zzb(this);
        ViewParent parent = this.zzoM.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this.zzoM.getView());
        }
        if (this.zzBp) {
            this.zzoM.setBackgroundColor(zzBh);
        }
        this.zzAn.addView(this.zzoM.getView(), -1, -1);
        if (!z && !this.zzBq) {
            zzeJ();
        }
        zzu(zZzbY);
        if (this.zzoM.zzhf()) {
            zza(zZzbY, true);
        }
    }
}
