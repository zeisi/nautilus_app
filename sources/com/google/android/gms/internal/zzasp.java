package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

public class zzasp implements SettingsApi {
    public PendingResult<LocationSettingsResult> checkLocationSettings(GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest) {
        return zza(googleApiClient, locationSettingsRequest, (String) null);
    }

    public PendingResult<LocationSettingsResult> zza(GoogleApiClient googleApiClient, final LocationSettingsRequest locationSettingsRequest, final String str) {
        return googleApiClient.zza(new LocationServices.zza<LocationSettingsResult>(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(locationSettingsRequest, (zzaad.zzb<LocationSettingsResult>) this, str);
            }

            /* renamed from: zzbp */
            public LocationSettingsResult zzc(Status status) {
                return new LocationSettingsResult(status);
            }
        });
    }
}
