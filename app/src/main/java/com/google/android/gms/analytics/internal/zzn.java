package com.google.android.gms.analytics.internal;

import android.content.Context;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public class zzn extends zzd {
    private volatile String zzMj;
    private Future<String> zzNM;

    protected zzn(zzf zzfVar) {
        super(zzfVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean zzi(Context context, String str) {
        boolean z = false;
        com.google.android.gms.common.internal.zzx.zzcr(str);
        com.google.android.gms.common.internal.zzx.zzcj("ClientId should be saved from worker thread");
        FileOutputStream fileOutputStreamOpenFileOutput = 0;
        fileOutputStreamOpenFileOutput = 0;
        fileOutputStreamOpenFileOutput = 0;
        try {
            try {
                try {
                    zza("Storing clientId", str);
                    fileOutputStreamOpenFileOutput = context.openFileOutput("gaClientId", 0);
                    fileOutputStreamOpenFileOutput.write(str.getBytes());
                    z = true;
                    fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                    if (fileOutputStreamOpenFileOutput != 0) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                            fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                        } catch (IOException e) {
                            zze("Failed to close clientId writing stream", e);
                            fileOutputStreamOpenFileOutput = "Failed to close clientId writing stream";
                        }
                    }
                } catch (FileNotFoundException e2) {
                    zze("Error creating clientId file", e2);
                    fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                    if (fileOutputStreamOpenFileOutput != 0) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                            fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                        } catch (IOException e3) {
                            zze("Failed to close clientId writing stream", e3);
                            fileOutputStreamOpenFileOutput = "Failed to close clientId writing stream";
                        }
                    }
                }
            } catch (IOException e4) {
                zze("Error writing to clientId file", e4);
                fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                if (fileOutputStreamOpenFileOutput != 0) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                        fileOutputStreamOpenFileOutput = fileOutputStreamOpenFileOutput;
                    } catch (IOException e5) {
                        zze("Failed to close clientId writing stream", e5);
                        fileOutputStreamOpenFileOutput = "Failed to close clientId writing stream";
                    }
                }
            }
            return z;
        } catch (Throwable th) {
            if (fileOutputStreamOpenFileOutput != 0) {
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e6) {
                    zze("Failed to close clientId writing stream", e6);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String zzjw() {
        String strZzjx = zzjx();
        try {
            return !zzi(zziw().getContext(), strZzjx) ? "0" : strZzjx;
        } catch (Exception e) {
            zze("Error saving clientId file", e);
            return "0";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x008d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x008b -> B:63:0x002e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x008d -> B:63:0x002e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x0092 -> B:63:0x002e). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.String zzZ(android.content.Context r7) throws java.lang.Throwable {
        /*
            r6 = this;
            r0 = 0
            java.lang.String r1 = "ClientId should be loaded from worker thread"
            com.google.android.gms.common.internal.zzx.zzcj(r1)
            java.lang.String r1 = "gaClientId"
            java.io.FileInputStream r2 = r7.openFileInput(r1)     // Catch: java.io.FileNotFoundException -> L70 java.io.IOException -> L7f java.lang.Throwable -> L98
            r1 = 36
            byte[] r3 = new byte[r1]     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            r1 = 0
            int r4 = r3.length     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            int r4 = r2.read(r3, r1, r4)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            int r1 = r2.available()     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            if (r1 <= 0) goto L36
            java.lang.String r1 = "clientId file seems corrupted, deleting it."
            r6.zzbd(r1)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            r2.close()     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            java.lang.String r1 = "gaClientId"
            r7.deleteFile(r1)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            if (r2 == 0) goto L2e
            r2.close()     // Catch: java.io.IOException -> L2f
        L2e:
            return r0
        L2f:
            r1 = move-exception
            java.lang.String r2 = "Failed to close client id reading stream"
            r6.zze(r2, r1)
            goto L2e
        L36:
            r1 = 14
            if (r4 >= r1) goto L54
            java.lang.String r1 = "clientId file is empty, deleting it."
            r6.zzbd(r1)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            r2.close()     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            java.lang.String r1 = "gaClientId"
            r7.deleteFile(r1)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            if (r2 == 0) goto L2e
            r2.close()     // Catch: java.io.IOException -> L4d
            goto L2e
        L4d:
            r1 = move-exception
            java.lang.String r2 = "Failed to close client id reading stream"
            r6.zze(r2, r1)
            goto L2e
        L54:
            r2.close()     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            java.lang.String r1 = new java.lang.String     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            r5 = 0
            r1.<init>(r3, r5, r4)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            java.lang.String r3 = "Read client id from disk"
            r6.zza(r3, r1)     // Catch: java.lang.Throwable -> La8 java.io.IOException -> Laa java.io.FileNotFoundException -> Lac
            if (r2 == 0) goto L67
            r2.close()     // Catch: java.io.IOException -> L69
        L67:
            r0 = r1
            goto L2e
        L69:
            r0 = move-exception
            java.lang.String r2 = "Failed to close client id reading stream"
            r6.zze(r2, r0)
            goto L67
        L70:
            r1 = move-exception
            r1 = r0
        L72:
            if (r1 == 0) goto L2e
            r1.close()     // Catch: java.io.IOException -> L78
            goto L2e
        L78:
            r1 = move-exception
            java.lang.String r2 = "Failed to close client id reading stream"
            r6.zze(r2, r1)
            goto L2e
        L7f:
            r1 = move-exception
            r2 = r0
        L81:
            java.lang.String r3 = "Error reading client id file, deleting it"
            r6.zze(r3, r1)     // Catch: java.lang.Throwable -> La8
            java.lang.String r1 = "gaClientId"
            r7.deleteFile(r1)     // Catch: java.lang.Throwable -> La8
            if (r2 == 0) goto L2e
            r2.close()     // Catch: java.io.IOException -> L91
            goto L2e
        L91:
            r1 = move-exception
            java.lang.String r2 = "Failed to close client id reading stream"
            r6.zze(r2, r1)
            goto L2e
        L98:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L9b:
            if (r2 == 0) goto La0
            r2.close()     // Catch: java.io.IOException -> La1
        La0:
            throw r0
        La1:
            r1 = move-exception
            java.lang.String r2 = "Failed to close client id reading stream"
            r6.zze(r2, r1)
            goto La0
        La8:
            r0 = move-exception
            goto L9b
        Laa:
            r1 = move-exception
            goto L81
        Lac:
            r1 = move-exception
            r1 = r2
            goto L72
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzn.zzZ(android.content.Context):java.lang.String");
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
    }

    public String zzjt() {
        String str;
        zziE();
        synchronized (this) {
            if (this.zzMj == null) {
                this.zzNM = zziw().zzb(new Callable<String>() { // from class: com.google.android.gms.analytics.internal.zzn.1
                    @Override // java.util.concurrent.Callable
                    /* JADX INFO: renamed from: zzjy, reason: merged with bridge method [inline-methods] */
                    public String call() throws Exception {
                        return zzn.this.zzjv();
                    }
                });
            }
            if (this.zzNM != null) {
                try {
                    this.zzMj = this.zzNM.get();
                } catch (InterruptedException e) {
                    zzd("ClientId loading or generation was interrupted", e);
                    this.zzMj = "0";
                } catch (ExecutionException e2) {
                    zze("Failed to load or generate client id", e2);
                    this.zzMj = "0";
                }
                if (this.zzMj == null) {
                    this.zzMj = "0";
                }
                zza("Loaded clientId", this.zzMj);
                this.zzNM = null;
                str = this.zzMj;
            } else {
                str = this.zzMj;
            }
        }
        return str;
    }

    String zzju() {
        synchronized (this) {
            this.zzMj = null;
            this.zzNM = zziw().zzb(new Callable<String>() { // from class: com.google.android.gms.analytics.internal.zzn.2
                @Override // java.util.concurrent.Callable
                /* JADX INFO: renamed from: zzjy, reason: merged with bridge method [inline-methods] */
                public String call() throws Exception {
                    return zzn.this.zzjw();
                }
            });
        }
        return zzjt();
    }

    String zzjv() throws Throwable {
        String strZzZ = zzZ(zziw().getContext());
        return strZzZ == null ? zzjw() : strZzZ;
    }

    protected String zzjx() {
        return UUID.randomUUID().toString().toLowerCase();
    }
}
