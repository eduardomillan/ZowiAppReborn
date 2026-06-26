package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzpc extends com.google.android.gms.measurement.zze<zzpc> {
    private String mName;
    private String zzaGu;
    private String zzaLn;
    private String zzaLo;
    private String zzaLp;
    private String zzaLq;
    private String zzaLr;
    private String zzaLs;
    private String zzvY;
    private String zzwN;

    public String getContent() {
        return this.zzvY;
    }

    public String getId() {
        return this.zzwN;
    }

    public String getName() {
        return this.mName;
    }

    public String getSource() {
        return this.zzaGu;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("name", this.mName);
        map.put("source", this.zzaGu);
        map.put("medium", this.zzaLn);
        map.put("keyword", this.zzaLo);
        map.put("content", this.zzvY);
        map.put("id", this.zzwN);
        map.put("adNetworkId", this.zzaLp);
        map.put("gclid", this.zzaLq);
        map.put("dclid", this.zzaLr);
        map.put("aclid", this.zzaLs);
        return zzB(map);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzpc zzpcVar) {
        if (!TextUtils.isEmpty(this.mName)) {
            zzpcVar.setName(this.mName);
        }
        if (!TextUtils.isEmpty(this.zzaGu)) {
            zzpcVar.zzdH(this.zzaGu);
        }
        if (!TextUtils.isEmpty(this.zzaLn)) {
            zzpcVar.zzdI(this.zzaLn);
        }
        if (!TextUtils.isEmpty(this.zzaLo)) {
            zzpcVar.zzdJ(this.zzaLo);
        }
        if (!TextUtils.isEmpty(this.zzvY)) {
            zzpcVar.zzdK(this.zzvY);
        }
        if (!TextUtils.isEmpty(this.zzwN)) {
            zzpcVar.zzdL(this.zzwN);
        }
        if (!TextUtils.isEmpty(this.zzaLp)) {
            zzpcVar.zzdM(this.zzaLp);
        }
        if (!TextUtils.isEmpty(this.zzaLq)) {
            zzpcVar.zzdN(this.zzaLq);
        }
        if (!TextUtils.isEmpty(this.zzaLr)) {
            zzpcVar.zzdO(this.zzaLr);
        }
        if (TextUtils.isEmpty(this.zzaLs)) {
            return;
        }
        zzpcVar.zzdP(this.zzaLs);
    }

    public void zzdH(String str) {
        this.zzaGu = str;
    }

    public void zzdI(String str) {
        this.zzaLn = str;
    }

    public void zzdJ(String str) {
        this.zzaLo = str;
    }

    public void zzdK(String str) {
        this.zzvY = str;
    }

    public void zzdL(String str) {
        this.zzwN = str;
    }

    public void zzdM(String str) {
        this.zzaLp = str;
    }

    public void zzdN(String str) {
        this.zzaLq = str;
    }

    public void zzdO(String str) {
        this.zzaLr = str;
    }

    public void zzdP(String str) {
        this.zzaLs = str;
    }

    public String zzyu() {
        return this.zzaLn;
    }

    public String zzyv() {
        return this.zzaLo;
    }

    public String zzyw() {
        return this.zzaLp;
    }

    public String zzyx() {
        return this.zzaLq;
    }

    public String zzyy() {
        return this.zzaLr;
    }

    public String zzyz() {
        return this.zzaLs;
    }
}
