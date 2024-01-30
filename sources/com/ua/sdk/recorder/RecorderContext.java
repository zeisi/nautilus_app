package com.ua.sdk.recorder;

import android.content.Context;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.heartrate.HeartRateZones;
import com.ua.sdk.user.User;

public class RecorderContext {
    private ActivityType activityType;
    private Context applicationContext;
    private RecorderClock clock;
    private HeartRateZones heartRateZones;
    private String name;
    private User user;

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(Context applicationContext2) {
        this.applicationContext = applicationContext2;
    }

    public RecorderClock getClock() {
        return this.clock;
    }

    public void setClock(RecorderClock clock2) {
        this.clock = clock2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user2) {
        this.user = user2;
    }

    public ActivityType getActivityType() {
        return this.activityType;
    }

    public void setActivityType(ActivityType activityType2) {
        this.activityType = activityType2;
    }

    public HeartRateZones getHeartRateZones() {
        return this.heartRateZones;
    }

    public void setHeartRateZones(HeartRateZones heartRateZones2) {
        this.heartRateZones = heartRateZones2;
    }
}
