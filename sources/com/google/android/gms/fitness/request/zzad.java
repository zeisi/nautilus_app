package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzapg;

public class zzad extends zza {
    public static final Parcelable.Creator<zzad> CREATOR = new zzae();
    private final zzapg zzaVX;
    private final int zzaiI;

    zzad(int i, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaVX = zzapg.zza.zzcK(iBinder);
    }

    public IBinder getCallbackBinder() {
        return this.zzaVX.asBinder();
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return String.format("GetSyncInfoRequest {%d, %s}", new Object[]{Integer.valueOf(this.zzaiI), this.zzaVX});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzae.zza(this, parcel, i);
    }
}
