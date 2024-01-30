package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.user.User;

public class RouteListRef implements EntityListRef<Route>, Parcelable {
    public static final Parcelable.Creator<RouteListRef> CREATOR = new Parcelable.Creator<RouteListRef>() {
        public RouteListRef createFromParcel(Parcel source) {
            return new RouteListRef(source);
        }

        public RouteListRef[] newArray(int size) {
            return new RouteListRef[size];
        }
    };
    private String href;

    private RouteListRef(Builder init) {
        Precondition.isNotNull(init);
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        private String city;
        private String country;
        private String fieldSet;
        private double latitude;
        private double longitude;
        private double maxDistance;
        private double minDistance;
        private Privacy.Level privacy;
        private double radius;
        private String state;
        private EntityRef<User> user;

        protected Builder() {
            super("/v7.0/route/");
        }

        public Builder setUser(EntityRef<User> user2) {
            this.user = user2;
            setParam("user", user2.getId());
            return this;
        }

        public Builder setLocation(double lat, double lng) {
            this.latitude = lat;
            this.longitude = lng;
            setParam("close_to_location", String.format("%f,%f", new Object[]{Double.valueOf(this.latitude), Double.valueOf(this.longitude)}));
            return this;
        }

        public Builder setRadius(double radius2) {
            this.radius = radius2;
            setParam("search_radius", String.valueOf(radius2));
            return this;
        }

        public Builder setMinDistance(double minDistance2) {
            this.minDistance = minDistance2;
            setParam("minimum_distance", String.valueOf(minDistance2));
            return this;
        }

        public Builder setMaxDistance(double maxDistance2) {
            this.maxDistance = maxDistance2;
            setParam("maximum_distance", String.valueOf(maxDistance2));
            return this;
        }

        public Builder setPrivacy(Privacy.Level privacy2) {
            this.privacy = privacy2;
            setParam("privacy", privacy2.id);
            return this;
        }

        public Builder setCity(String city2) {
            this.city = city2;
            setParam("city", String.valueOf(city2));
            return this;
        }

        public Builder setState(String state2) {
            this.state = state2;
            setParam("state", String.valueOf(state2));
            return this;
        }

        public Builder setCountry(String country2) {
            this.country = country2;
            setParam("country", String.valueOf(country2));
            return this;
        }

        public Builder setFieldSet(String fieldSet2) {
            this.fieldSet = fieldSet2;
            setParam("field_set", String.valueOf(fieldSet2));
            return this;
        }

        public RouteListRef build() {
            if (getParam("user") != null || getParam("close_to_location") != null) {
                return new RouteListRef(this);
            }
            throw new IllegalStateException("Must specify either user or location.");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private RouteListRef(Parcel in) {
        this.href = in.readString();
    }
}
