package com.adobe.mobile;

import android.database.DatabaseUtils;
import android.database.SQLException;
import com.adobe.mobile.AbstractDatabaseBacking;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
abstract class AbstractHitDatabase extends AbstractDatabaseBacking {
    protected String dbCreateStatement;
    protected long lastHitTimestamp;
    protected long numberOfUnsentHits;
    private TimerTask referrerTask;
    private Timer referrerTimer;
    protected boolean bgThreadActive = false;
    protected final Object backgroundMutex = new Object();
    private final Object referrerTimerMutex = new Object();

    AbstractHitDatabase() {
    }

    protected Hit selectOldestHit() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("getFirstHitInQueue must be overwritten");
    }

    protected Runnable workerThread() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("workerThread must be overwritten");
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void initializeDatabase() {
        try {
            this.database.execSQL(this.dbCreateStatement);
        } catch (SQLException x) {
            StaticMethods.logErrorFormat("%s - Unable to create database due to a sql error (%s)", this.logPrefix, x.getLocalizedMessage());
        } catch (NullPointerException x2) {
            StaticMethods.logErrorFormat("%s - Unable to create database due to an invalid path (%s)", this.logPrefix, x2.getLocalizedMessage());
        } catch (Exception x3) {
            StaticMethods.logErrorFormat("%s - Unable to create database due to an unexpected error (%s)", this.logPrefix, x3.getLocalizedMessage());
        }
    }

    protected void deleteHit(String identifier) throws AbstractDatabaseBacking.CorruptedDatabaseException {
        if (identifier == null || identifier.trim().length() == 0) {
            StaticMethods.logDebugFormat("%s - Unable to delete hit due to an invalid parameter", this.logPrefix);
            return;
        }
        synchronized (this.dbMutex) {
            try {
                try {
                    try {
                        this.database.delete("HITS", "ID = ?", new String[]{identifier});
                        this.numberOfUnsentHits--;
                    } catch (NullPointerException x) {
                        StaticMethods.logErrorFormat("%s - Unable to delete hit due to an unopened database (%s)", this.logPrefix, x.getLocalizedMessage());
                    }
                } catch (SQLException x2) {
                    StaticMethods.logErrorFormat("%s - Unable to delete hit due to a sql error (%s)", this.logPrefix, x2.getLocalizedMessage());
                    throw new AbstractDatabaseBacking.CorruptedDatabaseException("Unable to delete, database probably corrupted (" + x2.getLocalizedMessage() + ")");
                }
            } catch (Exception x3) {
                StaticMethods.logErrorFormat("%s - Unable to delete hit due to an unexpected error (%s)", this.logPrefix, x3.getLocalizedMessage());
                throw new AbstractDatabaseBacking.CorruptedDatabaseException("Unexpected exception, database probably corrupted (" + x3.getLocalizedMessage() + ")");
            }
        }
    }

    protected void clearTrackingQueue() {
        synchronized (this.dbMutex) {
            try {
                try {
                    this.database.delete("HITS", null, null);
                    this.numberOfUnsentHits = 0L;
                } catch (SQLException x) {
                    StaticMethods.logErrorFormat("%s - Unable to clear tracking queue due to a sql error (%s)", this.logPrefix, x.getLocalizedMessage());
                } catch (NullPointerException x2) {
                    StaticMethods.logErrorFormat("%s - Unable to clear tracking queue due to an unopened database (%s)", this.logPrefix, x2.getLocalizedMessage());
                }
            } catch (Exception x3) {
                StaticMethods.logErrorFormat("%s - Unable to clear tracking queue due to an unexpected error (%s)", this.logPrefix, x3.getLocalizedMessage());
            }
        }
    }

    protected void bringOnline() {
        if (!this.bgThreadActive) {
            this.bgThreadActive = true;
            synchronized (this.backgroundMutex) {
                new Thread(workerThread()).start();
            }
        }
    }

    protected void kick(boolean ignoreBatchLimit) {
        MobileConfig cfg = MobileConfig.getInstance();
        if (!ReferrerHandler.getReferrerProcessed() && cfg.getReferrerTimeout() > 0) {
            synchronized (this.referrerTimerMutex) {
                if (this.referrerTask == null) {
                    try {
                        this.referrerTask = new ReferrerTimeoutTask(ignoreBatchLimit);
                        this.referrerTimer = new Timer();
                        this.referrerTimer.schedule(this.referrerTask, MobileConfig.getInstance().getReferrerTimeout());
                    } catch (Exception e) {
                        StaticMethods.logErrorFormat("%s - Error creating referrer timer (%s)", this.logPrefix, e.getMessage());
                    }
                }
            }
            return;
        }
        if (this.referrerTimer != null) {
            synchronized (this.referrerTimerMutex) {
                try {
                    this.referrerTimer.cancel();
                } catch (Exception e2) {
                    StaticMethods.logErrorFormat("%s - Error cancelling referrer timer (%s)", this.logPrefix, e2.getMessage());
                }
                this.referrerTask = null;
            }
        }
        if (cfg.getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_IN) {
            boolean overBatchLimit = !cfg.getOfflineTrackingEnabled() || this.numberOfUnsentHits > ((long) cfg.getBatchLimit());
            if (overBatchLimit || ignoreBatchLimit) {
                bringOnline();
            }
        }
    }

    protected void forceKick() {
        kick(true);
    }

    protected long getTrackingQueueSize() {
        long numRows = 0;
        synchronized (this.dbMutex) {
            try {
                try {
                    numRows = DatabaseUtils.queryNumEntries(this.database, "HITS");
                } catch (SQLException x) {
                    StaticMethods.logErrorFormat("%s - Unable to get tracking queue size due to a sql error (%s)", this.logPrefix, x.getLocalizedMessage());
                } catch (Exception x2) {
                    StaticMethods.logErrorFormat("%s - Unable to get tracking queue size due to an unexpected error (%s)", this.logPrefix, x2.getLocalizedMessage());
                }
            } catch (NullPointerException x3) {
                StaticMethods.logErrorFormat("%s - Unable to get tracking queue size due to an unopened database (%s)", this.logPrefix, x3.getLocalizedMessage());
            }
        }
        return numRows;
    }

    @Override // com.adobe.mobile.AbstractDatabaseBacking
    protected void postReset() {
        this.numberOfUnsentHits = 0L;
    }

    protected static class Hit {
        String identifier;
        String postBody;
        String postType;
        int timeout;
        long timestamp;
        String urlFragment;

        protected Hit() {
        }
    }

    protected class ReferrerTimeoutTask extends TimerTask {
        private boolean kickFlag;

        ReferrerTimeoutTask(boolean flag) {
            this.kickFlag = false;
            this.kickFlag = flag;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            ReferrerHandler.setReferrerProcessed(true);
            StaticMethods.logDebugFormat("%s - Referrer timeout has expired without referrer data", AbstractHitDatabase.this.logPrefix);
            AbstractHitDatabase.this.kick(this.kickFlag);
        }
    }
}
