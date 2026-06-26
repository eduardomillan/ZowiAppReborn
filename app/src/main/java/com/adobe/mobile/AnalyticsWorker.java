package com.adobe.mobile;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.os.Process;
import com.adobe.mobile.AbstractDatabaseBacking;
import com.adobe.mobile.AbstractHitDatabase;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsWorker extends AbstractHitDatabase {
    protected static final String ANALYTICS_DB_CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS HITS (ID INTEGER PRIMARY KEY AUTOINCREMENT, URL TEXT, TIMESTAMP INTEGER)";
    protected static final String ANALYTICS_FILENAME = "ADBMobileDataCache.sqlite";
    private static final int CONNECTION_TIMEOUT_MSEC = 5000;
    private static final int TIMESTAMP_DISABLED_WAIT_THRESHOLD = 60;
    private static String baseURL;
    protected SQLiteStatement _preparedInsertStatement = null;
    private static final SecureRandom randomGen = new SecureRandom();
    private static AnalyticsWorker _instance = null;
    private static final Object _instanceMutex = new Object();
    private static volatile boolean analyticsGetBaseURL_pred = true;

    public static AnalyticsWorker sharedInstance() {
        AnalyticsWorker analyticsWorker;
        synchronized (_instanceMutex) {
            if (_instance == null) {
                _instance = new AnalyticsWorker();
            }
            analyticsWorker = _instance;
        }
        return analyticsWorker;
    }

    protected AnalyticsWorker() {
        this.fileName = ANALYTICS_FILENAME;
        this.logPrefix = "Analytics";
        this.dbCreateStatement = ANALYTICS_DB_CREATE_STATEMENT;
        this.lastHitTimestamp = 0L;
        initDatabaseBacking(new File(StaticMethods.getCacheDirectory(), this.fileName));
        this.numberOfUnsentHits = getTrackingQueueSize();
    }

    protected void queue(String url, long timeStamp) {
        MobileConfig mobileConfigInstance = MobileConfig.getInstance();
        if (mobileConfigInstance == null) {
            StaticMethods.logErrorFormat("Analytics - Cannot send hit, MobileConfig is null (this really shouldn't happen)", new Object[0]);
            return;
        }
        if (mobileConfigInstance.getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_OUT) {
            StaticMethods.logDebugFormat("Analytics - Ignoring hit due to privacy status being opted out", new Object[0]);
            return;
        }
        synchronized (this.dbMutex) {
            try {
                this._preparedInsertStatement.bindString(1, url);
                this._preparedInsertStatement.bindLong(2, timeStamp);
                this._preparedInsertStatement.execute();
                StaticMethods.updateLastKnownTimestamp(Long.valueOf(timeStamp));
                this.numberOfUnsentHits++;
                this._preparedInsertStatement.clearBindings();
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("Analytics - Unable to insert url (%s)", url);
                resetDatabase(e);
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("Analytics - Unknown error while inserting url (%s)", url);
                resetDatabase(e2);
            }
        }
        kick(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getBaseURL() {
        if (analyticsGetBaseURL_pred) {
            analyticsGetBaseURL_pred = false;
            baseURL = (MobileConfig.getInstance().getSSL() ? "https://" : "http://") + MobileConfig.getInstance().getTrackingServer() + "/b/ss/" + StaticMethods.URLEncode(MobileConfig.getInstance().getReportSuiteIds()) + "/" + MobileConfig.getInstance().getAnalyticsResponseType() + "/JAVA-4.8.1-AN/s";
            StaticMethods.logDebugFormat("Analytics - Setting base request URL(%s)", baseURL);
        }
        return baseURL;
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void preMigrate() {
        File oldFile = new File(StaticMethods.getCacheDirectory() + this.fileName);
        File newFile = new File(StaticMethods.getCacheDirectory(), this.fileName);
        if (oldFile.exists() && !newFile.exists()) {
            try {
                if (!oldFile.renameTo(newFile)) {
                    StaticMethods.logWarningFormat("Analytics - Unable to migrate old hits db, creating new hits db (move file returned false)", new Object[0]);
                }
            } catch (Exception e) {
                StaticMethods.logWarningFormat("Analytics - Unable to migrate old hits db, creating new hits db (%s)", e.getLocalizedMessage());
            }
        }
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void prepareStatements() {
        try {
            this._preparedInsertStatement = this.database.compileStatement("INSERT INTO HITS (URL, TIMESTAMP) VALUES (?, ?)");
        } catch (SQLException x) {
            StaticMethods.logErrorFormat("Analytics - Unable to create database due to a sql error (%s)", x.getLocalizedMessage());
        } catch (NullPointerException x2) {
            StaticMethods.logErrorFormat("Analytics - Unable to create database due to an invalid path (%s)", x2.getLocalizedMessage());
        } catch (Exception x3) {
            StaticMethods.logErrorFormat("Analytics - Unable to create database due to an unexpected error (%s)", x3.getLocalizedMessage());
        }
    }

    @Override // com.adobe.mobile.AbstractHitDatabase
    protected AbstractHitDatabase.Hit selectOldestHit() {
        AbstractHitDatabase.Hit hit = null;
        synchronized (this.dbMutex) {
            Cursor cursor = null;
            try {
                try {
                    cursor = this.database.query("HITS", new String[]{"ID", "URL", "TIMESTAMP"}, null, null, null, null, "ID ASC", "1");
                    if (cursor.moveToFirst()) {
                        AbstractHitDatabase.Hit hit2 = new AbstractHitDatabase.Hit();
                        try {
                            hit2.identifier = cursor.getString(0);
                            hit2.urlFragment = cursor.getString(1);
                            hit2.timestamp = cursor.getLong(2);
                            hit = hit2;
                        } catch (SQLException e) {
                            e = e;
                            hit = hit2;
                            StaticMethods.logErrorFormat("Analytics - Unable to read from database (%s)", e.getMessage());
                            if (cursor != null) {
                                cursor.close();
                            }
                        } catch (Exception e2) {
                            e = e2;
                            hit = hit2;
                            StaticMethods.logErrorFormat("Analytics - Unknown error reading from database (%s)", e.getMessage());
                            if (cursor != null) {
                                cursor.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (SQLException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return hit;
    }

    @Override // com.adobe.mobile.AbstractHitDatabase
    protected final Runnable workerThread() {
        return new Runnable() { // from class: com.adobe.mobile.AnalyticsWorker.1
            @Override // java.lang.Runnable
            public void run() {
                AbstractHitDatabase.Hit hit;
                AnalyticsWorker worker = AnalyticsWorker.sharedInstance();
                Process.setThreadPriority(10);
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept-Language", StaticMethods.getDefaultAcceptLanguage());
                headers.put("User-Agent", StaticMethods.getDefaultUserAgent());
                while (MobileConfig.getInstance().getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_IN && MobileConfig.getInstance().networkConnectivity() && (hit = worker.selectOldestHit()) != null) {
                    if (MobileConfig.getInstance().getOfflineTrackingEnabled()) {
                        long delta = hit.timestamp - worker.lastHitTimestamp;
                        if (delta < 0) {
                            long newTimestamp = worker.lastHitTimestamp + 1;
                            String oldTSString = "&ts=" + Long.toString(hit.timestamp);
                            String newTSString = "&ts=" + Long.toString(newTimestamp);
                            hit.urlFragment = hit.urlFragment.replaceFirst(oldTSString, newTSString);
                            StaticMethods.logDebugFormat("Analytics - Adjusting out of order hit timestamp(%d->%d)", Long.valueOf(hit.timestamp), Long.valueOf(newTimestamp));
                            hit.timestamp = newTimestamp;
                        }
                    } else if (hit.timestamp < StaticMethods.getTimeSince1970() - 60) {
                        try {
                            worker.deleteHit(hit.identifier);
                        } catch (AbstractDatabaseBacking.CorruptedDatabaseException ex) {
                            AnalyticsWorker.sharedInstance().resetDatabase(ex);
                        }
                    }
                    String postBody = hit.urlFragment.startsWith("ndh") ? hit.urlFragment : hit.urlFragment.substring(hit.urlFragment.indexOf(63) + 1);
                    byte[] response = RequestHandler.retrieveAnalyticsRequestData(AnalyticsWorker.getBaseURL() + AnalyticsWorker.randomGen.nextInt(100000000), postBody, headers, AnalyticsWorker.CONNECTION_TIMEOUT_MSEC, AnalyticsWorker.this.logPrefix);
                    if (response != null) {
                        if (response.length > 1) {
                            try {
                                worker.deleteHit(hit.identifier);
                                worker.lastHitTimestamp = hit.timestamp;
                                String responseString = new String(response, "UTF-8");
                                final JSONObject jsonResponse = new JSONObject(responseString);
                                StaticMethods.getAudienceExecutor().execute(new Runnable() { // from class: com.adobe.mobile.AnalyticsWorker.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        AudienceManagerWorker.processJsonResponse(jsonResponse);
                                    }
                                });
                            } catch (AbstractDatabaseBacking.CorruptedDatabaseException ex2) {
                                AnalyticsWorker.sharedInstance().resetDatabase(ex2);
                            } catch (UnsupportedEncodingException e) {
                                StaticMethods.logWarningFormat("Audience Manager - Unable to decode server response (%s)", e.getLocalizedMessage());
                            } catch (JSONException e2) {
                                StaticMethods.logWarningFormat("Audience Manager - Unable to parse JSON data (%s)", e2.getLocalizedMessage());
                            }
                        } else {
                            try {
                                worker.deleteHit(hit.identifier);
                                worker.lastHitTimestamp = hit.timestamp;
                            } catch (AbstractDatabaseBacking.CorruptedDatabaseException ex3) {
                                AnalyticsWorker.sharedInstance().resetDatabase(ex3);
                            }
                        }
                    } else {
                        for (int i = 0; i < 30; i++) {
                            try {
                                if (MobileConfig.getInstance().networkConnectivity()) {
                                    Thread.sleep(1000L);
                                }
                            } catch (Exception e3) {
                                StaticMethods.logWarningFormat("Analytics - Background Thread Interrupted(%s)", e3.getMessage());
                            }
                        }
                    }
                }
                worker.bgThreadActive = false;
            }
        };
    }

    protected void kickWithReferrerData(Map<String, Object> referrerData) {
        if (referrerData == null || referrerData.size() <= 0) {
            ReferrerHandler.setReferrerProcessed(true);
            kick(false);
            return;
        }
        AbstractHitDatabase.Hit hit = selectOldestHit();
        if (hit != null && hit.urlFragment != null) {
            hit.urlFragment = StaticMethods.appendContextData(referrerData, hit.urlFragment);
            updateHitInDatabase(hit);
            ReferrerHandler.setReferrerProcessed(true);
        }
        kick(false);
    }

    protected void updateHitInDatabase(AbstractHitDatabase.Hit hit) {
        synchronized (this.dbMutex) {
            try {
                ContentValues updatedURLFragment = new ContentValues();
                updatedURLFragment.put("URL", hit.urlFragment);
                this.database.update("HITS", updatedURLFragment, "id=" + hit.identifier, null);
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("Analytics - Unable to update url in database (%s)", e.getMessage());
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("Analytics - Unknown error updating url in database (%s)", e2.getMessage());
            }
        }
    }
}
