package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

public class zzs implements Parcelable.Creator<SessionStopResult> {
    static void zza(SessionStopResult sessionStopResult, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 2, (Parcelable) sessionStopResult.getStatus(), i, false);
        zzc.zzc(parcel, 3, sessionStopResult.getSessions(), false);
        zzc.zzc(parcel, 1000, sessionStopResult.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfs */
    public SessionStopResult createFromParcel(Parcel parcel) {
        Status status;
        int zzg;
        ArrayList<Session> arrayList;
        ArrayList<Session> arrayList2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Status status2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 2:
                    zzg = i;
                    Status status3 = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    arrayList = arrayList2;
                    status = status3;
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, zzaX, Session.CREATOR);
                    status = status2;
                    zzg = i;
                    break;
                case 1000:
                    ArrayList<Session> arrayList3 = arrayList2;
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
            return new SessionStopResult(i, status2, arrayList2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzif */
    public SessionStopResult[] newArray(int i) {
        return new SessionStopResult[i];
    }
}
