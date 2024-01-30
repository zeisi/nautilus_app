package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzaa;
import java.util.ArrayList;

public class zzr implements Parcelable.Creator<SessionReadResult> {
    static void zza(SessionReadResult sessionReadResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, sessionReadResult.getSessions(), false);
        zzc.zzc(parcel, 2, sessionReadResult.zzDw(), false);
        zzc.zza(parcel, 3, (Parcelable) sessionReadResult.getStatus(), i, false);
        zzc.zzc(parcel, 1000, sessionReadResult.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfr */
    public SessionReadResult createFromParcel(Parcel parcel) {
        Status status = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<zzaa> arrayList = null;
        ArrayList<Session> arrayList2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList2 = zzb.zzc(parcel, zzaX, Session.CREATOR);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, zzaX, zzaa.CREATOR);
                    break;
                case 3:
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
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
            return new SessionReadResult(i, arrayList2, arrayList, status);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzie */
    public SessionReadResult[] newArray(int i) {
        return new SessionReadResult[i];
    }
}
