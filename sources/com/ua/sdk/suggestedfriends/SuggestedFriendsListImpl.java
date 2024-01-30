package com.ua.sdk.suggestedfriends;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class SuggestedFriendsListImpl extends AbstractEntityList<SuggestedFriends> {
    public static Parcelable.Creator<SuggestedFriendsListImpl> CREATOR = new Parcelable.Creator<SuggestedFriendsListImpl>() {
        public SuggestedFriendsListImpl createFromParcel(Parcel source) {
            return new SuggestedFriendsListImpl(source);
        }

        public SuggestedFriendsListImpl[] newArray(int size) {
            return new SuggestedFriendsListImpl[size];
        }
    };
    private static final String LIST_KEY = "suggestions";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public SuggestedFriendsListImpl() {
    }

    private SuggestedFriendsListImpl(Parcel in) {
        super(in);
    }
}
