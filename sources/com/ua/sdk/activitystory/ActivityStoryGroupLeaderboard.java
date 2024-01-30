package com.ua.sdk.activitystory;

import android.os.Parcelable;

public interface ActivityStoryGroupLeaderboard extends Parcelable {
    int getRank();

    long getUserId();

    Double getValueDouble();

    Long getValueLong();
}
