package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
public final class PasswordSpecification implements SafeParcelable {
    public static final zze CREATOR = new zze();
    public static final PasswordSpecification zzSt = new zza().zzg(12, 16).zzbD("abcdefghijkmnopqrstxyzABCDEFGHJKLMNPQRSTXY3456789").zzf("abcdefghijkmnopqrstxyz", 1).zzf("ABCDEFGHJKLMNPQRSTXY", 1).zzf("3456789", 1).zzlK();
    public static final PasswordSpecification zzSu = new zza().zzg(12, 16).zzbD("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").zzf("abcdefghijklmnopqrstuvwxyz", 1).zzf("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 1).zzf("1234567890", 1).zzlK();
    final int mVersionCode;
    final String zzSv;
    final List<String> zzSw;
    final List<Integer> zzSx;
    final int zzSy;
    final int zzSz;
    private final int[] zzSA = zzlJ();
    private final Random zzts = new SecureRandom();

    public static class zza {
        private final TreeSet<Character> zzSB = new TreeSet<>();
        private final List<String> zzSw = new ArrayList();
        private final List<Integer> zzSx = new ArrayList();
        private int zzSy = 12;
        private int zzSz = 16;

        private void zzlL() {
            int i;
            int iIntValue = 0;
            Iterator<Integer> it = this.zzSx.iterator();
            while (true) {
                i = iIntValue;
                if (!it.hasNext()) {
                    break;
                } else {
                    iIntValue = it.next().intValue() + i;
                }
            }
            if (i > this.zzSz) {
                throw new zzb("required character count cannot be greater than the max password size");
            }
        }

        private void zzlM() {
            boolean[] zArr = new boolean[95];
            Iterator<String> it = this.zzSw.iterator();
            while (it.hasNext()) {
                for (char c : it.next().toCharArray()) {
                    if (zArr[c - ' ']) {
                        throw new zzb("character " + c + " occurs in more than one required character set");
                    }
                    zArr[c - ' '] = true;
                }
            }
        }

        private TreeSet<Character> zzr(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                throw new zzb(str2 + " cannot be null or empty");
            }
            TreeSet<Character> treeSet = new TreeSet<>();
            for (char c : str.toCharArray()) {
                if (PasswordSpecification.zzb(c, 32, TransportMediator.KEYCODE_MEDIA_PLAY)) {
                    throw new zzb(str2 + " must only contain ASCII printable characters");
                }
                treeSet.add(Character.valueOf(c));
            }
            return treeSet;
        }

        public zza zzbD(String str) {
            this.zzSB.addAll(zzr(str, "allowedChars"));
            return this;
        }

        public zza zzf(String str, int i) {
            if (i < 1) {
                throw new zzb("count must be at least 1");
            }
            this.zzSw.add(PasswordSpecification.zzb(zzr(str, "requiredChars")));
            this.zzSx.add(Integer.valueOf(i));
            return this;
        }

        public zza zzg(int i, int i2) {
            if (i < 1) {
                throw new zzb("minimumSize must be at least 1");
            }
            if (i > i2) {
                throw new zzb("maximumSize must be greater than or equal to minimumSize");
            }
            this.zzSy = i;
            this.zzSz = i2;
            return this;
        }

        public PasswordSpecification zzlK() {
            if (this.zzSB.isEmpty()) {
                throw new zzb("no allowed characters specified");
            }
            zzlL();
            zzlM();
            return new PasswordSpecification(1, PasswordSpecification.zzb(this.zzSB), this.zzSw, this.zzSx, this.zzSy, this.zzSz);
        }
    }

    public static class zzb extends Error {
        public zzb(String str) {
            super(str);
        }
    }

    PasswordSpecification(int version, String allowedChars, List<String> requiredCharSets, List<Integer> requiredCharCounts, int minimumSize, int maximumSize) {
        this.mVersionCode = version;
        this.zzSv = allowedChars;
        this.zzSw = Collections.unmodifiableList(requiredCharSets);
        this.zzSx = Collections.unmodifiableList(requiredCharCounts);
        this.zzSy = minimumSize;
        this.zzSz = maximumSize;
    }

    private int zza(char c) {
        return c - ' ';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String zzb(Collection<Character> collection) {
        char[] cArr = new char[collection.size()];
        int i = 0;
        Iterator<Character> it = collection.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return new String(cArr);
            }
            i = i2 + 1;
            cArr[i2] = it.next().charValue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzb(int i, int i2, int i3) {
        return i < i2 || i > i3;
    }

    private int[] zzlJ() {
        int[] iArr = new int[95];
        Arrays.fill(iArr, -1);
        Iterator<String> it = this.zzSw.iterator();
        int i = 0;
        while (it.hasNext()) {
            for (char c : it.next().toCharArray()) {
                iArr[zza(c)] = i;
            }
            i++;
        }
        return iArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zze.zza(this, out, flags);
    }
}
