package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    static final String[] zzaWv = "gtm.lifetime".toString().split("\\.");
    private static final Pattern zzaWw = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final LinkedList<Map<String, Object>> zzaWA;
    private final zzc zzaWB;
    private final CountDownLatch zzaWC;
    private final ConcurrentHashMap<zzb, Integer> zzaWx;
    private final Map<String, Object> zzaWy;
    private final ReentrantLock zzaWz;

    static final class zza {
        public final Object zzJy;
        public final String zzue;

        zza(String str, Object obj) {
            this.zzue = str;
            this.zzJy = obj;
        }

        public boolean equals(Object o) {
            if (!(o instanceof zza)) {
                return false;
            }
            zza zzaVar = (zza) o;
            return this.zzue.equals(zzaVar.zzue) && this.zzJy.equals(zzaVar.zzJy);
        }

        public int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zzue.hashCode()), Integer.valueOf(this.zzJy.hashCode())});
        }

        public String toString() {
            return "Key: " + this.zzue + " value: " + this.zzJy.toString();
        }
    }

    interface zzb {
        void zzJ(Map<String, Object> map);
    }

    interface zzc {

        public interface zza {
            void zzw(List<zza> list);
        }

        void zza(zza zzaVar);

        void zza(List<zza> list, long j);

        void zzeK(String str);
    }

    DataLayer() {
        this(new zzc() { // from class: com.google.android.gms.tagmanager.DataLayer.1
            @Override // com.google.android.gms.tagmanager.DataLayer.zzc
            public void zza(zzc.zza zzaVar) {
                zzaVar.zzw(new ArrayList());
            }

            @Override // com.google.android.gms.tagmanager.DataLayer.zzc
            public void zza(List<zza> list, long j) {
            }

            @Override // com.google.android.gms.tagmanager.DataLayer.zzc
            public void zzeK(String str) {
            }
        });
    }

    DataLayer(zzc persistentStore) {
        this.zzaWB = persistentStore;
        this.zzaWx = new ConcurrentHashMap<>();
        this.zzaWy = new HashMap();
        this.zzaWz = new ReentrantLock();
        this.zzaWA = new LinkedList<>();
        this.zzaWC = new CountDownLatch(1);
        zzCF();
    }

    public static List<Object> listOf(Object... objects) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objects) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap map = new HashMap();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= objects.length) {
                return map;
            }
            if (!(objects[i2] instanceof String)) {
                throw new IllegalArgumentException("key is not a string: " + objects[i2]);
            }
            map.put((String) objects[i2], objects[i2 + 1]);
            i = i2 + 2;
        }
    }

    private void zzCF() {
        this.zzaWB.zza(new zzc.zza() { // from class: com.google.android.gms.tagmanager.DataLayer.2
            @Override // com.google.android.gms.tagmanager.DataLayer.zzc.zza
            public void zzw(List<zza> list) {
                for (zza zzaVar : list) {
                    DataLayer.this.zzL(DataLayer.this.zzk(zzaVar.zzue, zzaVar.zzJy));
                }
                DataLayer.this.zzaWC.countDown();
            }
        });
    }

    private void zzCG() {
        int i = 0;
        do {
            int i2 = i;
            Map<String, Object> mapPoll = this.zzaWA.poll();
            if (mapPoll == null) {
                return;
            }
            zzQ(mapPoll);
            i = i2 + 1;
        } while (i <= 500);
        this.zzaWA.clear();
        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzL(Map<String, Object> map) {
        this.zzaWz.lock();
        try {
            this.zzaWA.offer(map);
            if (this.zzaWz.getHoldCount() == 1) {
                zzCG();
            }
            zzM(map);
        } finally {
            this.zzaWz.unlock();
        }
    }

    private void zzM(Map<String, Object> map) {
        Long lZzN = zzN(map);
        if (lZzN == null) {
            return;
        }
        List<zza> listZzP = zzP(map);
        listZzP.remove("gtm.lifetime");
        this.zzaWB.zza(listZzP, lZzN.longValue());
    }

    private Long zzN(Map<String, Object> map) {
        Object objZzO = zzO(map);
        if (objZzO == null) {
            return null;
        }
        return zzeJ(objZzO.toString());
    }

    private Object zzO(Map<String, Object> map) {
        String[] strArr = zzaWv;
        int length = strArr.length;
        int i = 0;
        Object obj = map;
        while (i < length) {
            String str = strArr[i];
            if (!(obj instanceof Map)) {
                return null;
            }
            i++;
            obj = ((Map) obj).get(str);
        }
        return obj;
    }

    private List<zza> zzP(Map<String, Object> map) {
        ArrayList arrayList = new ArrayList();
        zza(map, "", arrayList);
        return arrayList;
    }

    private void zzQ(Map<String, Object> map) {
        synchronized (this.zzaWy) {
            for (String str : map.keySet()) {
                zzd(zzk(str, map.get(str)), this.zzaWy);
            }
        }
        zzR(map);
    }

    private void zzR(Map<String, Object> map) {
        Iterator<zzb> it = this.zzaWx.keySet().iterator();
        while (it.hasNext()) {
            it.next().zzJ(map);
        }
    }

    private void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String str2 = str + (str.length() == 0 ? "" : ".") + entry.getKey();
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), str2, collection);
            } else if (!str2.equals("gtm.lifetime")) {
                collection.add(new zza(str2, entry.getValue()));
            }
        }
    }

    static Long zzeJ(String str) {
        long j;
        Matcher matcher = zzaWw.matcher(str);
        if (!matcher.matches()) {
            zzbg.zzaG("unknown _lifetime: " + str);
            return null;
        }
        try {
            j = Long.parseLong(matcher.group(1));
        } catch (NumberFormatException e) {
            zzbg.zzaH("illegal number in _lifetime value: " + str);
            j = 0;
        }
        if (j <= 0) {
            zzbg.zzaG("non-positive _lifetime: " + str);
            return null;
        }
        String strGroup = matcher.group(2);
        if (strGroup.length() == 0) {
            return Long.valueOf(j);
        }
        switch (strGroup.charAt(0)) {
            case 'd':
                break;
            case 'h':
                break;
            case 'm':
                break;
            case 's':
                break;
            default:
                zzbg.zzaH("unknown units in _lifetime: " + str);
                break;
        }
        return null;
    }

    public Object get(String key) {
        synchronized (this.zzaWy) {
            Map<String, Object> map = this.zzaWy;
            String[] strArrSplit = key.split("\\.");
            int length = strArrSplit.length;
            Object obj = map;
            int i = 0;
            while (i < length) {
                String str = strArrSplit[i];
                if (!(obj instanceof Map)) {
                    return null;
                }
                Object obj2 = ((Map) obj).get(str);
                if (obj2 == null) {
                    return null;
                }
                i++;
                obj = obj2;
            }
            return obj;
        }
    }

    public void push(String key, Object value) {
        push(zzk(key, value));
    }

    public void push(Map<String, Object> update) {
        try {
            this.zzaWC.await();
        } catch (InterruptedException e) {
            zzbg.zzaH("DataLayer.push: unexpected InterruptedException");
        }
        zzL(update);
    }

    public void pushEvent(String eventName, Map<String, Object> update) {
        HashMap map = new HashMap(update);
        map.put("event", eventName);
        push(map);
    }

    public String toString() {
        String string;
        synchronized (this.zzaWy) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.zzaWy.entrySet()) {
                sb.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", entry.getKey(), entry.getValue()));
            }
            string = sb.toString();
        }
        return string;
    }

    void zza(zzb zzbVar) {
        this.zzaWx.put(zzbVar, 0);
    }

    void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return;
            }
            Object obj = list.get(i2);
            if (obj instanceof List) {
                if (!(list2.get(i2) instanceof List)) {
                    list2.set(i2, new ArrayList());
                }
                zzb((List) obj, (List) list2.get(i2));
            } else if (obj instanceof Map) {
                if (!(list2.get(i2) instanceof Map)) {
                    list2.set(i2, new HashMap());
                }
                zzd((Map) obj, (Map) list2.get(i2));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i2, obj);
            }
            i = i2 + 1;
        }
    }

    void zzd(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zzb((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zzd((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    void zzeI(String str) {
        push(str, null);
        this.zzaWB.zzeK(str);
    }

    Map<String, Object> zzk(String str, Object obj) {
        HashMap map = new HashMap();
        String[] strArrSplit = str.toString().split("\\.");
        int i = 0;
        HashMap map2 = map;
        while (i < strArrSplit.length - 1) {
            HashMap map3 = new HashMap();
            map2.put(strArrSplit[i], map3);
            i++;
            map2 = map3;
        }
        map2.put(strArrSplit[strArrSplit.length - 1], obj);
        return map;
    }
}
