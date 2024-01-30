package com.ua.sdk.activitystory;

public enum ActivityStoryType {
    PUBLIC("public", false),
    USER("user", true),
    PAGE("page", true),
    GROUP("group", true),
    REPLY("reply", true),
    WORKOUT("workout", true);
    
    private final boolean idRequired;
    private final String value;

    private ActivityStoryType(String value2, boolean idRequired2) {
        this.value = value2;
        this.idRequired = idRequired2;
    }

    public String toString() {
        return this.value;
    }

    public boolean isIdRequired() {
        return this.idRequired;
    }
}
