package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzapb;
import java.util.List;

public class zzar extends zza {
    public static final Parcelable.Creator<zzar> CREATOR = new zzas();
    private final int versionCode;
    private final zzapb zzaWj;
    private final List<zzj> zzaWk;
    private final boolean zzaWl;
    private final boolean zzaWm;

    zzar(int i, IBinder iBinder, List<zzj> list, boolean z, boolean z2) {
        this.versionCode = i;
        this.zzaWj = zzapb.zza.zzcF(iBinder);
        this.zzaWk = list;
        this.zzaWl = z;
        this.zzaWm = z2;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWj != null) {
            return this.zzaWj.asBinder();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("params", this.zzaWk).zzg("server", Boolean.valueOf(this.zzaWm)).zzg("flush", Boolean.valueOf(this.zzaWl)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzas.zza(this, parcel, i);
    }

    public boolean zzCR() {
        return this.zzaWm;
    }

    public boolean zzCS() {
        return this.zzaWl;
    }

    public List<zzj> zzDb() {
        return this.zzaWk;
    }
}
