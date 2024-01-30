package com.ua.sdk;

public interface Source extends Entity<EntityRef<Source>> {
    String getId();

    String getSiteName();

    String getUrl();

    void setId(String str);

    void setSiteName(String str);

    void setUrl(String str);
}
