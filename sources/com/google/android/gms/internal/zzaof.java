package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaou;
import com.nautilus.omni.bleservices.BLEScanManager;

public class zzaof extends zzaob<zzaou> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.GOALS_API", new zzb(), zzaid);
    public static final Api.zzf<zzaof> zzaid = new Api.zzf<>();

    static abstract class zza<R extends Result> extends zzaad.zza<R, zzaof> {
        public zza(GoogleApiClient googleApiClient) {
            super((Api<?>) zzaof.API, googleApiClient);
        }

        public /* synthetic */ void setResult(Object obj) {
            super.zzb((Result) obj);
        }
    }

    public static class zzb extends Api.zza<zzaof, Api.ApiOptions.NoOptions> {
        /* renamed from: zzj */
        public zzaof zza(Context context, Looper looper, zzg zzg, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzaof(context, looper, zzg, connectionCallbacks, onConnectionFailedListener);
        }
    }

    public zzaof(Context context, Looper looper, zzg zzg, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, BLEScanManager.CONVERT_TO_SIGNAL_STRENGTH, connectionCallbacks, onConnectionFailedListener, zzg);
    }

    /* renamed from: zzcj */
    public zzaou zzh(IBinder iBinder) {
        return zzaou.zza.zzcy(iBinder);
    }

    public String zzeA() {
        return "com.google.android.gms.fitness.internal.IGoogleFitGoalsApi";
    }

    public String zzez() {
        return "com.google.android.gms.fitness.GoalsApi";
    }
}
