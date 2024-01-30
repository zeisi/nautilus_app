package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.GoalsReadRequest;

public interface zzaou extends IInterface {

    public static abstract class zza extends Binder implements zzaou {

        /* renamed from: com.google.android.gms.internal.zzaou$zza$zza  reason: collision with other inner class name */
        private static class C0037zza implements zzaou {
            private IBinder zzrk;

            C0037zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(GoalsReadRequest goalsReadRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
                    if (goalsReadRequest != null) {
                        obtain.writeInt(1);
                        goalsReadRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzaou zzcy(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaou)) ? new C0037zza(iBinder) : (zzaou) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
                    zza(parcel.readInt() != 0 ? GoalsReadRequest.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(GoalsReadRequest goalsReadRequest) throws RemoteException;
}
