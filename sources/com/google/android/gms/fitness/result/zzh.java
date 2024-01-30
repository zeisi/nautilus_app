package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;

public class zzh implements Parcelable.Creator<zzg> {
    static void zza(zzg zzg, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzg.getStatus(), i, false);
        zzc.zzc(parcel, 2, zzg.zzDt(), false);
        zzc.zzc(parcel, 1000, zzg.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfk */
    public zzg createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        ArrayList<zzd> arrayList;
        ArrayList<zzd> arrayList2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Status status3 = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    arrayList = arrayList2;
                    status = status3;
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, zzaX, zzd.CREATOR);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    ArrayList<zzd> arrayList3 = arrayList2;
                    status = status2;
                    zzg = zzb.zzg(parcel, zzaX);
                    arrayList = arrayList3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    arrayList = arrayList2;
                    status = status2;
                    zzg = i;
                    break;
            }
            i = zzg;
            status2 = status;
            arrayList2 = arrayList;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzg(i, status2, arrayList2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhX */
    public zzg[] newArray(int i) {
        return new zzg[i];
    }
}
