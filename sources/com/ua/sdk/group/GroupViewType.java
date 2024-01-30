package com.ua.sdk.group;

public enum GroupViewType {
    MEMBER("member"),
    INVITED("invited"),
    COMPLETED("completed"),
    IN_PROGRESS("in_progress"),
    ALL("all");
    
    private final String name;

    private GroupViewType(String name2) {
        this.name = name2;
    }

    public String toString() {
        return this.name;
    }
}
