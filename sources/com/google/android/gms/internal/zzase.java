package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzabb;
import com.google.android.gms.internal.zzasc;
import com.google.android.gms.internal.zzasd;
import com.google.android.gms.internal.zzasf;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzf;
import com.google.android.gms.location.zzk;
import com.google.android.gms.location.zzt;
import java.util.List;

public interface zzase extends IInterface {

    public static abstract class zza extends Binder implements zzase {

        /* renamed from: com.google.android.gms.internal.zzase$zza$zza  reason: collision with other inner class name */
        private static class C0055zza implements zzase {
            private IBinder zzrk;

            C0055zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public Location zzIn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.zzrk.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeLong(j);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PendingIntent pendingIntent, zzasd zzasd, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzasd != null ? zzasd.asBinder() : null);
                    obtain.writeString(str);
                    this.zzrk.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(Location location, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.zzrk.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzasc zzasc) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzasc != null ? zzasc.asBinder() : null);
                    this.zzrk.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzasd zzasd, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzasd != null ? zzasd.asBinder() : null);
                    obtain.writeString(str);
                    this.zzrk.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzasi zzasi, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (zzasi != null) {
                        obtain.writeInt(1);
                        zzasi.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzasi zzasi, zzk zzk) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (zzasi != null) {
                        obtain.writeInt(1);
                        zzasi.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzk != null ? zzk.asBinder() : null);
                    this.zzrk.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzask zzask) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (zzask != null) {
                        obtain.writeInt(1);
                        zzask.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzasd zzasd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (geofencingRequest != null) {
                        obtain.writeInt(1);
                        geofencingRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzasd != null ? zzasd.asBinder() : null);
                    this.zzrk.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequest locationRequest, zzk zzk) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzk != null ? zzk.asBinder() : null);
                    this.zzrk.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequest locationRequest, zzk zzk, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzk != null ? zzk.asBinder() : null);
                    obtain.writeString(str);
                    this.zzrk.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationSettingsRequest locationSettingsRequest, zzasf zzasf, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationSettingsRequest != null) {
                        obtain.writeInt(1);
                        locationSettingsRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzasf != null ? zzasf.asBinder() : null);
                    obtain.writeString(str);
                    this.zzrk.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(com.google.android.gms.location.zza zza, PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (zza != null) {
                        obtain.writeInt(1);
                        zza.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzf zzf, PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (zzf != null) {
                        obtain.writeInt(1);
                        zzf.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzk zzk) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzk != null ? zzk.asBinder() : null);
                    this.zzrk.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzt zzt, zzasd zzasd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (zzt != null) {
                        obtain.writeInt(1);
                        zzt.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzasd != null ? zzasd.asBinder() : null);
                    this.zzrk.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(List<zzasm> list, PendingIntent pendingIntent, zzasd zzasd, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeTypedList(list);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzasd != null ? zzasd.asBinder() : null);
                    obtain.writeString(str);
                    this.zzrk.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String[] strArr, zzasd zzasd, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(zzasd != null ? zzasd.asBinder() : null);
                    obtain.writeString(str);
                    this.zzrk.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzaH(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzrk.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzc(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzc(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzd(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzd(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzd(Location location) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zze(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzabb != null ? zzabb.asBinder() : null);
                    this.zzrk.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ActivityRecognitionResult zzeQ(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.zzrk.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? ActivityRecognitionResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location zzeR(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.zzrk.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public LocationAvailability zzeS(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.zzrk.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? LocationAvailability.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzase zzdi(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzase)) ? new C0055zza(iBinder) : (zzase) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.createTypedArrayList(zzasm.CREATOR), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzasd.zza.zzdh(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzasd.zza.zzdh(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.createStringArray(), zzasd.zza.zzdh(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(zzasd.zza.zzdh(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readLong(), parcel.readInt() != 0, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzc(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    Location zzIn = zzIn();
                    parcel2.writeNoException();
                    if (zzIn != null) {
                        parcel2.writeInt(1);
                        zzIn.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? LocationRequest.CREATOR.createFromParcel(parcel) : null, zzk.zza.zzde(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? LocationRequest.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(zzk.zza.zzde(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzd(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    zzaH(z);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzd(parcel.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? LocationRequest.CREATOR.createFromParcel(parcel) : null, zzk.zza.zzde(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    Location zzeR = zzeR(parcel.readString());
                    parcel2.writeNoException();
                    if (zzeR != null) {
                        parcel2.writeInt(1);
                        zzeR.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 26:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(parcel) : null, parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 34:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    LocationAvailability zzeS = zzeS(parcel.readString());
                    parcel2.writeNoException();
                    if (zzeS != null) {
                        parcel2.writeInt(1);
                        zzeS.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 52:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? zzasi.CREATOR.createFromParcel(parcel) : null, zzk.zza.zzde(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 53:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? zzasi.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? GeofencingRequest.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzasd.zza.zzdh(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 59:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? zzask.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? LocationSettingsRequest.CREATOR.createFromParcel(parcel) : null, zzasf.zza.zzdj(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 64:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    ActivityRecognitionResult zzeQ = zzeQ(parcel.readString());
                    parcel2.writeNoException();
                    if (zzeQ != null) {
                        parcel2.writeInt(1);
                        zzeQ.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 65:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzb(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 66:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzc(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 67:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(zzasc.zza.zzdg(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 68:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzd(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 69:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zze(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 70:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? com.google.android.gms.location.zza.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 71:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zzb(zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 72:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? zzf.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 73:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, zzabb.zza.zzbp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 74:
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    zza(parcel.readInt() != 0 ? zzt.CREATOR.createFromParcel(parcel) : null, zzasd.zza.zzdh(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    Location zzIn() throws RemoteException;

    void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException;

    void zza(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    void zza(PendingIntent pendingIntent, zzasd zzasd, String str) throws RemoteException;

    void zza(Location location, int i) throws RemoteException;

    void zza(zzasc zzasc) throws RemoteException;

    void zza(zzasd zzasd, String str) throws RemoteException;

    void zza(zzasi zzasi, PendingIntent pendingIntent) throws RemoteException;

    void zza(zzasi zzasi, zzk zzk) throws RemoteException;

    void zza(zzask zzask) throws RemoteException;

    void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzasd zzasd) throws RemoteException;

    void zza(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException;

    void zza(LocationRequest locationRequest, zzk zzk) throws RemoteException;

    void zza(LocationRequest locationRequest, zzk zzk, String str) throws RemoteException;

    void zza(LocationSettingsRequest locationSettingsRequest, zzasf zzasf, String str) throws RemoteException;

    void zza(com.google.android.gms.location.zza zza2, PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    void zza(zzf zzf, PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    void zza(zzk zzk) throws RemoteException;

    void zza(zzt zzt, zzasd zzasd) throws RemoteException;

    void zza(List<zzasm> list, PendingIntent pendingIntent, zzasd zzasd, String str) throws RemoteException;

    void zza(String[] strArr, zzasd zzasd, String str) throws RemoteException;

    void zzaH(boolean z) throws RemoteException;

    void zzb(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    void zzb(zzabb zzabb) throws RemoteException;

    void zzc(PendingIntent pendingIntent) throws RemoteException;

    void zzc(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    void zzd(PendingIntent pendingIntent) throws RemoteException;

    void zzd(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    void zzd(Location location) throws RemoteException;

    void zze(PendingIntent pendingIntent, zzabb zzabb) throws RemoteException;

    ActivityRecognitionResult zzeQ(String str) throws RemoteException;

    Location zzeR(String str) throws RemoteException;

    LocationAvailability zzeS(String str) throws RemoteException;
}
