package com.ua.sdk.user;

public enum UserSource {
    FACEBOOK("facebook"),
    MMF("mmf");
    
    private final String name;

    private UserSource(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}
