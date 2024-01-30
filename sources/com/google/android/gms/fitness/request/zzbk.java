package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.request.zza;
import com.google.android.gms.fitness.request.zzai;
import com.google.android.gms.internal.zzapf;

public class zzbk extends zza {
    public static final Parcelable.Creator<zzbk> CREATOR = new zzbl();
    private final zzapf zzaVt;
    private final zzai zzaWJ;
    private final int zzaiI;

    zzbk(int i, IBinder iBinder, IBinder iBinder2) {
        this.zzaiI = i;
        this.zzaWJ = zzai.zza.zzcN(iBinder);
        this.zzaVt = zzapf.zza.zzcJ(iBinder2);
    }

    public zzbk(BleScanCallback bleScanCallback, zzapf zzapf) {
        this((zzai) zza.C0018zza.zzCK().zzb(bleScanCallback), zzapf);
    }

    public zzbk(zzai zzai, zzapf zzapf) {
        this.zzaiI = 3;
        this.zzaWJ = zzai;
        this.zzaVt = zzapf;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaVt == null) {
            return null;
        }
        return this.zzaVt.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbl.zza(this, parcel, i);
    }

    public IBinder zzDl() {
        return this.zzaWJ.asBinder();
    }
}
