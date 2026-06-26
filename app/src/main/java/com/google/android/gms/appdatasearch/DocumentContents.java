package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DocumentContents implements SafeParcelable {
    public static final zzb CREATOR = new zzb();
    public final Account account;
    final int mVersionCode;
    final DocumentSection[] zzPX;
    public final String zzPY;
    public final boolean zzPZ;

    public static class zza {
        private List<DocumentSection> zzQa;
        private String zzQb;
        private boolean zzQc;
        private Account zzQd;

        public zza zzK(boolean z) {
            this.zzQc = z;
            return this;
        }

        public zza zza(DocumentSection documentSection) {
            if (this.zzQa == null) {
                this.zzQa = new ArrayList();
            }
            this.zzQa.add(documentSection);
            return this;
        }

        public zza zzb(Account account) {
            this.zzQd = account;
            return this;
        }

        public zza zzbx(String str) {
            this.zzQb = str;
            return this;
        }

        public DocumentContents zzlo() {
            return new DocumentContents(this.zzQb, this.zzQc, this.zzQd, this.zzQa != null ? (DocumentSection[]) this.zzQa.toArray(new DocumentSection[this.zzQa.size()]) : null);
        }
    }

    DocumentContents(int versionCode, DocumentSection[] sectionContents, String schemaOrgType, boolean globalSearchEnabled, Account account) {
        this.mVersionCode = versionCode;
        this.zzPX = sectionContents;
        this.zzPY = schemaOrgType;
        this.zzPZ = globalSearchEnabled;
        this.account = account;
    }

    DocumentContents(String schemaOrgType, boolean globalSearchEnabled, Account account, DocumentSection... sections) {
        this(1, sections, schemaOrgType, globalSearchEnabled, account);
        BitSet bitSet = new BitSet(zzh.zzls());
        for (DocumentSection documentSection : sections) {
            int i = documentSection.zzQl;
            if (i != -1) {
                if (bitSet.get(i)) {
                    throw new IllegalArgumentException("Duplicate global search section type " + zzh.zzak(i));
                }
                bitSet.set(i);
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        zzb zzbVar = CREATOR;
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        zzb zzbVar = CREATOR;
        zzb.zza(this, dest, flags);
    }

    public DocumentSection zzbw(String str) {
        zzx.zzcr(str);
        if (this.zzPX == null) {
            return null;
        }
        for (DocumentSection documentSection : this.zzPX) {
            if (str.equals(documentSection.zzlp().name)) {
                return documentSection;
            }
        }
        return null;
    }

    public String zzln() {
        DocumentSection documentSectionZzbw = zzbw("web_url");
        if (documentSectionZzbw != null) {
            return documentSectionZzbw.zzQj;
        }
        return null;
    }
}
