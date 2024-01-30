package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class SocialSettings implements Parcelable {
    public static final Parcelable.Creator<SocialSettings> CREATOR = new Parcelable.Creator<SocialSettings>() {
        public SocialSettings createFromParcel(Parcel source) {
            return new SocialSettings(source);
        }

        public SocialSettings[] newArray(int size) {
            return new SocialSettings[size];
        }
    };
    @SerializedName("facebook")
    Boolean facebook;
    @SerializedName("twitter")
    Boolean twitter;

    public Boolean getFacebook() {
        return this.facebook;
    }

    public void setFacebook(Boolean facebook2) {
        this.facebook = facebook2;
    }

    public Boolean getTwitter() {
        return this.twitter;
    }

    public void setTwitter(Boolean twitter2) {
        this.twitter = twitter2;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.facebook);
        dest.writeValue(this.twitter);
    }

    public SocialSettings() {
    }

    public SocialSettings(Builder init) {
        this.facebook = init.facebook;
        this.twitter = init.twitter;
    }

    private SocialSettings(Parcel in) {
        this.facebook = (Boolean) in.readValue((ClassLoader) null);
        this.twitter = (Boolean) in.readValue((ClassLoader) null);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SocialSettings that = (SocialSettings) o;
        if (this.facebook == null ? that.facebook != null : !this.facebook.equals(that.facebook)) {
            return false;
        }
        if (this.twitter != null) {
            if (this.twitter.equals(that.twitter)) {
                return true;
            }
        } else if (that.twitter == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.facebook != null) {
            result = this.facebook.hashCode();
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.twitter != null) {
            i = this.twitter.hashCode();
        }
        return i2 + i;
    }

    public static class Builder {
        Boolean facebook;
        Boolean twitter;

        public Builder setFacebookShare(Boolean facebookShare) {
            this.facebook = facebookShare;
            return this;
        }

        public Builder setTwitterShare(Boolean twitterShare) {
            this.twitter = twitterShare;
            return this;
        }

        public SocialSettings build() {
            return new SocialSettings(this);
        }
    }
}
