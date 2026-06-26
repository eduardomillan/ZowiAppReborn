package com.adobe.mobile;

import android.R;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes.dex */
public class MessageFullScreenActivity extends AdobeMarketingActivity {
    protected MessageFullScreen message;

    @Override // com.adobe.mobile.AdobeMarketingActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        this.message = Messages.getCurrentFullscreenMessage();
        this.message.messageFullScreenActivity = this;
    }

    @Override // com.adobe.mobile.AdobeMarketingActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        try {
            final ViewGroup rootViewGroup = (ViewGroup) findViewById(R.id.content);
            if (rootViewGroup == null) {
                StaticMethods.logErrorFormat("Messages - unable to get root view group from os", new Object[0]);
                finish();
                overridePendingTransition(0, 0);
            } else {
                rootViewGroup.post(new Runnable() { // from class: com.adobe.mobile.MessageFullScreenActivity.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MessageFullScreenActivity.this.message.rootViewGroup = rootViewGroup;
                        MessageFullScreenActivity.this.message.showInRootViewGroup();
                    }
                });
            }
        } catch (NullPointerException ex) {
            StaticMethods.logWarningFormat("Messages - content view is in undefined state (%s)", ex.getMessage());
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        this.message.isVisible = false;
        this.message.viewed();
        finish();
        overridePendingTransition(0, 0);
    }
}
