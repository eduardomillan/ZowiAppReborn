package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdl implements zzdk {
    private final Context mContext;
    private final VersionInfoParcel zzpb;

    @zzgr
    static class zza {
        private final String mValue;
        private final String zzue;

        public zza(String str, String str2) {
            this.zzue = str;
            this.mValue = str2;
        }

        public String getKey() {
            return this.zzue;
        }

        public String getValue() {
            return this.mValue;
        }
    }

    @zzgr
    static class zzb {
        private final String zzxF;
        private final URL zzxG;
        private final ArrayList<zza> zzxH;
        private final String zzxI;

        public zzb(String str, URL url, ArrayList<zza> arrayList, String str2) {
            this.zzxF = str;
            this.zzxG = url;
            if (arrayList == null) {
                this.zzxH = new ArrayList<>();
            } else {
                this.zzxH = arrayList;
            }
            this.zzxI = str2;
        }

        public String zzdE() {
            return this.zzxF;
        }

        public URL zzdF() {
            return this.zzxG;
        }

        public ArrayList<zza> zzdG() {
            return this.zzxH;
        }

        public String zzdH() {
            return this.zzxI;
        }
    }

    @zzgr
    class zzc {
        private final zzd zzxJ;
        private final boolean zzxK;
        private final String zzxL;

        public zzc(boolean z, zzd zzdVar, String str) {
            this.zzxK = z;
            this.zzxJ = zzdVar;
            this.zzxL = str;
        }

        public String getReason() {
            return this.zzxL;
        }

        public boolean isSuccess() {
            return this.zzxK;
        }

        public zzd zzdI() {
            return this.zzxJ;
        }
    }

    @zzgr
    static class zzd {
        private final String zzwq;
        private final String zzxF;
        private final int zzxM;
        private final List<zza> zzxN;

        public zzd(String str, int i, List<zza> list, String str2) {
            this.zzxF = str;
            this.zzxM = i;
            if (list == null) {
                this.zzxN = new ArrayList();
            } else {
                this.zzxN = list;
            }
            this.zzwq = str2;
        }

        public String getBody() {
            return this.zzwq;
        }

        public int getResponseCode() {
            return this.zzxM;
        }

        public String zzdE() {
            return this.zzxF;
        }

        public Iterable<zza> zzdJ() {
            return this.zzxN;
        }
    }

    public zzdl(Context context, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzpb = versionInfoParcel;
    }

    public JSONObject zzX(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject();
            String strOptString = "";
            try {
                strOptString = jSONObject.optString("http_request_id");
                zzc zzcVarZza = zza(zzb(jSONObject));
                if (zzcVarZza.isSuccess()) {
                    jSONObject2.put("response", zza(zzcVarZza.zzdI()));
                    jSONObject2.put("success", true);
                } else {
                    jSONObject2.put("response", new JSONObject().put("http_request_id", strOptString));
                    jSONObject2.put("success", false);
                    jSONObject2.put("reason", zzcVarZza.getReason());
                }
                return jSONObject2;
            } catch (Exception e) {
                try {
                    jSONObject2.put("response", new JSONObject().put("http_request_id", strOptString));
                    jSONObject2.put("success", false);
                    jSONObject2.put("reason", e.toString());
                    return jSONObject2;
                } catch (JSONException e2) {
                    return jSONObject2;
                }
            }
        } catch (JSONException e3) {
            com.google.android.gms.ads.internal.util.client.zzb.e("The request is not a valid JSON.");
            try {
                return new JSONObject().put("success", false);
            } catch (JSONException e4) {
                return new JSONObject();
            }
        }
    }

    protected zzc zza(zzb zzbVar) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) zzbVar.zzdF().openConnection();
            com.google.android.gms.ads.internal.zzp.zzbv().zza(this.mContext, this.zzpb.zzJu, false, httpURLConnection);
            for (zza zzaVar : zzbVar.zzdG()) {
                httpURLConnection.addRequestProperty(zzaVar.getKey(), zzaVar.getValue());
            }
            if (!TextUtils.isEmpty(zzbVar.zzdH())) {
                httpURLConnection.setDoOutput(true);
                byte[] bytes = zzbVar.zzdH().getBytes();
                httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            }
            ArrayList arrayList = new ArrayList();
            if (httpURLConnection.getHeaderFields() != null) {
                for (Map.Entry<String, List<String>> entry : httpURLConnection.getHeaderFields().entrySet()) {
                    Iterator<String> it = entry.getValue().iterator();
                    while (it.hasNext()) {
                        arrayList.add(new zza(entry.getKey(), it.next()));
                    }
                }
            }
            return new zzc(true, new zzd(zzbVar.zzdE(), httpURLConnection.getResponseCode(), arrayList, com.google.android.gms.ads.internal.zzp.zzbv().zza(new InputStreamReader(httpURLConnection.getInputStream()))), null);
        } catch (Exception e) {
            return new zzc(false, null, e.toString());
        }
    }

    protected JSONObject zza(zzd zzdVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("http_request_id", zzdVar.zzdE());
            if (zzdVar.getBody() != null) {
                jSONObject.put("body", zzdVar.getBody());
            }
            JSONArray jSONArray = new JSONArray();
            for (zza zzaVar : zzdVar.zzdJ()) {
                jSONArray.put(new JSONObject().put("key", zzaVar.getKey()).put("value", zzaVar.getValue()));
            }
            jSONObject.put("headers", jSONArray);
            jSONObject.put("response_code", zzdVar.getResponseCode());
        } catch (JSONException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing JSON for http response.", e);
        }
        return jSONObject;
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(final zziz zzizVar, final Map<String, String> map) {
        zzic.zza(new Runnable() { // from class: com.google.android.gms.internal.zzdl.1
            @Override // java.lang.Runnable
            public void run() {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Received Http request.");
                final JSONObject jSONObjectZzX = zzdl.this.zzX((String) map.get("http_request"));
                if (jSONObjectZzX == null) {
                    com.google.android.gms.ads.internal.util.client.zzb.e("Response should not be null.");
                } else {
                    zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzdl.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            zzizVar.zzb("fetchHttpRequestCompleted", jSONObjectZzX);
                            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Dispatched http response.");
                        }
                    });
                }
            }
        });
    }

    protected zzb zzb(JSONObject jSONObject) {
        URL url;
        String strOptString = jSONObject.optString("http_request_id");
        String strOptString2 = jSONObject.optString("url");
        String strOptString3 = jSONObject.optString("post_body", null);
        try {
            url = new URL(strOptString2);
        } catch (MalformedURLException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing http request.", e);
            url = null;
        }
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("headers");
        if (jSONArrayOptJSONArray == null) {
            jSONArrayOptJSONArray = new JSONArray();
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                arrayList.add(new zza(jSONObjectOptJSONObject.optString("key"), jSONObjectOptJSONObject.optString("value")));
            }
        }
        return new zzb(strOptString, url, arrayList, strOptString3);
    }
}
