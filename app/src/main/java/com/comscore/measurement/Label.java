package com.comscore.measurement;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
public class Label {
    public Boolean aggregate;
    public String name;
    public String value;

    public Label(String str, String str2, Boolean bool) {
        this.name = str;
        this.value = str2;
        this.aggregate = bool;
    }

    private String a(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char lowerCase = Character.toLowerCase(str.charAt(i));
            if ((lowerCase >= 'a' && lowerCase <= 'z') || ((lowerCase >= '0' && lowerCase <= '9') || lowerCase == '_' || lowerCase == '-' || lowerCase == '.')) {
                sb.append(lowerCase);
            }
        }
        return sb.toString();
    }

    public String pack() {
        StringBuilder sb = new StringBuilder();
        if (this.name != null && this.value != null) {
            String strA = a(this.name);
            if (strA.length() > 0) {
                try {
                    sb.append("&").append(strA).append("=").append(URLEncoder.encode(this.value, "UTF-8").replace("+", "%20"));
                    return sb.toString();
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return "";
    }
}
