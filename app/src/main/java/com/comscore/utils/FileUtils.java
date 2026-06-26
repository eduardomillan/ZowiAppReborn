package com.comscore.utils;

import android.content.Context;
import com.comscore.analytics.Core;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    public static boolean deleteFile(Core core, String str) {
        Context appContext = core.getAppContext();
        Storage storage = core.getStorage();
        boolean zDeleteFile = appContext.deleteFile(str);
        if (zDeleteFile) {
            CSLog.d((Class<? extends Object>) FileUtils.class, "File" + str + " was removed");
            storage.remove(str);
        }
        return zDeleteFile;
    }

    public static ArrayList<String> getFileList(Context context) {
        String[] list = null;
        File filesDir = context.getFilesDir();
        if (filesDir != null && filesDir.isDirectory()) {
            list = filesDir.list(new b());
            if (list != null) {
                Arrays.sort(list);
            } else {
                list = new String[0];
            }
        }
        return new ArrayList<>(Arrays.asList(list));
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0044 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] readCachedEvents(android.content.Context r5, java.lang.String r6) {
        /*
            java.util.LinkedList r3 = new java.util.LinkedList
            r3.<init>()
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L50
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L50
            java.io.FileInputStream r4 = r5.openFileInput(r6)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L50
            r0.<init>(r4)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L50
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L41 java.lang.Exception -> L50
        L14:
            java.lang.String r0 = r1.readLine()     // Catch: java.lang.Exception -> L1e java.lang.Throwable -> L4d
            if (r0 == 0) goto L31
            r3.add(r0)     // Catch: java.lang.Exception -> L1e java.lang.Throwable -> L4d
            goto L14
        L1e:
            r0 = move-exception
        L1f:
            com.comscore.utils.CSLog.printStackTrace(r0)     // Catch: java.lang.Throwable -> L4d
            if (r1 == 0) goto L27
            r1.close()     // Catch: java.io.IOException -> L3c
        L27:
            int r0 = r3.size()
            java.lang.String[] r0 = new java.lang.String[r0]
            r3.toArray(r0)
            return r0
        L31:
            if (r1 == 0) goto L27
            r1.close()     // Catch: java.io.IOException -> L37
            goto L27
        L37:
            r0 = move-exception
            com.comscore.utils.CSLog.printStackTrace(r0)
            goto L27
        L3c:
            r0 = move-exception
            com.comscore.utils.CSLog.printStackTrace(r0)
            goto L27
        L41:
            r0 = move-exception
        L42:
            if (r2 == 0) goto L47
            r2.close()     // Catch: java.io.IOException -> L48
        L47:
            throw r0
        L48:
            r1 = move-exception
            com.comscore.utils.CSLog.printStackTrace(r1)
            goto L47
        L4d:
            r0 = move-exception
            r2 = r1
            goto L42
        L50:
            r0 = move-exception
            r1 = r2
            goto L1f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.comscore.utils.FileUtils.readCachedEvents(android.content.Context, java.lang.String):java.lang.String[]");
    }

    public static void writeEvent(Core core, String str, int i, String str2) {
        Context appContext = core.getAppContext();
        Storage storage = core.getStorage();
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        int integer = Utils.getInteger(storage.get(str), 0);
        try {
            try {
                fileOutputStreamOpenFileOutput = appContext.openFileOutput(str, i);
                fileOutputStreamOpenFileOutput.write(str2.getBytes());
                storage.set(str, String.valueOf(integer + 1));
            } catch (Exception e) {
                CSLog.e((Class<? extends Object>) FileUtils.class, "Exception in writeEvent:" + e.getLocalizedMessage());
                CSLog.printStackTrace(e);
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e2) {
                        CSLog.printStackTrace(e2);
                    }
                }
            }
        } finally {
            if (fileOutputStreamOpenFileOutput != null) {
                try {
                    fileOutputStreamOpenFileOutput.close();
                } catch (IOException e3) {
                    CSLog.printStackTrace(e3);
                }
            }
        }
    }
}
