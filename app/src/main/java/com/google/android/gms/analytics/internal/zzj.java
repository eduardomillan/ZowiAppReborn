package com.google.android.gms.analytics.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.comscore.measurement.MeasurementDispatcher;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/* JADX INFO: loaded from: classes.dex */
class zzj extends zzd implements Closeable {
    private static final String zzNm = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzNn = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zza zzNo;
    private final zzaj zzNp;
    private final zzaj zzNq;

    class zza extends SQLiteOpenHelper {
        zza(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        }

        private void zza(SQLiteDatabase sQLiteDatabase) {
            Set<String> setZzb = zzb(sQLiteDatabase, "hits2");
            for (String str : new String[]{"hit_id", "hit_string", "hit_time", "hit_url"}) {
                if (!setZzb.remove(str)) {
                    throw new SQLiteException("Database hits2 is missing required column: " + str);
                }
            }
            boolean z = setZzb.remove("hit_app_id") ? false : true;
            if (!setZzb.isEmpty()) {
                throw new SQLiteException("Database hits2 has extra columns");
            }
            if (z) {
                sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER");
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean zza(android.database.sqlite.SQLiteDatabase r11, java.lang.String r12) throws java.lang.Throwable {
            /*
                r10 = this;
                r8 = 0
                r9 = 0
                java.lang.String r1 = "SQLITE_MASTER"
                r0 = 1
                java.lang.String[] r2 = new java.lang.String[r0]     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L36
                r0 = 0
                java.lang.String r3 = "name"
                r2[r0] = r3     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L36
                java.lang.String r3 = "name=?"
                r0 = 1
                java.lang.String[] r4 = new java.lang.String[r0]     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L36
                r0 = 0
                r4[r0] = r12     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L36
                r5 = 0
                r6 = 0
                r7 = 0
                r0 = r11
                android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch: android.database.sqlite.SQLiteException -> L26 java.lang.Throwable -> L36
                boolean r0 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L3d android.database.sqlite.SQLiteException -> L40
                if (r1 == 0) goto L25
                r1.close()
            L25:
                return r0
            L26:
                r0 = move-exception
                r1 = r9
            L28:
                com.google.android.gms.analytics.internal.zzj r2 = com.google.android.gms.analytics.internal.zzj.this     // Catch: java.lang.Throwable -> L3d
                java.lang.String r3 = "Error querying for table"
                r2.zzc(r3, r12, r0)     // Catch: java.lang.Throwable -> L3d
                if (r1 == 0) goto L34
                r1.close()
            L34:
                r0 = r8
                goto L25
            L36:
                r0 = move-exception
            L37:
                if (r9 == 0) goto L3c
                r9.close()
            L3c:
                throw r0
            L3d:
                r0 = move-exception
                r9 = r1
                goto L37
            L40:
                r0 = move-exception
                goto L28
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zza.zza(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
        }

        private Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
            HashSet hashSet = new HashSet();
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 0", null);
            try {
                for (String str2 : cursorRawQuery.getColumnNames()) {
                    hashSet.add(str2);
                }
                return hashSet;
            } finally {
                cursorRawQuery.close();
            }
        }

        private void zzb(SQLiteDatabase sQLiteDatabase) {
            Set<String> setZzb = zzb(sQLiteDatabase, "properties");
            for (String str : new String[]{"app_uid", "cid", "tid", "params", "adid", "hits_count"}) {
                if (!setZzb.remove(str)) {
                    throw new SQLiteException("Database properties is missing required column: " + str);
                }
            }
            if (!setZzb.isEmpty()) {
                throw new SQLiteException("Database properties table has extra columns");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public SQLiteDatabase getWritableDatabase() {
            if (!zzj.this.zzNq.zzv(3600000L)) {
                throw new SQLiteException("Database open failed");
            }
            try {
                return super.getWritableDatabase();
            } catch (SQLiteException e) {
                zzj.this.zzNq.start();
                zzj.this.zzbe("Opening the database failed, dropping the table and recreating it");
                zzj.this.getContext().getDatabasePath(zzj.this.zziZ()).delete();
                try {
                    SQLiteDatabase writableDatabase = super.getWritableDatabase();
                    zzj.this.zzNq.clear();
                    return writableDatabase;
                } catch (SQLiteException e2) {
                    zzj.this.zze("Failed to open freshly created database", e2);
                    throw e2;
                }
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase database) {
            zzx.zzbl(database.getPath());
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase database) {
            if (Build.VERSION.SDK_INT < 15) {
                Cursor cursorRawQuery = database.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    cursorRawQuery.moveToFirst();
                } finally {
                    cursorRawQuery.close();
                }
            }
            if (zza(database, "hits2")) {
                zza(database);
            } else {
                database.execSQL(zzj.zzNm);
            }
            if (zza(database, "properties")) {
                zzb(database);
            } else {
                database.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        }
    }

    zzj(zzf zzfVar) {
        super(zzfVar);
        this.zzNp = new zzaj(zzit());
        this.zzNq = new zzaj(zzit());
        this.zzNo = new zza(zzfVar.getContext(), zziZ());
    }

    private static String zzB(Map<String, String> map) {
        com.google.android.gms.common.internal.zzx.zzw(map);
        Uri.Builder builder = new Uri.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        String encodedQuery = builder.build().getEncodedQuery();
        return encodedQuery == null ? "" : encodedQuery;
    }

    private long zza(String str, String[] strArr, long j) {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (cursorRawQuery.moveToFirst()) {
                    j = cursorRawQuery.getLong(0);
                } else if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzd("Database error", str, e);
                throw e;
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    private long zzb(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (!cursorRawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = cursorRawQuery.getLong(0);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzd("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private String zzd(zzab zzabVar) {
        return zzabVar.zzkC() ? zziv().zzjO() : zziv().zzjP();
    }

    private static String zze(zzab zzabVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        Uri.Builder builder = new Uri.Builder();
        for (Map.Entry<String, String> entry : zzabVar.zzn().entrySet()) {
            String key = entry.getKey();
            if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key)) {
                builder.appendQueryParameter(key, entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        return encodedQuery == null ? "" : encodedQuery;
    }

    private void zziY() throws Throwable {
        int iZzjY = zziv().zzjY();
        long jZziP = zziP();
        if (jZziP > iZzjY - 1) {
            List<Long> listZzo = zzo((jZziP - ((long) iZzjY)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(listZzo.size()));
            zzk(listZzo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String zziZ() {
        if (zziv().zzjA() && !zziv().zzjB()) {
            return zziv().zzkb();
        }
        return zziv().zzka();
    }

    public void beginTransaction() {
        zziE();
        getWritableDatabase().beginTransaction();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.zzNo.close();
        } catch (SQLiteException e) {
            zze("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zze("Error closing database", e2);
        }
    }

    public void endTransaction() {
        zziE();
        getWritableDatabase().endTransaction();
    }

    SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzNo.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }

    boolean isEmpty() {
        return zziP() == 0;
    }

    public void setTransactionSuccessful() {
        zziE();
        getWritableDatabase().setTransactionSuccessful();
    }

    public long zza(long j, String str, String str2) {
        com.google.android.gms.common.internal.zzx.zzcr(str);
        com.google.android.gms.common.internal.zzx.zzcr(str2);
        zziE();
        zzis();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0L);
    }

    public void zza(long j, String str) {
        com.google.android.gms.common.internal.zzx.zzcr(str);
        zziE();
        zzis();
        int iDelete = getWritableDatabase().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(j), str});
        if (iDelete > 0) {
            zza("Deleted property records", Integer.valueOf(iDelete));
        }
    }

    public void zzb(zzh zzhVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzhVar);
        zziE();
        zzis();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String strZzB = zzB(zzhVar.zzn());
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_uid", Long.valueOf(zzhVar.zziM()));
        contentValues.put("cid", zzhVar.getClientId());
        contentValues.put("tid", zzhVar.zziN());
        contentValues.put("adid", Integer.valueOf(zzhVar.zziO() ? 1 : 0));
        contentValues.put("hits_count", Long.valueOf(zzhVar.zziP()));
        contentValues.put("params", strZzB);
        try {
            if (writableDatabase.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                zzbe("Failed to insert/update a property (got -1)");
            }
        } catch (SQLiteException e) {
            zze("Error storing a property", e);
        }
    }

    Map<String, String> zzbf(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                str = "?" + str;
            }
            List<NameValuePair> list = URLEncodedUtils.parse(new URI(str), "UTF-8");
            HashMap map = new HashMap(list.size());
            for (NameValuePair nameValuePair : list) {
                map.put(nameValuePair.getName(), nameValuePair.getValue());
            }
            return map;
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    Map<String, String> zzbg(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            List<NameValuePair> list = URLEncodedUtils.parse(new URI("?" + str), "UTF-8");
            HashMap map = new HashMap(list.size());
            for (NameValuePair nameValuePair : list) {
                map.put(nameValuePair.getName(), nameValuePair.getValue());
            }
            return map;
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    public void zzc(zzab zzabVar) throws Throwable {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        zzis();
        zziE();
        String strZze = zze(zzabVar);
        if (strZze.length() > 8192) {
            zziu().zza(zzabVar, "Hit length exceeds the maximum allowed size");
            return;
        }
        zziY();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", strZze);
        contentValues.put("hit_time", Long.valueOf(zzabVar.zzkA()));
        contentValues.put("hit_app_id", Integer.valueOf(zzabVar.zzky()));
        contentValues.put("hit_url", zzd(zzabVar));
        try {
            long jInsert = writableDatabase.insert("hits2", null, contentValues);
            if (jInsert == -1) {
                zzbe("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(jInsert), zzabVar);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
    }

    public long zziP() {
        zzis();
        zziE();
        return zzb("SELECT COUNT(*) FROM hits2", (String[]) null);
    }

    public void zziU() {
        zzis();
        zziE();
        getWritableDatabase().delete("hits2", null, null);
    }

    public void zziV() {
        zzis();
        zziE();
        getWritableDatabase().delete("properties", null, null);
    }

    public int zziW() {
        zzis();
        zziE();
        if (!this.zzNp.zzv(MeasurementDispatcher.MILLIS_PER_DAY)) {
            return 0;
        }
        this.zzNp.start();
        zzba("Deleting stale hits (if any)");
        int iDelete = getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzit().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(iDelete));
        return iDelete;
    }

    public long zziX() {
        zzis();
        zziE();
        return zza(zzNn, (String[]) null, 0L);
    }

    public void zzk(List<Long> list) {
        com.google.android.gms.common.internal.zzx.zzw(list);
        zzis();
        zziE();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("hit_id");
        sb.append(" in (");
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                sb.append(")");
                String string = sb.toString();
                try {
                    SQLiteDatabase writableDatabase = getWritableDatabase();
                    zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                    int iDelete = writableDatabase.delete("hits2", string, null);
                    if (iDelete != list.size()) {
                        zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(iDelete), string);
                        return;
                    }
                    return;
                } catch (SQLiteException e) {
                    zze("Error deleting hits", e);
                    throw e;
                }
            }
            Long l = list.get(i2);
            if (l == null || l.longValue() == 0) {
                break;
            }
            if (i2 > 0) {
                sb.append(",");
            }
            sb.append(l);
            i = i2 + 1;
        }
        throw new SQLiteException("Invalid hit id");
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0073: MOVE (r10 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:25:0x0073 */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<java.lang.Long> zzo(long r14) throws java.lang.Throwable {
        /*
            r13 = this;
            r10 = 0
            r13.zzis()
            r13.zziE()
            r0 = 0
            int r0 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r0 > 0) goto L12
            java.util.List r0 = java.util.Collections.emptyList()
        L11:
            return r0
        L12:
            android.database.sqlite.SQLiteDatabase r0 = r13.getWritableDatabase()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.lang.String r1 = "hits2"
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.String r7 = "%s ASC"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            r11 = 0
            java.lang.String r12 = "hit_id"
            r8[r11] = r12     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            java.lang.String r8 = java.lang.Long.toString(r14)     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L5e java.lang.Throwable -> L6b
            boolean r0 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L75
            if (r0 == 0) goto L57
        L45:
            r0 = 0
            long r2 = r1.getLong(r0)     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L75
            java.lang.Long r0 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L75
            r9.add(r0)     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L75
            boolean r0 = r1.moveToNext()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteException -> L75
            if (r0 != 0) goto L45
        L57:
            if (r1 == 0) goto L5c
            r1.close()
        L5c:
            r0 = r9
            goto L11
        L5e:
            r0 = move-exception
            r1 = r10
        L60:
            java.lang.String r2 = "Error selecting hit ids"
            r13.zzd(r2, r0)     // Catch: java.lang.Throwable -> L72
            if (r1 == 0) goto L5c
            r1.close()
            goto L5c
        L6b:
            r0 = move-exception
        L6c:
            if (r10 == 0) goto L71
            r10.close()
        L71:
            throw r0
        L72:
            r0 = move-exception
            r10 = r1
            goto L6c
        L75:
            r0 = move-exception
            goto L60
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zzo(long):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.google.android.gms.analytics.internal.zzab> zzp(long r14) {
        /*
            r13 = this;
            r0 = 1
            r1 = 0
            r9 = 0
            r2 = 0
            int r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r2 < 0) goto L8f
        L9:
            com.google.android.gms.common.internal.zzx.zzaa(r0)
            r13.zzis()
            r13.zziE()
            android.database.sqlite.SQLiteDatabase r0 = r13.getWritableDatabase()
            java.lang.String r1 = "hits2"
            r2 = 5
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r3 = 0
            java.lang.String r4 = "hit_id"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r3 = 1
            java.lang.String r4 = "hit_time"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r3 = 2
            java.lang.String r4 = "hit_string"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r3 = 3
            java.lang.String r4 = "hit_url"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r3 = 4
            java.lang.String r4 = "hit_app_id"
            r2[r3] = r4     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.String r7 = "%s ASC"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            r10 = 0
            java.lang.String r11 = "hit_id"
            r8[r10] = r11     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            java.lang.String r8 = java.lang.Long.toString(r14)     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            android.database.Cursor r9 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: android.database.sqlite.SQLiteException -> L92 java.lang.Throwable -> La2
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r10.<init>()     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            boolean r0 = r9.moveToFirst()     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            if (r0 == 0) goto L89
        L59:
            r0 = 0
            long r6 = r9.getLong(r0)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r0 = 1
            long r3 = r9.getLong(r0)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r0 = 2
            java.lang.String r0 = r9.getString(r0)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r1 = 3
            java.lang.String r1 = r9.getString(r1)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r2 = 4
            int r8 = r9.getInt(r2)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            java.util.Map r2 = r13.zzbf(r0)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            boolean r5 = com.google.android.gms.analytics.internal.zzam.zzbu(r1)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            com.google.android.gms.analytics.internal.zzab r0 = new com.google.android.gms.analytics.internal.zzab     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r1 = r13
            r0.<init>(r1, r2, r3, r5, r6, r8)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            r10.add(r0)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            boolean r0 = r9.moveToNext()     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteException -> La4
            if (r0 != 0) goto L59
        L89:
            if (r9 == 0) goto L8e
            r9.close()
        L8e:
            return r10
        L8f:
            r0 = r1
            goto L9
        L92:
            r0 = move-exception
            r1 = r9
        L94:
            java.lang.String r2 = "Error loading hits from the database"
            r13.zze(r2, r0)     // Catch: java.lang.Throwable -> L9a
            throw r0     // Catch: java.lang.Throwable -> L9a
        L9a:
            r0 = move-exception
            r9 = r1
        L9c:
            if (r9 == 0) goto La1
            r9.close()
        La1:
            throw r0
        La2:
            r0 = move-exception
            goto L9c
        La4:
            r0 = move-exception
            r1 = r9
            goto L94
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zzp(long):java.util.List");
    }

    public void zzq(long j) {
        zzis();
        zziE();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zzk(arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.google.android.gms.analytics.internal.zzh> zzr(long r14) throws java.lang.Throwable {
        /*
            r13 = this;
            r13.zziE()
            r13.zzis()
            android.database.sqlite.SQLiteDatabase r0 = r13.getWritableDatabase()
            r9 = 0
            r1 = 5
            java.lang.String[] r2 = new java.lang.String[r1]     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r1 = 0
            java.lang.String r3 = "cid"
            r2[r1] = r3     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r1 = 1
            java.lang.String r3 = "tid"
            r2[r1] = r3     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r1 = 2
            java.lang.String r3 = "adid"
            r2[r1] = r3     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r1 = 3
            java.lang.String r3 = "hits_count"
            r2[r1] = r3     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r1 = 4
            java.lang.String r3 = "params"
            r2[r1] = r3     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            com.google.android.gms.analytics.internal.zzr r1 = r13.zziv()     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            int r10 = r1.zzjZ()     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            java.lang.String r8 = java.lang.String.valueOf(r10)     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            java.lang.String r3 = "app_uid=?"
            r1 = 1
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r1 = 0
            java.lang.String r5 = java.lang.String.valueOf(r14)     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            r4[r1] = r5     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            java.lang.String r1 = "properties"
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r9 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lb8 android.database.sqlite.SQLiteException -> Lba
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            r11.<init>()     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            boolean r0 = r9.moveToFirst()     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            if (r0 == 0) goto L8b
        L53:
            r0 = 0
            java.lang.String r3 = r9.getString(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            r0 = 1
            java.lang.String r4 = r9.getString(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            r0 = 2
            int r0 = r9.getInt(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            if (r0 == 0) goto L9c
            r5 = 1
        L65:
            r0 = 3
            int r0 = r9.getInt(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            long r6 = (long) r0     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            r0 = 4
            java.lang.String r0 = r9.getString(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            java.util.Map r8 = r13.zzbg(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            if (r0 != 0) goto L80
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            if (r0 == 0) goto L9e
        L80:
            java.lang.String r0 = "Read property with empty client id or tracker id"
            r13.zzc(r0, r3, r4)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
        L85:
            boolean r0 = r9.moveToNext()     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            if (r0 != 0) goto L53
        L8b:
            int r0 = r11.size()     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            if (r0 < r10) goto L96
            java.lang.String r0 = "Sending hits to too many properties. Campaign report might be incorrect"
            r13.zzbd(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
        L96:
            if (r9 == 0) goto L9b
            r9.close()
        L9b:
            return r11
        L9c:
            r5 = 0
            goto L65
        L9e:
            com.google.android.gms.analytics.internal.zzh r0 = new com.google.android.gms.analytics.internal.zzh     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            r1 = r14
            r0.<init>(r1, r3, r4, r5, r6, r8)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            r11.add(r0)     // Catch: android.database.sqlite.SQLiteException -> La8 java.lang.Throwable -> Lb8
            goto L85
        La8:
            r0 = move-exception
            r1 = r9
        Laa:
            java.lang.String r2 = "Error loading hits from the database"
            r13.zze(r2, r0)     // Catch: java.lang.Throwable -> Lb0
            throw r0     // Catch: java.lang.Throwable -> Lb0
        Lb0:
            r0 = move-exception
            r9 = r1
        Lb2:
            if (r9 == 0) goto Lb7
            r9.close()
        Lb7:
            throw r0
        Lb8:
            r0 = move-exception
            goto Lb2
        Lba:
            r0 = move-exception
            r1 = r9
            goto Laa
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zzr(long):java.util.List");
    }
}
