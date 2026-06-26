package com.adobe.mobile;

import android.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import com.adobe.mobile.StaticMethods;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class MessageNotificationHandler extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Object notification;
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            StaticMethods.logDebugFormat("Messages - unable to load extras from local notification intent", new Object[0]);
            return;
        }
        try {
            String message = bundle.getString("alarm_message");
            int requestCode = bundle.getInt("adbMessageCode");
            int otherRequestCode = bundle.getInt("requestCode");
            if (requestCode == Messages.MESSAGE_LOCAL_IDENTIFIER.intValue()) {
                if (message == null) {
                    StaticMethods.logDebugFormat("Messages - local notification message was empty ", new Object[0]);
                    return;
                }
                try {
                    Activity currentActivity = StaticMethods.getCurrentActivity();
                    try {
                        Context sharedContext = StaticMethods.getSharedContext();
                        Activity notificationActivity = null;
                        try {
                            notificationActivity = StaticMethods.getCurrentActivity();
                        } catch (StaticMethods.NullActivityException e) {
                            StaticMethods.logErrorFormat("Messages - unable to find activity for your notification, using default", new Object[0]);
                        }
                        Intent resumeIntent = notificationActivity != null ? notificationActivity.getIntent() : intent;
                        resumeIntent.setFlags(603979776);
                        int buildVersion = Build.VERSION.SDK_INT;
                        try {
                            PendingIntent sender = PendingIntent.getActivity(sharedContext, otherRequestCode, resumeIntent, 134217728);
                            if (sender == null) {
                                StaticMethods.logDebugFormat("Messages - could not retrieve sender from broadcast, unable to post notification", new Object[0]);
                                return;
                            }
                            if (buildVersion >= 11) {
                                ClassLoader classLoader = BroadcastReceiver.class.getClassLoader();
                                Class<?> notificationBuilderClass = classLoader.loadClass("android.app.Notification$Builder");
                                Constructor<?> notificationConstructor = notificationBuilderClass.getConstructor(Context.class);
                                notificationConstructor.setAccessible(true);
                                Object notificationBuilder = notificationConstructor.newInstance(StaticMethods.getSharedContext());
                                Method setSmallIcon = notificationBuilderClass.getDeclaredMethod("setSmallIcon", Integer.TYPE);
                                setSmallIcon.invoke(notificationBuilder, Integer.valueOf(getSmallIcon()));
                                Method setLargeIcon = notificationBuilderClass.getDeclaredMethod("setLargeIcon", Bitmap.class);
                                setLargeIcon.invoke(notificationBuilder, getLargeIcon());
                                Method setContentTitle = notificationBuilderClass.getDeclaredMethod("setContentTitle", CharSequence.class);
                                setContentTitle.invoke(notificationBuilder, getAppName());
                                Method setContentText = notificationBuilderClass.getDeclaredMethod("setContentText", CharSequence.class);
                                setContentText.invoke(notificationBuilder, message);
                                Method setContentIntent = notificationBuilderClass.getDeclaredMethod("setContentIntent", PendingIntent.class);
                                setContentIntent.invoke(notificationBuilder, sender);
                                Method setAutoCancel = notificationBuilderClass.getDeclaredMethod("setAutoCancel", Boolean.TYPE);
                                setAutoCancel.invoke(notificationBuilder, true);
                                if (buildVersion >= 16) {
                                    Method build = notificationBuilderClass.getDeclaredMethod("build", new Class[0]);
                                    notification = build.invoke(notificationBuilder, new Object[0]);
                                } else {
                                    Method getNotification = notificationBuilderClass.getDeclaredMethod("getNotification", new Class[0]);
                                    notification = getNotification.invoke(notificationBuilder, new Object[0]);
                                }
                                if (notification == null) {
                                    return;
                                }
                            } else {
                                Notification tempNotification = new Notification();
                                Method __setLatestEventInfo = Notification.class.getDeclaredMethod("setLatestEventInfo", Context.class, String.class, String.class, PendingIntent.class);
                                __setLatestEventInfo.invoke(tempNotification, sharedContext, getAppName(), message, sender);
                                Field __icon = Notification.class.getField("icon");
                                __icon.set(tempNotification, Integer.valueOf(getSmallIcon()));
                                tempNotification.flags = 16;
                                notification = tempNotification;
                            }
                            NotificationManager notificationManager = (NotificationManager) currentActivity.getSystemService("notification");
                            notificationManager.notify(new SecureRandom().nextInt(), (Notification) notification);
                        } catch (StaticMethods.NullContextException ex) {
                            StaticMethods.logErrorFormat("Messages - error posting notification (%s)", ex.getMessage());
                        } catch (ClassNotFoundException ex2) {
                            StaticMethods.logErrorFormat("Messages - error posting notification, class not found (%s)", ex2.getMessage());
                        } catch (NoSuchMethodException ex3) {
                            StaticMethods.logErrorFormat("Messages - error posting notification, method not found (%s)", ex3.getMessage());
                        } catch (Exception ex4) {
                            StaticMethods.logErrorFormat("Messages - unexpected error posting notification (%s)", ex4.getMessage());
                        }
                    } catch (StaticMethods.NullContextException ex5) {
                        StaticMethods.logErrorFormat(ex5.getMessage(), new Object[0]);
                    }
                } catch (StaticMethods.NullActivityException ex6) {
                    StaticMethods.logErrorFormat(ex6.getMessage(), new Object[0]);
                }
            }
        } catch (Exception ex7) {
            StaticMethods.logDebugFormat("Messages - unable to load message from local notification (%s)", ex7.getMessage());
        }
    }

    private String getAppName() {
        ApplicationInfo applicationInfo;
        String appName = "";
        try {
            PackageManager packageManager = StaticMethods.getSharedContext().getPackageManager();
            if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(StaticMethods.getSharedContext().getPackageName(), 0)) == null || packageManager.getApplicationLabel(applicationInfo) == null) {
                return "";
            }
            appName = (String) packageManager.getApplicationLabel(applicationInfo);
            return appName;
        } catch (PackageManager.NameNotFoundException ex) {
            StaticMethods.logDebugFormat("Messages - unable to retrieve app name for local notification (%s)", ex.getMessage());
            return appName;
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logDebugFormat("Messages - unable to get app name (%s)", e.getMessage());
            return appName;
        }
    }

    private int getSmallIcon() {
        return Messages.getSmallIconResourceId() != -1 ? Messages.getSmallIconResourceId() : R.drawable.sym_def_app_icon;
    }

    private Bitmap getLargeIcon() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, StaticMethods.NullContextException, InvocationTargetException {
        PackageManager packageManager;
        Drawable iconDrawable = null;
        int largeIconResourceId = Messages.getLargeIconResourceId();
        if (largeIconResourceId != -1) {
            Context ctx = StaticMethods.getSharedContext();
            if (Build.VERSION.SDK_INT > 20) {
                Method __getDrawable = Resources.class.getDeclaredMethod("getDrawable", Integer.TYPE, Resources.Theme.class);
                iconDrawable = (Drawable) __getDrawable.invoke(ctx.getResources(), Integer.valueOf(largeIconResourceId), ctx.getTheme());
            } else {
                Method __getDrawable2 = Resources.class.getDeclaredMethod("getDrawable", Integer.TYPE);
                iconDrawable = (Drawable) __getDrawable2.invoke(ctx.getResources(), Integer.valueOf(largeIconResourceId));
            }
        } else {
            ApplicationInfo applicationInfo = StaticMethods.getSharedContext().getApplicationInfo();
            if (applicationInfo != null && (packageManager = StaticMethods.getSharedContext().getPackageManager()) != null) {
                iconDrawable = packageManager.getApplicationIcon(applicationInfo);
            }
        }
        if (iconDrawable == null) {
            return null;
        }
        Bitmap icon = ((BitmapDrawable) iconDrawable).getBitmap();
        return icon;
    }
}
