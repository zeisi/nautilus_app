package com.ua.sdk.friendship;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.internal.net.v7.UrlBuilderImpl;
import com.ua.sdk.user.User;

public class FriendshipRef implements EntityRef<Friendship> {
    public static Parcelable.Creator<FriendshipRef> CREATOR = new Parcelable.Creator<FriendshipRef>() {
        public FriendshipRef createFromParcel(Parcel source) {
            return new FriendshipRef(source);
        }

        public FriendshipRef[] newArray(int size) {
            return new FriendshipRef[size];
        }
    };
    private final String fromUserId;
    private final String href;
    private final String id;
    private final String toUserId;

    private FriendshipRef(Builder init) {
        this.href = init.href;
        this.id = init.id;
        this.toUserId = init.toUserId;
        this.fromUserId = init.fromUserId;
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public String getToUserId() {
        return this.toUserId;
    }

    public String getFromUserId() {
        return this.fromUserId;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static final class Builder extends BaseReferenceBuilder {
        String fromUserId;
        String href;
        String id;
        String toUserId;

        private Builder() {
            super(UrlBuilderImpl.CHANGE_FRIENDSHIP_URL);
        }

        public Builder setHref(String href2) {
            this.href = href2;
            return this;
        }

        public Builder setToUser(EntityRef<User> toUser) {
            this.toUserId = toUser.getId();
            return this;
        }

        public Builder setFromUser(EntityRef<User> fromUser) {
            this.fromUserId = fromUser.getId();
            return this;
        }

        public Builder setToUser(String id2) {
            this.toUserId = id2;
            return this;
        }

        public Builder setFromUser(String id2) {
            this.fromUserId = id2;
            return this;
        }

        public FriendshipRef build() {
            Precondition.isNotNull(this.fromUserId, "fromUser must be defined");
            Precondition.isNotNull(this.toUserId, "toUser must be defined");
            this.id = this.fromUserId + '_' + this.toUserId;
            setHref(String.format(UrlBuilderImpl.CHANGE_FRIENDSHIP_URL, new Object[]{this.id}));
            return new FriendshipRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
        dest.writeString(this.toUserId);
        dest.writeString(this.fromUserId);
    }

    private FriendshipRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
        this.toUserId = in.readString();
        this.fromUserId = in.readString();
    }
}
