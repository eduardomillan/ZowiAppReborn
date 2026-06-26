package com.adobe.mobile;

import android.content.SharedPreferences;
import com.adobe.mobile.Messages;
import com.adobe.mobile.StaticMethods;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class Message {
    protected static final String JSON_CONFIG_ASSETS = "assets";
    private static final String JSON_CONFIG_AUDIENCES = "audiences";
    private static final String JSON_CONFIG_END_DATE = "endDate";
    private static final String JSON_CONFIG_MESSAGE_ID = "messageId";
    private static final String JSON_CONFIG_SHOW_OFFLINE = "showOffline";
    private static final String JSON_CONFIG_SHOW_RULE = "showRule";
    private static final String JSON_CONFIG_START_DATE = "startDate";
    private static final String JSON_CONFIG_TEMPLATE = "template";
    private static final String JSON_CONFIG_TRIGGERS = "triggers";
    private static final boolean JSON_DEFAULT_SHOW_OFFLINE = false;
    private static final String MESSAGE_ENUM_STRING_UNKNOWN = "unknown";
    protected static final String MESSAGE_IMAGE_CACHE_DIR = "messageImages";
    protected static final String MESSAGE_JSON_PAYLOAD = "payload";
    private static final String MESSAGE_SHOW_RULE_STRING_ALWAYS = "always";
    private static final String MESSAGE_SHOW_RULE_STRING_ONCE = "once";
    private static final String MESSAGE_SHOW_RULE_STRING_UNTIL_CLICK = "untilClick";
    private static final String MESSAGE_TEMPLATE_STRING_ALERT = "alert";
    private static final String MESSAGE_TEMPLATE_STRING_CALLBACK = "callback";
    private static final String MESSAGE_TEMPLATE_STRING_FULLSCREEN = "fullscreen";
    private static final String MESSAGE_TEMPLATE_STRING_LOCAL_NOTIFICATION = "local";
    private static final String SHARED_PREFERENCES_BLACK_LIST = "messagesBlackList";
    private static HashMap<String, Integer> _blacklist;
    protected ArrayList<ArrayList<String>> assets;
    protected ArrayList<MessageMatcher> audiences;
    protected Date endDate;
    protected boolean isVisible;
    protected String messageId;
    protected int orientationWhenShown;
    protected boolean showOffline;
    protected Messages.MessageShowRule showRule;
    protected Date startDate;
    protected ArrayList<MessageMatcher> triggers;
    private static final Long JSON_DEFAULT_START_DATE = 0L;
    private static final Map<String, Class> _messageTypeDictionary = new HashMap<String, Class>() { // from class: com.adobe.mobile.Message.1
        {
            put(Message.MESSAGE_TEMPLATE_STRING_LOCAL_NOTIFICATION, MessageLocalNotification.class);
            put(Message.MESSAGE_TEMPLATE_STRING_ALERT, MessageAlert.class);
            put(Message.MESSAGE_TEMPLATE_STRING_FULLSCREEN, MessageFullScreen.class);
            put(Message.MESSAGE_TEMPLATE_STRING_CALLBACK, MessageTemplateCallback.class);
        }
    };
    private static final Object _blacklistMutex = new Object();
    private static final Map<String, Messages.MessageShowRule> _showRuleEnumDictionary = new HashMap<String, Messages.MessageShowRule>() { // from class: com.adobe.mobile.Message.2
        {
            put("unknown", Messages.MessageShowRule.MESSAGE_SHOW_RULE_UNKNOWN);
            put(Message.MESSAGE_SHOW_RULE_STRING_ALWAYS, Messages.MessageShowRule.MESSAGE_SHOW_RULE_ALWAYS);
            put(Message.MESSAGE_SHOW_RULE_STRING_ONCE, Messages.MessageShowRule.MESSAGE_SHOW_RULE_ONCE);
            put(Message.MESSAGE_SHOW_RULE_STRING_UNTIL_CLICK, Messages.MessageShowRule.MESSAGE_SHOW_RULE_UNTIL_CLICK);
        }
    };

    Message() {
    }

    protected static Message messageWithJsonObject(JSONObject dictionary) {
        String messageTypeString = "";
        try {
            messageTypeString = dictionary.getString(JSON_CONFIG_TEMPLATE);
            Class messageClass = _messageTypeDictionary.get(messageTypeString);
            Message message = (Message) messageClass.newInstance();
            boolean initComplete = message.initWithPayloadObject(dictionary);
            if (!initComplete) {
                message = null;
            }
            return message;
        } catch (IllegalAccessException ex) {
            StaticMethods.logWarningFormat("Messages - unable to create instance of message (%s)", ex.getMessage());
            return null;
        } catch (InstantiationException ex2) {
            StaticMethods.logWarningFormat("Messages - unable to create instance of message (%s)", ex2.getMessage());
            return null;
        } catch (NullPointerException e) {
            StaticMethods.logWarningFormat("Messages - invalid template specified for message (%s)", messageTypeString);
            return null;
        } catch (JSONException e2) {
            StaticMethods.logWarningFormat("Messages - template is required for in-app message", new Object[0]);
            return null;
        }
    }

    protected boolean initWithPayloadObject(JSONObject dictionary) {
        if (dictionary == null || dictionary.length() == 0) {
            return false;
        }
        try {
            this.messageId = dictionary.getString(JSON_CONFIG_MESSAGE_ID);
            if (this.messageId.length() <= 0) {
                StaticMethods.logWarningFormat("Messages - Unable to create message, messageId is empty", new Object[0]);
                return false;
            }
            try {
                String showRuleString = dictionary.getString(JSON_CONFIG_SHOW_RULE);
                this.showRule = messageShowRuleFromString(showRuleString);
                if (this.showRule == null || this.showRule == Messages.MessageShowRule.MESSAGE_SHOW_RULE_UNKNOWN) {
                    StaticMethods.logWarningFormat("Messages - Unable to create message \"%s\", showRule not valid (%s)", this.messageId, showRuleString);
                    return false;
                }
                try {
                    long startDateConfig = dictionary.getLong(JSON_CONFIG_START_DATE);
                    this.startDate = new Date(1000 * startDateConfig);
                } catch (JSONException e) {
                    StaticMethods.logDebugFormat("Messages - Tried to read startDate from message \"%s\" but none found. Using default value", this.messageId);
                    this.startDate = new Date(JSON_DEFAULT_START_DATE.longValue() * 1000);
                }
                try {
                    long endDateConfig = dictionary.getLong(JSON_CONFIG_END_DATE);
                    this.endDate = new Date(1000 * endDateConfig);
                } catch (JSONException e2) {
                    StaticMethods.logDebugFormat("Messages - Tried to read endDate from message \"%s\" but none found. Using default value", this.messageId);
                }
                try {
                    this.showOffline = dictionary.getBoolean(JSON_CONFIG_SHOW_OFFLINE);
                } catch (JSONException e3) {
                    StaticMethods.logDebugFormat("Messages - Tried to read showOffline from message \"%s\" but none found. Using default value", this.messageId);
                    this.showOffline = false;
                }
                this.audiences = new ArrayList<>();
                try {
                    JSONArray jsonAudiences = dictionary.getJSONArray(JSON_CONFIG_AUDIENCES);
                    int audienceCount = jsonAudiences.length();
                    for (int i = 0; i < audienceCount; i++) {
                        JSONObject matcher = jsonAudiences.getJSONObject(i);
                        this.audiences.add(MessageMatcher.messageMatcherWithJsonObject(matcher));
                    }
                } catch (JSONException ex) {
                    StaticMethods.logDebugFormat("Messages - failed to read audience for message \"%s\", error: %s", this.messageId, ex.getMessage());
                }
                this.triggers = new ArrayList<>();
                try {
                    JSONArray jsonTriggers = dictionary.getJSONArray(JSON_CONFIG_TRIGGERS);
                    int triggersCount = jsonTriggers.length();
                    for (int i2 = 0; i2 < triggersCount; i2++) {
                        JSONObject matcher2 = jsonTriggers.getJSONObject(i2);
                        this.triggers.add(MessageMatcher.messageMatcherWithJsonObject(matcher2));
                    }
                } catch (JSONException ex2) {
                    StaticMethods.logDebugFormat("Messages - failed to read trigger for message \"%s\", error: %s", this.messageId, ex2.getMessage());
                }
                if (this.triggers.size() <= 0) {
                    StaticMethods.logWarningFormat("Messages - Unable to load message \"%s\" - at least one valid trigger is required for a message", this.messageId);
                    return false;
                }
                this.isVisible = false;
                return true;
            } catch (JSONException e4) {
                StaticMethods.logWarningFormat("Messages - Unable to create message \"%s\", showRule is required", this.messageId);
                return false;
            }
        } catch (JSONException e5) {
            StaticMethods.logWarningFormat("Messages - Unable to create message, messageId is required", new Object[0]);
            return false;
        }
    }

    protected void blacklist() {
        synchronized (_blacklistMutex) {
            if (_blacklist == null) {
                _blacklist = loadBlacklist();
            }
            _blacklist.put(this.messageId, Integer.valueOf(this.showRule.getValue()));
            StaticMethods.logDebugFormat("Messages - adding message \"%s\" to blacklist", this.messageId);
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.putString(SHARED_PREFERENCES_BLACK_LIST, stringFromMap(_blacklist));
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Messages - Error persisting blacklist map (%s).", e.getMessage());
            }
        }
    }

    protected void removeFromBlacklist() {
        if (isBlacklisted()) {
            synchronized (_blacklistMutex) {
                _blacklist.remove(this.messageId);
                StaticMethods.logDebugFormat("Messages - removing message \"%s\" from blacklist", this.messageId);
                try {
                    SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                    editor.putString(SHARED_PREFERENCES_BLACK_LIST, stringFromMap(_blacklist));
                    editor.commit();
                } catch (StaticMethods.NullContextException e) {
                    StaticMethods.logErrorFormat("Messages - Error persisting blacklist map (%s).", e.getMessage());
                }
            }
        }
    }

    protected boolean isBlacklisted() {
        boolean z;
        synchronized (_blacklistMutex) {
            if (_blacklist == null) {
                _blacklist = loadBlacklist();
            }
            z = _blacklist.get(this.messageId) != null;
        }
        return z;
    }

    protected HashMap<String, Integer> loadBlacklist() {
        HashMap<String, Integer> mapMapFromString;
        try {
            String blackListString = StaticMethods.getSharedPreferences().getString(SHARED_PREFERENCES_BLACK_LIST, null);
            if (blackListString == null) {
                mapMapFromString = new HashMap<>();
            } else {
                mapMapFromString = mapFromString(blackListString);
            }
            return mapMapFromString;
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logDebugFormat("Messaging - Unable to get shared preferences while loading blacklist. (%s)", e.getMessage());
            return new HashMap<>();
        }
    }

    protected boolean shouldShowForVariables(Map<String, Object> vars, Map<String, Object> cdata, Map<String, Object> lifecycleData) {
        if (this.isVisible && this.orientationWhenShown != StaticMethods.getCurrentOrientation() && (this instanceof MessageAlert)) {
            return true;
        }
        if (Messages.getCurrentMessage() != null && !(this instanceof MessageLocalNotification) && !(this instanceof MessageTemplateCallback)) {
            return false;
        }
        if ((vars == null || vars.size() <= 0) && (cdata == null || cdata.size() <= 0)) {
            return false;
        }
        if (isBlacklisted()) {
            return false;
        }
        if (!MobileConfig.getInstance().networkConnectivity() && !this.showOffline) {
            return false;
        }
        Date now = new Date(StaticMethods.getTimeSince1970() * 1000);
        if (now.before(this.startDate)) {
            return false;
        }
        if (this.endDate != null && now.after(this.endDate)) {
            return false;
        }
        for (MessageMatcher matcher : this.audiences) {
            if (!matcher.matchesInMaps(lifecycleData)) {
                return false;
            }
        }
        Map<String, Object> cdataCleaned = StaticMethods.cleanContextDataDictionary(cdata);
        for (MessageMatcher matcher2 : this.triggers) {
            if (!matcher2.matchesInMaps(vars, cdataCleaned)) {
                return false;
            }
        }
        return true;
    }

    protected void show() {
        this.orientationWhenShown = StaticMethods.getCurrentOrientation();
        Messages.setCurrentMessage(this);
    }

    protected void viewed() {
        HashMap<String, Object> contextData = new HashMap<>();
        contextData.put("a.message.id", this.messageId);
        contextData.put("a.message.viewed", 1);
        AnalyticsTrackInternal.trackInternal("In-App Message", contextData, StaticMethods.getTimeSince1970());
        if (this.showRule == Messages.MessageShowRule.MESSAGE_SHOW_RULE_ONCE) {
            blacklist();
        }
        Messages.setCurrentMessage(null);
    }

    protected void clickedThrough() {
        HashMap<String, Object> contextData = new HashMap<>();
        contextData.put("a.message.id", this.messageId);
        contextData.put("a.message.clicked", 1);
        AnalyticsTrackInternal.trackInternal("In-App Message", contextData, StaticMethods.getTimeSince1970());
        if (this.showRule == Messages.MessageShowRule.MESSAGE_SHOW_RULE_ONCE || this.showRule == Messages.MessageShowRule.MESSAGE_SHOW_RULE_UNTIL_CLICK) {
            blacklist();
        }
        Messages.setCurrentMessage(null);
    }

    protected String description() {
        return "Message ID: " + this.messageId + "; Show Rule: " + this.showRule.toString() + "; Blacklisted: " + isBlacklisted();
    }

    private static Messages.MessageShowRule messageShowRuleFromString(String showRule) {
        return _showRuleEnumDictionary.get(showRule);
    }

    private HashMap<String, Integer> mapFromString(String string) {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(string);
            Iterator<String> itKeys = obj.keys();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                map.put(key, Integer.valueOf(obj.getInt(key)));
            }
        } catch (JSONException ex) {
            StaticMethods.logErrorFormat("Messages- Unable to deserialize blacklist(%s)", ex.getMessage());
        }
        return map;
    }

    private String stringFromMap(Map<String, Integer> map) {
        JSONObject obj = new JSONObject(map);
        return obj.toString();
    }
}
