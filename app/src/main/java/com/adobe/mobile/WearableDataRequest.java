package com.adobe.mobile;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
abstract class WearableDataRequest {
    protected int timeOut;
    protected String uuid;

    protected abstract DataMap getDataMap();

    protected abstract DataMap handle(Context context);

    protected WearableDataRequest() {
        this.uuid = UUID.randomUUID().toString();
    }

    protected WearableDataRequest(int timeOut) {
        this();
        this.timeOut = timeOut;
    }

    protected String getUUID() {
        return this.uuid;
    }

    protected int getTimeOut() {
        return this.timeOut;
    }

    static class Get extends WearableDataRequest {
        protected static final String logPrefix = "Wearable GET Requested Forward";
        protected String url;

        protected Get(String url, int timeOut) {
            super(timeOut);
            this.url = url;
        }

        protected Get(DataMap dataMap) {
            this.url = dataMap.getString("URL");
            this.uuid = dataMap.getString("ID");
            this.timeOut = dataMap.getInt("Timeout");
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap getDataMap() {
            DataMap dataMap = new DataMap();
            dataMap.putString("ID", this.uuid);
            dataMap.putInt("Timeout", this.timeOut);
            dataMap.putString("Type", "GET");
            dataMap.putString("URL", this.url);
            return dataMap;
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap handle(Context context) {
            DataMap responseDataMap = new DataMap();
            byte[] byteArray = RequestHandler.retrieveData(this.url, null, this.timeOut, logPrefix);
            responseDataMap.putByteArray("Result", byteArray);
            responseDataMap.putString("ID", this.uuid);
            responseDataMap.putString("Type", "GET");
            return responseDataMap;
        }

        protected String getURL() {
            return this.url;
        }
    }

    static class Post extends WearableDataRequest {
        protected static final String logPrefix = "Wearable POST Request Forward";
        protected String body;
        protected String url;
        private static String userAgent = null;
        private static final Object _userAgentMutex = new Object();

        protected Post() {
        }

        protected Post(String url, String body, int timeOut) {
            super(timeOut);
            this.url = url;
            this.body = body;
        }

        protected Post(DataMap dataMap) {
            this.timeOut = dataMap.getInt("Timeout");
            this.url = dataMap.getString("URL");
            this.body = dataMap.getString("Body");
            this.uuid = dataMap.getString("ID");
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap getDataMap() {
            DataMap dataMap = new DataMap();
            dataMap.putString("ID", this.uuid);
            dataMap.putString("Type", "POST");
            dataMap.putString("URL", this.url);
            dataMap.putInt("Timeout", this.timeOut);
            dataMap.putString("Body", this.body);
            return dataMap;
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap handle(Context context) {
            DataMap responseDataMap = new DataMap();
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Accept-Language", getDefaultAcceptLanguage(context));
            headers.put("User-Agent", getDefaultUserAgent(context));
            byte[] byteArray = RequestHandler.retrieveAnalyticsRequestData(this.url, this.body, headers, this.timeOut, logPrefix);
            responseDataMap.putByteArray("Result", byteArray);
            responseDataMap.putString("ID", this.uuid);
            responseDataMap.putString("Type", "POST");
            return responseDataMap;
        }

        protected static String getDefaultAcceptLanguage(Context context) {
            Resources resources;
            Configuration configuration;
            Locale locale;
            if (context == null || (resources = context.getResources()) == null || (configuration = resources.getConfiguration()) == null || (locale = configuration.locale) == null) {
                return null;
            }
            return locale.toString().replace('_', '-');
        }

        protected static String getDefaultUserAgent(Context context) {
            String str;
            synchronized (_userAgentMutex) {
                if (userAgent == null) {
                    userAgent = "Mozilla/5.0 (Linux; U; Android " + Build.VERSION.RELEASE + "; " + getDefaultAcceptLanguage(context) + "; " + Build.MODEL + " Build/" + Build.ID + ")";
                }
                str = userAgent;
            }
            return str;
        }

        protected String getURL() {
            return this.url;
        }
    }

    static class ThirdPartyRequest extends Post {
        protected static final String logPrefix = "Wearable Third Party Request Forward";
        protected String postType;

        protected ThirdPartyRequest(String url, String body, int timeout) {
            super(url, body, timeout);
        }

        protected ThirdPartyRequest(String url, String body, int timeOut, String postType) {
            this(url, body, timeOut);
            this.postType = postType;
        }

        protected ThirdPartyRequest(DataMap dataMap) {
            super(dataMap);
            this.postType = dataMap.getString("PostType");
        }

        @Override // com.adobe.mobile.WearableDataRequest.Post, com.adobe.mobile.WearableDataRequest
        protected DataMap getDataMap() {
            DataMap dataMap = super.getDataMap();
            dataMap.putString("Type", "ThirdParty");
            dataMap.putString("PostType", this.postType);
            return dataMap;
        }

        @Override // com.adobe.mobile.WearableDataRequest.Post, com.adobe.mobile.WearableDataRequest
        protected DataMap handle(Context context) {
            DataMap responseDataMap = new DataMap();
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Accept-Language", getDefaultAcceptLanguage(context));
            headers.put("User-Agent", getDefaultUserAgent(context));
            boolean result = RequestHandler.sendThirdPartyRequest(this.url, this.body, headers, this.timeOut, this.postType, logPrefix);
            responseDataMap.putBoolean("Result", result);
            responseDataMap.putString("ID", this.uuid);
            responseDataMap.putString("Type", "ThirdParty");
            return responseDataMap;
        }
    }

    static class ShareConfig extends WearableDataRequest {
        protected ShareConfig(int timeOut) {
            super(timeOut);
        }

        protected ShareConfig(DataMap dataMap) {
            this.uuid = dataMap.getString("ID");
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap getDataMap() {
            DataMap dataMap = new DataMap();
            dataMap.putString("Type", "Config");
            dataMap.putString("ID", this.uuid);
            return dataMap;
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap handle(Context context) {
            DataMap responseDataMap = new DataMap();
            responseDataMap.putString("ID", this.uuid);
            responseDataMap.putString("Type", "Config");
            responseDataMap.putAll(ConfigSynchronizer.getSharedConfig());
            return responseDataMap;
        }
    }

    static class Cache extends WearableDataRequest {
        protected String fileName;
        protected String url;

        protected Cache(String url, String fileName, int timeOut) {
            super(timeOut);
            this.url = url;
            this.fileName = fileName;
        }

        protected Cache(DataMap dataMap) {
            this.uuid = dataMap.getString("ID");
            this.fileName = dataMap.getString("FileName");
            this.url = dataMap.getString("URL");
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap getDataMap() {
            DataMap dataMap = new DataMap();
            dataMap.putString("Type", "File");
            dataMap.putString("ID", this.uuid);
            dataMap.putString("URL", this.url);
            dataMap.putString("FileName", this.fileName);
            return dataMap;
        }

        @Override // com.adobe.mobile.WearableDataRequest
        protected DataMap handle(Context context) throws Throwable {
            DataMap responseDataMap = new DataMap();
            responseDataMap.putString("ID", this.uuid);
            responseDataMap.putString("Type", "File");
            responseDataMap.putString("URL", this.url);
            File file = RemoteDownload.getFileForCachedURL(this.url);
            if (file == null) {
                responseDataMap.putBoolean("FileFound", false);
            } else {
                responseDataMap.putBoolean("FileFound", true);
                if (file.getName().equals(this.fileName)) {
                    responseDataMap.putBoolean("Updated", false);
                } else {
                    responseDataMap.putBoolean("Updated", true);
                    responseDataMap.putString("FileName", file.getName());
                    byte[] fileContents = WearableDataRequest.readFile(file);
                    if (fileContents != null && fileContents.length > 0) {
                        responseDataMap.putAsset("FileContent", Asset.createFromBytes(fileContents));
                    }
                }
            }
            return responseDataMap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] readFile(File file) throws Throwable {
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            try {
                InputStream ios2 = new FileInputStream(file);
                try {
                    if (ios2.read(buffer) == -1) {
                        if (ios2 != null) {
                            try {
                                ios2.close();
                            } catch (IOException e) {
                                StaticMethods.logDebugFormat("Wearable - Failed to close the file input stream", new Object[0]);
                            }
                        }
                        ios = ios2;
                        buffer = null;
                    } else {
                        if (ios2 != null) {
                            try {
                                ios2.close();
                            } catch (IOException e2) {
                                StaticMethods.logDebugFormat("Wearable - Failed to close the file input stream", new Object[0]);
                            }
                        }
                        ios = ios2;
                    }
                } catch (IOException e3) {
                    ios = ios2;
                    StaticMethods.logErrorFormat("Wearable - Failed to read cached file", new Object[0]);
                    if (ios != null) {
                        try {
                            ios.close();
                        } catch (IOException e4) {
                            StaticMethods.logDebugFormat("Wearable - Failed to close the file input stream", new Object[0]);
                        }
                    }
                    buffer = null;
                } catch (Exception e5) {
                    ios = ios2;
                    StaticMethods.logErrorFormat("Wearable - Failed to read cached file", new Object[0]);
                    if (ios != null) {
                        try {
                            ios.close();
                        } catch (IOException e6) {
                            StaticMethods.logDebugFormat("Wearable - Failed to close the file input stream", new Object[0]);
                        }
                    }
                    buffer = null;
                } catch (Throwable th) {
                    th = th;
                    ios = ios2;
                    if (ios != null) {
                        try {
                            ios.close();
                        } catch (IOException e7) {
                            StaticMethods.logDebugFormat("Wearable - Failed to close the file input stream", new Object[0]);
                        }
                    }
                    throw th;
                }
            } catch (IOException e8) {
            } catch (Exception e9) {
            }
            return buffer;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    protected static WearableDataRequest createGetRequest(String url, int timeOut) {
        return new Get(url, timeOut);
    }

    protected static WearableDataRequest createPostRequest(String url, String body, int timeOut) {
        return new Post(url, body, timeOut);
    }

    protected static WearableDataRequest createThirdPartyRequest(String url, String body, int timeOut, String postType) {
        return new ThirdPartyRequest(url, body, timeOut, postType);
    }

    protected static WearableDataRequest createShareConfigRequest(int timeOut) {
        return new ShareConfig(timeOut);
    }

    protected static WearableDataRequest createFileRequest(String url, String fileName, int timeOut) {
        return new Cache(url, fileName, timeOut);
    }

    protected static WearableDataRequest createRequestFromDataMap(DataMap dataMap) {
        if (!dataMap.containsKey("Type")) {
            return null;
        }
        if (dataMap.getString("Type").equals("POST")) {
            return new Post(dataMap);
        }
        if (dataMap.getString("Type").equals("GET")) {
            return new Get(dataMap);
        }
        if (dataMap.getString("Type").equals("Config")) {
            return new ShareConfig(dataMap);
        }
        if (dataMap.getString("Type").equals("File")) {
            return new Cache(dataMap);
        }
        if (dataMap.getString("Type").equals("ThirdParty")) {
            return new ThirdPartyRequest(dataMap);
        }
        return null;
    }
}
