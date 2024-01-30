package com.google.android.gms.fitness.request;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.data.zzae;

public interface zzaj extends IInterface {

    public static abstract class zza extends Binder implements zzaj {

        /* renamed from: com.google.android.gms.fitness.request.zzaj$zza$zza  reason: collision with other inner class name */
        private static class C0020zza implements zzaj {
            private IBinder zzrk;

            C0020zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zzb(zzae zzae) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.request.IWearableSyncStatusListener");
                    if (zzae != null) {
                        obtain.writeInt(1);
                        zzae.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static zzaj zzcO(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.request.IWearableSyncStatusListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaj)) ? new C0020zza(iBinder) : (zzaj) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.request.IWearableSyncStatusListener");
                    zzb(parcel.readInt() != 0 ? zzae.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.request.IWearableSyncStatusListener");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zzb(zzae zzae) throws RemoteException;
}
