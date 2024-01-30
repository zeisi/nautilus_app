package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.request.zzad;
import com.google.android.gms.fitness.request.zzaf;
import com.google.android.gms.fitness.request.zzar;
import com.google.android.gms.fitness.request.zzat;
import com.google.android.gms.fitness.request.zzbs;
import com.google.android.gms.fitness.request.zzd;
import com.google.android.gms.fitness.request.zzg;
import com.google.android.gms.fitness.request.zzq;
import com.google.android.gms.fitness.request.zzt;
import com.google.android.gms.fitness.request.zzz;

public interface zzaov extends IInterface {

    public static abstract class zza extends Binder implements zzaov {

        /* renamed from: com.google.android.gms.internal.zzaov$zza$zza  reason: collision with other inner class name */
        private static class C0038zza implements zzaov {
            private IBinder zzrk;

            C0038zza(IBinder iBinder) {
                this.zzrk = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrk;
            }

            public void zza(DataDeleteRequest dataDeleteRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (dataDeleteRequest != null) {
                        obtain.writeInt(1);
                        dataDeleteRequest.writeToParcel(obtain, 0);
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

            public void zza(DataReadRequest dataReadRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (dataReadRequest != null) {
                        obtain.writeInt(1);
                        dataReadRequest.writeToParcel(obtain, 0);
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

            public void zza(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (dataUpdateListenerRegistrationRequest != null) {
                        obtain.writeInt(1);
                        dataUpdateListenerRegistrationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(DataUpdateRequest dataUpdateRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (dataUpdateRequest != null) {
                        obtain.writeInt(1);
                        dataUpdateRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzad zzad) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzad != null) {
                        obtain.writeInt(1);
                        zzad.writeToParcel(obtain, 0);
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

            public void zza(zzaf zzaf) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzaf != null) {
                        obtain.writeInt(1);
                        zzaf.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzar zzar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzar != null) {
                        obtain.writeInt(1);
                        zzar.writeToParcel(obtain, 0);
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

            public void zza(zzat zzat) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzat != null) {
                        obtain.writeInt(1);
                        zzat.writeToParcel(obtain, 0);
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

            public void zza(zzbs zzbs) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzbs != null) {
                        obtain.writeInt(1);
                        zzbs.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzd zzd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzd != null) {
                        obtain.writeInt(1);
                        zzd.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzg zzg) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzg != null) {
                        obtain.writeInt(1);
                        zzg.writeToParcel(obtain, 0);
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

            public void zza(zzq zzq) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzq != null) {
                        obtain.writeInt(1);
                        zzq.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzt zzt) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzt != null) {
                        obtain.writeInt(1);
                        zzt.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzz zzz) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzz != null) {
                        obtain.writeInt(1);
                        zzz.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(zzg zzg) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
                    if (zzg != null) {
                        obtain.writeInt(1);
                        zzg.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzrk.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzaov zzcz(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzaov)) ? new C0038zza(iBinder) : (zzaov) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.google.android.gms.fitness.request.zzaf} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: com.google.android.gms.fitness.request.zzbs} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: com.google.android.gms.fitness.request.zzt} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: com.google.android.gms.fitness.request.zzz} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: com.google.android.gms.fitness.request.zzq} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: com.google.android.gms.fitness.request.DataUpdateRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: com.google.android.gms.fitness.request.zzg} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v41, resolved type: com.google.android.gms.fitness.request.zzd} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v46, resolved type: com.google.android.gms.fitness.request.zzar} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v51, resolved type: com.google.android.gms.fitness.request.zzat} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v56, resolved type: com.google.android.gms.fitness.request.zzad} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v61, resolved type: com.google.android.gms.fitness.request.DataDeleteRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v66, resolved type: com.google.android.gms.fitness.request.zzg} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v71, resolved type: com.google.android.gms.fitness.request.DataReadRequest} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v79 */
        /* JADX WARNING: type inference failed for: r0v80 */
        /* JADX WARNING: type inference failed for: r0v81 */
        /* JADX WARNING: type inference failed for: r0v82 */
        /* JADX WARNING: type inference failed for: r0v83 */
        /* JADX WARNING: type inference failed for: r0v84 */
        /* JADX WARNING: type inference failed for: r0v85 */
        /* JADX WARNING: type inference failed for: r0v86 */
        /* JADX WARNING: type inference failed for: r0v87 */
        /* JADX WARNING: type inference failed for: r0v88 */
        /* JADX WARNING: type inference failed for: r0v89 */
        /* JADX WARNING: type inference failed for: r0v90 */
        /* JADX WARNING: type inference failed for: r0v91 */
        /* JADX WARNING: type inference failed for: r0v92 */
        /* JADX WARNING: type inference failed for: r0v93 */
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
                    case 7: goto L_0x00b5;
                    case 8: goto L_0x00d1;
                    case 9: goto L_0x00ed;
                    case 10: goto L_0x0109;
                    case 11: goto L_0x0125;
                    case 12: goto L_0x0141;
                    case 13: goto L_0x015d;
                    case 14: goto L_0x0179;
                    case 15: goto L_0x0195;
                    case 1598968902: goto L_0x000a;
                    default: goto L_0x0005;
                }
            L_0x0005:
                boolean r0 = super.onTransact(r4, r5, r6, r7)
            L_0x0009:
                return r0
            L_0x000a:
                java.lang.String r0 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r6.writeString(r0)
                r0 = r1
                goto L_0x0009
            L_0x0011:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0024
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.DataReadRequest> r0 = com.google.android.gms.fitness.request.DataReadRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.DataReadRequest r0 = (com.google.android.gms.fitness.request.DataReadRequest) r0
            L_0x0024:
                r3.zza((com.google.android.gms.fitness.request.DataReadRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x002c:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x003f
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzg> r0 = com.google.android.gms.fitness.request.zzg.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzg r0 = (com.google.android.gms.fitness.request.zzg) r0
            L_0x003f:
                r3.zza((com.google.android.gms.fitness.request.zzg) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0047:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x005a
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.DataDeleteRequest> r0 = com.google.android.gms.fitness.request.DataDeleteRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.DataDeleteRequest r0 = (com.google.android.gms.fitness.request.DataDeleteRequest) r0
            L_0x005a:
                r3.zza((com.google.android.gms.fitness.request.DataDeleteRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0062:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0075
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzad> r0 = com.google.android.gms.fitness.request.zzad.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzad r0 = (com.google.android.gms.fitness.request.zzad) r0
            L_0x0075:
                r3.zza((com.google.android.gms.fitness.request.zzad) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x007d:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0090
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzat> r0 = com.google.android.gms.fitness.request.zzat.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzat r0 = (com.google.android.gms.fitness.request.zzat) r0
            L_0x0090:
                r3.zza((com.google.android.gms.fitness.request.zzat) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0099:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x00ac
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzar> r0 = com.google.android.gms.fitness.request.zzar.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzar r0 = (com.google.android.gms.fitness.request.zzar) r0
            L_0x00ac:
                r3.zza((com.google.android.gms.fitness.request.zzar) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x00b5:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x00c8
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzd> r0 = com.google.android.gms.fitness.request.zzd.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzd r0 = (com.google.android.gms.fitness.request.zzd) r0
            L_0x00c8:
                r3.zza((com.google.android.gms.fitness.request.zzd) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x00d1:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x00e4
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzg> r0 = com.google.android.gms.fitness.request.zzg.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzg r0 = (com.google.android.gms.fitness.request.zzg) r0
            L_0x00e4:
                r3.zzb(r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x00ed:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0100
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.DataUpdateRequest> r0 = com.google.android.gms.fitness.request.DataUpdateRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.DataUpdateRequest r0 = (com.google.android.gms.fitness.request.DataUpdateRequest) r0
            L_0x0100:
                r3.zza((com.google.android.gms.fitness.request.DataUpdateRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0109:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x011c
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest> r0 = com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest r0 = (com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest) r0
            L_0x011c:
                r3.zza((com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0125:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0138
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzq> r0 = com.google.android.gms.fitness.request.zzq.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzq r0 = (com.google.android.gms.fitness.request.zzq) r0
            L_0x0138:
                r3.zza((com.google.android.gms.fitness.request.zzq) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0141:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0154
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzz> r0 = com.google.android.gms.fitness.request.zzz.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzz r0 = (com.google.android.gms.fitness.request.zzz) r0
            L_0x0154:
                r3.zza((com.google.android.gms.fitness.request.zzz) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x015d:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0170
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzt> r0 = com.google.android.gms.fitness.request.zzt.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzt r0 = (com.google.android.gms.fitness.request.zzt) r0
            L_0x0170:
                r3.zza((com.google.android.gms.fitness.request.zzt) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0179:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x018c
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzbs> r0 = com.google.android.gms.fitness.request.zzbs.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzbs r0 = (com.google.android.gms.fitness.request.zzbs) r0
            L_0x018c:
                r3.zza((com.google.android.gms.fitness.request.zzbs) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            L_0x0195:
                java.lang.String r2 = "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi"
                r5.enforceInterface(r2)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x01a8
                android.os.Parcelable$Creator<com.google.android.gms.fitness.request.zzaf> r0 = com.google.android.gms.fitness.request.zzaf.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r5)
                com.google.android.gms.fitness.request.zzaf r0 = (com.google.android.gms.fitness.request.zzaf) r0
            L_0x01a8:
                r3.zza((com.google.android.gms.fitness.request.zzaf) r0)
                r6.writeNoException()
                r0 = r1
                goto L_0x0009
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaov.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(DataDeleteRequest dataDeleteRequest) throws RemoteException;

    void zza(DataReadRequest dataReadRequest) throws RemoteException;

    void zza(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) throws RemoteException;

    void zza(DataUpdateRequest dataUpdateRequest) throws RemoteException;

    void zza(zzad zzad) throws RemoteException;

    void zza(zzaf zzaf) throws RemoteException;

    void zza(zzar zzar) throws RemoteException;

    void zza(zzat zzat) throws RemoteException;

    void zza(zzbs zzbs) throws RemoteException;

    void zza(zzd zzd) throws RemoteException;

    void zza(zzg zzg) throws RemoteException;

    void zza(zzq zzq) throws RemoteException;

    void zza(zzt zzt) throws RemoteException;

    void zza(zzz zzz) throws RemoteException;

    void zzb(zzg zzg) throws RemoteException;
}
