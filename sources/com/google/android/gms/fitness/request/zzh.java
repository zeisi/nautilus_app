package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSet;

public class zzh implements Parcelable.Creator<zzg> {
    static void zza(zzg zzg, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzg.getDataSet(), i, false);
        zzc.zza(parcel, 2, zzg.getCallbackBinder(), false);
        zzc.zza(parcel, 4, zzg.zzCQ());
        zzc.zzc(parcel, 1000, zzg.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzev */
    public zzg createFromParcel(Parcel parcel) {
        IBinder iBinder;
        DataSet dataSet;
        int zzg;
        boolean z;
        IBinder iBinder2 = null;
        boolean z2 = false;
        int zzaY = zzb.zzaY(parcel);
        DataSet dataSet2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    IBinder iBinder3 = iBinder2;
                    dataSet = (DataSet) zzb.zza(parcel, zzaX, DataSet.CREATOR);
                    z = z2;
                    iBinder = iBinder3;
                    break;
                case 2:
                    dataSet = dataSet2;
                    zzg = i;
                    boolean z3 = z2;
                    iBinder = zzb.zzr(parcel, zzaX);
                    z = z3;
                    break;
                case 4:
                    z = zzb.zzc(parcel, zzaX);
                    iBinder = iBinder2;
                    dataSet = dataSet2;
                    zzg = i;
                    break;
                case 1000:
                    boolean z4 = z2;
                    iBinder = iBinder2;
                    dataSet = dataSet2;
                    zzg = zzb.zzg(parcel, zzaX);
                    z = z4;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    z = z2;
                    iBinder = iBinder2;
                    dataSet = dataSet2;
                    zzg = i;
                    break;
            }
            i = zzg;
            dataSet2 = dataSet;
            iBinder2 = iBinder;
            z2 = z;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzg(i, dataSet2, iBinder2, z2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhi */
    public zzg[] newArray(int i) {
        return new zzg[i];
    }
}
