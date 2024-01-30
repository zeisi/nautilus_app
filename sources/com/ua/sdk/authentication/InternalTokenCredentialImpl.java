package com.ua.sdk.authentication;

import android.os.Parcel;
import android.os.Parcelable;

public class InternalTokenCredentialImpl implements InternalTokenCredential, Parcelable {
    public static final Parcelable.Creator<InternalTokenCredentialImpl> CREATOR = new Parcelable.Creator<InternalTokenCredentialImpl>() {
        public InternalTokenCredentialImpl createFromParcel(Parcel source) {
            return new InternalTokenCredentialImpl(source);
        }

        public InternalTokenCredentialImpl[] newArray(int size) {
            return new InternalTokenCredentialImpl[size];
        }
    };
    private String secret;
    private String token;
    private String tokenType;

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType2) {
        this.tokenType = tokenType2;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret2) {
        this.secret = secret2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tokenType);
        dest.writeString(this.token);
        dest.writeString(this.secret);
    }

    public InternalTokenCredentialImpl() {
    }

    private InternalTokenCredentialImpl(Parcel in) {
        this.tokenType = in.readString();
        this.token = in.readString();
        this.secret = in.readString();
    }
}
