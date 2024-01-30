package com.ua.sdk.activitytype;

import com.ua.sdk.Entity;
import java.util.Date;

public interface ActivityType extends Entity {
    Boolean canCalculateCalories();

    Date getAccessedDate();

    String getActivityId();

    Boolean getHasChildren();

    String getIconUrl();

    String getMetsSpeed();

    Double getMetsValue();

    String getName();

    String getParentActivityId();

    String getParentUrl();

    ActivityTypeRef getRef();

    String getRootActivityId();

    String getRootUrl();

    String getShortName();

    boolean hasChildren();
}
