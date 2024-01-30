package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.result.DailyTotalResult;

public interface zzaol extends IInterface {

    public static abstract class zza extends Binder implements zzaol {

        /* renamed from: com.google.android.gms.internal.zzaol$zza$zza  reason: collision with other inner class name */
        private static class C0028zza implements zzaol {
            private IBinder zzrk;

            C0028zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IDailyTotalCallback");
                    if (dailyTotalResult != null) {
                        obtain.writeInt(1);
                        dailyTotalResult.writeToParcel(obtain, 0);
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
            attachInterface(this, "com.google.android.gms.fitness.internal.IDailyTotalCallback");
        }

        public static zzaol zzcp(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IDailyTotalCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaol)) ? new C0028zza(iBinder) : (zzaol) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.fitness.internal.IDailyTotalCallback");
                    zza(parcel.readInt() != 0 ? DailyTotalResult.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.fitness.internal.IDailyTotalCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(DailyTotalResult dailyTotalResult) throws RemoteException;
}
