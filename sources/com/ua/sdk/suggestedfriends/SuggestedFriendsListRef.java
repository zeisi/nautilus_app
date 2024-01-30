package com.ua.sdk.suggestedfriends;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.user.User;

public class SuggestedFriendsListRef implements EntityListRef<SuggestedFriends> {
    public static Parcelable.Creator<SuggestedFriendsListRef> CREATOR = new Parcelable.Creator<SuggestedFriendsListRef>() {
        public SuggestedFriendsListRef createFromParcel(Parcel source) {
            return new SuggestedFriendsListRef(source);
        }

        public SuggestedFriendsListRef[] newArray(int size) {
            return new SuggestedFriendsListRef[size];
        }
    };
    private final String href;

    private SuggestedFriendsListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getHref() {
        return this.href;
    }

    public String getId() {
        return null;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        EntityRef<User> user;

        private Builder() {
            super(UrlBuilderImpl.GET_SUGGESTED_FRIENDS_URL);
        }

        public Builder setUser(EntityRef<User> user2) {
            this.user = user2;
            setParam("user", user2.getId());
            return this;
        }

        public SuggestedFriendsListRef build() {
            Precondition.isNotNull(this.user);
            return new SuggestedFriendsListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private SuggestedFriendsListRef(Parcel in) {
        this.href = in.readString();
    }
}
