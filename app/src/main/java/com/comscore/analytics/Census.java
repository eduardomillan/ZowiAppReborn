package com.comscore.analytics;

import android.content.Context;
import com.comscore.applications.EventType;
import com.comscore.utils.Constants;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class Census {
    private static Census a = null;
    private static Object c = new Object();
    private String b;

    private Census() {
    }

    public static Census getInstance() {
        if (a == null) {
            synchronized (c) {
                if (a == null) {
                    a = new Census();
                }
            }
        }
        return a;
    }

    public void notifyStart(Context context, String str, String str2) {
        Core core = comScore.getCore();
        core.setAppContext(context);
        if (str != null && str.length() > 0) {
            this.b = str;
        }
        if (str2 != null && str2.length() > 0) {
            core.setPublisherSecret(str2, true);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("c2", this.b);
        map.put("name", Constants.DEFAULT_START_PAGE_NAME);
        core.setPixelURL(core.isSecure() ? Constants.CENSUS_URL_SECURE : Constants.CENSUS_URL, false);
        core.notify(EventType.START, map, true);
    }
}
