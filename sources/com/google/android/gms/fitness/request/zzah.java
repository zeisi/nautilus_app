package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import java.util.ArrayList;
import java.util.List;

public class zzah implements Parcelable.Creator<GoalsReadRequest> {
    static void zza(GoalsReadRequest goalsReadRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, goalsReadRequest.getCallbackBinder(), false);
        zzc.zzd(parcel, 2, goalsReadRequest.getDataTypes(), false);
        zzc.zzd(parcel, 3, goalsReadRequest.zzCY(), false);
        zzc.zzd(parcel, 4, goalsReadRequest.zzCr(), false);
        zzc.zzc(parcel, 1000, goalsReadRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeL */
    public GoalsReadRequest createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        IBinder iBinder = null;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 2:
                    zzb.zza(parcel, zzaX, (List) arrayList, getClass().getClassLoader());
                    break;
                case 3:
                    zzb.zza(parcel, zzaX, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    zzb.zza(parcel, zzaX, (List) arrayList3, getClass().getClassLoader());
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
            return new GoalsReadRequest(i, iBinder, arrayList, arrayList2, arrayList3);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhy */
    public GoalsReadRequest[] newArray(int i) {
        return new GoalsReadRequest[i];
    }
}
