package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class zza extends zzd {
    public static boolean zzMq;
    private AdvertisingIdClient.Info zzMr;
    private final zzaj zzMs;
    private String zzMt;
    private boolean zzMu;
    private Object zzMv;

    zza(zzf zzfVar) {
        super(zzfVar);
        this.zzMu = false;
        this.zzMv = new Object();
        this.zzMs = new zzaj(zzfVar.zzit());
    }

    private boolean zza(AdvertisingIdClient.Info info, AdvertisingIdClient.Info info2) {
        String strZzju;
        String id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String strZzjt = zziz().zzjt();
        synchronized (this.zzMv) {
            if (!this.zzMu) {
                this.zzMt = zzij();
                this.zzMu = true;
            } else if (TextUtils.isEmpty(this.zzMt)) {
                String id2 = info != null ? info.getId() : null;
                if (id2 == null) {
                    return zzaZ(id + strZzjt);
                }
                this.zzMt = zzaY(id2 + strZzjt);
            }
            String strZzaY = zzaY(id + strZzjt);
            if (TextUtils.isEmpty(strZzaY)) {
                return false;
            }
            if (strZzaY.equals(this.zzMt)) {
                return true;
            }
            if (TextUtils.isEmpty(this.zzMt)) {
                strZzju = strZzjt;
            } else {
                zzba("Resetting the client id because Advertising Id changed.");
                strZzju = zziz().zzju();
                zza("New client Id", strZzju);
            }
            return zzaZ(id + strZzju);
        }
    }

    private static String zzaY(String str) {
        MessageDigest messageDigestZzbs = zzam.zzbs("MD5");
        if (messageDigestZzbs == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzbs.digest(str.getBytes())));
    }

    private boolean zzaZ(String str) {
        try {
            String strZzaY = zzaY(str);
            zzba("Storing hashed adid.");
            FileOutputStream fileOutputStreamOpenFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            fileOutputStreamOpenFileOutput.write(strZzaY.getBytes());
            fileOutputStreamOpenFileOutput.close();
            this.zzMt = strZzaY;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    private synchronized AdvertisingIdClient.Info zzih() {
        if (this.zzMs.zzv(1000L)) {
            this.zzMs.start();
            AdvertisingIdClient.Info infoZzii = zzii();
            if (zza(this.zzMr, infoZzii)) {
                this.zzMr = infoZzii;
            } else {
                zzbe("Failed to reset client id on adid change. Not using adid");
                this.zzMr = new AdvertisingIdClient.Info("", false);
            }
        }
        return this.zzMr;
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
    }

    public boolean zzic() {
        zziE();
        AdvertisingIdClient.Info infoZzih = zzih();
        return (infoZzih == null || infoZzih.isLimitAdTrackingEnabled()) ? false : true;
    }

    public String zzig() {
        zziE();
        AdvertisingIdClient.Info infoZzih = zzih();
        String id = infoZzih != null ? infoZzih.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }

    protected AdvertisingIdClient.Info zzii() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException e) {
            zzbd("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Throwable th) {
            if (zzMq) {
                return null;
            }
            zzMq = true;
            zzd("Error getting advertiser id", th);
            return null;
        }
    }

    protected String zzij() {
        String str = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int i = fileInputStreamOpenFileInput.read(bArr, 0, 128);
            if (fileInputStreamOpenFileInput.available() > 0) {
                zzbd("Hash file seems corrupted, deleting it.");
                fileInputStreamOpenFileInput.close();
                getContext().deleteFile("gaClientIdData");
            } else if (i <= 0) {
                zzba("Hash file is empty.");
                fileInputStreamOpenFileInput.close();
            } else {
                String str2 = new String(bArr, 0, i);
                try {
                    fileInputStreamOpenFileInput.close();
                    str = str2;
                } catch (FileNotFoundException e) {
                    str = str2;
                } catch (IOException e2) {
                    str = str2;
                    e = e2;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
            e = e4;
        }
        return str;
    }
}
