package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzc;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.measurement.zze;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Product {
    Map<String, String> zzMp = new HashMap();

    void put(String name, String value) {
        zzx.zzb(name, "Name should be non-null");
        this.zzMp.put(name, value);
    }

    public Product setBrand(String value) {
        put("br", value);
        return this;
    }

    public Product setCategory(String value) {
        put("ca", value);
        return this;
    }

    public Product setCouponCode(String value) {
        put("cc", value);
        return this;
    }

    public Product setCustomDimension(int index, String value) {
        put(zzc.zzaa(index), value);
        return this;
    }

    public Product setCustomMetric(int index, int value) {
        put(zzc.zzab(index), Integer.toString(value));
        return this;
    }

    public Product setId(String value) {
        put("id", value);
        return this;
    }

    public Product setName(String value) {
        put("nm", value);
        return this;
    }

    public Product setPosition(int value) {
        put("ps", Integer.toString(value));
        return this;
    }

    public Product setPrice(double value) {
        put("pr", Double.toString(value));
        return this;
    }

    public Product setQuantity(int value) {
        put("qt", Integer.toString(value));
        return this;
    }

    public Product setVariant(String value) {
        put("va", value);
        return this;
    }

    public String toString() {
        return zze.zzH(this.zzMp);
    }

    public Map<String, String> zzaX(String str) {
        HashMap map = new HashMap();
        for (Map.Entry<String, String> entry : this.zzMp.entrySet()) {
            map.put(str + entry.getKey(), entry.getValue());
        }
        return map;
    }
}
