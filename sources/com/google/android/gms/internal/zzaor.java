package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.GoalsResult;

public interface zzaor extends IInterface {

    public static abstract class zza extends Binder implements zzaor {

        /* renamed from: com.google.android.gms.internal.zzaor$zza$zza  reason: collision with other inner class name */
        private static class C0034zza implements zzaor {
            private IBinder zzrk;

            C0034zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(GoalsResult goalsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoalsReadCallback");
                    if (goalsResult != null) {
                        obtain.writeInt(1);
                        goalsResult.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(1, obtain, (Parcel) null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.fitness.internal.IGoalsReadCallback");
        }

        public static zzaor zzcv(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoalsReadCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaor)) ? new C0034zza(iBinder) : (zzaor) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.IGoalsReadCallback");
                    zza(parcel.readInt() != 0 ? GoalsResult.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.internal.IGoalsReadCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(GoalsResult goalsResult) throws RemoteException;
}
