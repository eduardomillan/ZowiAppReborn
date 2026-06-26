package com.adobe.mobile;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class DataListenerHandheld {
    private static void handleRequest(DataMap requestDataMap, GoogleApiClient mGoogleApiClient, Context context) {
        if (mGoogleApiClient == null || context == null || requestDataMap == null) {
            StaticMethods.logDebugFormat("Wearable - GoogleApiClient or Context or DataMap is null", new Object[0]);
            return;
        }
        WearableDataRequest request = WearableDataRequest.createRequestFromDataMap(requestDataMap);
        if (request == null) {
            StaticMethods.logDebugFormat("Wearable - Invalid data request (%s)", requestDataMap.toString());
            return;
        }
        ConnectionResult connectionResult = GoogleApiClientWrapper.blockingConnect(mGoogleApiClient, 15000L, TimeUnit.MILLISECONDS);
        if (connectionResult == null || !connectionResult.isSuccess()) {
            StaticMethods.logDebugFormat("Wearable - Failed to setup connection", new Object[0]);
            return;
        }
        DataMap result = request.handle(context);
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("/abdmobile/data/response");
        dataMapRequest.getDataMap().putAll(result);
        Wearable.DataApi.putDataItem(mGoogleApiClient, dataMapRequest.asPutDataRequest());
    }

    public static void onDataChanged(DataEventBuffer dataEvents, GoogleApiClient mGoogleApiClient, Context context) {
        DataItem item;
        Uri uri;
        if (dataEvents != null) {
            Iterator it = dataEvents.iterator();
            while (it.hasNext()) {
                DataEvent event = (DataEvent) it.next();
                if (event.getType() == 1 && (item = event.getDataItem()) != null && (uri = item.getUri()) != null && uri.getPath() != null && uri.getPath().startsWith("/abdmobile/data/request")) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    handleRequest(dataMap, mGoogleApiClient, context);
                }
            }
        }
    }
}
