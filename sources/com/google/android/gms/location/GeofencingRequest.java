package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzasm;
import java.util.ArrayList;
import java.util.List;

public class GeofencingRequest extends zza {
    public static final Parcelable.Creator<GeofencingRequest> CREATOR = new zzi();
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;
    private final String mTag;
    private final List<zzasm> zzbjS;
    private final int zzbjT;

    public static final class Builder {
        private String mTag = "";
        private final List<zzasm> zzbjS = new ArrayList();
        private int zzbjT = 5;

        public static int zzkh(int i) {
            return i & 7;
        }

        public Builder addGeofence(Geofence geofence) {
            zzac.zzb(geofence, (Object) "geofence can't be null.");
            zzac.zzb(geofence instanceof zzasm, (Object) "Geofence must be created using Geofence.Builder.");
            this.zzbjS.add((zzasm) geofence);
            return this;
        }

        public Builder addGeofences(List<Geofence> list) {
            if (list != null && !list.isEmpty()) {
                for (Geofence next : list) {
                    if (next != null) {
                        addGeofence(next);
                    }
                }
            }
            return this;
        }

        public GeofencingRequest build() {
            zzac.zzb(!this.zzbjS.isEmpty(), (Object) "No geofence has been added to this request.");
            return new GeofencingRequest(this.zzbjS, this.zzbjT, this.mTag);
        }

        public Builder setInitialTrigger(int i) {
            this.zzbjT = zzkh(i);
            return this;
        }
    }

    GeofencingRequest(List<zzasm> list, int i, String str) {
        this.zzbjS = list;
        this.zzbjT = i;
        this.mTag = str;
    }

    public List<Geofence> getGeofences() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.zzbjS);
        return arrayList;
    }

    public int getInitialTrigger() {
        return this.zzbjT;
    }

    public String getTag() {
        return this.mTag;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }

    public List<zzasm> zzIe() {
        return this.zzbjS;
    }
}
