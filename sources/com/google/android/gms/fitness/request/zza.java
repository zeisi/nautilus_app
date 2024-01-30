package com.google.android.gms.fitness.request;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.zzai;
import java.util.HashMap;
import java.util.Map;

public class zza extends zzai.zza {
    private final BleScanCallback zzaVo;

    /* renamed from: com.google.android.gms.fitness.request.zza$zza  reason: collision with other inner class name */
    public static class C0018zza {
        private static final C0018zza zzaVp = new C0018zza();
        private final Map<BleScanCallback, zza> zzaVq = new HashMap();

        private C0018zza() {
        }

        public static C0018zza zzCK() {
            return zzaVp;
        }

        public zza zza(BleScanCallback bleScanCallback) {
            zza zza;
            synchronized (this.zzaVq) {
                zza = this.zzaVq.get(bleScanCallback);
                if (zza == null) {
                    zza = new zza(bleScanCallback);
                    this.zzaVq.put(bleScanCallback, zza);
                }
            }
            return zza;
        }

        public zza zzb(BleScanCallback bleScanCallback) {
            zza zza;
            synchronized (this.zzaVq) {
                zza = this.zzaVq.get(bleScanCallback);
                if (zza == null) {
                    zza = new zza(bleScanCallback);
                }
            }
            return zza;
        }
    }

    private zza(BleScanCallback bleScanCallback) {
        this.zzaVo = (BleScanCallback) zzac.zzw(bleScanCallback);
    }

    public void onDeviceFound(BleDevice bleDevice) throws RemoteException {
        this.zzaVo.onDeviceFound(bleDevice);
    }

    public void onScanStopped() throws RemoteException {
        this.zzaVo.onScanStopped();
    }
}
