package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder;

/* JADX INFO: loaded from: classes.dex */
class zzo implements ContainerHolder {
    private Status zzSC;
    private Container zzaVY;
    private Container zzaVZ;
    private zzb zzaWa;
    private zza zzaWb;
    private TagManager zzaWc;
    private final Looper zzaaO;
    private boolean zzajJ;

    public interface zza {
        String zzCv();

        void zzCx();

        void zzeE(String str);
    }

    private class zzb extends Handler {
        private final ContainerHolder.ContainerAvailableListener zzaWd;

        public zzb(ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
            super(looper);
            this.zzaWd = containerAvailableListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    zzeG((String) msg.obj);
                    break;
                default:
                    zzbg.e("Don't know how to handle this message.");
                    break;
            }
        }

        public void zzeF(String str) {
            sendMessage(obtainMessage(1, str));
        }

        protected void zzeG(String str) {
            this.zzaWd.onContainerAvailable(zzo.this, str);
        }
    }

    public zzo(Status status) {
        this.zzSC = status;
        this.zzaaO = null;
    }

    public zzo(TagManager tagManager, Looper looper, Container container, zza zzaVar) {
        this.zzaWc = tagManager;
        this.zzaaO = looper == null ? Looper.getMainLooper() : looper;
        this.zzaVY = container;
        this.zzaWb = zzaVar;
        this.zzSC = Status.zzabb;
        tagManager.zza(this);
    }

    private void zzCw() {
        if (this.zzaWa != null) {
            this.zzaWa.zzeF(this.zzaVZ.zzCt());
        }
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public synchronized Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.zzajJ) {
                zzbg.e("ContainerHolder is released.");
            } else {
                if (this.zzaVZ != null) {
                    this.zzaVY = this.zzaVZ;
                    this.zzaVZ = null;
                }
                container = this.zzaVY;
            }
        }
        return container;
    }

    String getContainerId() {
        if (!this.zzajJ) {
            return this.zzaVY.getContainerId();
        }
        zzbg.e("getContainerId called on a released ContainerHolder.");
        return "";
    }

    @Override // com.google.android.gms.common.api.Result
    public Status getStatus() {
        return this.zzSC;
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public synchronized void refresh() {
        if (this.zzajJ) {
            zzbg.e("Refreshing a released ContainerHolder.");
        } else {
            this.zzaWb.zzCx();
        }
    }

    @Override // com.google.android.gms.common.api.Releasable
    public synchronized void release() {
        if (this.zzajJ) {
            zzbg.e("Releasing a released ContainerHolder.");
        } else {
            this.zzajJ = true;
            this.zzaWc.zzb(this);
            this.zzaVY.release();
            this.zzaVY = null;
            this.zzaVZ = null;
            this.zzaWb = null;
            this.zzaWa = null;
        }
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public synchronized void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener listener) {
        if (this.zzajJ) {
            zzbg.e("ContainerHolder is released.");
        } else if (listener == null) {
            this.zzaWa = null;
        } else {
            this.zzaWa = new zzb(listener, this.zzaaO);
            if (this.zzaVZ != null) {
                zzCw();
            }
        }
    }

    String zzCv() {
        if (!this.zzajJ) {
            return this.zzaWb.zzCv();
        }
        zzbg.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return "";
    }

    public synchronized void zza(Container container) {
        if (!this.zzajJ) {
            if (container == null) {
                zzbg.e("Unexpected null container.");
            } else {
                this.zzaVZ = container;
                zzCw();
            }
        }
    }

    public synchronized void zzeC(String str) {
        if (!this.zzajJ) {
            this.zzaVY.zzeC(str);
        }
    }

    void zzeE(String str) {
        if (this.zzajJ) {
            zzbg.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.zzaWb.zzeE(str);
        }
    }
}
