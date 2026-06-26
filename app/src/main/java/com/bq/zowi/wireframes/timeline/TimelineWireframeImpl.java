package com.bq.zowi.wireframes.timeline;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.views.interactive.home.HomeViewActivity;
import com.bq.zowi.wireframes.interactive.InteractiveWireframeImpl;

/* JADX INFO: loaded from: classes.dex */
public class TimelineWireframeImpl extends InteractiveWireframeImpl implements TimelineWireframe {
    public TimelineWireframeImpl(FragmentActivity activity) {
        super(activity);
    }

    @Override // com.bq.zowi.wireframes.timeline.TimelineWireframe
    public void presentHome() {
        Intent i = new Intent(this.activity, (Class<?>) HomeViewActivity.class);
        i.setFlags(603979776);
        this.activity.startActivity(i);
        this.activity.finish();
    }
}
