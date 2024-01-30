package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzabh;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzasf;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.zzt;

public class zzash extends zzarv {
    private final zzasg zzbkO;

    private static final class zza extends zzasd.zza {
        private zzaad.zzb<Status> zzbkP;

        public zza(zzaad.zzb<Status> zzb) {
            this.zzbkP = zzb;
        }

        public void zza(int i, PendingIntent pendingIntent) {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByPendingIntentResult");
        }

        public void zza(int i, String[] strArr) {
            if (this.zzbkP == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            this.zzbkP.setResult(LocationStatusCodes.zzkt(LocationStatusCodes.zzks(i)));
            this.zzbkP = null;
        }

        public void zzb(int i, String[] strArr) {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByRequestIdsResult");
        }
    }

    private static final class zzb extends zzasd.zza {
        private zzaad.zzb<Status> zzbkP;

        public zzb(zzaad.zzb<Status> zzb) {
            this.zzbkP = zzb;
        }

        private void zzkx(int i) {
            if (this.zzbkP == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
                return;
            }
            this.zzbkP.setResult(LocationStatusCodes.zzkt(LocationStatusCodes.zzks(i)));
            this.zzbkP = null;
        }

        public void zza(int i, PendingIntent pendingIntent) {
            zzkx(i);
        }

        public void zza(int i, String[] strArr) {
            Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
        }

        public void zzb(int i, String[] strArr) {
            zzkx(i);
        }
    }

    private static final class zzc extends zzasf.zza {
        private zzaad.zzb<LocationSettingsResult> zzbkP;

        public zzc(zzaad.zzb<LocationSettingsResult> zzb) {
            zzac.zzb(zzb != null, (Object) "listener can't be null.");
            this.zzbkP = zzb;
        }

        public void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
            this.zzbkP.setResult(locationSettingsResult);
            this.zzbkP = null;
        }
    }

    public zzash(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzg.zzaS(context));
    }

    public zzash(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String str, zzg zzg) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzg);
        this.zzbkO = new zzasg(context, this.zzbkx);
    }

    public void disconnect() {
        synchronized (this.zzbkO) {
            if (isConnected()) {
                try {
                    this.zzbkO.removeAllListeners();
                    this.zzbkO.zzIp();
                } catch (Exception e) {
                    Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", e);
                }
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.zzbkO.getLastLocation();
    }

    public LocationAvailability zzIo() {
        return this.zzbkO.zzIo();
    }

    public void zza(long j, PendingIntent pendingIntent) throws RemoteException {
        zzxC();
        zzac.zzw(pendingIntent);
        zzac.zzb(j >= 0, (Object) "detectionIntervalMillis must be >= 0");
        ((zzase) zzxD()).zza(j, true, pendingIntent);
    }

    public void zza(PendingIntent pendingIntent, zzasc zzasc) throws RemoteException {
        this.zzbkO.zza(pendingIntent, zzasc);
    }

    public void zza(zzabh.zzb<LocationListener> zzb2, zzasc zzasc) throws RemoteException {
        this.zzbkO.zza(zzb2, zzasc);
    }

    public void zza(zzasc zzasc) throws RemoteException {
        this.zzbkO.zza(zzasc);
    }

    public void zza(zzasi zzasi, zzabh<LocationCallback> zzabh, zzasc zzasc) throws RemoteException {
        synchronized (this.zzbkO) {
            this.zzbkO.zza(zzasi, zzabh, zzasc);
        }
    }

    public void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzaad.zzb<Status> zzb2) throws RemoteException {
        zzxC();
        zzac.zzb(geofencingRequest, (Object) "geofencingRequest can't be null.");
        zzac.zzb(pendingIntent, (Object) "PendingIntent must be specified.");
        zzac.zzb(zzb2, (Object) "ResultHolder not provided.");
        ((zzase) zzxD()).zza(geofencingRequest, pendingIntent, (zzasd) new zza(zzb2));
    }

    public void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzasc zzasc) throws RemoteException {
        this.zzbkO.zza(locationRequest, pendingIntent, zzasc);
    }

    public void zza(LocationRequest locationRequest, zzabh<LocationListener> zzabh, zzasc zzasc) throws RemoteException {
        synchronized (this.zzbkO) {
            this.zzbkO.zza(locationRequest, zzabh, zzasc);
        }
    }

    public void zza(LocationSettingsRequest locationSettingsRequest, zzaad.zzb<LocationSettingsResult> zzb2, String str) throws RemoteException {
        boolean z = true;
        zzxC();
        zzac.zzb(locationSettingsRequest != null, (Object) "locationSettingsRequest can't be null nor empty.");
        if (zzb2 == null) {
            z = false;
        }
        zzac.zzb(z, (Object) "listener can't be null.");
        ((zzase) zzxD()).zza(locationSettingsRequest, (zzasf) new zzc(zzb2), str);
    }

    public void zza(zzt zzt, zzaad.zzb<Status> zzb2) throws RemoteException {
        zzxC();
        zzac.zzb(zzt, (Object) "removeGeofencingRequest can't be null.");
        zzac.zzb(zzb2, (Object) "ResultHolder not provided.");
        ((zzase) zzxD()).zza(zzt, (zzasd) new zzb(zzb2));
    }

    public void zzaH(boolean z) throws RemoteException {
        this.zzbkO.zzaH(z);
    }

    public void zzb(zzabh.zzb<LocationCallback> zzb2, zzasc zzasc) throws RemoteException {
        this.zzbkO.zzb(zzb2, zzasc);
    }

    public void zzc(PendingIntent pendingIntent) throws RemoteException {
        zzxC();
        zzac.zzw(pendingIntent);
        ((zzase) zzxD()).zzc(pendingIntent);
    }

    public void zzd(Location location) throws RemoteException {
        this.zzbkO.zzd(location);
    }
}
