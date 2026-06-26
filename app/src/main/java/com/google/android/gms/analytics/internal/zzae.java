package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class zzae {
    private static volatile Logger zzPq;

    static {
        setLogger(new zzs());
    }

    public static Logger getLogger() {
        return zzPq;
    }

    public static void setLogger(Logger logger) {
        zzPq = logger;
    }

    public static void v(String msg) {
        zzaf zzafVarZzkG = zzaf.zzkG();
        if (zzafVarZzkG != null) {
            zzafVarZzkG.zzba(msg);
        } else if (zzN(0)) {
            Log.v(zzy.zzOg.get(), msg);
        }
        Logger logger = zzPq;
        if (logger != null) {
            logger.verbose(msg);
        }
    }

    public static boolean zzN(int i) {
        return getLogger() != null && getLogger().getLogLevel() <= i;
    }

    public static void zzaG(String str) {
        zzaf zzafVarZzkG = zzaf.zzkG();
        if (zzafVarZzkG != null) {
            zzafVarZzkG.zzbc(str);
        } else if (zzN(1)) {
            Log.i(zzy.zzOg.get(), str);
        }
        Logger logger = zzPq;
        if (logger != null) {
            logger.info(str);
        }
    }

    public static void zzaH(String str) {
        zzaf zzafVarZzkG = zzaf.zzkG();
        if (zzafVarZzkG != null) {
            zzafVarZzkG.zzbd(str);
        } else if (zzN(2)) {
            Log.w(zzy.zzOg.get(), str);
        }
        Logger logger = zzPq;
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static void zzf(String str, Object obj) {
        zzaf zzafVarZzkG = zzaf.zzkG();
        if (zzafVarZzkG != null) {
            zzafVarZzkG.zze(str, obj);
        } else if (zzN(3)) {
            Log.e(zzy.zzOg.get(), obj != null ? str + ":" + obj : str);
        }
        Logger logger = zzPq;
        if (logger != null) {
            logger.error(str);
        }
    }
}
