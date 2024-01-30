package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaow;

public class zzaoh extends zzaob<zzaow> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.INTERNAL_API", new zza(), zzaid);
    public static final Api.zzf<zzaoh> zzaid = new Api.zzf<>();

    public static class zza extends Api.zza<zzaoh, Api.ApiOptions.NoOptions> {
        /* renamed from: zzl */
        public zzaoh zza(Context context, Looper looper, zzg zzg, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzaoh(context, looper, zzg, connectionCallbacks, onConnectionFailedListener);
        }
    }

    public zzaoh(Context context, Looper looper, zzg zzg, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 61, connectionCallbacks, onConnectionFailedListener, zzg);
    }

    /* renamed from: zzcl */
    public zzaow zzh(IBinder iBinder) {
        return zzaow.zza.zzcA(iBinder);
    }

    public String zzeA() {
        return "com.google.android.gms.fitness.internal.IGoogleFitInternalApi";
    }

    public String zzez() {
        return "com.google.android.gms.fitness.InternalApi";
    }
}
