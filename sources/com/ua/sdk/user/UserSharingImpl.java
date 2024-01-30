package com.ua.sdk.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class UserSharingImpl implements UserSharing, Parcelable {
    public static Parcelable.Creator<UserSharingImpl> CREATOR = new Parcelable.Creator<UserSharingImpl>() {
        public UserSharingImpl createFromParcel(Parcel source) {
            return new UserSharingImpl(source);
        }

        public UserSharingImpl[] newArray(int size) {
            return new UserSharingImpl[size];
        }
    };
    @SerializedName("facebook")
    Boolean facebook;
    @SerializedName("twitter")
    Boolean twitter;

    public UserSharingImpl() {
    }

    public Boolean getTwitter() {
        return this.twitter;
    }

    public boolean isTwitter() {
        return this.twitter != null && this.twitter.booleanValue();
    }

    public void setTwitter(Boolean twitter2) {
        this.twitter = twitter2;
    }

    public Boolean getFacebook() {
        return this.facebook;
    }

    public boolean isFacebook() {
        return this.facebook != null && this.facebook.booleanValue();
    }

    public void setFacebook(Boolean facebook2) {
        this.facebook = facebook2;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Boolean facebook;
        private Boolean twitter;

        public Builder setTwitter(Boolean twitter2) {
            this.twitter = twitter2;
            return this;
        }

        public Builder setFacebook(Boolean facebook2) {
            this.facebook = facebook2;
            return this;
        }

        public UserSharingImpl build() {
            UserSharingImpl answer = new UserSharingImpl();
            answer.setTwitter(this.twitter);
            answer.setFacebook(this.facebook);
            return answer;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.twitter);
        dest.writeValue(this.facebook);
    }

    private UserSharingImpl(Parcel in) {
        this.twitter = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.facebook = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }
}
