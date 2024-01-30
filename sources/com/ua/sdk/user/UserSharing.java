package com.ua.sdk.user;

import android.os.Parcelable;

public interface UserSharing extends Parcelable {
    Boolean getFacebook();

    Boolean getTwitter();

    boolean isFacebook();

    boolean isTwitter();

    void setFacebook(Boolean bool);

    void setTwitter(Boolean bool);
}
