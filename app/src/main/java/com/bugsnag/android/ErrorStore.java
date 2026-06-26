package com.bugsnag.android;

import android.content.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
final class ErrorStore {
    private final Configuration config;
    private final String path;

    ErrorStore(Configuration config, Context appContext) {
        String path;
        this.config = config;
        try {
            path = appContext.getCacheDir().getAbsolutePath() + "/bugsnag-errors/";
            File outFile = new File(path);
            outFile.mkdirs();
            if (!outFile.exists()) {
                AppData.warn("Could not prepare error storage directory");
                path = null;
            }
        } catch (Exception e) {
            AppData.warn("Could not prepare error storage directory", e);
            path = null;
        }
        this.path = path;
    }

    final void flush() {
        if (this.path != null) {
            Async.run(new Runnable() { // from class: com.bugsnag.android.ErrorStore.1
                @Override // java.lang.Runnable
                public final void run() {
                    File[] errorFiles;
                    File exceptionDir = new File(ErrorStore.this.path);
                    if (exceptionDir.exists() && exceptionDir.isDirectory() && (errorFiles = exceptionDir.listFiles()) != null && errorFiles.length > 0) {
                        AppData.info(String.format("Sending %d saved error(s) to Bugsnag", Integer.valueOf(errorFiles.length)));
                        for (File errorFile : errorFiles) {
                            try {
                                Notification notif = new Notification(ErrorStore.this.config);
                                notif.addError(errorFile);
                                notif.deliver();
                                AppData.info("Deleting sent error file " + errorFile.getName());
                                errorFile.delete();
                            } catch (HttpClient$NetworkException e) {
                                AppData.warn("Could not send previously saved error(s) to Bugsnag, will try again later", e);
                            } catch (Exception e2) {
                                AppData.warn("Problem sending unsent error from disk", e2);
                                errorFile.delete();
                            }
                        }
                    }
                }
            });
        }
    }

    final void write(Error error) {
        if (this.path != null) {
            String filename = String.format("%s%d.json", this.path, Long.valueOf(System.currentTimeMillis()));
            Writer out = null;
            try {
                try {
                    Writer out2 = new FileWriter(filename);
                    try {
                        JsonStream stream = new JsonStream(out2);
                        error.toStream(stream);
                        stream.close();
                        AppData.info(String.format("Saved unsent error to disk (%s) ", filename));
                        AppData.closeQuietly(out2);
                    } catch (Exception e) {
                        e = e;
                        out = out2;
                        AppData.warn(String.format("Couldn't save unsent error to disk (%s) ", filename), e);
                        AppData.closeQuietly(out);
                    } catch (Throwable th) {
                        th = th;
                        out = out2;
                        AppData.closeQuietly(out);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }
}
