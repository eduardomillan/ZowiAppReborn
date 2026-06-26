package com.adobe.mobile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.adobe.mobile.StaticMethods;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class MessageFullScreen extends Message {
    private static final String JSON_CONFIG_HTML = "html";
    private static final String MESSAGE_SCHEME = "adbinapp";
    private static final String MESSAGE_SCHEME_PATH_CANCEL = "cancel";
    private static final String MESSAGE_SCHEME_PATH_CONFIRM = "confirm";
    protected String html;
    protected Activity messageFullScreenActivity;
    protected String replacedHtml;
    protected ViewGroup rootViewGroup;
    private WebView webView;

    MessageFullScreen() {
    }

    @Override // com.adobe.mobile.Message
    protected boolean initWithPayloadObject(JSONObject dictionary) {
        if (dictionary == null || dictionary.length() <= 0 || !super.initWithPayloadObject(dictionary)) {
            return false;
        }
        try {
            JSONObject jsonPayload = dictionary.getJSONObject("payload");
            if (jsonPayload.length() <= 0) {
                StaticMethods.logDebugFormat("Messages - Unable to create fullscreen message \"%s\", payload is empty", this.messageId);
                return false;
            }
            try {
                this.html = jsonPayload.getString(JSON_CONFIG_HTML);
                if (this.html.length() <= 0) {
                    StaticMethods.logDebugFormat("Messages - Unable to create fullscreen message \"%s\", html is empty", this.messageId);
                    return false;
                }
                try {
                    JSONArray assetsArray = jsonPayload.getJSONArray("assets");
                    if (assetsArray != null && assetsArray.length() > 0) {
                        this.assets = new ArrayList<>();
                        int count = assetsArray.length();
                        for (int i = 0; i < count; i++) {
                            JSONArray currentAssetJson = assetsArray.getJSONArray(i);
                            if (currentAssetJson != null && currentAssetJson.length() > 0) {
                                ArrayList<String> currentAsset = new ArrayList<>();
                                int innerCount = currentAssetJson.length();
                                for (int j = 0; j < innerCount; j++) {
                                    currentAsset.add(currentAssetJson.getString(j));
                                }
                                this.assets.add(currentAsset);
                            }
                        }
                    }
                } catch (JSONException e) {
                    StaticMethods.logDebugFormat("Messages - No assets found for fullscreen message \"%s\"", this.messageId);
                }
                return true;
            } catch (JSONException e2) {
                StaticMethods.logDebugFormat("Messages - Unable to create fullscreen message \"%s\", html is required", this.messageId);
                return false;
            }
        } catch (JSONException e3) {
            StaticMethods.logDebugFormat("Messages - Unable to create fullscreen message \"%s\", payload is required", this.messageId);
            return false;
        }
    }

    protected void showInRootViewGroup() {
        int currentOrientation = StaticMethods.getCurrentOrientation();
        if (!this.isVisible || this.orientationWhenShown != currentOrientation) {
            this.orientationWhenShown = currentOrientation;
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new MessageFullScreenRunner(this));
        }
    }

    @Override // com.adobe.mobile.Message
    protected void show() {
        try {
            Activity currentActivity = StaticMethods.getCurrentActivity();
            super.show();
            Messages.setCurrentMessageFullscreen(this);
            HashMap<String, String> imageTokens = new HashMap<>();
            if (this.assets != null && this.assets.size() > 0) {
                for (ArrayList<String> currentAssetArray : this.assets) {
                    int currentAssetArrayCount = currentAssetArray.size();
                    if (currentAssetArrayCount > 0) {
                        String assetUrl = currentAssetArray.get(0);
                        String assetValue = null;
                        Iterator<String> it = currentAssetArray.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            String currentAsset = it.next();
                            File imageFile = RemoteDownload.getFileForCachedURL(currentAsset, "messageImages");
                            if (imageFile != null) {
                                assetValue = imageFile.toURI().toString();
                                break;
                            }
                        }
                        if (assetValue == null) {
                            String lastAsset = currentAssetArray.get(currentAssetArrayCount - 1);
                            boolean isLocalImage = !RemoteDownload.stringIsUrl(lastAsset);
                            if (isLocalImage) {
                                assetValue = lastAsset;
                            }
                        }
                        if (assetValue != null) {
                            imageTokens.put(assetUrl, assetValue);
                        }
                    }
                }
            }
            this.replacedHtml = StaticMethods.expandTokens(this.html, imageTokens);
            try {
                Intent fullscreen = new Intent(currentActivity.getApplicationContext(), (Class<?>) MessageFullScreenActivity.class);
                fullscreen.addFlags(65536);
                currentActivity.startActivity(fullscreen);
                currentActivity.overridePendingTransition(0, 0);
            } catch (ActivityNotFoundException ex) {
                StaticMethods.logWarningFormat("Messages - Must declare MessageFullScreenActivity in AndroidManifest.xml in order to show full screen messages (%s)", ex.getMessage());
            }
        } catch (StaticMethods.NullActivityException ex2) {
            StaticMethods.logErrorFormat(ex2.getMessage(), new Object[0]);
        }
    }

    private static class MessageFullScreenRunner implements Runnable {
        private MessageFullScreen message;

        protected MessageFullScreenRunner(MessageFullScreen message) {
            this.message = message;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.message.webView = new WebView(this.message.messageFullScreenActivity);
                this.message.webView.setVerticalScrollBarEnabled(false);
                this.message.webView.setHorizontalScrollBarEnabled(false);
                this.message.webView.setBackgroundColor(0);
                this.message.webView.setWebViewClient(new MessageFullScreenWebViewClient(this.message));
                WebSettings settings = this.message.webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setAllowFileAccess(true);
                settings.setDefaultTextEncodingName("UTF-8");
                this.message.webView.loadDataWithBaseURL("file:///android_asset/", this.message.replacedHtml, "text/html", "UTF-8", null);
                if (this.message.rootViewGroup == null) {
                    StaticMethods.logErrorFormat("Messages - unable to get root view group from os", new Object[0]);
                    MessageFullScreen.killMessageActivity(this.message);
                    return;
                }
                int width = this.message.rootViewGroup.getMeasuredWidth();
                int height = this.message.rootViewGroup.getMeasuredHeight();
                if (width == 0 || height == 0) {
                    StaticMethods.logErrorFormat("Messages - root view hasn't been measured, cannot show message", new Object[0]);
                    MessageFullScreen.killMessageActivity(this.message);
                    return;
                }
                if (this.message.isVisible) {
                    this.message.rootViewGroup.addView(this.message.webView, width, height);
                } else {
                    Animation translate = new TranslateAnimation(0.0f, 0.0f, height, 0.0f);
                    translate.setDuration(300L);
                    this.message.webView.setAnimation(translate);
                    this.message.rootViewGroup.addView(this.message.webView, width, height);
                }
                this.message.isVisible = true;
            } catch (Exception ex) {
                StaticMethods.logDebugFormat("Messages - Failed to show full screen message (%s)", ex.getMessage());
            }
        }
    }

    private static class MessageFullScreenWebViewClient extends WebViewClient {
        private MessageFullScreen message;

        protected MessageFullScreenWebViewClient(MessageFullScreen message) {
            this.message = message;
        }

        private void dismissMessage(WebView view) {
            if (this.message.rootViewGroup == null) {
                StaticMethods.logErrorFormat("Messages - unable to get root view group from os", new Object[0]);
                return;
            }
            Animation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, this.message.rootViewGroup.getMeasuredHeight());
            animation.setDuration(300L);
            animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.adobe.mobile.MessageFullScreen.MessageFullScreenWebViewClient.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation2) {
                    MessageFullScreen.killMessageActivity(MessageFullScreenWebViewClient.this.message);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation2) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation2) {
                }
            });
            view.setAnimation(animation);
            this.message.rootViewGroup.removeView(view);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(MessageFullScreen.MESSAGE_SCHEME)) {
                if (url.contains(MessageFullScreen.MESSAGE_SCHEME_PATH_CANCEL)) {
                    this.message.viewed();
                    dismissMessage(view);
                } else if (url.contains(MessageFullScreen.MESSAGE_SCHEME_PATH_CONFIRM)) {
                    this.message.clickedThrough();
                    dismissMessage(view);
                    int customUrlRange = url.indexOf("url=");
                    if (customUrlRange >= 0) {
                        String customUrl = url.substring(customUrlRange + 4);
                        HashMap<String, String> urlTokens = new HashMap<>();
                        urlTokens.put("{userId}", StaticMethods.getVisitorID() == null ? "" : StaticMethods.getVisitorID());
                        urlTokens.put("{trackingId}", StaticMethods.getAID() == null ? "" : StaticMethods.getAID());
                        urlTokens.put("{messageId}", this.message.messageId);
                        urlTokens.put("{lifetimeValue}", AnalyticsTrackLifetimeValueIncrease.getLifetimeValue().toString());
                        String customUrl2 = StaticMethods.expandTokens(customUrl, urlTokens);
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setData(Uri.parse(customUrl2));
                            this.message.messageFullScreenActivity.startActivity(intent);
                        } catch (Exception ex) {
                            StaticMethods.logDebugFormat("Messages - unable to launch intent from full screen message (%s)", ex.getMessage());
                        }
                    }
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void killMessageActivity(MessageFullScreen message) {
        message.messageFullScreenActivity.finish();
        message.messageFullScreenActivity.overridePendingTransition(0, 0);
        message.isVisible = false;
    }
}
