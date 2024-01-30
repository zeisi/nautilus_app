package com.ua.sdk.activitystory;

import android.os.Parcelable;

public interface ActivityStoryHighlight extends Parcelable {
    String getKey();

    Double getPercentile();

    String getThumbnailUrl();

    Number getValue();
}
