package com.ua.sdk.authentication;

import com.ua.sdk.UaException;

public interface InternalTokenCredentialManager {
    OAuth2Credentials createForLogin(TokenType tokenType, String str, String str2) throws UaException;

    OAuth2Credentials updateForSync(TokenType tokenType, String str, String str2) throws UaException;

    public enum TokenType {
        FACEBOOK("facebook"),
        TWITTER("twitter");
        
        private String name;

        private TokenType(String name2) {
            this.name = name2;
        }

        public String toString() {
            return this.name;
        }
    }
}
