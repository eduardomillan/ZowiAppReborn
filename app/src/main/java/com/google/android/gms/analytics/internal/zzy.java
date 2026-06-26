package com.google.android.gms.analytics.internal;

import com.comscore.measurement.MeasurementDispatcher;
import com.comscore.utils.Constants;
import com.google.android.gms.internal.zzlr;

/* JADX INFO: loaded from: classes.dex */
public final class zzy {
    public static zza<Boolean> zzOe = zza.zzd("analytics.service_enabled", false);
    public static zza<Boolean> zzOf = zza.zzd("analytics.service_client_enabled", true);
    public static zza<String> zzOg = zza.zzd("analytics.log_tag", "GAv4", "GAv4-SVC");
    public static zza<Long> zzOh = zza.zzc("analytics.max_tokens", 60);
    public static zza<Float> zzOi = zza.zza("analytics.tokens_per_sec", 0.5f);
    public static zza<Integer> zzOj = zza.zza("analytics.max_stored_hits", 2000, 20000);
    public static zza<Integer> zzOk = zza.zze("analytics.max_stored_hits_per_app", 2000);
    public static zza<Integer> zzOl = zza.zze("analytics.max_stored_properties_per_app", 100);
    public static zza<Long> zzOm = zza.zza("analytics.local_dispatch_millis", Constants.SESSION_INACTIVE_PERIOD, 120000L);
    public static zza<Long> zzOn = zza.zza("analytics.initial_local_dispatch_millis", 5000L, 5000L);
    public static zza<Long> zzOo = zza.zzc("analytics.min_local_dispatch_millis", 120000);
    public static zza<Long> zzOp = zza.zzc("analytics.max_local_dispatch_millis", 7200000);
    public static zza<Long> zzOq = zza.zzc("analytics.dispatch_alarm_millis", 7200000);
    public static zza<Long> zzOr = zza.zzc("analytics.max_dispatch_alarm_millis", 32400000);
    public static zza<Integer> zzOs = zza.zze("analytics.max_hits_per_dispatch", 20);
    public static zza<Integer> zzOt = zza.zze("analytics.max_hits_per_batch", 20);
    public static zza<String> zzOu = zza.zzn("analytics.insecure_host", "http://www.google-analytics.com");
    public static zza<String> zzOv = zza.zzn("analytics.secure_host", "https://ssl.google-analytics.com");
    public static zza<String> zzOw = zza.zzn("analytics.simple_endpoint", "/collect");
    public static zza<String> zzOx = zza.zzn("analytics.batching_endpoint", "/batch");
    public static zza<Integer> zzOy = zza.zze("analytics.max_get_length", 2036);
    public static zza<String> zzOz = zza.zzd("analytics.batching_strategy.k", zzm.BATCH_BY_COUNT.name(), zzm.BATCH_BY_COUNT.name());
    public static zza<String> zzOA = zza.zzn("analytics.compression_strategy.k", zzo.GZIP.name());
    public static zza<Integer> zzOB = zza.zze("analytics.max_hits_per_request.k", 20);
    public static zza<Integer> zzOC = zza.zze("analytics.max_hit_length.k", 8192);
    public static zza<Integer> zzOD = zza.zze("analytics.max_post_length.k", 8192);
    public static zza<Integer> zzOE = zza.zze("analytics.max_batch_post_length", 8192);
    public static zza<String> zzOF = zza.zzn("analytics.fallback_responses.k", "404,502");
    public static zza<Integer> zzOG = zza.zze("analytics.batch_retry_interval.seconds.k", 3600);
    public static zza<Long> zzOH = zza.zzc("analytics.service_monitor_interval", MeasurementDispatcher.MILLIS_PER_DAY);
    public static zza<Integer> zzOI = zza.zze("analytics.http_connection.connect_timeout_millis", Constants.MINIMAL_AUTOUPDATE_INTERVAL);
    public static zza<Integer> zzOJ = zza.zze("analytics.http_connection.read_timeout_millis", 61000);
    public static zza<Long> zzOK = zza.zzc("analytics.campaigns.time_limit", MeasurementDispatcher.MILLIS_PER_DAY);
    public static zza<String> zzOL = zza.zzn("analytics.first_party_experiment_id", "");
    public static zza<Integer> zzOM = zza.zze("analytics.first_party_experiment_variant", 0);
    public static zza<Boolean> zzON = zza.zzd("analytics.test.disable_receiver", false);
    public static zza<Long> zzOO = zza.zza("analytics.service_client.idle_disconnect_millis", com.comscore.streaming.Constants.HEARTBEAT_STAGE_ONE_INTERVAL, com.comscore.streaming.Constants.HEARTBEAT_STAGE_ONE_INTERVAL);
    public static zza<Long> zzOP = zza.zzc("analytics.service_client.connect_timeout_millis", 5000);
    public static zza<Long> zzOQ = zza.zzc("analytics.service_client.second_connect_delay_millis", 5000);
    public static zza<Long> zzOR = zza.zzc("analytics.service_client.unexpected_reconnect_millis", 60000);
    public static zza<Long> zzOS = zza.zzc("analytics.service_client.reconnect_throttle_millis", Constants.SESSION_INACTIVE_PERIOD);
    public static zza<Long> zzOT = zza.zzc("analytics.monitoring.sample_period_millis", MeasurementDispatcher.MILLIS_PER_DAY);
    public static zza<Long> zzOU = zza.zzc("analytics.initialization_warning_threshold", 5000);

    public static final class zza<V> {
        private final V zzOV;
        private final zzlr<V> zzOW;
        private V zzOX;

        private zza(zzlr<V> zzlrVar, V v) {
            com.google.android.gms.common.internal.zzx.zzw(zzlrVar);
            this.zzOW = zzlrVar;
            this.zzOV = v;
        }

        static zza<Float> zza(String str, float f) {
            return zza(str, f, f);
        }

        static zza<Float> zza(String str, float f, float f2) {
            return new zza<>(zzlr.zza(str, Float.valueOf(f2)), Float.valueOf(f));
        }

        static zza<Integer> zza(String str, int i, int i2) {
            return new zza<>(zzlr.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
        }

        static zza<Long> zza(String str, long j, long j2) {
            return new zza<>(zzlr.zza(str, Long.valueOf(j2)), Long.valueOf(j));
        }

        static zza<Boolean> zza(String str, boolean z, boolean z2) {
            return new zza<>(zzlr.zzg(str, z2), Boolean.valueOf(z));
        }

        static zza<Long> zzc(String str, long j) {
            return zza(str, j, j);
        }

        static zza<String> zzd(String str, String str2, String str3) {
            return new zza<>(zzlr.zzu(str, str3), str2);
        }

        static zza<Boolean> zzd(String str, boolean z) {
            return zza(str, z, z);
        }

        static zza<Integer> zze(String str, int i) {
            return zza(str, i, i);
        }

        static zza<String> zzn(String str, String str2) {
            return zzd(str, str2, str2);
        }

        public V get() {
            return this.zzOX != null ? this.zzOX : (com.google.android.gms.common.internal.zzd.zzaeK && zzlr.isInitialized()) ? this.zzOW.zzop() : this.zzOV;
        }
    }
}
