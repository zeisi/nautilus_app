package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

public class zzl implements Parcelable.Creator<DataSourcesRequest> {
    static void zza(DataSourcesRequest dataSourcesRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, dataSourcesRequest.getDataTypes(), false);
        zzc.zza(parcel, 2, dataSourcesRequest.zzCV(), false);
        zzc.zza(parcel, 3, dataSourcesRequest.zzCW());
        zzc.zza(parcel, 4, dataSourcesRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, dataSourcesRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzey */
    public DataSourcesRequest createFromParcel(Parcel parcel) {
        boolean z = false;
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        ArrayList<Integer> arrayList = null;
        ArrayList<DataType> arrayList2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList2 = zzb.zzc(parcel, zzaX, DataType.CREATOR);
                    break;
                case 2:
                    arrayList = zzb.zzD(parcel, zzaX);
                    break;
                case 3:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 4:
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
            return new DataSourcesRequest(i, arrayList2, arrayList, z, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhl */
    public DataSourcesRequest[] newArray(int i) {
        return new DataSourcesRequest[i];
    }
}
