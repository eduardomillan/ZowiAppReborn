package com.comscore.utils;

/* JADX INFO: loaded from: classes.dex */
public class Base64Coder {
    private static char[] a = new char[64];
    private static byte[] b;

    static {
        char c = 'A';
        int i = 0;
        while (c <= 'Z') {
            a[i] = c;
            c = (char) (c + 1);
            i++;
        }
        char c2 = 'a';
        while (c2 <= 'z') {
            a[i] = c2;
            c2 = (char) (c2 + 1);
            i++;
        }
        char c3 = '0';
        while (c3 <= '9') {
            a[i] = c3;
            c3 = (char) (c3 + 1);
            i++;
        }
        int i2 = i + 1;
        a[i] = '+';
        int i3 = i2 + 1;
        a[i2] = '/';
        b = new byte[128];
        for (int i4 = 0; i4 < b.length; i4++) {
            b[i4] = -1;
        }
        for (int i5 = 0; i5 < 64; i5++) {
            b[a[i5]] = (byte) i5;
        }
    }

    private Base64Coder() {
    }

    public static byte[] decode(String str) {
        return decode(str.toCharArray());
    }

    public static byte[] decode(char[] cArr) {
        char c;
        int i;
        char c2;
        int i2;
        int i3;
        int i4 = 0;
        int length = cArr.length;
        if (length % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        int i5 = length;
        while (i5 > 0 && cArr[i5 - 1] == '=') {
            i5--;
        }
        int i6 = (i5 * 3) / 4;
        byte[] bArr = new byte[i6];
        int i7 = 0;
        while (i4 < i5) {
            int i8 = i4 + 1;
            char c3 = cArr[i4];
            int i9 = i8 + 1;
            char c4 = cArr[i8];
            if (i9 < i5) {
                c = cArr[i9];
                i9++;
            } else {
                c = 'A';
            }
            if (i9 < i5) {
                int i10 = i9 + 1;
                c2 = cArr[i9];
                i = i10;
            } else {
                i = i9;
                c2 = 'A';
            }
            if (c3 > 127 || c4 > 127 || c > 127 || c2 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte b2 = b[c3];
            byte b3 = b[c4];
            byte b4 = b[c];
            byte b5 = b[c2];
            if (b2 < 0 || b3 < 0 || b4 < 0 || b5 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int i11 = (b2 << 2) | (b3 >>> 4);
            int i12 = ((b3 & 15) << 4) | (b4 >>> 2);
            int i13 = ((b4 & 3) << 6) | b5;
            int i14 = i7 + 1;
            bArr[i7] = (byte) i11;
            if (i14 < i6) {
                i2 = i14 + 1;
                bArr[i14] = (byte) i12;
            } else {
                i2 = i14;
            }
            if (i2 < i6) {
                i3 = i2 + 1;
                bArr[i2] = (byte) i13;
            } else {
                i3 = i2;
            }
            i7 = i3;
            i4 = i;
        }
        return bArr;
    }

    public static String decodeString(String str) {
        return new String(decode(str));
    }

    public static char[] encode(byte[] bArr) {
        return encode(bArr, bArr.length);
    }

    public static char[] encode(byte[] bArr, int i) {
        int i2;
        int i3;
        int i4 = ((i * 4) + 2) / 3;
        char[] cArr = new char[((i + 2) / 3) * 4];
        int i5 = 0;
        int i6 = 0;
        while (i6 < i) {
            int i7 = i6 + 1;
            int i8 = bArr[i6] & 255;
            if (i7 < i) {
                i2 = bArr[i7] & 255;
                i7++;
            } else {
                i2 = 0;
            }
            if (i7 < i) {
                i6 = i7 + 1;
                i3 = bArr[i7] & 255;
            } else {
                i6 = i7;
                i3 = 0;
            }
            int i9 = i8 >>> 2;
            int i10 = ((i8 & 3) << 4) | (i2 >>> 4);
            int i11 = ((i2 & 15) << 2) | (i3 >>> 6);
            int i12 = i3 & 63;
            int i13 = i5 + 1;
            cArr[i5] = a[i9];
            int i14 = i13 + 1;
            cArr[i13] = a[i10];
            cArr[i14] = i14 < i4 ? a[i11] : '=';
            int i15 = i14 + 1;
            cArr[i15] = i15 < i4 ? a[i12] : '=';
            i5 = i15 + 1;
        }
        return cArr;
    }

    public static String encodeString(String str) {
        return new String(encode(str.getBytes()));
    }
}
