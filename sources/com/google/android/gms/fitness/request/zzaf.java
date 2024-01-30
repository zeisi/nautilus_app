package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaph;

public class zzaf extends zza {
    public static final Parcelable.Creator<zzaf> CREATOR = new zzag();
    private final zzaph zzaVY;
    private final int zzaiI;

    zzaf(int i, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaVY = zzaph.zza.zzcL(iBinder);
    }

    public IBinder getCallbackBinder() {
        return this.zzaVY.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzag.zza(this, parcel, i);
    }
}
