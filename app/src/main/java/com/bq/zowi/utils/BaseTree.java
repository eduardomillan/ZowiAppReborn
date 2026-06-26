package com.bq.zowi.utils;

import android.util.Log;
import com.bq.robotic.droid2ino.utils.Droid2InoConstants;
import com.bq.zowi.utils.Grove;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTree implements Grove.Tree {
    private final ThreadLocal<String> explicitTag = new ThreadLocal<>();

    String getTag() {
        String tag = this.explicitTag.get();
        if (tag != null) {
            this.explicitTag.remove();
        }
        return tag;
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void v(String message, Object... args) {
        prepareLog(2, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void v(Throwable t, String message, Object... args) {
        prepareLog(2, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void d(String message, Object... args) {
        prepareLog(3, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void d(Throwable t, String message, Object... args) {
        prepareLog(3, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void i(String message, Object... args) {
        prepareLog(4, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void i(Throwable t, String message, Object... args) {
        prepareLog(4, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void w(String message, Object... args) {
        prepareLog(5, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void w(Throwable t, String message, Object... args) {
        prepareLog(5, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void e(String message, Object... args) {
        prepareLog(6, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void e(Throwable t, String message, Object... args) {
        prepareLog(6, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void wtf(String message, Object... args) {
        prepareLog(7, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void wtf(Throwable t, String message, Object... args) {
        prepareLog(7, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void log(int priority, String message, Object... args) {
        prepareLog(priority, null, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public void log(int priority, Throwable t, String message, Object... args) {
        prepareLog(priority, t, message, args);
    }

    @Override // com.bq.zowi.utils.Grove.Tree
    public ThreadLocal<String> explicitTag() {
        return this.explicitTag;
    }

    protected boolean isLoggable(int priority) {
        return true;
    }

    private void prepareLog(int priority, Throwable t, String message, Object... args) {
        if (isLoggable(priority)) {
            if (message != null && message.length() == 0) {
                message = null;
            }
            if (message == null) {
                if (t != null) {
                    message = getStackTraceString(t);
                } else {
                    return;
                }
            } else {
                if (args.length > 0) {
                    message = String.format(message, args);
                }
                if (t != null) {
                    message = message + Droid2InoConstants.NEW_LINE_CHARACTER + getStackTraceString(t);
                }
            }
            log(priority, getTag(), message, t);
        }
    }

    private String getStackTraceString(Throwable t) {
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter((Writer) sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static class DebugTree extends BaseTree {
        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
        private static final int CALL_STACK_INDEX = 5;
        private static final int MAX_LOG_LENGTH = 4000;

        protected String createStackElementTag(StackTraceElement element) {
            String tag = element.getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m.find()) {
                tag = m.replaceAll("");
            }
            return tag.substring(tag.lastIndexOf(46) + 1);
        }

        @Override // com.bq.zowi.utils.BaseTree
        final String getTag() {
            String tag = super.getTag();
            if (tag == null) {
                StackTraceElement[] stackTrace = new Throwable().getStackTrace();
                if (stackTrace.length <= 5) {
                    throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
                }
                return createStackElementTag(stackTrace[5]);
            }
            return tag;
        }

        @Override // com.bq.zowi.utils.Grove.Tree
        public void log(int priority, String tag, String message, Throwable t) {
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == 7) {
                    Log.wtf(tag, message);
                    return;
                } else {
                    Log.println(priority, tag, message);
                    return;
                }
            }
            int i = 0;
            int length = message.length();
            while (i < length) {
                int newline = message.indexOf(10, i);
                if (newline == -1) {
                    newline = length;
                }
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == 7) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
                i++;
            }
        }
    }
}
