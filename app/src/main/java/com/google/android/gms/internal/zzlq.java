package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public class zzlq<R extends Result> extends com.google.android.gms.common.api.zze<R> implements ResultCallback<R> {
    private final Object zzabh;
    private com.google.android.gms.common.api.zzb<? super R, ? extends Result> zzacY;
    private zzlq<? extends Result> zzacZ;
    private ResultCallbacks<? super R> zzada;
    private PendingResult<R> zzadb;

    private void zzd(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                Log.w("TransformedResultImpl", "Unable to release " + result, e);
            }
        }
    }

    private void zzon() {
        if (this.zzadb != null) {
            if (this.zzacY == null && this.zzada == null) {
                return;
            }
            this.zzadb.setResultCallback(this);
        }
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
    @Override // com.google.android.gms.common.api.ResultCallback
    public void onResult(R result) {
        synchronized (this.zzabh) {
            if (!result.getStatus().isSuccess()) {
                zzx(result.getStatus());
                zzd(result);
            } else if (this.zzacY != null) {
                PendingResult<S> pendingResultZza = this.zzacY.zza(result);
                if (pendingResultZza == 0) {
                    zzx(new Status(13, "Transform returned null"));
                } else {
                    this.zzacZ.zza(pendingResultZza);
                }
                zzd(result);
            } else if (this.zzada != null) {
                this.zzada.onSuccess(result);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void zza(PendingResult<?> pendingResult) {
        synchronized (this.zzabh) {
            this.zzadb = pendingResult;
            zzon();
        }
    }

    public void zzx(Status status) {
        synchronized (this.zzabh) {
            if (this.zzacY != null) {
                Status statusZzu = this.zzacY.zzu(status);
                com.google.android.gms.common.internal.zzx.zzb(statusZzu, "onFailure must not return null");
                this.zzacZ.zzx(statusZzu);
            } else if (this.zzada != null) {
                this.zzada.onFailure(status);
            }
        }
    }
}
