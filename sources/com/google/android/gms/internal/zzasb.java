package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.zzt;
import java.util.List;

public class zzasb implements GeofencingApi {

    private static abstract class zza extends LocationServices.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* renamed from: zzb */
        public Status zzc(Status status) {
            return status;
        }
    }

    public PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, final GeofencingRequest geofencingRequest, final PendingIntent pendingIntent) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(geofencingRequest, pendingIntent, (zzaad.zzb<Status>) this);
            }
        });
    }

    @Deprecated
    public PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, List<Geofence> list, PendingIntent pendingIntent) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.addGeofences(list);
        builder.setInitialTrigger(5);
        return addGeofences(googleApiClient, builder.build(), pendingIntent);
    }

    public PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zza(googleApiClient, zzt.zzb(pendingIntent));
    }

    public PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, List<String> list) {
        return zza(googleApiClient, zzt.zzE(list));
    }

    public PendingResult<Status> zza(GoogleApiClient googleApiClient, final zzt zzt) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(zzt, (zzaad.zzb<Status>) this);
            }
        });
    }
}
