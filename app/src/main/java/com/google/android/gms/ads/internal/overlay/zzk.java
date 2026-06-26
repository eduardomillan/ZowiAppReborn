package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.internal.zzce;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zziz;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzk extends FrameLayout implements zzh {
    private final FrameLayout zzBN;
    private final zzq zzBO;
    private zzi zzBP;
    private boolean zzBQ;
    private boolean zzBR;
    private TextView zzBS;
    private long zzBT;
    private long zzBU;
    private String zzBV;
    private final zziz zzoM;
    private String zzxZ;

    public zzk(Context context, zziz zzizVar, int i, zzcg zzcgVar, zzce zzceVar) {
        super(context);
        this.zzoM = zzizVar;
        this.zzBN = new FrameLayout(context);
        addView(this.zzBN, new FrameLayout.LayoutParams(-1, -1));
        com.google.android.gms.common.internal.zzb.zzs(zzizVar.zzhb());
        this.zzBP = zzizVar.zzhb().zzoH.zza(context, zzizVar, i, zzcgVar, zzceVar);
        if (this.zzBP != null) {
            this.zzBN.addView(this.zzBP, new FrameLayout.LayoutParams(-1, -1, 17));
        }
        this.zzBS = new TextView(context);
        this.zzBS.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        zzeY();
        this.zzBO = new zzq(this);
        this.zzBO.zzfg();
        if (this.zzBP != null) {
            this.zzBP.zza(this);
        }
        if (this.zzBP == null) {
            zzh("AdVideoUnderlay Error", "Allocating player failed.");
        }
    }

    private void zza(String str, String... strArr) {
        HashMap map = new HashMap();
        map.put("event", str);
        int length = strArr.length;
        int i = 0;
        String str2 = null;
        while (i < length) {
            String str3 = strArr[i];
            if (str2 != null) {
                map.put(str2, str3);
                str3 = null;
            }
            i++;
            str2 = str3;
        }
        this.zzoM.zzb("onVideoEvent", map);
    }

    public static void zzd(zziz zzizVar) {
        HashMap map = new HashMap();
        map.put("event", "no_video_view");
        zzizVar.zzb("onVideoEvent", map);
    }

    private void zzeY() {
        if (zzfa()) {
            return;
        }
        this.zzBN.addView(this.zzBS, new FrameLayout.LayoutParams(-1, -1));
        this.zzBN.bringChildToFront(this.zzBS);
    }

    private void zzeZ() {
        if (zzfa()) {
            this.zzBN.removeView(this.zzBS);
        }
    }

    private boolean zzfa() {
        return this.zzBS.getParent() != null;
    }

    private void zzfb() {
        if (this.zzoM.zzgZ() == null || this.zzBQ) {
            return;
        }
        this.zzBR = (this.zzoM.zzgZ().getWindow().getAttributes().flags & 128) != 0;
        if (this.zzBR) {
            return;
        }
        this.zzoM.zzgZ().getWindow().addFlags(128);
        this.zzBQ = true;
    }

    private void zzfc() {
        if (this.zzoM.zzgZ() == null || !this.zzBQ || this.zzBR) {
            return;
        }
        this.zzoM.zzgZ().getWindow().clearFlags(128);
        this.zzBQ = false;
    }

    public void destroy() {
        this.zzBO.cancel();
        if (this.zzBP != null) {
            this.zzBP.stop();
        }
        zzfc();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void onPaused() {
        zza("pause", new String[0]);
        zzfc();
    }

    public void pause() {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.pause();
    }

    public void play() {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.play();
    }

    public void seekTo(int millis) {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.seekTo(millis);
    }

    public void setMimeType(String mimeType) {
        this.zzBV = mimeType;
    }

    public void zza(float f) {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.zza(f);
    }

    public void zzan(String str) {
        this.zzxZ = str;
    }

    public void zzd(int i, int i2, int i3, int i4) {
        if (i3 == 0 || i4 == 0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i3 + 2, i4 + 2);
        layoutParams.setMargins(i - 1, i2 - 1, 0, 0);
        this.zzBN.setLayoutParams(layoutParams);
        requestLayout();
    }

    public void zzd(MotionEvent motionEvent) {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.dispatchTouchEvent(motionEvent);
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void zzeQ() {
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void zzeR() {
        if (this.zzBP != null && this.zzBU == 0) {
            zza("canplaythrough", "duration", String.valueOf(this.zzBP.getDuration() / 1000.0f), "videoWidth", String.valueOf(this.zzBP.getVideoWidth()), "videoHeight", String.valueOf(this.zzBP.getVideoHeight()));
        }
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void zzeS() {
        zzfb();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void zzeT() {
        zza("ended", new String[0]);
        zzfc();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void zzeU() {
        zzeY();
        this.zzBU = this.zzBT;
    }

    public void zzeV() {
        if (this.zzBP == null) {
            return;
        }
        if (TextUtils.isEmpty(this.zzxZ)) {
            zza("no_src", new String[0]);
        } else {
            this.zzBP.setMimeType(this.zzBV);
            this.zzBP.setVideoPath(this.zzxZ);
        }
    }

    public void zzeW() {
        if (this.zzBP == null) {
            return;
        }
        TextView textView = new TextView(this.zzBP.getContext());
        textView.setText("AdMob - " + this.zzBP.zzer());
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
        textView.setBackgroundColor(InputDeviceCompat.SOURCE_ANY);
        this.zzBN.addView(textView, new FrameLayout.LayoutParams(-2, -2, 17));
        this.zzBN.bringChildToFront(textView);
    }

    void zzeX() {
        if (this.zzBP == null) {
            return;
        }
        long currentPosition = this.zzBP.getCurrentPosition();
        if (this.zzBT == currentPosition || currentPosition <= 0) {
            return;
        }
        zzeZ();
        zza("timeupdate", "time", String.valueOf(currentPosition / 1000.0f));
        this.zzBT = currentPosition;
    }

    public void zzex() {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.zzex();
    }

    public void zzey() {
        if (this.zzBP == null) {
            return;
        }
        this.zzBP.zzey();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzh
    public void zzh(String str, String str2) {
        zza("error", "what", str, "extra", str2);
    }
}
