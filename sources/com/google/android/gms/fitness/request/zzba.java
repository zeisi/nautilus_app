package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

public class zzba implements Parcelable.Creator<SessionReadRequest> {
    static void zza(SessionReadRequest sessionReadRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, sessionReadRequest.getSessionName(), false);
        zzc.zza(parcel, 2, sessionReadRequest.getSessionId(), false);
        zzc.zza(parcel, 3, sessionReadRequest.zzqn());
        zzc.zza(parcel, 4, sessionReadRequest.zzAl());
        zzc.zzc(parcel, 5, sessionReadRequest.getDataTypes(), false);
        zzc.zzc(parcel, 6, sessionReadRequest.getDataSources(), false);
        zzc.zza(parcel, 7, sessionReadRequest.zzDj());
        zzc.zzc(parcel, 1000, sessionReadRequest.getVersionCode());
        zzc.zza(parcel, 8, sessionReadRequest.zzCR());
        zzc.zzb(parcel, 9, sessionReadRequest.getExcludedPackages(), false);
        zzc.zza(parcel, 10, sessionReadRequest.getCallbackBinder(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeU */
    public SessionReadRequest createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        long j = 0;
        long j2 = 0;
        ArrayList<DataType> arrayList = null;
        ArrayList<DataSource> arrayList2 = null;
        boolean z = false;
        boolean z2 = false;
        ArrayList<String> arrayList3 = null;
        IBinder iBinder = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaX);
                    break;
                case 3:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, zzaX, DataType.CREATOR);
                    break;
                case 6:
                    arrayList2 = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 7:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 8:
                    z2 = zzb.zzc(parcel, zzaX);
                    break;
                case 9:
                    arrayList3 = zzb.zzE(parcel, zzaX);
                    break;
                case 10:
                    iBinder = zzb.zzr(parcel, zzaX);
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
            return new SessionReadRequest(i, str, str2, j, j2, arrayList, arrayList2, z, z2, arrayList3, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhH */
    public SessionReadRequest[] newArray(int i) {
        return new SessionReadRequest[i];
    }
}
