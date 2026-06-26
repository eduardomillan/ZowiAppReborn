package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzfp extends Handler {
    private final zzfo zzCq;

    public zzfp(Context context) {
        this(new zzfq(context));
    }

    public zzfp(zzfo zzfoVar) {
        this.zzCq = zzfoVar;
    }

    private void zzc(JSONObject jSONObject) {
        try {
            this.zzCq.zza(jSONObject.getString("request_id"), jSONObject.getString("base_url"), jSONObject.getString("html"));
        } catch (Exception e) {
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        try {
            Bundle data = msg.getData();
            if (data == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject(data.getString("data"));
            if ("fetch_html".equals(jSONObject.getString("message_name"))) {
                zzc(jSONObject);
            }
        } catch (Exception e) {
        }
    }
}
