package com.ua.sdk.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.Reference;
import com.ua.sdk.internal.AbstractEntityList;

public class UserListImpl extends AbstractEntityList<User> {
    public static Parcelable.Creator<UserListImpl> CREATOR = new Parcelable.Creator<UserListImpl>() {
        public UserListImpl createFromParcel(Parcel source) {
            return new UserListImpl(source);
        }

        public UserListImpl[] newArray(int size) {
            return new UserListImpl[size];
        }
    };

    public UserListImpl() {
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "user";
    }

    public int describeContents() {
        return 0;
    }

    private UserListImpl(Parcel in) {
        super(in);
    }

    public boolean preparePartials(Reference ref) {
        if (ref == null || !ref.getHref().contains("friends_with")) {
            return false;
        }
        for (User user : getAll()) {
            if (user instanceof UserImpl) {
                ((UserImpl) user).setObjectState(UserObjectState.FRIENDS_WITH);
            }
        }
        return true;
    }
}
