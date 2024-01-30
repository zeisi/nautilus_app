package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.request.zzn;
import com.google.android.gms.fitness.request.zzw;

public interface zzaot extends IInterface {

    public static abstract class zza extends Binder implements zzaot {

        /* renamed from: com.google.android.gms.internal.zzaot$zza$zza  reason: collision with other inner class name */
        private static class C0036zza implements zzaot {
            private IBinder zzrk;

            C0036zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(DataTypeCreateRequest dataTypeCreateRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
                    if (dataTypeCreateRequest != null) {
                        obtain.writeInt(1);
                        dataTypeCreateRequest.writeToParcel(obtain, 0);
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

            public void zza(zzn zzn) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
                    if (zzn != null) {
                        obtain.writeInt(1);
                        zzn.writeToParcel(obtain, 0);
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

            public void zza(zzw zzw) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
                    if (zzw != null) {
                        obtain.writeInt(1);
                        zzw.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzaot zzcx(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaot)) ? new C0036zza(iBinder) : (zzaot) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.fitness.request.zzw} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: com.google.android.gms.fitness.request.zzn} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.fitness.request.DataTypeCreateRequest} */
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
                    case 22: goto L_0x0047;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r0 = super.onTransact(r4, r5, r6, r7)
            L_0x0009:
                return r0
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.fitness.internal.IGoogleFitConfigApi"
                r6.writeString(r0)
                r0 = r1
                goto L_0x0009
            L_0x0011:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitConfigApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.DataTypeCreateRequest> r0 = com.google.android.gms.fitness.request.DataTypeCreateRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.DataTypeCreateRequest r0 = (com.google.android.gms.fitness.request.DataTypeCreateRequest) r0
            L_0x0024:
                r3.zza((com.google.android.gms.fitness.request.DataTypeCreateRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x002c:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitConfigApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzn> r0 = com.google.android.gms.fitness.request.zzn.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzn r0 = (com.google.android.gms.fitness.request.zzn) r0
            L_0x003f:
                r3.zza((com.google.android.gms.fitness.request.zzn) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0047:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitConfigApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzw> r0 = com.google.android.gms.fitness.request.zzw.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzw r0 = (com.google.android.gms.fitness.request.zzw) r0
            L_0x005a:
                r3.zza((com.google.android.gms.fitness.request.zzw) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaot.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(DataTypeCreateRequest dataTypeCreateRequest) throws RemoteException;

    void zza(zzn zzn) throws RemoteException;

    void zza(zzw zzw) throws RemoteException;
}
