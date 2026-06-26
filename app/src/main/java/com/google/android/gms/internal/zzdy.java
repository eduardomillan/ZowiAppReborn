package com.google.android.gms.internal;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdy extends zzdv {
    private static final Set<String> zzyk = Collections.synchronizedSet(new HashSet());
    private static final DecimalFormat zzyl = new DecimalFormat("#,###");
    private File zzym;
    private boolean zzyn;

    public zzdy(zziz zzizVar) {
        super(zzizVar);
        File cacheDir = zzizVar.getContext().getCacheDir();
        if (cacheDir == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Context.getCacheDir() returned null");
            return;
        }
        this.zzym = new File(cacheDir, "admobVideoStreams");
        if (!this.zzym.isDirectory() && !this.zzym.mkdirs()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not create preload cache directory at " + this.zzym.getAbsolutePath());
            this.zzym = null;
        } else {
            if (this.zzym.setReadable(true, false) && this.zzym.setExecutable(true, false)) {
                return;
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not set cache file permissions at " + this.zzym.getAbsolutePath());
            this.zzym = null;
        }
    }

    private File zza(File file) {
        return new File(this.zzym, file.getName() + ".done");
    }

    private static void zzb(File file) {
        if (file.isFile()) {
            file.setLastModified(System.currentTimeMillis());
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    @Override // com.google.android.gms.internal.zzdv
    public void abort() {
        this.zzyn = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0323, code lost:
    
        throw new java.io.IOException("abort requested");
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0324, code lost:
    
        r2 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0325, code lost:
    
        r4 = "externalAbort";
        r5 = r10;
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0344, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x034c, code lost:
    
        if (com.google.android.gms.ads.internal.util.client.zzb.zzN(3) == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x034e, code lost:
    
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Preloaded " + com.google.android.gms.internal.zzdy.zzyl.format(r5) + " bytes from " + r27);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0377, code lost:
    
        r11.setReadable(true, false);
        zzb(r12);
        zza(r27, r11.getAbsolutePath(), r5);
        com.google.android.gms.internal.zzdy.zzyk.remove(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x038f, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25, types: [java.lang.String] */
    @Override // com.google.android.gms.internal.zzdv
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean zzab(java.lang.String r27) {
        /*
            Method dump skipped, instruction units count: 966
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdy.zzab(java.lang.String):boolean");
    }

    public int zzdK() {
        int i = 0;
        if (this.zzym != null) {
            for (File file : this.zzym.listFiles()) {
                if (!file.getName().endsWith(".done")) {
                    i++;
                }
            }
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean zzdL() {
        /*
            r11 = this;
            r6 = 0
            java.io.File r0 = r11.zzym
            if (r0 != 0) goto L6
        L5:
            return r6
        L6:
            r5 = 0
            r2 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            java.io.File r0 = r11.zzym
            java.io.File[] r8 = r0.listFiles()
            int r9 = r8.length
            r7 = r6
        L14:
            if (r7 >= r9) goto L33
            r4 = r8[r7]
            java.lang.String r0 = r4.getName()
            java.lang.String r1 = ".done"
            boolean r0 = r0.endsWith(r1)
            if (r0 != 0) goto L4c
            long r0 = r4.lastModified()
            int r10 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r10 >= 0) goto L4c
            r2 = r4
        L2d:
            int r3 = r7 + 1
            r7 = r3
            r5 = r2
            r2 = r0
            goto L14
        L33:
            if (r5 == 0) goto L4a
            boolean r0 = r5.delete()
            java.io.File r1 = r11.zza(r5)
            boolean r2 = r1.isFile()
            if (r2 == 0) goto L48
            boolean r1 = r1.delete()
            r0 = r0 & r1
        L48:
            r6 = r0
            goto L5
        L4a:
            r0 = r6
            goto L48
        L4c:
            r0 = r2
            r2 = r5
            goto L2d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdy.zzdL():boolean");
    }
}
