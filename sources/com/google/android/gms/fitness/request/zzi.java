package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import java.util.ArrayList;

public class zzi implements Parcelable.Creator<DataReadRequest> {
    static void zza(DataReadRequest dataReadRequest, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zzc(parcel, 1, dataReadRequest.getDataTypes(), false);
        zzc.zzc(parcel, 2, dataReadRequest.getDataSources(), false);
        zzc.zza(parcel, 3, dataReadRequest.zzqn());
        zzc.zza(parcel, 4, dataReadRequest.zzAl());
        zzc.zzc(parcel, 5, dataReadRequest.getAggregatedDataTypes(), false);
        zzc.zzc(parcel, 6, dataReadRequest.getAggregatedDataSources(), false);
        zzc.zzc(parcel, 7, dataReadRequest.getBucketType());
        zzc.zzc(parcel, 1000, dataReadRequest.getVersionCode());
        zzc.zza(parcel, 8, dataReadRequest.zzCT());
        zzc.zza(parcel, 9, (Parcelable) dataReadRequest.getActivityDataSource(), i, false);
        zzc.zzc(parcel, 10, dataReadRequest.getLimit());
        zzc.zza(parcel, 12, dataReadRequest.zzCS());
        zzc.zza(parcel, 13, dataReadRequest.zzCR());
        zzc.zza(parcel, 14, dataReadRequest.getCallbackBinder(), false);
        zzc.zzc(parcel, 16, dataReadRequest.zzCU(), false);
        zzc.zza(parcel, 17, dataReadRequest.getFilteredDataQualityStandards(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzew */
    public DataReadRequest createFromParcel(Parcel parcel) {
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        ArrayList<DataType> arrayList = null;
        ArrayList<DataSource> arrayList2 = null;
        long j = 0;
        long j2 = 0;
        ArrayList<DataType> arrayList3 = null;
        ArrayList<DataSource> arrayList4 = null;
        int i2 = 0;
        long j3 = 0;
        DataSource dataSource = null;
        int i3 = 0;
        boolean z = false;
        boolean z2 = false;
        IBinder iBinder = null;
        ArrayList<Device> arrayList5 = null;
        ArrayList<Integer> arrayList6 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList = zzb.zzc(parcel, zzaX, DataType.CREATOR);
                    break;
                case 2:
                    arrayList2 = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 3:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 5:
                    arrayList3 = zzb.zzc(parcel, zzaX, DataType.CREATOR);
                    break;
                case 6:
                    arrayList4 = zzb.zzc(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 7:
                    i2 = zzb.zzg(parcel, zzaX);
                    break;
                case 8:
                    j3 = zzb.zzi(parcel, zzaX);
                    break;
                case 9:
                    dataSource = (DataSource) zzb.zza(parcel, zzaX, DataSource.CREATOR);
                    break;
                case 10:
                    i3 = zzb.zzg(parcel, zzaX);
                    break;
                case 12:
                    z = zzb.zzc(parcel, zzaX);
                    break;
                case 13:
                    z2 = zzb.zzc(parcel, zzaX);
                    break;
                case 14:
                    iBinder = zzb.zzr(parcel, zzaX);
                    break;
                case 16:
                    arrayList5 = zzb.zzc(parcel, zzaX, Device.CREATOR);
                    break;
                case 17:
                    arrayList6 = zzb.zzD(parcel, zzaX);
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
            return new DataReadRequest(i, arrayList, arrayList2, j, j2, arrayList3, arrayList4, i2, j3, dataSource, i3, z, z2, iBinder, arrayList5, arrayList6);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzhj */
    public DataReadRequest[] newArray(int i) {
        return new DataReadRequest[i];
    }
}
