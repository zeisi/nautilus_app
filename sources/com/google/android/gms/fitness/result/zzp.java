package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.io.Closeable;
import java.util.Collections;
import java.util.List;

public class zzp extends zza implements Result, Closeable {
    public static final Parcelable.Creator<zzp> CREATOR = new zzq();
    private final DataHolder zzaBi;
    private final List<DataHolder> zzaXc;
    private final int zzaiI;

    zzp(int i, DataHolder dataHolder, List<DataHolder> list) {
        this.zzaiI = i;
        this.zzaBi = dataHolder;
        this.zzaXc = list == null ? Collections.singletonList(dataHolder) : list;
    }

    public void close() {
        if (this.zzaBi != null) {
            this.zzaBi.close();
        }
        for (DataHolder close : this.zzaXc) {
            close.close();
        }
    }

    public Status getStatus() {
        return new Status(this.zzaBi.getStatusCode());
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzq.zza(this, parcel, i);
    }

    /* access modifiers changed from: package-private */
    public DataHolder zzAP() {
        return this.zzaBi;
    }

    public List<DataHolder> zzDv() {
        return this.zzaXc;
    }
}
