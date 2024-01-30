package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.internal.zzapc;
import java.util.List;

public class zzat extends zza {
    public static final Parcelable.Creator<zzat> CREATOR = new zzau();
    private final List<DataSource> zzaVw;
    private final zzapc zzaWn;
    private final int zzaiI;

    zzat(int i, IBinder iBinder, List<DataSource> list) {
        this.zzaiI = i;
        this.zzaWn = zzapc.zza.zzcG(iBinder);
        this.zzaVw = list;
    }

    public IBinder getCallbackBinder() {
        return this.zzaWn.asBinder();
    }

    public List<DataSource> getDataSources() {
        return this.zzaVw;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public String toString() {
        return String.format("ReadStatsRequest", new Object[0]);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzau.zza(this, parcel, i);
    }
}
