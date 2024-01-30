package com.ua.sdk.activitystory;

public enum ActivityStoryReplyType {
    COMMENTS("comment"),
    LIKES("like"),
    REPOSTS("repost");
    
    private String value;

    private ActivityStoryReplyType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }
}
