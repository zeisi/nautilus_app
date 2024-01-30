package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzae extends zza {
    public static final Parcelable.Creator<zzae> CREATOR = new zzaf();
    private final int status;
    private final int versionCode;

    zzae(int i, int i2) {
        this.versionCode = i;
        this.status = i2;
    }

    private boolean zza(zzae zzae) {
        return this.status == zzae.status;
    }

    private static String zzha(int i) {
        switch (i) {
            case 0:
                return "skipped";
            case 1:
                return "started";
            case 2:
                return "completed";
            case 3:
                return "failed";
            default:
                return "invalid";
        }
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzae) && zza((zzae) obj));
    }

    public int getStatus() {
        return this.status;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public int hashCode() {
        return this.status;
    }

    public String toString() {
        return zzha(this.status);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzaf.zza(this, parcel, i);
    }
}
