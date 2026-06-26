package com.adobe.mobile;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import com.adobe.mobile.Analytics;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackTimedAction extends AbstractDatabaseBacking {
    private static final String TIMED_ACTION_IN_APP_TIME_KEY = "a.action.time.inapp";
    private static final String TIMED_ACTION_TOTAL_TIME_KEY = "a.action.time.total";
    private static AnalyticsTrackTimedAction _instance = null;
    private static final Object _instanceMutex = new Object();
    private SQLiteStatement sqlDeleteAction;
    private SQLiteStatement sqlDeleteContextDataForAction;
    private String sqlExistsAction;
    private String sqlExistsContextDataByActionAndKey;
    private SQLiteStatement sqlInsertAction;
    private SQLiteStatement sqlInsertContextData;
    private String sqlSelectAction;
    private String sqlSelectContextDataForAction;
    private SQLiteStatement sqlUpdateAction;
    private SQLiteStatement sqlUpdateActionsClearAdjustedTime;
    private SQLiteStatement sqlUpdateContextData;

    public static AnalyticsTrackTimedAction sharedInstance() {
        AnalyticsTrackTimedAction analyticsTrackTimedAction;
        synchronized (_instanceMutex) {
            if (_instance == null) {
                _instance = new AnalyticsTrackTimedAction();
            }
            analyticsTrackTimedAction = _instance;
        }
        return analyticsTrackTimedAction;
    }

    private AnalyticsTrackTimedAction() {
        this.fileName = "ADBMobileTimedActionsCache.sqlite";
        this.logPrefix = "Analytics";
        initDatabaseBacking(new File(StaticMethods.getCacheDirectory(), this.fileName));
    }

    protected void trackTimedActionStart(String timedActionName, Map<String, Object> contextData) {
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("%s - trackTimedActionStart() failed(the required parameter actionName was null or empty.)", this.logPrefix);
            return;
        }
        long startTime = StaticMethods.getTimeSince1970();
        AnalyticsTimedAction timedAction = getTimedAction(timedActionName);
        if (timedAction != null) {
            deleteTimedAction(timedAction.databaseID);
        }
        insertTimedAction(timedActionName, contextData, startTime);
    }

    protected void trackTimedActionUpdate(String timedActionName, Map<String, Object> contextData) {
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("%s - Unable to update the timed action (timed action name was null or empty)", this.logPrefix);
        } else if (contextData == null || contextData.isEmpty()) {
            StaticMethods.logWarningFormat("%s - Unable to update the timed action (context data was null or empty)", this.logPrefix);
        } else {
            saveContextDataForTimedAction(timedActionName, contextData);
        }
    }

    protected void trackTimedActionEnd(final String timedActionName, Analytics.TimedActionBlock<Boolean> logicBlock) {
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("%s - Unable to end the timed action (timed action name was null or empty)", this.logPrefix);
            return;
        }
        AnalyticsTimedAction timedAction = getTimedAction(timedActionName);
        if (timedAction != null) {
            Long now = Long.valueOf(StaticMethods.getTimeSince1970());
            long inAppDuration = timedAction.adjustedStartTime == 0 ? 0L : now.longValue() - timedAction.adjustedStartTime;
            long totalDuration = now.longValue() - timedAction.startTime;
            final HashMap<String, Object> contextData = timedAction.contextData != null ? new HashMap<>(timedAction.contextData) : new HashMap<>();
            if (logicBlock == null || logicBlock.call(inAppDuration, totalDuration, contextData).booleanValue()) {
                contextData.put(TIMED_ACTION_TOTAL_TIME_KEY, String.valueOf(totalDuration));
                if (timedAction.adjustedStartTime != 0) {
                    contextData.put(TIMED_ACTION_IN_APP_TIME_KEY, String.valueOf(inAppDuration));
                }
                StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.AnalyticsTrackTimedAction.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnalyticsTrackAction.trackAction(timedActionName, contextData);
                    }
                });
            } else {
                StaticMethods.logDebugFormat("%s - Not sending hit for timed action due to block cancellation (%s)", this.logPrefix, timedActionName);
            }
            deleteTimedAction(timedAction.databaseID);
            return;
        }
        StaticMethods.logWarningFormat("%s - Unable to end a timed action that has not yet begun (no timed action was found matching the name %s)", this.logPrefix, timedActionName);
    }

    protected void trackTimedActionUpdateAdjustedStartTime(long timeDelta) {
        synchronized (this.dbMutex) {
            try {
                this.sqlUpdateAction.bindLong(1, timeDelta);
                this.sqlUpdateAction.execute();
                this.sqlUpdateAction.clearBindings();
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("%s - Unable to bind prepared statement values for updating the adjusted start time for timed action (%s)", this.logPrefix, e.getLocalizedMessage());
                resetDatabase(e);
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("%s - Unable to adjust start times for timed actions (%s)", this.logPrefix, e2.getMessage());
            }
        }
    }

    protected void trackTimedActionUpdateActionsClearAdjustedStartTime() {
        synchronized (this.dbMutex) {
            try {
                this.sqlUpdateActionsClearAdjustedTime.execute();
                this.sqlUpdateActionsClearAdjustedTime.clearBindings();
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("%s - Unable to update adjusted time for timed actions after crash (%s)", this.logPrefix, e.getMessage());
                resetDatabase(e);
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("%s - Unknown error clearing adjusted start times for timed actions (%s)", this.logPrefix, e2.getMessage());
                resetDatabase(e2);
            }
        }
    }

    protected boolean trackTimedActionExists(String timedActionName) {
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("%s - Unable to verify the existence of timed action (timed action name was null or empty)", this.logPrefix);
            return false;
        }
        boolean timedActionExists = false;
        synchronized (this.dbMutex) {
            if (this.database == null) {
                return false;
            }
            try {
                try {
                    Cursor cursor = this.database.rawQuery(this.sqlExistsAction, new String[]{timedActionName});
                    if (cursor.moveToFirst()) {
                        timedActionExists = cursor.getInt(0) > 0;
                    }
                    cursor.close();
                } catch (Exception e) {
                    StaticMethods.logErrorFormat("%s - Unknown error checking for timed action (%s)", this.logPrefix, e.getMessage());
                }
            } catch (SQLException e2) {
                StaticMethods.logErrorFormat("%s - Unable to query timed actions database (%s)", this.logPrefix, e2.getMessage());
            }
            return timedActionExists;
        }
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void preMigrate() {
        File oldFile = new File(StaticMethods.getCacheDirectory() + "ADBMobileDataCache.sqlite" + this.fileName);
        File newFile = new File(StaticMethods.getCacheDirectory(), this.fileName);
        if (oldFile.exists() && !newFile.exists()) {
            try {
                if (!oldFile.renameTo(newFile)) {
                    StaticMethods.logWarningFormat("%s - Unable to migrate old Timed Actions db, creating new Timed Actions db (move file returned false)", this.logPrefix);
                }
            } catch (Exception e) {
                StaticMethods.logWarningFormat("%s - Unable to migrate old Timed Actions db, creating new Timed Actions db (%s)", this.logPrefix, e.getLocalizedMessage());
            }
        }
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void postMigrate() {
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void postReset() {
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void initializeDatabase() {
        try {
            this.database.execSQL("CREATE TABLE IF NOT EXISTS TIMEDACTIONS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, STARTTIME INTEGER, ADJSTARTTIME INTEGER)");
            this.database.execSQL("CREATE TABLE IF NOT EXISTS CONTEXTDATA (ID INTEGER PRIMARY KEY AUTOINCREMENT, ACTIONID INTEGER, KEY TEXT, VALUE TEXT, FOREIGN KEY(ACTIONID) REFERENCES TIMEDACTIONS(ID))");
        } catch (SQLException e) {
            StaticMethods.logErrorFormat("%s - Unable to open or create timed actions database (%s)", this.logPrefix, e.getMessage());
        } catch (Exception e2) {
            StaticMethods.logErrorFormat("%s - Uknown error creating timed actions database (%s)", this.logPrefix, e2.getMessage());
        }
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void prepareStatements() {
        this.sqlSelectAction = "SELECT ID, STARTTIME, ADJSTARTTIME FROM TIMEDACTIONS WHERE NAME=?";
        this.sqlExistsAction = "SELECT COUNT(*) FROM TIMEDACTIONS WHERE NAME=?";
        this.sqlSelectContextDataForAction = "SELECT KEY, VALUE FROM CONTEXTDATA WHERE ACTIONID=?";
        this.sqlExistsContextDataByActionAndKey = "SELECT COUNT(*) FROM CONTEXTDATA WHERE ACTIONID=? AND KEY=?";
        try {
            this.sqlInsertAction = this.database.compileStatement("INSERT INTO TIMEDACTIONS (NAME, STARTTIME, ADJSTARTTIME) VALUES (@NAME, @START, @START)");
            this.sqlUpdateAction = this.database.compileStatement("UPDATE TIMEDACTIONS SET ADJSTARTTIME=ADJSTARTTIME+@DELTA WHERE ADJSTARTTIME!=0");
            this.sqlUpdateActionsClearAdjustedTime = this.database.compileStatement("UPDATE TIMEDACTIONS SET ADJSTARTTIME=0");
            this.sqlDeleteAction = this.database.compileStatement("DELETE FROM TIMEDACTIONS WHERE ID=@ID");
            this.sqlInsertContextData = this.database.compileStatement("INSERT INTO CONTEXTDATA(ACTIONID, KEY, VALUE) VALUES (@ACTIONID, @KEY, @VALUE)");
            this.sqlUpdateContextData = this.database.compileStatement("UPDATE CONTEXTDATA SET VALUE=@VALUE WHERE ACTIONID=@ACTIONID AND KEY=@KEY");
            this.sqlDeleteContextDataForAction = this.database.compileStatement("DELETE FROM CONTEXTDATA WHERE ACTIONID=@ACTIONID");
        } catch (SQLException e) {
            StaticMethods.logErrorFormat("Analytics - unable to prepare the needed SQL statements for interacting with the timed actions database (%s)", e.getMessage());
        } catch (Exception e2) {
            StaticMethods.logErrorFormat("Analytics - Unknown error preparing sql statements (%s)", e2.getMessage());
        }
    }

    private void insertTimedAction(String timedActionName, Map<String, Object> contextData, long startTime) {
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("Analytics - Unable to insert timed action (timed action name was null or empty)", new Object[0]);
            return;
        }
        synchronized (this.dbMutex) {
            try {
                this.sqlInsertAction.bindString(1, timedActionName);
                this.sqlInsertAction.bindLong(2, startTime);
                if (this.sqlInsertAction.executeInsert() == -1) {
                    StaticMethods.logWarningFormat("Analytics - Unable to insert the timed action (%s)", timedActionName);
                }
                this.sqlInsertAction.clearBindings();
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("Analytics - Unable to bind prepared statement values for inserting the timed action (%s)", timedActionName);
                resetDatabase(e);
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("Analyitcs - Unknown error when inserting timed action (%s)", e2.getMessage());
            }
        }
        saveContextDataForTimedAction(timedActionName, contextData);
    }

    private void saveContextDataForTimedAction(String timedActionName, Map<String, Object> contextData) {
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("Analytics - Unable to save context data (timed action name was null or empty)", new Object[0]);
            return;
        }
        if (contextData == null || contextData.isEmpty()) {
            StaticMethods.logWarningFormat("Analytics - Unable to save context data (context data was null or empty)", new Object[0]);
            return;
        }
        synchronized (this.dbMutex) {
            try {
            } catch (SQLException e) {
                StaticMethods.logErrorFormat("Analytics - SQL exception when attempting to update context data for timed action (%s)", e.getMessage());
            } catch (Exception e2) {
                StaticMethods.logErrorFormat("Analytics - Unexpected exception when attempting to update context data for timed action (%s)", e2.getMessage());
            }
            if (this.database == null) {
                StaticMethods.logErrorFormat("Analytics - Null Database Object, unable to save context data for timed action", new Object[0]);
                return;
            }
            Cursor cursor = this.database.rawQuery(this.sqlSelectAction, new String[]{timedActionName});
            if (cursor.moveToFirst()) {
                int timedActionID = cursor.getInt(0);
                cursor.close();
                for (Map.Entry<String, Object> entry : contextData.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null && key.length() > 0) {
                        Cursor cursor2 = this.database.rawQuery(this.sqlExistsContextDataByActionAndKey, new String[]{String.valueOf(timedActionID), entry.getKey()});
                        String cDataValue = value == null ? "" : value.toString();
                        if (cursor2.moveToFirst() && cursor2.getInt(0) > 0) {
                            this.sqlUpdateContextData.bindString(1, cDataValue);
                            this.sqlUpdateContextData.bindLong(2, timedActionID);
                            this.sqlUpdateContextData.bindString(3, entry.getKey());
                            this.sqlUpdateContextData.execute();
                            this.sqlUpdateContextData.clearBindings();
                        } else {
                            this.sqlInsertContextData.bindLong(1, timedActionID);
                            this.sqlInsertContextData.bindString(2, entry.getKey());
                            this.sqlInsertContextData.bindString(3, cDataValue);
                            if (this.sqlInsertContextData.executeInsert() == -1) {
                                StaticMethods.logWarningFormat("Analytics - Unable to insert the timed action's context data (%s)", timedActionName);
                            }
                            this.sqlInsertContextData.clearBindings();
                        }
                        cursor2.close();
                    }
                }
                return;
            }
            cursor.close();
            StaticMethods.logWarningFormat("Analytics - Unable to save context data (no timed action was found matching the name %s)", timedActionName);
        }
    }

    private AnalyticsTimedAction getTimedAction(String timedActionName) {
        AnalyticsTimedAction timedAction = null;
        if (timedActionName == null || timedActionName.trim().length() == 0) {
            StaticMethods.logWarningFormat("Analytics - Unable to get timed action (timed action name was null or empty)", new Object[0]);
        } else {
            synchronized (this.dbMutex) {
                try {
                    if (this.database != null) {
                        try {
                            Cursor actionCursor = this.database.rawQuery(this.sqlSelectAction, new String[]{timedActionName});
                            if (!actionCursor.moveToFirst()) {
                                timedAction = null;
                            } else {
                                timedAction = new AnalyticsTimedAction(null, actionCursor.getLong(1), actionCursor.getLong(2), actionCursor.getInt(0));
                                try {
                                    Cursor contextDataCursor = this.database.rawQuery(this.sqlSelectContextDataForAction, new String[]{String.valueOf(timedAction.databaseID)});
                                    if (contextDataCursor.moveToFirst()) {
                                        timedAction.contextData = new HashMap();
                                        do {
                                            timedAction.contextData.put(contextDataCursor.getString(0), contextDataCursor.getString(1));
                                        } while (contextDataCursor.moveToNext());
                                    }
                                    contextDataCursor.close();
                                } catch (SQLException e) {
                                    e = e;
                                    StaticMethods.logErrorFormat("Analytics - Unable to read from timed actions database (%s)", e.getMessage());
                                    resetDatabase(e);
                                } catch (Exception e2) {
                                    e = e2;
                                    StaticMethods.logErrorFormat("Analytics - Unknown error reading from timed actions database (%s)", e.getMessage());
                                }
                            }
                            actionCursor.close();
                        } catch (SQLException e3) {
                            e = e3;
                            timedAction = null;
                        } catch (Exception e4) {
                            e = e4;
                            timedAction = null;
                        }
                    }
                }
            }
        }
        return timedAction;
    }

    private void deleteTimedAction(int timedActionID) {
        synchronized (this.dbMutex) {
            try {
                try {
                    this.sqlDeleteContextDataForAction.bindLong(1, timedActionID);
                    this.sqlDeleteContextDataForAction.execute();
                    this.sqlDeleteAction.bindLong(1, timedActionID);
                    this.sqlDeleteAction.execute();
                    this.sqlDeleteContextDataForAction.clearBindings();
                    this.sqlDeleteAction.clearBindings();
                } catch (Exception e) {
                    StaticMethods.logErrorFormat("Analytics - Unknown error deleting timed action (%s)", e.getMessage());
                }
            } catch (SQLException e2) {
                StaticMethods.logErrorFormat("Analytics - Unable to delete the timed action (ID = %d, Exception: %s)", Integer.valueOf(timedActionID), e2.getMessage());
                resetDatabase(e2);
            }
        }
    }
}
