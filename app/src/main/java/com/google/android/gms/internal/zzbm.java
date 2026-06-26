package com.google.android.gms.internal;

import android.util.Base64OutputStream;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.google.android.gms.internal.zzbp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.PriorityQueue;

/* JADX INFO: loaded from: classes.dex */
public class zzbm {
    private final int zzsq;
    private final zzbl zzss = new zzbo();
    private final int zzsp = 6;
    private final int zzsr = 0;

    static class zza {
        ByteArrayOutputStream zzsu = new ByteArrayOutputStream(4096);
        Base64OutputStream zzsv = new Base64OutputStream(this.zzsu, 10);

        public String toString() {
            String string;
            try {
                this.zzsv.close();
            } catch (IOException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("HashManager: Unable to convert to Base64.", e);
            }
            try {
                this.zzsu.close();
                string = this.zzsu.toString();
            } catch (IOException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("HashManager: Unable to convert to Base64.", e2);
                string = "";
            } finally {
                this.zzsu = null;
                this.zzsv = null;
            }
            return string;
        }

        public void write(byte[] data) throws IOException {
            this.zzsv.write(data);
        }
    }

    public zzbm(int i) {
        this.zzsq = i;
    }

    private String zzA(String str) {
        String[] strArrSplit = str.split(Droid2InoConstants.NEW_LINE_CHARACTER);
        if (strArrSplit.length == 0) {
            return "";
        }
        zza zzaVarZzcz = zzcz();
        Arrays.sort(strArrSplit, new Comparator<String>() { // from class: com.google.android.gms.internal.zzbm.1
            @Override // java.util.Comparator
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        for (int i = 0; i < strArrSplit.length && i < this.zzsq; i++) {
            if (strArrSplit[i].trim().length() != 0) {
                try {
                    zzaVarZzcz.write(this.zzss.zzz(strArrSplit[i]));
                } catch (IOException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Error while writing hash to byteStream", e);
                }
            }
        }
        return zzaVarZzcz.toString();
    }

    String zzB(String str) {
        String[] strArrSplit = str.split(Droid2InoConstants.NEW_LINE_CHARACTER);
        if (strArrSplit.length == 0) {
            return "";
        }
        zza zzaVarZzcz = zzcz();
        PriorityQueue priorityQueue = new PriorityQueue(this.zzsq, new Comparator<zzbp.zza>() { // from class: com.google.android.gms.internal.zzbm.2
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
            public int compare(zzbp.zza zzaVar, zzbp.zza zzaVar2) {
                return (int) (zzaVar.value - zzaVar2.value);
            }
        });
        for (String str2 : strArrSplit) {
            String[] strArrZzD = zzbn.zzD(str2);
            if (strArrZzD.length >= this.zzsp) {
                zzbp.zza(strArrZzD, this.zzsq, this.zzsp, (PriorityQueue<zzbp.zza>) priorityQueue);
            }
        }
        Iterator it = priorityQueue.iterator();
        while (it.hasNext()) {
            try {
                zzaVarZzcz.write(this.zzss.zzz(((zzbp.zza) it.next()).zzsx));
            } catch (IOException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return zzaVarZzcz.toString();
    }

    public String zza(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next().toLowerCase(Locale.US));
            stringBuffer.append('\n');
        }
        switch (this.zzsr) {
            case 0:
                return zzB(stringBuffer.toString());
            case 1:
                return zzA(stringBuffer.toString());
            default:
                return "";
        }
    }

    zza zzcz() {
        return new zza();
    }
}
