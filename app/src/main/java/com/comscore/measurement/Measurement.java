package com.comscore.measurement;

import com.comscore.analytics.Core;
import com.comscore.utils.Constants;
import com.comscore.utils.Date;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class Measurement {
    protected HashMap<String, Label> a = new HashMap<>();
    protected String b;
    protected long c;

    protected Measurement(Core core) {
        setPixelURL(core.getPixelURL());
        this.c = Date.unixTime();
    }

    protected Boolean a(String str, long j) {
        return a(str, Long.toString(j));
    }

    protected Boolean a(String str, String str2) {
        Label label = this.a.get(str);
        if (label != null) {
            return Boolean.valueOf(label.value.equals(str2));
        }
        return false;
    }

    protected void a(Label label) {
        if (this.a.get(label.name) == null) {
            this.a.put(label.name, label);
        }
    }

    protected void a(String str, String str2, Boolean bool, Boolean bool2) {
        if (bool2.booleanValue()) {
            a(new PrivilegedLabel(str, str2, bool));
        } else {
            a(new Label(str, str2, bool));
        }
    }

    protected void a(HashMap<String, String> map) {
        a(map, false);
    }

    protected void a(HashMap<String, String> map, boolean z) {
        if (map != null) {
            for (String str : map.keySet()) {
                setLabel(str, map.get(str), Boolean.valueOf(z));
            }
        }
    }

    protected void b(String str, String str2, Boolean bool, Boolean bool2) {
        removeLabel(str, bool, bool2);
        a(str, str2, bool, bool2);
    }

    public Label getLabel(String str) {
        return this.a.get(str);
    }

    public String getPixelURL() {
        return this.b;
    }

    public Boolean hasLabel(String str) {
        return Boolean.valueOf(this.a.containsKey(str));
    }

    public void removeLabel(String str, Boolean bool, Boolean bool2) {
        Label label = this.a.get(str);
        if (label != null) {
            if (!(label instanceof PrivilegedLabel) || bool2.booleanValue() || bool.booleanValue()) {
                this.a.remove(str);
            }
        }
    }

    public String retrieveLabelsAsString(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        HashMap map = new HashMap(this.a);
        for (String str : strArr) {
            Label label = (Label) map.get(str);
            if (label != null) {
                sb.append(label.pack());
                map.remove(str);
            }
        }
        if (strArr != Constants.LABELS_ORDER) {
            for (String str2 : Constants.LABELS_ORDER) {
                Label label2 = (Label) map.get(str2);
                if (label2 != null) {
                    sb.append(label2.pack());
                    map.remove(str2);
                }
            }
        }
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            sb.append(((Label) it.next()).pack());
        }
        return (sb.length() <= 0 || sb.charAt(0) != '&') ? sb.toString() : sb.substring(1);
    }

    public void setLabel(Label label) {
        removeLabel(label.name, label.aggregate, Boolean.valueOf(label instanceof PrivilegedLabel));
        a(label);
    }

    public void setLabel(String str, String str2) {
        setLabel(str, str2, false);
    }

    public void setLabel(String str, String str2, Boolean bool) {
        removeLabel(str, bool, false);
        a(str, str2, bool, false);
    }

    public void setPixelURL(String str) {
        this.b = str;
    }
}
