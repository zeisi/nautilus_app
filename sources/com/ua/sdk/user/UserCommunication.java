package com.ua.sdk.user;

import android.os.Parcelable;

public interface UserCommunication extends Parcelable {
    Boolean getNewsletter();

    Boolean getPromotions();

    Boolean getSystemMessages();

    boolean isNewsletter();

    boolean isPromotions();

    boolean isSystemMessages();

    void setNewsletter(Boolean bool);

    void setPromotions(Boolean bool);

    void setSystemMessages(Boolean bool);
}
