package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaoa;
import java.util.concurrent.TimeUnit;

public class zzanz {
    public static <DP, DT> String zza(DP dp, zzanv<DP, DT> zzanv) {
        double zzb;
        zzanw<DT> zzBX = zzanv.zzBX();
        if (!zzBX.zzdW(zzanv.zzC(dp))) {
            return null;
        }
        DT zzB = zzanv.zzB(dp);
        for (int i = 0; i < zzBX.zzD(zzB); i++) {
            String zzg = zzBX.zzg(zzB, i);
            if (zzanv.zzd(dp, i)) {
                double zze = (double) zzBX.zze(zzB, i);
                if (zze == 1.0d) {
                    zzb = (double) zzanv.zzc(dp, i);
                } else if (zze == 2.0d) {
                    zzb = zzanv.zzb(dp, i);
                } else {
                    continue;
                }
                zzaoa.zza zzej = zzaoa.zzCH().zzej(zzg);
                if (zzej != null && !zzej.zzg(zzb)) {
                    return "Field out of range";
                }
                zzaoa.zza zzK = zzaoa.zzCH().zzK(zzBX.zzE(zzB), zzg);
                if (zzK != null) {
                    long zza = zzanv.zza(dp, TimeUnit.NANOSECONDS);
                    if (zza == 0) {
                        if (zzb == 0.0d) {
                            return null;
                        }
                        return "DataPoint out of range";
                    } else if (!zzK.zzg(zzb / ((double) zza))) {
                        return "DataPoint out of range";
                    }
                } else {
                    continue;
                }
            } else if (!zzBX.zzf(zzB, i) && !zzaoa.zzaUw.contains(zzg)) {
                return String.valueOf(zzg).concat(" not set");
            }
        }
        return null;
    }
}
