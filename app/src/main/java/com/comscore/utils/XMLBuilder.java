package com.comscore.utils;

/* JADX INFO: loaded from: classes.dex */
public class XMLBuilder {
    private static StringBuilder a;
    private static int b = -1;
    private static int c = -1;
    private static final String[] d = {"c12", "c1", "ns_ap_an", "ns_ap_pn", "ns_ap_device", "ns_ak"};
    private static final String[] e = {"c12", "c1", "ns_ap_an", "ns_ap_pn", "ns_ap_device", "ns_ts", "ns_ak"};
    private static final long f = (1 << e.length) - 1;

    private static void a(String str, String str2) {
        c = -1;
        b = -1;
        int iIndexOf = 0;
        do {
            iIndexOf = str.indexOf(str2, iIndexOf);
            if (iIndexOf >= 0) {
                int length = str2.length() + iIndexOf;
                if ((iIndexOf == 0 || str.charAt(iIndexOf - 1) == '&') && length < str.length() && str.charAt(length) == '=') {
                    b = length + 1;
                    c = str.indexOf(38, b);
                    if (c == -1) {
                        c = str.length();
                        return;
                    }
                    return;
                }
                iIndexOf = length + 1;
            }
            if (iIndexOf < 0) {
                return;
            }
        } while (iIndexOf < str.length());
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x008c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.lang.String r14, java.lang.StringBuilder r15) {
        /*
            r2 = 0
            java.lang.String r0 = "ns_ts"
            a(r14, r0)
            int r0 = com.comscore.utils.XMLBuilder.b
            r1 = -1
            if (r0 == r1) goto L97
            int r0 = com.comscore.utils.XMLBuilder.c
            int r1 = com.comscore.utils.XMLBuilder.b
            if (r0 <= r1) goto L97
            java.lang.String r0 = "<event t=\""
            java.lang.StringBuilder r0 = r15.append(r0)
            int r1 = com.comscore.utils.XMLBuilder.b
            int r4 = com.comscore.utils.XMLBuilder.c
            java.lang.StringBuilder r0 = r0.append(r14, r1, r4)
            java.lang.String r1 = "\">"
            r0.append(r1)
            r1 = 0
            r0 = 0
        L27:
            int r4 = r14.length()
            if (r1 >= r4) goto L92
            r4 = 38
            int r4 = r14.indexOf(r4, r1)
            r5 = -1
            if (r4 != r5) goto L9a
            int r4 = r14.length()
            r6 = r4
        L3b:
            if (r6 <= r1) goto L8c
            r4 = 61
            int r4 = r14.indexOf(r4, r1)
            if (r4 <= r1) goto L8c
            r5 = 1
            long r8 = com.comscore.utils.XMLBuilder.f
            int r4 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r4 == 0) goto L98
            r4 = 0
        L4d:
            java.lang.String[] r7 = com.comscore.utils.XMLBuilder.e
            int r7 = r7.length
            if (r4 >= r7) goto L98
            r7 = 1
            int r7 = r7 << r4
            long r8 = (long) r7
            long r10 = r2 & r8
            r12 = 0
            int r7 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r7 != 0) goto L8f
            java.lang.String[] r7 = com.comscore.utils.XMLBuilder.e
            r7 = r7[r4]
            r10 = 0
            java.lang.String[] r11 = com.comscore.utils.XMLBuilder.e
            r11 = r11[r4]
            int r11 = r11.length()
            boolean r7 = r7.regionMatches(r10, r14, r1, r11)
            if (r7 == 0) goto L8f
            r4 = 0
            long r2 = r2 | r8
        L72:
            if (r4 == 0) goto L8c
            if (r0 <= 0) goto L82
            java.lang.StringBuilder r4 = com.comscore.utils.XMLBuilder.a
            r5 = 38
            r4.append(r5)
            r4 = 38
            r15.append(r4)
        L82:
            java.lang.StringBuilder r4 = com.comscore.utils.XMLBuilder.a
            r4.append(r14, r1, r6)
            r15.append(r14, r1, r6)
            int r0 = r0 + 1
        L8c:
            int r1 = r6 + 1
            goto L27
        L8f:
            int r4 = r4 + 1
            goto L4d
        L92:
            java.lang.String r0 = "</event>"
            r15.append(r0)
        L97:
            return
        L98:
            r4 = r5
            goto L72
        L9a:
            r6 = r4
            goto L3b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.comscore.utils.XMLBuilder.a(java.lang.String, java.lang.StringBuilder):void");
    }

    private static void a(StringBuilder sb) {
        sb.insert("md5=\"".length() + sb.indexOf("md5=\""), Utils.md5(a.toString()).toLowerCase());
    }

    private static void a(StringBuilder sb, String str, String str2) {
        sb.append("<events t=\"").append(String.valueOf(Date.unixTime())).append("\" ");
        for (int i = 0; i < d.length; i++) {
            b(sb, str, d[i]);
        }
        sb.append("dropped=\"").append(str2).append("\" md5=\"\">");
    }

    private static void a(String[] strArr, StringBuilder sb) {
        a = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (Utils.isNotEmpty(strArr[i])) {
                a(strArr[i], sb);
            }
        }
    }

    private static void b(StringBuilder sb, String str, String str2) {
        a(str, str2);
        if (b == -1 || c <= b) {
            return;
        }
        sb.append(str2).append("=\"").append((CharSequence) str, b, c).append("\" ");
    }

    public static synchronized String generateXMLRequestString(String[] strArr, String str) {
        StringBuilder sb;
        sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        a(sb, strArr[0], str);
        a(strArr, sb);
        a(sb);
        sb.append("</events>");
        return sb.toString();
    }

    public static synchronized String getLabelFromEvent(String str, String str2) {
        a(str, str2);
        return (b == -1 || c <= b) ? null : str.substring(b, c);
    }
}
