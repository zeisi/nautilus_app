package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.DataType;
import java.util.Collections;
import java.util.List;

public class zzapv extends zza {
    public static final Parcelable.Creator<zzapv> CREATOR = new zzapw();
    private final List<DataType> zzaSs;
    private final int zzaiI;

    zzapv(int i, List<DataType> list) {
        this.zzaiI = i;
        this.zzaSs = list;
    }

    public List<DataType> getDataTypes() {
        return Collections.unmodifiableList(this.zzaSs);
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return zzaa.zzv(this).zzg("dataTypes", this.zzaSs).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzapw.zza(this, parcel, i);
    }
}
