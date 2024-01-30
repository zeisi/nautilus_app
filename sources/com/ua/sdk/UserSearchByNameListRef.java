package com.ua.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;

@Deprecated
public class UserSearchByNameListRef implements EntityListRef<User> {
    public static Parcelable.Creator<UserSearchByNameListRef> CREATOR = new Parcelable.Creator<UserSearchByNameListRef>() {
        public UserSearchByNameListRef createFromParcel(Parcel source) {
            return new UserSearchByNameListRef(source);
        }

        public UserSearchByNameListRef[] newArray(int size) {
            return new UserSearchByNameListRef[size];
        }
    };
    private final String href;

    private UserSearchByNameListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        private String searchName;

        private Builder() {
            super("/v7.0/user/");
        }

        public Builder setSearchName(String searchName2) {
            this.searchName = searchName2;
            setParam("q", searchName2);
            return this;
        }

        public UserSearchByNameListRef build() {
            Precondition.isNotNull(this.searchName);
            return new UserSearchByNameListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private UserSearchByNameListRef(Parcel in) {
        this.href = in.readString();
    }
}
