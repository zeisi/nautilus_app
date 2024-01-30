package com.google.android.gms.fitness.result;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzm implements Parcelable.Creator<zzl> {
    static void zza(zzl zzl, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, (Parcelable) zzl.getUri(), i, false);
        zzc.zza(parcel, 2, (Parcelable) zzl.getStatus(), i, false);
        zzc.zzc(parcel, 1000, zzl.getVersionCode());
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzfn */
    public zzl createFromParcel(Parcel parcel) {
        Uri uri;
        int zzg;
        Status status;
        Status status2 = null;
        int zzaY = zzb.zzaY(parcel);
        int i = 0;
        Uri uri2 = null;
        while (parcel.dataPosition() < zzaY) {
            int zzaX = zzb.zzaX(parcel);
            switch (zzb.zzdc(zzaX)) {
                case 1:
                    zzg = i;
                    Uri uri3 = (Uri) zzb.zza(parcel, zzaX, Uri.CREATOR);
                    status = status2;
                    uri = uri3;
                    break;
                case 2:
                    status = (Status) zzb.zza(parcel, zzaX, Status.CREATOR);
                    uri = uri2;
                    zzg = i;
                    break;
                case 1000:
                    Status status3 = status2;
                    uri = uri2;
                    zzg = zzb.zzg(parcel, zzaX);
                    status = status3;
                    break;
                default:
                    zzb.zzb(parcel, zzaX);
                    status = status2;
                    uri = uri2;
                    zzg = i;
                    break;
            }
            i = zzg;
            uri2 = uri;
            status2 = status;
        }
        if (parcel.dataPosition() == zzaY) {
            return new zzl(i, uri2, status2);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzia */
    public zzl[] newArray(int i) {
        return new zzl[i];
    }
}
