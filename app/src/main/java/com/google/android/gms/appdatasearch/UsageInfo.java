package com.google.android.gms.appdatasearch;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.appdatasearch.DocumentContents;
import com.google.android.gms.appdatasearch.RegisterSectionInfo;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzox;
import com.google.android.gms.internal.zzse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.CRC32;

/* JADX INFO: loaded from: classes.dex */
public class UsageInfo implements SafeParcelable {
    public static final zzj CREATOR = new zzj();
    final int mVersionCode;
    final DocumentId zzQU;
    final long zzQV;
    int zzQW;
    final DocumentContents zzQX;
    final boolean zzQY;
    int zzQZ;
    int zzRa;
    public final String zzub;

    public static final class zza {
        private String zzLe;
        private DocumentId zzQU;
        private DocumentContents zzQX;
        private long zzQV = -1;
        private int zzQW = -1;
        private int zzQZ = -1;
        private boolean zzQY = false;
        private int zzRa = 0;

        public zza zzO(boolean z) {
            this.zzQY = z;
            return this;
        }

        public zza zza(DocumentContents documentContents) {
            this.zzQX = documentContents;
            return this;
        }

        public zza zza(DocumentId documentId) {
            this.zzQU = documentId;
            return this;
        }

        public zza zzan(int i) {
            this.zzQW = i;
            return this;
        }

        public zza zzao(int i) {
            this.zzRa = i;
            return this;
        }

        public UsageInfo zzlv() {
            return new UsageInfo(this.zzQU, this.zzQV, this.zzQW, this.zzLe, this.zzQX, this.zzQY, this.zzQZ, this.zzRa);
        }

        public zza zzw(long j) {
            this.zzQV = j;
            return this;
        }
    }

    UsageInfo(int versionCode, DocumentId documentId, long timestamp, int usageType, String query, DocumentContents document, boolean isDeviceOnly, int taskPosition, int eventStatus) {
        this.mVersionCode = versionCode;
        this.zzQU = documentId;
        this.zzQV = timestamp;
        this.zzQW = usageType;
        this.zzub = query;
        this.zzQX = document;
        this.zzQY = isDeviceOnly;
        this.zzQZ = taskPosition;
        this.zzRa = eventStatus;
    }

    private UsageInfo(DocumentId documentId, long timestampMs, int usageType, String query, DocumentContents document, boolean isDeviceOnly, int taskPosition, int eventStatus) {
        this(1, documentId, timestampMs, usageType, query, document, isDeviceOnly, taskPosition, eventStatus);
    }

    public UsageInfo(String packageName, Intent viewIntent, String title, Uri webUrl, String schemaOrgType, List<AppIndexApi.AppIndexingLink> outLinks, int eventStatus) {
        this(1, zza(packageName, viewIntent), System.currentTimeMillis(), 0, (String) null, zza(viewIntent, title, webUrl, schemaOrgType, outLinks).zzlo(), false, -1, eventStatus);
    }

    public static DocumentContents.zza zza(Intent intent, String str, Uri uri, String str2, List<AppIndexApi.AppIndexingLink> list) {
        String string;
        DocumentContents.zza zzaVar = new DocumentContents.zza();
        zzaVar.zza(zzbC(str));
        if (uri != null) {
            zzaVar.zza(zzi(uri));
        }
        if (list != null) {
            zzaVar.zza(zzo(list));
        }
        String action = intent.getAction();
        if (action != null) {
            zzaVar.zza(zzq("intent_action", action));
        }
        String dataString = intent.getDataString();
        if (dataString != null) {
            zzaVar.zza(zzq("intent_data", dataString));
        }
        ComponentName component = intent.getComponent();
        if (component != null) {
            zzaVar.zza(zzq("intent_activity", component.getClassName()));
        }
        Bundle extras = intent.getExtras();
        if (extras != null && (string = extras.getString("intent_extra_data_key")) != null) {
            zzaVar.zza(zzq("intent_extra_data", string));
        }
        return zzaVar.zzbx(str2).zzK(true);
    }

    public static DocumentId zza(String str, Intent intent) {
        return zzp(str, zzg(intent));
    }

    private static DocumentSection zzbC(String str) {
        return new DocumentSection(str, new RegisterSectionInfo.zza("title").zzal(1).zzN(true).zzbB("name").zzlt(), "text1");
    }

    private static String zzg(Intent intent) {
        String uri = intent.toUri(1);
        CRC32 crc32 = new CRC32();
        try {
            crc32.update(uri.getBytes("UTF-8"));
            return Long.toHexString(crc32.getValue());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    private static DocumentSection zzi(Uri uri) {
        return new DocumentSection(uri.toString(), new RegisterSectionInfo.zza("web_url").zzal(4).zzM(true).zzbB("url").zzlt());
    }

    private static DocumentSection zzo(List<AppIndexApi.AppIndexingLink> list) {
        zzox.zza zzaVar = new zzox.zza();
        zzox.zza.C0092zza[] c0092zzaArr = new zzox.zza.C0092zza[list.size()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= c0092zzaArr.length) {
                zzaVar.zzaCU = c0092zzaArr;
                return new DocumentSection(zzse.zzf(zzaVar), new RegisterSectionInfo.zza("outlinks").zzM(true).zzbB(".private:outLinks").zzbA("blob").zzlt());
            }
            c0092zzaArr[i2] = new zzox.zza.C0092zza();
            AppIndexApi.AppIndexingLink appIndexingLink = list.get(i2);
            c0092zzaArr[i2].zzaCW = appIndexingLink.appIndexingUrl.toString();
            c0092zzaArr[i2].viewId = appIndexingLink.viewId;
            if (appIndexingLink.webUrl != null) {
                c0092zzaArr[i2].zzaCX = appIndexingLink.webUrl.toString();
            }
            i = i2 + 1;
        }
    }

    private static DocumentId zzp(String str, String str2) {
        return new DocumentId(str, "", str2);
    }

    private static DocumentSection zzq(String str, String str2) {
        return new DocumentSection(str2, new RegisterSectionInfo.zza(str).zzM(true).zzlt(), str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        zzj zzjVar = CREATOR;
        return 0;
    }

    public String toString() {
        return String.format("UsageInfo[documentId=%s, timestamp=%d, usageType=%d, status=%d]", this.zzQU, Long.valueOf(this.zzQV), Integer.valueOf(this.zzQW), Integer.valueOf(this.zzRa));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        zzj zzjVar = CREATOR;
        zzj.zza(this, dest, flags);
    }

    public DocumentContents zzlu() {
        return this.zzQX;
    }
}
