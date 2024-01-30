package com.ua.sdk.friendship;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class FriendshipListImpl extends AbstractEntityList<Friendship> {
    public static Parcelable.Creator<FriendshipListImpl> CREATOR = new Parcelable.Creator<FriendshipListImpl>() {
        public FriendshipListImpl createFromParcel(Parcel source) {
            return new FriendshipListImpl(source);
        }

        public FriendshipListImpl[] newArray(int size) {
            return new FriendshipListImpl[size];
        }
    };

    public FriendshipListImpl() {
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return FriendshipPageTransferObject.KEY_FRIENDSHIPS;
    }

    private FriendshipListImpl(Parcel in) {
        super(in);
    }
}
