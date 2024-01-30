package com.ua.sdk.heartrate;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;

public class HeartRateZone extends ApiTransferObject implements Parcelable {
    public static final Parcelable.Creator<HeartRateZone> CREATOR = new Parcelable.Creator<HeartRateZone>() {
        public HeartRateZone createFromParcel(Parcel source) {
            return new HeartRateZone(source);
        }

        public HeartRateZone[] newArray(int size) {
            return new HeartRateZone[size];
        }
    };
    @SerializedName("end")
    int end;
    @SerializedName("name")
    private String name;
    private transient EntityRef<HeartRateZones> selfRef;
    @SerializedName("start")
    private int start;

    public HeartRateZone() {
    }

    public HeartRateZone(String name2, int start2, int end2) {
        this.name = name2;
        this.start = start2;
        this.end = end2;
    }

    private HeartRateZone(Parcel in) {
        super(in);
        this.name = in.readString();
        this.start = in.readInt();
        this.end = in.readInt();
    }

    public String getName() {
        return this.name;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeInt(this.start);
        dest.writeInt(this.end);
    }
}
