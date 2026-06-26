package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.support.v7.internal.widget.ActivityChooserView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
class zzjd extends WebView implements ViewTreeObserver.OnGlobalLayoutListener, DownloadListener, zziz {
    private int zzAD;
    private int zzAE;
    private int zzAG;
    private int zzAH;
    private String zzBY;
    private Boolean zzHZ;
    private Map<String, zzdv> zzKA;
    private final zza zzKm;
    private zzja zzKn;
    private com.google.android.gms.ads.internal.overlay.zzd zzKo;
    private boolean zzKp;
    private boolean zzKq;
    private boolean zzKr;
    private boolean zzKs;
    private int zzKt;
    private boolean zzKu;
    private zzce zzKv;
    private zzce zzKw;
    private zzce zzKx;
    private zzcf zzKy;
    private com.google.android.gms.ads.internal.overlay.zzd zzKz;
    private final com.google.android.gms.ads.internal.zzd zzow;
    private final VersionInfoParcel zzpb;
    private final Object zzpd;
    private zzim zzqR;
    private final WindowManager zzri;
    private final zzan zzwL;
    private AdSizeParcel zzzm;

    @zzgr
    public static class zza extends MutableContextWrapper {
        private Activity zzJn;
        private Context zzKC;
        private Context zzqZ;

        public zza(Context context) {
            super(context);
            setBaseContext(context);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Object getSystemService(String service) {
            return this.zzKC.getSystemService(service);
        }

        @Override // android.content.MutableContextWrapper
        public void setBaseContext(Context base) {
            this.zzqZ = base.getApplicationContext();
            this.zzJn = base instanceof Activity ? (Activity) base : null;
            this.zzKC = base;
            super.setBaseContext(this.zzqZ);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public void startActivity(Intent intent) {
            if (this.zzJn != null && !zzmx.isAtLeastL()) {
                this.zzJn.startActivity(intent);
            } else {
                intent.setFlags(268435456);
                this.zzqZ.startActivity(intent);
            }
        }

        public Activity zzgZ() {
            return this.zzJn;
        }

        public Context zzha() {
            return this.zzKC;
        }
    }

    protected zzjd(zza zzaVar, AdSizeParcel adSizeParcel, boolean z, boolean z2, zzan zzanVar, VersionInfoParcel versionInfoParcel, zzcg zzcgVar, com.google.android.gms.ads.internal.zzd zzdVar) {
        super(zzaVar);
        this.zzpd = new Object();
        this.zzKu = true;
        this.zzBY = "";
        this.zzAE = -1;
        this.zzAD = -1;
        this.zzAG = -1;
        this.zzAH = -1;
        this.zzKm = zzaVar;
        this.zzzm = adSizeParcel;
        this.zzKr = z;
        this.zzKt = -1;
        this.zzwL = zzanVar;
        this.zzpb = versionInfoParcel;
        this.zzow = zzdVar;
        this.zzri = (WindowManager) getContext().getSystemService("window");
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(0);
        }
        com.google.android.gms.ads.internal.zzp.zzbv().zza(zzaVar, versionInfoParcel.zzJu, settings);
        com.google.android.gms.ads.internal.zzp.zzbx().zza(getContext(), settings);
        setDownloadListener(this);
        zzhz();
        if (zzmx.zzqz()) {
            addJavascriptInterface(new zzje(this), "googleAdsJsInterface");
        }
        this.zzqR = new zzim(this.zzKm.zzgZ(), this, null);
        zzd(zzcgVar);
    }

    static zzjd zzb(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, zzan zzanVar, VersionInfoParcel versionInfoParcel, zzcg zzcgVar, com.google.android.gms.ads.internal.zzd zzdVar) {
        return new zzjd(new zza(context), adSizeParcel, z, z2, zzanVar, versionInfoParcel, zzcgVar, zzdVar);
    }

    private void zzd(zzcg zzcgVar) {
        zzhD();
        this.zzKy = new zzcf(new zzcg(true, "make_wv", this.zzzm.zzte));
        this.zzKy.zzdm().zzc(zzcgVar);
        this.zzKw = zzcc.zzb(this.zzKy.zzdm());
        this.zzKy.zza("native:view_create", this.zzKw);
        this.zzKx = null;
        this.zzKv = null;
    }

    private void zzhA() {
        synchronized (this.zzpd) {
            if (!this.zzKs) {
                com.google.android.gms.ads.internal.zzp.zzbx().zzm(this);
            }
            this.zzKs = true;
        }
    }

    private void zzhB() {
        synchronized (this.zzpd) {
            if (this.zzKs) {
                com.google.android.gms.ads.internal.zzp.zzbx().zzl(this);
            }
            this.zzKs = false;
        }
    }

    private void zzhC() {
        synchronized (this.zzpd) {
            if (this.zzKA != null) {
                Iterator<zzdv> it = this.zzKA.values().iterator();
                while (it.hasNext()) {
                    it.next().release();
                }
            }
        }
    }

    private void zzhD() {
        zzcg zzcgVarZzdm;
        if (this.zzKy == null || (zzcgVarZzdm = this.zzKy.zzdm()) == null || com.google.android.gms.ads.internal.zzp.zzby().zzgo() == null) {
            return;
        }
        com.google.android.gms.ads.internal.zzp.zzby().zzgo().zza(zzcgVarZzdm);
    }

    private void zzhy() {
        synchronized (this.zzpd) {
            this.zzHZ = com.google.android.gms.ads.internal.zzp.zzby().zzgs();
            if (this.zzHZ == null) {
                try {
                    evaluateJavascript("(function(){})()", null);
                    zzb((Boolean) true);
                } catch (IllegalStateException e) {
                    zzb((Boolean) false);
                }
            }
        }
    }

    private void zzhz() {
        synchronized (this.zzpd) {
            if (this.zzKr || this.zzzm.zztf) {
                if (Build.VERSION.SDK_INT < 14) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaF("Disabling hardware acceleration on an overlay.");
                    zzhA();
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaF("Enabling hardware acceleration on an overlay.");
                    zzhB();
                }
            } else if (Build.VERSION.SDK_INT < 18) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Disabling hardware acceleration on an AdView.");
                zzhA();
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.zzaF("Enabling hardware acceleration on an AdView.");
                zzhB();
            }
        }
    }

    @Override // android.webkit.WebView, com.google.android.gms.internal.zziz
    public void destroy() {
        synchronized (this.zzpd) {
            zzhD();
            this.zzqR.zzgP();
            if (this.zzKo != null) {
                this.zzKo.close();
                this.zzKo.onDestroy();
                this.zzKo = null;
            }
            this.zzKn.reset();
            if (this.zzKq) {
                return;
            }
            com.google.android.gms.ads.internal.zzp.zzbI().zza(this);
            zzhC();
            this.zzKq = true;
            com.google.android.gms.ads.internal.util.client.zzb.v("Initiating WebView self destruct sequence in 3...");
            this.zzKn.zzhs();
        }
    }

    @Override // android.webkit.WebView
    public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        synchronized (this.zzpd) {
            if (!isDestroyed()) {
                super.evaluateJavascript(script, resultCallback);
                return;
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview is destroyed. Ignoring action.");
            if (resultCallback != null) {
                resultCallback.onReceiveValue(null);
            }
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public String getRequestId() {
        String str;
        synchronized (this.zzpd) {
            str = this.zzBY;
        }
        return str;
    }

    @Override // com.google.android.gms.internal.zziz
    public int getRequestedOrientation() {
        int i;
        synchronized (this.zzpd) {
            i = this.zzKt;
        }
        return i;
    }

    @Override // com.google.android.gms.internal.zziz
    public View getView() {
        return this;
    }

    @Override // com.google.android.gms.internal.zziz
    public WebView getWebView() {
        return this;
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean isDestroyed() {
        boolean z;
        synchronized (this.zzpd) {
            z = this.zzKq;
        }
        return z;
    }

    @Override // android.webkit.WebView, com.google.android.gms.internal.zziz
    public void loadData(String data, String mimeType, String encoding) {
        synchronized (this.zzpd) {
            if (isDestroyed()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview is destroyed. Ignoring action.");
            } else {
                super.loadData(data, mimeType, encoding);
            }
        }
    }

    @Override // android.webkit.WebView, com.google.android.gms.internal.zziz
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        synchronized (this.zzpd) {
            if (isDestroyed()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview is destroyed. Ignoring action.");
            } else {
                super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
            }
        }
    }

    @Override // android.webkit.WebView, com.google.android.gms.internal.zziz
    public void loadUrl(String uri) {
        synchronized (this.zzpd) {
            if (isDestroyed()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview is destroyed. Ignoring action.");
            } else {
                try {
                    super.loadUrl(uri);
                } catch (Throwable th) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not call loadUrl. " + th);
                }
            }
        }
    }

    @Override // android.webkit.WebView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        synchronized (this.zzpd) {
            super.onAttachedToWindow();
            if (!isDestroyed()) {
                this.zzqR.onAttachedToWindow();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        synchronized (this.zzpd) {
            if (!isDestroyed()) {
                this.zzqR.onDetachedFromWindow();
            }
            super.onDetachedFromWindow();
        }
    }

    @Override // android.webkit.DownloadListener
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long size) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), mimeType);
            com.google.android.gms.ads.internal.zzp.zzbv().zzb(getContext(), intent);
        } catch (ActivityNotFoundException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Couldn't find an Activity to view url/mimetype: " + url + " / " + mimeType);
        }
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (isDestroyed()) {
            return;
        }
        if (Build.VERSION.SDK_INT == 21 && canvas.isHardwareAccelerated() && !isAttachedToWindow()) {
            return;
        }
        super.onDraw(canvas);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        boolean zZzhx = zzhx();
        com.google.android.gms.ads.internal.overlay.zzd zzdVarZzhc = zzhc();
        if (zzdVarZzhc == null || !zZzhx) {
            return;
        }
        zzdVarZzhc.zzeI();
    }

    @Override // android.webkit.WebView, android.widget.AbsoluteLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        synchronized (this.zzpd) {
            if (isDestroyed()) {
                setMeasuredDimension(0, 0);
                return;
            }
            if (isInEditMode() || this.zzKr || this.zzzm.zzth || this.zzzm.zzti) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                return;
            }
            if (this.zzzm.zztf) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                this.zzri.getDefaultDisplay().getMetrics(displayMetrics);
                setMeasuredDimension(displayMetrics.widthPixels, displayMetrics.heightPixels);
                return;
            }
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
            int i2 = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
            if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
                i = size2;
            }
            if (this.zzzm.widthPixels > i2 || this.zzzm.heightPixels > i) {
                float f = this.zzKm.getResources().getDisplayMetrics().density;
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Not enough space to show ad. Needs " + ((int) (this.zzzm.widthPixels / f)) + "x" + ((int) (this.zzzm.heightPixels / f)) + " dp, but only has " + ((int) (size / f)) + "x" + ((int) (size2 / f)) + " dp.");
                if (getVisibility() != 8) {
                    setVisibility(4);
                }
                setMeasuredDimension(0, 0);
            } else {
                if (getVisibility() != 8) {
                    setVisibility(0);
                }
                setMeasuredDimension(this.zzzm.widthPixels, this.zzzm.heightPixels);
            }
        }
    }

    @Override // android.webkit.WebView
    public void onPause() {
        if (isDestroyed()) {
            return;
        }
        try {
            if (zzmx.zzqu()) {
                super.onPause();
            }
        } catch (Exception e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Could not pause webview.", e);
        }
    }

    @Override // android.webkit.WebView
    public void onResume() {
        if (isDestroyed()) {
            return;
        }
        try {
            if (zzmx.zzqu()) {
                super.onResume();
            }
        } catch (Exception e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Could not resume webview.", e);
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.zzwL != null) {
            this.zzwL.zza(event);
        }
        if (isDestroyed()) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override // com.google.android.gms.internal.zziz
    public void setContext(Context context) {
        this.zzKm.setBaseContext(context);
        this.zzqR.zzk(this.zzKm.zzgZ());
    }

    @Override // com.google.android.gms.internal.zziz
    public void setRequestedOrientation(int requestedOrientation) {
        synchronized (this.zzpd) {
            this.zzKt = requestedOrientation;
            if (this.zzKo != null) {
                this.zzKo.setRequestedOrientation(this.zzKt);
            }
        }
    }

    @Override // android.webkit.WebView, com.google.android.gms.internal.zziz
    public void setWebViewClient(WebViewClient webViewClient) {
        super.setWebViewClient(webViewClient);
        if (webViewClient instanceof zzja) {
            this.zzKn = (zzja) webViewClient;
        }
    }

    @Override // android.webkit.WebView, com.google.android.gms.internal.zziz
    public void stopLoading() {
        if (isDestroyed()) {
            return;
        }
        try {
            super.stopLoading();
        } catch (Exception e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Could not stop loading webview.", e);
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzC(boolean z) {
        synchronized (this.zzpd) {
            this.zzKr = z;
            zzhz();
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzD(boolean z) {
        synchronized (this.zzpd) {
            if (this.zzKo != null) {
                this.zzKo.zza(this.zzKn.zzbY(), z);
            } else {
                this.zzKp = z;
            }
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzE(boolean z) {
        synchronized (this.zzpd) {
            this.zzKu = z;
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(Context context, AdSizeParcel adSizeParcel, zzcg zzcgVar) {
        synchronized (this.zzpd) {
            this.zzqR.zzgP();
            setContext(context);
            this.zzKo = null;
            this.zzzm = adSizeParcel;
            this.zzKr = false;
            this.zzKp = false;
            this.zzBY = "";
            this.zzKt = -1;
            com.google.android.gms.ads.internal.zzp.zzbx().zzb(this);
            loadUrl("about:blank");
            this.zzKn.reset();
            setOnTouchListener(null);
            setOnClickListener(null);
            this.zzKu = true;
            zzd(zzcgVar);
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(AdSizeParcel adSizeParcel) {
        synchronized (this.zzpd) {
            this.zzzm = adSizeParcel;
            requestLayout();
        }
    }

    @Override // com.google.android.gms.internal.zzaw
    public void zza(zzaz zzazVar, boolean z) {
        HashMap map = new HashMap();
        map.put("isVisible", z ? "1" : "0");
        zzb("onAdVisibilityChanged", map);
    }

    protected void zza(String str, ValueCallback<String> valueCallback) {
        synchronized (this.zzpd) {
            if (isDestroyed()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview is destroyed. Ignoring action.");
                if (valueCallback != null) {
                    valueCallback.onReceiveValue(null);
                }
            } else {
                evaluateJavascript(str, valueCallback);
            }
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(String str, String str2) {
        zzaM(str + "(" + str2 + ");");
    }

    @Override // com.google.android.gms.internal.zziz
    public void zza(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        zza(str, jSONObject.toString());
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzaI(String str) {
        synchronized (this.zzpd) {
            try {
                super.loadUrl(str);
            } catch (Throwable th) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not call loadUrl. " + th);
            }
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzaJ(String str) {
        synchronized (this.zzpd) {
            if (str == null) {
                str = "";
            }
            this.zzBY = str;
        }
    }

    protected void zzaL(String str) {
        synchronized (this.zzpd) {
            if (isDestroyed()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("The webview is destroyed. Ignoring action.");
            } else {
                loadUrl(str);
            }
        }
    }

    protected void zzaM(String str) {
        if (!zzmx.zzqB()) {
            zzaL("javascript:" + str);
            return;
        }
        if (zzgs() == null) {
            zzhy();
        }
        if (zzgs().booleanValue()) {
            zza(str, (ValueCallback<String>) null);
        } else {
            zzaL("javascript:" + str);
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public AdSizeParcel zzaN() {
        AdSizeParcel adSizeParcel;
        synchronized (this.zzpd) {
            adSizeParcel = this.zzzm;
        }
        return adSizeParcel;
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzb(com.google.android.gms.ads.internal.overlay.zzd zzdVar) {
        synchronized (this.zzpd) {
            this.zzKo = zzdVar;
        }
    }

    void zzb(Boolean bool) {
        this.zzHZ = bool;
        com.google.android.gms.ads.internal.zzp.zzby().zzb(bool);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzb(String str, Map<String, ?> map) {
        try {
            zzb(str, com.google.android.gms.ads.internal.zzp.zzbv().zzz(map));
        } catch (JSONException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not convert parameters to JSON.");
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzb(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String string = jSONObject.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("AFMA_ReceiveMessage('");
        sb.append(str);
        sb.append("'");
        sb.append(",");
        sb.append(string);
        sb.append(");");
        com.google.android.gms.ads.internal.util.client.zzb.v("Dispatching AFMA event: " + sb.toString());
        zzaM(sb.toString());
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzc(com.google.android.gms.ads.internal.overlay.zzd zzdVar) {
        synchronized (this.zzpd) {
            this.zzKz = zzdVar;
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzeJ() {
        if (this.zzKv != null) {
            zzcc.zza(this.zzKy.zzdm(), this.zzKx, "aes");
            this.zzKv = zzcc.zzb(this.zzKy.zzdm());
            this.zzKy.zza("native:view_show", this.zzKx);
        }
        HashMap map = new HashMap(1);
        map.put("version", this.zzpb.zzJu);
        zzb("onshow", map);
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzgY() {
        HashMap map = new HashMap(1);
        map.put("version", this.zzpb.zzJu);
        zzb("onhide", map);
    }

    @Override // com.google.android.gms.internal.zziz
    public Activity zzgZ() {
        return this.zzKm.zzgZ();
    }

    Boolean zzgs() {
        Boolean bool;
        synchronized (this.zzpd) {
            bool = this.zzHZ;
        }
        return bool;
    }

    @Override // com.google.android.gms.internal.zziz
    public Context zzha() {
        return this.zzKm.zzha();
    }

    @Override // com.google.android.gms.internal.zziz
    public com.google.android.gms.ads.internal.zzd zzhb() {
        return this.zzow;
    }

    @Override // com.google.android.gms.internal.zziz
    public com.google.android.gms.ads.internal.overlay.zzd zzhc() {
        com.google.android.gms.ads.internal.overlay.zzd zzdVar;
        synchronized (this.zzpd) {
            zzdVar = this.zzKo;
        }
        return zzdVar;
    }

    @Override // com.google.android.gms.internal.zziz
    public com.google.android.gms.ads.internal.overlay.zzd zzhd() {
        com.google.android.gms.ads.internal.overlay.zzd zzdVar;
        synchronized (this.zzpd) {
            zzdVar = this.zzKz;
        }
        return zzdVar;
    }

    @Override // com.google.android.gms.internal.zziz
    public zzja zzhe() {
        return this.zzKn;
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean zzhf() {
        return this.zzKp;
    }

    @Override // com.google.android.gms.internal.zziz
    public zzan zzhg() {
        return this.zzwL;
    }

    @Override // com.google.android.gms.internal.zziz
    public VersionInfoParcel zzhh() {
        return this.zzpb;
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean zzhi() {
        boolean z;
        synchronized (this.zzpd) {
            z = this.zzKr;
        }
        return z;
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzhj() {
        synchronized (this.zzpd) {
            com.google.android.gms.ads.internal.util.client.zzb.v("Destroying WebView!");
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzjd.1
                @Override // java.lang.Runnable
                public void run() {
                    zzjd.super.destroy();
                }
            });
        }
    }

    @Override // com.google.android.gms.internal.zziz
    public boolean zzhk() {
        boolean z;
        synchronized (this.zzpd) {
            z = this.zzKu;
        }
        return z;
    }

    @Override // com.google.android.gms.internal.zziz
    public zziy zzhl() {
        return null;
    }

    @Override // com.google.android.gms.internal.zziz
    public zzce zzhm() {
        return this.zzKx;
    }

    @Override // com.google.android.gms.internal.zziz
    public zzcf zzhn() {
        return this.zzKy;
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzho() {
        this.zzqR.zzgO();
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzhp() {
        if (this.zzKx != null || "about:blank".equals(getUrl())) {
            return;
        }
        this.zzKx = zzcc.zzb(this.zzKy.zzdm());
        this.zzKy.zza("native:view_load", this.zzKx);
    }

    public boolean zzhx() {
        int iZzb;
        int iZzb2;
        if (!zzhe().zzbY()) {
            return false;
        }
        DisplayMetrics displayMetricsZza = com.google.android.gms.ads.internal.zzp.zzbv().zza(this.zzri);
        int iZzb3 = com.google.android.gms.ads.internal.client.zzl.zzcF().zzb(displayMetricsZza, displayMetricsZza.widthPixels);
        int iZzb4 = com.google.android.gms.ads.internal.client.zzl.zzcF().zzb(displayMetricsZza, displayMetricsZza.heightPixels);
        Activity activityZzgZ = zzgZ();
        if (activityZzgZ == null || activityZzgZ.getWindow() == null) {
            iZzb = iZzb4;
            iZzb2 = iZzb3;
        } else {
            int[] iArrZzg = com.google.android.gms.ads.internal.zzp.zzbv().zzg(activityZzgZ);
            iZzb2 = com.google.android.gms.ads.internal.client.zzl.zzcF().zzb(displayMetricsZza, iArrZzg[0]);
            iZzb = com.google.android.gms.ads.internal.client.zzl.zzcF().zzb(displayMetricsZza, iArrZzg[1]);
        }
        if (this.zzAD == iZzb3 && this.zzAE == iZzb4 && this.zzAG == iZzb2 && this.zzAH == iZzb) {
            return false;
        }
        boolean z = (this.zzAD == iZzb3 && this.zzAE == iZzb4) ? false : true;
        this.zzAD = iZzb3;
        this.zzAE = iZzb4;
        this.zzAG = iZzb2;
        this.zzAH = iZzb;
        new zzfh(this).zza(iZzb3, iZzb4, iZzb2, iZzb, displayMetricsZza.density, this.zzri.getDefaultDisplay().getRotation());
        return z;
    }

    @Override // com.google.android.gms.internal.zziz
    public void zzv(int i) {
        HashMap map = new HashMap(2);
        map.put("closetype", String.valueOf(i));
        map.put("version", this.zzpb.zzJu);
        zzb("onhide", map);
    }
}
