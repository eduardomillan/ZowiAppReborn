package com.comscore.applications;

import com.comscore.analytics.Core;
import com.comscore.measurement.Label;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AggregateMeasurement extends ApplicationMeasurement {
    protected AggregateMeasurement(Core core, EventType eventType, String str) {
        super(core, eventType, str);
    }

    private Boolean a(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!"0123456789".contains("" + str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private Boolean b(String str) {
        if (str != null && str.contains(",") && !str.contains(" ")) {
            return true;
        }
        return false;
    }

    private Boolean b(String str, String str2) {
        return Boolean.valueOf(str.contains(str2));
    }

    private String c(String str, String str2) {
        StringBuilder sb = new StringBuilder(str2);
        for (String str3 : c(str)) {
            if (sb.toString().contains(str3)) {
                String[] strArrSplit = sb.toString().split(";");
                for (int i = 0; i < strArrSplit.length; i++) {
                    if (strArrSplit[i].contains(str3)) {
                        String[] strArrSplit2 = strArrSplit[i].split(":");
                        sb.replace(sb.indexOf(strArrSplit[i]), sb.indexOf(strArrSplit[i]) + strArrSplit[i].length(), strArrSplit2[0] + ":" + Integer.valueOf(Integer.valueOf(strArrSplit2[1]).intValue() + 1));
                    }
                }
            } else if (sb.toString().equals("")) {
                sb.append(str3).append(":1");
            } else {
                sb.append(";").append(str3).append(":1");
            }
        }
        return sb.toString();
    }

    private List<String> c(String str) {
        String[] strArrSplit = str.split(",");
        ArrayList arrayList = new ArrayList();
        for (String str2 : strArrSplit) {
            arrayList.add(str2);
        }
        return arrayList;
    }

    public void aggregateLabels(List<Label> list) {
        for (Label label : list) {
            Label label2 = this.a.get(label.name);
            if (label2 == null) {
                if (b(label.value).booleanValue()) {
                    setLabel(label.name, c(label.value, ""), true);
                } else {
                    setLabel(label);
                }
            } else if (a(label2.value).booleanValue() && a(label.value).booleanValue()) {
                setLabel(label2.name, Integer.valueOf(Integer.valueOf(label.value).intValue() + Integer.valueOf(label2.value).intValue()).toString(), true);
            } else if (b(label.value).booleanValue()) {
                setLabel(label.name, c(label.value, label2.value), true);
            } else if (!b(label2.value, label.value).booleanValue()) {
                setLabel(label2.name, label2.value + "," + label.value, true);
            }
        }
    }

    public void formatLists() {
        ArrayList<Label> arrayList = new ArrayList();
        for (Label label : this.a.values()) {
            if (b(label.value).booleanValue()) {
                arrayList.add(label);
            }
        }
        for (Label label2 : arrayList) {
            setLabel(label2.name, c(label2.value, ""), true);
        }
    }

    public List<Label> getAggregateLabels() {
        ArrayList arrayList = new ArrayList();
        for (Label label : this.a.values()) {
            if (label.aggregate.booleanValue()) {
                arrayList.add(label);
            }
        }
        return arrayList;
    }
}
