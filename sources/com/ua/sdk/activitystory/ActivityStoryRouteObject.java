package com.ua.sdk.activitystory;

import com.ua.sdk.activitystory.ActivityStoryObject;
import com.ua.sdk.location.Location;
import com.ua.sdk.privacy.Privacy;
import java.util.List;

public interface ActivityStoryRouteObject extends ActivityStoryObject {
    Double getDistance();

    List<ActivityStoryHighlight> getHighlights();

    Location getLocation();

    Privacy getPrivacy();

    String getTitle();

    ActivityStoryObject.Type getType();
}
