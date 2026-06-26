package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.internal.zzmk;
import com.google.android.gms.internal.zzmu;
import com.google.android.gms.internal.zzmv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class FastJsonResponse {

    public static class Field<I, O> implements SafeParcelable {
        public static final com.google.android.gms.common.server.response.zza CREATOR = new com.google.android.gms.common.server.response.zza();
        private final int mVersionCode;
        protected final int zzagU;
        protected final boolean zzagV;
        protected final int zzagW;
        protected final boolean zzagX;
        protected final String zzagY;
        protected final int zzagZ;
        protected final Class<? extends FastJsonResponse> zzaha;
        protected final String zzahb;
        private FieldMappingDictionary zzahc;
        private zza<I, O> zzahd;

        Field(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, ConverterWrapper converterWrapper) {
            this.mVersionCode = i;
            this.zzagU = i2;
            this.zzagV = z;
            this.zzagW = i3;
            this.zzagX = z2;
            this.zzagY = str;
            this.zzagZ = i4;
            if (str2 == null) {
                this.zzaha = null;
                this.zzahb = null;
            } else {
                this.zzaha = SafeParcelResponse.class;
                this.zzahb = str2;
            }
            if (converterWrapper == null) {
                this.zzahd = null;
            } else {
                this.zzahd = (zza<I, O>) converterWrapper.zzpz();
            }
        }

        protected Field(int typeIn, boolean typeInArray, int typeOut, boolean typeOutArray, String outputFieldName, int safeParcelableFieldId, Class<? extends FastJsonResponse> concreteType, zza<I, O> converter) {
            this.mVersionCode = 1;
            this.zzagU = typeIn;
            this.zzagV = typeInArray;
            this.zzagW = typeOut;
            this.zzagX = typeOutArray;
            this.zzagY = outputFieldName;
            this.zzagZ = safeParcelableFieldId;
            this.zzaha = concreteType;
            if (concreteType == null) {
                this.zzahb = null;
            } else {
                this.zzahb = concreteType.getCanonicalName();
            }
            this.zzahd = converter;
        }

        public static Field zza(String str, int i, zza<?, ?> zzaVar, boolean z) {
            return new Field(zzaVar.zzpB(), z, zzaVar.zzpC(), false, str, i, null, zzaVar);
        }

        public static <T extends FastJsonResponse> Field<T, T> zza(String str, int i, Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i, cls, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> zzb(String str, int i, Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i, cls, null);
        }

        public static Field<Integer, Integer> zzj(String str, int i) {
            return new Field<>(0, false, 0, false, str, i, null, null);
        }

        public static Field<Double, Double> zzk(String str, int i) {
            return new Field<>(4, false, 4, false, str, i, null, null);
        }

        public static Field<Boolean, Boolean> zzl(String str, int i) {
            return new Field<>(6, false, 6, false, str, i, null, null);
        }

        public static Field<String, String> zzm(String str, int i) {
            return new Field<>(7, false, 7, false, str, i, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> zzn(String str, int i) {
            return new Field<>(7, true, 7, true, str, i, null, null);
        }

        public I convertBack(O output) {
            return this.zzahd.convertBack(output);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            com.google.android.gms.common.server.response.zza zzaVar = CREATOR;
            return 0;
        }

        public int getVersionCode() {
            return this.mVersionCode;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Field\n");
            sb.append("            versionCode=").append(this.mVersionCode).append('\n');
            sb.append("                 typeIn=").append(this.zzagU).append('\n');
            sb.append("            typeInArray=").append(this.zzagV).append('\n');
            sb.append("                typeOut=").append(this.zzagW).append('\n');
            sb.append("           typeOutArray=").append(this.zzagX).append('\n');
            sb.append("        outputFieldName=").append(this.zzagY).append('\n');
            sb.append("      safeParcelFieldId=").append(this.zzagZ).append('\n');
            sb.append("       concreteTypeName=").append(zzpM()).append('\n');
            if (zzpL() != null) {
                sb.append("     concreteType.class=").append(zzpL().getCanonicalName()).append('\n');
            }
            sb.append("          converterName=").append(this.zzahd == null ? "null" : this.zzahd.getClass().getCanonicalName()).append('\n');
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            com.google.android.gms.common.server.response.zza zzaVar = CREATOR;
            com.google.android.gms.common.server.response.zza.zza(this, out, flags);
        }

        public void zza(FieldMappingDictionary fieldMappingDictionary) {
            this.zzahc = fieldMappingDictionary;
        }

        public int zzpB() {
            return this.zzagU;
        }

        public int zzpC() {
            return this.zzagW;
        }

        public Field<I, O> zzpG() {
            return new Field<>(this.mVersionCode, this.zzagU, this.zzagV, this.zzagW, this.zzagX, this.zzagY, this.zzagZ, this.zzahb, zzpO());
        }

        public boolean zzpH() {
            return this.zzagV;
        }

        public boolean zzpI() {
            return this.zzagX;
        }

        public String zzpJ() {
            return this.zzagY;
        }

        public int zzpK() {
            return this.zzagZ;
        }

        public Class<? extends FastJsonResponse> zzpL() {
            return this.zzaha;
        }

        String zzpM() {
            if (this.zzahb == null) {
                return null;
            }
            return this.zzahb;
        }

        public boolean zzpN() {
            return this.zzahd != null;
        }

        ConverterWrapper zzpO() {
            if (this.zzahd == null) {
                return null;
            }
            return ConverterWrapper.zza(this.zzahd);
        }

        public Map<String, Field<?, ?>> zzpP() {
            zzx.zzw(this.zzahb);
            zzx.zzw(this.zzahc);
            return this.zzahc.zzcw(this.zzahb);
        }
    }

    public interface zza<I, O> {
        I convertBack(O o);

        int zzpB();

        int zzpC();
    }

    private void zza(StringBuilder sb, Field field, Object obj) {
        if (field.zzpB() == 11) {
            sb.append(field.zzpL().cast(obj).toString());
        } else {
            if (field.zzpB() != 7) {
                sb.append(obj);
                return;
            }
            sb.append("\"");
            sb.append(zzmu.zzcz((String) obj));
            sb.append("\"");
        }
    }

    private void zza(StringBuilder sb, Field field, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(sb, field, obj);
            }
        }
        sb.append("]");
    }

    public String toString() {
        Map<String, Field<?, ?>> mapZzpD = zzpD();
        StringBuilder sb = new StringBuilder(100);
        for (String str : mapZzpD.keySet()) {
            Field<?, ?> field = mapZzpD.get(str);
            if (zza(field)) {
                Object objZza = zza(field, zzb(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"").append(str).append("\":");
                if (objZza != null) {
                    switch (field.zzpC()) {
                        case 8:
                            sb.append("\"").append(zzmk.zzi((byte[]) objZza)).append("\"");
                            break;
                        case 9:
                            sb.append("\"").append(zzmk.zzj((byte[]) objZza)).append("\"");
                            break;
                        case 10:
                            zzmv.zza(sb, (HashMap) objZza);
                            break;
                        default:
                            if (field.zzpH()) {
                                zza(sb, (Field) field, (ArrayList<Object>) objZza);
                            } else {
                                zza(sb, field, objZza);
                            }
                            break;
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <O, I> I zza(Field<I, O> field, Object obj) {
        return ((Field) field).zzahd != null ? field.convertBack(obj) : obj;
    }

    protected boolean zza(Field field) {
        return field.zzpC() == 11 ? field.zzpI() ? zzcv(field.zzpJ()) : zzcu(field.zzpJ()) : zzct(field.zzpJ());
    }

    protected Object zzb(Field field) {
        String strZzpJ = field.zzpJ();
        if (field.zzpL() == null) {
            return zzcs(field.zzpJ());
        }
        zzx.zza(zzcs(field.zzpJ()) == null, "Concrete field shouldn't be value object: %s", field.zzpJ());
        HashMap<String, Object> mapZzpF = field.zzpI() ? zzpF() : zzpE();
        if (mapZzpF != null) {
            return mapZzpF.get(strZzpJ);
        }
        try {
            return getClass().getMethod("get" + Character.toUpperCase(strZzpJ.charAt(0)) + strZzpJ.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Object zzcs(String str);

    protected abstract boolean zzct(String str);

    protected boolean zzcu(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean zzcv(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    public abstract Map<String, Field<?, ?>> zzpD();

    public HashMap<String, Object> zzpE() {
        return null;
    }

    public HashMap<String, Object> zzpF() {
        return null;
    }
}
