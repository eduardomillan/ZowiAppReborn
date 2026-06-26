package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: loaded from: classes.dex */
class zzce extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.REGEX_GROUP.toString();
    private static final String zzaYe = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaYf = com.google.android.gms.internal.zzae.ARG1.toString();
    private static final String zzaYg = com.google.android.gms.internal.zzae.IGNORE_CASE.toString();
    private static final String zzaYh = com.google.android.gms.internal.zzae.GROUP.toString();

    public zzce() {
        super(ID, zzaYe, zzaYf);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        int iIntValue;
        zzag.zza zzaVar = map.get(zzaYe);
        zzag.zza zzaVar2 = map.get(zzaYf);
        if (zzaVar == null || zzaVar == zzdf.zzDX() || zzaVar2 == null || zzaVar2 == zzdf.zzDX()) {
            return zzdf.zzDX();
        }
        int i = zzdf.zzk(map.get(zzaYg)).booleanValue() ? 66 : 64;
        zzag.zza zzaVar3 = map.get(zzaYh);
        if (zzaVar3 != null) {
            Long lZzi = zzdf.zzi(zzaVar3);
            if (lZzi == zzdf.zzDS()) {
                return zzdf.zzDX();
            }
            iIntValue = lZzi.intValue();
            if (iIntValue < 0) {
                return zzdf.zzDX();
            }
        } else {
            iIntValue = 1;
        }
        try {
            String strZzg = zzdf.zzg(zzaVar);
            String strGroup = null;
            Matcher matcher = Pattern.compile(zzdf.zzg(zzaVar2), i).matcher(strZzg);
            if (matcher.find() && matcher.groupCount() >= iIntValue) {
                strGroup = matcher.group(iIntValue);
            }
            return strGroup == null ? zzdf.zzDX() : zzdf.zzQ(strGroup);
        } catch (PatternSyntaxException e) {
            return zzdf.zzDX();
        }
    }
}
