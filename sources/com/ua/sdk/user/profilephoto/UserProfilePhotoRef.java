package com.ua.sdk.user.profilephoto;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class UserProfilePhotoRef implements EntityRef<UserProfilePhoto>, Parcelable {
    public static final Parcelable.Creator<UserProfilePhotoRef> CREATOR = new Parcelable.Creator<UserProfilePhotoRef>() {
        public UserProfilePhotoRef createFromParcel(Parcel source) {
            return new UserProfilePhotoRef(source);
        }

        public UserProfilePhotoRef[] newArray(int size) {
            return new UserProfilePhotoRef[size];
        }
    };
    private final String href;
    private final String id;

    private UserProfilePhotoRef(Builder init) {
        this.id = init.id;
        this.href = init.getHref();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;

        private Builder() {
            super("/v7.0/user_profile_photo/{id}/");
        }

        public Builder setId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public UserProfilePhotoRef build() {
            return new UserProfilePhotoRef(this);
        }
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
    }

    private UserProfilePhotoRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
    }
}
