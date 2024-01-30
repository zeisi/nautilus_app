package com.ua.sdk.heartrate;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeartRateZonesImpl extends ApiTransferObject implements HeartRateZones {
    public static final Parcelable.Creator<HeartRateZonesImpl> CREATOR = new Parcelable.Creator<HeartRateZonesImpl>() {
        public HeartRateZonesImpl createFromParcel(Parcel source) {
            return new HeartRateZonesImpl(source);
        }

        public HeartRateZonesImpl[] newArray(int size) {
            return new HeartRateZonesImpl[size];
        }
    };
    private transient EntityRef<HeartRateZones> selfRef;
    private transient EntityRef<User> userRef;
    @SerializedName("zones")
    private List<HeartRateZone> zones;

    public HeartRateZonesImpl() {
        this.zones = new ArrayList();
    }

    public HeartRateZonesImpl(HeartRateZone... hrZones) {
        this.zones = new ArrayList();
        this.zones = Arrays.asList(hrZones);
    }

    private HeartRateZonesImpl(Parcel in) {
        super(in);
        this.zones = new ArrayList();
        in.readList(this.zones, HeartRateZone.class.getClassLoader());
    }

    public List<HeartRateZone> getZones() {
        return this.zones;
    }

    public void add(HeartRateZone zone) {
        this.zones.add(zone);
    }

    public HeartRateZone getZone(int index) {
        if (index <= -1 || index >= this.zones.size()) {
            return null;
        }
        return this.zones.get(index);
    }

    public HeartRateZone getZone(String name) {
        for (HeartRateZone zone : this.zones) {
            if (zone.getName().equals(name)) {
                return zone;
            }
        }
        return null;
    }

    public EntityRef<HeartRateZones> getRef() {
        Link ref;
        if (this.selfRef == null && (ref = getLink("self")) != null) {
            this.selfRef = new LinkEntityRef(ref.getId(), ref.getHref());
        }
        return this.selfRef;
    }

    public EntityRef<User> getUserRef() {
        Link ref;
        if (this.userRef == null && (ref = getLink("user")) != null) {
            this.userRef = new LinkEntityRef(ref.getId(), ref.getHref());
        }
        return this.userRef;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.zones);
    }
}
