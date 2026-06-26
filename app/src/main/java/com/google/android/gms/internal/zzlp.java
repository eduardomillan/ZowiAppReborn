package com.google.android.gms.internal;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class zzlp extends Fragment implements DialogInterface.OnCancelListener {
    private static final GoogleApiAvailability zzacJ = GoogleApiAvailability.getInstance();
    private boolean mStarted;
    private boolean zzacK;
    private ConnectionResult zzacM;
    private zzll zzacO;
    private int zzacL = -1;
    private final Handler zzacN = new Handler(Looper.getMainLooper());
    private final SparseArray<zza> zzacP = new SparseArray<>();

    private class zza implements GoogleApiClient.OnConnectionFailedListener {
        public final int zzacQ;
        public final GoogleApiClient zzacR;
        public final GoogleApiClient.OnConnectionFailedListener zzacS;

        public zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zzacQ = i;
            this.zzacR = googleApiClient;
            this.zzacS = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            writer.append((CharSequence) prefix).append("GoogleApiClient #").print(this.zzacQ);
            writer.println(":");
            this.zzacR.dump(prefix + "  ", fd, writer, args);
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
        public void onConnectionFailed(ConnectionResult result) {
            zzlp.this.zzacN.post(zzlp.this.new zzb(this.zzacQ, result));
        }

        public void zzom() {
            this.zzacR.unregisterConnectionFailedListener(this);
            this.zzacR.disconnect();
        }
    }

    private class zzb implements Runnable {
        private final int zzacU;
        private final ConnectionResult zzacV;

        public zzb(int i, ConnectionResult connectionResult) {
            this.zzacU = i;
            this.zzacV = connectionResult;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!zzlp.this.mStarted || zzlp.this.zzacK) {
                return;
            }
            zzlp.this.zzacK = true;
            zzlp.this.zzacL = this.zzacU;
            zzlp.this.zzacM = this.zzacV;
            if (this.zzacV.hasResolution()) {
                try {
                    this.zzacV.startResolutionForResult(zzlp.this.getActivity(), ((zzlp.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzlp.this) + 1) << 16) + 1);
                    return;
                } catch (IntentSender.SendIntentException e) {
                    zzlp.this.zzok();
                    return;
                }
            }
            if (zzlp.zzacJ.isUserResolvableError(this.zzacV.getErrorCode())) {
                GooglePlayServicesUtil.showErrorDialogFragment(this.zzacV.getErrorCode(), zzlp.this.getActivity(), zzlp.this, 2, zzlp.this);
            } else {
                if (this.zzacV.getErrorCode() != 18) {
                    zzlp.this.zza(this.zzacU, this.zzacV);
                    return;
                }
                final Dialog dialogZza = zzlp.zzacJ.zza(zzlp.this.getActivity(), zzlp.this);
                zzlp.this.zzacO = zzll.zza(zzlp.this.getActivity().getApplicationContext(), new zzll() { // from class: com.google.android.gms.internal.zzlp.zzb.1
                    @Override // com.google.android.gms.internal.zzll
                    protected void zzoi() {
                        zzlp.this.zzok();
                        dialogZza.dismiss();
                    }
                });
            }
        }
    }

    public static zzlp zza(FragmentActivity fragmentActivity) {
        com.google.android.gms.common.internal.zzx.zzci("Must be called from main thread of process");
        try {
            zzlp zzlpVar = (zzlp) fragmentActivity.getSupportFragmentManager().findFragmentByTag("GmsSupportLifecycleFragment");
            if (zzlpVar == null || zzlpVar.isRemoving()) {
                return null;
            }
            return zzlpVar;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Fragment with tag GmsSupportLifecycleFragment is not a SupportLifecycleFragment", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zza(int i, ConnectionResult connectionResult) {
        Log.w("GmsSupportLifecycleFragment", "Unresolved error while connecting client. Stopping auto-manage.");
        zza zzaVar = this.zzacP.get(i);
        if (zzaVar != null) {
            zzbp(i);
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = zzaVar.zzacS;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
        zzok();
    }

    public static zzlp zzb(FragmentActivity fragmentActivity) {
        zzlp zzlpVarZza = zza(fragmentActivity);
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        if (zzlpVarZza != null) {
            return zzlpVarZza;
        }
        zzlp zzlpVar = new zzlp();
        supportFragmentManager.beginTransaction().add(zzlpVar, "GmsSupportLifecycleFragment").commitAllowingStateLoss();
        supportFragmentManager.executePendingTransactions();
        return zzlpVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzok() {
        int i = 0;
        this.zzacK = false;
        this.zzacL = -1;
        this.zzacM = null;
        if (this.zzacO != null) {
            this.zzacO.unregister();
            this.zzacO = null;
        }
        while (true) {
            int i2 = i;
            if (i2 >= this.zzacP.size()) {
                return;
            }
            this.zzacP.valueAt(i2).zzacR.connect();
            i = i2 + 1;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzacP.size()) {
                return;
            }
            this.zzacP.valueAt(i2).dump(prefix, fd, writer, args);
            i = i2 + 1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0005  */
    @Override // android.support.v4.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
            r4 = this;
            r0 = 1
            r1 = 0
            switch(r5) {
                case 1: goto L19;
                case 2: goto Lc;
                default: goto L5;
            }
        L5:
            r0 = r1
        L6:
            if (r0 == 0) goto L29
            r4.zzok()
        Lb:
            return
        Lc:
            com.google.android.gms.common.GoogleApiAvailability r2 = com.google.android.gms.internal.zzlp.zzacJ
            android.support.v4.app.FragmentActivity r3 = r4.getActivity()
            int r2 = r2.isGooglePlayServicesAvailable(r3)
            if (r2 != 0) goto L5
            goto L6
        L19:
            r2 = -1
            if (r6 == r2) goto L6
            if (r6 != 0) goto L5
            com.google.android.gms.common.ConnectionResult r0 = new com.google.android.gms.common.ConnectionResult
            r2 = 13
            r3 = 0
            r0.<init>(r2, r3)
            r4.zzacM = r0
            goto L5
        L29:
            int r0 = r4.zzacL
            com.google.android.gms.common.ConnectionResult r1 = r4.zzacM
            r4.zza(r0, r1)
            goto Lb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzlp.onActivityResult(int, int, android.content.Intent):void");
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        zza(this.zzacL, new ConnectionResult(13, null));
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.zzacK = savedInstanceState.getBoolean("resolving_error", false);
            this.zzacL = savedInstanceState.getInt("failed_client_id", -1);
            if (this.zzacL >= 0) {
                this.zzacM = new ConnectionResult(savedInstanceState.getInt("failed_status"), (PendingIntent) savedInstanceState.getParcelable("failed_resolution"));
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("resolving_error", this.zzacK);
        if (this.zzacL >= 0) {
            outState.putInt("failed_client_id", this.zzacL);
            outState.putInt("failed_status", this.zzacM.getErrorCode());
            outState.putParcelable("failed_resolution", this.zzacM.getResolution());
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onStart() {
        super.onStart();
        this.mStarted = true;
        if (this.zzacK) {
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzacP.size()) {
                return;
            }
            this.zzacP.valueAt(i2).zzacR.connect();
            i = i2 + 1;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        int i = 0;
        super.onStop();
        this.mStarted = false;
        while (true) {
            int i2 = i;
            if (i2 >= this.zzacP.size()) {
                return;
            }
            this.zzacP.valueAt(i2).zzacR.disconnect();
            i = i2 + 1;
        }
    }

    public void zza(int i, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        com.google.android.gms.common.internal.zzx.zzb(googleApiClient, "GoogleApiClient instance cannot be null");
        com.google.android.gms.common.internal.zzx.zza(this.zzacP.indexOfKey(i) < 0, "Already managing a GoogleApiClient with id " + i);
        this.zzacP.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (!this.mStarted || this.zzacK) {
            return;
        }
        googleApiClient.connect();
    }

    public void zzbp(int i) {
        zza zzaVar = this.zzacP.get(i);
        this.zzacP.remove(i);
        if (zzaVar != null) {
            zzaVar.zzom();
        }
    }
}
