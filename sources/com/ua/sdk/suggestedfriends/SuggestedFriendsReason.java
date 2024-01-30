package com.ua.sdk.suggestedfriends;

import android.os.Parcelable;

public interface SuggestedFriendsReason extends Parcelable {
    String getSource();

    Double getWeight();
}
