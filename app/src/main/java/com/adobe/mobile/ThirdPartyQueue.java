package com.adobe.mobile;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.os.Process;
import com.adobe.mobile.AbstractDatabaseBacking;
import com.adobe.mobile.AbstractHitDatabase;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class ThirdPartyQueue extends AbstractHitDatabase {
    protected static final String THIRDPARTY_DB_CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS HITS (ID INTEGER PRIMARY KEY AUTOINCREMENT, URL TEXT, POSTBODY TEXT, POSTTYPE TEXT, TIMESTAMP INTEGER, TIMEOUT INTEGER)";
    protected static final String THIRDPARTY_FILENAME = "ADBMobile3rdPartyDataCache.sqlite";
    protected static final String THIRDPARTY_LOG_PREFIX = "External Callback";
    private static final int THIRDPARTY_TIMEOUT_COOLDOWN_TIMER = 30;
    private static final int THIRDPARTY_TIMESTAMP_DISABLED_WAIT_THRESHOLD = 60;
    private static final String _hitsNumberOfRowsToReturn = "1";
    private static final String _hitsOrderBy = "ID ASC";
    private static final String _hitsTableName = "HITS";
    private SQLiteStatement _preparedInsertStatement = null;
    private static final String[] _hitsSelectedColumns = {"ID", "URL", "POSTBODY", "POSTTYPE", "TIMESTAMP", "TIMEOUT"};
    private static ThirdPartyQueue _instance = null;
    private static final Object _instanceMutex = new Object();

    protected static ThirdPartyQueue sharedInstance() {
        ThirdPartyQueue thirdPartyQueue;
        synchronized (_instanceMutex) {
            if (_instance == null) {
                _instance = new ThirdPartyQueue();
            }
            thirdPartyQueue = _instance;
        }
        return thirdPartyQueue;
    }

    private ThirdPartyQueue() {
        this.fileName = THIRDPARTY_FILENAME;
        this.logPrefix = THIRDPARTY_LOG_PREFIX;
        this.dbCreateStatement = THIRDPARTY_DB_CREATE_STATEMENT;
        this.lastHitTimestamp = 0L;
        initDatabaseBacking(new File(StaticMethods.getCacheDirectory(), this.fileName));
        this.numberOfUnsentHits = getTrackingQueueSize();
    }

    protected void queue(String url, String postBody, String type, long timestamp, long timeout) {
        MobileConfig mobileConfigInstance = MobileConfig.getInstance();
        if (mobileConfigInstance == null) {
            StaticMethods.logErrorFormat("%s - Cannot send hit, MobileConfig is null (this really shouldn't happen)", this.logPrefix);
            return;
        }
        if (mobileConfigInstance.getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_OUT) {
            StaticMethods.logDebugFormat("%s - Ignoring hit due to privacy status being opted out", this.logPrefix);
            return;
        }
        synchronized (this.dbMutex) {
            try {
                this._preparedInsertStatement.bindString(1, url);
                if (postBody != null && postBody.length() > 0) {
                    this._preparedInsertStatement.bindString(2, postBody);
                } else {
                    this._preparedInsertStatement.bindNull(2);
                }
                if (type != null && type.length() > 0) {
                    this._preparedInsertStatement.bindString(3, type);
                } else {
                    this._preparedInsertStatement.bindNull(3);
                }
                this._preparedInsertStatement.bindLong(4, timestamp);
                this._preparedInsertStatement.bindLong(5, timeout);
                this._preparedInsertStatement.execute();
                this.numberOfUnsentHits++;
                this._preparedInsertStatement.clearBindings();
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("%s - Unable to insert url (%s)", this.logPrefix, url);
                resetDatabase(e);
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("%s - Unknown error while inserting url (%s)", this.logPrefix, url);
                resetDatabase(e2);
            }
        }
        kick(false);
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void prepareStatements() {
        try {
            this._preparedInsertStatement = this.database.compileStatement("INSERT INTO HITS (URL, POSTBODY, POSTTYPE, TIMESTAMP, TIMEOUT) VALUES (?, ?, ?, ?, ?)");
        } catch (SQLException x) {
            StaticMethods.logErrorFormat("%s - Unable to create database due to a sql error (%s)", this.logPrefix, x.getLocalizedMessage());
        } catch (NullPointerException x2) {
            StaticMethods.logErrorFormat("%s - Unable to create database due to an invalid path (%s)", this.logPrefix, x2.getLocalizedMessage());
        } catch (Exception x3) {
            StaticMethods.logErrorFormat("%s - Unable to create database due to an unexpected error (%s)", this.logPrefix, x3.getLocalizedMessage());
        }
    }

    @Override // com.adobe.mobile.AbstractHitDatabase
    protected AbstractHitDatabase.Hit selectOldestHit() {
        AbstractHitDatabase.Hit hit = null;
        synchronized (this.dbMutex) {
            Cursor cursor = null;
            try {
                try {
                    cursor = this.database.query(_hitsTableName, _hitsSelectedColumns, null, null, null, null, _hitsOrderBy, "1");
                    if (cursor.moveToFirst()) {
                        AbstractHitDatabase.Hit hit2 = new AbstractHitDatabase.Hit();
                        try {
                            hit2.identifier = cursor.getString(0);
                            hit2.urlFragment = cursor.getString(1);
                            hit2.postBody = cursor.getString(2);
                            hit2.postType = cursor.getString(3);
                            hit2.timestamp = cursor.getLong(4);
                            hit2.timeout = cursor.getInt(5);
                            hit = hit2;
                        } catch (SQLException e) {
                            e = e;
                            hit = hit2;
                            StaticMethods.logErrorFormat("%s - Unable to read from database (%s)", this.logPrefix, e.getMessage());
                            if (cursor != null) {
                                cursor.close();
                            }
                        } catch (Exception e2) {
                            e = e2;
                            hit = hit2;
                            StaticMethods.logErrorFormat("%s - Unknown error reading from database (%s)", this.logPrefix, e.getMessage());
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
        return new Runnable() { // from class: com.adobe.mobile.ThirdPartyQueue.1
            @Override // java.lang.Runnable
            public void run() {
                AbstractHitDatabase.Hit hit;
                ThirdPartyQueue worker = ThirdPartyQueue.sharedInstance();
                Process.setThreadPriority(10);
                boolean offlineEnabled = MobileConfig.getInstance().getOfflineTrackingEnabled();
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept-Language", StaticMethods.getDefaultAcceptLanguage());
                headers.put("User-Agent", StaticMethods.getDefaultUserAgent());
                while (MobileConfig.getInstance().getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_IN && MobileConfig.getInstance().networkConnectivity() && (hit = worker.selectOldestHit()) != null && hit.urlFragment != null) {
                    if (!offlineEnabled && hit.timestamp < StaticMethods.getTimeSince1970() - 60) {
                        try {
                            worker.deleteHit(hit.identifier);
                        } catch (AbstractDatabaseBacking.CorruptedDatabaseException ex) {
                            worker.resetDatabase(ex);
                        }
                    } else {
                        hit.postBody = hit.postBody != null ? hit.postBody : "";
                        hit.postType = hit.postType != null ? hit.postType : "";
                        hit.timeout = hit.timeout < 2 ? 2000 : hit.timeout * 1000;
                        if (RequestHandler.sendThirdPartyRequest(hit.urlFragment, hit.postBody, headers, hit.timeout, hit.postType, ThirdPartyQueue.this.logPrefix)) {
                            try {
                                worker.deleteHit(hit.identifier);
                                worker.lastHitTimestamp = hit.timestamp;
                            } catch (AbstractDatabaseBacking.CorruptedDatabaseException ex2) {
                                worker.resetDatabase(ex2);
                            }
                        } else {
                            StaticMethods.logWarningFormat("%s - Unable to forward hit (%s)", ThirdPartyQueue.this.logPrefix, hit.urlFragment);
                            if (MobileConfig.getInstance().getOfflineTrackingEnabled()) {
                                StaticMethods.logDebugFormat("%s - Network error, imposing internal cooldown (%d seconds)", ThirdPartyQueue.this.logPrefix, 30L);
                                for (int i = 0; i < 30; i++) {
                                    try {
                                        if (MobileConfig.getInstance().networkConnectivity()) {
                                            Thread.sleep(1000L);
                                        }
                                    } catch (Exception e) {
                                        StaticMethods.logWarningFormat("%s - Background Thread Interrupted (%s)", ThirdPartyQueue.this.logPrefix, e.getMessage());
                                    }
                                }
                            } else {
                                try {
                                    worker.deleteHit(hit.identifier);
                                } catch (AbstractDatabaseBacking.CorruptedDatabaseException ex3) {
                                    worker.resetDatabase(ex3);
                                }
                            }
                        }
                    }
                }
                worker.bgThreadActive = false;
            }
        };
    }
}
