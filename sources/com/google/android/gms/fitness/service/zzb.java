package com.google.android.gms.fitness.service;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.zzs;
import java.util.List;

class zzb implements SensorEventDispatcher {
    private final zzs zzaWo;

    zzb(zzs zzs) {
        this.zzaWo = (zzs) zzac.zzw(zzs);
    }

    public void publish(DataPoint dataPoint) throws RemoteException {
        dataPoint.zzCd();
        this.zzaWo.zzf(dataPoint);
    }

    public void publish(List<DataPoint> list) throws RemoteException {
        for (DataPoint publish : list) {
            publish(publish);
        }
    }
}
