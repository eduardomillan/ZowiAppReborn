package com.comscore.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static List<String> arrayOfStrings(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (Object obj : list) {
                if (obj != null) {
                    arrayList.add(obj.toString());
                }
            }
        }
        return arrayList;
    }

    public static String encrypt(String str) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Constants.RSA_PUBLIC_KEY));
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        cipher.init(1, publicKeyGeneratePublic);
        String str2 = new String(Base64Coder.encode(cipher.doFinal(str.getBytes())));
        CSLog.d((Class<? extends Object>) Utils.class, "encrypt(" + str + ")=" + str2);
        return str2;
    }

    public static boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public static boolean getBoolean(String str, boolean z) {
        if (str == null) {
            return z;
        }
        if (str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("true")) {
            return true;
        }
        if (str.equalsIgnoreCase("no") || str.equalsIgnoreCase("false")) {
            return false;
        }
        return z;
    }

    public static int getInteger(String str) {
        return getInteger(str, 0);
    }

    public static int getInteger(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public static long getLong(String str) {
        return getLong(str, 0L);
    }

    public static long getLong(String str, long j) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return j;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static HashMap<String, String> mapOfStrings(Map map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            for (Object obj : map.keySet()) {
                if (obj != null && map.get(obj) != null) {
                    String string = obj.toString();
                    if (string.length() > 0) {
                        map2.put(string, map.get(obj).toString());
                    }
                }
            }
        }
        return map2;
    }

    public static String md5(String str) {
        byte[] bytes = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(bytes);
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArrDigest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    stringBuffer.append('0');
                }
                stringBuffer.append(hexString);
            }
            return ((Object) stringBuffer) + "";
        } catch (Exception e) {
            return null;
        }
    }
}
