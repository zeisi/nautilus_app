package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.request.zzbb;
import com.google.android.gms.fitness.request.zzbd;
import com.google.android.gms.fitness.request.zzbf;
import com.google.android.gms.fitness.request.zzbh;

public interface zzaoz extends IInterface {

    public static abstract class zza extends Binder implements zzaoz {

        /* renamed from: com.google.android.gms.internal.zzaoz$zza$zza  reason: collision with other inner class name */
        private static class C0042zza implements zzaoz {
            private IBinder zzrk;

            C0042zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(SessionInsertRequest sessionInsertRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
                    if (sessionInsertRequest != null) {
                        obtain.writeInt(1);
                        sessionInsertRequest.writeToParcel(obtain, 0);
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

            public void zza(SessionReadRequest sessionReadRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
                    if (sessionReadRequest != null) {
                        obtain.writeInt(1);
                        sessionReadRequest.writeToParcel(obtain, 0);
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

            public void zza(zzbb zzbb) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
                    if (zzbb != null) {
                        obtain.writeInt(1);
                        zzbb.writeToParcel(obtain, 0);
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

            public void zza(zzbd zzbd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
                    if (zzbd != null) {
                        obtain.writeInt(1);
                        zzbd.writeToParcel(obtain, 0);
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

            public void zza(zzbf zzbf) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
                    if (zzbf != null) {
                        obtain.writeInt(1);
                        zzbf.writeToParcel(obtain, 0);
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

            public void zza(zzbh zzbh) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
                    if (zzbh != null) {
                        obtain.writeInt(1);
                        zzbh.writeToParcel(obtain, 0);
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
        }

        public static zzaoz zzcD(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaoz)) ? new C0042zza(iBinder) : (zzaoz) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.fitness.request.zzbh} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: com.google.android.gms.fitness.request.zzbb} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.fitness.request.SessionReadRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: com.google.android.gms.fitness.request.SessionInsertRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: com.google.android.gms.fitness.request.zzbf} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: com.google.android.gms.fitness.request.zzbd} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v34 */
        /* JADX WARNING: type inference failed for: r0v35 */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: type inference failed for: r0v37 */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r0v39 */
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
                    case 6: goto L_0x0099;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r0 = super.onTransact(r4, r5, r6, r7)
            L_0x0009:
                return r0
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r6.writeString(r0)
                r0 = r1
                goto L_0x0009
            L_0x0011:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbd> r0 = com.google.android.gms.fitness.request.zzbd.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbd r0 = (com.google.android.gms.fitness.request.zzbd) r0
            L_0x0024:
                r3.zza((com.google.android.gms.fitness.request.zzbd) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x002c:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbf> r0 = com.google.android.gms.fitness.request.zzbf.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbf r0 = (com.google.android.gms.fitness.request.zzbf) r0
            L_0x003f:
                r3.zza((com.google.android.gms.fitness.request.zzbf) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0047:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.SessionInsertRequest> r0 = com.google.android.gms.fitness.request.SessionInsertRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.SessionInsertRequest r0 = (com.google.android.gms.fitness.request.SessionInsertRequest) r0
            L_0x005a:
                r3.zza((com.google.android.gms.fitness.request.SessionInsertRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0062:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0075
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.SessionReadRequest> r0 = com.google.android.gms.fitness.request.SessionReadRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.SessionReadRequest r0 = (com.google.android.gms.fitness.request.SessionReadRequest) r0
            L_0x0075:
                r3.zza((com.google.android.gms.fitness.request.SessionReadRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x007d:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0090
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbb> r0 = com.google.android.gms.fitness.request.zzbb.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbb r0 = (com.google.android.gms.fitness.request.zzbb) r0
            L_0x0090:
                r3.zza((com.google.android.gms.fitness.request.zzbb) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0099:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x00ac
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbh> r0 = com.google.android.gms.fitness.request.zzbh.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbh r0 = (com.google.android.gms.fitness.request.zzbh) r0
            L_0x00ac:
                r3.zza((com.google.android.gms.fitness.request.zzbh) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaoz.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(SessionInsertRequest sessionInsertRequest) throws RemoteException;

    void zza(SessionReadRequest sessionReadRequest) throws RemoteException;

    void zza(zzbb zzbb) throws RemoteException;

    void zza(zzbd zzbd) throws RemoteException;

    void zza(zzbf zzbf) throws RemoteException;

    void zza(zzbh zzbh) throws RemoteException;
}
