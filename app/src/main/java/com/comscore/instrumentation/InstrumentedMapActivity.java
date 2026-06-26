package com.comscore.instrumentation;

import android.os.Bundle;
import com.comscore.analytics.comScore;
import com.google.android.maps.MapActivity;

/* JADX INFO: loaded from: classes.dex */
public class InstrumentedMapActivity extends MapActivity {
    protected boolean isRouteDisplayed() {
        return false;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        comScore.setAppContext(getApplicationContext());
    }

    protected void onPause() {
        super.onPause();
        comScore.onExitForeground();
    }

    protected void onResume() {
        super.onResume();
        comScore.getCore().setCurrentActivityName(getClass().getSimpleName());
        comScore.onEnterForeground();
    }
}
