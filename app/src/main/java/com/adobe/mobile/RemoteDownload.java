package com.adobe.mobile;

import com.google.android.gms.search.SearchAuth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
class RemoteDownload {
    private static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
    private static final int DEFAULT_READ_TIMEOUT = 10000;

    protected interface RemoteDownloadBlock {
        void call(boolean z, File file);
    }

    RemoteDownload() {
    }

    protected static boolean stringIsUrl(String stringUrl) {
        if (stringUrl == null || stringUrl.length() <= 0) {
            return false;
        }
        try {
            new URL(stringUrl);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    protected static void remoteDownloadAsync(String url, int connectionTimeout, int readTimeout, RemoteDownloadBlock block, String directory) {
        Thread thread = new Thread(new DownloadFileTask(url, block, connectionTimeout, readTimeout, directory));
        thread.start();
    }

    protected static void remoteDownloadAsync(String url, int connectionTimeout, int readTimeout, RemoteDownloadBlock block) {
        remoteDownloadAsync(url, connectionTimeout, readTimeout, block, "adbdownloadcache");
    }

    protected static void remoteDownloadAsync(String url, RemoteDownloadBlock block) {
        remoteDownloadAsync(url, SearchAuth.StatusCodes.AUTH_DISABLED, SearchAuth.StatusCodes.AUTH_DISABLED, block, "adbdownloadcache");
    }

    protected static void remoteDownloadAsync(String url, String directory, RemoteDownloadBlock block) {
        remoteDownloadAsync(url, SearchAuth.StatusCodes.AUTH_DISABLED, SearchAuth.StatusCodes.AUTH_DISABLED, block, directory);
    }

    protected static void remoteDownloadSync(String url, int connectionTimeout, int readTimeout, RemoteDownloadBlock block, String directory) {
        Runnable r = new DownloadFileTask(url, block, connectionTimeout, readTimeout, directory);
        r.run();
    }

    protected static void remoteDownloadSync(String url, int connectionTimeout, int readTimeout, RemoteDownloadBlock block) {
        remoteDownloadSync(url, connectionTimeout, readTimeout, block, "adbdownloadcache");
    }

    protected static void remoteDownloadSync(String url, RemoteDownloadBlock block) {
        remoteDownloadSync(url, SearchAuth.StatusCodes.AUTH_DISABLED, SearchAuth.StatusCodes.AUTH_DISABLED, block, "adbdownloadcache");
    }

    protected static void remoteDownloadSync(String url, String directory, RemoteDownloadBlock block) {
        remoteDownloadSync(url, SearchAuth.StatusCodes.AUTH_DISABLED, SearchAuth.StatusCodes.AUTH_DISABLED, block, directory);
    }

    protected static File getFileForCachedURL(String url) {
        return getFileForCachedURL(url, "adbdownloadcache");
    }

    protected static File getFileForCachedURL(String url, String directory) {
        File cacheDirectory;
        if (url == null || url.length() < 1 || (cacheDirectory = getDownloadCacheDirectory(directory)) == null) {
            return null;
        }
        String[] cachedFiles = cacheDirectory.list();
        if (cachedFiles == null || cachedFiles.length < 1) {
            StaticMethods.logDebugFormat("Cached Files - Directory is empty (%s).", cacheDirectory.getAbsolutePath());
            return null;
        }
        String hashedName = md5hash(url);
        for (String fileName : cachedFiles) {
            if (fileName.substring(0, fileName.lastIndexOf(46)).equals(hashedName)) {
                return new File(cacheDirectory, fileName);
            }
        }
        StaticMethods.logDebugFormat("Cached Files - This file has not previously been cached (%s).", url);
        return null;
    }

    protected static void deleteFilesForDirectoryNotInList(String directory, List<String> urls) {
        File[] cachedFiles;
        if (urls == null || urls.size() <= 0) {
            deleteFilesInDirectory(directory);
            return;
        }
        File cacheDir = getDownloadCacheDirectory(directory);
        if (cacheDir != null && (cachedFiles = cacheDir.listFiles()) != null && cachedFiles.length > 0) {
            ArrayList<String> hashedUrls = new ArrayList<>();
            for (String url : urls) {
                hashedUrls.add(md5hash(url));
            }
            for (File file : cachedFiles) {
                String fileName = file.getName();
                String fileHash = fileName.substring(0, fileName.indexOf("."));
                if (!hashedUrls.contains(fileHash)) {
                    if (file.delete()) {
                        StaticMethods.logDebugFormat("Cached File - Removed unused cache file", new Object[0]);
                    } else {
                        StaticMethods.logWarningFormat("Cached File - Failed to remove unused cache file", new Object[0]);
                    }
                }
            }
        }
    }

    protected static void deleteFilesInDirectory(String directory) {
        File[] cachedFiles;
        File cacheDir = getDownloadCacheDirectory(directory);
        if (cacheDir != null && (cachedFiles = cacheDir.listFiles()) != null && cachedFiles.length > 0) {
            for (File file : cachedFiles) {
                if (file.delete()) {
                    StaticMethods.logDebugFormat("Cached File - Removed unused cache file", new Object[0]);
                } else {
                    StaticMethods.logWarningFormat("Cached File - Failed to remove unused cache file", new Object[0]);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File getNewCachedFile(String url, Date lastModified, String etag, String directory) {
        String md5Hash;
        if (url == null || url.length() < 1) {
            StaticMethods.logWarningFormat("Cached File - Invalid url parameter while attempting to create cache file. Could not save data.", new Object[0]);
            return null;
        }
        if (lastModified == null) {
            StaticMethods.logWarningFormat("Cached File - Invalid lastModified parameter while attempting to create cache file. Could not save data.", new Object[0]);
            return null;
        }
        if (etag == null || etag.length() < 1) {
            StaticMethods.logWarningFormat("Cached File - Invalid etag parameter while attempting to create cache file. Could not save data.", new Object[0]);
            return null;
        }
        File cacheDirectory = getDownloadCacheDirectory(directory);
        if (cacheDirectory == null || (md5Hash = md5hash(url)) == null || md5Hash.length() < 1) {
            return null;
        }
        return new File(cacheDirectory.getPath() + File.separator + md5hash(url) + "." + lastModified.getTime() + "_" + etag);
    }

    protected static File getDownloadCacheDirectory(String directory) {
        File downloadCacheDirectory = new File(StaticMethods.getCacheDirectory(), directory);
        if (!downloadCacheDirectory.exists() && !downloadCacheDirectory.mkdir()) {
            StaticMethods.logWarningFormat("Cached File - unable to open/make download cache directory", new Object[0]);
            return null;
        }
        return downloadCacheDirectory;
    }

    protected static boolean deleteCachedDataForURL(String url, String directory) {
        if (url == null || url.length() < 1) {
            StaticMethods.logWarningFormat("Cached File - tried to delete cached file, but file path was empty", new Object[0]);
            return false;
        }
        File cachedFile = getFileForCachedURL(url, directory);
        return cachedFile != null && cachedFile.delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getLastModifiedOfFile(String path) {
        if (path == null || path.length() < 1) {
            StaticMethods.logWarningFormat("Cached File - Path was null or empty for Cache File. Could not get Last Modified Date.", new Object[0]);
            return 0L;
        }
        String[] splitExtension = splitPathExtension(getPathExtension(path));
        if (splitExtension == null || splitExtension.length < 1) {
            StaticMethods.logWarningFormat("Cached File - No last modified date for file. Extension had no values after split.", new Object[0]);
            return 0L;
        }
        return Long.parseLong(splitExtension[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getEtagOfFile(String path) {
        if (path == null || path.length() < 1) {
            StaticMethods.logWarningFormat("Cached File - Path was null or empty for Cache File", new Object[0]);
            return null;
        }
        String[] splitExtension = splitPathExtension(getPathExtension(path));
        if (splitExtension == null || splitExtension.length < 2) {
            StaticMethods.logWarningFormat("Cached File - No etag for file. Extension had no second value after split.", new Object[0]);
            return null;
        }
        return splitExtension[1];
    }

    private static String getPathExtension(String path) {
        if (path != null && path.length() >= 1) {
            return path.substring(path.lastIndexOf(".") + 1);
        }
        StaticMethods.logWarningFormat("Cached File - Path was null or empty for Cache File", new Object[0]);
        return null;
    }

    private static String[] splitPathExtension(String extension) {
        if (extension == null || extension.length() < 1) {
            StaticMethods.logWarningFormat("Cached File - Extension was null or empty on Cache File", new Object[0]);
            return null;
        }
        String[] separated = extension.split("_");
        if (separated.length != 2) {
            StaticMethods.logWarningFormat("Cached File - Invalid Extension on Cache File (%s)", extension);
            return null;
        }
        return separated;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SimpleDateFormat createRFC2822Formatter() {
        SimpleDateFormat rfc2822formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        rfc2822formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return rfc2822formatter;
    }

    private static String md5hash(String input) {
        if (input == null || input.length() < 1) {
            return null;
        }
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(input.getBytes("UTF-8"));
            byte[] messageDigest = messagedigest.digest();
            StringBuilder md5HexBuilder = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String hexString = Integer.toHexString(aMessageDigest & 255);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                md5HexBuilder.append(hexString);
            }
            return md5HexBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            StaticMethods.logErrorFormat("Cached Files - Unsupported Encoding: UTF-8 (%s)", e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e2) {
            StaticMethods.logErrorFormat("Cached Files - unable to get md5 hash (%s)", e2.getMessage());
            return null;
        }
    }

    private static class DownloadFileTask implements Runnable {
        private final RemoteDownloadBlock callback;
        private final int connectionTimeout;
        private final String directory;
        private final int readTimeout;
        private final String url;

        private DownloadFileTask(String initRequest, RemoteDownloadBlock initCallback, int initConnectionTimeout, int initReadTimeout, String initDirectory) {
            this.url = initRequest;
            this.callback = initCallback;
            this.connectionTimeout = initConnectionTimeout;
            this.readTimeout = initReadTimeout;
            this.directory = initDirectory;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            if (this.url == null) {
                StaticMethods.logDebugFormat("Cached Files - url is null and cannot be cached", new Object[0]);
                if (this.callback != null) {
                    this.callback.call(false, null);
                    return;
                }
                return;
            }
            if (!RemoteDownload.stringIsUrl(this.url)) {
                StaticMethods.logDebugFormat("Cached Files - given url is not valid and cannot be cached (\"%s\")", this.url);
                if (this.callback != null) {
                    this.callback.call(false, null);
                    return;
                }
                return;
            }
            File cachefile = RemoteDownload.getFileForCachedURL(this.url, this.directory);
            SimpleDateFormat rfc2822Formatter = RemoteDownload.createRFC2822Formatter();
            HttpURLConnection connection = requestConnect(this.url);
            InputStream input = null;
            OutputStream output = null;
            if (connection == null) {
                if (this.callback != null) {
                    this.callback.call(false, null);
                    return;
                }
                return;
            }
            connection.setConnectTimeout(this.connectionTimeout);
            connection.setReadTimeout(this.readTimeout);
            if (cachefile != null) {
                String etag = StaticMethods.hexToString(RemoteDownload.getEtagOfFile(cachefile.getPath()));
                Long date = Long.valueOf(RemoteDownload.getLastModifiedOfFile(cachefile.getPath()));
                if (date.longValue() != 0) {
                    connection.setRequestProperty("If-Modified-Since", rfc2822Formatter.format(date));
                }
                if (etag != null) {
                    connection.setRequestProperty("If-None-Match", etag);
                }
            }
            try {
                try {
                    connection.connect();
                    if (connection.getResponseCode() == 304) {
                        StaticMethods.logDebugFormat("Cached Files - File has not been modified since last download. (%s)", this.url);
                        if (this.callback != null) {
                            this.callback.call(false, cachefile);
                        }
                        if (0 != 0) {
                            try {
                                output.close();
                            } catch (IOException ex) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex.getLocalizedMessage());
                                return;
                            }
                        }
                        if (0 != 0) {
                            input.close();
                        }
                        connection.disconnect();
                        return;
                    }
                    if (connection.getResponseCode() == 404) {
                        StaticMethods.logDebugFormat("Cached Files - File not found. (%s)", this.url);
                        if (this.callback != null) {
                            this.callback.call(false, cachefile);
                        }
                        if (0 != 0) {
                            try {
                                output.close();
                            } catch (IOException ex2) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex2.getLocalizedMessage());
                                return;
                            }
                        }
                        if (0 != 0) {
                            input.close();
                        }
                        connection.disconnect();
                        return;
                    }
                    if (connection.getResponseCode() != 200) {
                        StaticMethods.logWarningFormat("Cached Files - File could not be downloaded from URL (%s) Response: (%d) Message: (%s)", this.url, Integer.valueOf(connection.getResponseCode()), connection.getResponseMessage());
                        if (this.callback != null) {
                            this.callback.call(false, cachefile);
                        }
                        if (0 != 0) {
                            try {
                                output.close();
                            } catch (IOException ex3) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex3.getLocalizedMessage());
                                return;
                            }
                        }
                        if (0 != 0) {
                            input.close();
                        }
                        connection.disconnect();
                        return;
                    }
                    if (cachefile != null) {
                        RemoteDownload.deleteCachedDataForURL(this.url, this.directory);
                    }
                    Date lastModifiedDate = new Date(connection.getLastModified());
                    String newETag = connection.getHeaderField("ETag");
                    if (newETag != null) {
                        newETag = StaticMethods.getHexString(newETag);
                    }
                    File newCacheFile = RemoteDownload.getNewCachedFile(this.url, lastModifiedDate, newETag, this.directory);
                    if (newCacheFile == null) {
                        if (this.callback != null) {
                            this.callback.call(false, null);
                        }
                        if (0 != 0) {
                            try {
                                output.close();
                            } catch (IOException ex4) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex4.getLocalizedMessage());
                                return;
                            }
                        }
                        if (0 != 0) {
                            input.close();
                        }
                        connection.disconnect();
                        return;
                    }
                    input = connection.getInputStream();
                    OutputStream output2 = new FileOutputStream(newCacheFile);
                    try {
                        byte[] data = new byte[4096];
                        while (true) {
                            int count = input.read(data);
                            if (count == -1) {
                                break;
                            } else {
                                output2.write(data, 0, count);
                            }
                        }
                        StaticMethods.logDebugFormat("Cached Files - Caching successful (%s)", this.url);
                        if (this.callback != null) {
                            this.callback.call(true, newCacheFile);
                        }
                        if (output2 != null) {
                            try {
                                output2.close();
                            } catch (IOException ex5) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex5.getLocalizedMessage());
                                return;
                            }
                        }
                        if (input != null) {
                            input.close();
                        }
                        connection.disconnect();
                    } catch (Error e) {
                        e = e;
                        output = output2;
                        StaticMethods.logWarningFormat("Cached Files - Unexpected error while attempting to get remote file (%s)", e.getLocalizedMessage());
                        if (this.callback != null) {
                            this.callback.call(false, null);
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException ex6) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex6.getLocalizedMessage());
                                return;
                            }
                        }
                        if (input != null) {
                            input.close();
                        }
                        connection.disconnect();
                    } catch (SocketTimeoutException e2) {
                        output = output2;
                        StaticMethods.logWarningFormat("Cached Files - Timed out making request (%s)", this.url);
                        if (this.callback != null) {
                            this.callback.call(false, null);
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException ex7) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex7.getLocalizedMessage());
                                return;
                            }
                        }
                        if (input != null) {
                            input.close();
                        }
                        connection.disconnect();
                    } catch (IOException e3) {
                        e = e3;
                        output = output2;
                        StaticMethods.logWarningFormat("Cached Files - IOException while making request (%s)", e.getLocalizedMessage());
                        if (this.callback != null) {
                            this.callback.call(false, null);
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException ex8) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex8.getLocalizedMessage());
                                return;
                            }
                        }
                        if (input != null) {
                            input.close();
                        }
                        connection.disconnect();
                    } catch (Exception e4) {
                        e = e4;
                        output = output2;
                        StaticMethods.logWarningFormat("Cached Files - Unexpected exception while attempting to get remote file (%s)", e.getLocalizedMessage());
                        if (this.callback != null) {
                            this.callback.call(false, null);
                        }
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException ex9) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex9.getLocalizedMessage());
                                return;
                            }
                        }
                        if (input != null) {
                            input.close();
                        }
                        connection.disconnect();
                    } catch (Throwable th) {
                        th = th;
                        output = output2;
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException ex10) {
                                StaticMethods.logWarningFormat("Cached Files - Exception while attempting to close streams (%s)", ex10.getLocalizedMessage());
                                throw th;
                            }
                        }
                        if (input != null) {
                            input.close();
                        }
                        connection.disconnect();
                        throw th;
                    }
                } catch (SocketTimeoutException e5) {
                } catch (IOException e6) {
                    e = e6;
                } catch (Error e7) {
                    e = e7;
                } catch (Exception e8) {
                    e = e8;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        protected static HttpURLConnection requestConnect(String url) {
            try {
                URL requestURL = new URL(url);
                return (HttpURLConnection) requestURL.openConnection();
            } catch (Exception e) {
                StaticMethods.logErrorFormat("Cached Files - Exception opening URL(%s)", e.getLocalizedMessage());
                return null;
            }
        }
    }
}
