package com.ua.sdk.activitystory;

import com.ua.sdk.activitystory.ActivityStoryObject;

public interface ActivityStoryCommentObject extends ActivityStoryObject {
    String getText();

    ActivityStoryObject.Type getType();
}
