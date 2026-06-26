package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.comscore.utils.Constants;
import java.util.Map;
import java.util.WeakHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzds implements zzdk {
    private final Map<zziz, Integer> zzxX = new WeakHashMap();

    private static int zza(Context context, Map<String, String> map, String str, int i) {
        String str2 = map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return com.google.android.gms.ads.internal.client.zzl.zzcF().zzb(context, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not parse " + str + " in a video GMSG: " + str2);
            return i;
        }
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        int i;
        com.google.android.gms.ads.internal.overlay.zzk zzkVarZzgX;
        String str = map.get("action");
        if (str == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Action missing from video GMSG.");
            return;
        }
        if (com.google.android.gms.ads.internal.util.client.zzb.zzN(3)) {
            JSONObject jSONObject = new JSONObject(map);
            jSONObject.remove("google.afma.Notify_dt");
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Video GMSG: " + str + " " + jSONObject.toString());
        }
        if (Constants.DEFAULT_BACKGROUND_PAGE_NAME.equals(str)) {
            String str2 = map.get("color");
            if (TextUtils.isEmpty(str2)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Color parameter missing from color video GMSG.");
                return;
            }
            try {
                int color = Color.parseColor(str2);
                zziy zziyVarZzhl = zzizVar.zzhl();
                if (zziyVarZzhl == null || (zzkVarZzgX = zziyVarZzhl.zzgX()) == null) {
                    this.zzxX.put(zzizVar, Integer.valueOf(color));
                } else {
                    zzkVarZzgX.setBackgroundColor(color);
                }
                return;
            } catch (IllegalArgumentException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Invalid color parameter in video GMSG.");
                return;
            }
        }
        zziy zziyVarZzhl2 = zzizVar.zzhl();
        if (zziyVarZzhl2 == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not get underlay container for a video GMSG.");
            return;
        }
        boolean zEquals = "new".equals(str);
        boolean zEquals2 = "position".equals(str);
        if (zEquals || zEquals2) {
            Context context = zzizVar.getContext();
            int iZza = zza(context, map, "x", 0);
            int iZza2 = zza(context, map, "y", 0);
            int iZza3 = zza(context, map, "w", -1);
            int iZza4 = zza(context, map, "h", -1);
            try {
                i = Integer.parseInt(map.get("player"));
            } catch (NumberFormatException e2) {
                i = 0;
            }
            if (!zEquals || zziyVarZzhl2.zzgX() != null) {
                zziyVarZzhl2.zze(iZza, iZza2, iZza3, iZza4);
                return;
            }
            zziyVarZzhl2.zza(iZza, iZza2, iZza3, iZza4, i);
            if (this.zzxX.containsKey(zzizVar)) {
                int iIntValue = this.zzxX.get(zzizVar).intValue();
                com.google.android.gms.ads.internal.overlay.zzk zzkVarZzgX2 = zziyVarZzhl2.zzgX();
                zzkVarZzgX2.setBackgroundColor(iIntValue);
                zzkVarZzgX2.zzeW();
                return;
            }
            return;
        }
        com.google.android.gms.ads.internal.overlay.zzk zzkVarZzgX3 = zziyVarZzhl2.zzgX();
        if (zzkVarZzgX3 == null) {
            com.google.android.gms.ads.internal.overlay.zzk.zzd(zzizVar);
            return;
        }
        if ("click".equals(str)) {
            Context context2 = zzizVar.getContext();
            int iZza5 = zza(context2, map, "x", 0);
            int iZza6 = zza(context2, map, "y", 0);
            long jUptimeMillis = SystemClock.uptimeMillis();
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, iZza5, iZza6, 0);
            zzkVarZzgX3.zzd(motionEventObtain);
            motionEventObtain.recycle();
            return;
        }
        if ("currentTime".equals(str)) {
            String str3 = map.get("time");
            if (str3 == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Time parameter missing from currentTime video GMSG.");
                return;
            }
            try {
                zzkVarZzgX3.seekTo((int) (Float.parseFloat(str3) * 1000.0f));
                return;
            } catch (NumberFormatException e3) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not parse time parameter from currentTime video GMSG: " + str3);
                return;
            }
        }
        if ("hide".equals(str)) {
            zzkVarZzgX3.setVisibility(4);
            return;
        }
        if ("load".equals(str)) {
            zzkVarZzgX3.zzeV();
            return;
        }
        if ("mimetype".equals(str)) {
            zzkVarZzgX3.setMimeType(map.get("mimetype"));
            return;
        }
        if ("muted".equals(str)) {
            if (Boolean.parseBoolean(map.get("muted"))) {
                zzkVarZzgX3.zzex();
                return;
            } else {
                zzkVarZzgX3.zzey();
                return;
            }
        }
        if ("pause".equals(str)) {
            zzkVarZzgX3.pause();
            return;
        }
        if ("play".equals(str)) {
            zzkVarZzgX3.play();
            return;
        }
        if ("show".equals(str)) {
            zzkVarZzgX3.setVisibility(0);
            return;
        }
        if ("src".equals(str)) {
            zzkVarZzgX3.zzan(map.get("src"));
            return;
        }
        if (!"volume".equals(str)) {
            if ("watermark".equals(str)) {
                zzkVarZzgX3.zzeW();
                return;
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Unknown video action: " + str);
                return;
            }
        }
        String str4 = map.get("volume");
        if (str4 == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Level parameter missing from volume video GMSG.");
            return;
        }
        try {
            zzkVarZzgX3.zza(Float.parseFloat(str4));
        } catch (NumberFormatException e4) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not parse volume parameter from volume video GMSG: " + str4);
        }
    }
}
