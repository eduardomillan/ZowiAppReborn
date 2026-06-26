package com.google.android.gms.tagmanager;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.tagmanager.zzm;
import com.google.android.gms.tagmanager.zzt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class zzcp {
    private static final zzbw<zzag.zza> zzaYu = new zzbw<>(zzdf.zzDX(), true);
    private final DataLayer zzaVR;
    private final zzl<zzrb.zza, zzbw<zzag.zza>> zzaYA;
    private final zzl<String, zzb> zzaYB;
    private final Set<zzrb.zze> zzaYC;
    private final Map<String, zzc> zzaYD;
    private volatile String zzaYE;
    private int zzaYF;
    private final zzrb.zzc zzaYv;
    private final zzah zzaYw;
    private final Map<String, zzak> zzaYx;
    private final Map<String, zzak> zzaYy;
    private final Map<String, zzak> zzaYz;

    interface zza {
        void zza(zzrb.zze zzeVar, Set<zzrb.zza> set, Set<zzrb.zza> set2, zzck zzckVar);
    }

    private static class zzb {
        private zzbw<zzag.zza> zzaYL;
        private zzag.zza zzaYM;

        public zzb(zzbw<zzag.zza> zzbwVar, zzag.zza zzaVar) {
            this.zzaYL = zzbwVar;
            this.zzaYM = zzaVar;
        }

        public int getSize() {
            return (this.zzaYM == null ? 0 : this.zzaYM.zzFQ()) + this.zzaYL.getObject().zzFQ();
        }

        public zzbw<zzag.zza> zzDy() {
            return this.zzaYL;
        }

        public zzag.zza zzDz() {
            return this.zzaYM;
        }
    }

    private static class zzc {
        private zzrb.zza zzaYR;
        private final Set<zzrb.zze> zzaYC = new HashSet();
        private final Map<zzrb.zze, List<zzrb.zza>> zzaYN = new HashMap();
        private final Map<zzrb.zze, List<String>> zzaYP = new HashMap();
        private final Map<zzrb.zze, List<zzrb.zza>> zzaYO = new HashMap();
        private final Map<zzrb.zze, List<String>> zzaYQ = new HashMap();

        public Set<zzrb.zze> zzDA() {
            return this.zzaYC;
        }

        public Map<zzrb.zze, List<zzrb.zza>> zzDB() {
            return this.zzaYN;
        }

        public Map<zzrb.zze, List<String>> zzDC() {
            return this.zzaYP;
        }

        public Map<zzrb.zze, List<String>> zzDD() {
            return this.zzaYQ;
        }

        public Map<zzrb.zze, List<zzrb.zza>> zzDE() {
            return this.zzaYO;
        }

        public zzrb.zza zzDF() {
            return this.zzaYR;
        }

        public void zza(zzrb.zze zzeVar) {
            this.zzaYC.add(zzeVar);
        }

        public void zza(zzrb.zze zzeVar, zzrb.zza zzaVar) {
            List<zzrb.zza> arrayList = this.zzaYN.get(zzeVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzaYN.put(zzeVar, arrayList);
            }
            arrayList.add(zzaVar);
        }

        public void zza(zzrb.zze zzeVar, String str) {
            List<String> arrayList = this.zzaYP.get(zzeVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzaYP.put(zzeVar, arrayList);
            }
            arrayList.add(str);
        }

        public void zzb(zzrb.zza zzaVar) {
            this.zzaYR = zzaVar;
        }

        public void zzb(zzrb.zze zzeVar, zzrb.zza zzaVar) {
            List<zzrb.zza> arrayList = this.zzaYO.get(zzeVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzaYO.put(zzeVar, arrayList);
            }
            arrayList.add(zzaVar);
        }

        public void zzb(zzrb.zze zzeVar, String str) {
            List<String> arrayList = this.zzaYQ.get(zzeVar);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.zzaYQ.put(zzeVar, arrayList);
            }
            arrayList.add(str);
        }
    }

    public zzcp(Context context, zzrb.zzc zzcVar, DataLayer dataLayer, zzt.zza zzaVar, zzt.zza zzaVar2, zzah zzahVar) {
        if (zzcVar == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.zzaYv = zzcVar;
        this.zzaYC = new HashSet(zzcVar.zzEd());
        this.zzaVR = dataLayer;
        this.zzaYw = zzahVar;
        this.zzaYA = new zzm().zza(AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START, new zzm.zza<zzrb.zza, zzbw<zzag.zza>>() { // from class: com.google.android.gms.tagmanager.zzcp.1
            @Override // com.google.android.gms.tagmanager.zzm.zza
            /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
            public int sizeOf(zzrb.zza zzaVar3, zzbw<zzag.zza> zzbwVar) {
                return zzbwVar.getObject().zzFQ();
            }
        });
        this.zzaYB = new zzm().zza(AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START, new zzm.zza<String, zzb>() { // from class: com.google.android.gms.tagmanager.zzcp.2
            @Override // com.google.android.gms.tagmanager.zzm.zza
            /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
            public int sizeOf(String str, zzb zzbVar) {
                return str.length() + zzbVar.getSize();
            }
        });
        this.zzaYx = new HashMap();
        zzb(new zzj(context));
        zzb(new zzt(zzaVar2));
        zzb(new zzx(dataLayer));
        zzb(new zzdg(context, dataLayer));
        zzb(new zzdb(context, dataLayer));
        this.zzaYy = new HashMap();
        zzc(new zzr());
        zzc(new zzae());
        zzc(new zzaf());
        zzc(new zzam());
        zzc(new zzan());
        zzc(new zzbc());
        zzc(new zzbd());
        zzc(new zzcf());
        zzc(new zzcy());
        this.zzaYz = new HashMap();
        zza(new com.google.android.gms.tagmanager.zzb(context));
        zza(new com.google.android.gms.tagmanager.zzc(context));
        zza(new zze(context));
        zza(new zzf(context));
        zza(new zzg(context));
        zza(new zzh(context));
        zza(new zzi(context));
        zza(new zzn());
        zza(new zzq(this.zzaYv.getVersion()));
        zza(new zzt(zzaVar));
        zza(new zzv(dataLayer));
        zza(new zzaa(context));
        zza(new zzab());
        zza(new zzad());
        zza(new zzai(this));
        zza(new zzao());
        zza(new zzap());
        zza(new zzaw(context));
        zza(new zzay());
        zza(new zzbb());
        zza(new zzbi());
        zza(new zzbk(context));
        zza(new zzbx());
        zza(new zzbz());
        zza(new zzcc());
        zza(new zzce());
        zza(new zzcg(context));
        zza(new zzcq());
        zza(new zzcr());
        zza(new zzda());
        zza(new zzdh());
        this.zzaYD = new HashMap();
        for (zzrb.zze zzeVar : this.zzaYC) {
            if (zzahVar.zzCS()) {
                zza(zzeVar.zzEl(), zzeVar.zzEm(), "add macro");
                zza(zzeVar.zzEq(), zzeVar.zzEn(), "remove macro");
                zza(zzeVar.zzEj(), zzeVar.zzEo(), "add tag");
                zza(zzeVar.zzEk(), zzeVar.zzEp(), "remove tag");
            }
            for (int i = 0; i < zzeVar.zzEl().size(); i++) {
                zzrb.zza zzaVar3 = zzeVar.zzEl().get(i);
                String str = "Unknown";
                if (zzahVar.zzCS() && i < zzeVar.zzEm().size()) {
                    str = zzeVar.zzEm().get(i);
                }
                zzc zzcVarZzi = zzi(this.zzaYD, zza(zzaVar3));
                zzcVarZzi.zza(zzeVar);
                zzcVarZzi.zza(zzeVar, zzaVar3);
                zzcVarZzi.zza(zzeVar, str);
            }
            for (int i2 = 0; i2 < zzeVar.zzEq().size(); i2++) {
                zzrb.zza zzaVar4 = zzeVar.zzEq().get(i2);
                String str2 = "Unknown";
                if (zzahVar.zzCS() && i2 < zzeVar.zzEn().size()) {
                    str2 = zzeVar.zzEn().get(i2);
                }
                zzc zzcVarZzi2 = zzi(this.zzaYD, zza(zzaVar4));
                zzcVarZzi2.zza(zzeVar);
                zzcVarZzi2.zzb(zzeVar, zzaVar4);
                zzcVarZzi2.zzb(zzeVar, str2);
            }
        }
        for (Map.Entry<String, List<zzrb.zza>> entry : this.zzaYv.zzEe().entrySet()) {
            for (zzrb.zza zzaVar5 : entry.getValue()) {
                if (!zzdf.zzk(zzaVar5.zzEa().get(com.google.android.gms.internal.zzae.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    zzi(this.zzaYD, entry.getKey()).zzb(zzaVar5);
                }
            }
        }
    }

    private String zzDx() {
        if (this.zzaYF <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.zzaYF));
        for (int i = 2; i < this.zzaYF; i++) {
            sb.append(' ');
        }
        sb.append(": ");
        return sb.toString();
    }

    private zzbw<zzag.zza> zza(zzag.zza zzaVar, Set<String> set, zzdi zzdiVar) {
        if (!zzaVar.zzje) {
            return new zzbw<>(zzaVar, true);
        }
        switch (zzaVar.type) {
            case 2:
                zzag.zza zzaVarZzo = zzrb.zzo(zzaVar);
                zzaVarZzo.zziV = new zzag.zza[zzaVar.zziV.length];
                for (int i = 0; i < zzaVar.zziV.length; i++) {
                    zzbw<zzag.zza> zzbwVarZza = zza(zzaVar.zziV[i], set, zzdiVar.zzjv(i));
                    if (zzbwVarZza == zzaYu) {
                        return zzaYu;
                    }
                    zzaVarZzo.zziV[i] = zzbwVarZza.getObject();
                }
                return new zzbw<>(zzaVarZzo, false);
            case 3:
                zzag.zza zzaVarZzo2 = zzrb.zzo(zzaVar);
                if (zzaVar.zziW.length != zzaVar.zziX.length) {
                    zzbg.e("Invalid serving value: " + zzaVar.toString());
                    return zzaYu;
                }
                zzaVarZzo2.zziW = new zzag.zza[zzaVar.zziW.length];
                zzaVarZzo2.zziX = new zzag.zza[zzaVar.zziW.length];
                for (int i2 = 0; i2 < zzaVar.zziW.length; i2++) {
                    zzbw<zzag.zza> zzbwVarZza2 = zza(zzaVar.zziW[i2], set, zzdiVar.zzjw(i2));
                    zzbw<zzag.zza> zzbwVarZza3 = zza(zzaVar.zziX[i2], set, zzdiVar.zzjx(i2));
                    if (zzbwVarZza2 == zzaYu || zzbwVarZza3 == zzaYu) {
                        return zzaYu;
                    }
                    zzaVarZzo2.zziW[i2] = zzbwVarZza2.getObject();
                    zzaVarZzo2.zziX[i2] = zzbwVarZza3.getObject();
                }
                return new zzbw<>(zzaVarZzo2, false);
            case 4:
                if (set.contains(zzaVar.zziY)) {
                    zzbg.e("Macro cycle detected.  Current macro reference: " + zzaVar.zziY + ".  Previous macro references: " + set.toString() + ".");
                    return zzaYu;
                }
                set.add(zzaVar.zziY);
                zzbw<zzag.zza> zzbwVarZza4 = zzdj.zza(zza(zzaVar.zziY, set, zzdiVar.zzDg()), zzaVar.zzjd);
                set.remove(zzaVar.zziY);
                return zzbwVarZza4;
            case 5:
            case 6:
            default:
                zzbg.e("Unknown type: " + zzaVar.type);
                return zzaYu;
            case 7:
                zzag.zza zzaVarZzo3 = zzrb.zzo(zzaVar);
                zzaVarZzo3.zzjc = new zzag.zza[zzaVar.zzjc.length];
                for (int i3 = 0; i3 < zzaVar.zzjc.length; i3++) {
                    zzbw<zzag.zza> zzbwVarZza5 = zza(zzaVar.zzjc[i3], set, zzdiVar.zzjy(i3));
                    if (zzbwVarZza5 == zzaYu) {
                        return zzaYu;
                    }
                    zzaVarZzo3.zzjc[i3] = zzbwVarZza5.getObject();
                }
                return new zzbw<>(zzaVarZzo3, false);
        }
    }

    private zzbw<zzag.zza> zza(String str, Set<String> set, zzbj zzbjVar) {
        zzrb.zza next;
        this.zzaYF++;
        zzb zzbVar = this.zzaYB.get(str);
        if (zzbVar != null && !this.zzaYw.zzCS()) {
            zza(zzbVar.zzDz(), set);
            this.zzaYF--;
            return zzbVar.zzDy();
        }
        zzc zzcVar = this.zzaYD.get(str);
        if (zzcVar == null) {
            zzbg.e(zzDx() + "Invalid macro: " + str);
            this.zzaYF--;
            return zzaYu;
        }
        zzbw<Set<zzrb.zza>> zzbwVarZza = zza(str, zzcVar.zzDA(), zzcVar.zzDB(), zzcVar.zzDC(), zzcVar.zzDE(), zzcVar.zzDD(), set, zzbjVar.zzCI());
        if (zzbwVarZza.getObject().isEmpty()) {
            next = zzcVar.zzDF();
        } else {
            if (zzbwVarZza.getObject().size() > 1) {
                zzbg.zzaH(zzDx() + "Multiple macros active for macroName " + str);
            }
            next = zzbwVarZza.getObject().iterator().next();
        }
        if (next == null) {
            this.zzaYF--;
            return zzaYu;
        }
        zzbw<zzag.zza> zzbwVarZza2 = zza(this.zzaYz, next, set, zzbjVar.zzCY());
        zzbw<zzag.zza> zzbwVar = zzbwVarZza2 == zzaYu ? zzaYu : new zzbw<>(zzbwVarZza2.getObject(), zzbwVarZza.zzDh() && zzbwVarZza2.zzDh());
        zzag.zza zzaVarZzDz = next.zzDz();
        if (zzbwVar.zzDh()) {
            this.zzaYB.zzf(str, new zzb(zzbwVar, zzaVarZzDz));
        }
        zza(zzaVarZzDz, set);
        this.zzaYF--;
        return zzbwVar;
    }

    private zzbw<zzag.zza> zza(Map<String, zzak> map, zzrb.zza zzaVar, Set<String> set, zzch zzchVar) {
        boolean z;
        zzag.zza zzaVar2 = zzaVar.zzEa().get(com.google.android.gms.internal.zzae.FUNCTION.toString());
        if (zzaVar2 == null) {
            zzbg.e("No function id in properties");
            return zzaYu;
        }
        String str = zzaVar2.zziZ;
        zzak zzakVar = map.get(str);
        if (zzakVar == null) {
            zzbg.e(str + " has no backing implementation.");
            return zzaYu;
        }
        zzbw<zzag.zza> zzbwVar = this.zzaYA.get(zzaVar);
        if (zzbwVar != null && !this.zzaYw.zzCS()) {
            return zzbwVar;
        }
        HashMap map2 = new HashMap();
        boolean z2 = true;
        for (Map.Entry<String, zzag.zza> entry : zzaVar.zzEa().entrySet()) {
            zzbw<zzag.zza> zzbwVarZza = zza(entry.getValue(), set, zzchVar.zzeU(entry.getKey()).zze(entry.getValue()));
            if (zzbwVarZza == zzaYu) {
                return zzaYu;
            }
            if (zzbwVarZza.zzDh()) {
                zzaVar.zza(entry.getKey(), zzbwVarZza.getObject());
                z = z2;
            } else {
                z = false;
            }
            map2.put(entry.getKey(), zzbwVarZza.getObject());
            z2 = z;
        }
        if (!zzakVar.zzf(map2.keySet())) {
            zzbg.e("Incorrect keys for function " + str + " required " + zzakVar.zzCU() + " had " + map2.keySet());
            return zzaYu;
        }
        boolean z3 = z2 && zzakVar.zzCo();
        zzbw<zzag.zza> zzbwVar2 = new zzbw<>(zzakVar.zzI(map2), z3);
        if (z3) {
            this.zzaYA.zzf(zzaVar, zzbwVar2);
        }
        zzchVar.zzd(zzbwVar2.getObject());
        return zzbwVar2;
    }

    private zzbw<Set<zzrb.zza>> zza(Set<zzrb.zze> set, Set<String> set2, zza zzaVar, zzco zzcoVar) {
        Set<zzrb.zza> hashSet = new HashSet<>();
        Set<zzrb.zza> hashSet2 = new HashSet<>();
        boolean z = true;
        for (zzrb.zze zzeVar : set) {
            zzck zzckVarZzDf = zzcoVar.zzDf();
            zzbw<Boolean> zzbwVarZza = zza(zzeVar, set2, zzckVarZzDf);
            if (zzbwVarZza.getObject().booleanValue()) {
                zzaVar.zza(zzeVar, hashSet, hashSet2, zzckVarZzDf);
            }
            z = z && zzbwVarZza.zzDh();
        }
        hashSet.removeAll(hashSet2);
        zzcoVar.zzg(hashSet);
        return new zzbw<>(hashSet, z);
    }

    private static String zza(zzrb.zza zzaVar) {
        return zzdf.zzg(zzaVar.zzEa().get(com.google.android.gms.internal.zzae.INSTANCE_NAME.toString()));
    }

    private void zza(zzag.zza zzaVar, Set<String> set) {
        zzbw<zzag.zza> zzbwVarZza;
        if (zzaVar == null || (zzbwVarZza = zza(zzaVar, set, new zzbu())) == zzaYu) {
            return;
        }
        Object objZzl = zzdf.zzl(zzbwVarZza.getObject());
        if (objZzl instanceof Map) {
            this.zzaVR.push((Map) objZzl);
            return;
        }
        if (!(objZzl instanceof List)) {
            zzbg.zzaH("pushAfterEvaluate: value not a Map or List");
            return;
        }
        for (Object obj : (List) objZzl) {
            if (obj instanceof Map) {
                this.zzaVR.push((Map) obj);
            } else {
                zzbg.zzaH("pushAfterEvaluate: value not a Map");
            }
        }
    }

    private static void zza(List<zzrb.zza> list, List<String> list2, String str) {
        if (list.size() != list2.size()) {
            zzbg.zzaG("Invalid resource: imbalance of rule names of functions for " + str + " operation. Using default rule name instead");
        }
    }

    private static void zza(Map<String, zzak> map, zzak zzakVar) {
        if (map.containsKey(zzakVar.zzCT())) {
            throw new IllegalArgumentException("Duplicate function type name: " + zzakVar.zzCT());
        }
        map.put(zzakVar.zzCT(), zzakVar);
    }

    private static zzc zzi(Map<String, zzc> map, String str) {
        zzc zzcVar = map.get(str);
        if (zzcVar != null) {
            return zzcVar;
        }
        zzc zzcVar2 = new zzc();
        map.put(str, zzcVar2);
        return zzcVar2;
    }

    public synchronized void zzA(List<zzaf.zzi> list) {
        for (zzaf.zzi zziVar : list) {
            if (zziVar.name == null || !zziVar.name.startsWith("gaExperiment:")) {
                zzbg.v("Ignored supplemental: " + zziVar);
            } else {
                zzaj.zza(this.zzaVR, zziVar);
            }
        }
    }

    synchronized String zzDw() {
        return this.zzaYE;
    }

    zzbw<Boolean> zza(zzrb.zza zzaVar, Set<String> set, zzch zzchVar) {
        zzbw<zzag.zza> zzbwVarZza = zza(this.zzaYy, zzaVar, set, zzchVar);
        Boolean boolZzk = zzdf.zzk(zzbwVarZza.getObject());
        zzchVar.zzd(zzdf.zzQ(boolZzk));
        return new zzbw<>(boolZzk, zzbwVarZza.zzDh());
    }

    zzbw<Boolean> zza(zzrb.zze zzeVar, Set<String> set, zzck zzckVar) {
        Iterator<zzrb.zza> it = zzeVar.zzEi().iterator();
        boolean z = true;
        while (it.hasNext()) {
            zzbw<Boolean> zzbwVarZza = zza(it.next(), set, zzckVar.zzCZ());
            if (zzbwVarZza.getObject().booleanValue()) {
                zzckVar.zzf(zzdf.zzQ(false));
                return new zzbw<>(false, zzbwVarZza.zzDh());
            }
            z = z && zzbwVarZza.zzDh();
        }
        Iterator<zzrb.zza> it2 = zzeVar.zzEh().iterator();
        while (it2.hasNext()) {
            zzbw<Boolean> zzbwVarZza2 = zza(it2.next(), set, zzckVar.zzDa());
            if (!zzbwVarZza2.getObject().booleanValue()) {
                zzckVar.zzf(zzdf.zzQ(false));
                return new zzbw<>(false, zzbwVarZza2.zzDh());
            }
            z = z && zzbwVarZza2.zzDh();
        }
        zzckVar.zzf(zzdf.zzQ(true));
        return new zzbw<>(true, z);
    }

    zzbw<Set<zzrb.zza>> zza(String str, Set<zzrb.zze> set, final Map<zzrb.zze, List<zzrb.zza>> map, final Map<zzrb.zze, List<String>> map2, final Map<zzrb.zze, List<zzrb.zza>> map3, final Map<zzrb.zze, List<String>> map4, Set<String> set2, zzco zzcoVar) {
        return zza(set, set2, new zza() { // from class: com.google.android.gms.tagmanager.zzcp.3
            @Override // com.google.android.gms.tagmanager.zzcp.zza
            public void zza(zzrb.zze zzeVar, Set<zzrb.zza> set3, Set<zzrb.zza> set4, zzck zzckVar) {
                List<zzrb.zza> list = (List) map.get(zzeVar);
                List<String> list2 = (List) map2.get(zzeVar);
                if (list != null) {
                    set3.addAll(list);
                    zzckVar.zzDb().zzc(list, list2);
                }
                List<zzrb.zza> list3 = (List) map3.get(zzeVar);
                List<String> list4 = (List) map4.get(zzeVar);
                if (list3 != null) {
                    set4.addAll(list3);
                    zzckVar.zzDc().zzc(list3, list4);
                }
            }
        }, zzcoVar);
    }

    zzbw<Set<zzrb.zza>> zza(Set<zzrb.zze> set, zzco zzcoVar) {
        return zza(set, new HashSet(), new zza() { // from class: com.google.android.gms.tagmanager.zzcp.4
            @Override // com.google.android.gms.tagmanager.zzcp.zza
            public void zza(zzrb.zze zzeVar, Set<zzrb.zza> set2, Set<zzrb.zza> set3, zzck zzckVar) {
                set2.addAll(zzeVar.zzEj());
                set3.addAll(zzeVar.zzEk());
                zzckVar.zzDd().zzc(zzeVar.zzEj(), zzeVar.zzEo());
                zzckVar.zzDe().zzc(zzeVar.zzEk(), zzeVar.zzEp());
            }
        }, zzcoVar);
    }

    void zza(zzak zzakVar) {
        zza(this.zzaYz, zzakVar);
    }

    void zzb(zzak zzakVar) {
        zza(this.zzaYx, zzakVar);
    }

    void zzc(zzak zzakVar) {
        zza(this.zzaYy, zzakVar);
    }

    public synchronized void zzeC(String str) {
        zzeZ(str);
        zzag zzagVarZzeP = this.zzaYw.zzeP(str);
        zzu zzuVarZzCQ = zzagVarZzeP.zzCQ();
        Iterator<zzrb.zza> it = zza(this.zzaYC, zzuVarZzCQ.zzCI()).getObject().iterator();
        while (it.hasNext()) {
            zza(this.zzaYx, it.next(), new HashSet(), zzuVarZzCQ.zzCH());
        }
        zzagVarZzeP.zzCR();
        zzeZ(null);
    }

    public zzbw<zzag.zza> zzeY(String str) {
        this.zzaYF = 0;
        zzag zzagVarZzeO = this.zzaYw.zzeO(str);
        zzbw<zzag.zza> zzbwVarZza = zza(str, new HashSet(), zzagVarZzeO.zzCP());
        zzagVarZzeO.zzCR();
        return zzbwVarZza;
    }

    synchronized void zzeZ(String str) {
        this.zzaYE = str;
    }
}
