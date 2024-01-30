package com.google.android.gms.internal;

import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaon;

public class zzaoc extends zzaon.zza {
    private final zzaad.zzb<DataSourcesResult> zzaUC;

    public zzaoc(zzaad.zzb<DataSourcesResult> zzb) {
        this.zzaUC = zzb;
    }

    public void zza(DataSourcesResult dataSourcesResult) {
        this.zzaUC.setResult(dataSourcesResult);
    }
}
