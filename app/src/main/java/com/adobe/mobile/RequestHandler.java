package com.adobe.mobile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
final class RequestHandler {
    private static final int BUF_SIZE = 1024;
    private static final int CONNECTION_TIMEOUT = 2000;
    private static final int MAX_REDIRECT_COUNT = 21;

    protected interface HeaderCallback {
        void call(Map<String, List<String>> map);
    }

    RequestHandler() {
    }

    protected static byte[] retrieveData(String url, final Map<String, String> headers, int readTimeout, String source) {
        if (StaticMethods.isWearableApp()) {
            return WearableFunctionBridge.retrieveData(url, readTimeout);
        }
        Callable<Map<String, String>> headersCallback = new Callable<Map<String, String>>() { // from class: com.adobe.mobile.RequestHandler.1
            @Override // java.util.concurrent.Callable
            public Map<String, String> call() throws Exception {
                return headers;
            }
        };
        if (headers == null) {
            headersCallback = null;
        }
        return retrieveData(url, readTimeout, source, headersCallback, null);
    }

    protected static byte[] retrieveData(String url, int readTimeout, String source, Callable<Map<String, String>> requestHeaderProvider, HeaderCallback responseHeaderReceiver) {
        Map<String, String> requestHeaders;
        HttpURLConnection connection = null;
        InputStream inStream = null;
        String _url = url;
        int responseCode = 0;
        int redirectCount = 0;
        while (true) {
            if (redirectCount > 21) {
                StaticMethods.logErrorFormat("%s - Too many redirects for (%s) - %d", source, url, Integer.valueOf(redirectCount));
            } else {
                try {
                    try {
                        try {
                            try {
                                connection = (HttpURLConnection) new URL(_url).openConnection();
                                connection.setConnectTimeout(2000);
                                connection.setReadTimeout(readTimeout);
                                connection.setInstanceFollowRedirects(false);
                                connection.setRequestProperty("Accept-Language", StaticMethods.getDefaultAcceptLanguage());
                                connection.setRequestProperty("User-Agent", StaticMethods.getDefaultUserAgent());
                                if (requestHeaderProvider != null && (requestHeaders = requestHeaderProvider.call()) != null) {
                                    for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                                    }
                                }
                                responseCode = connection.getResponseCode();
                                if (responseHeaderReceiver != null) {
                                    responseHeaderReceiver.call(connection.getHeaderFields());
                                }
                                switch (responseCode) {
                                    case 301:
                                    case 302:
                                        redirectCount++;
                                        String location = connection.getHeaderField("Location");
                                        URL base = new URL(_url);
                                        URL next = new URL(base, location);
                                        _url = next.toExternalForm();
                                        break;
                                }
                            } catch (IOException ex) {
                                StaticMethods.logWarningFormat("%s - IOException while sending request (%s)", source, ex.getLocalizedMessage());
                                if (connection != null) {
                                    connection.disconnect();
                                }
                                if (inStream == null) {
                                    return null;
                                }
                                try {
                                    inStream.close();
                                    return null;
                                } catch (IOException ex2) {
                                    StaticMethods.logWarningFormat("%s - Unable to close stream (%s)", source, ex2.getLocalizedMessage());
                                    return null;
                                }
                            }
                        } catch (Error er) {
                            StaticMethods.logWarningFormat("%s - Unexpected error while sending request (%s)", source, er.getLocalizedMessage());
                            if (connection != null) {
                                connection.disconnect();
                            }
                            if (inStream == null) {
                                return null;
                            }
                            try {
                                inStream.close();
                                return null;
                            } catch (IOException ex3) {
                                StaticMethods.logWarningFormat("%s - Unable to close stream (%s)", source, ex3.getLocalizedMessage());
                                return null;
                            }
                        }
                    } catch (Exception ex4) {
                        StaticMethods.logWarningFormat("%s - Exception while sending request (%s)", source, ex4.getLocalizedMessage());
                        if (connection != null) {
                            connection.disconnect();
                        }
                        if (inStream == null) {
                            return null;
                        }
                        try {
                            inStream.close();
                            return null;
                        } catch (IOException ex5) {
                            StaticMethods.logWarningFormat("%s - Unable to close stream (%s)", source, ex5.getLocalizedMessage());
                            return null;
                        }
                    }
                } catch (Throwable th) {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException ex6) {
                            StaticMethods.logWarningFormat("%s - Unable to close stream (%s)", source, ex6.getLocalizedMessage());
                        }
                    }
                    throw th;
                }
            }
        }
        if (responseCode != 200) {
            if (connection != null) {
                connection.disconnect();
            }
            if (0 != 0) {
                try {
                    inStream.close();
                } catch (IOException ex7) {
                    StaticMethods.logWarningFormat("%s - Unable to close stream (%s)", source, ex7.getLocalizedMessage());
                }
            }
            return null;
        }
        inStream = connection.getInputStream();
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            int len = inStream.read(buffer);
            if (len == -1) {
                inStream.close();
                byte[] byteArray = baos.toByteArray();
                if (connection != null) {
                    connection.disconnect();
                }
                if (inStream == null) {
                    return byteArray;
                }
                try {
                    inStream.close();
                    return byteArray;
                } catch (IOException ex8) {
                    StaticMethods.logWarningFormat("%s - Unable to close stream (%s)", source, ex8.getLocalizedMessage());
                    return byteArray;
                }
            }
            baos.write(buffer, 0, len);
        }
    }

    protected static void sendGenericRequest(String url, Map<String, String> headers, int timeout, String source) {
        if (url != null) {
            if (StaticMethods.isWearableApp()) {
                WearableFunctionBridge.sendGenericRequest(url, timeout, source);
                return;
            }
            try {
                HttpURLConnection connection = requestConnect(url);
                if (connection != null) {
                    connection.setConnectTimeout(timeout);
                    connection.setReadTimeout(timeout);
                    if (headers != null) {
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            String value = entry.getValue();
                            if (value.trim().length() > 0) {
                                connection.setRequestProperty(entry.getKey(), value);
                            }
                        }
                    }
                    StaticMethods.logDebugFormat("%s - Request Sent(%s)", source, url);
                    connection.getResponseCode();
                    InputStream stream = connection.getInputStream();
                    stream.close();
                    connection.disconnect();
                }
            } catch (SocketTimeoutException e) {
                StaticMethods.logWarningFormat("%s - Timed out sending request(%s)", source, url);
            } catch (IOException e2) {
                StaticMethods.logWarningFormat("%s - IOException while sending request, may retry(%s)", source, e2.getLocalizedMessage());
            } catch (Error e3) {
                StaticMethods.logWarningFormat("%s - Exception while attempting to send hit, will not retry(%s)", source, e3.getLocalizedMessage());
            } catch (Exception e4) {
                StaticMethods.logWarningFormat("%s - Exception while attempting to send hit, will not retry(%s)", source, e4.getLocalizedMessage());
            }
        }
    }

    protected static byte[] retrieveAnalyticsRequestData(String url, String postBody, Map<String, String> headers, int timeout, String logPrefix) {
        if (url == null) {
            return null;
        }
        if (StaticMethods.isWearableApp()) {
            return WearableFunctionBridge.retrieveAnalyticsRequestData(url, postBody, timeout, logPrefix);
        }
        HttpURLConnection connection = requestConnect(url);
        if (connection == null) {
            return null;
        }
        try {
            try {
                try {
                    connection.setConnectTimeout(timeout);
                    connection.setReadTimeout(timeout);
                    connection.setRequestMethod("POST");
                    if (!MobileConfig.getInstance().getSSL()) {
                        connection.setRequestProperty("connection", "close");
                    }
                    byte[] outputBytes = postBody.getBytes("UTF-8");
                    connection.setFixedLengthStreamingMode(outputBytes.length);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    if (headers != null) {
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            connection.setRequestProperty(entry.getKey(), entry.getValue());
                        }
                    }
                    OutputStream postBodyStream = new BufferedOutputStream(connection.getOutputStream());
                    postBodyStream.write(outputBytes);
                    postBodyStream.close();
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    if (MobileConfig.getInstance().getSSL() || connection.getResponseCode() == 200) {
                        while (true) {
                            int len = inputStream.read(buffer);
                            if (len == -1) {
                                break;
                            }
                            baos.write(buffer, 0, len);
                        }
                    }
                    inputStream.close();
                    StaticMethods.logDebugFormat("%s - Request Sent(%s)", logPrefix, postBody);
                    byte[] byteArray = baos.toByteArray();
                    if (MobileConfig.getInstance().getSSL()) {
                        return byteArray;
                    }
                    connection.disconnect();
                    return byteArray;
                } catch (Error e) {
                    StaticMethods.logErrorFormat("%s - Exception while attempting to send hit, will not retry(%s)", logPrefix, e.getLocalizedMessage());
                    byte[] bArr = new byte[0];
                    if (MobileConfig.getInstance().getSSL()) {
                        return bArr;
                    }
                    connection.disconnect();
                    return bArr;
                } catch (SocketTimeoutException e2) {
                    StaticMethods.logDebugFormat("%s - Timed out sending request(%s)", logPrefix, postBody);
                    if (MobileConfig.getInstance().getSSL()) {
                        return null;
                    }
                    connection.disconnect();
                    return null;
                }
            } catch (IOException e3) {
                StaticMethods.logDebugFormat("%s - IOException while sending request, may retry(%s)", logPrefix, e3.getLocalizedMessage());
                if (MobileConfig.getInstance().getSSL()) {
                    return null;
                }
                connection.disconnect();
                return null;
            } catch (Exception e4) {
                StaticMethods.logErrorFormat("%s - Exception while attempting to send hit, will not retry(%s)", logPrefix, e4.getLocalizedMessage());
                byte[] bArr2 = new byte[0];
                if (MobileConfig.getInstance().getSSL()) {
                    return bArr2;
                }
                connection.disconnect();
                return bArr2;
            }
        } catch (Throwable th) {
            if (!MobileConfig.getInstance().getSSL()) {
                connection.disconnect();
            }
            throw th;
        }
    }

    protected static boolean sendThirdPartyRequest(String url, String postBody, Map<String, String> headers, int timeout, String postType, String logPrefix) {
        if (url == null) {
            return false;
        }
        if (StaticMethods.isWearableApp()) {
            return WearableFunctionBridge.sendThirdPartyRequest(url, postBody, timeout, postType, logPrefix);
        }
        HttpURLConnection connection = requestConnect(url);
        if (connection == null) {
            return false;
        }
        try {
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("GET");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (postBody != null && postBody.length() > 0) {
                connection.setRequestMethod("POST");
                String contentType = (postType == null || postType.length() <= 0) ? "application/x-www-form-urlencoded" : postType;
                byte[] outputBytes = postBody.getBytes("UTF-8");
                connection.setFixedLengthStreamingMode(outputBytes.length);
                connection.setRequestProperty("Content-Type", contentType);
                OutputStream connectionOutputStream = connection.getOutputStream();
                OutputStream postBodyStream = new BufferedOutputStream(connectionOutputStream);
                postBodyStream.write(outputBytes);
                postBodyStream.close();
            }
            InputStream inputStream = connection.getInputStream();
            byte[] b = new byte[10];
            while (inputStream.read(b) > 0) {
            }
            inputStream.close();
            StaticMethods.logDebugFormat("%s - Successfully forwarded hit (%s body: %s type: %s)", logPrefix, url, postBody, postType);
        } catch (Error e) {
            StaticMethods.logErrorFormat("%s - Exception while attempting to send hit, will not retry (%s)", logPrefix, e.getLocalizedMessage());
        } catch (SocketTimeoutException e2) {
            StaticMethods.logDebugFormat("%s - Timed out sending request (%s)", logPrefix, postBody);
            return false;
        } catch (IOException e3) {
            StaticMethods.logDebugFormat("%s - IOException while sending request, will not retry (%s)", logPrefix, e3.getLocalizedMessage());
        } catch (Exception e4) {
            StaticMethods.logErrorFormat("%s - Exception while attempting to send hit, will not retry (%s)", logPrefix, e4.getLocalizedMessage());
        }
        return true;
    }

    private static HttpURLConnection requestConnect(String url) {
        try {
            return (HttpURLConnection) new URL(url).openConnection();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Adobe Mobile - Exception opening URL(%s)", e.getLocalizedMessage());
            return null;
        }
    }
}
