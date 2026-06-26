package com.google.android.gms.tagmanager;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* JADX INFO: loaded from: classes.dex */
class zzcb {
    private static zzcb zzaXU;
    private volatile String zzaVQ;
    private volatile zza zzaXV;
    private volatile String zzaXW;
    private volatile String zzaXX;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzcb() {
        clear();
    }

    static zzcb zzDm() {
        zzcb zzcbVar;
        synchronized (zzcb.class) {
            if (zzaXU == null) {
                zzaXU = new zzcb();
            }
            zzcbVar = zzaXU;
        }
        return zzcbVar;
    }

    private String zzeV(String str) {
        return str.split("&")[0].split("=")[1];
    }

    private String zzn(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", "");
    }

    void clear() {
        this.zzaXV = zza.NONE;
        this.zzaXW = null;
        this.zzaVQ = null;
        this.zzaXX = null;
    }

    String getContainerId() {
        return this.zzaVQ;
    }

    zza zzDn() {
        return this.zzaXV;
    }

    String zzDo() {
        return this.zzaXW;
    }

    synchronized boolean zzm(Uri uri) {
        boolean z = true;
        synchronized (this) {
            try {
                String strDecode = URLDecoder.decode(uri.toString(), "UTF-8");
                if (strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                    zzbg.v("Container preview url: " + strDecode);
                    if (strDecode.matches(".*?&gtm_debug=x$")) {
                        this.zzaXV = zza.CONTAINER_DEBUG;
                    } else {
                        this.zzaXV = zza.CONTAINER;
                    }
                    this.zzaXX = zzn(uri);
                    if (this.zzaXV == zza.CONTAINER || this.zzaXV == zza.CONTAINER_DEBUG) {
                        this.zzaXW = "/r?" + this.zzaXX;
                    }
                    this.zzaVQ = zzeV(this.zzaXX);
                } else if (!strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    zzbg.zzaH("Invalid preview uri: " + strDecode);
                    z = false;
                } else if (zzeV(uri.getQuery()).equals(this.zzaVQ)) {
                    zzbg.v("Exit preview mode for container: " + this.zzaVQ);
                    this.zzaXV = zza.NONE;
                    this.zzaXW = null;
                } else {
                    z = false;
                }
            } catch (UnsupportedEncodingException e) {
                z = false;
            }
        }
        return z;
    }
}
