package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;

public class zzapx extends zza {
    public static final Parcelable.Creator<zzapx> CREATOR = new zzapy();
    private final DataSource zzaTi;
    private final int zzaiI;

    zzapx(int i, DataSource dataSource) {
        this.zzaiI = i;
        this.zzaTi = dataSource;
    }

    public DataSource getDataSource() {
        return this.zzaTi;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return String.format("ApplicationUnregistrationRequest{%s}", new Object[]{this.zzaTi});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzapy.zza(this, parcel, i);
    }
}
