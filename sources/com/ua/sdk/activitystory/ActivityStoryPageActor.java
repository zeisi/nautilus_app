package com.ua.sdk.activitystory;

import com.ua.sdk.EntityRef;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.activitystory.ActivityStoryActor;
import com.ua.sdk.page.Page;

public interface ActivityStoryPageActor extends ActivityStoryActor {
    String getAlias();

    ImageUrl getCoverPhoto();

    EntityRef<Page> getPageRef();

    ImageUrl getProfilePhoto();

    String getTitle();

    ActivityStoryActor.Type getType();
}
