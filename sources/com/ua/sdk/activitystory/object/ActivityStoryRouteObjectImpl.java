package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.activitystory.ActivityStoryHighlight;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryRouteObject;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.location.Location;
import com.ua.sdk.privacy.Privacy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityStoryRouteObjectImpl extends ApiTransferObject implements ActivityStoryRouteObject, Parcelable {
    public static Parcelable.Creator<ActivityStoryRouteObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryRouteObjectImpl>() {
        public ActivityStoryRouteObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryRouteObjectImpl(source);
        }

        public ActivityStoryRouteObjectImpl[] newArray(int size) {
            return new ActivityStoryRouteObjectImpl[size];
        }
    };
    @SerializedName("distance")
    private Double mDistance;
    @SerializedName("highlights")
    private List<ActivityStoryHighlight> mHighlights;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("privacy")
    private Privacy mPrivacy;
    @SerializedName("title")
    private String mTitle;

    public ActivityStoryRouteObjectImpl() {
    }

    public void setDistance(Double mDistance2) {
        this.mDistance = mDistance2;
    }

    public void setTitle(String mTitle2) {
        this.mTitle = mTitle2;
    }

    public void setPrivacy(Privacy mPrivacy2) {
        this.mPrivacy = mPrivacy2;
    }

    public void setHighlights(List<ActivityStoryHighlight> mHighlights2) {
        this.mHighlights = mHighlights2;
    }

    public void setLocation(Location mLocation2) {
        this.mLocation = mLocation2;
    }

    public Double getDistance() {
        return this.mDistance;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public Privacy getPrivacy() {
        return this.mPrivacy;
    }

    public List<ActivityStoryHighlight> getHighlights() {
        if (this.mHighlights == null) {
            return Collections.emptyList();
        }
        return this.mHighlights;
    }

    public Location getLocation() {
        return this.mLocation;
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.ROUTE;
    }

    public String getRouteId() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return link.getId();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.mDistance);
        dest.writeString(this.mTitle);
        dest.writeParcelable(this.mPrivacy, flags);
        dest.writeList(this.mHighlights);
        dest.writeParcelable(this.mLocation, 0);
    }

    private ActivityStoryRouteObjectImpl(Parcel in) {
        super(in);
        this.mDistance = (Double) in.readValue(Double.class.getClassLoader());
        this.mTitle = in.readString();
        this.mPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.mHighlights = new ArrayList();
        in.readList(this.mHighlights, ActivityStoryHighlightImpl.class.getClassLoader());
        if (this.mHighlights.isEmpty()) {
            this.mHighlights = null;
        }
        this.mLocation = (Location) in.readParcelable(Location.class.getClassLoader());
    }
}
