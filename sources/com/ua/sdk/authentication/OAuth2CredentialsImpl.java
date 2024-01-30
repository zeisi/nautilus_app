package com.ua.sdk.authentication;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.Reference;

public class OAuth2CredentialsImpl implements OAuth2Credentials, Parcelable {
    public static final Parcelable.Creator<OAuth2CredentialsImpl> CREATOR = new Parcelable.Creator<OAuth2CredentialsImpl>() {
        public OAuth2CredentialsImpl createFromParcel(Parcel source) {
            return new OAuth2CredentialsImpl(source);
        }

        public OAuth2CredentialsImpl[] newArray(int size) {
            return new OAuth2CredentialsImpl[size];
        }
    };
    private String accessToken;
    private Long expiresAt;
    private String refreshToken;

    public OAuth2CredentialsImpl() {
    }

    public Reference getRef() {
        return null;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken2) {
        this.accessToken = accessToken2;
    }

    public Long getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(Long expiresAt2) {
        this.expiresAt = expiresAt2;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken2) {
        this.refreshToken = refreshToken2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeValue(this.expiresAt);
        dest.writeString(this.refreshToken);
    }

    private OAuth2CredentialsImpl(Parcel in) {
        this.accessToken = in.readString();
        this.expiresAt = (Long) in.readValue(Long.class.getClassLoader());
        this.refreshToken = in.readString();
    }
}
