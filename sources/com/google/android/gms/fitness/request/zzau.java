package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import java.util.ArrayList;

public class zzau implements Parcelable.Creator<zzat> {
    static void zza(zzat zzat, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, zzat.getCallbackBinder(), false);
        zzc.zzc(parcel, 3, zzat.getDataSources(), false);
        zzc.zzc(parcel, 1000, zzat.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeQ */
    public zzat createFromParcel(Parcel parcel) {
        ArrayList<DataSource> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzat(i, iBinder, arrayList);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhD */
    public zzat[] newArray(int i) {
        return new zzat[i];
    }
}
