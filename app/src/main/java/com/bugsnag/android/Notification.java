package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
final class Notification implements JsonStream.Streamable {
    private Configuration config;
    private Collection<Error> errors = new LinkedList();
    private Collection<File> errorFiles = new LinkedList();

    Notification(Configuration config) {
        this.config = config;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws Throwable {
        writer.beginObject();
        writer.name("apiKey").value(this.config.apiKey);
        Notifier.getInstance().toStream(writer.name("notifier"));
        writer.name("events").beginArray();
        for (Error error : this.errors) {
            error.toStream(writer);
        }
        for (File errorFile : this.errorFiles) {
            writer.value(errorFile);
        }
        writer.endArray();
        writer.endObject();
    }

    final void addError(Error error) {
        this.errors.add(error);
    }

    final void addError(File errorFile) {
        this.errorFiles.add(errorFile);
    }

    final int deliver() throws HttpClient$NetworkException, HttpClient$BadResponseException {
        HttpURLConnection httpURLConnection = null;
        OutputStream outputStream = null;
        httpURLConnection = null;
        final String str = this.config.endpoint;
        try {
            try {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setChunkedStreamingMode(0);
                    httpURLConnection2.addRequestProperty("Content-Type", "application/json");
                    try {
                        outputStream = httpURLConnection2.getOutputStream();
                        JsonStream jsonStream = new JsonStream(new OutputStreamWriter(outputStream));
                        toStream(jsonStream);
                        jsonStream.close();
                        AppData.closeQuietly(outputStream);
                        final int responseCode = httpURLConnection2.getResponseCode();
                        if (responseCode / 100 != 2) {
                            throw new Exception(str, responseCode) { // from class: com.bugsnag.android.HttpClient$BadResponseException
                                {
                                    super(String.format("Got non-200 response code (%d) from %s", Integer.valueOf(responseCode), str));
                                }
                            };
                        }
                        AppData.close(httpURLConnection2);
                        return this.errors.size() + this.errorFiles.size();
                    } catch (Throwable th) {
                        AppData.closeQuietly(outputStream);
                        throw th;
                    }
                } catch (IOException e) {
                    httpURLConnection = httpURLConnection2;
                    e = e;
                    throw new IOException(str, e) { // from class: com.bugsnag.android.HttpClient$NetworkException
                        {
                            super(String.format("Network error when posting to %s", str));
                            initCause(e);
                        }
                    };
                } catch (Throwable th2) {
                    httpURLConnection = httpURLConnection2;
                    th = th2;
                    AppData.close(httpURLConnection);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }
}
