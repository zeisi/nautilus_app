package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzapf;

public class zzapr extends zzapf.zza {
    private final zzaad.zzb<Status> zzaGN;

    public zzapr(zzaad.zzb<Status> zzb) {
        this.zzaGN = zzb;
    }

    public void zzp(Status status) {
        this.zzaGN.setResult(status);
    }
}
