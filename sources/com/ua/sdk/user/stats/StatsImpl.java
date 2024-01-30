package com.ua.sdk.user.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class StatsImpl extends ApiTransferObject implements Stats {
    public static Parcelable.Creator<StatsImpl> CREATOR = new Parcelable.Creator<StatsImpl>() {
        public StatsImpl createFromParcel(Parcel source) {
            return new StatsImpl(source);
        }

        public StatsImpl[] newArray(int size) {
            return new StatsImpl[size];
        }
    };
    protected static final String REF_ACTIVITY_TYPE = "activity_type";
    @SerializedName("activity_count")
    Integer activityCount;
    @SerializedName("aggregate_period")
    AggregatePeriodImpl aggregatePeriod;
    @SerializedName("avg_pace")
    Double averagePace;
    @SerializedName("avg_speed")
    Double averageSpeed;
    @SerializedName("distance")
    Double distance;
    @SerializedName("duration")
    Double duration;
    @SerializedName("energy")
    Double energy;
    @SerializedName("time_in_heart_rate_zones")
    HeartRateTimesAggregateImpl heartRateTimesAggregate;

    public StatsImpl() {
    }

    public Double getDistance() {
        return this.distance;
    }

    public HeartRateTimesAggregate getHeartRateTimeAggregate() {
        return this.heartRateTimesAggregate;
    }

    public Double getAveragePace() {
        return this.averagePace;
    }

    public Integer getActivityCount() {
        return this.activityCount;
    }

    public Double getEnergy() {
        return this.energy;
    }

    public AggregatePeriod getAggregatePeriod() {
        return this.aggregatePeriod;
    }

    public EntityRef<ActivityType> getActivityTypeRef() {
        Link link = getLink(REF_ACTIVITY_TYPE);
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public Double getDuration() {
        return this.duration;
    }

    public Double getAverageSpeed() {
        return this.averageSpeed;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.distance);
        dest.writeParcelable(this.heartRateTimesAggregate, flags);
        dest.writeValue(this.averagePace);
        dest.writeValue(this.activityCount);
        dest.writeValue(this.energy);
        dest.writeParcelable(this.aggregatePeriod, flags);
        dest.writeValue(this.duration);
        dest.writeValue(this.averageSpeed);
    }

    private StatsImpl(Parcel in) {
        super(in);
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.heartRateTimesAggregate = (HeartRateTimesAggregateImpl) in.readParcelable(HeartRateTimesAggregate.class.getClassLoader());
        this.averagePace = (Double) in.readValue(Double.class.getClassLoader());
        this.activityCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.energy = (Double) in.readValue(Double.class.getClassLoader());
        this.aggregatePeriod = (AggregatePeriodImpl) in.readParcelable(AggregatePeriod.class.getClassLoader());
        this.duration = (Double) in.readValue(Double.class.getClassLoader());
        this.averageSpeed = (Double) in.readValue(Double.class.getClassLoader());
    }
}
