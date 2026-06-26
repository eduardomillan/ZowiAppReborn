package com.comscore.instrumentation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.comscore.analytics.comScore;

/* JADX INFO: loaded from: classes.dex */
public class InstrumentedFragmentActivity extends FragmentActivity {
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        comScore.setAppContext(getApplicationContext());
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        comScore.onExitForeground();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        comScore.getCore().setCurrentActivityName(getClass().getSimpleName());
        comScore.onEnterForeground();
    }
}
