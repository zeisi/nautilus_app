package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

public class zzf implements Parcelable.Creator<DataDeleteRequest> {
    static void zza(DataDeleteRequest dataDeleteRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, dataDeleteRequest.zzqn());
        zzc.zza(parcel, 2, dataDeleteRequest.zzAl());
        zzc.zzc(parcel, 3, dataDeleteRequest.getDataSources(), false);
        zzc.zzc(parcel, 4, dataDeleteRequest.getDataTypes(), false);
        zzc.zzc(parcel, 5, dataDeleteRequest.getSessions(), false);
        zzc.zza(parcel, 6, dataDeleteRequest.zzCN());
        zzc.zza(parcel, 7, dataDeleteRequest.zzCO());
        zzc.zzc(parcel, 1000, dataDeleteRequest.getVersionCode());
        zzc.zza(parcel, 8, dataDeleteRequest.getCallbackBinder(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeu */
    public DataDeleteRequest createFromParcel(Parcel parcel) {
        long j = 0;
        boolean z = false;
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        boolean z2 = false;
        ArrayList<Session> arrayList = null;
        ArrayList<DataType> arrayList2 = null;
        ArrayList<DataSource> arrayList3 = null;
        long j2 = 0;
        int i = 0;
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
                    arrayList3 = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 4:
                    arrayList2 = zzb.zzc(parcel, zzaX, DataType.CREATOR);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, zzaX, Session.CREATOR);
                    break;
                case 6:
                    z2 = zzb.zzc(parcel, zzaX);
                    break;
                case 7:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 8:
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
            return new DataDeleteRequest(i, j2, j, arrayList3, arrayList2, arrayList, z2, z, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhh */
    public DataDeleteRequest[] newArray(int i) {
        return new DataDeleteRequest[i];
    }
}
