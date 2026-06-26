package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class zzrb {

    public static class zza {
        private final zzag.zza zzaYM;
        private final Map<String, zzag.zza> zzban;

        private zza(Map<String, zzag.zza> map, zzag.zza zzaVar) {
            this.zzban = map;
            this.zzaYM = zzaVar;
        }

        public static zzb zzDZ() {
            return new zzb();
        }

        public String toString() {
            return "Properties: " + zzEa() + " pushAfterEvaluate: " + this.zzaYM;
        }

        public zzag.zza zzDz() {
            return this.zzaYM;
        }

        public Map<String, zzag.zza> zzEa() {
            return Collections.unmodifiableMap(this.zzban);
        }

        public void zza(String str, zzag.zza zzaVar) {
            this.zzban.put(str, zzaVar);
        }
    }

    public static class zzb {
        private zzag.zza zzaYM;
        private final Map<String, zzag.zza> zzban;

        private zzb() {
            this.zzban = new HashMap();
        }

        public zza zzEb() {
            return new zza(this.zzban, this.zzaYM);
        }

        public zzb zzb(String str, zzag.zza zzaVar) {
            this.zzban.put(str, zzaVar);
            return this;
        }

        public zzb zzq(zzag.zza zzaVar) {
            this.zzaYM = zzaVar;
            return this;
        }
    }

    public static class zzc {
        private final String zzYk;
        private final List<zze> zzbao;
        private final Map<String, List<zza>> zzbap;
        private final int zzbaq;

        private zzc(List<zze> list, Map<String, List<zza>> map, String str, int i) {
            this.zzbao = Collections.unmodifiableList(list);
            this.zzbap = Collections.unmodifiableMap(map);
            this.zzYk = str;
            this.zzbaq = i;
        }

        public static zzd zzEc() {
            return new zzd();
        }

        public String getVersion() {
            return this.zzYk;
        }

        public String toString() {
            return "Rules: " + zzEd() + "  Macros: " + this.zzbap;
        }

        public List<zze> zzEd() {
            return this.zzbao;
        }

        public Map<String, List<zza>> zzEe() {
            return this.zzbap;
        }
    }

    public static class zzd {
        private String zzYk;
        private final List<zze> zzbao;
        private final Map<String, List<zza>> zzbap;
        private int zzbaq;

        private zzd() {
            this.zzbao = new ArrayList();
            this.zzbap = new HashMap();
            this.zzYk = "";
            this.zzbaq = 0;
        }

        public zzc zzEf() {
            return new zzc(this.zzbao, this.zzbap, this.zzYk, this.zzbaq);
        }

        public zzd zzb(zze zzeVar) {
            this.zzbao.add(zzeVar);
            return this;
        }

        public zzd zzc(zza zzaVar) {
            String strZzg = com.google.android.gms.tagmanager.zzdf.zzg(zzaVar.zzEa().get(zzae.INSTANCE_NAME.toString()));
            List<zza> arrayList = this.zzbap.get(strZzg);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzbap.put(strZzg, arrayList);
            }
            arrayList.add(zzaVar);
            return this;
        }

        public zzd zzfo(String str) {
            this.zzYk = str;
            return this;
        }

        public zzd zzjC(int i) {
            this.zzbaq = i;
            return this;
        }
    }

    public static class zze {
        private final List<String> zzbaA;
        private final List<zza> zzbar;
        private final List<zza> zzbas;
        private final List<zza> zzbat;
        private final List<zza> zzbau;
        private final List<zza> zzbav;
        private final List<zza> zzbaw;
        private final List<String> zzbax;
        private final List<String> zzbay;
        private final List<String> zzbaz;

        private zze(List<zza> list, List<zza> list2, List<zza> list3, List<zza> list4, List<zza> list5, List<zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.zzbar = Collections.unmodifiableList(list);
            this.zzbas = Collections.unmodifiableList(list2);
            this.zzbat = Collections.unmodifiableList(list3);
            this.zzbau = Collections.unmodifiableList(list4);
            this.zzbav = Collections.unmodifiableList(list5);
            this.zzbaw = Collections.unmodifiableList(list6);
            this.zzbax = Collections.unmodifiableList(list7);
            this.zzbay = Collections.unmodifiableList(list8);
            this.zzbaz = Collections.unmodifiableList(list9);
            this.zzbaA = Collections.unmodifiableList(list10);
        }

        public static zzf zzEg() {
            return new zzf();
        }

        public String toString() {
            return "Positive predicates: " + zzEh() + "  Negative predicates: " + zzEi() + "  Add tags: " + zzEj() + "  Remove tags: " + zzEk() + "  Add macros: " + zzEl() + "  Remove macros: " + zzEq();
        }

        public List<zza> zzEh() {
            return this.zzbar;
        }

        public List<zza> zzEi() {
            return this.zzbas;
        }

        public List<zza> zzEj() {
            return this.zzbat;
        }

        public List<zza> zzEk() {
            return this.zzbau;
        }

        public List<zza> zzEl() {
            return this.zzbav;
        }

        public List<String> zzEm() {
            return this.zzbax;
        }

        public List<String> zzEn() {
            return this.zzbay;
        }

        public List<String> zzEo() {
            return this.zzbaz;
        }

        public List<String> zzEp() {
            return this.zzbaA;
        }

        public List<zza> zzEq() {
            return this.zzbaw;
        }
    }

    public static class zzf {
        private final List<String> zzbaA;
        private final List<zza> zzbar;
        private final List<zza> zzbas;
        private final List<zza> zzbat;
        private final List<zza> zzbau;
        private final List<zza> zzbav;
        private final List<zza> zzbaw;
        private final List<String> zzbax;
        private final List<String> zzbay;
        private final List<String> zzbaz;

        private zzf() {
            this.zzbar = new ArrayList();
            this.zzbas = new ArrayList();
            this.zzbat = new ArrayList();
            this.zzbau = new ArrayList();
            this.zzbav = new ArrayList();
            this.zzbaw = new ArrayList();
            this.zzbax = new ArrayList();
            this.zzbay = new ArrayList();
            this.zzbaz = new ArrayList();
            this.zzbaA = new ArrayList();
        }

        public zze zzEr() {
            return new zze(this.zzbar, this.zzbas, this.zzbat, this.zzbau, this.zzbav, this.zzbaw, this.zzbax, this.zzbay, this.zzbaz, this.zzbaA);
        }

        public zzf zzd(zza zzaVar) {
            this.zzbar.add(zzaVar);
            return this;
        }

        public zzf zze(zza zzaVar) {
            this.zzbas.add(zzaVar);
            return this;
        }

        public zzf zzf(zza zzaVar) {
            this.zzbat.add(zzaVar);
            return this;
        }

        public zzf zzfp(String str) {
            this.zzbaz.add(str);
            return this;
        }

        public zzf zzfq(String str) {
            this.zzbaA.add(str);
            return this;
        }

        public zzf zzfr(String str) {
            this.zzbax.add(str);
            return this;
        }

        public zzf zzfs(String str) {
            this.zzbay.add(str);
            return this;
        }

        public zzf zzg(zza zzaVar) {
            this.zzbau.add(zzaVar);
            return this;
        }

        public zzf zzh(zza zzaVar) {
            this.zzbav.add(zzaVar);
            return this;
        }

        public zzf zzi(zza zzaVar) {
            this.zzbaw.add(zzaVar);
            return this;
        }
    }

    public static class zzg extends Exception {
        public zzg(String str) {
            super(str);
        }
    }

    private static zzag.zza zza(int i, zzaf.zzf zzfVar, zzag.zza[] zzaVarArr, Set<Integer> set) throws zzg {
        int i2 = 0;
        if (set.contains(Integer.valueOf(i))) {
            zzfn("Value cycle detected.  Current value reference: " + i + ".  Previous value references: " + set + ".");
        }
        zzag.zza zzaVar = (zzag.zza) zza(zzfVar.zzif, i, "values");
        if (zzaVarArr[i] != null) {
            return zzaVarArr[i];
        }
        zzag.zza zzaVarZzo = null;
        set.add(Integer.valueOf(i));
        switch (zzaVar.type) {
            case 1:
            case 5:
            case 6:
            case 8:
                zzaVarZzo = zzaVar;
                break;
            case 2:
                zzaf.zzh zzhVarZzp = zzp(zzaVar);
                zzaVarZzo = zzo(zzaVar);
                zzaVarZzo.zziV = new zzag.zza[zzhVarZzp.zziG.length];
                int[] iArr = zzhVarZzp.zziG;
                int length = iArr.length;
                int i3 = 0;
                while (i2 < length) {
                    zzaVarZzo.zziV[i3] = zza(iArr[i2], zzfVar, zzaVarArr, set);
                    i2++;
                    i3++;
                }
                break;
            case 3:
                zzaVarZzo = zzo(zzaVar);
                zzaf.zzh zzhVarZzp2 = zzp(zzaVar);
                if (zzhVarZzp2.zziH.length != zzhVarZzp2.zziI.length) {
                    zzfn("Uneven map keys (" + zzhVarZzp2.zziH.length + ") and map values (" + zzhVarZzp2.zziI.length + ")");
                }
                zzaVarZzo.zziW = new zzag.zza[zzhVarZzp2.zziH.length];
                zzaVarZzo.zziX = new zzag.zza[zzhVarZzp2.zziH.length];
                int[] iArr2 = zzhVarZzp2.zziH;
                int length2 = iArr2.length;
                int i4 = 0;
                int i5 = 0;
                while (i4 < length2) {
                    zzaVarZzo.zziW[i5] = zza(iArr2[i4], zzfVar, zzaVarArr, set);
                    i4++;
                    i5++;
                }
                int[] iArr3 = zzhVarZzp2.zziI;
                int length3 = iArr3.length;
                int i6 = 0;
                while (i2 < length3) {
                    zzaVarZzo.zziX[i6] = zza(iArr3[i2], zzfVar, zzaVarArr, set);
                    i2++;
                    i6++;
                }
                break;
            case 4:
                zzaVarZzo = zzo(zzaVar);
                zzaVarZzo.zziY = com.google.android.gms.tagmanager.zzdf.zzg(zza(zzp(zzaVar).zziL, zzfVar, zzaVarArr, set));
                break;
            case 7:
                zzaVarZzo = zzo(zzaVar);
                zzaf.zzh zzhVarZzp3 = zzp(zzaVar);
                zzaVarZzo.zzjc = new zzag.zza[zzhVarZzp3.zziK.length];
                int[] iArr4 = zzhVarZzp3.zziK;
                int length4 = iArr4.length;
                int i7 = 0;
                while (i2 < length4) {
                    zzaVarZzo.zzjc[i7] = zza(iArr4[i2], zzfVar, zzaVarArr, set);
                    i2++;
                    i7++;
                }
                break;
        }
        if (zzaVarZzo == null) {
            zzfn("Invalid value: " + zzaVar);
        }
        zzaVarArr[i] = zzaVarZzo;
        set.remove(Integer.valueOf(i));
        return zzaVarZzo;
    }

    private static zza zza(zzaf.zzb zzbVar, zzaf.zzf zzfVar, zzag.zza[] zzaVarArr, int i) throws zzg {
        zzb zzbVarZzDZ = zza.zzDZ();
        for (int i2 : zzbVar.zzhQ) {
            zzaf.zze zzeVar = (zzaf.zze) zza(zzfVar.zzig, Integer.valueOf(i2).intValue(), "properties");
            String str = (String) zza(zzfVar.zzie, zzeVar.key, "keys");
            zzag.zza zzaVar = (zzag.zza) zza(zzaVarArr, zzeVar.value, "values");
            if (zzae.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzbVarZzDZ.zzq(zzaVar);
            } else {
                zzbVarZzDZ.zzb(str, zzaVar);
            }
        }
        return zzbVarZzDZ.zzEb();
    }

    private static zze zza(zzaf.zzg zzgVar, List<zza> list, List<zza> list2, List<zza> list3, zzaf.zzf zzfVar) {
        zzf zzfVarZzEg = zze.zzEg();
        for (int i : zzgVar.zziu) {
            zzfVarZzEg.zzd(list3.get(Integer.valueOf(i).intValue()));
        }
        for (int i2 : zzgVar.zziv) {
            zzfVarZzEg.zze(list3.get(Integer.valueOf(i2).intValue()));
        }
        for (int i3 : zzgVar.zziw) {
            zzfVarZzEg.zzf(list.get(Integer.valueOf(i3).intValue()));
        }
        for (int i4 : zzgVar.zziy) {
            zzfVarZzEg.zzfp(zzfVar.zzif[Integer.valueOf(i4).intValue()].zziU);
        }
        for (int i5 : zzgVar.zzix) {
            zzfVarZzEg.zzg(list.get(Integer.valueOf(i5).intValue()));
        }
        for (int i6 : zzgVar.zziz) {
            zzfVarZzEg.zzfq(zzfVar.zzif[Integer.valueOf(i6).intValue()].zziU);
        }
        for (int i7 : zzgVar.zziA) {
            zzfVarZzEg.zzh(list2.get(Integer.valueOf(i7).intValue()));
        }
        for (int i8 : zzgVar.zziC) {
            zzfVarZzEg.zzfr(zzfVar.zzif[Integer.valueOf(i8).intValue()].zziU);
        }
        for (int i9 : zzgVar.zziB) {
            zzfVarZzEg.zzi(list2.get(Integer.valueOf(i9).intValue()));
        }
        for (int i10 : zzgVar.zziD) {
            zzfVarZzEg.zzfs(zzfVar.zzif[Integer.valueOf(i10).intValue()].zziU);
        }
        return zzfVarZzEg.zzEr();
    }

    private static <T> T zza(T[] tArr, int i, String str) throws zzg {
        if (i < 0 || i >= tArr.length) {
            zzfn("Index out of bounds detected: " + i + " in " + str);
        }
        return tArr[i];
    }

    public static zzc zzb(zzaf.zzf zzfVar) throws zzg {
        zzag.zza[] zzaVarArr = new zzag.zza[zzfVar.zzif.length];
        for (int i = 0; i < zzfVar.zzif.length; i++) {
            zza(i, zzfVar, zzaVarArr, new HashSet(0));
        }
        zzd zzdVarZzEc = zzc.zzEc();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zzfVar.zzii.length; i2++) {
            arrayList.add(zza(zzfVar.zzii[i2], zzfVar, zzaVarArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zzfVar.zzij.length; i3++) {
            arrayList2.add(zza(zzfVar.zzij[i3], zzfVar, zzaVarArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zzfVar.zzih.length; i4++) {
            zza zzaVarZza = zza(zzfVar.zzih[i4], zzfVar, zzaVarArr, i4);
            zzdVarZzEc.zzc(zzaVarZza);
            arrayList3.add(zzaVarZza);
        }
        for (zzaf.zzg zzgVar : zzfVar.zzik) {
            zzdVarZzEc.zzb(zza(zzgVar, arrayList, arrayList3, arrayList2, zzfVar));
        }
        zzdVarZzEc.zzfo(zzfVar.version);
        zzdVarZzEc.zzjC(zzfVar.zzis);
        return zzdVarZzEc.zzEf();
    }

    public static void zzb(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    private static void zzfn(String str) throws zzg {
        com.google.android.gms.tagmanager.zzbg.e(str);
        throw new zzg(str);
    }

    public static zzag.zza zzo(zzag.zza zzaVar) {
        zzag.zza zzaVar2 = new zzag.zza();
        zzaVar2.type = zzaVar.type;
        zzaVar2.zzjd = (int[]) zzaVar.zzjd.clone();
        if (zzaVar.zzje) {
            zzaVar2.zzje = zzaVar.zzje;
        }
        return zzaVar2;
    }

    private static zzaf.zzh zzp(zzag.zza zzaVar) throws zzg {
        if (((zzaf.zzh) zzaVar.zza(zzaf.zzh.zziE)) == null) {
            zzfn("Expected a ServingValue and didn't get one. Value is: " + zzaVar);
        }
        return (zzaf.zzh) zzaVar.zza(zzaf.zzh.zziE);
    }
}
