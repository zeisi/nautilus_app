package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.request.zzak;
import com.google.android.gms.fitness.request.zzb;
import com.google.android.gms.fitness.request.zzbk;
import com.google.android.gms.fitness.request.zzbo;

public interface zzaos extends IInterface {

    public static abstract class zza extends Binder implements zzaos {

        /* renamed from: com.google.android.gms.internal.zzaos$zza$zza  reason: collision with other inner class name */
        private static class C0035zza implements zzaos {
            private IBinder zzrk;

            C0035zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(StartBleScanRequest startBleScanRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
                    if (startBleScanRequest != null) {
                        obtain.writeInt(1);
                        startBleScanRequest.writeToParcel(obtain, 0);
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

            public void zza(zzak zzak) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
                    if (zzak != null) {
                        obtain.writeInt(1);
                        zzak.writeToParcel(obtain, 0);
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

            public void zza(zzb zzb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
                    if (zzb != null) {
                        obtain.writeInt(1);
                        zzb.writeToParcel(obtain, 0);
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

            public void zza(zzbk zzbk) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
                    if (zzbk != null) {
                        obtain.writeInt(1);
                        zzbk.writeToParcel(obtain, 0);
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

            public void zza(zzbo zzbo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
                    if (zzbo != null) {
                        obtain.writeInt(1);
                        zzbo.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzaos zzcw(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaos)) ? new C0035zza(iBinder) : (zzaos) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.fitness.request.zzak} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: com.google.android.gms.fitness.request.zzbo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.fitness.request.zzb} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: com.google.android.gms.fitness.request.zzbk} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: com.google.android.gms.fitness.request.StartBleScanRequest} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: type inference failed for: r0v30 */
        /* JADX WARNING: type inference failed for: r0v31 */
        /* JADX WARNING: type inference failed for: r0v32 */
        /* JADX WARNING: type inference failed for: r0v33 */
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
                    case 4: goto L_0x0062;
                    case 5: goto L_0x007d;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r0 = super.onTransact(r4, r5, r6, r7)
            L_0x0009:
                return r0
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.fitness.internal.IGoogleFitBleApi"
                r6.writeString(r0)
                r0 = r1
                goto L_0x0009
            L_0x0011:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitBleApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.StartBleScanRequest> r0 = com.google.android.gms.fitness.request.StartBleScanRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.StartBleScanRequest r0 = (com.google.android.gms.fitness.request.StartBleScanRequest) r0
            L_0x0024:
                r3.zza((com.google.android.gms.fitness.request.StartBleScanRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x002c:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitBleApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbk> r0 = com.google.android.gms.fitness.request.zzbk.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbk r0 = (com.google.android.gms.fitness.request.zzbk) r0
            L_0x003f:
                r3.zza((com.google.android.gms.fitness.request.zzbk) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0047:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitBleApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzb> r0 = com.google.android.gms.fitness.request.zzb.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzb r0 = (com.google.android.gms.fitness.request.zzb) r0
            L_0x005a:
                r3.zza((com.google.android.gms.fitness.request.zzb) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0062:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitBleApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0075
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbo> r0 = com.google.android.gms.fitness.request.zzbo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbo r0 = (com.google.android.gms.fitness.request.zzbo) r0
            L_0x0075:
                r3.zza((com.google.android.gms.fitness.request.zzbo) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x007d:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitBleApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0090
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzak> r0 = com.google.android.gms.fitness.request.zzak.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzak r0 = (com.google.android.gms.fitness.request.zzak) r0
            L_0x0090:
                r3.zza((com.google.android.gms.fitness.request.zzak) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaos.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(StartBleScanRequest startBleScanRequest) throws RemoteException;

    void zza(zzak zzak) throws RemoteException;

    void zza(zzb zzb) throws RemoteException;

    void zza(zzbk zzbk) throws RemoteException;

    void zza(zzbo zzbo) throws RemoteException;
}
