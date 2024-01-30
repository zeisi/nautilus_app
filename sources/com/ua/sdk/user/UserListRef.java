package com.ua.sdk.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.suggestedfriends.SuggestedFriendsImpl;

public class UserListRef implements EntityListRef<User> {
    public static Parcelable.Creator<UserListRef> CREATOR = new Parcelable.Creator<UserListRef>() {
        public UserListRef createFromParcel(Parcel source) {
            return new UserListRef(source);
        }

        public UserListRef[] newArray(int size) {
            return new UserListRef[size];
        }
    };
    private final String href;

    private UserListRef(Builder init) {
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
        private static final int EMAIL_LENGTH = 1024;
        private static final int EMAIL_LIMIT = 50;
        private String email;
        private String friendsWith;
        private String mutualFriends;
        private String name;
        private String query;
        private String requestedFriendshipWith;
        private String suggestedFriendsEmails;
        private String suggestedFriendsFor;
        private String suggestedFriendsSource;
        private String username;

        private Builder() {
            super("/v7.0/user/");
        }

        public Builder setFriendsWith(String userId) {
            Precondition.isNotNull(userId);
            Precondition.check(!TextUtils.isEmpty(userId), "You must provide a user id");
            this.friendsWith = userId;
            return this;
        }

        public Builder setMutualFriendsIds(String userIds) {
            Precondition.isNotNull(userIds);
            Precondition.check(!TextUtils.isEmpty(userIds), "You must provide user ids");
            this.mutualFriends = userIds;
            return this;
        }

        public Builder setRequestedFriendshipWith(String userId) {
            Precondition.isNotNull(userId);
            Precondition.check(!TextUtils.isEmpty(userId), "You must provide a user id");
            this.requestedFriendshipWith = userId;
            return this;
        }

        public Builder setSuggestedFriendsFor(EntityRef<User> user) {
            Precondition.isNotNull(user);
            Precondition.check(!TextUtils.isEmpty(user.getId()), "You must provide a user that has an id");
            this.suggestedFriendsFor = user.getId();
            return this;
        }

        public Builder setSuggestedFriendsSource(UserSource source) {
            Precondition.isNotNull(source);
            this.suggestedFriendsSource = source.getName();
            return this;
        }

        public Builder setSuggestedFriendsEmails(String emails) {
            Precondition.isNotNull(emails);
            Precondition.check(!TextUtils.isEmpty(emails), "You must provide comma separated emails");
            if (emails.length() <= 1024 || emails.length() - emails.replaceAll(",", "").length() <= 50) {
                this.suggestedFriendsEmails = emails;
                return this;
            }
            throw new IllegalArgumentException("Too many emails provided. Please limit them to at least 50.");
        }

        public Builder setQueryFilter(String query2) {
            Precondition.isNotNull(query2);
            Precondition.check(!TextUtils.isEmpty(query2), "You must provide a query");
            this.query = query2;
            return this;
        }

        public Builder setEmailFilter(String email2) {
            Precondition.isNotNull(email2);
            Precondition.check(!TextUtils.isEmpty(email2), "You must provide an email");
            this.email = email2;
            return this;
        }

        public Builder setNameFilter(String name2) {
            Precondition.isNotNull(name2);
            Precondition.check(!TextUtils.isEmpty(name2), "You must provide a name");
            this.name = name2;
            return this;
        }

        public Builder setUsernameFilter(String username2) {
            Precondition.isNotNull(username2);
            Precondition.check(!TextUtils.isEmpty(username2), "You must provide a username");
            this.username = username2;
            return this;
        }

        public UserListRef build() {
            if (this.friendsWith != null) {
                setParam("friends_with", this.friendsWith);
            } else if (this.mutualFriends != null) {
                setParam(SuggestedFriendsImpl.REF_MUTUAL_FRIENDS, this.mutualFriends);
            } else if (this.requestedFriendshipWith != null) {
                setParam("requested_friendship_with", this.requestedFriendshipWith);
            } else if (this.suggestedFriendsFor != null) {
                setParam("suggested_friends_for", this.suggestedFriendsFor);
                if (this.suggestedFriendsEmails == null && this.suggestedFriendsSource == null) {
                    throw new IllegalArgumentException("SuggestedFriends source or emails must be provided.");
                } else if (this.suggestedFriendsSource != null) {
                    setParam("suggested_friends_source", this.suggestedFriendsSource);
                } else if (this.suggestedFriendsEmails != null) {
                    setParam("suggested_friends_emails", this.suggestedFriendsEmails);
                }
            } else if (this.query != null) {
                setParam("q", this.query);
            } else if (this.email != null) {
                setParam("email", this.email);
            } else if (this.username != null) {
                setParam("username", this.username);
            } else if (this.name != null) {
                setParam("name", this.name);
            }
            return new UserListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private UserListRef(Parcel in) {
        this.href = in.readString();
    }
}
