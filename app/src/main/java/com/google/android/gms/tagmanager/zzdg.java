package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.zzag;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class zzdg extends zzdd {
    private static final String ID = com.google.android.gms.internal.zzad.UNIVERSAL_ANALYTICS.toString();
    private static final String zzaZS = com.google.android.gms.internal.zzae.ACCOUNT.toString();
    private static final String zzaZT = com.google.android.gms.internal.zzae.ANALYTICS_PASS_THROUGH.toString();
    private static final String zzaZU = com.google.android.gms.internal.zzae.ENABLE_ECOMMERCE.toString();
    private static final String zzaZV = com.google.android.gms.internal.zzae.ECOMMERCE_USE_DATA_LAYER.toString();
    private static final String zzaZW = com.google.android.gms.internal.zzae.ECOMMERCE_MACRO_DATA.toString();
    private static final String zzaZX = com.google.android.gms.internal.zzae.ANALYTICS_FIELDS.toString();
    private static final String zzaZY = com.google.android.gms.internal.zzae.TRACK_TRANSACTION.toString();
    private static final String zzaZZ = com.google.android.gms.internal.zzae.TRANSACTION_DATALAYER_MAP.toString();
    private static final String zzbaa = com.google.android.gms.internal.zzae.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static final List<String> zzbab = Arrays.asList(ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, ProductAction.ACTION_CHECKOUT_OPTION, "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, ProductAction.ACTION_PURCHASE, ProductAction.ACTION_REFUND);
    private static final Pattern zzbac = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzbad = Pattern.compile("metric(\\d+)");
    private static Map<String, String> zzbae;
    private static Map<String, String> zzbaf;
    private final DataLayer zzaVR;
    private final Set<String> zzbag;
    private final zzdc zzbah;

    public zzdg(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new zzdc(context));
    }

    zzdg(Context context, DataLayer dataLayer, zzdc zzdcVar) {
        super(ID, new String[0]);
        this.zzaVR = dataLayer;
        this.zzbah = zzdcVar;
        this.zzbag = new HashSet();
        this.zzbag.add("");
        this.zzbag.add("0");
        this.zzbag.add("false");
    }

    private Promotion zzS(Map<String, String> map) {
        Promotion promotion = new Promotion();
        String str = map.get("id");
        if (str != null) {
            promotion.setId(String.valueOf(str));
        }
        String str2 = map.get("name");
        if (str2 != null) {
            promotion.setName(String.valueOf(str2));
        }
        String str3 = map.get("creative");
        if (str3 != null) {
            promotion.setCreative(String.valueOf(str3));
        }
        String str4 = map.get("position");
        if (str4 != null) {
            promotion.setPosition(String.valueOf(str4));
        }
        return promotion;
    }

    private Product zzT(Map<String, Object> map) {
        Product product = new Product();
        Object obj = map.get("id");
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        Object obj2 = map.get("name");
        if (obj2 != null) {
            product.setName(String.valueOf(obj2));
        }
        Object obj3 = map.get("brand");
        if (obj3 != null) {
            product.setBrand(String.valueOf(obj3));
        }
        Object obj4 = map.get("category");
        if (obj4 != null) {
            product.setCategory(String.valueOf(obj4));
        }
        Object obj5 = map.get("variant");
        if (obj5 != null) {
            product.setVariant(String.valueOf(obj5));
        }
        Object obj6 = map.get("coupon");
        if (obj6 != null) {
            product.setCouponCode(String.valueOf(obj6));
        }
        Object obj7 = map.get("position");
        if (obj7 != null) {
            product.setPosition(zzV(obj7).intValue());
        }
        Object obj8 = map.get("price");
        if (obj8 != null) {
            product.setPrice(zzU(obj8).doubleValue());
        }
        Object obj9 = map.get("quantity");
        if (obj9 != null) {
            product.setQuantity(zzV(obj9).intValue());
        }
        for (String str : map.keySet()) {
            Matcher matcher = zzbac.matcher(str);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str)));
                } catch (NumberFormatException e) {
                    zzbg.zzaH("illegal number in custom dimension value: " + str);
                }
            } else {
                Matcher matcher2 = zzbad.matcher(str);
                if (matcher2.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher2.group(1)), zzV(map.get(str)).intValue());
                    } catch (NumberFormatException e2) {
                        zzbg.zzaH("illegal number in custom metric value: " + str);
                    }
                }
            }
        }
        return product;
    }

    private Double zzU(Object obj) {
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cannot convert the object to Double: " + e.getMessage());
            }
        }
        if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        throw new RuntimeException("Cannot convert the object to Double: " + obj.toString());
    }

    private Map<String, String> zzU(Map<String, zzag.zza> map) {
        zzag.zza zzaVar = map.get(zzaZZ);
        if (zzaVar != null) {
            return zzc(zzaVar);
        }
        if (zzbae == null) {
            HashMap map2 = new HashMap();
            map2.put("transactionId", "&ti");
            map2.put("transactionAffiliation", "&ta");
            map2.put("transactionTax", "&tt");
            map2.put("transactionShipping", "&ts");
            map2.put("transactionTotal", "&tr");
            map2.put("transactionCurrency", "&cu");
            zzbae = map2;
        }
        return zzbae;
    }

    private Integer zzV(Object obj) {
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cannot convert the object to Integer: " + e.getMessage());
            }
        }
        if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        throw new RuntimeException("Cannot convert the object to Integer: " + obj.toString());
    }

    private Map<String, String> zzV(Map<String, zzag.zza> map) {
        zzag.zza zzaVar = map.get(zzbaa);
        if (zzaVar != null) {
            return zzc(zzaVar);
        }
        if (zzbaf == null) {
            HashMap map2 = new HashMap();
            map2.put("name", "&in");
            map2.put("sku", "&ic");
            map2.put("category", "&iv");
            map2.put("price", "&ip");
            map2.put("quantity", "&iq");
            map2.put("currency", "&cu");
            zzbaf = map2;
        }
        return zzbaf;
    }

    private void zza(Tracker tracker, Map<String, zzag.zza> map) {
        String strZzfj = zzfj("transactionId");
        if (strZzfj == null) {
            zzbg.e("Cannot find transactionId in data layer.");
            return;
        }
        LinkedList linkedList = new LinkedList();
        try {
            Map<String, String> mapZzm = zzm(map.get(zzaZX));
            mapZzm.put("&t", "transaction");
            for (Map.Entry<String, String> entry : zzU(map).entrySet()) {
                zze(mapZzm, entry.getValue(), zzfj(entry.getKey()));
            }
            linkedList.add(mapZzm);
            List<Map<String, String>> listZzfk = zzfk("transactionProducts");
            if (listZzfk != null) {
                for (Map<String, String> map2 : listZzfk) {
                    if (map2.get("name") == null) {
                        zzbg.e("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map<String, String> mapZzm2 = zzm(map.get(zzaZX));
                    mapZzm2.put("&t", "item");
                    mapZzm2.put("&ti", strZzfj);
                    for (Map.Entry<String, String> entry2 : zzV(map).entrySet()) {
                        zze(mapZzm2, entry2.getValue(), map2.get(entry2.getKey()));
                    }
                    linkedList.add(mapZzm2);
                }
            }
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                tracker.send((Map) it.next());
            }
        } catch (IllegalArgumentException e) {
            zzbg.zzb("Unable to send transaction", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void zzb(com.google.android.gms.analytics.Tracker r8, java.util.Map<java.lang.String, com.google.android.gms.internal.zzag.zza> r9) {
        /*
            Method dump skipped, instruction units count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzdg.zzb(com.google.android.gms.analytics.Tracker, java.util.Map):void");
    }

    private Map<String, String> zzc(zzag.zza zzaVar) {
        Object objZzl = zzdf.zzl(zzaVar);
        if (!(objZzl instanceof Map)) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((Map) objZzl).entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private ProductAction zzd(String str, Map<String, Object> map) {
        ProductAction productAction = new ProductAction(str);
        Object obj = map.get("id");
        if (obj != null) {
            productAction.setTransactionId(String.valueOf(obj));
        }
        Object obj2 = map.get("affiliation");
        if (obj2 != null) {
            productAction.setTransactionAffiliation(String.valueOf(obj2));
        }
        Object obj3 = map.get("coupon");
        if (obj3 != null) {
            productAction.setTransactionCouponCode(String.valueOf(obj3));
        }
        Object obj4 = map.get("list");
        if (obj4 != null) {
            productAction.setProductActionList(String.valueOf(obj4));
        }
        Object obj5 = map.get("option");
        if (obj5 != null) {
            productAction.setCheckoutOptions(String.valueOf(obj5));
        }
        Object obj6 = map.get("revenue");
        if (obj6 != null) {
            productAction.setTransactionRevenue(zzU(obj6).doubleValue());
        }
        Object obj7 = map.get("tax");
        if (obj7 != null) {
            productAction.setTransactionTax(zzU(obj7).doubleValue());
        }
        Object obj8 = map.get("shipping");
        if (obj8 != null) {
            productAction.setTransactionShipping(zzU(obj8).doubleValue());
        }
        Object obj9 = map.get("step");
        if (obj9 != null) {
            productAction.setCheckoutStep(zzV(obj9).intValue());
        }
        return productAction;
    }

    private void zze(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private String zzfj(String str) {
        Object obj = this.zzaVR.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private List<Map<String, String>> zzfk(String str) {
        Object obj = this.zzaVR.get(str);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        Iterator it = ((List) obj).iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof Map)) {
                throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
            }
        }
        return (List) obj;
    }

    private boolean zzj(Map<String, zzag.zza> map, String str) {
        zzag.zza zzaVar = map.get(str);
        if (zzaVar == null) {
            return false;
        }
        return zzdf.zzk(zzaVar).booleanValue();
    }

    private Map<String, String> zzm(zzag.zza zzaVar) {
        Map<String, String> mapZzc;
        if (zzaVar != null && (mapZzc = zzc(zzaVar)) != null) {
            String str = mapZzc.get("&aip");
            if (str != null && this.zzbag.contains(str.toLowerCase())) {
                mapZzc.remove("&aip");
            }
            return mapZzc;
        }
        return new HashMap();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ String zzCT() {
        return super.zzCT();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ Set zzCU() {
        return super.zzCU();
    }

    @Override // com.google.android.gms.tagmanager.zzdd, com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ boolean zzCo() {
        return super.zzCo();
    }

    @Override // com.google.android.gms.tagmanager.zzdd, com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ zzag.zza zzI(Map map) {
        return super.zzI(map);
    }

    @Override // com.google.android.gms.tagmanager.zzdd
    public void zzK(Map<String, zzag.zza> map) {
        Tracker trackerZzfb = this.zzbah.zzfb("_GTM_DEFAULT_TRACKER_");
        trackerZzfb.enableAdvertisingIdCollection(zzj(map, "collect_adid"));
        if (zzj(map, zzaZU)) {
            zzb(trackerZzfb, map);
            return;
        }
        if (zzj(map, zzaZT)) {
            trackerZzfb.send(zzm(map.get(zzaZX)));
        } else if (zzj(map, zzaZY)) {
            zza(trackerZzfb, map);
        } else {
            zzbg.zzaH("Ignoring unknown tag.");
        }
    }
}
