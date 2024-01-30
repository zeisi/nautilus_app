package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;

public final class zzb extends zza {
    public static final Parcelable.Creator<zzb> CREATOR = new zzc();
    public static final zzb zzaSo = new zzb("com.google.android.gms", (String) null, (String) null);
    private final String packageName;
    private final String version;
    private final int versionCode;
    private final String zzaSp;

    zzb(int i, String str, String str2, String str3) {
        this.versionCode = i;
        this.packageName = (String) zzac.zzw(str);
        this.version = "";
        this.zzaSp = str3;
    }

    public zzb(String str, String str2, String str3) {
        this(1, str, "", str3);
    }

    private boolean zza(zzb zzb) {
        return this.packageName.equals(zzb.packageName) && zzaa.equal(this.version, zzb.version) && zzaa.equal(this.zzaSp, zzb.zzaSp);
    }

    public static zzb zzdV(String str) {
        return zzf(str, (String) null, (String) null);
    }

    public static zzb zzf(String str, String str2, String str3) {
        return "com.google.android.gms".equals(str) ? zzaSo : new zzb(str, str2, str3);
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof zzb) && zza((zzb) obj));
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getVersion() {
        return this.version;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.packageName, this.version, this.zzaSp);
    }

    public String toString() {
        return String.format("Application{%s:%s:%s}", new Object[]{this.packageName, this.version, this.zzaSp});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }

    public String zzBU() {
        return this.zzaSp;
    }
}
