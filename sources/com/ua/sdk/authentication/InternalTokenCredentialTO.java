package com.ua.sdk.authentication;

import com.google.gson.annotations.SerializedName;

public class InternalTokenCredentialTO {
    @SerializedName("secret")
    String secret;
    @SerializedName("token")
    String token;
    @SerializedName("token_type")
    String tokenType;

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

    public static InternalTokenCredentialTO toTransferObject(InternalTokenCredential from) {
        InternalTokenCredentialTO answer = new InternalTokenCredentialTO();
        answer.setTokenType(from.getTokenType());
        answer.setToken(from.getToken());
        answer.setSecret(from.getSecret());
        return answer;
    }
}
