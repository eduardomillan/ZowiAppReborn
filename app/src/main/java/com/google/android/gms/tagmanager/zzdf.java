package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class zzdf {
    private static final Object zzaZJ = null;
    private static Long zzaZK = new Long(0);
    private static Double zzaZL = new Double(0.0d);
    private static zzde zzaZM = zzde.zzW(0);
    private static String zzaZN = new String("");
    private static Boolean zzaZO = new Boolean(false);
    private static List<Object> zzaZP = new ArrayList(0);
    private static Map<Object, Object> zzaZQ = new HashMap();
    private static zzag.zza zzaZR = zzQ(zzaZN);

    private static double getDouble(Object o) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        zzbg.e("getDouble received non-Number");
        return 0.0d;
    }

    public static Object zzDR() {
        return zzaZJ;
    }

    public static Long zzDS() {
        return zzaZK;
    }

    public static Double zzDT() {
        return zzaZL;
    }

    public static Boolean zzDU() {
        return zzaZO;
    }

    public static zzde zzDV() {
        return zzaZM;
    }

    public static String zzDW() {
        return zzaZN;
    }

    public static zzag.zza zzDX() {
        return zzaZR;
    }

    public static String zzL(Object obj) {
        return obj == null ? zzaZN : obj.toString();
    }

    public static zzde zzM(Object obj) {
        return obj instanceof zzde ? (zzde) obj : zzS(obj) ? zzde.zzW(zzT(obj)) : zzR(obj) ? zzde.zza(Double.valueOf(getDouble(obj))) : zzff(zzL(obj));
    }

    public static Long zzN(Object obj) {
        return zzS(obj) ? Long.valueOf(zzT(obj)) : zzfg(zzL(obj));
    }

    public static Double zzO(Object obj) {
        return zzR(obj) ? Double.valueOf(getDouble(obj)) : zzfh(zzL(obj));
    }

    public static Boolean zzP(Object obj) {
        return obj instanceof Boolean ? (Boolean) obj : zzfi(zzL(obj));
    }

    public static zzag.zza zzQ(Object obj) {
        boolean z = false;
        zzag.zza zzaVar = new zzag.zza();
        if (obj instanceof zzag.zza) {
            return (zzag.zza) obj;
        }
        if (obj instanceof String) {
            zzaVar.type = 1;
            zzaVar.zziU = (String) obj;
        } else if (obj instanceof List) {
            zzaVar.type = 2;
            List list = (List) obj;
            ArrayList arrayList = new ArrayList(list.size());
            Iterator it = list.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                zzag.zza zzaVarZzQ = zzQ(it.next());
                if (zzaVarZzQ == zzaZR) {
                    return zzaZR;
                }
                boolean z3 = z2 || zzaVarZzQ.zzje;
                arrayList.add(zzaVarZzQ);
                z2 = z3;
            }
            zzaVar.zziV = (zzag.zza[]) arrayList.toArray(new zzag.zza[0]);
            z = z2;
        } else if (obj instanceof Map) {
            zzaVar.type = 3;
            Set<Map.Entry> setEntrySet = ((Map) obj).entrySet();
            ArrayList arrayList2 = new ArrayList(setEntrySet.size());
            ArrayList arrayList3 = new ArrayList(setEntrySet.size());
            boolean z4 = false;
            for (Map.Entry entry : setEntrySet) {
                zzag.zza zzaVarZzQ2 = zzQ(entry.getKey());
                zzag.zza zzaVarZzQ3 = zzQ(entry.getValue());
                if (zzaVarZzQ2 == zzaZR || zzaVarZzQ3 == zzaZR) {
                    return zzaZR;
                }
                boolean z5 = z4 || zzaVarZzQ2.zzje || zzaVarZzQ3.zzje;
                arrayList2.add(zzaVarZzQ2);
                arrayList3.add(zzaVarZzQ3);
                z4 = z5;
            }
            zzaVar.zziW = (zzag.zza[]) arrayList2.toArray(new zzag.zza[0]);
            zzaVar.zziX = (zzag.zza[]) arrayList3.toArray(new zzag.zza[0]);
            z = z4;
        } else if (zzR(obj)) {
            zzaVar.type = 1;
            zzaVar.zziU = obj.toString();
        } else if (zzS(obj)) {
            zzaVar.type = 6;
            zzaVar.zzja = zzT(obj);
        } else {
            if (!(obj instanceof Boolean)) {
                zzbg.e("Converting to Value from unknown object type: " + (obj == null ? "null" : obj.getClass().toString()));
                return zzaZR;
            }
            zzaVar.type = 8;
            zzaVar.zzjb = ((Boolean) obj).booleanValue();
        }
        zzaVar.zzje = z;
        return zzaVar;
    }

    private static boolean zzR(Object obj) {
        return (obj instanceof Double) || (obj instanceof Float) || ((obj instanceof zzde) && ((zzde) obj).zzDM());
    }

    private static boolean zzS(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || ((obj instanceof zzde) && ((zzde) obj).zzDN());
    }

    private static long zzT(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzbg.e("getInt64 received non-Number");
        return 0L;
    }

    public static zzag.zza zzfe(String str) {
        zzag.zza zzaVar = new zzag.zza();
        zzaVar.type = 5;
        zzaVar.zziZ = str;
        return zzaVar;
    }

    private static zzde zzff(String str) {
        try {
            return zzde.zzfd(str);
        } catch (NumberFormatException e) {
            zzbg.e("Failed to convert '" + str + "' to a number.");
            return zzaZM;
        }
    }

    private static Long zzfg(String str) {
        zzde zzdeVarZzff = zzff(str);
        return zzdeVarZzff == zzaZM ? zzaZK : Long.valueOf(zzdeVarZzff.longValue());
    }

    private static Double zzfh(String str) {
        zzde zzdeVarZzff = zzff(str);
        return zzdeVarZzff == zzaZM ? zzaZL : Double.valueOf(zzdeVarZzff.doubleValue());
    }

    private static Boolean zzfi(String str) {
        return "true".equalsIgnoreCase(str) ? Boolean.TRUE : "false".equalsIgnoreCase(str) ? Boolean.FALSE : zzaZO;
    }

    public static String zzg(zzag.zza zzaVar) {
        return zzL(zzl(zzaVar));
    }

    public static zzde zzh(zzag.zza zzaVar) {
        return zzM(zzl(zzaVar));
    }

    public static Long zzi(zzag.zza zzaVar) {
        return zzN(zzl(zzaVar));
    }

    public static Double zzj(zzag.zza zzaVar) {
        return zzO(zzl(zzaVar));
    }

    public static Boolean zzk(zzag.zza zzaVar) {
        return zzP(zzl(zzaVar));
    }

    public static Object zzl(zzag.zza zzaVar) {
        int i = 0;
        if (zzaVar == null) {
            return zzaZJ;
        }
        switch (zzaVar.type) {
            case 1:
                break;
            case 2:
                ArrayList arrayList = new ArrayList(zzaVar.zziV.length);
                zzag.zza[] zzaVarArr = zzaVar.zziV;
                int length = zzaVarArr.length;
                while (i < length) {
                    Object objZzl = zzl(zzaVarArr[i]);
                    if (objZzl != zzaZJ) {
                        arrayList.add(objZzl);
                        i++;
                    }
                    break;
                }
                break;
            case 3:
                if (zzaVar.zziW.length == zzaVar.zziX.length) {
                    HashMap map = new HashMap(zzaVar.zziX.length);
                    while (i < zzaVar.zziW.length) {
                        Object objZzl2 = zzl(zzaVar.zziW[i]);
                        Object objZzl3 = zzl(zzaVar.zziX[i]);
                        if (objZzl2 != zzaZJ && objZzl3 != zzaZJ) {
                            map.put(objZzl2, objZzl3);
                            i++;
                        }
                    }
                } else {
                    zzbg.e("Converting an invalid value to object: " + zzaVar.toString());
                }
                break;
            case 4:
                zzbg.e("Trying to convert a macro reference to object");
                break;
            case 5:
                zzbg.e("Trying to convert a function id to object");
                break;
            case 6:
                break;
            case 7:
                StringBuffer stringBuffer = new StringBuffer();
                zzag.zza[] zzaVarArr2 = zzaVar.zzjc;
                int length2 = zzaVarArr2.length;
                while (i < length2) {
                    String strZzg = zzg(zzaVarArr2[i]);
                    if (strZzg != zzaZN) {
                        stringBuffer.append(strZzg);
                        i++;
                    }
                    break;
                }
                break;
            case 8:
                break;
            default:
                zzbg.e("Failed to convert a value of type: " + zzaVar.type);
                break;
        }
        return zzaZJ;
    }
}
