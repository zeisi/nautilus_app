package com.ua.sdk.workout;

import android.content.Context;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;
import java.util.Date;

public class WorkoutNameGeneratorImpl implements WorkoutNameGenerator {
    public String generateName(User user, ActivityType activityType, Context context, Date startDate, Double distanceMeters) {
        Precondition.isNotNull(activityType, "activity type");
        return activityType.getName();
    }
}
