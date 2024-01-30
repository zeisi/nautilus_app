package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;

public class zzaru implements ActivityRecognitionApi {

    private static abstract class zza extends ActivityRecognition.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* renamed from: zzb */
        public Status zzc(Status status) {
            return status;
        }
    }

    public PendingResult<Status> removeActivityUpdates(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zzc(pendingIntent);
                zzb(Status.zzazx);
            }
        });
    }

    public PendingResult<Status> requestActivityUpdates(GoogleApiClient googleApiClient, long j, PendingIntent pendingIntent) {
        final long j2 = j;
        final PendingIntent pendingIntent2 = pendingIntent;
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(j2, pendingIntent2);
                zzb(Status.zzazx);
            }
        });
    }
}
