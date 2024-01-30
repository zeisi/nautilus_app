package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzabh;
import com.google.android.gms.internal.zzasc;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class zzary implements FusedLocationProviderApi {

    private static abstract class zza extends LocationServices.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* renamed from: zzb */
        public Status zzc(Status status) {
            return status;
        }
    }

    private static class zzb extends zzasc.zza {
        private final zzaad.zzb<Status> zzaGN;

        public zzb(zzaad.zzb<Status> zzb) {
            this.zzaGN = zzb;
        }

        public void zza(zzarz zzarz) {
            this.zzaGN.setResult(zzarz.getStatus());
        }
    }

    public PendingResult<Status> flushLocations(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(new zzb(this));
            }
        });
    }

    public Location getLastLocation(GoogleApiClient googleApiClient) {
        try {
            return LocationServices.zzj(googleApiClient).getLastLocation();
        } catch (Exception e) {
            return null;
        }
    }

    public LocationAvailability getLocationAvailability(GoogleApiClient googleApiClient) {
        try {
            return LocationServices.zzj(googleApiClient).zzIo();
        } catch (Exception e) {
            return null;
        }
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(pendingIntent, (zzasc) new zzb(this));
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, final LocationCallback locationCallback) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zzb(zzabi.zza(locationCallback, LocationCallback.class.getSimpleName()), new zzb(this));
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, final LocationListener locationListener) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza((zzabh.zzb<LocationListener>) zzabi.zza(locationListener, LocationListener.class.getSimpleName()), (zzasc) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, final LocationRequest locationRequest, final PendingIntent pendingIntent) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(locationRequest, pendingIntent, (zzasc) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        final LocationRequest locationRequest2 = locationRequest;
        final LocationCallback locationCallback2 = locationCallback;
        final Looper looper2 = looper;
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(zzasi.zzb(locationRequest2), (zzabh<LocationCallback>) zzabi.zzb(locationCallback2, zzata.zzc(looper2), LocationCallback.class.getSimpleName()), (zzasc) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, final LocationRequest locationRequest, final LocationListener locationListener) {
        zzac.zzb(Looper.myLooper(), (Object) "Calling thread must be a prepared Looper thread.");
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(locationRequest, (zzabh<LocationListener>) zzabi.zzb(locationListener, zzata.zzJk(), LocationListener.class.getSimpleName()), (zzasc) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        final LocationRequest locationRequest2 = locationRequest;
        final LocationListener locationListener2 = locationListener;
        final Looper looper2 = looper;
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zza(locationRequest2, (zzabh<LocationListener>) zzabi.zzb(locationListener2, zzata.zzc(looper2), LocationListener.class.getSimpleName()), (zzasc) new zzb(this));
            }
        });
    }

    public PendingResult<Status> setMockLocation(GoogleApiClient googleApiClient, final Location location) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zzd(location);
                zzb(Status.zzazx);
            }
        });
    }

    public PendingResult<Status> setMockMode(GoogleApiClient googleApiClient, final boolean z) {
        return googleApiClient.zzb(new zza(this, googleApiClient) {
            /* access modifiers changed from: protected */
            public void zza(zzash zzash) throws RemoteException {
                zzash.zzaH(z);
                zzb(Status.zzazx);
            }
        });
    }
}
