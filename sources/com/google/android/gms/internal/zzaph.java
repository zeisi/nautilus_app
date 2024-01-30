package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.zzv;

public interface zzaph extends IInterface {

    public static abstract class zza extends Binder implements zzaph {

        /* renamed from: com.google.android.gms.internal.zzaph$zza$zza  reason: collision with other inner class name */
        private static class C0050zza implements zzaph {
            private IBinder zzrk;

            C0050zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(zzv zzv) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IWearableSyncInfoCallback");
                    if (zzv != null) {
                        obtain.writeInt(1);
                        zzv.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static zzaph zzcL(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IWearableSyncInfoCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaph)) ? new C0050zza(iBinder) : (zzaph) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.IWearableSyncInfoCallback");
                    zza(parcel.readInt() != 0 ? zzv.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.internal.IWearableSyncInfoCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(zzv zzv) throws RemoteException;
}
