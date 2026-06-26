package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public abstract class zza<T extends LifecycleDelegate> {
    private T zzapn;
    private Bundle zzapo;
    private LinkedList<InterfaceC0044zza> zzapp;
    private final zzf<T> zzapq = (zzf<T>) new zzf<T>() { // from class: com.google.android.gms.dynamic.zza.1
        @Override // com.google.android.gms.dynamic.zzf
        public void zza(T t) {
            zza.this.zzapn = t;
            Iterator it = zza.this.zzapp.iterator();
            while (it.hasNext()) {
                ((InterfaceC0044zza) it.next()).zzb(zza.this.zzapn);
            }
            zza.this.zzapp.clear();
            zza.this.zzapo = null;
        }
    };

    /* JADX INFO: renamed from: com.google.android.gms.dynamic.zza$zza, reason: collision with other inner class name */
    private interface InterfaceC0044zza {
        int getState();

        void zzb(LifecycleDelegate lifecycleDelegate);
    }

    private void zza(Bundle bundle, InterfaceC0044zza interfaceC0044zza) {
        if (this.zzapn != null) {
            interfaceC0044zza.zzb(this.zzapn);
            return;
        }
        if (this.zzapp == null) {
            this.zzapp = new LinkedList<>();
        }
        this.zzapp.add(interfaceC0044zza);
        if (bundle != null) {
            if (this.zzapo == null) {
                this.zzapo = (Bundle) bundle.clone();
            } else {
                this.zzapo.putAll(bundle);
            }
        }
        zza(this.zzapq);
    }

    public static void zzb(FrameLayout frameLayout) {
        final Context context = frameLayout.getContext();
        final int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        String strZzc = com.google.android.gms.common.internal.zzg.zzc(context, iIsGooglePlayServicesAvailable, GooglePlayServicesUtil.zzaf(context));
        String strZzh = com.google.android.gms.common.internal.zzg.zzh(context, iIsGooglePlayServicesAvailable);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        TextView textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        textView.setText(strZzc);
        linearLayout.addView(textView);
        if (strZzh != null) {
            Button button = new Button(context);
            button.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            button.setText(strZzh);
            linearLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.gms.dynamic.zza.5
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    context.startActivity(GooglePlayServicesUtil.zzbj(iIsGooglePlayServicesAvailable));
                }
            });
        }
    }

    private void zzer(int i) {
        while (!this.zzapp.isEmpty() && this.zzapp.getLast().getState() >= i) {
            this.zzapp.removeLast();
        }
    }

    public void onCreate(final Bundle savedInstanceState) {
        zza(savedInstanceState, new InterfaceC0044zza() { // from class: com.google.android.gms.dynamic.zza.3
            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public int getState() {
                return 1;
            }

            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzapn.onCreate(savedInstanceState);
            }
        });
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        zza(savedInstanceState, new InterfaceC0044zza() { // from class: com.google.android.gms.dynamic.zza.4
            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public int getState() {
                return 2;
            }

            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                frameLayout.removeAllViews();
                frameLayout.addView(zza.this.zzapn.onCreateView(inflater, container, savedInstanceState));
            }
        });
        if (this.zzapn == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.zzapn != null) {
            this.zzapn.onDestroy();
        } else {
            zzer(1);
        }
    }

    public void onDestroyView() {
        if (this.zzapn != null) {
            this.zzapn.onDestroyView();
        } else {
            zzer(2);
        }
    }

    public void onInflate(final Activity activity, final Bundle attrs, final Bundle savedInstanceState) {
        zza(savedInstanceState, new InterfaceC0044zza() { // from class: com.google.android.gms.dynamic.zza.2
            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public int getState() {
                return 0;
            }

            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzapn.onInflate(activity, attrs, savedInstanceState);
            }
        });
    }

    public void onLowMemory() {
        if (this.zzapn != null) {
            this.zzapn.onLowMemory();
        }
    }

    public void onPause() {
        if (this.zzapn != null) {
            this.zzapn.onPause();
        } else {
            zzer(5);
        }
    }

    public void onResume() {
        zza((Bundle) null, new InterfaceC0044zza() { // from class: com.google.android.gms.dynamic.zza.7
            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public int getState() {
                return 5;
            }

            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzapn.onResume();
            }
        });
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.zzapn != null) {
            this.zzapn.onSaveInstanceState(outState);
        } else if (this.zzapo != null) {
            outState.putAll(this.zzapo);
        }
    }

    public void onStart() {
        zza((Bundle) null, new InterfaceC0044zza() { // from class: com.google.android.gms.dynamic.zza.6
            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public int getState() {
                return 4;
            }

            @Override // com.google.android.gms.dynamic.zza.InterfaceC0044zza
            public void zzb(LifecycleDelegate lifecycleDelegate) {
                zza.this.zzapn.onStart();
            }
        });
    }

    public void onStop() {
        if (this.zzapn != null) {
            this.zzapn.onStop();
        } else {
            zzer(4);
        }
    }

    protected void zza(FrameLayout frameLayout) {
        zzb(frameLayout);
    }

    protected abstract void zza(zzf<T> zzfVar);

    public T zzrZ() {
        return this.zzapn;
    }
}
