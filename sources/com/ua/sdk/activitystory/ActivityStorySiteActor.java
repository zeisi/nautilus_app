package com.ua.sdk.activitystory;

import com.ua.sdk.activitystory.ActivityStoryActor;

public interface ActivityStorySiteActor extends ActivityStoryActor {
    ActivityStoryActor.Type getType();
}
