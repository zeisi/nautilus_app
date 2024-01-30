package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzapt;

public class zzak extends zza {
    public static final Parcelable.Creator<zzak> CREATOR = new zzal();
    private final zzapt zzaWc;
    private final int zzaiI;

    zzak(int i, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaWc = zzapt.zza.zzcM(iBinder);
    }

    public zzak(zzapt zzapt) {
        this.zzaiI = 2;
        this.zzaWc = zzapt;
    }

    public IBinder getCallbackBinder() {
        return this.zzaWc.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzal.zza(this, parcel, i);
    }
}
