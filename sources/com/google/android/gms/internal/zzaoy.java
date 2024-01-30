package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.zzav;
import com.google.android.gms.fitness.request.zzax;

public interface zzaoy extends IInterface {

    public static abstract class zza extends Binder implements zzaoy {

        /* renamed from: com.google.android.gms.internal.zzaoy$zza$zza  reason: collision with other inner class name */
        private static class C0041zza implements zzaoy {
            private IBinder zzrk;

            C0041zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(DataSourcesRequest dataSourcesRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
                    if (dataSourcesRequest != null) {
                        obtain.writeInt(1);
                        dataSourcesRequest.writeToParcel(obtain, 0);
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

            public void zza(zzav zzav) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
                    if (zzav != null) {
                        obtain.writeInt(1);
                        zzav.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzax zzax) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
                    if (zzax != null) {
                        obtain.writeInt(1);
                        zzax.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzaoy zzcC(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaoy)) ? new C0041zza(iBinder) : (zzaoy) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.fitness.request.zzax} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: com.google.android.gms.fitness.request.zzav} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.fitness.request.DataSourcesRequest} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v19 */
        /* JADX WARNING: type inference failed for: r0v20 */
        /* JADX WARNING: type inference failed for: r0v21 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) throws android.os.RemoteException {
            /*
                r3 = this;
                r0 = 0
                r1 = 1
                switch(r4) {
                    case 1: goto L_0x0011;
                    case 2: goto L_0x002c;
                    case 3: goto L_0x0047;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r0 = super.onTransact(r4, r5, r6, r7)
            L_0x0009:
                return r0
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi"
                r6.writeString(r0)
                r0 = r1
                goto L_0x0009
            L_0x0011:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.DataSourcesRequest> r0 = com.google.android.gms.fitness.request.DataSourcesRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.DataSourcesRequest r0 = (com.google.android.gms.fitness.request.DataSourcesRequest) r0
            L_0x0024:
                r3.zza((com.google.android.gms.fitness.request.DataSourcesRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x002c:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzav> r0 = com.google.android.gms.fitness.request.zzav.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzav r0 = (com.google.android.gms.fitness.request.zzav) r0
            L_0x003f:
                r3.zza((com.google.android.gms.fitness.request.zzav) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0047:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzax> r0 = com.google.android.gms.fitness.request.zzax.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzax r0 = (com.google.android.gms.fitness.request.zzax) r0
            L_0x005a:
                r3.zza((com.google.android.gms.fitness.request.zzax) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaoy.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(DataSourcesRequest dataSourcesRequest) throws RemoteException;

    void zza(zzav zzav) throws RemoteException;

    void zza(zzax zzax) throws RemoteException;
}
