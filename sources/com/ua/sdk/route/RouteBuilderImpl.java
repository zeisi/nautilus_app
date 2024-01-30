package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class RouteBuilderImpl implements RouteBuilder, Parcelable {
    public static final Parcelable.Creator<RouteBuilderImpl> CREATOR = new Parcelable.Creator<RouteBuilderImpl>() {
        public RouteBuilderImpl createFromParcel(Parcel source) {
            return new RouteBuilderImpl(source);
        }

        public RouteBuilderImpl[] newArray(int size) {
            return new RouteBuilderImpl[size];
        }
    };
    String description;
    String name;
    ArrayList<Point> points;
    String postalCode;
    String startPointType;

    public RouteBuilderImpl() {
        this.points = new ArrayList<>();
    }

    public RouteBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public RouteBuilder setDescription(String description2) {
        this.description = description2;
        return this;
    }

    public RouteBuilder setStartPointType(String startPointType2) {
        this.startPointType = startPointType2;
        return this;
    }

    public RouteBuilder setPoints(ArrayList<Point> points2) {
        this.points = points2;
        return this;
    }

    public RouteBuilder setPostalCode(String postalCode2) {
        this.postalCode = postalCode2;
        return this;
    }

    public Route build() {
        if (this.name == null) {
            throw new IllegalArgumentException("A name must be specified.");
        } else if (this.points != null && !this.points.isEmpty()) {
            return new RouteImpl(this);
        } else {
            throw new IllegalArgumentException("Points must be specified.");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.startPointType);
        dest.writeList(this.points);
        dest.writeString(this.postalCode);
    }

    private RouteBuilderImpl(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.startPointType = in.readString();
        this.points = new ArrayList<>();
        in.readList(this.points, PointImpl.class.getClassLoader());
        this.postalCode = in.readString();
    }
}
