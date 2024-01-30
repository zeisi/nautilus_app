package com.ua.sdk.workout;

import android.content.Context;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.user.User;
import java.util.Date;

public interface WorkoutNameGenerator {
    String generateName(User user, ActivityType activityType, Context context, Date date, Double d);
}
