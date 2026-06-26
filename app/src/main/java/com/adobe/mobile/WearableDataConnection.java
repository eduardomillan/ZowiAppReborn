package com.adobe.mobile;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class WearableDataConnection implements GoogleApiClient.OnConnectionFailedListener, DataApi.DataListener {
    protected DataMap mDataMap;
    private final GoogleApiClient mGoogleApiClient;
    private CountDownLatch mInTimeCountDownLatch;
    protected String requestID;

    protected WearableDataConnection(Context context) {
        this.mGoogleApiClient = new GoogleApiClient.Builder(context).addOnConnectionFailedListener(this).addApi(Wearable.API).build();
    }

    protected WearableDataResponse send(WearableDataRequest wearableDataRequest) {
        WearableDataResponse response = null;
        if (!connect(wearableDataRequest.getTimeOut())) {
            return null;
        }
        Wearable.DataApi.addListener(this.mGoogleApiClient, this);
        this.requestID = wearableDataRequest.getUUID();
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("/abdmobile/data/request");
        DataMap dataMap = dataMapRequest.getDataMap();
        dataMap.putAll(wearableDataRequest.getDataMap());
        PutDataRequest putDataRequest = dataMapRequest.asPutDataRequest();
        this.mInTimeCountDownLatch = new CountDownLatch(1);
        Wearable.DataApi.putDataItem(this.mGoogleApiClient, putDataRequest);
        try {
            if (this.mInTimeCountDownLatch.await(wearableDataRequest.getTimeOut(), TimeUnit.MILLISECONDS)) {
                response = getResponse();
                Wearable.DataApi.removeListener(this.mGoogleApiClient, this);
                GoogleApiClientWrapper.disconnect(this.mGoogleApiClient);
            } else {
                StaticMethods.logWarningFormat("Wearable - Failed to get data from handheld app", new Object[0]);
            }
            return response;
        } catch (InterruptedException e) {
            StaticMethods.logWarningFormat("Wearable - Failed to get data from handheld app", new Object[0]);
            return null;
        } finally {
            Wearable.DataApi.removeListener(this.mGoogleApiClient, this);
            GoogleApiClientWrapper.disconnect(this.mGoogleApiClient);
        }
    }

    private boolean connect(int timeOut) {
        GoogleApiClientWrapper.connect(this.mGoogleApiClient);
        if (!waitForConnect(timeOut)) {
            StaticMethods.logWarningFormat("Wearable - Timeout setup connection", new Object[0]);
            return false;
        }
        if (!hasNodes()) {
            StaticMethods.logWarningFormat("Wearable - No connected Node found", new Object[0]);
            return false;
        }
        return true;
    }

    public void onDataChanged(DataEventBuffer dataEvents) {
        Uri uri;
        DataMap returnedDataMap;
        Iterator it = dataEvents.iterator();
        while (it.hasNext()) {
            DataEvent event = (DataEvent) it.next();
            if (event.getType() == 1) {
                DataItem item = event.getDataItem();
                if (item != null && (uri = item.getUri()) != null && uri.getPath() != null && uri.getPath().compareTo("/abdmobile/data/response") == 0 && (returnedDataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap()) != null && returnedDataMap.containsKey("ID") && returnedDataMap.getString("ID").equals(this.requestID)) {
                    this.mDataMap = returnedDataMap;
                    this.mInTimeCountDownLatch.countDown();
                    return;
                }
            } else {
                return;
            }
        }
    }

    private boolean waitForConnect(int timeOut) {
        if (GoogleApiClientWrapper.isConnected(this.mGoogleApiClient).booleanValue()) {
            return true;
        }
        ConnectionResult connectionResult = GoogleApiClientWrapper.blockingConnect(this.mGoogleApiClient, timeOut, TimeUnit.MILLISECONDS);
        return connectionResult != null && connectionResult.isSuccess();
    }

    private boolean hasNodes() {
        PendingResult<NodeApi.GetConnectedNodesResult> result = Wearable.NodeApi.getConnectedNodes(this.mGoogleApiClient);
        NodeApi.GetConnectedNodesResult getConnectedNodesResultAwait = GoogleApiClientWrapper.await(result);
        NodeApi.GetConnectedNodesResult nodes = getConnectedNodesResultAwait instanceof NodeApi.GetConnectedNodesResult ? getConnectedNodesResultAwait : null;
        return (nodes == null || nodes.getNodes() == null || nodes.getNodes().size() <= 0) ? false : true;
    }

    protected WearableDataResponse getResponse() {
        if (this.mDataMap == null) {
            return null;
        }
        return WearableDataResponse.createResponseFromDataMap(this.mDataMap, this.mGoogleApiClient);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult result) {
    }
}
