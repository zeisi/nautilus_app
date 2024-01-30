package com.ua.sdk.activitystory;

public enum ActivityStoryView {
    PUBLIC_STATUS("status", ActivityStoryType.PUBLIC),
    PUBLIC_PHOTO("photo", ActivityStoryType.PUBLIC),
    PUBLIC_VIDEO("video", ActivityStoryType.PUBLIC),
    PUBLIC_WORKOUT("workout", ActivityStoryType.PUBLIC),
    USER_ME("me", ActivityStoryType.USER),
    PAGE_SELF("self", ActivityStoryType.PAGE),
    PAGE_FEATURED("featured", ActivityStoryType.PAGE);
    
    private final ActivityStoryType type;
    private final String value;

    private ActivityStoryView(String value2, ActivityStoryType type2) {
        this.value = value2;
        this.type = type2;
    }

    public String toString() {
        return this.value;
    }

    public ActivityStoryType getActivityStoryType() {
        return this.type;
    }
}
