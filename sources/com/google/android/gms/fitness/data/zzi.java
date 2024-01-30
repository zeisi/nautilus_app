package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;
import java.util.List;

public class zzi implements Parcelable.Creator<DataSet> {
    static void zza(DataSet dataSet, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) dataSet.getDataSource(), i, false);
        zzc.zza(parcel, 2, (Parcelable) dataSet.getDataType(), i, false);
        zzc.zzd(parcel, 3, dataSet.zzCf(), false);
        zzc.zzc(parcel, 4, dataSet.zzCg(), false);
        zzc.zza(parcel, 5, dataSet.zzBW());
        zzc.zzc(parcel, 1000, dataSet.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdW */
    public DataSet createFromParcel(Parcel parcel) {
        boolean z = false;
        ArrayList<DataSource> arrayList = null;
        int zzaY = zzb.zzaY(parcel);
        ArrayList arrayList2 = new ArrayList();
        DataType dataType = null;
        DataSource dataSource = null;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 2:
                    dataType = (DataType) zzb.zza(parcel, zzaX, DataType.CREATOR);
                    break;
                case 3:
                    zzb.zza(parcel, zzaX, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    arrayList = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 5:
                    z = zzb.zzc(parcel, zzaX);
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
            return new DataSet(i, dataSource, dataType, arrayList2, arrayList, z);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgB */
    public DataSet[] newArray(int i) {
        return new DataSet[i];
    }
}
