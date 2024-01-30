package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public class zzc implements Parcelable.Creator<ActivityRecognitionResult> {
    static void zza(ActivityRecognitionResult activityRecognitionResult, Parcel parcel, int i) {
        int zzaZ = com.google.android.gms.common.internal.safeparcel.zzc.zzaZ(parcel);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 1, activityRecognitionResult.zzbju, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 2, activityRecognitionResult.zzbjv);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 3, activityRecognitionResult.zzbjw);
        com.google.android.gms.common.internal.safeparcel.zzc.zzc(parcel, 4, activityRecognitionResult.zzbjx);
        com.google.android.gms.common.internal.safeparcel.zzc.zza(parcel, 5, activityRecognitionResult.extras, false);
        com.google.android.gms.common.internal.safeparcel.zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzgG */
    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        long j = 0;
        Bundle bundle = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        long j2 = 0;
        ArrayList<DetectedActivity> arrayList = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    arrayList = zzb.zzc(parcel, zzaX, DetectedActivity.CREATOR);
                    break;
                case 2:
                    j2 = zzb.zzi(parcel, zzaX);
                    break;
                case 3:
                    j = zzb.zzi(parcel, zzaX);
                    break;
                case 4:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 5:
                    bundle = zzb.zzs(parcel, zzaX);
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaY) {
            return new ActivityRecognitionResult((List<DetectedActivity>) arrayList, j2, j, i, bundle);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzkb */
    public ActivityRecognitionResult[] newArray(int i) {
        return new ActivityRecognitionResult[i];
    }
}
