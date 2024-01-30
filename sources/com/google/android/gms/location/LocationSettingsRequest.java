package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest extends zza {
    public static final Parcelable.Creator<LocationSettingsRequest> CREATOR = new zzq();
    private final List<LocationRequest> zzaWt;
    private final boolean zzbki;
    private final boolean zzbkj;
    private zzo zzbkk;

    public static final class Builder {
        private boolean zzbki = false;
        private boolean zzbkj = false;
        private zzo zzbkk = null;
        private final ArrayList<LocationRequest> zzbkl = new ArrayList<>();

        public Builder addAllLocationRequests(Collection<LocationRequest> collection) {
            this.zzbkl.addAll(collection);
            return this;
        }

        public Builder addLocationRequest(LocationRequest locationRequest) {
            this.zzbkl.add(locationRequest);
            return this;
        }

        public LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zzbkl, this.zzbki, this.zzbkj, (zzo) null);
        }

        public Builder setAlwaysShow(boolean z) {
            this.zzbki = z;
            return this;
        }

        public Builder setNeedBle(boolean z) {
            this.zzbkj = z;
            return this;
        }
    }

    LocationSettingsRequest(List<LocationRequest> list, boolean z, boolean z2, zzo zzo) {
        this.zzaWt = list;
        this.zzbki = z;
        this.zzbkj = z2;
        this.zzbkk = zzo;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzq.zza(this, parcel, i);
    }

    public List<LocationRequest> zzDe() {
        return Collections.unmodifiableList(this.zzaWt);
    }

    public boolean zzIi() {
        return this.zzbki;
    }

    public boolean zzIj() {
        return this.zzbkj;
    }

    @Nullable
    public zzo zzIk() {
        return this.zzbkk;
    }
}
