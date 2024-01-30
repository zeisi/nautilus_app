package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzo extends zza {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private final String zzbkf;
    private final String zzbkg;
    private final int zzbkh;

    zzo(String str, String str2, int i) {
        this.zzbkf = str;
        this.zzbkg = str2;
        this.zzbkh = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzp.zza(this, parcel, i);
    }

    public String zzIf() {
        return this.zzbkf;
    }

    public String zzIg() {
        return this.zzbkg;
    }

    public int zzIh() {
        return this.zzbkh;
    }
}
