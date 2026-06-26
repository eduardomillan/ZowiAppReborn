package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzqz;
import com.google.android.gms.internal.zzrb;
import com.google.android.gms.internal.zzsd;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.tagmanager.zzp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class zzcn implements zzp.zzf {
    private final Context mContext;
    private final String zzaVQ;
    private zzbf<zzqz.zza> zzaYk;
    private final ExecutorService zzaYr = Executors.newSingleThreadExecutor();

    zzcn(Context context, String str) {
        this.mContext = context;
        this.zzaVQ = str;
    }

    private zzrb.zzc zza(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return zzaz.zzeT(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            zzbg.zzaF("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
            return null;
        } catch (JSONException e2) {
            zzbg.zzaH("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private void zzd(zzqz.zza zzaVar) throws IllegalArgumentException {
        if (zzaVar.zziR == null && zzaVar.zzbaj == null) {
            throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
        }
    }

    private zzrb.zzc zzv(byte[] bArr) {
        try {
            zzrb.zzc zzcVarZzb = zzrb.zzb(zzaf.zzf.zzc(bArr));
            if (zzcVarZzb == null) {
                return zzcVarZzb;
            }
            zzbg.v("The container was successfully loaded from the resource (using binary file)");
            return zzcVarZzb;
        } catch (zzrb.zzg e) {
            zzbg.zzaH("The resource file is invalid. The container from the binary file is invalid");
            return null;
        } catch (zzsd e2) {
            zzbg.e("The resource file is corrupted. The container cannot be extracted from the binary file");
            return null;
        }
    }

    @Override // com.google.android.gms.common.api.Releasable
    public synchronized void release() {
        this.zzaYr.shutdown();
    }

    @Override // com.google.android.gms.tagmanager.zzp.zzf
    public void zzCD() {
        this.zzaYr.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.zzcn.1
            @Override // java.lang.Runnable
            public void run() {
                zzcn.this.zzDu();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void zzDu() {
        /*
            r3 = this;
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r0 = r3.zzaYk
            if (r0 != 0) goto Lc
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Callback must be set before execute"
            r0.<init>(r1)
            throw r0
        Lc:
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r0 = r3.zzaYk
            r0.zzCC()
            java.lang.String r0 = "Attempting to load resource from disk"
            com.google.android.gms.tagmanager.zzbg.v(r0)
            com.google.android.gms.tagmanager.zzcb r0 = com.google.android.gms.tagmanager.zzcb.zzDm()
            com.google.android.gms.tagmanager.zzcb$zza r0 = r0.zzDn()
            com.google.android.gms.tagmanager.zzcb$zza r1 = com.google.android.gms.tagmanager.zzcb.zza.CONTAINER
            if (r0 == r1) goto L2e
            com.google.android.gms.tagmanager.zzcb r0 = com.google.android.gms.tagmanager.zzcb.zzDm()
            com.google.android.gms.tagmanager.zzcb$zza r0 = r0.zzDn()
            com.google.android.gms.tagmanager.zzcb$zza r1 = com.google.android.gms.tagmanager.zzcb.zza.CONTAINER_DEBUG
            if (r0 != r1) goto L46
        L2e:
            java.lang.String r0 = r3.zzaVQ
            com.google.android.gms.tagmanager.zzcb r1 = com.google.android.gms.tagmanager.zzcb.zzDm()
            java.lang.String r1 = r1.getContainerId()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L46
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r0 = r3.zzaYk
            com.google.android.gms.tagmanager.zzbf$zza r1 = com.google.android.gms.tagmanager.zzbf.zza.NOT_AVAILABLE
            r0.zza(r1)
        L45:
            return
        L46:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> L70
            java.io.File r0 = r3.zzDv()     // Catch: java.io.FileNotFoundException -> L70
            r1.<init>(r0)     // Catch: java.io.FileNotFoundException -> L70
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            r0.<init>()     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            com.google.android.gms.internal.zzrb.zzb(r1, r0)     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            byte[] r0 = r0.toByteArray()     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            com.google.android.gms.internal.zzqz$zza r0 = com.google.android.gms.internal.zzqz.zza.zzw(r0)     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            r3.zzd(r0)     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r2 = r3.zzaYk     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            r2.zzH(r0)     // Catch: java.io.IOException -> L85 java.lang.IllegalArgumentException -> L9d java.lang.Throwable -> Lb5
            r1.close()     // Catch: java.io.IOException -> L7e
        L6a:
            java.lang.String r0 = "The Disk resource was successfully read."
            com.google.android.gms.tagmanager.zzbg.v(r0)
            goto L45
        L70:
            r0 = move-exception
            java.lang.String r0 = "Failed to find the resource in the disk"
            com.google.android.gms.tagmanager.zzbg.zzaF(r0)
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r0 = r3.zzaYk
            com.google.android.gms.tagmanager.zzbf$zza r1 = com.google.android.gms.tagmanager.zzbf.zza.NOT_AVAILABLE
            r0.zza(r1)
            goto L45
        L7e:
            r0 = move-exception
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)
            goto L6a
        L85:
            r0 = move-exception
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r0 = r3.zzaYk     // Catch: java.lang.Throwable -> Lb5
            com.google.android.gms.tagmanager.zzbf$zza r2 = com.google.android.gms.tagmanager.zzbf.zza.IO_ERROR     // Catch: java.lang.Throwable -> Lb5
            r0.zza(r2)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r0 = "Failed to read the resource from disk"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)     // Catch: java.lang.Throwable -> Lb5
            r1.close()     // Catch: java.io.IOException -> L96
            goto L6a
        L96:
            r0 = move-exception
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)
            goto L6a
        L9d:
            r0 = move-exception
            com.google.android.gms.tagmanager.zzbf<com.google.android.gms.internal.zzqz$zza> r0 = r3.zzaYk     // Catch: java.lang.Throwable -> Lb5
            com.google.android.gms.tagmanager.zzbf$zza r2 = com.google.android.gms.tagmanager.zzbf.zza.IO_ERROR     // Catch: java.lang.Throwable -> Lb5
            r0.zza(r2)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r0 = "Failed to read the resource from disk. The resource is inconsistent"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)     // Catch: java.lang.Throwable -> Lb5
            r1.close()     // Catch: java.io.IOException -> Lae
            goto L6a
        Lae:
            r0 = move-exception
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzbg.zzaH(r0)
            goto L6a
        Lb5:
            r0 = move-exception
            r1.close()     // Catch: java.io.IOException -> Lba
        Lb9:
            throw r0
        Lba:
            r1 = move-exception
            java.lang.String r1 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzbg.zzaH(r1)
            goto Lb9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcn.zzDu():void");
    }

    File zzDv() {
        return new File(this.mContext.getDir("google_tagmanager", 0), "resource_" + this.zzaVQ);
    }

    @Override // com.google.android.gms.tagmanager.zzp.zzf
    public void zza(zzbf<zzqz.zza> zzbfVar) {
        this.zzaYk = zzbfVar;
    }

    @Override // com.google.android.gms.tagmanager.zzp.zzf
    public void zzb(final zzqz.zza zzaVar) {
        this.zzaYr.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.zzcn.2
            @Override // java.lang.Runnable
            public void run() {
                zzcn.this.zzc(zzaVar);
            }
        });
    }

    boolean zzc(zzqz.zza zzaVar) {
        boolean z = false;
        File fileZzDv = zzDv();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileZzDv);
            try {
                try {
                    fileOutputStream.write(zzse.zzf(zzaVar));
                    z = true;
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        zzbg.zzaH("error closing stream for writing resource to disk");
                    }
                }
            } catch (IOException e2) {
                zzbg.zzaH("Error writing resource to disk. Removing resource from disk.");
                fileZzDv.delete();
                try {
                    fileOutputStream.close();
                } catch (IOException e3) {
                    zzbg.zzaH("error closing stream for writing resource to disk");
                }
            }
        } catch (FileNotFoundException e4) {
            zzbg.e("Error opening resource file for writing");
        }
        return z;
    }

    @Override // com.google.android.gms.tagmanager.zzp.zzf
    public zzrb.zzc zzjs(int i) {
        try {
            InputStream inputStreamOpenRawResource = this.mContext.getResources().openRawResource(i);
            zzbg.v("Attempting to load a container from the resource ID " + i + " (" + this.mContext.getResources().getResourceName(i) + ")");
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzrb.zzb(inputStreamOpenRawResource, byteArrayOutputStream);
                zzrb.zzc zzcVarZza = zza(byteArrayOutputStream);
                if (zzcVarZza != null) {
                    zzbg.v("The container was successfully loaded from the resource (using JSON file format)");
                } else {
                    zzcVarZza = zzv(byteArrayOutputStream.toByteArray());
                }
                return zzcVarZza;
            } catch (IOException e) {
                zzbg.zzaH("Error reading the default container with resource ID " + i + " (" + this.mContext.getResources().getResourceName(i) + ")");
                return null;
            }
        } catch (Resources.NotFoundException e2) {
            zzbg.zzaH("Failed to load the container. No default container resource found with the resource ID " + i);
            return null;
        }
    }
}
