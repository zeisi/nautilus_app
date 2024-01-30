package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzw implements Parcelable.Creator<RawDataPoint> {
    static void zza(RawDataPoint rawDataPoint, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, rawDataPoint.zzaUb);
        zzc.zza(parcel, 2, rawDataPoint.zzaUc);
        zzc.zza(parcel, 3, (T[]) rawDataPoint.zzaUd, i, false);
        zzc.zzc(parcel, 4, rawDataPoint.zzaUe);
        zzc.zzc(parcel, 5, rawDataPoint.zzaUf);
        zzc.zza(parcel, 6, rawDataPoint.zzaUg);
        zzc.zza(parcel, 7, rawDataPoint.zzaUh);
        zzc.zzc(parcel, 1000, rawDataPoint.versionCode);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzei */
    public RawDataPoint createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        Value[] valueArr = null;
        int i2 = 0;
        int i3 = 0;
        long j3 = 0;
        long j4 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 2:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    valueArr = (Value[]) zzb.zzb(parcel, zzaX, Value.CREATOR);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 5:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                case 6:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 7:
                    j4 = zzb.zzi(parcel, zzaX);
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
            return new RawDataPoint(i, j, j2, valueArr, i2, i3, j3, j4);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgS */
    public RawDataPoint[] newArray(int i) {
        return new RawDataPoint[i];
    }
}
