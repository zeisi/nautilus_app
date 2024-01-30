package com.ua.sdk.activitystory;

import android.os.Parcelable;

public interface ActivityStoryTarget extends Parcelable {

    public enum Type {
        GROUP,
        UNKNOWN
    }

    String getId();

    Type getType();
}
