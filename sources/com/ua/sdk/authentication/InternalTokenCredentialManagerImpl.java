package com.ua.sdk.authentication;

import com.ua.sdk.UaException;
import com.ua.sdk.authentication.InternalTokenCredentialManager;
import java.util.concurrent.ExecutorService;

public class InternalTokenCredentialManagerImpl implements InternalTokenCredentialManager {
    private final ExecutorService executor;
    private final InternalTokenCredentialService service;

    public InternalTokenCredentialManagerImpl(InternalTokenCredentialService service2, ExecutorService executor2) {
        this.service = service2;
        this.executor = executor2;
    }

    public OAuth2Credentials createForLogin(InternalTokenCredentialManager.TokenType tokenType, String token, String tokenSecret) throws UaException {
        InternalTokenCredential internalTokenCredential = new InternalTokenCredentialImpl();
        internalTokenCredential.setTokenType(tokenType.toString());
        internalTokenCredential.setToken(token);
        internalTokenCredential.setSecret(tokenSecret);
        return this.service.save(internalTokenCredential, false);
    }

    public OAuth2Credentials updateForSync(InternalTokenCredentialManager.TokenType tokenType, String token, String tokenSecret) throws UaException {
        InternalTokenCredential internalTokenCredential = new InternalTokenCredentialImpl();
        internalTokenCredential.setTokenType(tokenType.toString());
        internalTokenCredential.setToken(token);
        internalTokenCredential.setSecret(tokenSecret);
        return this.service.save(internalTokenCredential, true);
    }
}
