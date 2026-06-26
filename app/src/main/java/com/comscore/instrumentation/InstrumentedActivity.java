package com.comscore.instrumentation;

import android.app.Activity;
import android.os.Bundle;
import com.comscore.analytics.comScore;

/* JADX INFO: loaded from: classes.dex */
public class InstrumentedActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        comScore.setAppContext(getApplicationContext());
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        comScore.onExitForeground();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        comScore.getCore().setCurrentActivityName(getClass().getSimpleName());
        comScore.onEnterForeground();
    }
}
