package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzq implements Parcelable.Creator<zzp> {
    static void zza(zzp zzp, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzp.zzAP(), i, false);
        zzc.zzc(parcel, 2, zzp.zzDv(), false);
        zzc.zzc(parcel, 1000, zzp.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfq */
    public zzp createFromParcel(Parcel parcel) {
        DataHolder dataHolder;
        int zzg;
        ArrayList<DataHolder> arrayList;
        ArrayList<DataHolder> arrayList2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        DataHolder dataHolder2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    DataHolder dataHolder3 = (DataHolder) zzb.zza(parcel, zzaX, DataHolder.CREATOR);
                    arrayList = arrayList2;
                    dataHolder = dataHolder3;
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, zzaX, DataHolder.CREATOR);
                    dataHolder = dataHolder2;
                    zzg = i;
                    break;
                case 1000:
                    ArrayList<DataHolder> arrayList3 = arrayList2;
                    dataHolder = dataHolder2;
                    zzg = zzb.zzg(parcel, zzaX);
                    arrayList = arrayList3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    arrayList = arrayList2;
                    dataHolder = dataHolder2;
                    zzg = i;
                    break;
            }
            i = zzg;
            dataHolder2 = dataHolder;
            arrayList2 = arrayList;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzp(i, dataHolder2, arrayList2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzid */
    public zzp[] newArray(int i) {
        return new zzp[i];
    }
}
