package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzabh;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzj;
import com.google.android.gms.location.zzk;
import java.util.HashMap;
import java.util.Map;

public class zzasg {
    private final Context mContext;
    private final Map<zzabh.zzb<LocationListener>, zzb> zzaWg = new HashMap();
    private ContentProviderClient zzbkI = null;
    private boolean zzbkJ = false;
    private final Map<zzabh.zzb<LocationCallback>, zza> zzbkK = new HashMap();
    private final zzaso<zzase> zzbkx;

    private static class zza extends zzj.zza {
        private final zzabh<LocationCallback> zzaDf;

        zza(zzabh<LocationCallback> zzabh) {
            this.zzaDf = zzabh;
        }

        public void onLocationAvailability(final LocationAvailability locationAvailability) {
            this.zzaDf.zza(new zzabh.zzc<LocationCallback>(this) {
                /* renamed from: zza */
                public void zzs(LocationCallback locationCallback) {
                    locationCallback.onLocationAvailability(locationAvailability);
                }

                public void zzwc() {
                }
            });
        }

        public void onLocationResult(final LocationResult locationResult) {
            this.zzaDf.zza(new zzabh.zzc<LocationCallback>(this) {
                /* renamed from: zza */
                public void zzs(LocationCallback locationCallback) {
                    locationCallback.onLocationResult(locationResult);
                }

                public void zzwc() {
                }
            });
        }

        public synchronized void release() {
            this.zzaDf.clear();
        }
    }

    private static class zzb extends zzk.zza {
        private final zzabh<LocationListener> zzaDf;

        zzb(zzabh<LocationListener> zzabh) {
            this.zzaDf = zzabh;
        }

        public synchronized void onLocationChanged(final Location location) {
            this.zzaDf.zza(new zzabh.zzc<LocationListener>(this) {
                /* renamed from: zza */
                public void zzs(LocationListener locationListener) {
                    locationListener.onLocationChanged(location);
                }

                public void zzwc() {
                }
            });
        }

        public synchronized void release() {
            this.zzaDf.clear();
        }
    }

    public zzasg(Context context, zzaso<zzase> zzaso) {
        this.mContext = context;
        this.zzbkx = zzaso;
    }

    private zzb zzf(zzabh<LocationListener> zzabh) {
        zzb zzb2;
        synchronized (this.zzaWg) {
            zzb2 = this.zzaWg.get(zzabh.zzwW());
            if (zzb2 == null) {
                zzb2 = new zzb(zzabh);
            }
            this.zzaWg.put(zzabh.zzwW(), zzb2);
        }
        return zzb2;
    }

    private zza zzg(zzabh<LocationCallback> zzabh) {
        zza zza2;
        synchronized (this.zzbkK) {
            zza2 = this.zzbkK.get(zzabh.zzwW());
            if (zza2 == null) {
                zza2 = new zza(zzabh);
            }
            this.zzbkK.put(zzabh.zzwW(), zza2);
        }
        return zza2;
    }

    public Location getLastLocation() {
        this.zzbkx.zzxC();
        try {
            return this.zzbkx.zzxD().zzeR(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.zzaWg) {
                for (zzb next : this.zzaWg.values()) {
                    if (next != null) {
                        this.zzbkx.zzxD().zza(zzask.zza((zzk) next, (zzasc) null));
                    }
                }
                this.zzaWg.clear();
            }
            synchronized (this.zzbkK) {
                for (zza next2 : this.zzbkK.values()) {
                    if (next2 != null) {
                        this.zzbkx.zzxD().zza(zzask.zza((zzj) next2, (zzasc) null));
                    }
                }
                this.zzbkK.clear();
            }
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public LocationAvailability zzIo() {
        this.zzbkx.zzxC();
        try {
            return this.zzbkx.zzxD().zzeS(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void zzIp() {
        if (this.zzbkJ) {
            try {
                zzaH(false);
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void zza(PendingIntent pendingIntent, zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zza(zzask.zzb(pendingIntent, zzasc));
    }

    public void zza(zzabh.zzb<LocationListener> zzb2, zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        zzac.zzb(zzb2, (Object) "Invalid null listener key");
        synchronized (this.zzaWg) {
            zzb remove = this.zzaWg.remove(zzb2);
            if (remove != null) {
                remove.release();
                this.zzbkx.zzxD().zza(zzask.zza((zzk) remove, zzasc));
            }
        }
    }

    public void zza(zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zza(zzasc);
    }

    public void zza(zzasi zzasi, zzabh<LocationCallback> zzabh, zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zza(zzask.zza(zzasi, (zzj) zzg(zzabh), zzasc));
    }

    public void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zza(zzask.zza(zzasi.zzb(locationRequest), pendingIntent, zzasc));
    }

    public void zza(LocationRequest locationRequest, zzabh<LocationListener> zzabh, zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zza(zzask.zza(zzasi.zzb(locationRequest), (zzk) zzf(zzabh), zzasc));
    }

    public void zzaH(boolean z) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zzaH(z);
        this.zzbkJ = z;
    }

    public void zzb(zzabh.zzb<LocationCallback> zzb2, zzasc zzasc) throws RemoteException {
        this.zzbkx.zzxC();
        zzac.zzb(zzb2, (Object) "Invalid null listener key");
        synchronized (this.zzbkK) {
            zza remove = this.zzbkK.remove(zzb2);
            if (remove != null) {
                remove.release();
                this.zzbkx.zzxD().zza(zzask.zza((zzj) remove, zzasc));
            }
        }
    }

    public void zzd(Location location) throws RemoteException {
        this.zzbkx.zzxC();
        this.zzbkx.zzxD().zzd(location);
    }
}
