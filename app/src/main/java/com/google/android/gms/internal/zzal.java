package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.internal.zzar;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzal extends zzak {
    private static Method zzmY;
    private static Method zzmZ;
    private static Method zzna;
    private static Method zznb;
    private static Method zznc;
    private static Method zznd;
    private static Method zzne;
    private static Method zznf;
    private static Method zzng;
    private static Method zznh;
    private static Method zzni;
    private static Method zznj;
    private static Method zznk;
    private static String zznl;
    private static String zznm;
    private static String zznn;
    private static zzar zzno;
    private static long startTime = 0;
    static boolean zznp = false;

    static class zza extends Exception {
        public zza() {
        }

        public zza(Throwable th) {
            super(th);
        }
    }

    protected zzal(Context context, zzap zzapVar, zzaq zzaqVar) {
        super(context, zzapVar, zzaqVar);
    }

    static String zzU() throws zza {
        if (zznl == null) {
            throw new zza();
        }
        return zznl;
    }

    static Long zzV() throws zza {
        if (zzmY == null) {
            throw new zza();
        }
        try {
            return (Long) zzmY.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static String zzW() throws zza {
        if (zzna == null) {
            throw new zza();
        }
        try {
            return (String) zzna.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static Long zzX() throws zza {
        if (zzmZ == null) {
            throw new zza();
        }
        try {
            return (Long) zzmZ.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static String zza(Context context, zzap zzapVar) throws zza {
        if (zznm != null) {
            return zznm;
        }
        if (zznb == null) {
            throw new zza();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) zznb.invoke(null, context);
            if (byteBuffer == null) {
                throw new zza();
            }
            zznm = zzapVar.zza(byteBuffer.array(), true);
            return zznm;
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static ArrayList<Long> zza(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws zza {
        if (zznc == null || motionEvent == null) {
            throw new zza();
        }
        try {
            return (ArrayList) zznc.invoke(null, motionEvent, displayMetrics);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    protected static synchronized void zza(String str, Context context, zzap zzapVar) {
        if (!zznp) {
            try {
                zzno = new zzar(zzapVar, null);
                zznl = str;
                zzl(context);
                startTime = zzV().longValue();
                zznp = true;
            } catch (zza e) {
            } catch (UnsupportedOperationException e2) {
            }
        }
    }

    static String zzb(Context context, zzap zzapVar) throws zza {
        if (zznn != null) {
            return zznn;
        }
        if (zzne == null) {
            throw new zza();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) zzne.invoke(null, context);
            if (byteBuffer == null) {
                throw new zza();
            }
            zznn = zzapVar.zza(byteBuffer.array(), true);
            return zznn;
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    private static String zzb(byte[] bArr, String str) throws zza {
        try {
            return new String(zzno.zzc(bArr, str), "UTF-8");
        } catch (zzar.zza e) {
            throw new zza(e);
        } catch (UnsupportedEncodingException e2) {
            throw new zza(e2);
        }
    }

    static String zze(Context context) throws zza {
        if (zznd == null) {
            throw new zza();
        }
        try {
            String str = (String) zznd.invoke(null, context);
            if (str == null) {
                throw new zza();
            }
            return str;
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static String zzf(Context context) throws zza {
        if (zznh == null) {
            throw new zza();
        }
        try {
            return (String) zznh.invoke(null, context);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static Long zzg(Context context) throws zza {
        if (zzni == null) {
            throw new zza();
        }
        try {
            return (Long) zzni.invoke(null, context);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static ArrayList<Long> zzh(Context context) throws zza {
        if (zznf == null) {
            throw new zza();
        }
        try {
            ArrayList<Long> arrayList = (ArrayList) zznf.invoke(null, context);
            if (arrayList == null || arrayList.size() != 2) {
                throw new zza();
            }
            return arrayList;
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static int[] zzi(Context context) throws zza {
        if (zzng == null) {
            throw new zza();
        }
        try {
            return (int[]) zzng.invoke(null, context);
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static int zzj(Context context) throws zza {
        if (zznj == null) {
            throw new zza();
        }
        try {
            return ((Integer) zznj.invoke(null, context)).intValue();
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    static int zzk(Context context) throws zza {
        if (zznk == null) {
            throw new zza();
        }
        try {
            return ((Integer) zznk.invoke(null, context)).intValue();
        } catch (IllegalAccessException e) {
            throw new zza(e);
        } catch (InvocationTargetException e2) {
            throw new zza(e2);
        }
    }

    private static void zzl(Context context) throws zza {
        try {
            byte[] bArrZzl = zzno.zzl(zzat.getKey());
            byte[] bArrZzc = zzno.zzc(bArrZzl, zzat.zzad());
            File cacheDir = context.getCacheDir();
            if (cacheDir == null && (cacheDir = context.getDir("dex", 0)) == null) {
                throw new zza();
            }
            File file = cacheDir;
            File fileCreateTempFile = File.createTempFile("ads", ".jar", file);
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            fileOutputStream.write(bArrZzc, 0, bArrZzc.length);
            fileOutputStream.close();
            try {
                DexClassLoader dexClassLoader = new DexClassLoader(fileCreateTempFile.getAbsolutePath(), file.getAbsolutePath(), null, context.getClassLoader());
                Class clsLoadClass = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzam()));
                Class clsLoadClass2 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzaA()));
                Class clsLoadClass3 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzau()));
                Class clsLoadClass4 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzaq()));
                Class clsLoadClass5 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzaC()));
                Class clsLoadClass6 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzao()));
                Class clsLoadClass7 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzay()));
                Class clsLoadClass8 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzaw()));
                Class clsLoadClass9 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzak()));
                Class clsLoadClass10 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzai()));
                Class clsLoadClass11 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzag()));
                Class clsLoadClass12 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzas()));
                Class clsLoadClass13 = dexClassLoader.loadClass(zzb(bArrZzl, zzat.zzae()));
                zzmY = clsLoadClass.getMethod(zzb(bArrZzl, zzat.zzan()), new Class[0]);
                zzmZ = clsLoadClass2.getMethod(zzb(bArrZzl, zzat.zzaB()), new Class[0]);
                zzna = clsLoadClass3.getMethod(zzb(bArrZzl, zzat.zzav()), new Class[0]);
                zznb = clsLoadClass4.getMethod(zzb(bArrZzl, zzat.zzar()), Context.class);
                zznc = clsLoadClass5.getMethod(zzb(bArrZzl, zzat.zzaD()), MotionEvent.class, DisplayMetrics.class);
                zznd = clsLoadClass6.getMethod(zzb(bArrZzl, zzat.zzap()), Context.class);
                zzne = clsLoadClass7.getMethod(zzb(bArrZzl, zzat.zzaz()), Context.class);
                zznf = clsLoadClass8.getMethod(zzb(bArrZzl, zzat.zzax()), Context.class);
                zzng = clsLoadClass9.getMethod(zzb(bArrZzl, zzat.zzal()), Context.class);
                zznh = clsLoadClass10.getMethod(zzb(bArrZzl, zzat.zzaj()), Context.class);
                zzni = clsLoadClass11.getMethod(zzb(bArrZzl, zzat.zzah()), Context.class);
                zznj = clsLoadClass12.getMethod(zzb(bArrZzl, zzat.zzat()), Context.class);
                zznk = clsLoadClass13.getMethod(zzb(bArrZzl, zzat.zzaf()), Context.class);
            } finally {
                String name = fileCreateTempFile.getName();
                fileCreateTempFile.delete();
                new File(file, name.replace(".jar", ".dex")).delete();
            }
        } catch (zzar.zza e) {
            throw new zza(e);
        } catch (FileNotFoundException e2) {
            throw new zza(e2);
        } catch (IOException e3) {
            throw new zza(e3);
        } catch (ClassNotFoundException e4) {
            throw new zza(e4);
        } catch (NoSuchMethodException e5) {
            throw new zza(e5);
        } catch (NullPointerException e6) {
            throw new zza(e6);
        }
    }

    @Override // com.google.android.gms.internal.zzak
    protected void zzc(Context context) {
        try {
            try {
                zza(1, zzW());
            } catch (IOException e) {
                return;
            }
        } catch (zza e2) {
        }
        try {
            zza(2, zzU());
        } catch (zza e3) {
        }
        try {
            long jLongValue = zzV().longValue();
            zza(25, jLongValue);
            if (startTime != 0) {
                zza(17, jLongValue - startTime);
                zza(23, startTime);
            }
        } catch (zza e4) {
        }
        try {
            ArrayList<Long> arrayListZzh = zzh(context);
            zza(31, arrayListZzh.get(0).longValue());
            zza(32, arrayListZzh.get(1).longValue());
        } catch (zza e5) {
        }
        try {
            zza(33, zzX().longValue());
        } catch (zza e6) {
        }
        try {
            zza(27, zza(context, this.zzmW));
        } catch (zza e7) {
        }
        try {
            zza(29, zzb(context, this.zzmW));
        } catch (zza e8) {
        }
        try {
            int[] iArrZzi = zzi(context);
            zza(5, iArrZzi[0]);
            zza(6, iArrZzi[1]);
        } catch (zza e9) {
        }
        try {
            zza(12, zzj(context));
        } catch (zza e10) {
        }
        try {
            zza(3, zzk(context));
        } catch (zza e11) {
        }
        try {
            zza(34, zzf(context));
        } catch (zza e12) {
        }
        try {
            zza(35, zzg(context).longValue());
        } catch (zza e13) {
        }
    }

    @Override // com.google.android.gms.internal.zzak
    protected void zzd(Context context) {
        try {
            try {
                zza(2, zzU());
            } catch (IOException e) {
                return;
            }
        } catch (zza e2) {
        }
        try {
            zza(1, zzW());
        } catch (zza e3) {
        }
        try {
            zza(25, zzV().longValue());
        } catch (zza e4) {
        }
        try {
            ArrayList<Long> arrayListZza = zza(this.zzmU, this.zzmV);
            zza(14, arrayListZza.get(0).longValue());
            zza(15, arrayListZza.get(1).longValue());
            if (arrayListZza.size() >= 3) {
                zza(16, arrayListZza.get(2).longValue());
            }
        } catch (zza e5) {
        }
        try {
            zza(34, zzf(context));
        } catch (zza e6) {
        }
        try {
            zza(35, zzg(context).longValue());
        } catch (zza e7) {
        }
    }
}
