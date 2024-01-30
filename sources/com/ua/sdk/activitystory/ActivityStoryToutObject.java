package com.ua.sdk.activitystory;

import com.ua.sdk.activitystory.ActivityStoryObject;

public interface ActivityStoryToutObject extends ActivityStoryObject {

    public enum Subtype {
        FIND_FRIENDS
    }

    Subtype getSubtype();

    ActivityStoryObject.Type getType();
}
