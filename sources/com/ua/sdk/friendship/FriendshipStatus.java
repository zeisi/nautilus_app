package com.ua.sdk.friendship;

public enum FriendshipStatus {
    NONE("none"),
    PENDING("pending"),
    ACTIVE("active");
    
    private String value;

    private FriendshipStatus(String value2) {
        this.value = value2;
    }

    public String getValue() {
        return this.value;
    }

    public static FriendshipStatus getStatusFromString(String value2) {
        for (FriendshipStatus status : values()) {
            if (status.value.equals(value2)) {
                return status;
            }
        }
        return null;
    }
}
