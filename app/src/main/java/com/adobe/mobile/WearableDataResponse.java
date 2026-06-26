package com.adobe.mobile;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
class WearableDataResponse {
    protected boolean success = false;

    WearableDataResponse() {
    }

    protected boolean isSuccess() {
        return this.success;
    }

    static class GetResponse extends WearableDataResponse {
        protected byte[] result;

        protected GetResponse(DataMap dataMap) {
            this.result = dataMap.getByteArray("Result");
            if (this.result != null) {
                this.success = true;
            }
        }

        protected byte[] getResult() {
            return this.result;
        }
    }

    static class PostResponse extends WearableDataResponse {
        protected byte[] result;

        protected PostResponse(DataMap dataMap) {
            this.result = dataMap.getByteArray("Result");
            if (this.result != null && this.result.length > 0) {
                this.success = true;
            }
        }

        protected byte[] getResult() {
            return this.result;
        }
    }

    static class ThirdPartyResponse extends WearableDataResponse {
        protected ThirdPartyResponse(DataMap dataMap) {
            this.success = dataMap.getBoolean("Result");
        }
    }

    static class ShareConfigResponse extends WearableDataResponse {
        final DataMap result;

        protected ShareConfigResponse(DataMap dataMap) {
            this.result = dataMap;
        }

        protected DataMap getResult() {
            return this.result;
        }
    }

    static class CacheResponse extends WearableDataResponse {
        final boolean result;

        protected CacheResponse(DataMap dataMap, GoogleApiClient mGoogleApiClient) throws Throwable {
            boolean fileFound = dataMap.getBoolean("FileFound");
            if (!fileFound) {
                RemoteDownload.deleteFilesInDirectory("adbdownloadcache");
                this.result = false;
                return;
            }
            this.result = dataMap.getBoolean("Updated");
            if (this.result) {
                String url = dataMap.getString("URL");
                RemoteDownload.deleteCachedDataForURL(url, "adbdownloadcache");
                Asset asset = dataMap.getAsset("FileContent");
                String fileName = dataMap.getString("FileName");
                File dir = RemoteDownload.getDownloadCacheDirectory("adbdownloadcache");
                if (dir != null) {
                    WearableDataResponse.saveFileFromAsset(asset, dir.getPath() + File.separator + fileName, mGoogleApiClient);
                }
            }
        }

        protected boolean getResult() {
            return this.result;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveFileFromAsset(Asset asset, String fileName, GoogleApiClient mGoogleApiClient) throws Throwable {
        ConnectionResult result;
        if (asset == null || mGoogleApiClient == null || (result = GoogleApiClientWrapper.blockingConnect(mGoogleApiClient, 15000L, TimeUnit.MILLISECONDS)) == null || !result.isSuccess()) {
            return;
        }
        PendingResult<DataApi.GetFdForAssetResult> pendingResult = Wearable.DataApi.getFdForAsset(mGoogleApiClient, asset);
        DataApi.GetFdForAssetResult getFdForAssetResultAwait = GoogleApiClientWrapper.await(pendingResult);
        InputStream assetInputStream = getFdForAssetResultAwait instanceof DataApi.GetFdForAssetResult ? getFdForAssetResultAwait.getInputStream() : null;
        GoogleApiClientWrapper.disconnect(mGoogleApiClient);
        if (assetInputStream == null) {
            return;
        }
        File targetFile = new File(fileName);
        OutputStream outStream = null;
        try {
            try {
                OutputStream outStream2 = new FileOutputStream(targetFile);
                try {
                    byte[] buffer = new byte[8192];
                    while (true) {
                        int bytesRead = assetInputStream.read(buffer);
                        if (bytesRead == -1) {
                            break;
                        } else {
                            outStream2.write(buffer, 0, bytesRead);
                        }
                    }
                    if (outStream2 != null) {
                        try {
                            outStream2.close();
                            outStream = outStream2;
                        } catch (IOException e) {
                            StaticMethods.logDebugFormat("Wearable - Failed to close file output stream", new Object[0]);
                            outStream = outStream2;
                        }
                    } else {
                        outStream = outStream2;
                    }
                } catch (IOException e2) {
                    outStream = outStream2;
                    StaticMethods.logErrorFormat("Wearable - Failed to save cache file", new Object[0]);
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (IOException e3) {
                            StaticMethods.logDebugFormat("Wearable - Failed to close file output stream", new Object[0]);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    outStream = outStream2;
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (IOException e4) {
                            StaticMethods.logDebugFormat("Wearable - Failed to close file output stream", new Object[0]);
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    protected static WearableDataResponse createResponseFromDataMap(DataMap dataMap, GoogleApiClient mGoogleApiClient) {
        if (!dataMap.containsKey("Type")) {
            return null;
        }
        if (dataMap.getString("Type").equals("POST")) {
            return new PostResponse(dataMap);
        }
        if (dataMap.getString("Type").equals("GET")) {
            return new GetResponse(dataMap);
        }
        if (dataMap.getString("Type").equals("Config")) {
            return new ShareConfigResponse(dataMap);
        }
        if (dataMap.getString("Type").equals("File")) {
            return new CacheResponse(dataMap, mGoogleApiClient);
        }
        if (dataMap.getString("Type").equals("ThirdParty")) {
            return new ThirdPartyResponse(dataMap);
        }
        return null;
    }
}
