package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zze implements Parcelable.Creator<Bucket> {
    static void zza(Bucket bucket, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, bucket.zzqn());
        zzc.zza(parcel, 2, bucket.zzAl());
        zzc.zza(parcel, 3, (Parcelable) bucket.getSession(), i, false);
        zzc.zzc(parcel, 4, bucket.zzBV());
        zzc.zzc(parcel, 5, bucket.getDataSets(), false);
        zzc.zzc(parcel, 6, bucket.getBucketType());
        zzc.zza(parcel, 7, bucket.zzBW());
        zzc.zzc(parcel, 1000, bucket.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzdU */
    public Bucket createFromParcel(Parcel parcel) {
        long j = 0;
        ArrayList<DataSet> arrayList = null;
        boolean z = false;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        int i2 = 0;
        Session session = null;
        long j2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 2:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    session = (Session) zzb.zza(parcel, zzaX, Session.CREATOR);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, zzaX, DataSet.CREATOR);
                    break;
                case 6:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 7:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 1000:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new Bucket(i3, j2, j, session, i2, arrayList, i, z);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgx */
    public Bucket[] newArray(int i) {
        return new Bucket[i];
    }
}
