package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzac implements Parcelable.Creator<Subscription> {
    static void zza(Subscription subscription, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) subscription.getDataSource(), i, false);
        zzc.zza(parcel, 2, (Parcelable) subscription.getDataType(), i, false);
        zzc.zza(parcel, 3, subscription.zzCy());
        zzc.zzc(parcel, 4, subscription.getAccuracyMode());
        zzc.zzc(parcel, 1000, subscription.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzen */
    public Subscription createFromParcel(Parcel parcel) {
        DataType dataType = null;
        int i = 0;
        int zzaY = zzb.zzaY(parcel);
        long j = 0;
        DataSource dataSource = null;
        int i2 = 0;
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
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new Subscription(i2, dataSource, dataType, j, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgY */
    public Subscription[] newArray(int i) {
        return new Subscription[i];
    }
}
