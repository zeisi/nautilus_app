package com.ua.sdk.user.profilephoto;

import com.ua.sdk.Entity;

public interface UserProfilePhoto extends Entity {
    String getLargeImageURL();

    String getMediumImageURL();

    String getSmallImageURL();
}
