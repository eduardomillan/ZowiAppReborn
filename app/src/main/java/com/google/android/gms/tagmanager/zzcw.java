package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzm;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzcw<K, V> implements zzl<K, V> {
    private final Map<K, V> zzaZh = new HashMap();
    private final int zzaZi;
    private final zzm.zza<K, V> zzaZj;
    private int zzaZk;

    zzcw(int i, zzm.zza<K, V> zzaVar) {
        this.zzaZi = i;
        this.zzaZj = zzaVar;
    }

    @Override // com.google.android.gms.tagmanager.zzl
    public synchronized V get(K key) {
        return this.zzaZh.get(key);
    }

    @Override // com.google.android.gms.tagmanager.zzl
    public synchronized void zzf(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        this.zzaZk += this.zzaZj.sizeOf(k, v);
        if (this.zzaZk > this.zzaZi) {
            Iterator<Map.Entry<K, V>> it = this.zzaZh.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<K, V> next = it.next();
                this.zzaZk -= this.zzaZj.sizeOf(next.getKey(), next.getValue());
                it.remove();
                if (this.zzaZk <= this.zzaZi) {
                    break;
                }
            }
        }
        this.zzaZh.put(k, v);
    }
}
