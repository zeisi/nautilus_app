package com.ua.sdk.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class LocationImpl implements Location {
    public static Parcelable.Creator<LocationImpl> CREATOR = new Parcelable.Creator<LocationImpl>() {
        public LocationImpl createFromParcel(Parcel source) {
            return new LocationImpl(source);
        }

        public LocationImpl[] newArray(int size) {
            return new LocationImpl[size];
        }
    };
    @SerializedName("address")
    String address;
    @SerializedName("country")
    String country;
    @SerializedName("locality")
    String locality;
    @SerializedName("region")
    String region;

    public LocationImpl() {
    }

    public LocationImpl(String country2, String region2, String locality2, String address2) {
        this.country = country2;
        this.region = region2;
        this.locality = locality2;
        this.address = address2;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region2) {
        this.region = region2;
    }

    public String getLocality() {
        return this.locality;
    }

    public void setLocality(String locality2) {
        this.locality = locality2;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.country);
        dest.writeString(this.region);
        dest.writeString(this.locality);
        dest.writeString(this.address);
    }

    private LocationImpl(Parcel in) {
        this.country = in.readString();
        this.region = in.readString();
        this.locality = in.readString();
        this.address = in.readString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationImpl)) {
            return false;
        }
        LocationImpl location = (LocationImpl) o;
        if (this.address == null ? location.address != null : !this.address.equals(location.address)) {
            return false;
        }
        if (this.country == null ? location.country != null : !this.country.equals(location.country)) {
            return false;
        }
        if (this.locality == null ? location.locality != null : !this.locality.equals(location.locality)) {
            return false;
        }
        if (this.region != null) {
            if (this.region.equals(location.region)) {
                return true;
            }
        } else if (location.region == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i;
        int i2;
        int i3 = 0;
        if (this.country != null) {
            result = this.country.hashCode();
        } else {
            result = 0;
        }
        int i4 = result * 31;
        if (this.region != null) {
            i = this.region.hashCode();
        } else {
            i = 0;
        }
        int i5 = (i4 + i) * 31;
        if (this.locality != null) {
            i2 = this.locality.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i5 + i2) * 31;
        if (this.address != null) {
            i3 = this.address.hashCode();
        }
        return i6 + i3;
    }
}
