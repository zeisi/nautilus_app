package com.ua.sdk.authentication;

import com.ua.sdk.UaException;

public interface FilemobileCredentialManager {
    FilemobileCredential getFilemobileTokenCredentials() throws UaException;
}
