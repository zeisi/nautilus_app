package com.ua.sdk.page;

public enum PageListViewEnum {
    INITIAL("initial"),
    SUGGESTED("suggested");
    
    private final String name;

    private PageListViewEnum(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}
