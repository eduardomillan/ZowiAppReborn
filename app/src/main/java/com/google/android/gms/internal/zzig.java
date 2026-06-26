package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class zzig {
    private final String[] zzIO;
    private final double[] zzIP;
    private final double[] zzIQ;
    private final int[] zzIR;
    private int zzIS;

    public static class zza {
        public final int count;
        public final String name;
        public final double zzIT;
        public final double zzIU;
        public final double zzIV;

        public zza(String str, double d, double d2, double d3, int i) {
            this.name = str;
            this.zzIU = d;
            this.zzIT = d2;
            this.zzIV = d3;
            this.count = i;
        }

        public boolean equals(Object other) {
            if (!(other instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) other;
            return com.google.android.gms.common.internal.zzw.equal(this.name, zzaVar.name) && this.zzIT == zzaVar.zzIT && this.zzIU == zzaVar.zzIU && this.count == zzaVar.count && Double.compare(this.zzIV, zzaVar.zzIV) == 0;
        }

        public int hashCode() {
            return com.google.android.gms.common.internal.zzw.hashCode(this.name, Double.valueOf(this.zzIT), Double.valueOf(this.zzIU), Double.valueOf(this.zzIV), Integer.valueOf(this.count));
        }

        public String toString() {
            return com.google.android.gms.common.internal.zzw.zzv(this).zzg("name", this.name).zzg("minBound", Double.valueOf(this.zzIU)).zzg("maxBound", Double.valueOf(this.zzIT)).zzg("percent", Double.valueOf(this.zzIV)).zzg("count", Integer.valueOf(this.count)).toString();
        }
    }

    public static class zzb {
        private final List<String> zzIW = new ArrayList();
        private final List<Double> zzIX = new ArrayList();
        private final List<Double> zzIY = new ArrayList();

        public zzb zza(String str, double d, double d2) {
            int i;
            int i2 = 0;
            while (true) {
                i = i2;
                if (i >= this.zzIW.size()) {
                    break;
                }
                double dDoubleValue = this.zzIY.get(i).doubleValue();
                double dDoubleValue2 = this.zzIX.get(i).doubleValue();
                if (d < dDoubleValue || (dDoubleValue == d && d2 < dDoubleValue2)) {
                    break;
                }
                i2 = i + 1;
            }
            this.zzIW.add(i, str);
            this.zzIY.add(i, Double.valueOf(d));
            this.zzIX.add(i, Double.valueOf(d2));
            return this;
        }

        public zzig zzgK() {
            return new zzig(this);
        }
    }

    private zzig(zzb zzbVar) {
        int size = zzbVar.zzIX.size();
        this.zzIO = (String[]) zzbVar.zzIW.toArray(new String[size]);
        this.zzIP = zzg(zzbVar.zzIX);
        this.zzIQ = zzg(zzbVar.zzIY);
        this.zzIR = new int[size];
        this.zzIS = 0;
    }

    private double[] zzg(List<Double> list) {
        double[] dArr = new double[list.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= dArr.length) {
                return dArr;
            }
            dArr[i2] = list.get(i2).doubleValue();
            i = i2 + 1;
        }
    }

    public List<zza> getBuckets() {
        ArrayList arrayList = new ArrayList(this.zzIO.length);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzIO.length) {
                return arrayList;
            }
            arrayList.add(new zza(this.zzIO[i2], this.zzIQ[i2], this.zzIP[i2], ((double) this.zzIR[i2]) / ((double) this.zzIS), this.zzIR[i2]));
            i = i2 + 1;
        }
    }

    public void zza(double d) {
        this.zzIS++;
        for (int i = 0; i < this.zzIQ.length; i++) {
            if (this.zzIQ[i] <= d && d < this.zzIP[i]) {
                int[] iArr = this.zzIR;
                iArr[i] = iArr[i] + 1;
            }
            if (d < this.zzIQ[i]) {
                return;
            }
        }
    }
}
