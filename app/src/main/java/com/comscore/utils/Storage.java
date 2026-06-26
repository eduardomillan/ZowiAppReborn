package com.comscore.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Storage {
    public static final String APP_NAME_KEY = "appName";
    private static final String h = "cSPrefs";
    private static final long i = 300;
    protected SharedPreferences a;
    protected final HashMap<String, String> b;
    protected final HashSet<String> c;
    protected final Object d;
    protected final Object e;
    protected long f;
    protected boolean g;
    private final Runnable j;

    public Storage(Context context) {
        this(context, h);
    }

    public Storage(Context context, String str) {
        this.b = new HashMap<>();
        this.c = new HashSet<>();
        this.d = new Object();
        this.e = new Object();
        this.f = -1L;
        this.g = false;
        this.j = new d(this);
        a(context, str);
    }

    protected void a() {
        synchronized (this.d) {
            if (this.g) {
                if (this.f < 0) {
                    this.f = System.currentTimeMillis() + i;
                    new Thread(this.j).start();
                } else {
                    this.f = System.currentTimeMillis() + i;
                    this.d.notify();
                }
            }
        }
    }

    protected void a(Context context, String str) {
        synchronized (this.e) {
            synchronized (this.d) {
                this.a = context.getSharedPreferences(str, 0);
                for (Map.Entry<String, ?> entry : this.a.getAll().entrySet()) {
                    if (entry.getValue() instanceof String) {
                        this.b.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                this.g = true;
            }
        }
    }

    public void add(String str, long j) {
        long j2;
        synchronized (this.e) {
            if (this.g) {
                if (has(str).booleanValue()) {
                    try {
                        j2 = Long.parseLong(this.b.get(str));
                    } catch (NumberFormatException e) {
                        j2 = 0;
                    }
                    this.b.put(str, Long.toString(j2 + j));
                    this.c.add(str);
                    a();
                } else {
                    set(str, Long.toString(j));
                }
            }
        }
    }

    protected void b() {
        while (true) {
            synchronized (this.d) {
                long jCurrentTimeMillis = this.f - System.currentTimeMillis();
                if (jCurrentTimeMillis <= 0) {
                    return;
                } else {
                    try {
                        this.d.wait(jCurrentTimeMillis);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    protected void c() {
        synchronized (this.e) {
            synchronized (this.d) {
                this.f = -1L;
            }
            SharedPreferences.Editor editorEdit = this.a.edit();
            for (String str : this.c) {
                if (this.b.containsKey(str)) {
                    editorEdit.putString(str, this.b.get(str));
                } else {
                    editorEdit.remove(str);
                }
            }
            editorEdit.commit();
            this.c.clear();
        }
    }

    public void clear() {
        synchronized (this.e) {
            if (this.g) {
                this.c.addAll(this.b.keySet());
                this.b.clear();
                a();
            }
        }
    }

    public void close() {
        synchronized (this.e) {
            this.g = false;
            while (this.f >= 0) {
                try {
                    this.e.wait(100L);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public String get(String str) {
        synchronized (this.e) {
            if (!this.g || !has(str).booleanValue()) {
                return "";
            }
            return this.b.get(str);
        }
    }

    public Boolean has(String str) {
        synchronized (this.e) {
            if (!this.g) {
                return false;
            }
            return Boolean.valueOf(this.b.containsKey(str));
        }
    }

    public void remove(String str) {
        synchronized (this.e) {
            if (this.g && has(str).booleanValue()) {
                this.b.remove(str);
                this.c.add(str);
                a();
            }
        }
    }

    public void set(String str, String str2) {
        synchronized (this.e) {
            if (this.g) {
                String str3 = this.b.get(str);
                if (!this.b.containsKey(str) || (str3 != str2 && (str2 == null || str3 == null || !str3.equals(str2)))) {
                    this.b.put(str, str2);
                    this.c.add(str);
                    a();
                }
            }
        }
    }
}
