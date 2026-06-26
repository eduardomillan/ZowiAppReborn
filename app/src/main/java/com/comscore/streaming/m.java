package com.comscore.streaming;

import com.comscore.utils.CSLog;
import java.util.HashMap;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
class m extends TimerTask {
    final /* synthetic */ long a;
    final /* synthetic */ HashMap b;
    final /* synthetic */ StreamSenseVideoView c;

    m(StreamSenseVideoView streamSenseVideoView, long j, HashMap map) {
        this.c = streamSenseVideoView;
        this.a = j;
        this.b = map;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        CSLog.d("StreamSenseVideoView", "startEventTimer -> lastPosition:" + this.a + " currentPosition:" + this.c.getCurrentPlayerSafePosition());
        long j = Long.parseLong((String) this.b.get("ns_ts"));
        long currentPlayerSafePosition = this.c.getCurrentPlayerSafePosition() - this.a;
        long jCurrentTimeMillis = System.currentTimeMillis() - j;
        if (currentPlayerSafePosition < 500) {
            this.c.d();
            this.c.a((HashMap<String, String>) this.b);
            return;
        }
        long j2 = jCurrentTimeMillis - currentPlayerSafePosition;
        if (j2 > 0) {
            StreamSenseVideoView.a(this.c, j2);
            CSLog.d("StreamSenseVideoView", "addToBufferingTotal=" + j2);
            this.b.put("ns_ts", String.valueOf(j + j2));
        }
        this.b.put("ns_st_bt", String.valueOf(this.c.h));
        this.c.d();
        this.c.b(this.b, this.c.getCurrentPlayerSafePosition());
        this.c.g();
    }
}
