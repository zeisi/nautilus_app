package com.ua.sdk.authentication;

import com.ua.sdk.UaException;

public class FilemobileCredentialManagerImpl implements FilemobileCredentialManager {
    private final FilemobileCredentialService service;

    public FilemobileCredentialManagerImpl(FilemobileCredentialService service2) {
        this.service = service2;
    }

    public FilemobileCredential getFilemobileTokenCredentials() throws UaException {
        return this.service.fetchCredentials();
    }
}
