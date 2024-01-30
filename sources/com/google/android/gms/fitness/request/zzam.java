package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzapa;

public class zzam extends zza {
    public static final Parcelable.Creator<zzam> CREATOR = new zzan();
    private final DataType zzaTj;
    private final zzapa zzaWd;
    private final int zzaiI;

    zzam(int i, DataType dataType, IBinder iBinder) {
        this.zzaiI = i;
        this.zzaTj = dataType;
        this.zzaWd = zzapa.zza.zzcE(iBinder);
    }

    public zzam(DataType dataType, zzapa zzapa) {
        this.zzaiI = 3;
        this.zzaTj = dataType;
        this.zzaWd = zzapa;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWd == null) {
            return null;
        }
        return this.zzaWd.asBinder();
    }

    public DataType getDataType() {
        return this.zzaTj;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.zzaiI;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzan.zza(this, parcel, i);
    }
}
