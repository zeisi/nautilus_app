package com.ua.sdk;

import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.user.User;

public interface MetabolicEnergyCalculator {
    double calculateJoules(User user, ActivityType activityType, double d, double d2);
}
