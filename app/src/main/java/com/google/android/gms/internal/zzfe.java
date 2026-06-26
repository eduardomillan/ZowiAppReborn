package com.google.android.gms.internal;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.URLUtil;
import com.google.android.gms.R;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzfe extends zzfh {
    private final Context mContext;
    private final Map<String, String> zzvS;

    public zzfe(zziz zzizVar, Map<String, String> map) {
        super(zzizVar, "storePicture");
        this.zzvS = map;
        this.mContext = zzizVar.zzgZ();
    }

    public void execute() {
        if (this.mContext == null) {
            zzak("Activity context is not available");
            return;
        }
        if (!com.google.android.gms.ads.internal.zzp.zzbv().zzL(this.mContext).zzcY()) {
            zzak("Feature is not supported by the device.");
            return;
        }
        final String str = this.zzvS.get("iurl");
        if (TextUtils.isEmpty(str)) {
            zzak("Image url cannot be empty.");
            return;
        }
        if (!URLUtil.isValidUrl(str)) {
            zzak("Invalid image url: " + str);
            return;
        }
        final String strZzaj = zzaj(str);
        if (!com.google.android.gms.ads.internal.zzp.zzbv().zzaB(strZzaj)) {
            zzak("Image type not recognized: " + strZzaj);
            return;
        }
        AlertDialog.Builder builderZzK = com.google.android.gms.ads.internal.zzp.zzbv().zzK(this.mContext);
        builderZzK.setTitle(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.store_picture_title, "Save image"));
        builderZzK.setMessage(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.store_picture_message, "Allow Ad to store image in Picture gallery?"));
        builderZzK.setPositiveButton(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.accept, "Accept"), new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzfe.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ((DownloadManager) zzfe.this.mContext.getSystemService("download")).enqueue(zzfe.this.zzg(str, strZzaj));
                } catch (IllegalStateException e) {
                    zzfe.this.zzak("Could not store picture.");
                }
            }
        });
        builderZzK.setNegativeButton(com.google.android.gms.ads.internal.zzp.zzby().zzd(R.string.decline, "Decline"), new DialogInterface.OnClickListener() { // from class: com.google.android.gms.internal.zzfe.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                zzfe.this.zzak("User canceled the download.");
            }
        });
        builderZzK.create().show();
    }

    String zzaj(String str) {
        return Uri.parse(str).getLastPathSegment();
    }

    DownloadManager.Request zzg(String str, String str2) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, str2);
        com.google.android.gms.ads.internal.zzp.zzbx().zza(request);
        return request;
    }
}
