package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzz implements Parcelable.Creator<Session> {
    static void zza(Session session, Parcel parcel, int i) {
        int zzaZ = zzc.zzaZ(parcel);
        zzc.zza(parcel, 1, session.zzqn());
        zzc.zza(parcel, 2, session.zzAl());
        zzc.zza(parcel, 3, session.getName(), false);
        zzc.zza(parcel, 4, session.getIdentifier(), false);
        zzc.zza(parcel, 5, session.getDescription(), false);
        zzc.zzc(parcel, 7, session.zzBV());
        zzc.zzc(parcel, 1000, session.getVersionCode());
        zzc.zza(parcel, 8, (Parcelable) session.zzCh(), i, false);
        zzc.zza(parcel, 9, session.zzCx(), false);
        zzc.zzJ(parcel, zzaZ);
    }

    /* renamed from: zzel */
    public Session createFromParcel(Parcel parcel) {
        long j = 0;
        int i = 0;
        Long l = null;
        int zzaY = zzb.zzaY(parcel);
        zzb zzb = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        long j2 = 0;
        int i2 = 0;
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
                    str3 = zzb.zzq(parcel, zzaX);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, zzaX);
                    break;
                case 5:
                    str = zzb.zzq(parcel, zzaX);
                    break;
                case 7:
                    i = zzb.zzg(parcel, zzaX);
                    break;
                case 8:
                    zzb = (zzb) zzb.zza(parcel, zzaX, zzb.CREATOR);
                    break;
                case 9:
                    l = zzb.zzj(parcel, zzaX);
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
            return new Session(i2, j2, j, str3, str2, str, i, zzb, l);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaY).toString(), parcel);
    }

    /* renamed from: zzgW */
    public Session[] newArray(int i) {
        return new Session[i];
    }
}
