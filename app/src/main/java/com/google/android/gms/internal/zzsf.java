package com.google.android.gms.internal;

import android.support.v7.widget.helper.ItemTouchHelper;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class zzsf {
    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        if (obj == null) {
            return;
        }
        if (!(obj instanceof zzse)) {
            stringBuffer2.append(stringBuffer).append(zzfB(str)).append(": ");
            if (obj instanceof String) {
                stringBuffer2.append("\"").append(zzfC((String) obj)).append("\"");
            } else if (obj instanceof byte[]) {
                zza((byte[]) obj, stringBuffer2);
            } else {
                stringBuffer2.append(obj);
            }
            stringBuffer2.append(Droid2InoConstants.NEW_LINE_CHARACTER);
            return;
        }
        int length = stringBuffer.length();
        if (str != null) {
            stringBuffer2.append(stringBuffer).append(zzfB(str)).append(" <\n");
            stringBuffer.append("  ");
        }
        Class<?> cls = obj.getClass();
        for (Field field : cls.getFields()) {
            int modifiers = field.getModifiers();
            String name = field.getName();
            if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith("_") && !name.endsWith("_")) {
                Class<?> type = field.getType();
                Object obj2 = field.get(obj);
                if (!type.isArray()) {
                    zza(name, obj2, stringBuffer, stringBuffer2);
                } else if (type.getComponentType() == Byte.TYPE) {
                    zza(name, obj2, stringBuffer, stringBuffer2);
                } else {
                    int length2 = obj2 == null ? 0 : Array.getLength(obj2);
                    for (int i = 0; i < length2; i++) {
                        zza(name, Array.get(obj2, i), stringBuffer, stringBuffer2);
                    }
                }
            }
        }
        for (Method method : cls.getMethods()) {
            String name2 = method.getName();
            if (name2.startsWith("set")) {
                String strSubstring = name2.substring(3);
                try {
                    if (((Boolean) cls.getMethod("has" + strSubstring, new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                        try {
                            zza(strSubstring, cls.getMethod("get" + strSubstring, new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                        } catch (NoSuchMethodException e) {
                        }
                    }
                } catch (NoSuchMethodException e2) {
                }
            }
        }
        if (str != null) {
            stringBuffer.setLength(length);
            stringBuffer2.append(stringBuffer).append(">\n");
        }
    }

    private static void zza(byte[] bArr, StringBuffer stringBuffer) {
        if (bArr == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        for (byte b : bArr) {
            int i = b & 255;
            if (i == 92 || i == 34) {
                stringBuffer.append('\\').append((char) i);
            } else if (i < 32 || i >= 127) {
                stringBuffer.append(String.format("\\%03o", Integer.valueOf(i)));
            } else {
                stringBuffer.append((char) i);
            }
        }
        stringBuffer.append('\"');
    }

    private static String zzcz(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < ' ' || cCharAt > '~' || cCharAt == '\"' || cCharAt == '\'') {
                sb.append(String.format("\\u%04x", Integer.valueOf(cCharAt)));
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    private static String zzfB(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (i == 0) {
                stringBuffer.append(Character.toLowerCase(cCharAt));
            } else if (Character.isUpperCase(cCharAt)) {
                stringBuffer.append('_').append(Character.toLowerCase(cCharAt));
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    private static String zzfC(String str) {
        if (!str.startsWith("http") && str.length() > 200) {
            str = str.substring(0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) + "[...]";
        }
        return zzcz(str);
    }

    public static <T extends zzse> String zzg(T t) {
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            return "Error printing proto: " + e.getMessage();
        } catch (InvocationTargetException e2) {
            return "Error printing proto: " + e2.getMessage();
        }
    }
}
