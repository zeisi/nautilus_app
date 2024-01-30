package com.ua.sdk.suggestedfriends;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class SuggestedFriendsReasonImpl implements SuggestedFriendsReason {
    public static Parcelable.Creator<SuggestedFriendsReasonImpl> CREATOR = new Parcelable.Creator<SuggestedFriendsReasonImpl>() {
        public SuggestedFriendsReasonImpl createFromParcel(Parcel source) {
            return new SuggestedFriendsReasonImpl(source);
        }

        public SuggestedFriendsReasonImpl[] newArray(int size) {
            return new SuggestedFriendsReasonImpl[size];
        }
    };
    @SerializedName("source")
    String source;
    @SerializedName("weight")
    Double weight;

    public SuggestedFriendsReasonImpl() {
    }

    protected SuggestedFriendsReasonImpl(String source2, Double weight2) {
        this.source = source2;
        this.weight = weight2;
    }

    public String getSource() {
        return this.source;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setSource(String source2) {
        this.source = source2;
    }

    public void setWeight(Double weight2) {
        this.weight = weight2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.source);
        dest.writeValue(this.weight);
    }

    private SuggestedFriendsReasonImpl(Parcel in) {
        this.source = in.readString();
        this.weight = (Double) in.readValue(Double.class.getClassLoader());
    }
}
