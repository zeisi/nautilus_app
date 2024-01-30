package com.ua.sdk.gear.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.LocalDate;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.gear.Gear;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.List;

public class UserGearImpl extends ApiTransferObject implements UserGear {
    public static final Parcelable.Creator<UserGearImpl> CREATOR = new Parcelable.Creator<UserGearImpl>() {
        public UserGearImpl createFromParcel(Parcel source) {
            return new UserGearImpl(source);
        }

        public UserGearImpl[] newArray(int size) {
            return new UserGearImpl[size];
        }
    };
    private static final String REF_DEFAULT_ACTIVITIES = "default_activities";
    private static final String REF_SELF = "self";
    private static final String REF_USER = "user";
    @SerializedName("current_distance")
    Double currentDistance;
    @SerializedName("gear")
    Gear gear;
    @SerializedName("initial_distance")
    Double initialDistance;
    @SerializedName("retired")
    Boolean isRetired;
    @SerializedName("name")
    String name;
    @SerializedName("purchase_date")
    LocalDate purchaseDate;
    @SerializedName("target_distance")
    Double targetDistance;

    public UserGearImpl() {
    }

    public Gear getGear() {
        return this.gear;
    }

    public String getName() {
        return this.name;
    }

    public Double getInitialDistance() {
        return this.initialDistance;
    }

    public Double getTargetDistance() {
        return this.targetDistance;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public Double getCurrentDistance() {
        return this.currentDistance;
    }

    public Boolean isRetired() {
        return this.isRetired;
    }

    public EntityRef<User> getUser() {
        Link link = getLink("user");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public List<EntityRef<ActivityType>> getDefaultActivities() {
        List<Link> links = getLinks(REF_DEFAULT_ACTIVITIES);
        if (links == null) {
            return null;
        }
        List<EntityRef<ActivityType>> activityTypes = new ArrayList<>(links.size());
        for (Link link : links) {
            activityTypes.add(new LinkEntityRef(link.getId(), link.getHref()));
        }
        return activityTypes;
    }

    public void setGear(Gear gear2) {
        this.gear = gear2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setPurchaseDate(LocalDate date) {
        this.purchaseDate = date;
    }

    public void setInitialDistance(Double distance) {
        this.initialDistance = distance;
    }

    public void setTargetDistance(Double distance) {
        this.targetDistance = distance;
    }

    public void setDefaultActivities(List<EntityRef<ActivityType>> activities) {
        for (EntityRef activity : activities) {
            addLink(REF_DEFAULT_ACTIVITIES, new Link(activity.getHref(), activity.getId()));
        }
    }

    public void setRetired(Boolean isRetired2) {
        this.isRetired = isRetired2;
    }

    public UserGearRef getRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new UserGearRef(link.getId(), link.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.gear, 0);
        dest.writeString(this.name);
        dest.writeValue(this.initialDistance);
        dest.writeValue(this.targetDistance);
        dest.writeParcelable(this.purchaseDate, 0);
        dest.writeValue(this.currentDistance);
        dest.writeValue(this.isRetired);
    }

    private UserGearImpl(Parcel in) {
        super(in);
        this.gear = (Gear) in.readParcelable(Gear.class.getClassLoader());
        this.name = in.readString();
        this.initialDistance = (Double) in.readValue(Double.class.getClassLoader());
        this.targetDistance = (Double) in.readValue(Double.class.getClassLoader());
        this.purchaseDate = (LocalDate) in.readParcelable(LocalDate.class.getClassLoader());
        this.currentDistance = (Double) in.readValue(Double.class.getClassLoader());
        this.isRetired = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }
}
