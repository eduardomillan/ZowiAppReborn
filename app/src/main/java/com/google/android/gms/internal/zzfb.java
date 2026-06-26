package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.text.TextUtils;
import com.google.android.gms.R;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzfb extends zzfh {
    private final Context mContext;
    private final Map<String, String> zzvS;
    private String zzzV;
    private long zzzW;
    private long zzzX;
    private String zzzY;
    private String zzzZ;

    public zzfb(zziz zzizVar, Map<String, String> map) {
        super(zzizVar, "createCalendarEvent");
        this.zzvS = map;
        this.mContext = zzizVar.zzgZ();
        zzec();
    }

    private String zzah(String str) {
        return TextUtils.isEmpty(this.zzvS.get(str)) ? "" : this.zzvS.get(str);
    }

    private long zzai(String str) {
        String str2 = this.zzvS.get(str);
        if (str2 == null) {
            return -1L;
        }
        try {
            return Long.parseLong(str2);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    private void zzec() {
        this.zzzV = zzah("description");
        this.zzzY = zzah("summary");
        this.zzzW = zzai("start_ticks");
        this.zzzX = zzai("end_ticks");
        this.zzzZ = zzah("location");
    }

    Intent createIntent() {
        Intent data = new Intent("android.intent.action.EDIT").setData(CalendarContract.Events.CONTENT_URI);
        data.putExtra("title", this.zzzV);
        data.putExtra("eventLocation", this.zzzZ);
        data.putExtra("description", this.zzzY);
        if (this.zzzW > -1) {
            data.putExtra("beginTime", this.zzzW);
        }
        if (this.zzzX > -1) {
            data.putExtra("endTime", this.zzzX);
        }
        data.setFlags(268435456);
        return data;
    }

    public void execute() {
        if (this.mContext == null) {
            zzak("Activity context is not available.");
            return;
        }
        if (!com.google.android.gms.ads.internal.zzp.zzbv().zzL(this.mContext).zzdb()) {
            zzak("This feature is not available on the device.");
            return;
        }
        AlertDialog.Builder builderZzK = com.google.android.gms.ads.internal.zzp.zzbv().zzK(this.mContext);
        builderZzK.setTitle(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.create_calendar_title, "Create calendar event"));
        builderZzK.setMessage(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.create_calendar_message, "Allow Ad to create a calendar event?"));
        builderZzK.setPositiveButton(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.accept, "Accept"), new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzfb.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                com.google.android.gms.ads.internal.zzp.zzbv().zzb(zzfb.this.mContext, zzfb.this.createIntent());
            }
        });
        builderZzK.setNegativeButton(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.decline, "Decline"), new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzfb.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                zzfb.this.zzak("Operation denied by user.");
            }
        });
        builderZzK.create().show();
    }
}
