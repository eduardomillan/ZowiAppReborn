package com.adobe.mobile;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
abstract class AbstractDatabaseBacking {
    protected SQLiteDatabase database;
    protected String fileName;
    protected String logPrefix;
    private File _dbFile = null;
    protected final Object dbMutex = new Object();

    AbstractDatabaseBacking() {
    }

    protected void initializeDatabase() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("initializeDatabase must be overwritten");
    }

    protected void prepareStatements() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("prepareStatements must be overwritten");
    }

    protected void postReset() {
    }

    protected void preMigrate() {
    }

    protected void postMigrate() {
    }

    private void openOrCreateDatabase() {
        try {
            this.database = SQLiteDatabase.openDatabase(this._dbFile.getPath(), null, 268435472);
        } catch (SQLException x) {
            StaticMethods.logErrorFormat("%s - Unable to open database (%s).", this.logPrefix, x.getLocalizedMessage());
        }
    }

    protected void initDatabaseBacking(File databaseFile) {
        this._dbFile = databaseFile;
        synchronized (this.dbMutex) {
            preMigrate();
            openOrCreateDatabase();
            if (this.database != null) {
                postMigrate();
                initializeDatabase();
                prepareStatements();
            }
        }
    }

    protected final void resetDatabase(Exception reason) {
        StaticMethods.logErrorFormat("%s - Database in unrecoverable state (%s), resetting.", this.logPrefix, reason.getLocalizedMessage());
        synchronized (this.dbMutex) {
            if (!this._dbFile.delete()) {
                StaticMethods.logDebugFormat("%s - Database file(%s) was not found.", this.logPrefix, this._dbFile.getAbsolutePath());
            } else {
                StaticMethods.logDebugFormat("%s - Database file(%s) was corrupt and had to be deleted.", this.logPrefix, this._dbFile.getAbsolutePath());
            }
            openOrCreateDatabase();
            initializeDatabase();
            prepareStatements();
            postReset();
        }
    }

    protected static class CorruptedDatabaseException extends Exception {
        public CorruptedDatabaseException(String message) {
            super(message);
        }
    }
}
