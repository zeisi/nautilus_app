package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;

public class zzbj implements Parcelable.Creator<StartBleScanRequest> {
    static void zza(StartBleScanRequest startBleScanRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, startBleScanRequest.getDataTypes(), false);
        zzc.zza(parcel, 2, startBleScanRequest.zzDl(), false);
        zzc.zzc(parcel, 3, startBleScanRequest.getTimeoutSecs());
        zzc.zza(parcel, 4, startBleScanRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 1000, startBleScanRequest.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzeZ */
    public StartBleScanRequest createFromParcel(Parcel parcel) {
        int i = 0;
        IBinder iBinder = null;
        int zzaY = zzb.zzaY(parcel);
        IBinder iBinder2 = null;
        ArrayList<DataType> arrayList = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList = zzb.zzc(parcel, zzaX, DataType.CREATOR);
                    break;
                case 2:
                    iBinder2 = zzb.zzr(parcel, zzaX);
                    break;
                case 3:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 4:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new StartBleScanRequest(i2, arrayList, iBinder2, i, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhM */
    public StartBleScanRequest[] newArray(int i) {
        return new StartBleScanRequest[i];
    }
}
