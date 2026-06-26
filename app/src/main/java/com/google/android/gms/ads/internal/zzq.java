package com.google.android.gms.ads.internal;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ViewSwitcher;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzu;
import com.google.android.gms.ads.internal.client.zzv;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzan;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzck;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzcx;
import com.google.android.gms.internal.zzcy;
import com.google.android.gms.internal.zzcz;
import com.google.android.gms.internal.zzfs;
import com.google.android.gms.internal.zzfw;
import com.google.android.gms.internal.zzgh;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzht;
import com.google.android.gms.internal.zzhx;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzif;
import com.google.android.gms.internal.zzik;
import com.google.android.gms.internal.zzim;
import com.google.android.gms.internal.zziz;
import com.google.android.gms.internal.zzmi;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzq implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    public final Context context;
    boolean zzpt;
    zzmi<String, zzcz> zzqA;
    NativeAdOptionsParcel zzqB;
    zzck zzqC;
    List<String> zzqD;
    com.google.android.gms.ads.internal.purchase.zzk zzqE;
    public zzhx zzqF;
    View zzqG;
    public int zzqH;
    boolean zzqI;
    private HashSet<zzht> zzqJ;
    private int zzqK;
    private int zzqL;
    private zzik zzqM;
    private boolean zzqN;
    private boolean zzqO;
    private boolean zzqP;
    final String zzqg;
    public String zzqh;
    final zzan zzqi;
    public final VersionInfoParcel zzqj;
    zza zzqk;
    public zzhz zzql;
    public zzgh zzqm;
    public AdSizeParcel zzqn;
    public zzhs zzqo;
    public zzhs.zza zzqp;
    public zzht zzqq;
    com.google.android.gms.ads.internal.client.zzn zzqr;
    com.google.android.gms.ads.internal.client.zzo zzqs;
    zzu zzqt;
    zzv zzqu;
    zzfs zzqv;
    zzfw zzqw;
    zzcw zzqx;
    zzcx zzqy;
    zzmi<String, zzcy> zzqz;

    public static class zza extends ViewSwitcher {
        private final zzif zzqQ;
        private final zzim zzqR;

        public zza(Context context, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
            super(context);
            this.zzqQ = new zzif(context);
            if (!(context instanceof Activity)) {
                this.zzqR = null;
            } else {
                this.zzqR = new zzim((Activity) context, onGlobalLayoutListener, onScrollChangedListener);
                this.zzqR.zzgO();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.zzqR != null) {
                this.zzqR.onAttachedToWindow();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.zzqR != null) {
                this.zzqR.onDetachedFromWindow();
            }
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.zzqQ.zze(event);
            return false;
        }

        @Override // android.widget.ViewAnimator, android.view.ViewGroup
        public void removeAllViews() {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= getChildCount()) {
                    break;
                }
                KeyEvent.Callback childAt = getChildAt(i2);
                if (childAt != null && (childAt instanceof zziz)) {
                    arrayList.add((zziz) childAt);
                }
                i = i2 + 1;
            }
            super.removeAllViews();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((zziz) it.next()).destroy();
            }
        }

        public void zzbP() {
            com.google.android.gms.ads.internal.util.client.zzb.v("Disable position monitoring on adFrame.");
            if (this.zzqR != null) {
                this.zzqR.zzgP();
            }
        }

        public zzif zzbT() {
            return this.zzqQ;
        }
    }

    public zzq(Context context, AdSizeParcel adSizeParcel, String str, VersionInfoParcel versionInfoParcel) {
        this(context, adSizeParcel, str, versionInfoParcel, null);
    }

    zzq(Context context, AdSizeParcel adSizeParcel, String str, VersionInfoParcel versionInfoParcel, zzan zzanVar) {
        this.zzqF = null;
        this.zzqG = null;
        this.zzqH = 0;
        this.zzqI = false;
        this.zzpt = false;
        this.zzqJ = null;
        this.zzqK = -1;
        this.zzqL = -1;
        this.zzqN = true;
        this.zzqO = true;
        this.zzqP = false;
        zzby.initialize(context);
        if (zzp.zzby().zzgo() != null) {
            List<String> listZzdf = zzby.zzdf();
            if (versionInfoParcel.zzJv != 0) {
                listZzdf.add(Integer.toString(versionInfoParcel.zzJv));
            }
            zzp.zzby().zzgo().zzb(listZzdf);
        }
        this.zzqg = UUID.randomUUID().toString();
        if (adSizeParcel.zztf || adSizeParcel.zzth) {
            this.zzqk = null;
        } else {
            this.zzqk = new zza(context, this, this);
            this.zzqk.setMinimumWidth(adSizeParcel.widthPixels);
            this.zzqk.setMinimumHeight(adSizeParcel.heightPixels);
            this.zzqk.setVisibility(4);
        }
        this.zzqn = adSizeParcel;
        this.zzqh = str;
        this.context = context;
        this.zzqj = versionInfoParcel;
        this.zzqi = zzanVar == null ? new zzan(new zzh(this)) : zzanVar;
        this.zzqM = new zzik(200L);
        this.zzqA = new zzmi<>();
    }

    private void zzbQ() {
        View viewFindViewById = this.zzqk.getRootView().findViewById(R.id.content);
        if (viewFindViewById == null) {
            return;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        this.zzqk.getGlobalVisibleRect(rect);
        viewFindViewById.getGlobalVisibleRect(rect2);
        if (rect.top != rect2.top) {
            this.zzqN = false;
        }
        if (rect.bottom != rect2.bottom) {
            this.zzqO = false;
        }
    }

    private void zze(boolean z) {
        if (this.zzqk == null || this.zzqo == null || this.zzqo.zzBD == null) {
            return;
        }
        if (!z || this.zzqM.tryAcquire()) {
            if (this.zzqo.zzBD.zzhe().zzbY()) {
                int[] iArr = new int[2];
                this.zzqk.getLocationOnScreen(iArr);
                int iZzc = com.google.android.gms.ads.internal.client.zzl.zzcF().zzc(this.context, iArr[0]);
                int iZzc2 = com.google.android.gms.ads.internal.client.zzl.zzcF().zzc(this.context, iArr[1]);
                if (iZzc != this.zzqK || iZzc2 != this.zzqL) {
                    this.zzqK = iZzc;
                    this.zzqL = iZzc2;
                    this.zzqo.zzBD.zzhe().zza(this.zzqK, this.zzqL, z ? false : true);
                }
            }
            zzbQ();
        }
    }

    public void destroy() {
        zzbP();
        this.zzqs = null;
        this.zzqt = null;
        this.zzqw = null;
        this.zzqv = null;
        this.zzqC = null;
        this.zzqu = null;
        zzf(false);
        if (this.zzqk != null) {
            this.zzqk.removeAllViews();
        }
        zzbK();
        zzbM();
        this.zzqo = null;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        zze(false);
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        zze(true);
        this.zzqP = true;
    }

    public void zza(HashSet<zzht> hashSet) {
        this.zzqJ = hashSet;
    }

    public HashSet<zzht> zzbJ() {
        return this.zzqJ;
    }

    public void zzbK() {
        if (this.zzqo == null || this.zzqo.zzBD == null) {
            return;
        }
        this.zzqo.zzBD.destroy();
    }

    public void zzbL() {
        if (this.zzqo == null || this.zzqo.zzBD == null) {
            return;
        }
        this.zzqo.zzBD.stopLoading();
    }

    public void zzbM() {
        if (this.zzqo == null || this.zzqo.zzzv == null) {
            return;
        }
        try {
            this.zzqo.zzzv.destroy();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not destroy mediation adapter.");
        }
    }

    public boolean zzbN() {
        return this.zzqH == 0;
    }

    public boolean zzbO() {
        return this.zzqH == 1;
    }

    public void zzbP() {
        if (this.zzqk != null) {
            this.zzqk.zzbP();
        }
    }

    public String zzbR() {
        return (this.zzqN && this.zzqO) ? "" : this.zzqN ? this.zzqP ? "top-scrollable" : "top-locked" : this.zzqO ? this.zzqP ? "bottom-scrollable" : "bottom-locked" : "";
    }

    public void zzbS() {
        this.zzqq.zzl(this.zzqo.zzHz);
        this.zzqq.zzm(this.zzqo.zzHA);
        this.zzqq.zzy(this.zzqn.zztf);
        this.zzqq.zzz(this.zzqo.zzEK);
    }

    public void zzf(boolean z) {
        if (this.zzqH == 0) {
            zzbL();
        }
        if (this.zzql != null) {
            this.zzql.cancel();
        }
        if (this.zzqm != null) {
            this.zzqm.cancel();
        }
        if (z) {
            this.zzqo = null;
        }
    }
}
