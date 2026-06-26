package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzih;
import com.google.android.gms.internal.zzip;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzgm implements Callable<zzhs> {
    private static final long zzDE = TimeUnit.SECONDS.toMillis(60);
    private final Context mContext;
    private final zzih zzDF;
    private final com.google.android.gms.ads.internal.zzn zzDG;
    private final zzbc zzDH;
    private final zzhs.zza zzDe;
    private final zzan zzwL;
    private final Object zzpd = new Object();
    private boolean zzDI = false;
    private int zzDv = -2;
    private List<String> zzDJ = null;

    public interface zza<T extends zzh.zza> {
        T zza(zzgm zzgmVar, JSONObject jSONObject) throws ExecutionException, JSONException, InterruptedException;
    }

    class zzb {
        public zzdk zzDZ;

        zzb() {
        }
    }

    public zzgm(Context context, com.google.android.gms.ads.internal.zzn zznVar, zzbc zzbcVar, zzih zzihVar, zzan zzanVar, zzhs.zza zzaVar) {
        this.mContext = context;
        this.zzDG = zznVar;
        this.zzDF = zzihVar;
        this.zzDH = zzbcVar;
        this.zzDe = zzaVar;
        this.zzwL = zzanVar;
    }

    private zzh.zza zza(zzbb zzbbVar, zza zzaVar, JSONObject jSONObject) throws ExecutionException, JSONException, InterruptedException {
        if (zzfD()) {
            return null;
        }
        String[] strArrZzc = zzc(jSONObject.getJSONObject("tracking_urls_and_actions"), "impression_tracking_urls");
        this.zzDJ = strArrZzc == null ? null : Arrays.asList(strArrZzc);
        zzh.zza zzaVarZza = zzaVar.zza(this, jSONObject);
        if (zzaVarZza == null) {
            com.google.android.gms.ads.internal.util.client.zzb.e("Failed to retrieve ad assets.");
            return null;
        }
        zzaVarZza.zza(new com.google.android.gms.ads.internal.formats.zzh(this.mContext, this.zzDG, zzbbVar, this.zzwL, jSONObject, zzaVarZza, this.zzDe.zzHC.zzqj));
        return zzaVarZza;
    }

    private zzhs zza(zzh.zza zzaVar) {
        int i;
        synchronized (this.zzpd) {
            i = this.zzDv;
            if (zzaVar == null && this.zzDv == -2) {
                i = 0;
            }
        }
        return new zzhs(this.zzDe.zzHC.zzEn, null, this.zzDe.zzHD.zzyY, i, this.zzDe.zzHD.zzyZ, this.zzDJ, this.zzDe.zzHD.orientation, this.zzDe.zzHD.zzzc, this.zzDe.zzHC.zzEq, false, null, null, null, null, null, 0L, this.zzDe.zzqn, this.zzDe.zzHD.zzEJ, this.zzDe.zzHz, this.zzDe.zzHA, this.zzDe.zzHD.zzEP, this.zzDe.zzHw, i != -2 ? null : zzaVar);
    }

    private zziq<com.google.android.gms.ads.internal.formats.zzc> zza(JSONObject jSONObject, final boolean z, boolean z2) throws JSONException {
        final String string = z ? jSONObject.getString("url") : jSONObject.optString("url");
        final double dOptDouble = jSONObject.optDouble("scale", 1.0d);
        if (!TextUtils.isEmpty(string)) {
            return z2 ? new zzio(new com.google.android.gms.ads.internal.formats.zzc(null, Uri.parse(string), dOptDouble)) : this.zzDF.zza(string, new zzih.zza<com.google.android.gms.ads.internal.formats.zzc>() { // from class: com.google.android.gms.internal.zzgm.5
                @Override // com.google.android.gms.internal.zzih.zza
                /* JADX INFO: renamed from: zzfE, reason: merged with bridge method [inline-methods] */
                public com.google.android.gms.ads.internal.formats.zzc zzfF() {
                    zzgm.this.zza(2, z);
                    return null;
                }

                @Override // com.google.android.gms.internal.zzih.zza
                /* JADX INFO: renamed from: zzg, reason: merged with bridge method [inline-methods] */
                public com.google.android.gms.ads.internal.formats.zzc zzh(InputStream inputStream) {
                    byte[] bArrZzk;
                    try {
                        bArrZzk = zzmt.zzk(inputStream);
                    } catch (IOException e) {
                        bArrZzk = null;
                    }
                    if (bArrZzk == null) {
                        zzgm.this.zza(2, z);
                        return null;
                    }
                    Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrZzk, 0, bArrZzk.length);
                    if (bitmapDecodeByteArray == null) {
                        zzgm.this.zza(2, z);
                        return null;
                    }
                    bitmapDecodeByteArray.setDensity((int) (160.0d * dOptDouble));
                    return new com.google.android.gms.ads.internal.formats.zzc(new BitmapDrawable(Resources.getSystem(), bitmapDecodeByteArray), Uri.parse(string), dOptDouble);
                }
            });
        }
        zza(0, z);
        return new zzio(null);
    }

    private void zza(zzh.zza zzaVar, zzbb zzbbVar) {
        if (zzaVar instanceof com.google.android.gms.ads.internal.formats.zzf) {
            final com.google.android.gms.ads.internal.formats.zzf zzfVar = (com.google.android.gms.ads.internal.formats.zzf) zzaVar;
            zzb zzbVar = new zzb();
            zzdk zzdkVar = new zzdk() { // from class: com.google.android.gms.internal.zzgm.3
                @Override // com.google.android.gms.internal.zzdk
                public void zza(zziz zzizVar, Map<String, String> map) {
                    zzgm.this.zzb(zzfVar, map.get("asset"));
                }
            };
            zzbVar.zzDZ = zzdkVar;
            zzbbVar.zza("/nativeAdCustomClick", zzdkVar);
        }
    }

    private Integer zzb(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt("b")));
        } catch (JSONException e) {
            return null;
        }
    }

    private JSONObject zzb(final zzbb zzbbVar) throws JSONException, TimeoutException {
        if (zzfD()) {
            return null;
        }
        final zzin zzinVar = new zzin();
        final zzb zzbVar = new zzb();
        zzdk zzdkVar = new zzdk() { // from class: com.google.android.gms.internal.zzgm.1
            @Override // com.google.android.gms.internal.zzdk
            public void zza(zziz zzizVar, Map<String, String> map) {
                zzbbVar.zzb("/nativeAdPreProcess", zzbVar.zzDZ);
                try {
                    String str = map.get("success");
                    if (!TextUtils.isEmpty(str)) {
                        zzinVar.zzf(new JSONObject(str).getJSONArray("ads").getJSONObject(0));
                        return;
                    }
                } catch (JSONException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Malformed native JSON response.", e);
                }
                zzgm.this.zzC(0);
                com.google.android.gms.common.internal.zzx.zza(zzgm.this.zzfD(), "Unable to set the ad state error!");
                zzinVar.zzf(null);
            }
        };
        zzbVar.zzDZ = zzdkVar;
        zzbbVar.zza("/nativeAdPreProcess", zzdkVar);
        zzbbVar.zza("google.afma.nativeAds.preProcessJsonGmsg", new JSONObject(this.zzDe.zzHD.body));
        return (JSONObject) zzinVar.get(zzDE, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzb(zzcu zzcuVar, String str) {
        try {
            zzcy zzcyVarZzr = this.zzDG.zzr(zzcuVar.getCustomTemplateId());
            if (zzcyVarZzr != null) {
                zzcyVarZzr.zza(zzcuVar, str);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call onCustomClick for asset " + str + ".", e);
        }
    }

    private String[] zzc(JSONObject jSONObject, String str) throws JSONException {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str);
        if (jSONArrayOptJSONArray == null) {
            return null;
        }
        String[] strArr = new String[jSONArrayOptJSONArray.length()];
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            strArr[i] = jSONArrayOptJSONArray.getString(i);
        }
        return strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<Drawable> zzd(List<com.google.android.gms.ads.internal.formats.zzc> list) throws RemoteException {
        ArrayList arrayList = new ArrayList();
        Iterator<com.google.android.gms.ads.internal.formats.zzc> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((Drawable) com.google.android.gms.dynamic.zze.zzp(it.next().zzdv()));
        }
        return arrayList;
    }

    private zzbb zzfC() throws ExecutionException, InterruptedException, CancellationException, TimeoutException {
        if (zzfD()) {
            return null;
        }
        zzbb zzbbVar = this.zzDH.zza(this.mContext, this.zzDe.zzHC.zzqj, (this.zzDe.zzHD.zzBF.indexOf("https") == 0 ? "https:" : "http:") + zzby.zzvj.get(), this.zzwL).get(zzDE, TimeUnit.MILLISECONDS);
        zzbbVar.zza(this.zzDG, this.zzDG, this.zzDG, this.zzDG, false, null, null, null, null);
        return zzbbVar;
    }

    public void zzC(int i) {
        synchronized (this.zzpd) {
            this.zzDI = true;
            this.zzDv = i;
        }
    }

    public zziq<com.google.android.gms.ads.internal.formats.zzc> zza(JSONObject jSONObject, String str, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, z, z2);
    }

    public List<zziq<com.google.android.gms.ads.internal.formats.zzc>> zza(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) throws JSONException {
        JSONArray jSONArray = z ? jSONObject.getJSONArray(str) : jSONObject.optJSONArray(str);
        ArrayList arrayList = new ArrayList();
        if (jSONArray == null || jSONArray.length() == 0) {
            zza(0, z);
            return arrayList;
        }
        int length = z3 ? jSONArray.length() : 1;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            arrayList.add(zza(jSONObject2, z, z2));
        }
        return arrayList;
    }

    public Future<com.google.android.gms.ads.internal.formats.zzc> zza(JSONObject jSONObject, String str, boolean z) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        boolean zOptBoolean = jSONObject2.optBoolean("require", true);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, zOptBoolean, z);
    }

    public void zza(int i, boolean z) {
        if (z) {
            zzC(i);
        }
    }

    protected zza zzd(JSONObject jSONObject) throws JSONException, TimeoutException {
        if (zzfD()) {
            return null;
        }
        String string = jSONObject.getString("template_id");
        boolean z = this.zzDe.zzHC.zzqB != null ? this.zzDe.zzHC.zzqB.zzwR : false;
        boolean z2 = this.zzDe.zzHC.zzqB != null ? this.zzDe.zzHC.zzqB.zzwT : false;
        if ("2".equals(string)) {
            return new zzgn(z, z2);
        }
        if ("1".equals(string)) {
            return new zzgo(z, z2);
        }
        if ("3".equals(string)) {
            final String string2 = jSONObject.getString("custom_template_id");
            final zzin zzinVar = new zzin();
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgm.2
                @Override // java.lang.Runnable
                public void run() {
                    zzinVar.zzf(zzgm.this.zzDG.zzbo().get(string2));
                }
            });
            if (zzinVar.get(zzDE, TimeUnit.MILLISECONDS) != null) {
                return new zzgp(z);
            }
            com.google.android.gms.ads.internal.util.client.zzb.e("No handler for custom template: " + jSONObject.getString("custom_template_id"));
        } else {
            zzC(0);
        }
        return null;
    }

    public zziq<com.google.android.gms.ads.internal.formats.zza> zze(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("attribution");
        if (jSONObjectOptJSONObject == null) {
            return new zzio(null);
        }
        final String strOptString = jSONObjectOptJSONObject.optString("text");
        final int iOptInt = jSONObjectOptJSONObject.optInt("text_size", -1);
        final Integer numZzb = zzb(jSONObjectOptJSONObject, "text_color");
        final Integer numZzb2 = zzb(jSONObjectOptJSONObject, "bg_color");
        final int iOptInt2 = jSONObjectOptJSONObject.optInt("animation_ms", 1000);
        final int iOptInt3 = jSONObjectOptJSONObject.optInt("presentation_ms", 4000);
        List<zziq<com.google.android.gms.ads.internal.formats.zzc>> arrayList = new ArrayList<>();
        if (jSONObjectOptJSONObject.optJSONArray("images") != null) {
            arrayList = zza(jSONObjectOptJSONObject, "images", false, false, true);
        } else {
            arrayList.add(zza(jSONObjectOptJSONObject, "image", false, false));
        }
        return zzip.zza(zzip.zzh(arrayList), new zzip.zza<List<com.google.android.gms.ads.internal.formats.zzc>, com.google.android.gms.ads.internal.formats.zza>() { // from class: com.google.android.gms.internal.zzgm.4
            /* JADX WARN: Removed duplicated region for block: B:6:0x0009  */
            @Override // com.google.android.gms.internal.zzip.zza
            /* JADX INFO: renamed from: zzf, reason: merged with bridge method [inline-methods] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.google.android.gms.ads.internal.formats.zza zze(java.util.List<com.google.android.gms.ads.internal.formats.zzc> r10) {
                /*
                    r9 = this;
                    r7 = 0
                    if (r10 == 0) goto L9
                    boolean r0 = r10.isEmpty()     // Catch: android.os.RemoteException -> L2b
                    if (r0 == 0) goto Lc
                L9:
                    r0 = r7
                La:
                    r7 = r0
                Lb:
                    return r7
                Lc:
                    com.google.android.gms.ads.internal.formats.zza r0 = new com.google.android.gms.ads.internal.formats.zza     // Catch: android.os.RemoteException -> L2b
                    java.lang.String r1 = r2     // Catch: android.os.RemoteException -> L2b
                    java.util.List r2 = com.google.android.gms.internal.zzgm.zze(r10)     // Catch: android.os.RemoteException -> L2b
                    java.lang.Integer r3 = r3     // Catch: android.os.RemoteException -> L2b
                    java.lang.Integer r4 = r4     // Catch: android.os.RemoteException -> L2b
                    int r5 = r5     // Catch: android.os.RemoteException -> L2b
                    if (r5 <= 0) goto L32
                    int r5 = r5     // Catch: android.os.RemoteException -> L2b
                    java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: android.os.RemoteException -> L2b
                L22:
                    int r6 = r6     // Catch: android.os.RemoteException -> L2b
                    int r8 = r7     // Catch: android.os.RemoteException -> L2b
                    int r6 = r6 + r8
                    r0.<init>(r1, r2, r3, r4, r5, r6)     // Catch: android.os.RemoteException -> L2b
                    goto La
                L2b:
                    r0 = move-exception
                    java.lang.String r1 = "Could not get attribution icon"
                    com.google.android.gms.ads.internal.util.client.zzb.zzb(r1, r0)
                    goto Lb
                L32:
                    r5 = r7
                    goto L22
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgm.AnonymousClass4.zze(java.util.List):com.google.android.gms.ads.internal.formats.zza");
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: zzfB, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.gms.internal.zzhs call() {
        /*
            r3 = this;
            com.google.android.gms.internal.zzbb r0 = r3.zzfC()     // Catch: org.json.JSONException -> L18 java.util.concurrent.TimeoutException -> L2c java.lang.InterruptedException -> L33 java.util.concurrent.ExecutionException -> L35 java.util.concurrent.CancellationException -> L37
            org.json.JSONObject r1 = r3.zzb(r0)     // Catch: org.json.JSONException -> L18 java.util.concurrent.TimeoutException -> L2c java.lang.InterruptedException -> L33 java.util.concurrent.ExecutionException -> L35 java.util.concurrent.CancellationException -> L37
            com.google.android.gms.internal.zzgm$zza r2 = r3.zzd(r1)     // Catch: org.json.JSONException -> L18 java.util.concurrent.TimeoutException -> L2c java.lang.InterruptedException -> L33 java.util.concurrent.ExecutionException -> L35 java.util.concurrent.CancellationException -> L37
            com.google.android.gms.ads.internal.formats.zzh$zza r1 = r3.zza(r0, r2, r1)     // Catch: org.json.JSONException -> L18 java.util.concurrent.TimeoutException -> L2c java.lang.InterruptedException -> L33 java.util.concurrent.ExecutionException -> L35 java.util.concurrent.CancellationException -> L37
            r3.zza(r1, r0)     // Catch: org.json.JSONException -> L18 java.util.concurrent.TimeoutException -> L2c java.lang.InterruptedException -> L33 java.util.concurrent.ExecutionException -> L35 java.util.concurrent.CancellationException -> L37
            com.google.android.gms.internal.zzhs r0 = r3.zza(r1)     // Catch: org.json.JSONException -> L18 java.util.concurrent.TimeoutException -> L2c java.lang.InterruptedException -> L33 java.util.concurrent.ExecutionException -> L35 java.util.concurrent.CancellationException -> L37
        L17:
            return r0
        L18:
            r0 = move-exception
            java.lang.String r1 = "Malformed native JSON response."
            com.google.android.gms.ads.internal.util.client.zzb.zzd(r1, r0)
        L1e:
            boolean r0 = r3.zzDI
            if (r0 != 0) goto L26
            r0 = 0
            r3.zzC(r0)
        L26:
            r0 = 0
            com.google.android.gms.internal.zzhs r0 = r3.zza(r0)
            goto L17
        L2c:
            r0 = move-exception
            java.lang.String r1 = "Timeout when loading native ad."
            com.google.android.gms.ads.internal.util.client.zzb.zzd(r1, r0)
            goto L1e
        L33:
            r0 = move-exception
            goto L1e
        L35:
            r0 = move-exception
            goto L1e
        L37:
            r0 = move-exception
            goto L1e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgm.call():com.google.android.gms.internal.zzhs");
    }

    public boolean zzfD() {
        boolean z;
        synchronized (this.zzpd) {
            z = this.zzDI;
        }
        return z;
    }
}
