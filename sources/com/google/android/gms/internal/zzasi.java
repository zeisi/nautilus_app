package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class zzasi extends zza {
    public static final Parcelable.Creator<zzasi> CREATOR = new zzasj();
    static final List<zzarw> zzbkQ = Collections.emptyList();
    @Nullable
    String mTag;
    LocationRequest zzaWw;
    List<zzarw> zzbjB;
    boolean zzbjp = true;
    boolean zzbkR;
    boolean zzbkS;

    zzasi(LocationRequest locationRequest, List<zzarw> list, @Nullable String str, boolean z, boolean z2) {
        this.zzaWw = locationRequest;
        this.zzbjB = list;
        this.mTag = str;
        this.zzbkR = z;
        this.zzbkS = z2;
    }

    public static zzasi zza(@Nullable String str, LocationRequest locationRequest) {
        return new zzasi(locationRequest, zzbkQ, str, false, false);
    }

    @Deprecated
    public static zzasi zzb(LocationRequest locationRequest) {
        return zza((String) null, locationRequest);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzasi)) {
            return false;
        }
        zzasi zzasi = (zzasi) obj;
        return zzaa.equal(this.zzaWw, zzasi.zzaWw) && zzaa.equal(this.zzbjB, zzasi.zzbjB) && zzaa.equal(this.mTag, zzasi.mTag) && this.zzbkR == zzasi.zzbkR && this.zzbkS == zzasi.zzbkS;
    }

    public int hashCode() {
        return this.zzaWw.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.zzaWw.toString());
        if (this.mTag != null) {
            sb.append(" tag=").append(this.mTag);
        }
        sb.append(" hideAppOps=").append(this.zzbkR);
        sb.append(" clients=").append(this.zzbjB);
        sb.append(" forceCoarseLocation=").append(this.zzbkS);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzasj.zza(this, parcel, i);
    }
}
