package com.adobe.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
abstract class MessageMatcher {
    private static final String MESSAGE_JSON_KEY = "key";
    private static final String MESSAGE_JSON_MATCHES = "matches";
    private static final String MESSAGE_JSON_VALUES = "values";
    private static final String MESSAGE_MATCHER_STRING_CONTAINS = "co";
    private static final String MESSAGE_MATCHER_STRING_ENDS_WITH = "ew";
    private static final String MESSAGE_MATCHER_STRING_EQUALS = "eq";
    private static final String MESSAGE_MATCHER_STRING_EXISTS = "ex";
    private static final String MESSAGE_MATCHER_STRING_GREATER_THAN = "gt";
    private static final String MESSAGE_MATCHER_STRING_GREATER_THAN_OR_EQUALS = "ge";
    private static final String MESSAGE_MATCHER_STRING_LESS_THAN = "lt";
    private static final String MESSAGE_MATCHER_STRING_LESS_THAN_OR_EQUALS = "le";
    private static final String MESSAGE_MATCHER_STRING_NOT_CONTAINS = "nc";
    private static final String MESSAGE_MATCHER_STRING_NOT_EQUALS = "ne";
    private static final String MESSAGE_MATCHER_STRING_NOT_EXISTS = "nx";
    private static final String MESSAGE_MATCHER_STRING_STARTS_WITH = "sw";
    private static final Map<String, Class> _messageMatcherDictionary = new HashMap<String, Class>() { // from class: com.adobe.mobile.MessageMatcher.1
        {
            put(MessageMatcher.MESSAGE_MATCHER_STRING_EQUALS, MessageMatcherEquals.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_NOT_EQUALS, MessageMatcherNotEquals.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_GREATER_THAN, MessageMatcherGreaterThan.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_GREATER_THAN_OR_EQUALS, MessageMatcherGreaterThanOrEqual.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_LESS_THAN, MessageMatcherLessThan.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_LESS_THAN_OR_EQUALS, MessageMatcherLessThanOrEqual.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_CONTAINS, MessageMatcherContains.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_NOT_CONTAINS, MessageMatcherNotContains.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_STARTS_WITH, MessageMatcherStartsWith.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_ENDS_WITH, MessageMatcherEndsWith.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_EXISTS, MessageMatcherExists.class);
            put(MessageMatcher.MESSAGE_MATCHER_STRING_NOT_EXISTS, MessageMatcherNotExists.class);
        }
    };
    protected String key;
    protected ArrayList<Object> values;

    MessageMatcher() {
    }

    protected static MessageMatcher messageMatcherWithJsonObject(JSONObject dictionary) {
        String matcherString;
        MessageMatcher matcher = null;
        try {
            matcherString = dictionary.getString(MESSAGE_JSON_MATCHES);
            if (matcherString != null && matcherString.length() <= 0) {
                StaticMethods.logWarningFormat("Messages - message matcher type is empty", new Object[0]);
            }
        } catch (JSONException e) {
            matcherString = "UNKNOWN";
            StaticMethods.logWarningFormat("Messages - message matcher type is required", new Object[0]);
        }
        Class<MessageMatcherUnknown> cls = _messageMatcherDictionary.get(matcherString);
        if (cls == null) {
            cls = MessageMatcherUnknown.class;
            StaticMethods.logWarningFormat("Messages - message matcher type \"%s\" is invalid", matcherString);
        }
        try {
            matcher = cls.newInstance();
        } catch (IllegalAccessException ex) {
            StaticMethods.logErrorFormat("Messages - Error creating matcher (%s)", ex.getMessage());
        } catch (InstantiationException ex2) {
            StaticMethods.logErrorFormat("Messages - Error creating matcher (%s)", ex2.getMessage());
        }
        if (matcher != null) {
            try {
                matcher.key = dictionary.getString(MESSAGE_JSON_KEY).toLowerCase();
                if (matcher.key != null && matcher.key.length() <= 0) {
                    StaticMethods.logWarningFormat("Messages - error creating matcher, key is empty", new Object[0]);
                }
            } catch (NullPointerException e2) {
                StaticMethods.logWarningFormat("Messages - error creating matcher, key is required", new Object[0]);
            } catch (JSONException e3) {
                StaticMethods.logWarningFormat("Messages - error creating matcher, key is required", new Object[0]);
            }
            try {
                matcher.values = new ArrayList<>();
                if (!(matcher instanceof MessageMatcherExists)) {
                    JSONArray jsonArray = dictionary.getJSONArray(MESSAGE_JSON_VALUES);
                    int arrayLength = jsonArray.length();
                    for (int i = 0; i < arrayLength; i++) {
                        matcher.values.add(jsonArray.get(i));
                    }
                    if (matcher.values.size() == 0) {
                        StaticMethods.logWarningFormat("Messages - error creating matcher, values is empty", new Object[0]);
                    }
                }
            } catch (JSONException e4) {
                StaticMethods.logWarningFormat("Messages - error creating matcher, values is required", new Object[0]);
            }
        }
        return matcher;
    }

    protected boolean matchesInMaps(Map<String, Object>... maps) {
        if (maps == null || maps.length <= 0) {
            return false;
        }
        Object value = null;
        int length = maps.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Map<String, Object> map = maps[i];
            if (map == null || !map.containsKey(this.key)) {
                i++;
            } else {
                value = map.get(this.key);
                break;
            }
        }
        return value != null && matches(value);
    }

    protected boolean matches(Object value) {
        return false;
    }

    protected Double tryParseDouble(Object value) {
        try {
            return Double.valueOf(value.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
