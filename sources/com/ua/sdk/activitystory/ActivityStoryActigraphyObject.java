package com.ua.sdk.activitystory;

import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.privacy.Privacy;
import java.util.Date;
import java.util.List;

public interface ActivityStoryActigraphyObject extends ActivityStoryObject {
    Date getEndTime();

    List<ActivityStoryHighlight> getHighlights();

    Privacy getPrivacy();

    Date getPublishedTime();

    Date getStartTime();

    Integer getSteps();

    ActivityStoryObject.Type getType();
}
