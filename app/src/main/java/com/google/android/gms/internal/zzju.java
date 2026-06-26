package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.appdatasearch.UsageInfo;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzlb;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzju implements com.google.android.gms.appdatasearch.zzk, AppIndexApi {

    @Deprecated
    private static final class zza implements AppIndexApi.ActionResult {
        private zzju zzRf;
        private PendingResult<Status> zzRg;
        private Action zzRh;

        zza(zzju zzjuVar, PendingResult<Status> pendingResult, Action action) {
            this.zzRf = zzjuVar;
            this.zzRg = pendingResult;
            this.zzRh = action;
        }

        @Override // com.google.android.gms.appindexing.AppIndexApi.ActionResult
        public PendingResult<Status> end(GoogleApiClient apiClient) {
            return this.zzRf.zza(apiClient, zzjt.zza(this.zzRh, System.currentTimeMillis(), apiClient.getContext().getPackageName(), 2));
        }

        @Override // com.google.android.gms.appindexing.AppIndexApi.ActionResult
        public PendingResult<Status> getPendingResult() {
            return this.zzRg;
        }
    }

    private static abstract class zzb<T extends Result> extends zzlb.zza<T, zzjs> {
        public zzb(GoogleApiClient googleApiClient) {
            super(com.google.android.gms.appdatasearch.zza.zzPT, googleApiClient);
        }

        protected abstract void zza(zzjp zzjpVar) throws RemoteException;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlb.zza
        public final void zza(zzjs zzjsVar) throws RemoteException {
            zza(zzjsVar.zzlw());
        }
    }

    private static abstract class zzc<T extends Result> extends zzb<Status> {
        zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlc
        /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
        public Status zzb(Status status) {
            return status;
        }
    }

    private static final class zzd extends zzjr<Status> {
        public zzd(zzlb.zzb<Status> zzbVar) {
            super(zzbVar);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // com.google.android.gms.internal.zzjr, com.google.android.gms.internal.zzjq
        public void zzc(Status status) {
            this.zzRb.zzp((T) status);
        }
    }

    public static Intent zza(String str, Uri uri) {
        zzb(str, uri);
        List<String> pathSegments = uri.getPathSegments();
        String str2 = pathSegments.get(0);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(str2);
        if (pathSegments.size() > 1) {
            builder.authority(pathSegments.get(1));
            int i = 2;
            while (true) {
                int i2 = i;
                if (i2 >= pathSegments.size()) {
                    break;
                }
                builder.appendPath(pathSegments.get(i2));
                i = i2 + 1;
            }
        }
        builder.encodedQuery(uri.getEncodedQuery());
        builder.encodedFragment(uri.getEncodedFragment());
        return new Intent("android.intent.action.VIEW", builder.build());
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, Action action, int i) {
        return zza(googleApiClient, zzjt.zza(action, System.currentTimeMillis(), googleApiClient.getContext().getPackageName(), i));
    }

    private static void zzb(String str, Uri uri) {
        if (!"android-app".equals(uri.getScheme())) {
            throw new IllegalArgumentException("AppIndex: The URI scheme must be 'android-app' and follow the format (android-app://<package_name>/<scheme>/[host_path]). Provided URI: " + uri);
        }
        String host = uri.getHost();
        if (str != null && !str.equals(host)) {
            throw new IllegalArgumentException("AppIndex: The URI host must match the package name and follow the format (android-app://<package_name>/<scheme>/[host_path]). Provided URI: " + uri);
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.isEmpty() || pathSegments.get(0).isEmpty()) {
            throw new IllegalArgumentException("AppIndex: The app URI scheme must exist and follow the format android-app://<package_name>/<scheme>/[host_path]). Provided URI: " + uri);
        }
    }

    public static void zzp(List<AppIndexApi.AppIndexingLink> list) {
        if (list == null) {
            return;
        }
        Iterator<AppIndexApi.AppIndexingLink> it = list.iterator();
        while (it.hasNext()) {
            zzb(null, it.next().appIndexingUrl);
        }
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public AppIndexApi.ActionResult action(GoogleApiClient apiClient, Action action) {
        return new zza(this, zza(apiClient, action, 1), action);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public PendingResult<Status> end(GoogleApiClient apiClient, Action action) {
        return zza(apiClient, action, 2);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public PendingResult<Status> start(GoogleApiClient apiClient, Action action) {
        return zza(apiClient, action, 1);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public PendingResult<Status> view(GoogleApiClient apiClient, Activity activity, Intent viewIntent, String title, Uri webUrl, List<AppIndexApi.AppIndexingLink> outLinks) {
        String packageName = apiClient.getContext().getPackageName();
        zzp(outLinks);
        return zza(apiClient, new UsageInfo(packageName, viewIntent, title, webUrl, null, outLinks, 1));
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public PendingResult<Status> view(GoogleApiClient apiClient, Activity activity, Uri appIndexingUrl, String title, Uri webUrl, List<AppIndexApi.AppIndexingLink> outLinks) {
        String packageName = apiClient.getContext().getPackageName();
        zzb(packageName, appIndexingUrl);
        return view(apiClient, activity, zza(packageName, appIndexingUrl), title, webUrl, outLinks);
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public PendingResult<Status> viewEnd(GoogleApiClient apiClient, Activity activity, Intent viewIntent) {
        return zza(apiClient, new UsageInfo.zza().zza(UsageInfo.zza(apiClient.getContext().getPackageName(), viewIntent)).zzw(System.currentTimeMillis()).zzan(0).zzao(2).zzlv());
    }

    @Override // com.google.android.gms.appindexing.AppIndexApi
    public PendingResult<Status> viewEnd(GoogleApiClient apiClient, Activity activity, Uri appUri) {
        return viewEnd(apiClient, activity, zza(apiClient.getContext().getPackageName(), appUri));
    }

    @Override // com.google.android.gms.appdatasearch.zzk
    public PendingResult<GetRecentContextCall.Response> zza(GoogleApiClient googleApiClient, GetRecentContextCall.Request request) {
        return googleApiClient.zza(new GetRecentContextCall.zza(request, googleApiClient));
    }

    public PendingResult<Status> zza(GoogleApiClient googleApiClient, final UsageInfo... usageInfoArr) {
        final String packageName = googleApiClient.getContext().getPackageName();
        return googleApiClient.zza(new zzc<Status>(googleApiClient) { // from class: com.google.android.gms.internal.zzju.1
            @Override // com.google.android.gms.internal.zzju.zzb
            protected void zza(zzjp zzjpVar) throws RemoteException {
                zzjpVar.zza(new zzd(this), packageName, usageInfoArr);
            }
        });
    }
}
