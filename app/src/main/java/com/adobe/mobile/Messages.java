package com.adobe.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class Messages {
    protected static final String MESSAGE_ACTION_NAME = "In-App Message";
    protected static final String MESSAGE_CLICKED = "a.message.clicked";
    protected static final String MESSAGE_ID = "a.message.id";
    protected static final String MESSAGE_LOCAL_ADB_CODE = "adbMessageCode";
    protected static final String MESSAGE_LOCAL_PAYLOAD = "alarm_message";
    protected static final String MESSAGE_LOCAL_REQUEST_CODE = "requestCode";
    protected static final String MESSAGE_TOKEN_LIFETIME_VALUE = "{lifetimeValue}";
    protected static final String MESSAGE_TOKEN_MESSAGE_ID = "{messageId}";
    protected static final String MESSAGE_TOKEN_TRACKING_ID = "{trackingId}";
    protected static final String MESSAGE_TOKEN_USER_ID = "{userId}";
    protected static final String MESSAGE_VIEWED = "a.message.viewed";
    protected static final Integer MESSAGE_LOCAL_IDENTIFIER = 750183;
    private static MessageFullScreen _messageFullScreen = null;
    private static final Object _messageFullScreenMutex = new Object();
    private static int _smallIconResourceId = -1;
    private static int _largeIconResourceId = -1;
    private static Message _currentMessage = null;
    private static final Object _currentMessageMutex = new Object();

    Messages() {
    }

    protected enum MessageShowRule {
        MESSAGE_SHOW_RULE_UNKNOWN(0),
        MESSAGE_SHOW_RULE_ALWAYS(1),
        MESSAGE_SHOW_RULE_ONCE(2),
        MESSAGE_SHOW_RULE_UNTIL_CLICK(3);

        private final int value;

        MessageShowRule(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }
    }

    protected static HashMap<String, Object> lowercaseKeysForMap(Map<String, Object> dataMap) {
        if (dataMap == null || dataMap.size() <= 0) {
            return null;
        }
        HashMap<String, Object> lowercasedDataMap = new HashMap<>(dataMap.size());
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            lowercasedDataMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return lowercasedDataMap;
    }

    protected static void block3rdPartyCallbacksQueueForReferrer() {
        StaticMethods.getThirdPartyCallbacksExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Messages.1
            @Override // java.lang.Runnable
            public void run() {
                while (!ReferrerHandler.getReferrerProcessed()) {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception e) {
                        StaticMethods.logWarningFormat("Data Callback - Data Callback Queue Is Interrupted(%s)", e.getMessage());
                    }
                }
            }
        });
    }

    protected static void checkFor3rdPartyCallbacks(final Map<String, Object> vars, final Map<String, Object> cdata) {
        StaticMethods.getThirdPartyCallbacksExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Messages.2
            @Override // java.lang.Runnable
            public void run() {
                ArrayList<Message> callbacks = MobileConfig.getInstance().getCallbackTemplates();
                if (callbacks != null && callbacks.size() > 0) {
                    Map<String, Object> lifecycleData = new HashMap<>(Lifecycle.getContextDataLowercase());
                    HashMap<String, Object> lowercaseContextData = Messages.lowercaseKeysForMap(cdata);
                    HashMap<String, Object> lowercaseVars = Messages.lowercaseKeysForMap(vars);
                    for (Message message : callbacks) {
                        if (message.shouldShowForVariables(lowercaseVars, lowercaseContextData, lifecycleData)) {
                            message.show();
                        }
                    }
                }
            }
        });
    }

    protected static void checkForInAppMessage(final Map<String, Object> vars, final Map<String, Object> cdata, final Map<String, Object> lifecycleData) {
        StaticMethods.getMessagesExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Messages.3
            @Override // java.lang.Runnable
            public void run() {
                ArrayList<Message> messages = null;
                if (!StaticMethods.isWearableApp()) {
                    messages = MobileConfig.getInstance().getInAppMessages();
                }
                if (messages != null && messages.size() > 0) {
                    if (vars == null || !vars.containsKey("pev2") || !vars.get("pev2").toString().equals("ADBINTERNAL:In-App Message")) {
                        HashMap<String, Object> lowercaseContextData = Messages.lowercaseKeysForMap(cdata);
                        HashMap<String, Object> lowercaseVars = Messages.lowercaseKeysForMap(vars);
                        for (Message message : messages) {
                            if (message.shouldShowForVariables(lowercaseVars, lowercaseContextData, lifecycleData)) {
                                message.show();
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    protected static void setCurrentMessageFullscreen(MessageFullScreen message) {
        synchronized (_messageFullScreenMutex) {
            _messageFullScreen = message;
        }
    }

    protected static MessageFullScreen getCurrentFullscreenMessage() {
        MessageFullScreen messageFullScreen;
        synchronized (_messageFullScreenMutex) {
            messageFullScreen = _messageFullScreen;
        }
        return messageFullScreen;
    }

    protected static void resetAllInAppMessages() {
        StaticMethods.getMessagesExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Messages.4
            @Override // java.lang.Runnable
            public void run() {
                ArrayList<Message> messages = MobileConfig.getInstance().getInAppMessages();
                if (messages != null && messages.size() > 0) {
                    for (Message message : messages) {
                        message.isVisible = false;
                    }
                }
            }
        });
    }

    protected static void setSmallIconResourceId(int resourceId) {
        _smallIconResourceId = resourceId;
    }

    protected static int getSmallIconResourceId() {
        return _smallIconResourceId;
    }

    protected static void setLargeIconResourceId(int resourceId) {
        _largeIconResourceId = resourceId;
    }

    protected static int getLargeIconResourceId() {
        return _largeIconResourceId;
    }

    protected static Message getCurrentMessage() {
        Message message;
        synchronized (_currentMessageMutex) {
            message = _currentMessage;
        }
        return message;
    }

    protected static void setCurrentMessage(Message message) {
        synchronized (_currentMessageMutex) {
            _currentMessage = message;
        }
    }
}
