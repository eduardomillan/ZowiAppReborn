package com.adobe.mobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import com.adobe.mobile.StaticMethods;
import java.security.SecureRandom;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class MessageLocalNotification extends Message {
    private static final String JSON_CONFIG_CONTENT = "content";
    private static final String JSON_CONFIG_WAIT = "wait";
    protected String content;
    protected Integer localNotificationDelay;

    MessageLocalNotification() {
    }

    @Override // com.adobe.mobile.Message
    protected boolean initWithPayloadObject(JSONObject dictionary) {
        if (dictionary == null || dictionary.length() <= 0 || !super.initWithPayloadObject(dictionary)) {
            return false;
        }
        try {
            JSONObject jsonPayload = dictionary.getJSONObject("payload");
            if (jsonPayload.length() <= 0) {
                StaticMethods.logDebugFormat("Messages - Unable to create local notification message \"%s\", payload is empty", this.messageId);
                return false;
            }
            try {
                this.content = jsonPayload.getString(JSON_CONFIG_CONTENT);
                if (this.content.length() <= 0) {
                    StaticMethods.logDebugFormat("Messages - Unable to create local notification message \"%s\", content is empty", this.messageId);
                    return false;
                }
                try {
                    this.localNotificationDelay = Integer.valueOf(jsonPayload.getInt(JSON_CONFIG_WAIT));
                    return true;
                } catch (JSONException e) {
                    StaticMethods.logDebugFormat("Messages - Unable to create local notification message \"%s\", localNotificationDelay is required", this.messageId);
                    return false;
                }
            } catch (JSONException e2) {
                StaticMethods.logDebugFormat("Messages - Unable to create local notification message \"%s\", content is required", this.messageId);
                return false;
            }
        } catch (JSONException e3) {
            StaticMethods.logDebugFormat("Messages - Unable to create local notification message \"%s\", payload is required", this.messageId);
            return false;
        }
    }

    @Override // com.adobe.mobile.Message
    protected void show() {
        super.show();
        try {
            Activity currentActivity = StaticMethods.getCurrentActivity();
            int requestCode = new SecureRandom().nextInt();
            Calendar calendar = Calendar.getInstance();
            calendar.add(13, this.localNotificationDelay.intValue());
            Intent intent = new Intent();
            intent.setClass(currentActivity, MessageNotificationHandler.class);
            intent.putExtra("alarm_message", this.content);
            intent.putExtra("adbMessageCode", Messages.MESSAGE_LOCAL_IDENTIFIER);
            intent.putExtra("requestCode", requestCode);
            try {
                PendingIntent sender = PendingIntent.getBroadcast(StaticMethods.getSharedContext(), requestCode, intent, 134217728);
                AlarmManager alarmManager = (AlarmManager) StaticMethods.getSharedContext().getSystemService("alarm");
                alarmManager.set(0, calendar.getTimeInMillis(), sender);
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Messaging - Error scheduling local notification (%s)", e.getMessage());
            }
            viewed();
        } catch (StaticMethods.NullActivityException ex) {
            StaticMethods.logErrorFormat(ex.getMessage(), new Object[0]);
        }
    }
}
