package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.security.KeyStore;

public class NoKeyStoreFactory implements KeyStoreFactory {
    @Nullable
    public KeyStore create(@NonNull Context context) {
        return null;
    }
}
