package com.google.android.gms.ads.internal.formats;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.internal.zzco;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zziu;
import com.google.android.gms.internal.zziz;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class zzj extends zzco.zza implements View.OnClickListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private final FrameLayout zznZ;
    private final FrameLayout zzwU;
    private zzb zzwW;
    int zzwY;
    int zzwZ;
    private zzh zzwx;
    private final Object zzpd = new Object();
    private final Map<String, WeakReference<View>> zzwV = new HashMap();
    boolean zzwX = false;

    public zzj(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.zzwU = frameLayout;
        this.zznZ = frameLayout2;
        zziu.zza((View) this.zzwU, (ViewTreeObserver.OnGlobalLayoutListener) this);
        zziu.zza((View) this.zzwU, (ViewTreeObserver.OnScrollChangedListener) this);
        this.zzwU.setOnTouchListener(this);
    }

    int getMeasuredHeight() {
        return this.zzwU.getMeasuredHeight();
    }

    int getMeasuredWidth() {
        return this.zzwU.getMeasuredWidth();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        synchronized (this.zzpd) {
            if (this.zzwx == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            for (Map.Entry<String, WeakReference<View>> entry : this.zzwV.entrySet()) {
                View view2 = entry.getValue().get();
                Point pointZzj = zzj(view2);
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("width", zzp(view2.getWidth()));
                    jSONObject2.put("height", zzp(view2.getHeight()));
                    jSONObject2.put("x", zzp(pointZzj.x));
                    jSONObject2.put("y", zzp(pointZzj.y));
                    jSONObject.put(entry.getKey(), jSONObject2);
                } catch (JSONException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("Unable to get view rectangle for view " + entry.getKey());
                }
            }
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put("x", zzp(this.zzwY));
                jSONObject3.put("y", zzp(this.zzwZ));
            } catch (JSONException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Unable to get click location");
            }
            if (this.zzwW == null || !this.zzwW.zzdu().equals(view)) {
                this.zzwx.zza(view, this.zzwV, jSONObject, jSONObject3);
            } else {
                this.zzwx.zza("1007", jSONObject, jSONObject3);
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        synchronized (this.zzpd) {
            if (this.zzwX) {
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                if (measuredWidth != 0 && measuredHeight != 0) {
                    this.zznZ.setLayoutParams(new FrameLayout.LayoutParams(measuredWidth, measuredHeight));
                    this.zzwX = false;
                }
            }
            if (this.zzwx != null) {
                this.zzwx.zzi(this.zzwU);
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        synchronized (this.zzpd) {
            if (this.zzwx != null) {
                this.zzwx.zzi(this.zzwU);
            }
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this.zzpd) {
            if (this.zzwx != null) {
                Point pointZzc = zzc(motionEvent);
                this.zzwY = pointZzc.x;
                this.zzwZ = pointZzc.y;
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setLocation(pointZzc.x, pointZzc.y);
                this.zzwx.zzb(motionEventObtain);
                motionEventObtain.recycle();
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.zzco
    public com.google.android.gms.dynamic.zzd zzW(String str) {
        com.google.android.gms.dynamic.zzd zzdVarZzy;
        synchronized (this.zzpd) {
            WeakReference<View> weakReference = this.zzwV.get(str);
            zzdVarZzy = com.google.android.gms.dynamic.zze.zzy(weakReference == null ? null : weakReference.get());
        }
        return zzdVarZzy;
    }

    @Override // com.google.android.gms.internal.zzco
    public void zza(String str, com.google.android.gms.dynamic.zzd zzdVar) {
        View view = (View) com.google.android.gms.dynamic.zze.zzp(zzdVar);
        synchronized (this.zzpd) {
            if (view == null) {
                this.zzwV.remove(str);
            } else {
                this.zzwV.put(str, new WeakReference<>(view));
                view.setOnTouchListener(this);
                view.setOnClickListener(this);
            }
        }
    }

    @Override // com.google.android.gms.internal.zzco
    public void zzb(com.google.android.gms.dynamic.zzd zzdVar) {
        synchronized (this.zzpd) {
            this.zzwX = true;
            final zzh zzhVar = (zzh) com.google.android.gms.dynamic.zze.zzp(zzdVar);
            if ((this.zzwx instanceof zzg) && ((zzg) this.zzwx).zzdB()) {
                ((zzg) this.zzwx).zzb(zzhVar);
            } else {
                this.zzwx = zzhVar;
                if (this.zzwx instanceof zzg) {
                    ((zzg) this.zzwx).zzb((zzh) null);
                }
            }
            this.zznZ.removeAllViews();
            this.zzwW = zzf(zzhVar);
            if (this.zzwW != null) {
                this.zzwV.put("1007", new WeakReference<>(this.zzwW.zzdu()));
                this.zznZ.addView(this.zzwW);
            }
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.formats.zzj.1
                @Override // java.lang.Runnable
                public void run() {
                    zziz zzizVarZzdC = zzhVar.zzdC();
                    if (zzizVarZzdC != null) {
                        zzj.this.zznZ.addView(zzizVarZzdC.getView());
                    }
                }
            });
            zzhVar.zzh(this.zzwU);
        }
    }

    Point zzc(MotionEvent motionEvent) {
        this.zzwU.getLocationOnScreen(new int[2]);
        return new Point((int) (motionEvent.getRawX() - r0[0]), (int) (motionEvent.getRawY() - r0[1]));
    }

    zzb zzf(zzh zzhVar) {
        return zzhVar.zza(this);
    }

    Point zzj(View view) {
        if (this.zzwW == null || !this.zzwW.zzdu().equals(view)) {
            Point point = new Point();
            view.getGlobalVisibleRect(new Rect(), point);
            return point;
        }
        Point point2 = new Point();
        this.zzwU.getGlobalVisibleRect(new Rect(), point2);
        Point point3 = new Point();
        view.getGlobalVisibleRect(new Rect(), point3);
        return new Point(point3.x - point2.x, point3.y - point2.y);
    }

    int zzp(int i) {
        return zzl.zzcF().zzc(this.zzwx.getContext(), i);
    }
}
