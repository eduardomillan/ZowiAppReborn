package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class zzb extends RelativeLayout {
    private static final float[] zzwj = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
    private final RelativeLayout zzwk;
    private AnimationDrawable zzwl;

    public zzb(Context context, zza zzaVar) {
        super(context);
        zzx.zzw(zzaVar);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(11);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(zzwj, null, null));
        shapeDrawable.getPaint().setColor(zzaVar.getBackgroundColor());
        this.zzwk = new RelativeLayout(context);
        this.zzwk.setLayoutParams(layoutParams);
        zzp.zzbx().zza(this.zzwk, shapeDrawable);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        if (!TextUtils.isEmpty(zzaVar.getText())) {
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams3);
            textView.setId(1195835393);
            textView.setTypeface(Typeface.DEFAULT);
            textView.setText(zzaVar.getText());
            textView.setTextColor(zzaVar.getTextColor());
            textView.setTextSize(zzaVar.getTextSize());
            textView.setPadding(zzl.zzcF().zzb(context, 4), 0, zzl.zzcF().zzb(context, 4), 0);
            this.zzwk.addView(textView);
            layoutParams2.addRule(1, textView.getId());
        }
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams2);
        imageView.setId(1195835394);
        List<Drawable> listZzds = zzaVar.zzds();
        if (listZzds.size() > 1) {
            this.zzwl = new AnimationDrawable();
            Iterator<Drawable> it = listZzds.iterator();
            while (it.hasNext()) {
                this.zzwl.addFrame(it.next(), zzaVar.zzdt());
            }
            zzp.zzbx().zza(imageView, this.zzwl);
        } else if (listZzds.size() == 1) {
            imageView.setImageDrawable(listZzds.get(0));
        }
        this.zzwk.addView(imageView);
        addView(this.zzwk);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        if (this.zzwl != null) {
            this.zzwl.start();
        }
        super.onAttachedToWindow();
    }

    public ViewGroup zzdu() {
        return this.zzwk;
    }
}
