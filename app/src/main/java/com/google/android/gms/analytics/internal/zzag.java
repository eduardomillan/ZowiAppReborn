package com.google.android.gms.analytics.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
class zzag extends BroadcastReceiver {
    static final String zzPu = zzag.class.getName();
    private final zzf zzME;
    private boolean zzPv;
    private boolean zzPw;

    zzag(zzf zzfVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzfVar);
        this.zzME = zzfVar;
    }

    private Context getContext() {
        return this.zzME.getContext();
    }

    private zzb zzhP() {
        return this.zzME.zzhP();
    }

    private zzaf zziu() {
        return this.zzME.zziu();
    }

    private void zzkI() {
        zziu();
        zzhP();
    }

    public boolean isConnected() {
        if (!this.zzPv) {
            this.zzME.zziu().zzbd("Connectivity unknown. Receiver not registered");
        }
        return this.zzPw;
    }

    public boolean isRegistered() {
        return this.zzPv;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context ctx, Intent intent) {
        zzkI();
        String action = intent.getAction();
        this.zzME.zziu().zza("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zZzkK = zzkK();
            if (this.zzPw != zZzkK) {
                this.zzPw = zZzkK;
                zzhP().zzI(zZzkK);
                return;
            }
            return;
        }
        if (!"com.google.analytics.RADIO_POWERED".equals(action)) {
            this.zzME.zziu().zzd("NetworkBroadcastReceiver received unknown action", action);
        } else {
            if (intent.hasExtra(zzPu)) {
                return;
            }
            zzhP().zzio();
        }
    }

    public void unregister() {
        if (isRegistered()) {
            this.zzME.zziu().zzba("Unregistering connectivity change receiver");
            this.zzPv = false;
            this.zzPw = false;
            try {
                getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                zziu().zze("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public void zzkH() {
        zzkI();
        if (this.zzPv) {
            return;
        }
        Context context = getContext();
        context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
        intentFilter.addCategory(context.getPackageName());
        context.registerReceiver(this, intentFilter);
        this.zzPw = zzkK();
        this.zzME.zziu().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzPw));
        this.zzPv = true;
    }

    public void zzkJ() {
        if (Build.VERSION.SDK_INT <= 10) {
            return;
        }
        Context context = getContext();
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzPu, true);
        context.sendOrderedBroadcast(intent, null);
    }

    protected boolean zzkK() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException e) {
            return false;
        }
    }
}
