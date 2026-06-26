package com.adobe.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.adobe.mobile.StaticMethods;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class MessageAlert extends Message {
    private static final String JSON_CONFIG_CANCEL = "cancel";
    private static final String JSON_CONFIG_CONFIRM = "confirm";
    private static final String JSON_CONFIG_CONTENT = "content";
    private static final String JSON_CONFIG_TITLE = "title";
    private static final String JSON_CONFIG_URL = "url";
    protected AlertDialog alertDialog;
    protected String cancelButtonText;
    protected String confirmButtonText;
    protected String content;
    protected String title;
    protected String url;

    MessageAlert() {
    }

    @Override // com.adobe.mobile.Message
    protected boolean initWithPayloadObject(JSONObject dictionary) {
        if (dictionary == null || dictionary.length() <= 0 || !super.initWithPayloadObject(dictionary)) {
            return false;
        }
        try {
            JSONObject jsonPayload = dictionary.getJSONObject("payload");
            if (jsonPayload.length() <= 0) {
                StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", payload is empty", this.messageId);
                return false;
            }
            try {
                this.title = jsonPayload.getString(JSON_CONFIG_TITLE);
                if (this.title.length() <= 0) {
                    StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", title is empty", this.messageId);
                    return false;
                }
                try {
                    this.content = jsonPayload.getString(JSON_CONFIG_CONTENT);
                    if (this.content.length() <= 0) {
                        StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", content is empty", this.messageId);
                        return false;
                    }
                    try {
                        this.confirmButtonText = jsonPayload.getString(JSON_CONFIG_CONFIRM);
                        if (this.confirmButtonText.length() <= 0) {
                            StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", confirm is empty", this.messageId);
                            return false;
                        }
                        try {
                            this.cancelButtonText = jsonPayload.getString(JSON_CONFIG_CANCEL);
                            if (this.cancelButtonText.length() <= 0) {
                                StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", cancel is empty", this.messageId);
                                return false;
                            }
                            try {
                                this.url = jsonPayload.getString(JSON_CONFIG_URL);
                            } catch (JSONException e) {
                                StaticMethods.logDebugFormat("Messages - Tried to read url for alert message but found none.  This is not a required field", new Object[0]);
                            }
                            return true;
                        } catch (JSONException e2) {
                            StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", cancel is required", this.messageId);
                            return false;
                        }
                    } catch (JSONException e3) {
                        StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", confirm is required", this.messageId);
                        return false;
                    }
                } catch (JSONException e4) {
                    StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", content is required", this.messageId);
                    return false;
                }
            } catch (JSONException e5) {
                StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", title is required", this.messageId);
                return false;
            }
        } catch (JSONException e6) {
            StaticMethods.logWarningFormat("Messages - Unable to create alert message \"%s\", payload is required", this.messageId);
            return false;
        }
    }

    private static final class MessageShower implements Runnable {
        private final MessageAlert message;

        public MessageShower(MessageAlert messageToActOn) {
            this.message = messageToActOn;
        }

        private static final class PositiveClickHandler implements DialogInterface.OnClickListener {
            private final MessageAlert message;

            public PositiveClickHandler(MessageAlert messageToActOn) {
                this.message = messageToActOn;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                this.message.clickedThrough();
                this.message.isVisible = false;
                if (this.message.url != null && this.message.url.length() > 0) {
                    HashMap<String, String> tokens = new HashMap<>();
                    tokens.put("{userId}", StaticMethods.getVisitorID() == null ? "" : StaticMethods.getVisitorID());
                    tokens.put("{trackingId}", StaticMethods.getAID() == null ? "" : StaticMethods.getAID());
                    tokens.put("{messageId}", this.message.messageId);
                    tokens.put("{lifetimeValue}", AnalyticsTrackLifetimeValueIncrease.getLifetimeValue().toString());
                    this.message.url = StaticMethods.expandTokens(this.message.url, tokens);
                    try {
                        Activity currentActivity = StaticMethods.getCurrentActivity();
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setData(Uri.parse(this.message.url));
                            currentActivity.startActivity(intent);
                        } catch (Exception ex) {
                            StaticMethods.logDebugFormat("Messages - Could not load click-through intent for message (%s)", ex.toString());
                        }
                    } catch (StaticMethods.NullActivityException ex2) {
                        StaticMethods.logErrorFormat(ex2.getMessage(), new Object[0]);
                    }
                }
            }
        }

        private static final class NegativeClickHandler implements DialogInterface.OnClickListener {
            private final MessageAlert message;

            public NegativeClickHandler(MessageAlert messageToActOn) {
                this.message = messageToActOn;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                this.message.viewed();
                this.message.isVisible = false;
            }
        }

        private static final class CancelClickHandler implements DialogInterface.OnCancelListener {
            private final MessageAlert message;

            public CancelClickHandler(MessageAlert messageAlert) {
                this.message = messageAlert;
            }

            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                this.message.viewed();
                this.message.isVisible = false;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Activity currentActivity = StaticMethods.getCurrentActivity();
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
                    builder.setTitle(this.message.title);
                    builder.setMessage(this.message.content);
                    builder.setPositiveButton(this.message.confirmButtonText, new PositiveClickHandler(this.message));
                    builder.setNegativeButton(this.message.cancelButtonText, new NegativeClickHandler(this.message));
                    builder.setOnCancelListener(new CancelClickHandler(this.message));
                    this.message.alertDialog = builder.create();
                    this.message.alertDialog.setCanceledOnTouchOutside(false);
                    this.message.alertDialog.show();
                    this.message.isVisible = true;
                } catch (Exception ex) {
                    StaticMethods.logDebugFormat("Messages - Could not show alert message (%s)", ex.toString());
                }
            } catch (StaticMethods.NullActivityException ex2) {
                StaticMethods.logErrorFormat(ex2.getMessage(), new Object[0]);
            }
        }
    }

    @Override // com.adobe.mobile.Message
    protected void show() {
        if ((this.cancelButtonText != null && this.cancelButtonText.length() >= 1) || (this.confirmButtonText != null && this.confirmButtonText.length() >= 1)) {
            super.show();
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new MessageShower(this));
        }
    }

    protected static void clearCurrentDialog() {
        Message currentMessage = Messages.getCurrentMessage();
        if (currentMessage != null && (currentMessage instanceof MessageAlert) && currentMessage.orientationWhenShown != StaticMethods.getCurrentOrientation()) {
            if (((MessageAlert) currentMessage).alertDialog != null && ((MessageAlert) currentMessage).alertDialog.isShowing()) {
                ((MessageAlert) currentMessage).alertDialog.dismiss();
            }
            ((MessageAlert) currentMessage).alertDialog = null;
        }
    }
}
