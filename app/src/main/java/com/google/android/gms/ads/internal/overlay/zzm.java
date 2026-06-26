package com.google.android.gms.ads.internal.overlay;

import android.R;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzm extends FrameLayout implements View.OnClickListener {
    private final ImageButton zzBW;
    private final zzo zzBX;

    public zzm(Context context, int i, zzo zzoVar) {
        super(context);
        this.zzBX = zzoVar;
        setOnClickListener(this);
        this.zzBW = new ImageButton(context);
        this.zzBW.setImageResource(R.drawable.btn_dialog);
        this.zzBW.setBackgroundColor(0);
        this.zzBW.setOnClickListener(this);
        this.zzBW.setPadding(0, 0, 0, 0);
        this.zzBW.setContentDescription("Interstitial close button");
        int iZzb = com.google.android.gms.ads.internal.client.zzl.zzcF().zzb(context, i);
        addView(this.zzBW, new FrameLayout.LayoutParams(iZzb, iZzb, 17));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.zzBX != null) {
            this.zzBX.zzeE();
        }
    }

    public void zza(boolean z, boolean z2) {
        if (!z2) {
            this.zzBW.setVisibility(0);
        } else if (z) {
            this.zzBW.setVisibility(4);
        } else {
            this.zzBW.setVisibility(8);
        }
    }
}
