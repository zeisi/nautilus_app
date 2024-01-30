package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.fitness.data.DataType;

public class DataTypeResult extends zza implements Result {
    public static final Parcelable.Creator<DataTypeResult> CREATOR = new zzi();
    private final DataType zzaTj;
    private final int zzaiI;
    private final Status zzair;

    DataTypeResult(int i, Status status, DataType dataType) {
        this.zzaiI = i;
        this.zzair = status;
        this.zzaTj = dataType;
    }

    public DataTypeResult(Status status, DataType dataType) {
        this.zzaiI = 2;
        this.zzair = status;
        this.zzaTj = dataType;
    }

    public static DataTypeResult zzaf(Status status) {
        return new DataTypeResult(status, (DataType) null);
    }

    private boolean zzb(DataTypeResult dataTypeResult) {
        return this.zzair.equals(dataTypeResult.zzair) && zzaa.equal(this.zzaTj, dataTypeResult.zzaTj);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof DataTypeResult) && zzb((DataTypeResult) obj));
    }

    public DataType getDataType() {
        return this.zzaTj;
    }

    public Status getStatus() {
        return this.zzair;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzair, this.zzaTj);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("status", this.zzair).zzg("dataType", this.zzaTj).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
