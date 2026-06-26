package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzcd {

    @zzgr
    public static final zzcd zzvK = new zzcd() { // from class: com.google.android.gms.internal.zzcd.1
        @Override // com.google.android.gms.internal.zzcd
        public String zzd(String str, String str2) {
            return str2;
        }
    };

    @zzgr
    public static final zzcd zzvL = new zzcd() { // from class: com.google.android.gms.internal.zzcd.2
        @Override // com.google.android.gms.internal.zzcd
        public String zzd(String str, String str2) {
            return str != null ? str : str2;
        }
    };

    @zzgr
    public static final zzcd zzvM = new zzcd() { // from class: com.google.android.gms.internal.zzcd.3
        private String zzS(String str) {
            if (TextUtils.isEmpty(str)) {
                return str;
            }
            int i = 0;
            int length = str.length();
            while (i < str.length() && str.charAt(i) == ',') {
                i++;
            }
            while (length > 0 && str.charAt(length - 1) == ',') {
                length--;
            }
            return (i == 0 && length == str.length()) ? str : str.substring(i, length);
        }

        @Override // com.google.android.gms.internal.zzcd
        public String zzd(String str, String str2) {
            String strZzS = zzS(str);
            String strZzS2 = zzS(str2);
            return TextUtils.isEmpty(strZzS) ? strZzS2 : TextUtils.isEmpty(strZzS2) ? strZzS : strZzS + "," + strZzS2;
        }
    };

    public final void zza(Map<String, String> map, String str, String str2) {
        map.put(str, zzd(map.get(str), str2));
    }

    public abstract String zzd(String str, String str2);
}
