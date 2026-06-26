package com.adobe.mobile;

import android.net.Uri;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class DataListenerWearable {
    public static void onDataChanged(DataEventBuffer dataEvents) {
        DataItem item;
        Uri uri;
        if (dataEvents != null) {
            Iterator it = dataEvents.iterator();
            while (it.hasNext()) {
                DataEvent event = (DataEvent) it.next();
                if (event.getType() == 1 && (item = event.getDataItem()) != null && (uri = item.getUri()) != null && uri.getPath() != null && uri.getPath().startsWith("/abdmobile/data/config/")) {
                    ConfigSynchronizer.restoreConfig(item);
                }
            }
        }
    }
}
