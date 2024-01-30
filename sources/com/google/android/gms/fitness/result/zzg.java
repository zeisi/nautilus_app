package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.List;
import java.util.Locale;

public class zzg extends zza implements Result {
    public static final Parcelable.Creator<zzg> CREATOR = new zzh();
    private final int versionCode;
    private final List<zzd> zzaWY;
    private final Status zzahw;

    zzg(int i, Status status, List<zzd> list) {
        this.versionCode = i;
        this.zzahw = status;
        this.zzaWY = list;
    }

    public Status getStatus() {
        return this.zzahw;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.versionCode;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "DataStatsResult{%s %d sources}", new Object[]{this.zzahw, Integer.valueOf(this.zzaWY.size())});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.zza(this, parcel, i);
    }

    /* access modifiers changed from: package-private */
    public List<zzd> zzDt() {
        return this.zzaWY;
    }
}
