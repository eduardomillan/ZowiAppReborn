package com.google.android.gms.measurement;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LogPrinter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class zzb implements zzi {
    private static final Uri zzaKM;
    private final LogPrinter zzaKN = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("local");
        zzaKM = builder.build();
    }

    @Override // com.google.android.gms.measurement.zzi
    public void zzb(zzc zzcVar) {
        ArrayList arrayList = new ArrayList(zzcVar.zzyf());
        Collections.sort(arrayList, new Comparator<zze>() { // from class: com.google.android.gms.measurement.zzb.1
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
            public int compare(zze zzeVar, zze zzeVar2) {
                return zzeVar.getClass().getCanonicalName().compareTo(zzeVar2.getClass().getCanonicalName());
            }
        });
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String string = ((zze) it.next()).toString();
            if (!TextUtils.isEmpty(string)) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(string);
            }
        }
        this.zzaKN.println(sb.toString());
    }

    @Override // com.google.android.gms.measurement.zzi
    public Uri zzhI() {
        return zzaKM;
    }
}
