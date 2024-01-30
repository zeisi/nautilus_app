package com.ua.sdk.moderation;

import com.ua.sdk.Entity;

public interface ModerationAction extends Entity {
    String getAction();

    String getResource();

    void setAction(String str);

    void setResource(String str);
}
