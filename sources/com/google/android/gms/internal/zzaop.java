package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.zzj;

public interface zzaop extends IInterface {

    public static abstract class zza extends Binder implements zzaop {

        /* renamed from: com.google.android.gms.internal.zzaop$zza$zza  reason: collision with other inner class name */
        private static class C0032zza implements zzaop {
            private IBinder zzrk;

            C0032zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(zzj zzj) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IDebugInfoCallback");
                    if (zzj != null) {
                        obtain.writeInt(1);
                        zzj.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public static zzaop zzct(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDebugInfoCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaop)) ? new C0032zza(iBinder) : (zzaop) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.IDebugInfoCallback");
                    zza(parcel.readInt() != 0 ? zzj.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.internal.IDebugInfoCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(zzj zzj) throws RemoteException;
}
