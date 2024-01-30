package com.ua.sdk.activitystory.object;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitystory.ActivityStoryHighlight;
import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.activitystory.ActivityStoryWorkoutObject;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.workout.WorkoutRef;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityStoryWorkoutObjectImpl extends ApiTransferObject implements ActivityStoryWorkoutObject {
    public static Parcelable.Creator<ActivityStoryWorkoutObjectImpl> CREATOR = new Parcelable.Creator<ActivityStoryWorkoutObjectImpl>() {
        public ActivityStoryWorkoutObjectImpl createFromParcel(Parcel source) {
            return new ActivityStoryWorkoutObjectImpl(source);
        }

        public ActivityStoryWorkoutObjectImpl[] newArray(int size) {
            return new ActivityStoryWorkoutObjectImpl[size];
        }
    };
    @SerializedName("avg_pace")
    Double mAveragePace;
    @SerializedName("avg_speed")
    Double mAverageSpeed;
    @SerializedName("distance")
    Double mDistance;
    @SerializedName("duration")
    Long mDuration;
    @SerializedName("energy_burned")
    Integer mEnergyBurned;
    @SerializedName("highlights")
    List<ActivityStoryHighlight> mHighlights;
    @SerializedName("notes")
    String mNotes;
    @SerializedName("privacy")
    Privacy mPrivacy;
    @SerializedName("steps")
    Integer mSteps;
    @SerializedName("title")
    String mTitle;

    public Double getDistance() {
        return this.mDistance;
    }

    public Double getAveragePace() {
        return this.mAveragePace;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public List<ActivityStoryHighlight> getHighlights() {
        if (this.mHighlights == null) {
            return Collections.emptyList();
        }
        return this.mHighlights;
    }

    public String getNotes() {
        return this.mNotes;
    }

    public Privacy getPrivacy() {
        return this.mPrivacy;
    }

    public Integer getSteps() {
        return this.mSteps;
    }

    public Double getAverageSpeed() {
        return this.mAverageSpeed;
    }

    public long getDuration() {
        if (this.mDuration == null) {
            return 0;
        }
        return this.mDuration.longValue();
    }

    public Integer getEnergyBurned() {
        return this.mEnergyBurned;
    }

    public EntityRef<ActivityType> getActivityTypeRef() {
        return null;
    }

    public WorkoutRef getWorkoutRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new WorkoutRef(link.getId(), link.getHref());
    }

    public ActivityStoryObject.Type getType() {
        return ActivityStoryObject.Type.WORKOUT;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.mDistance);
        dest.writeValue(this.mAveragePace);
        dest.writeString(this.mTitle);
        dest.writeString(this.mNotes);
        dest.writeParcelable(this.mPrivacy, flags);
        dest.writeValue(this.mSteps);
        dest.writeValue(this.mAverageSpeed);
        dest.writeValue(this.mEnergyBurned);
        dest.writeValue(this.mDuration);
        dest.writeList(this.mHighlights);
    }

    public ActivityStoryWorkoutObjectImpl() {
    }

    public ActivityStoryWorkoutObjectImpl(String notes) {
        this.mNotes = notes;
    }

    private ActivityStoryWorkoutObjectImpl(Parcel in) {
        super(in);
        this.mDistance = (Double) in.readValue(Double.class.getClassLoader());
        this.mAveragePace = (Double) in.readValue(Double.class.getClassLoader());
        this.mTitle = in.readString();
        this.mNotes = in.readString();
        this.mPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.mSteps = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mAverageSpeed = (Double) in.readValue(Double.class.getClassLoader());
        this.mEnergyBurned = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mDuration = (Long) in.readValue(Long.class.getClassLoader());
        this.mHighlights = new ArrayList();
        in.readList(this.mHighlights, ActivityStoryHighlightImpl.class.getClassLoader());
        if (this.mHighlights.isEmpty()) {
            this.mHighlights = null;
        }
    }
}
