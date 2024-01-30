package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzarw;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class zzf extends zza {
    public static final Parcelable.Creator<zzf> CREATOR = new zzg();
    public static final Comparator<zzd> zzbjz = new Comparator<zzd>() {
        /* renamed from: zza */
        public int compare(zzd zzd, zzd zzd2) {
            int zzBV = zzd.zzBV();
            int zzBV2 = zzd2.zzBV();
            if (zzBV != zzBV2) {
                return zzBV < zzBV2 ? -1 : 1;
            }
            int zzIb = zzd.zzIb();
            int zzIb2 = zzd2.zzIb();
            if (zzIb == zzIb2) {
                return 0;
            }
            return zzIb >= zzIb2 ? 1 : -1;
        }
    };
    @Nullable
    private final String mTag;
    private final List<zzd> zzbjA;
    private final List<zzarw> zzbjB;

    public zzf(List<zzd> list, @Nullable String str, @Nullable List<zzarw> list2) {
        zzac.zzb(list, (Object) "transitions can't be null");
        zzac.zzb(list.size() > 0, (Object) "transitions can't be empty.");
        zzD(list);
        this.zzbjA = Collections.unmodifiableList(list);
        this.mTag = str;
        this.zzbjB = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
    }

    private static void zzD(List<zzd> list) {
        TreeSet treeSet = new TreeSet(zzbjz);
        for (zzd next : list) {
            zzac.zzb(treeSet.add(next), (Object) String.format("Found duplicated transition: %s.", new Object[]{next}));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzf zzf = (zzf) obj;
        return zzaa.equal(this.zzbjA, zzf.zzbjA) && zzaa.equal(this.mTag, zzf.mTag) && zzaa.equal(this.zzbjB, zzf.zzbjB);
    }

    @Nullable
    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.mTag != null ? this.mTag.hashCode() : 0) + (this.zzbjA.hashCode() * 31)) * 31;
        if (this.zzbjB != null) {
            i = this.zzbjB.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        String valueOf = String.valueOf(this.zzbjA);
        String str = this.mTag;
        String valueOf2 = String.valueOf(this.zzbjB);
        return new StringBuilder(String.valueOf(valueOf).length() + 61 + String.valueOf(str).length() + String.valueOf(valueOf2).length()).append("ActivityTransitionRequest [mTransitions=").append(valueOf).append(", mTag='").append(str).append("'").append(", mClients=").append(valueOf2).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzg.zza(this, parcel, i);
    }

    public List<zzd> zzIc() {
        return this.zzbjA;
    }

    public List<zzarw> zzId() {
        return this.zzbjB;
    }
}
