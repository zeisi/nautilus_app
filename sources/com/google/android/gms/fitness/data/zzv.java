package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzv implements Parcelable.Creator<RawBucket> {
    static void zza(RawBucket rawBucket, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, rawBucket.zzafe);
        zzc.zza(parcel, 2, rawBucket.zzaSt);
        zzc.zza(parcel, 3, (Parcelable) rawBucket.zzaSj, i, false);
        zzc.zzc(parcel, 4, rawBucket.zzaUa);
        zzc.zzc(parcel, 5, rawBucket.zzaSv, false);
        zzc.zzc(parcel, 6, rawBucket.zzaSw);
        zzc.zza(parcel, 7, rawBucket.zzaSx);
        zzc.zzc(parcel, 1000, rawBucket.zzaiI);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeh */
    public RawBucket createFromParcel(Parcel parcel) {
        long j = 0;
        ArrayList<RawDataSet> arrayList = null;
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
                    arrayList = zzb.zzc(parcel, zzaX, RawDataSet.CREATOR);
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
            return new RawBucket(i3, j2, j, session, i2, arrayList, i, z);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgR */
    public RawBucket[] newArray(int i) {
        return new RawBucket[i];
    }
}
