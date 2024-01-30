package com.ua.sdk.page.follow;

import android.os.Parcel;
import android.os.Parcelable;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.user.User;

public class PageFollowListRef implements EntityListRef<PageFollow> {
    public static Parcelable.Creator<PageFollowListRef> CREATOR = new Parcelable.Creator<PageFollowListRef>() {
        public PageFollowListRef createFromParcel(Parcel source) {
            return new PageFollowListRef(source);
        }

        public PageFollowListRef[] newArray(int size) {
            return new PageFollowListRef[size];
        }
    };
    private String href;

    private PageFollowListRef(Builder init) {
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

    public static class Builder extends BaseReferenceBuilder {
        private String id;
        private EntityRef<User> user;

        private Builder() {
            super("/v7.0/page_follow/");
        }

        public Builder setUserId(String id2) {
            this.id = id2;
            return this;
        }

        public Builder setUser(EntityRef<User> user2) {
            this.user = user2;
            return this;
        }

        public PageFollowListRef build() {
            if (this.id == null && this.user == null) {
                throw new NullPointerException("user id is null.");
            }
            if (this.user == null) {
                setParam(MyFitnessPalConstants.MFP_USER_ID_KEY, this.id);
            } else if (this.id == null) {
                setParam(MyFitnessPalConstants.MFP_USER_ID_KEY, this.user.getId());
            } else {
                throw new IllegalStateException("cannot specify a user and an id");
            }
            return new PageFollowListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    private PageFollowListRef(Parcel in) {
        this.href = in.readString();
    }
}
