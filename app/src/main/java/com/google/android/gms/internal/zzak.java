package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzak implements zzaj {
    protected MotionEvent zzmU;
    protected DisplayMetrics zzmV;
    protected zzap zzmW;
    private zzaq zzmX;

    protected zzak(Context context, zzap zzapVar, zzaq zzaqVar) {
        this.zzmW = zzapVar;
        this.zzmX = zzaqVar;
        try {
            this.zzmV = context.getResources().getDisplayMetrics();
        } catch (UnsupportedOperationException e) {
            this.zzmV = new DisplayMetrics();
            this.zzmV.density = 1.0f;
        }
    }

    private void zzS() {
        this.zzmX.reset();
    }

    private byte[] zzT() throws IOException {
        return this.zzmX.zzac();
    }

    private String zza(Context context, String str, boolean z) {
        byte[] bArrZzT;
        try {
            synchronized (this) {
                zzS();
                if (z) {
                    zzd(context);
                } else {
                    zzc(context);
                }
                bArrZzT = zzT();
            }
            return bArrZzT.length == 0 ? Integer.toString(5) : zza(bArrZzT, str);
        } catch (UnsupportedEncodingException e) {
            return Integer.toString(7);
        } catch (IOException e2) {
            return Integer.toString(3);
        } catch (NoSuchAlgorithmException e3) {
            return Integer.toString(7);
        }
    }

    String zza(byte[] bArr, String str) throws NoSuchAlgorithmException, IOException {
        byte[] bArrArray;
        if (bArr.length > 239) {
            zzS();
            zza(20, 1L);
            bArr = zzT();
        }
        if (bArr.length < 239) {
            byte[] bArr2 = new byte[239 - bArr.length];
            new SecureRandom().nextBytes(bArr2);
            bArrArray = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            bArrArray = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).array();
        }
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bArrArray);
        byte[] bArrArray2 = ByteBuffer.allocate(256).put(messageDigest.digest()).put(bArrArray).array();
        byte[] bArr3 = new byte[256];
        new zzai().zzb(bArrArray2, bArr3);
        if (str != null && str.length() > 0) {
            zza(str, bArr3);
        }
        return this.zzmW.zza(bArr3, true);
    }

    @Override // com.google.android.gms.internal.zzaj
    public void zza(int i, int i2, int i3) {
        if (this.zzmU != null) {
            this.zzmU.recycle();
        }
        this.zzmU = MotionEvent.obtain(0L, i3, 1, i * this.zzmV.density, i2 * this.zzmV.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
    }

    protected void zza(int i, long j) throws IOException {
        this.zzmX.zzb(i, j);
    }

    protected void zza(int i, String str) throws IOException {
        this.zzmX.zzb(i, str);
    }

    @Override // com.google.android.gms.internal.zzaj
    public void zza(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.zzmU != null) {
                this.zzmU.recycle();
            }
            this.zzmU = MotionEvent.obtain(motionEvent);
        }
    }

    void zza(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (str.length() > 32) {
            str = str.substring(0, 32);
        }
        new zzrv(str.getBytes("UTF-8")).zzA(bArr);
    }

    @Override // com.google.android.gms.internal.zzaj
    public String zzb(Context context) {
        return zza(context, (String) null, false);
    }

    @Override // com.google.android.gms.internal.zzaj
    public String zzb(Context context, String str) {
        return zza(context, str, true);
    }

    protected abstract void zzc(Context context);

    protected abstract void zzd(Context context);

    protected String zzk(String str) {
        if (str == null || !str.matches("^[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-F0-9]{12}$")) {
            return str;
        }
        UUID uuidFromString = UUID.fromString(str);
        byte[] bArr = new byte[16];
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.putLong(uuidFromString.getMostSignificantBits());
        byteBufferWrap.putLong(uuidFromString.getLeastSignificantBits());
        return this.zzmW.zza(bArr, true);
    }
}
