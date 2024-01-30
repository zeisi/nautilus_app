package com.ua.sdk.friendship;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;

public class FriendshipListRef implements EntityListRef<Friendship> {
    public static Parcelable.Creator<FriendshipListRef> CREATOR = new Parcelable.Creator<FriendshipListRef>() {
        public FriendshipListRef createFromParcel(Parcel source) {
            return new FriendshipListRef(source);
        }

        public FriendshipListRef[] newArray(int size) {
            return new FriendshipListRef[size];
        }
    };
    private final String href;

    private FriendshipListRef(Builder init) {
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
        EntityRef<User> fromUser;
        String href;
        FriendshipStatus status;
        EntityRef<User> toUser;

        private Builder() {
            super("/v7.0/friendship/");
        }

        public Builder setHref(String href2) {
            this.href = href2;
            return this;
        }

        public Builder setToUser(EntityRef<User> toUser2) {
            this.toUser = toUser2;
            return this;
        }

        public Builder setFromUser(EntityRef<User> fromUser2) {
            this.fromUser = fromUser2;
            return this;
        }

        public Builder setFriendshipStatus(FriendshipStatus status2) {
            this.status = status2;
            return this;
        }

        public FriendshipListRef build() {
            if (this.status == FriendshipStatus.NONE) {
                Precondition.check(false, "none is not a valid status type.");
            }
            if (this.fromUser == null && this.toUser == null) {
                Precondition.check(false, "a from_user and/or a to_user filter must be defined.");
            }
            if (this.fromUser != null) {
                setParam(FriendshipImpl.ARG_FROM_USER, this.fromUser.getId());
            }
            if (this.toUser != null) {
                setParam(FriendshipImpl.ARG_TO_USER, this.toUser.getId());
            }
            if (this.status != null) {
                if (this.fromUser == null || this.toUser == null) {
                    setParam("status", this.status.getValue());
                } else {
                    Precondition.check(false, "a status cannot be applied when a to_user and from_user filter are applied");
                }
            }
            return new FriendshipListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private FriendshipListRef(Parcel in) {
        this.href = in.readString();
    }
}
