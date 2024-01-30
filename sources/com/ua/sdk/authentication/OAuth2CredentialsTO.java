package com.ua.sdk.authentication;

import com.google.gson.annotations.SerializedName;

public class OAuth2CredentialsTO {
    @SerializedName("access_token")
    String accessToken;
    @SerializedName("expires_in")
    Long expiresIn;
    @SerializedName("refresh_token")
    String refreshToken;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken2) {
        this.accessToken = accessToken2;
    }

    public Long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(Long expiresIn2) {
        this.expiresIn = expiresIn2;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken2) {
        this.refreshToken = refreshToken2;
    }

    public static OAuth2Credentials toImpl(OAuth2CredentialsTO from) {
        OAuth2Credentials answer = new OAuth2CredentialsImpl();
        answer.setAccessToken(from.getAccessToken());
        answer.setRefreshToken(from.getRefreshToken());
        answer.setExpiresAt(Long.valueOf(System.currentTimeMillis() + (from.getExpiresIn().longValue() * 1000)));
        return answer;
    }
}
