package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
class zzjc extends FrameLayout implements zziz {
    private final zziz zzKk;
    private final zziy zzKl;

    public zzjc(zziz zzizVar) {
        super(zzizVar.zzha());
        this.zzKk = zzizVar;
        this.zzKl = new zziy(zzizVar.zzha(), this, this);
        zzja zzjaVarZzhe = this.zzKk.zzhe();
        if (zzjaVarZzhe != null) {
            zzjaVarZzhe.zze(this);
        }
        addView(this.zzKk.getView());
    }

    @Override // com.google.android.gms.internal.zziz
    public void clearCache(boolean includeDiskFiles) {
        this.zzKk.clearCache(includeDiskFiles);
    }

    @Override // com.google.android.gms.internal.zziz
    public void destroy() {
        this.zzKk.destroy();
    }

    @Override // com.google.android.gms.internal.zziz
    public String getRequestId() {
        return this.zzKk.getRequestId();
    }

    @Override // com.google.android.gms.internal.zziz
    public int getRequestedOrientation() {
        return this.zzKk.getRequestedOrientation();
    }

    @Override // com.google.android.gms.internal.zziz
    public View getView() {
        return this;
    }

    @Override // com.google.android.gms.internal.zziz
    public WebView getWebView() {
        return this.zzKk.getWebView();
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean isDestroyed() {
        return this.zzKk.isDestroyed();
    }

    @Override // com.google.android.gms.internal.zziz
    public void loadData(String data, String mimeType, String encoding) {
        this.zzKk.loadData(data, mimeType, encoding);
    }

    @Override // com.google.android.gms.internal.zziz
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        this.zzKk.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override // com.google.android.gms.internal.zziz
    public void loadUrl(String url) {
        this.zzKk.loadUrl(url);
    }

    @Override // android.view.View, com.google.android.gms.internal.zziz
    public void setBackgroundColor(int color) {
        this.zzKk.setBackgroundColor(color);
    }

    @Override // com.google.android.gms.internal.zziz
    public void setContext(Context context) {
        this.zzKk.setContext(context);
    }

    @Override // android.view.View, com.google.android.gms.internal.zziz
    public void setOnClickListener(View.OnClickListener listener) {
        this.zzKk.setOnClickListener(listener);
    }

    @Override // android.view.View, com.google.android.gms.internal.zziz
    public void setOnTouchListener(View.OnTouchListener listener) {
        this.zzKk.setOnTouchListener(listener);
    }

    @Override // com.google.android.gms.internal.zziz
    public void setRequestedOrientation(int requestedOrientation) {
        this.zzKk.setRequestedOrientation(requestedOrientation);
    }

    @Override // com.google.android.gms.internal.zziz
    public void setWebChromeClient(WebChromeClient client) {
        this.zzKk.setWebChromeClient(client);
    }

    @Override // com.google.android.gms.internal.zziz
    public void setWebViewClient(WebViewClient client) {
        this.zzKk.setWebViewClient(client);
    }

    @Override // com.google.android.gms.internal.zziz
    public void stopLoading() {
        this.zzKk.stopLoading();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzC(boolean z) {
        this.zzKk.zzC(z);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzD(boolean z) {
        this.zzKk.zzD(z);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzE(boolean z) {
        this.zzKk.zzE(z);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(Context context, AdSizeParcel adSizeParcel, zzcg zzcgVar) {
        this.zzKk.zza(context, adSizeParcel, zzcgVar);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(AdSizeParcel adSizeParcel) {
        this.zzKk.zza(adSizeParcel);
    }

    @Override // com.google.android.gms.internal.zzaw
    public void zza(zzaz zzazVar, boolean z) {
        this.zzKk.zza(zzazVar, z);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(String str, String str2) {
        this.zzKk.zza(str, str2);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(String str, JSONObject jSONObject) {
        this.zzKk.zza(str, jSONObject);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzaI(String str) {
        this.zzKk.zzaI(str);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzaJ(String str) {
        this.zzKk.zzaJ(str);
    }

    @Override // com.google.android.gms.internal.zziz
    public AdSizeParcel zzaN() {
        return this.zzKk.zzaN();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzb(com.google.android.gms.ads.internal.overlay.zzd zzdVar) {
        this.zzKk.zzb(zzdVar);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzb(String str, Map<String, ?> map) {
        this.zzKk.zzb(str, map);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzb(String str, JSONObject jSONObject) {
        this.zzKk.zzb(str, jSONObject);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzc(com.google.android.gms.ads.internal.overlay.zzd zzdVar) {
        this.zzKk.zzc(zzdVar);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzeJ() {
        this.zzKk.zzeJ();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzgY() {
        this.zzKk.zzgY();
    }

    @Override // com.google.android.gms.internal.zziz
    public Activity zzgZ() {
        return this.zzKk.zzgZ();
    }

    @Override // com.google.android.gms.internal.zziz
    public Context zzha() {
        return this.zzKk.zzha();
    }

    @Override // com.google.android.gms.internal.zziz
    public com.google.android.gms.ads.internal.zzd zzhb() {
        return this.zzKk.zzhb();
    }

    @Override // com.google.android.gms.internal.zziz
    public com.google.android.gms.ads.internal.overlay.zzd zzhc() {
        return this.zzKk.zzhc();
    }

    @Override // com.google.android.gms.internal.zziz
    public com.google.android.gms.ads.internal.overlay.zzd zzhd() {
        return this.zzKk.zzhd();
    }

    @Override // com.google.android.gms.internal.zziz
    public zzja zzhe() {
        return this.zzKk.zzhe();
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean zzhf() {
        return this.zzKk.zzhf();
    }

    @Override // com.google.android.gms.internal.zziz
    public zzan zzhg() {
        return this.zzKk.zzhg();
    }

    @Override // com.google.android.gms.internal.zziz
    public VersionInfoParcel zzhh() {
        return this.zzKk.zzhh();
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean zzhi() {
        return this.zzKk.zzhi();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzhj() {
        this.zzKl.onDestroy();
        this.zzKk.zzhj();
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean zzhk() {
        return this.zzKk.zzhk();
    }

    @Override // com.google.android.gms.internal.zziz
    public zziy zzhl() {
        return this.zzKl;
    }

    @Override // com.google.android.gms.internal.zziz
    public zzce zzhm() {
        return this.zzKk.zzhm();
    }

    @Override // com.google.android.gms.internal.zziz
    public zzcf zzhn() {
        return this.zzKk.zzhn();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzho() {
        this.zzKk.zzho();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzhp() {
        this.zzKk.zzhp();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzv(int i) {
        this.zzKk.zzv(i);
    }
}
