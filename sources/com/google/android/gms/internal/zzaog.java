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
import com.google.android.gms.internal.zzaov;

public class zzaog extends zzaob<zzaov> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.API", new zzb(), zzaid);
    public static final Api.zzf<zzaog> zzaid = new Api.zzf<>();

    static abstract class zza<R extends Result> extends zzaad.zza<R, zzaog> {
        public zza(GoogleApiClient googleApiClient) {
            super((Api<?>) zzaog.API, googleApiClient);
        }

        public /* synthetic */ void setResult(Object obj) {
            super.zzb((Result) obj);
        }
    }

    public static class zzb extends Api.zza<zzaog, Api.ApiOptions.NoOptions> {
        /* renamed from: zzk */
        public zzaog zza(Context context, Looper looper, zzg zzg, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzaog(context, looper, zzg, connectionCallbacks, onConnectionFailedListener);
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

    public zzaog(Context context, Looper looper, zzg zzg, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 57, connectionCallbacks, onConnectionFailedListener, zzg);
    }

    /* renamed from: zzck */
    public zzaov zzh(IBinder iBinder) {
        return zzaov.zza.zzcz(iBinder);
    }

    public String zzeA() {
        return "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi";
    }

    public String zzez() {
        return "com.google.android.gms.fitness.HistoryApi";
    }
}
