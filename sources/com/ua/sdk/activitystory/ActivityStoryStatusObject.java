package com.ua.sdk.activitystory;

import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.privacy.Privacy;

public interface ActivityStoryStatusObject extends ActivityStoryObject {
    Privacy getPrivacy();

    String getText();

    ActivityStoryObject.Type getType();
}
