package com.adobe.mobile;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class MessageTemplateCallback extends Message {
    private static final String ADB_TEMPLATE_CALLBACK_BODY = "templatebody";
    private static final String ADB_TEMPLATE_CALLBACK_TIMEOUT = "timeout";
    private static final String ADB_TEMPLATE_CALLBACK_TYPE = "contenttype";
    private static final String ADB_TEMPLATE_CALLBACK_URL = "templateurl";
    private static final int ADB_TEMPLATE_TIMEOUT_DEFAULT = 2;
    private static final char ADB_TEMPLATE_TOKEN_END = '}';
    private static final char ADB_TEMPLATE_TOKEN_START = '{';
    private static final boolean[] tokenDataMask = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private HashMap<String, Object> _combinedVariablesCopy;
    protected String contentType;
    private final SecureRandom randomGen = new SecureRandom();
    protected String templateBody;
    protected String templateUrl;
    protected int timeout;

    MessageTemplateCallback() {
    }

    @Override // com.adobe.mobile.Message
    protected boolean initWithPayloadObject(JSONObject dictionary) {
        byte[] decodedBytes;
        if (dictionary == null || dictionary.length() <= 0 || !super.initWithPayloadObject(dictionary)) {
            return false;
        }
        try {
            JSONObject jsonPayload = dictionary.getJSONObject("payload");
            if (jsonPayload.length() <= 0) {
                StaticMethods.logDebugFormat("Data Callback - Unable to create data callback \"%s\", \"payload\" is empty", this.messageId);
                return false;
            }
            try {
                this.templateUrl = jsonPayload.getString(ADB_TEMPLATE_CALLBACK_URL);
                if (this.templateUrl.length() <= 0) {
                    StaticMethods.logDebugFormat("Data Callback - Unable to create data callback \"%s\", \"templateurl\" is empty", this.messageId);
                    return false;
                }
                try {
                    this.timeout = jsonPayload.getInt(ADB_TEMPLATE_CALLBACK_TIMEOUT);
                } catch (JSONException e) {
                    StaticMethods.logDebugFormat("Data Callback - Tried to read \"timeout\" for data callback, but found none.  Using default value of two (2) seconds", new Object[0]);
                    this.timeout = 2;
                }
                try {
                    String tempBody = jsonPayload.getString(ADB_TEMPLATE_CALLBACK_BODY);
                    if (tempBody != null && tempBody.length() > 0 && (decodedBytes = Base64.decode(tempBody, 0)) != null) {
                        String decodedBody = new String(decodedBytes, "UTF-8");
                        if (decodedBody.length() > 0) {
                            this.templateBody = decodedBody;
                        }
                    }
                } catch (UnsupportedEncodingException ex) {
                    StaticMethods.logDebugFormat("Data Callback - Failed to decode \"templatebody\" for data callback (%s).  This is not a required field", ex.getLocalizedMessage());
                } catch (IllegalArgumentException ex2) {
                    StaticMethods.logDebugFormat("Data Callback - Failed to decode \"templatebody\" for data callback (%s).  This is not a required field", ex2.getLocalizedMessage());
                } catch (JSONException e2) {
                    StaticMethods.logDebugFormat("Data Callback - Tried to read \"templatebody\" for data callback, but found none.  This is not a required field", new Object[0]);
                }
                if (this.templateBody != null && this.templateBody.length() > 0) {
                    try {
                        this.contentType = jsonPayload.getString(ADB_TEMPLATE_CALLBACK_TYPE);
                    } catch (JSONException e3) {
                        StaticMethods.logDebugFormat("Data Callback - Tried to read \"contenttype\" for data callback, but found none.  This is not a required field", new Object[0]);
                    }
                }
                return true;
            } catch (JSONException e4) {
                StaticMethods.logDebugFormat("Data Callback - Unable to create data callback \"%s\", \"templateurl\" is required", this.messageId);
                return false;
            }
        } catch (JSONException e5) {
            StaticMethods.logDebugFormat("Data Callback - Unable to create create data callback \"%s\", \"payload\" is required", this.messageId);
            return false;
        }
    }

    @Override // com.adobe.mobile.Message
    protected boolean shouldShowForVariables(Map<String, Object> vars, Map<String, Object> cdata, Map<String, Object> lifecycleData) {
        HashMap<String, Object> combinedVars = cdata != null ? new HashMap<>(cdata) : new HashMap<>();
        if (vars != null) {
            combinedVars.putAll(vars);
        }
        if (lifecycleData != null) {
            combinedVars.putAll(lifecycleData);
        }
        combinedVars.putAll(getMapForTemplatedTokens());
        this._combinedVariablesCopy = new HashMap<>(combinedVars);
        return super.shouldShowForVariables(vars, cdata, lifecycleData);
    }

    @Override // com.adobe.mobile.Message
    protected void show() {
        HashMap<String, String> urlExpansions = buildExpansionsForTokens(findTokensForExpansion(this.templateUrl));
        String expandedUrl = StaticMethods.expandTokens(this.templateUrl, urlExpansions);
        String expandedBody = null;
        if (this.templateBody != null && this.templateBody.length() > 0) {
            HashMap<String, String> bodyExpansions = buildExpansionsForTokens(findTokensForExpansion(this.templateBody));
            expandedBody = StaticMethods.expandTokens(this.templateBody, bodyExpansions);
        }
        StaticMethods.logDebugFormat("Data Callback - Attempting to send request (url:%s body:%s contentType:%s)", expandedUrl, expandedBody, this.contentType);
        ThirdPartyQueue.sharedInstance().queue(expandedUrl, expandedBody, this.contentType, StaticMethods.getTimeSince1970(), this.timeout);
    }

    private HashMap<String, Object> getMapForTemplatedTokens() {
        HashMap<String, Object> tokens = new HashMap<>(5);
        tokens.put("%sdkver%", "4.8.1-AN");
        tokens.put("%cachebust%", String.valueOf(this.randomGen.nextInt(100000000)));
        tokens.put("%timestampu%", String.valueOf(StaticMethods.getTimeSince1970()));
        tokens.put("%timestampz%", StaticMethods.getIso8601Date());
        tokens.put("%adid%", StaticMethods.getAdvertisingIdentifier());
        tokens.put("%pushid%", StaticMethods.getPushIdentifier());
        return tokens;
    }

    private HashMap<String, String> buildExpansionsForTokens(ArrayList<String> tokens) {
        HashMap<String, String> expansions = new HashMap<>(tokens.size());
        for (String token : tokens) {
            String cleanToken = token.substring(1, token.length() - 1).toLowerCase();
            Object tokenObject = this._combinedVariablesCopy.get(cleanToken);
            String tokenString = tokenObject == null ? "" : tokenObject.toString();
            expansions.put(token, StaticMethods.URLEncode(tokenString));
        }
        return expansions;
    }

    private ArrayList<String> findTokensForExpansion(String input) {
        if (input == null || input.length() <= 0) {
            return null;
        }
        ArrayList<String> foundList = new ArrayList<>(32);
        int inputLength = input.length();
        int i = 0;
        while (i < inputLength) {
            if (input.charAt(i) == '{') {
                int j = i + 1;
                while (j < inputLength && input.charAt(j) != '}') {
                    j++;
                }
                if (j != inputLength) {
                    String token = input.substring(i, j + 1);
                    if (tokenIsValid(token.substring(1, token.length() - 1))) {
                        foundList.add(token);
                        i = j;
                    }
                } else {
                    return foundList;
                }
            }
            i++;
        }
        return foundList;
    }

    private boolean tokenIsValid(String token) {
        try {
            byte[] utf8Token = token.getBytes("UTF-8");
            for (byte currentByte : utf8Token) {
                if (!tokenDataMask[currentByte & 255]) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException ex) {
            StaticMethods.logErrorFormat("Data Callback - Unable to validate token (%s)", ex.getLocalizedMessage());
            return false;
        }
    }
}
