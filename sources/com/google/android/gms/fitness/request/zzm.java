package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.Field;
import java.util.ArrayList;

public class zzm implements Parcelable.Creator<DataTypeCreateRequest> {
    static void zza(DataTypeCreateRequest dataTypeCreateRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, dataTypeCreateRequest.getName(), false);
        zzc.zzc(parcel, 2, dataTypeCreateRequest.getFields(), false);
        zzc.zza(parcel, 3, dataTypeCreateRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, dataTypeCreateRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzez */
    public DataTypeCreateRequest createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<Field> arrayList = null;
        String str = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, zzaX, Field.CREATOR);
                    break;
                case 3:
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
            return new DataTypeCreateRequest(i, str, arrayList, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhm */
    public DataTypeCreateRequest[] newArray(int i) {
        return new DataTypeCreateRequest[i];
    }
}
