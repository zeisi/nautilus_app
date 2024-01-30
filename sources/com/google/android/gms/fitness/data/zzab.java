package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzab implements Parcelable.Creator<zzaa> {
    static void zza(zzaa zzaa, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzaa.getSession(), i, false);
        zzc.zza(parcel, 2, (Parcelable) zzaa.getDataSet(), i, false);
        zzc.zzc(parcel, 1000, zzaa.zzaiI);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzem */
    public zzaa createFromParcel(Parcel parcel) {
        Session session;
        int zzg;
        DataSet dataSet;
        DataSet dataSet2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Session session2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Session session3 = (Session) zzb.zza(parcel, zzaX, Session.CREATOR);
                    dataSet = dataSet2;
                    session = session3;
                    break;
                case 2:
                    dataSet = (DataSet) zzb.zza(parcel, zzaX, DataSet.CREATOR);
                    session = session2;
                    zzg = i;
                    break;
                case 1000:
                    DataSet dataSet3 = dataSet2;
                    session = session2;
                    zzg = zzb.zzg(parcel, zzaX);
                    dataSet = dataSet3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    dataSet = dataSet2;
                    session = session2;
                    zzg = i;
                    break;
            }
            i = zzg;
            session2 = session;
            dataSet2 = dataSet;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzaa(i, session2, dataSet2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgX */
    public zzaa[] newArray(int i) {
        return new zzaa[i];
    }
}
