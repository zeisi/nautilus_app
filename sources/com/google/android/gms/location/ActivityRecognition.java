package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaru;
import com.google.android.gms.internal.zzash;

public class ActivityRecognition {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("ActivityRecognition.API", zzaie, zzaid);
    public static final ActivityRecognitionApi ActivityRecognitionApi = new zzaru();
    public static final String CLIENT_NAME = "activity_recognition";
    private static final Api.zzf<zzash> zzaid = new Api.zzf<>();
    private static final Api.zza<zzash, Api.ApiOptions.NoOptions> zzaie = new Api.zza<zzash, Api.ApiOptions.NoOptions>() {
        /* renamed from: zzq */
        public zzash zza(Context context, Looper looper, zzg zzg, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzash(context, looper, connectionCallbacks, onConnectionFailedListener, ActivityRecognition.CLIENT_NAME);
        }
    };

    public static abstract class zza<R extends Result> extends zzaad.zza<R, zzash> {
        public zza(GoogleApiClient googleApiClient) {
            super((Api<?>) ActivityRecognition.API, googleApiClient);
        }

        public /* synthetic */ void setResult(Object obj) {
            super.zzb((Result) obj);
        }
    }

    private ActivityRecognition() {
    }
}
