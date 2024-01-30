package com.ua.sdk.user.stats;

import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitytype.ActivityType;

public interface Stats extends Parcelable {
    Integer getActivityCount();

    EntityRef<ActivityType> getActivityTypeRef();

    AggregatePeriod getAggregatePeriod();

    Double getAveragePace();

    Double getAverageSpeed();

    Double getDistance();

    Double getDuration();

    Double getEnergy();

    HeartRateTimesAggregate getHeartRateTimeAggregate();
}
