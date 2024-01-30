package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaoz;

public class zzaok extends zzaob<zzaoz> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.SESSIONS_API", new zzb(), zzaid);
    public static final Api.zzf<zzaok> zzaid = new Api.zzf<>();

    static abstract class zza<R extends Result> extends zzaad.zza<R, zzaok> {
        public zza(GoogleApiClient googleApiClient) {
            super((Api<?>) zzaok.API, googleApiClient);
        }

        public /* synthetic */ void setResult(Object obj) {
            super.zzb((Result) obj);
        }
    }

    public static class zzb extends Api.zza<zzaok, Api.ApiOptions.NoOptions> {
        /* renamed from: zzo */
        public zzaok zza(Context context, Looper looper, zzg zzg, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzaok(context, looper, zzg, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static abstract class zzc extends zza<Status> {
        zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* access modifiers changed from: protected */
        /* renamed from: zzb */
        public Status zzc(Status status) {
            zzac.zzax(!status.isSuccess());
            return status;
        }
    }

    public zzaok(Context context, Looper looper, zzg zzg, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 58, connectionCallbacks, onConnectionFailedListener, zzg);
    }

    /* renamed from: zzco */
    public zzaoz zzh(IBinder iBinder) {
        return zzaoz.zza.zzcD(iBinder);
    }

    public String zzeA() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi";
    }

    public String zzez() {
        return "com.google.android.gms.fitness.SessionsApi";
    }
}
