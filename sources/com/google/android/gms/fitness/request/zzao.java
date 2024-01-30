package com.google.android.gms.fitness.request;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.zzs;
import java.util.HashMap;
import java.util.Map;

public class zzao extends zzs.zza {
    private final OnDataPointListener zzaWe;

    public static class zza {
        private static final zza zzaWf = new zza();
        private final Map<OnDataPointListener, zzao> zzaWg = new HashMap();

        private zza() {
        }

        public static zza zzCZ() {
            return zzaWf;
        }

        public zzao zza(OnDataPointListener onDataPointListener) {
            zzao zzao;
            synchronized (this.zzaWg) {
                zzao = this.zzaWg.get(onDataPointListener);
                if (zzao == null) {
                    zzao = new zzao(onDataPointListener);
                    this.zzaWg.put(onDataPointListener, zzao);
                }
            }
            return zzao;
        }

        public zzao zzb(OnDataPointListener onDataPointListener) {
            zzao zzao;
            synchronized (this.zzaWg) {
                zzao = this.zzaWg.get(onDataPointListener);
            }
            return zzao;
        }

        public zzao zzc(OnDataPointListener onDataPointListener) {
            zzao remove;
            synchronized (this.zzaWg) {
                remove = this.zzaWg.remove(onDataPointListener);
                if (remove == null) {
                    remove = new zzao(onDataPointListener);
                }
            }
            return remove;
        }
    }

    private zzao(OnDataPointListener onDataPointListener) {
        this.zzaWe = (OnDataPointListener) zzac.zzw(onDataPointListener);
    }

    public void zzf(DataPoint dataPoint) throws RemoteException {
        this.zzaWe.onDataPoint(dataPoint);
    }
}
