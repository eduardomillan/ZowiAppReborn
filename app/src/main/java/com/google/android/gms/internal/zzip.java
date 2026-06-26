package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class zzip {

    public interface zza<D, R> {
        R zze(D d);
    }

    public static <A, B> zziq<B> zza(final zziq<A> zziqVar, final zza<A, B> zzaVar) {
        final zzin zzinVar = new zzin();
        zziqVar.zzc(new Runnable() { // from class: com.google.android.gms.internal.zzip.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    zzinVar.zzf(zzaVar.zze(zziqVar.get()));
                } catch (InterruptedException | CancellationException | ExecutionException e) {
                    zzinVar.cancel(true);
                }
            }
        });
        return zzinVar;
    }

    public static <V> zziq<List<V>> zzh(final List<zziq<V>> list) {
        final zzin zzinVar = new zzin();
        final int size = list.size();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        Iterator<zziq<V>> it = list.iterator();
        while (it.hasNext()) {
            it.next().zzc(new Runnable() { // from class: com.google.android.gms.internal.zzip.2
                @Override // java.lang.Runnable
                public void run() {
                    if (atomicInteger.incrementAndGet() >= size) {
                        try {
                            zzinVar.zzf(zzip.zzi(list));
                        } catch (InterruptedException | ExecutionException e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzd("Unable to convert list of futures to a future of list", e);
                        }
                    }
                }
            });
        }
        return zzinVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <V> List<V> zzi(List<zziq<V>> list) throws ExecutionException, InterruptedException {
        ArrayList arrayList = new ArrayList();
        Iterator<zziq<V>> it = list.iterator();
        while (it.hasNext()) {
            V v = it.next().get();
            if (v != null) {
                arrayList.add(v);
            }
        }
        return arrayList;
    }
}
