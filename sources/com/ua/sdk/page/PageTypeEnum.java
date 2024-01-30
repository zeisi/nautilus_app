package com.ua.sdk.page;

public enum PageTypeEnum {
    PERSONAL("personal"),
    PUBLIC_ENTITY("public_entity"),
    PUBLIC_FIGURE("public_figure");
    
    private final String name;

    private PageTypeEnum(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    public static PageTypeEnum getById(String id) {
        for (PageTypeEnum type : values()) {
            if (type.toString().toLowerCase().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
